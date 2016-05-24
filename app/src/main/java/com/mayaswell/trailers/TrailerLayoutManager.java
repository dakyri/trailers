package com.mayaswell.trailers;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dak on 5/24/2016.
 *    based on
 * http://stackoverflow.com/questions/32011995/how-to-have-a-listview-recyclerview-inside-a-parent-recyclerview-i-have-a-pare
 */
public class TrailerLayoutManager extends GridLayoutManager {
	private static final String TAG = TrailerLayoutManager.class.getSimpleName();

	public TrailerLayoutManager(Context context, int ns) {
		super(context, ns);
	}

	private int[] mMeasuredDimension = new int[2];


	@Override
	public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {

		final int widthMode = View.MeasureSpec.getMode(widthSpec);
		final int heightMode = View.MeasureSpec.getMode(heightSpec);
		final int widthSize = View.MeasureSpec.getSize(widthSpec);
		final int heightSize = View.MeasureSpec.getSize(heightSpec);

		int width = 0;
		int height = 0;
		int spanMeasure = 0;
		int span = 0;
		for (int i = 0; i < getItemCount(); i++) {
			measureScrapChild(recycler, i,
					View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
					View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
					mMeasuredDimension);


			if (getOrientation() == HORIZONTAL) {
				if (mMeasuredDimension[1] > spanMeasure) {
					spanMeasure = mMeasuredDimension[1];
				}
				if ((i+1) % getSpanCount() == 0) {
					width += spanMeasure;
					spanMeasure = 0;
					span++;
				}
				if (i == 0) {
					height = mMeasuredDimension[1];
				}
			} else {
				if (mMeasuredDimension[1] > spanMeasure) {
					spanMeasure = mMeasuredDimension[1];
				}
				if ((i+1) % getSpanCount() == 0) {
					height += spanMeasure;
					spanMeasure = 0;
					span++;
				}
				if (i == 0) {
					width = mMeasuredDimension[0];
				}
			}
		}
		height += spanMeasure;
		switch (widthMode) {
			case View.MeasureSpec.EXACTLY:
				width = widthSize;
			case View.MeasureSpec.AT_MOST:
			case View.MeasureSpec.UNSPECIFIED:
		}

		switch (heightMode) {
			case View.MeasureSpec.EXACTLY:
				height = heightSize;
			case View.MeasureSpec.AT_MOST:
			case View.MeasureSpec.UNSPECIFIED:
		}

		Log.d("TrailerLayoutManager", "got " + width + ". " + height + " span count "+getSpanCount()+" nspan "+span);

		setMeasuredDimension(width, height);
	}

	private void measureScrapChild(RecyclerView.Recycler recycler, int position, int widthSpec,
								   int heightSpec, int[] measuredDimension) {
		try {
			View view = recycler.getViewForPosition(0);//fix 动态添加时报IndexOutOfBoundsException

			if (view != null) {
				RecyclerView.LayoutParams p = (RecyclerView.LayoutParams) view.getLayoutParams();

				int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,
						getPaddingLeft() + getPaddingRight(), p.width);

				int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
						getPaddingTop() + getPaddingBottom(), p.height);

				view.measure(childWidthSpec, childHeightSpec);
				measuredDimension[0] = view.getMeasuredWidth() + p.leftMargin + p.rightMargin;
				measuredDimension[1] = view.getMeasuredHeight() + p.bottomMargin + p.topMargin;
				recycler.recycleView(view);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
