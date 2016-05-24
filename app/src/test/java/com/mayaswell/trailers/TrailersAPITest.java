package com.mayaswell.trailers;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by dak on 5/22/2016.
 */
public class TrailersAPITest extends TestCase {

	private final String mockResponse = "{\"layout\": \"grid\", \"title\": \"Upcoming\", \"tabs\": null, \"menu\": null, \"sections\": [{\"items\": [{\"images\": [{\"url\": \"http://image.tmdb.org/t/p/original\\/tnJrxidNiBE3kMVxAKYOe5f5Bra.jpg\", \"aspectRatio\": 1.6699999999999999}], \"layout\": \"oneColumn\", \"id\": 241259, \"actions\": [{\"data\": \"actions/trailer/241259\", \"type\": \"getAction\"}, {\"data\": \"views/movie/241259\", \"type\": \"openView\", \"layout\": \"withParallaxImage\"}], \"title\": \"Alice Through the Looking Glass\"}], \"title\": \"2016-05\"}, {\"items\": [{\"images\": [{\"url\": \"http://image.tmdb.org/t/p/original\\/j52KyBIB0MlB8QfegceTQ6y1BHT.jpg\", \"aspectRatio\": 1.6699999999999999}], \"layout\": \"oneColumn\", \"id\": 308531, \"actions\": [{\"data\": \"actions/trailer/308531\", \"type\": \"getAction\"}, {\"data\": \"views/movie/308531\", \"type\": \"openView\", \"layout\": \"withParallaxImage\"}], \"title\": \"Teenage Mutant Ninja Turtles: Out of the Shadows\"}], \"title\": \"2016-06\"}, {\"items\": [{\"images\": [{\"url\": \"http://image.tmdb.org/t/p/original\\/hU0E130tsGdsYa4K9lc3Xrn5Wyt.jpg\", \"aspectRatio\": 0.75}], \"layout\": \"threeColumn\", \"id\": 291805, \"actions\": [{\"data\": \"actions/trailer/291805\", \"type\": \"getAction\"}, {\"data\": \"views/movie/291805\", \"type\": \"openView\", \"layout\": \"withParallaxImage\"}], \"title\": \"Now You See Me 2\"}, {\"images\": [{\"url\": \"http://image.tmdb.org/t/p/original\\/j4HCXbZdwTehmJkOWOpYO8ixtVl.jpg\", \"aspectRatio\": 0.75}], \"layout\": \"threeColumn\", \"id\": 223702, \"actions\": [{\"data\": \"actions/trailer/223702\", \"type\": \"getAction\"}, {\"data\": \"views/movie/223702\", \"type\": \"openView\", \"layout\": \"withParallaxImage\"}], \"title\": \"Sausage Party\"}, {\"images\": [{\"url\": \"http://image.tmdb.org/t/p/original\\/q7GOSxRsBLTJysmAEjFoQZfScsq.jpg\", \"aspectRatio\": 0.75}], \"layout\": \"threeColumn\", \"id\": 302699, \"actions\": [{\"data\": \"actions/trailer/302699\", \"type\": \"getAction\"}, {\"data\": \"views/movie/302699\", \"type\": \"openView\", \"layout\": \"withParallaxImage\"}], \"title\": \"Central Intelligence\"}, {\"images\": [{\"url\": \"http://image.tmdb.org/t/p/original\\/3XuQFGeRLFhZ7HnEgMdTkBVL0Tj.jpg\", \"aspectRatio\": 0.75}], \"layout\": \"threeColumn\", \"id\": 127380, \"actions\": [{\"data\": \"actions/trailer/127380\", \"type\": \"getAction\"}, {\"data\": \"views/movie/127380\", \"type\": \"openView\", \"layout\": \"withParallaxImage\"}], \"title\": \"Finding Dory\"}, {\"images\": [{\"url\": \"http://image.tmdb.org/t/p/original\\/9KQX22BeFzuNM66pBA6JbiaJ7Mi.jpg\", \"aspectRatio\": 0.75}], \"layout\": \"threeColumn\", \"id\": 47933, \"actions\": [{\"data\": \"actions/trailer/47933\", \"type\": \"getAction\"}, {\"data\": \"views/movie/47933\", \"type\": \"openView\", \"layout\": \"withParallaxImage\"}], \"title\": \"Independence Day: Resurgence\"}, {\"images\": [{\"url\": \"http://image.tmdb.org/t/p/original\\/7V5wyODhVBH8DhWRXgKrRcsIutO.jpg\", \"aspectRatio\": 0.75}], \"layout\": \"threeColumn\", \"id\": 328111, \"actions\": [{\"data\": \"actions/trailer/328111\", \"type\": \"getAction\"}, {\"data\": \"views/movie/328111\", \"type\": \"openView\", \"layout\": \"withParallaxImage\"}], \"title\": \"The Secret Life of Pets\"}, {\"images\": [{\"url\": \"http://image.tmdb.org/t/p/original\\/pGledltm2nvouHpIxNwAmaBcQ2T.jpg\", \"aspectRatio\": 0.75}], \"layout\": \"threeColumn\", \"id\": 278154, \"actions\": [{\"data\": \"actions/trailer/278154\", \"type\": \"getAction\"}, {\"data\": \"views/movie/278154\", \"type\": \"openView\", \"layout\": \"withParallaxImage\"}], \"title\": \"Ice Age: Collision Course\"}, {\"images\": [{\"url\": \"http://image.tmdb.org/t/p/original\\/atGuu6Z51UdWaC00Idds02ny1ER.jpg\", \"aspectRatio\": 0.75}], \"layout\": \"threeColumn\", \"id\": 188927, \"actions\": [{\"data\": \"actions/trailer/188927\", \"type\": \"getAction\"}, {\"data\": \"views/movie/188927\", \"type\": \"openView\", \"layout\": \"withParallaxImage\"}], \"title\": \"Star Trek Beyond\"}], \"title\": \"2016-07\"}, {\"items\": [{\"images\": [{\"url\": \"http://image.tmdb.org/t/p/original\\/gtVH1gIhcgba26kPqFfYul7RuPA.jpg\", \"aspectRatio\": 1.6699999999999999}], \"layout\": \"oneColumn\", \"id\": 302401, \"actions\": [{\"data\": \"actions/trailer/302401\", \"type\": \"getAction\"}, {\"data\": \"views/movie/302401\", \"type\": \"openView\", \"layout\": \"withParallaxImage\"}], \"title\": \"Snowden\"}], \"title\": \"2016-09\"}, {\"items\": [{\"images\": [{\"url\": \"http://image.tmdb.org/t/p/original\\/pI9ZUIEXhkHIDka1hyTTDEjjE2I.jpg\", \"aspectRatio\": 0.75}], \"layout\": \"twoColumn\", \"id\": 213681, \"actions\": [{\"data\": \"actions/trailer/213681\", \"type\": \"getAction\"}, {\"data\": \"views/movie/213681\", \"type\": \"openView\", \"layout\": \"withParallaxImage\"}], \"title\": \"Masterminds\"}, {\"images\": [{\"url\": \"http://image.tmdb.org/t/p/original\\/1udSwERoYpIN7Fxmlz1XHWbdqaO.jpg\", \"aspectRatio\": 0.75}], \"layout\": \"twoColumn\", \"id\": 284052, \"actions\": [{\"data\": \"actions/trailer/284052\", \"type\": \"getAction\"}, {\"data\": \"views/movie/284052\", \"type\": \"openView\", \"layout\": \"withParallaxImage\"}], \"title\": \"Doctor Strange\"}], \"title\": \"2016-10\"}, {\"items\": [{\"images\": [{\"url\": \"http://image.tmdb.org/t/p/original\\/ixKzD8p95NIe8trdqrDu9OaJJWO.jpg\", \"aspectRatio\": 1.6699999999999999}], \"layout\": \"oneColumn\", \"id\": 259316, \"actions\": [{\"data\": \"actions/trailer/259316\", \"type\": \"getAction\"}, {\"data\": \"views/movie/259316\", \"type\": \"openView\", \"layout\": \"withParallaxImage\"}], \"title\": \"Fantastic Beasts and Where to Find Them\"}], \"title\": \"2016-11\"}, {\"items\": [{\"images\": [{\"url\": \"http://image.tmdb.org/t/p/original\\/a3PdqSsWHUl7togmMmDynyy4R3f.jpg\", \"aspectRatio\": 1.6699999999999999}], \"layout\": \"oneColumn\", \"id\": 166426, \"actions\": [{\"data\": \"actions/trailer/166426\", \"type\": \"getAction\"}, {\"data\": \"views/movie/166426\", \"type\": \"openView\", \"layout\": \"withParallaxImage\"}], \"title\": \"Pirates of the Caribbean: Dead Men Tell No Tales\"}], \"title\": \"2017-05\"}, {\"items\": [], \"title\": \"2017-06\"}], \"id\": \"upcoming\", \"icon\": \"https://maxcdn.icons8.com/iOS7/PNG/75/Alphabet/tenses-75.png\"}\n";
	private TrailersAPI trailersAPI;

	@Before
	public void setUp() throws Exception {
		trailersAPI = new TrailersAPI("somewhere", "something");
	}

	@Test
	public void testProcessUpcoming() throws Exception {
		JSONObject jsonpool = new JSONObject(mockResponse);
		Upcoming ts = trailersAPI.processUpcoming(jsonpool);
		assertNotNull(ts);
		assertEquals(8, ts.sets.size());
	}

	@Test
	public void testProcessTrailers() throws Exception {
		JSONObject jsonpool = new JSONObject(mockResponse);
		JSONArray jsona = jsonpool.getJSONArray("sections");
		JSONObject jsono = jsona.getJSONObject(0);
		TrailerSet ts = trailersAPI.processTrailers(jsono);
		assertNotNull(ts);
		assertEquals("2016-05", ts.title);
		assertEquals(1, ts.trailers.size());
	}

	@Test
	public void testProcessTrailer() throws Exception {
		JSONObject jsonpool = new JSONObject(mockResponse);
		JSONArray jsona = jsonpool.getJSONArray("sections");
		JSONObject jsono = jsona.getJSONObject(0);
		JSONArray jsoni = jsono.getJSONArray("items");
		assertNotNull(jsoni);
		assertEquals(1, jsoni.length());
		Trailer t = trailersAPI.processTrailer(jsoni.getJSONObject(0));
		assertNotNull(t);
		assertEquals("Alice Through the Looking Glass", t.title);
		assertEquals(241259, t.id);
		assertEquals(Trailer.LayoutMode.COLUMN1, t.layout);
		assertEquals(2, t.actions.size());
		assertEquals("getAction", t.actions.get(0).type);
		assertEquals("actions/trailer/241259", t.actions.get(0).data);
		assertEquals("openView", t.actions.get(1).type);
		assertEquals("views/movie/241259", t.actions.get(1).data);
		assertEquals(1, t.images.size());
		assertEquals("http://image.tmdb.org/t/p/original/tnJrxidNiBE3kMVxAKYOe5f5Bra.jpg", t.images.get(0).url);
		assertEquals(1.67, t.images.get(0).aspectRatio, 0.00001);
	}
}