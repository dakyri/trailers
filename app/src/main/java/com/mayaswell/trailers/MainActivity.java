package com.mayaswell.trailers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;

import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

	private TrailersAPI trailersAPI;
	private TrailerSetAdapter trailerSetAdapter;
	private RecyclerView trailerSetView;
	private GridLayoutManager trailerSetLayoutManager;
	private Upcoming upcoming;
//	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_PROGRESS);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		upcoming = null;

		/* todo check saveInstanceState for prior */

		trailerSetAdapter = new TrailerSetAdapter();
		trailerSetView = (RecyclerView) findViewById(R.id.trailerSetListView);
		trailerSetView.setHasFixedSize(true);
		trailerSetLayoutManager = new GridLayoutManager(this, 6);
		trailerSetLayoutManager.setSpanSizeLookup(trailerSetAdapter.spanLookup);
		trailerSetView.setLayoutManager(trailerSetLayoutManager);
		trailerSetView.setAdapter(trailerSetAdapter);

//		progressBar = (ProgressBar) findViewById(R.id.progressBar);

		String trailersURL = getResources().getString(R.string.trailersURL);
		String trailersToken = getResources().getString(R.string.trailersToken);
		trailersAPI = new TrailersAPI(trailersURL, trailersToken);

	}

	@Override
	protected void onStart()
	{
		super.onStart();

		if (upcoming != null) {
			trailerSetAdapter.clear();
			trailerSetAdapter.addAll(upcoming.sets);
		} else {
			/* todo should be a cleaner way of getting this more rx-ish way via composition */
			showProgress(true);
			trailersAPI.getCurrent(new Subscriber<Upcoming>() {

				@Override
				public void onCompleted() {
					showProgress(false);
					Log.d("Main", "Results are ok");
				}

				@Override
				public void onError(Throwable e) {
					Log.d("Main", "Got error " + e.getMessage());
					showProgress(false);
					e.printStackTrace();
				}

				@Override
				public void onNext(Upcoming u) {
					Log.d("Main", "Got result");
					upcoming = u;
					trailerSetAdapter.clear();
					trailerSetAdapter.addAll(u.sets);
				}
			});
		}
	}

	private void showProgress(boolean b) {
		setProgressBarVisibility(b);
		/*
		if (progressBar != null) {
			progressBar.setVisibility(b?View.VISIBLE:View.GONE);
		}
		*/
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onStop()
	{
		super.onStop();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}
}
