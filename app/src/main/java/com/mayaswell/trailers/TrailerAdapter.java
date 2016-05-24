package com.mayaswell.trailers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dak on 5/24/2016.
 *
 * todo perhaps this is better off as a simple GridView
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
	ArrayList<Trailer> dataSet = new ArrayList<Trailer>();

	public class ViewHolder extends RecyclerView.ViewHolder {
		private final RelativeLayout parent;
		private final TextView nameView;
		private final ImageView imageView;
		private Trailer trailer ;

		public ViewHolder(RelativeLayout v) {
			super(v);
			parent = v;
			trailer = null;
			nameView = (TextView) v.findViewById(R.id.itemNameView);
			imageView = (ImageView) v.findViewById(R.id.itemImageView);
		}

		public void setTrailer(Trailer t) {
			trailer = t;
			/* todo convert to reactive */
			imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String actionMsg = "clicked";
					if (trailer.actions.size() > 0) {
						Trailer.Action a = trailer.actions.get(0);
						actionMsg = a.data+" layout \""+a.layout+"\" type \"" + a.type + "\"";
					}
					AlertDialog.Builder d = new AlertDialog.Builder(parent.getContext());
					d.setTitle("something happened");
					d.setMessage(actionMsg);
					d.setPositiveButton("ok", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int whichButton) {
							dialog.dismiss();
						}
					});
					d.show();
				}
			});
			String imageUrl = t.images.size() > 0 ? t.images.get(0).url : null;
			Log.d("TrailerAdapter", "set to " + t.title + ", " + (imageUrl != null ? imageUrl : "no image") + " parent w " + parent.getMeasuredWidth());
			if (t.layout == Trailer.LayoutMode.COLUMN1) {
				nameView.setText(t.title);
				nameView.setVisibility(View.VISIBLE);
			} else {
				nameView.setVisibility(View.GONE);
			}
			if (imageUrl != null) {
				Picasso.with(parent.getContext())
						.load(imageUrl)
						.into(imageView);
			} else {
				imageView.setImageResource(android.R.color.transparent);
			}
		}
	}
	public void clear() {
		dataSet.clear();
		notifyDataSetChanged();
	}

	public void addAll(Collection<Trailer> trailers) {
		dataSet.addAll(trailers);
		notifyDataSetChanged();
	}

	public void setTo(Collection<Trailer> trailers) {
		Log.d("TrailerAdapter", "setting " + trailers.size());
		dataSet.clear();
		dataSet.addAll(trailers);
		notifyDataSetChanged();
	}

	/**
	 *   create a new view
	 * @param parent
	 * @param viewType
	 * @return
	 */
	@Override
	public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list_item, parent, false);
		ViewHolder vh = new ViewHolder(v);
		Log.d("TrailerAdapter", "creating v ");
		return vh;
	}

	@Override
	public int getItemViewType(int pos) {
		return 0;
	}

	/**
	 * 	- get element from your dataset at this position
	 *  - replace the contents of the view with that element
	 * @param holder
	 * @param position
	 */
	@Override
	public void onBindViewHolder(TrailerAdapter.ViewHolder holder, int position) {
		Log.d("TrailerAdapter", "binding v " + position);
		Trailer t = dataSet.get(position);
		if (t == null) {
			Log.d("TrailerAdapter", "null item");
		} else {
			holder.setTrailer(t);
		}
	}

	@Override
	public int getItemCount() {
//		Log.d("TrailerAdapter", "iten count "+dataSet.size());
		return dataSet.size();
	}
}
