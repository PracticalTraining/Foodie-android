package cn.edu.bjtu.foodie_android.UI;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import cn.edu.bjtu.foodie_android.manager.AppManager;
import cn.edu.bjtu.foodie_android.manager.PromptManager;

public abstract class BaseActivity extends Activity {

	public static final String TAG = BaseActivity.class.getSimpleName();
	protected InputMethodManager imm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AppManager.getInstance().addActivity(this);
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	/**
	 * 初始化控件
	 */
	protected abstract void initView();

	/**
	 * 设置监听
	 */
	protected abstract void setListener();

	/**
	 * 是否显示软键盘
	 * 
	 * @param isShowSoft
	 * @param editText
	 */
	protected void hideOrShowSoftInput(boolean isShowSoft, EditText editText) {
		if (isShowSoft) {
			imm.showSoftInput(editText, 0);
		} else {
			imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
		}
	}

	/**
	 * 获得当前程序版本信息
	 * 
	 * @return
	 * @throws Exception
	 */
	protected String getVersionName() throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
				0);
		return packInfo.versionName;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		AppManager appManager = AppManager.getInstance();
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (appManager.mActivityStack.size() <= 1) {
				PromptManager.showExitSystem(BaseActivity.this);
			} else {
				appManager.killTopActivity();
			}
			break;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

}
