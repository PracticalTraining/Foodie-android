package cn.edu.bjtu.foodie_android.UI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cn.edu.bjtu.foodie_android.R;

public class HomeActivity extends BaseActivity {
	private RadioGroup home_radio_button_group;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initView();
		setListener();

	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		home_radio_button_group = (RadioGroup) findViewById(R.id.home_radio_button_group);
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		home_radio_button_group
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						switch (checkedId) {
						case R.id.home_tab_main:// 主页
							System.out.println("主页");
							break;
						case R.id.home_tab_search:// 搜索
							System.out.println("搜索");
							break;
						case R.id.home_tab_category:// 分类
							System.out.println("分类");
							break;
						case R.id.home_tab_personal:// 个人中心
							System.out.println("进入个人中心");
							Intent intent = new Intent(HomeActivity.this,
									MyAccountActivity.class);
							startActivity(intent);
							break;
						default:
							break;
						}
					}
				});
	}
}
