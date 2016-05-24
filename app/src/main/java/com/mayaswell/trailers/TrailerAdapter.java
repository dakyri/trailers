package com.mayaswell.trailers;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dak on 5/24/2016.
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
			String imageUrl = t.images.size() > 0? t.images.get(0).url: null;
			Log.d("TrailerAdapter", "set to "+t.title+", "+(imageUrl != null? imageUrl: "no image"));
			nameView.setText(t.title);
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
		Log.d("TrailerAdapter", "iten count "+dataSet.size());
		return dataSet.size();
	}
}
