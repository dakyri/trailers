package com.mayaswell.trailers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mayaswell.trailers.databinding.TrailerSetListItemBinding;
import com.mayaswell.trailers.databinding.TrailerSetListItemBinding;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dak on 5/22/2016.
 */
public class TrailerSetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private int itemCount = 0;

	private final static class ViewType {
		public static final int TRAILER = 1;
		public static final int TITLE = 0;
	}

	public class SpanLookup extends GridLayoutManager.SpanSizeLookup {

		@Override
		public int getSpanSize(int position) {
			int p = 0;
			for (TrailerSet ts: dataSet) {
				if (p == position) { // header
					return 6;
				} else if (position < (p + ts.trailers.size() + 1)) {
					switch (ts.layout) {
						case COLUMN1: return 6;
						case COLUMN2: return 3;
						case COLUMN3: return 2;
					}
				}
				p += 1 + ts.trailers.size();
			}
			return 0;
		}
	}

	public SpanLookup spanLookup = new SpanLookup();
	protected ArrayList<TrailerSet> dataSet = new ArrayList<TrailerSet>();

	/**
	 * view holder.
	 * assume that all the items in a collection have the same layout mode
	 */
	public static class TitleViewHolder extends RecyclerView.ViewHolder {
		public TrailerSetListItemBinding binding;
		protected RelativeLayout parent;
		private TrailerSet trailerSet;

		public TitleViewHolder(TrailerSetListItemBinding v) {
			super(v.getRoot());
			binding = v;
			parent = (RelativeLayout) v.getRoot();
			trailerSet = null;
		}

		public void setToTrailer(TrailerSet trailerSet) {
			Log.d("TrailerSetAdapater", "setting to " + trailerSet.title);
			this.trailerSet = trailerSet;
			binding.setTrailers(trailerSet);
		}
	}

	/**
	 * todo convert to binding
	 */
	public class ImageViewHolder extends RecyclerView.ViewHolder {
		private final RelativeLayout parent;
		private final TextView nameView;
		private final ImageView imageView;
		private Trailer trailer ;

		public ImageViewHolder(RelativeLayout v) {
			super(v);
			parent = v;
			trailer = null;
			nameView = (TextView) v.findViewById(R.id.itemNameView);
			imageView = (ImageView) v.findViewById(R.id.itemImageView);
		}

		public void setTrailer(Trailer t) {
			trailer = t;
			/* todo convert to reactive */
			imageView.setOnClickListener(new View.OnClickListener() {
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
				/*Picasso*/
				Glide.with(parent.getContext())
						.load(imageUrl)
						.into(imageView);
			} else {
				imageView.setImageResource(android.R.color.transparent);
			}
		}
	}


	public void clear() {
		dataSet.clear();
		itemCount = 0;
		notifyDataSetChanged();
	}

	public void addAll(Collection<TrailerSet> trailers) {
		dataSet.addAll(trailers);
		for (TrailerSet ts: trailers) {
			itemCount += ts.trailers.size() + 1;
		}
		notifyDataSetChanged();
	}

	/**
	 * create a new view
	 *
	 * @param parent
	 * @param viewType
	 * @return
	 */
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		RecyclerView.ViewHolder vh = null;
		if (viewType == ViewType.TRAILER) {
			RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list_item, parent, false);
			vh = new ImageViewHolder(v);
		} else if (viewType == ViewType.TITLE) {
			TrailerSetListItemBinding binding = TrailerSetListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
			vh = new TitleViewHolder(binding);
		} else { // nooooooo
			Log.d("TrailerSetAdapter", "null view holder, unknown type "+viewType);
		}
		return vh;
	}

	@Override
	public int getItemViewType(int position) {
		int p = 0;
		for (TrailerSet ts: dataSet) {
			if (p == position) { // header
				return ViewType.TITLE;
			} else if (position < (p + ts.trailers.size() + 1)) {
				return ViewType.TRAILER;
			}
			p += ts.trailers.size() + 1;
		}
		return 0;
	}

	/**
	 * - get element from your dataset at this position
	 * - replace the contents of the view with that element
	 *
	 * @param holder
	 * @param position
	 */
	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		int type = getItemViewType(position);
		int p = 0;
		for (TrailerSet ts: dataSet) {
			if (position == 0) { // header
				((TitleViewHolder)holder).setToTrailer(ts);
				return;
			} else if (position > 0){
				position--;
				if (position < ts.trailers.size()) {
					Trailer t = ts.trailers.get(position);
					((ImageViewHolder) holder).setTrailer(t);
					return;
				}
				position -= ts.trailers.size();
			} else {
				Log.d("TrailerSetAdapter", "position goes negative in onBind");
			}
		}
	}

	@Override
	public int getItemCount() {
		return itemCount;
	}
}