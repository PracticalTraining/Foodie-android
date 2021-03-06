package cn.edu.bjtu.foodie_android.UI;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import cn.edu.bjtu.foodie_android.R;
import cn.edu.bjtu.foodie_android.bean.Dish;
import cn.edu.bjtu.foodie_android.bean.Restaurant;
import cn.edu.bjtu.foodie_android.global.GlobalParams;
import cn.edu.bjtu.foodie_android.manager.ApplicationController;
import cn.edu.bjtu.foodie_android.service.ListenNetStateService;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;

public class SplashActivity extends Activity {

	private ImageView mSplashItem_iv;
	private LocationClient mLocClient;
	private MyLocationListenner myListener;
	public static final String PREFS_NAME = "Data";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_splash);

		getRestaurantRequest();
		getDishsRequest();
		mLocClient = new LocationClient(this);
		myListener = new MyLocationListenner();
		// 定位
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
		option.setOpenGps(true);// 打开gps
		option.SetIgnoreCacheException(true);
		mLocClient.setLocOption(option);
		mLocClient.registerLocationListener(myListener);
		mLocClient.start();
		findViewById();
		Intent intent = new Intent(getApplicationContext(),
				ListenNetStateService.class);
		startService(intent);
		initView();
	}

	public void findViewById() {
		mSplashItem_iv = (ImageView) findViewById(R.id.splash_loading_item);
	}

	public void initView() {
		// TODO Auto-generated method stub
		Animation translate = AnimationUtils.loadAnimation(this,
				R.anim.splash_loading);
		translate.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				overridePendingTransition(R.anim.push_left_in,
						R.anim.push_left_out);
				Intent intent = new Intent(SplashActivity.this,
						HomeActivity.class);
				startActivity(intent);
				int version = Integer.valueOf(android.os.Build.VERSION.SDK);
				if (version >= 5) {
					overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
				}
				SplashActivity.this.finish();
			}
		});
		mSplashItem_iv.setAnimation(translate);
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			String addStr = location.getAddrStr();
			// 纪录当前的位置
			GlobalParams.LOCATION = addStr;
			GlobalParams.LAT = location.getLatitude();
			GlobalParams.LONG = location.getLongitude();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mLocClient.unRegisterLocationListener(myListener);
		mLocClient.stop();
	}

	public void getDishsRequest() {
		String finalurl = getString(R.string.URL) + "dish/searchdish";

		JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
				finalurl, null, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							VolleyLog.v("Response:%s%n", response.toString(4));
							Log.d("Response:", response.toString());
							if (response.has("dish")) {
								for (int i = 0; i < response.getJSONArray(
										"dish").length(); i++) {
									JSONObject jsonobject = response
											.getJSONArray("dish")
											.getJSONObject(i);
									Dish my_dish = new Dish(0,
											jsonobject.getString("name"),
											jsonobject.getInt("price"),
											jsonobject.getInt("restId"));

									ApplicationController.getInstance()
											.getDish_list().add(my_dish);
								}
								Log.d("DishList:", ApplicationController
										.getInstance().getDish_list()
										.toString());

							}
							if (response.has("errorCode")) {
								if (response.getString("errorCode")
										.equals("-1")) {
									int duration = Toast.LENGTH_LONG;
									Toast toast = Toast.makeText(
											getApplicationContext(),
											getText(R.string.generic_error),
											duration);
									toast.show();
								}
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(getApplicationContext(),
								getText(R.string.no_internet),
								Toast.LENGTH_SHORT).show();
						VolleyLog.e("Error: ", error.getMessage());
					}
				});
		// add the request object to the queue to be executed
		ApplicationController.getInstance().addToRequestQueue(req, "getDish");

	}

	public void getRestaurantRequest() {
		String finalurl = getString(R.string.URL) + "restaurant/searchall";

		JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,
				finalurl, null, new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							VolleyLog.v("Response:%s%n", response.toString(4));
							Log.d("Response:", response.toString());
							if (response.has("restaurants")) {
								for (int i = 0; i < response.getJSONArray(
										"restaurants").length(); i++) {
									JSONObject jsonobject = response
											.getJSONArray("restaurants")
											.getJSONObject(i);
									int id = Integer.parseInt(jsonobject
											.getString("id"));
									float longitude = (float) jsonobject
											.getDouble("longitude");
									float latitude = (float) jsonobject
											.getDouble("latitude");

									Restaurant my_restaurant = new Restaurant(
											id,
											jsonobject.getString("name"),
											jsonobject.getString("description"),
											longitude, latitude, jsonobject
													.getString("pictureUrl"));

									ApplicationController.getInstance()
											.getRestaurant_list()
											.add(my_restaurant);
								}

							}
							if (response.has("errorCode")) {
								if (response.getString("errorCode")
										.equals("-1")) {
									int duration = Toast.LENGTH_LONG;
									Toast toast = Toast.makeText(
											getApplicationContext(),
											getText(R.string.generic_error),
											duration);
									toast.show();
								}
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(getApplicationContext(),
								getText(R.string.no_internet),
								Toast.LENGTH_SHORT).show();
						VolleyLog.e("Error: ", error.getMessage());
					}
				});
		// add the request object to the queue to be executed
		ApplicationController.getInstance().addToRequestQueue(req,
				"getRestaurant");
	}

}
