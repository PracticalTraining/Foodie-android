package cn.edu.bjtu.foodie_android.UI;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.bjtu.foodie_android.R;
import cn.edu.bjtu.foodie_android.utils.EncodingHandler;

import com.google.zxing.WriterException;

public class BlueToothActivity extends Activity {

	private ImageView iv_qr_image;
	private TextView tv_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.blue_touth);
		Intent intent = getIntent();
		int money = intent.getIntExtra("totalMoney", 0);
		String selectDishStr = intent.getStringExtra("select");
		iv_qr_image = (ImageView) findViewById(R.id.iv_qr_image);
		tv_content = (TextView) findViewById(R.id.tv_content);
		tv_content.setText("总金额：" + money + "所选菜单：" + selectDishStr);
		if (money != 0) {
			Bitmap qrCodeBitmap;
			try {
				qrCodeBitmap = EncodingHandler.createQRCode(money + ""
						+ selectDishStr, 350);
				iv_qr_image.setImageBitmap(qrCodeBitmap);
			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Toast.makeText(BlueToothActivity.this, "Text can not be empty",
					Toast.LENGTH_SHORT).show();
		}
	}
}
