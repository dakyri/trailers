package com.mayaswell.trailers;

import java.util.ArrayList;

/**
 * Created by dak on 5/22/2016.
 */
public class Trailer {

	public  enum LayoutMode {
		COLUMN1,
		COLUMN2,
		COLUMN3,
		FREE

	}
	public class Image {
		public String url = "";
		double aspectRatio = 1;

		public Image(String url, double aspectRatio) {
			this.url = url;
			this.aspectRatio = aspectRatio;
		}
	}
	public class Action {
		public String data = "";
		public String type = "";
		public String layout = "";

		public Action(String data, String type, String layout) {
			this.data = data;
			this.type = type;
			this.layout = layout;
		}
	}

	public String title="";
	public int id=-1;
	public LayoutMode layout=LayoutMode.COLUMN1;
	public ArrayList<Image> images = new ArrayList<Image>();
	public ArrayList<Action> actions = new ArrayList<Action>();

	Trailer(int id, String title, LayoutMode layout) {
		this.id = id;
		this.title = title;
		this.layout = layout;
	}

	public Image addImage(String url, double aspectRatio) {
		Image i = new Image(url, aspectRatio);
		images.add(i);
		return i;
	}

	public Action addAction(String data, String type, String layout) {
		Action a = new Action(data, type, layout);
		actions.add(a);
		return a;
	}


	public int getColumnCount() {
		switch (layout) {
			case COLUMN1:
				return 1;
			case COLUMN2:
				return 2;
			case COLUMN3:
				return 3;
		}
		return 1;
	}


	}
