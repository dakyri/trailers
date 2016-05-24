package com.mayaswell.trailers;

import java.util.ArrayList;

/**
 * Created by dak on 5/22/2016.
 */
public class TrailerSet {
	public String title;
	public ArrayList<Trailer> trailers;

	public TrailerSet(String title) {
		this.title = title;
		this.trailers = new ArrayList<Trailer>();
	}

	public boolean add(Trailer t) {
		return trailers.add(t);
	}
}
