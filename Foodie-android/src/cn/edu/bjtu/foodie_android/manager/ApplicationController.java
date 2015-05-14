package cn.edu.bjtu.foodie_android.manager;

import org.apache.http.HttpRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

import android.app.Application;
import android.text.TextUtils;

public class ApplicationController extends Application {

	 public static final String TAG = "VolleyPatterns";

	    private RequestQueue mRequestQueue;

	    private static          ApplicationController           sInstance;
	    private                 DefaultHttpClient               mHttpClient;
	    private static          HttpRequest                     my_httpRequest;


	    @Override
	    public void onCreate() {
	        super.onCreate();
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

	  /*  public void initializeRequestQueue() {

	        my_httpRequest.getRequest();

	    }*/


	    public void cancelPendingRequests(Object tag) {
	        if (mRequestQueue != null) {
	            mRequestQueue.cancelAll(tag);
	        }
	    }



}
