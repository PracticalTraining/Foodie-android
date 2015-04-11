package cn.edu.bjtu.foodie_android.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.edu.bjtu.foodie_android.R;

public class LoginActivity extends BaseActivity {
	private Button register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		setListener();
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		register = (Button) findViewById(R.id.register);
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
			}
		});
	}
}
