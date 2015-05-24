package cn.edu.bjtu.foodie_android.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import cn.edu.bjtu.foodie_android.R;
import cn.edu.bjtu.foodie_android.global.GlobalParams;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_splash);
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
}
