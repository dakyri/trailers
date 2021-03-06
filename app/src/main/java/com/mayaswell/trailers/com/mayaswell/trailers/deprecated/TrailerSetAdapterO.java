package com.mayaswell.trailers.com.mayaswell.trailers.deprecated;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mayaswell.trailers.R;
import com.mayaswell.trailers.Trailer;
import com.mayaswell.trailers.TrailerSet;
import com.mayaswell.trailers.databinding.TrailerSetListItemBinding;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by dak on 5/22/2016.
 */
public class TrailerSetAdapterO extends RecyclerView.Adapter<TrailerSetAdapterO.ViewHolder> {
	ArrayList<TrailerSet> dataSet = new ArrayList<TrailerSet>();

	/**
	 * view holder.
	 * assume that all the items in a collection have the same layout mode
	 */
	public static class ViewHolder extends RecyclerView.ViewHolder {
		private final TrailerAdapter trailerAdapter;
		private final RecyclerView trailerView;
		private final TrailerLayoutManager trailerLayoutManager;
		public TrailerSetListItemBinding binding;
		protected RelativeLayout parent;
		private TrailerSet trailerSet;
		private Trailer.LayoutMode layoutMode;

		public ViewHolder(TrailerSetListItemBinding v) {
			super(v.getRoot());
			binding = v;
			parent = (RelativeLayout) v.getRoot();
			trailerSet = null;
			trailerAdapter = new TrailerAdapter();
			trailerView = (RecyclerView) parent.findViewById(R.id.trailerListView);
			trailerLayoutManager = new TrailerLayoutManager(parent.getContext(), 1);
			trailerView.setLayoutManager(trailerLayoutManager);
			trailerView.setAdapter(trailerAdapter);
		}

		public void setToTrailer(TrailerSet trailerSet) {
			Log.d("TrailerSetAdapater", "setting to " + trailerSet.title);
			this.trailerSet = trailerSet;
			binding.setTrailers(trailerSet);

			layoutMode = trailerSet.trailers.size() > 0? trailerSet.trailers.get(0).layout: Trailer.LayoutMode.COLUMN1;
			int nColumns=1;
			switch (layoutMode) {
				case COLUMN1: nColumns = 1; break;
				case COLUMN2: nColumns = 2; break;
				case COLUMN3: nColumns = 3; break;
			}
			trailerLayoutManager.setSpanCount(nColumns);

			trailerAdapter.setTo(trailerSet.trailers);
		}
	}

	public void clear() {
		dataSet.clear();
	}

	public void addAll(Collection<TrailerSet> trailers) {
		dataSet.addAll(trailers);
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
	public TrailerSetAdapterO.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//		RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_set_list_item, parent, false);
		TrailerSetListItemBinding binding = TrailerSetListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

		ViewHolder vh = new ViewHolder(binding);
		return vh;
	}

	@Override
	public int getItemViewType(int pos) {
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
	public void onBindViewHolder(TrailerSetAdapterO.ViewHolder holder, int position) {
		holder.setToTrailer(dataSet.get(position));
	}

	@Override
	public int getItemCount() {
		return dataSet.size();
	}
}