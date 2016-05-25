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

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;
import com.mayaswell.trailers.databinding.TrailerSetListItemBinding;
import com.mayaswell.trailers.databinding.TrailerSetListItemBinding;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dak on 5/22/2016.
 */
public class TrailerSetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private int itemCount = 0;
	private ViewGroup parent;

	private final static class ViewType {
		public static final int TRAILER = 1;
		public static final int TITLE = 0;
	}

	public class SpanLookup extends GridLayoutManager.SpanSizeLookup {

		@Override
		public int getSpanSize(int position) {
			return position >= 0 && position < dataSetWidths.size()? dataSetWidths.get(position):0;
			/*
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
			*/
		}
	}

	public SpanLookup spanLookup = new SpanLookup();
	protected ArrayList<TrailerSet> dataSet = new ArrayList<TrailerSet>();
	protected ArrayList<Integer> dataSetWidths = new ArrayList<Integer>();
	protected ArrayList<Integer> dataSetTypes = new ArrayList<Integer>();
	protected ArrayList<Object> dataSetObjects = new ArrayList<Object>();
	/**
	 * view holder.
	 * assume that all the items in a collection have the same layout mode
	 */
	public static class TitleViewHolder extends RecyclerView.ViewHolder {
		public TrailerSetListItemBinding binding;
		protected RelativeLayout main;
		private TrailerSet trailerSet;

		public TitleViewHolder(TrailerSetListItemBinding v) {
			super(v.getRoot());
			binding = v;
			main = (RelativeLayout) v.getRoot();
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
		private final RelativeLayout main;
		private final TextView nameView;
		private final ImageView imageView;
		private Trailer trailer ;

		public ImageViewHolder(RelativeLayout v) {
			super(v);
			main = v;
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
					AlertDialog.Builder d = new AlertDialog.Builder(main.getContext());
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
			double imageAR = t.images.size() > 0 ? t.images.get(0).aspectRatio : 1;
/** todo try to set the most appropriate width. at the moment, this approach is playing havoc with bitmap caching */
			int w = 0;
			int h = 0;
			if (parent != null) {
				int nc = t.getColumnCount();
				w = parent.getMeasuredWidth()/nc;
				h = (int)(w / imageAR);
			}
			/*
			if (w > 0) {
				int nc = t.getColumnCount();
				imageView.getLayoutParams().width = w/nc;
				imageView.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
				Log.d("TrailerAdapter", "set layout params " + (w/nc));
			}*/
			Log.d("TrailerAdapter", "set to " + t.title + ", " + (imageUrl != null ? imageUrl : "no image") + " main w " + w + " h " + h);
			if (t.layout == Trailer.LayoutMode.COLUMN1) {
				nameView.setText(t.title);
				nameView.setVisibility(View.VISIBLE);
			} else {
				nameView.setVisibility(View.GONE);
			}
			if (imageUrl != null) {
				/*Picasso*/
				DrawableTypeRequest<String> im = Glide.with(main.getContext()).load(imageUrl);
				if (w > 0 && h > 0) {
					im.override(w, h).into(imageView);
				} else {
					im.into(imageView);
				}
			} else {
				imageView.setImageResource(android.R.color.transparent);
			}
		}
	}


	public void clear() {
		dataSet.clear();
		dataSetWidths.clear();
		dataSetTypes.clear();
		dataSetObjects.clear();
		itemCount = 0;
		notifyDataSetChanged();
	}

	public void addAll(Collection<TrailerSet> trailers) {
		dataSet.addAll(trailers);
		for (TrailerSet ts: trailers) {
			dataSetWidths.add(6);
			dataSetTypes.add(ViewType.TITLE);
			dataSetObjects.add(ts);
			for (Trailer t: ts.trailers) {
				int w = 6;
				switch (ts.layout) {
					case COLUMN1: w = 6; break;
					case COLUMN2: w = 3; break;
					case COLUMN3: w = 2; break;
				}

				dataSetWidths.add(w);
				dataSetTypes.add(ViewType.TRAILER);
				dataSetObjects.add(t);
			}
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
		this.parent = parent;
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
		/*
		int p = 0;
		for (TrailerSet ts: dataSet) {
			if (p == position) { // header
				return ViewType.TITLE;
			} else if (position < (p + ts.trailers.size() + 1)) {
				return ViewType.TRAILER;
			}
			p += ts.trailers.size() + 1;
		}*/
		return position >= 0 && position<dataSetTypes.size()? dataSetTypes.get(position):ViewType.TRAILER;
	}

	/**
	 * - get element from your dataset at this position
	 * - replace the contents of the view with that element
	 *
	 * todo index the objects according to position
	 *
	 * @param holder
	 * @param position
	 */
	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		int type = getItemViewType(position);
		int p = 0;
		Object o = position >= 0 && position < dataSetObjects.size()? dataSetObjects.get(position): null;
		if (o != null) {
			if (o instanceof Trailer && holder instanceof ImageViewHolder) {
				((ImageViewHolder) holder).setTrailer((Trailer) o);
			} else if (o instanceof TrailerSet && holder instanceof TitleViewHolder) {
				((TitleViewHolder)holder).setToTrailer((TrailerSet) o);
			}
		}
		/*
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
		}*/
	}

	@Override
	public int getItemCount() {
		return itemCount;
	}
}