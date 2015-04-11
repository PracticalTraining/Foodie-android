package cn.edu.bjtu.foodie_android.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.edu.bjtu.foodie_android.R;

public class MyAccountActivity extends BaseActivity {
	private Button btn_myebuy_login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myaccount);
		initView();
		setListener();
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		btn_myebuy_login = (Button) findViewById(R.id.btn_myebuy_login);
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		btn_myebuy_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyAccountActivity.this,
						LoginActivity.class);
				startActivity(intent);
			}
		});
	}
}
