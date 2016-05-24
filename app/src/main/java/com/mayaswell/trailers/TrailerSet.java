package com.mayaswell.trailers;

import java.util.ArrayList;

/**
 * Created by dak on 5/22/2016.
 */
public class TrailerSet {
	public Trailer.LayoutMode layout;
	public String title;
	public ArrayList<Trailer> trailers;

	public TrailerSet(String title) {
		this.title = title;
		this.trailers = new ArrayList<Trailer>();
		this.layout = Trailer.LayoutMode.COLUMN1;
	}

	public boolean add(Trailer t) {
		layout = t.layout;
		return trailers.add(t);
	}
}
