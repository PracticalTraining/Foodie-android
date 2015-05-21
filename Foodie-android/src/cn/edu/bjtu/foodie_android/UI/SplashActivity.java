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

import com.baidu.mapapi.SDKInitializer;

public class SplashActivity extends Activity {

	private ImageView mSplashItem_iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SDKInitializer.initialize(getApplicationContext());

		setContentView(R.layout.activity_splash);
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
}
