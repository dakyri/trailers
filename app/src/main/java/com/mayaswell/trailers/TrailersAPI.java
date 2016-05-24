package com.mayaswell.trailers;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by dak on 5/22/2016.
 */
public class TrailersAPI {

	private final String token;
	private final String url;
	private Observable<Response> okObservable;

	public TrailersAPI(String u, String t) {
		this.url = u;
		this.token = t;
//		Log.d("TrailersAPI", "making api");
		okObservable = Observable.create(new Observable.OnSubscribe<Response>() {
			OkHttpClient client = new OkHttpClient();

			@Override
			public void call(Subscriber<? super Response> subscriber) {
				try {
					Request okRequest = new Request.Builder().url(url).header("token", token).build();
					Response response = client.newCall(okRequest).execute();
					subscriber.onNext(response);
					if (!response.isSuccessful()) {
						subscriber.onError(new Exception("error"));
					} else {
						subscriber.onCompleted();
					}
				} catch (IOException e) {
					subscriber.onError(e);
				}
			}
		});
	}

	public void getCurrent(final Subscriber<Upcoming> su) {
		okObservable.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<Response>() {
					@Override
					public void onCompleted() {
						su.onCompleted();
						unsubscribe();
					}

					@Override
					public void onError(Throwable e) {
						su.onError(e);
						unsubscribe();
					}

					@Override
					public void onNext(Response response) {
						int responseCode = response.code();
						if (responseCode != HttpURLConnection.HTTP_OK) {
							su.onError(new Exception("Bad response code "+responseCode));
							return;
						}
						String responseBody = "";
						try {
							responseBody = response.body().string();
						} catch (IOException e) {
							su.onError(e);
							return;
						}
						try {
							JSONObject jsono = new JSONObject(responseBody);
							Upcoming u = processUpcoming(jsono);
							if (u != null) {
								su.onNext(u);
							} else {
								su.onError(new Exception("Unexpected null result processing JSON"));
							}
						} catch (JSONException e) {
							su.onError(e);
							return;
						}


					}
				});
	}

	public Upcoming processUpcoming(JSONObject jo) throws JSONException {
		Upcoming al = new Upcoming();
		JSONArray seca = jo.getJSONArray("sections");
		for (int i=0; i<seca.length(); i++) {
			JSONObject jsoi = seca.getJSONObject(i);
			TrailerSet trailerSet = processTrailers(jsoi);
			al.add(trailerSet);
		}
		return al;
	}

	public TrailerSet processTrailers(JSONObject jo) throws JSONException {
		String title = jo.getString("title");
		TrailerSet tso = new TrailerSet(title);
		JSONArray jsa = jo.getJSONArray("items");
		for (int i=0; i<jsa.length(); i++) {
			JSONObject jsto = jsa.getJSONObject(i);
			tso.add(processTrailer(jsto));
		}
		return tso;
	}

	public Trailer processTrailer(JSONObject jo) throws JSONException {
		int id = jo.getInt("id");
		String title = jo.getString("title");
		String layoutS = jo.getString("layout");
		Trailer.LayoutMode layout = Trailer.LayoutMode.COLUMN1;
		if (layoutS.equals("oneColumn")) {
			layout = Trailer.LayoutMode.COLUMN1;
		} else if (layoutS.equals("twoColumn")) {
			layout = Trailer.LayoutMode.COLUMN2;
		} else if (layoutS.equals("threeColumn")) {
			layout = Trailer.LayoutMode.COLUMN3;
		}

		Trailer t = new Trailer(id, title, layout);
		JSONArray ja = jo.getJSONArray("images");
		for (int i=0; i<ja.length(); i++) {
			JSONObject jio = ja.getJSONObject(i);
			String url = jio.getString("url");
			double ar = jio.getDouble("aspectRatio");
			t.addImage(url, ar);
		}
		ja = jo.getJSONArray("actions");
		for (int i=0; i<ja.length(); i++) {
			JSONObject jio = ja.getJSONObject(i);
			String data= jio.getString("data");
			String type = jio.getString("type");

			String alayout;
			if (jio.has("layout")) {
				alayout = jio.getString("layout");
			} else {
				alayout = "";
			}
			t.addAction(data, type, alayout);
		}
		return t;
	}
}
