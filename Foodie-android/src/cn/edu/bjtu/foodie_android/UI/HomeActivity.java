package cn.edu.bjtu.foodie_android.UI;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cn.edu.bjtu.foodie_android.R;
import cn.edu.bjtu.foodie_android.fragment.CategoryFragment;
import cn.edu.bjtu.foodie_android.fragment.HomeFragment;
import cn.edu.bjtu.foodie_android.fragment.MyAccountFragment;
import cn.edu.bjtu.foodie_android.fragment.OrderFragment;

public class HomeActivity extends BaseActivity {
	// private RadioGroup home_radio_button_group;

	private RadioGroup home_radio_button_group;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		HomeFragment fragment = new HomeFragment();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.ll_mid_content, fragment).commit();
		initView();
		setListener();

	}

	@Override
	protected void initView() {
		home_radio_button_group = (RadioGroup) findViewById(R.id.home_radio_button_group);
	}

	@Override
	protected void setListener() {
		home_radio_button_group
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						switch (checkedId) {
						case R.id.home_tab_main:
							HomeFragment homeFragment = new HomeFragment();
							getSupportFragmentManager().beginTransaction()
									.replace(R.id.ll_mid_content, homeFragment)
									.commit();
							break;
						case R.id.home_tab_search:
							OrderFragment searchFragment = new OrderFragment();
							getSupportFragmentManager()
									.beginTransaction()
									.replace(R.id.ll_mid_content,
											searchFragment).commit();
							break;
						case R.id.home_tab_category:
							CategoryFragment categoryFragment = new CategoryFragment();
							getSupportFragmentManager()
									.beginTransaction()
									.replace(R.id.ll_mid_content,
											categoryFragment).commit();
							break;
						case R.id.home_tab_personal:
							MyAccountFragment accountFragment = new MyAccountFragment();
							getSupportFragmentManager()
									.beginTransaction()
									.replace(R.id.ll_mid_content,
											accountFragment).commit();
							break;
						default:
							break;
						}
					}
				});
	}
}
