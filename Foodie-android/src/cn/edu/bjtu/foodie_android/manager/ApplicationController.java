package cn.edu.bjtu.foodie_android.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Application;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import cn.edu.bjtu.foodie_android.bean.Dish;
import cn.edu.bjtu.foodie_android.bean.Restaurant;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class ApplicationController extends Application {

	public static final String TAG = "VolleyPatterns";

	private RequestQueue mRequestQueue;

	private static ApplicationController sInstance;
	private DefaultHttpClient mHttpClient;
	private HttpRequest my_httpRequest;
	private List<Dish> dish_list;
	private List<Restaurant> restaurant_list;
	private ImageLoader imageLoader;

	@Override
	public void onCreate() {
		super.onCreate();
		this.dish_list = new ArrayList<Dish>();
		this.restaurant_list = new ArrayList<Restaurant>();
		imageLoader = new ImageLoader(this.getRequestQueue(),
				new ImageLoader.ImageCache() {
					private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(
							20);

					@Override
					public Bitmap getBitmap(String url) {
						return cache.get(url);
					}

					@Override
					public void putBitmap(String url, Bitmap bitmap) {
						cache.put(url, bitmap);
					}
				});
		// initialize the singleton
		sInstance = this;
	}

	/**
	 * @return ApplicationController singleton instance
	 */
	public static synchronized ApplicationController getInstance() {
		return sInstance;
	}

	public RequestQueue getRequestQueue() {
		// lazy initialize the request queue, the queue instance will be
		// created when it is accessed for the first time
		if (mRequestQueue == null) {
			mHttpClient = new DefaultHttpClient();
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

		VolleyLog.d("Adding request to queue: %s", req.getUrl());

		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		// set the default tag if tag is empty
		req.setTag(TAG);

		getRequestQueue().add(req);
	}

	/*
	 * public void initializeRequestQueue() {
	 * 
	 * my_httpRequest.getRequest();
	 * 
	 * }
	 */

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

	public List<Dish> getDish_list() {
		return dish_list;
	}

	public void setDish_list(List<Dish> dish_list) {
		this.dish_list = dish_list;
	}

	public List<Restaurant> getRestaurant_list() {
		return restaurant_list;
	}

	public void setRestaurant_list(List<Restaurant> restaurant_list) {
		this.restaurant_list = restaurant_list;
	}

	public List<Dish> getDishByRestId(int restId) {
		List<Dish> restDishList = new ArrayList<Dish>();

		for (int i = 0; i < dish_list.size(); i++) {
			if (dish_list.get(i).getRestId() == restId)
				restDishList.add(dish_list.get(i));
		}
		return restDishList;
	}

	public ImageLoader getImageLoader() {
		return imageLoader;
	}

}
