package cn.edu.bjtu.foodie_android.UI;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.bjtu.foodie_android.R;
import cn.edu.bjtu.foodie_android.bean.Dish;
import cn.edu.bjtu.foodie_android.manager.ApplicationController;

@SuppressLint("NewApi")
public class DishActivity extends Activity {
	private List<Dish> list;
	private ListView lv_dish;
	int count = 0;
	private List<Dish> restDishList;
	private ArrayList<Dish> selectDishList = new ArrayList<Dish>();
	private DishAdapter adapter;
	private int totalMoney = 0;
	private TextView tv_money;
	private Button btn_commit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dish);
		lv_dish = (ListView) findViewById(R.id.lv_dish);
		tv_money = (TextView) findViewById(R.id.tv_money);
		btn_commit = (Button) findViewById(R.id.btn_commit);
		adapter = new DishAdapter();
		initData();

		lv_dish.setAdapter(adapter);
		btn_commit.setOnClickListener(new OnClickListener() {

			private NfcAdapter mNfcAdapter;
			StringBuffer sb = new StringBuffer();

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(DishActivity.this,
						BlueToothActivity.class);
				intent.putExtra("totalMoney", totalMoney);

				for (Dish dish : selectDishList) {
					sb.append(dish.toString());
				}
				intent.putExtra("select", sb.toString());
				startActivity(intent);
			}
		});
	}

	private void initData() {
		Intent intent = getIntent();
		list = new ArrayList<Dish>();
		restDishList = new ArrayList<Dish>();
		restDishList = ApplicationController.getInstance().getDishByRestId(
				intent.getIntExtra("restId", 0));
		Dish dish;
		for (int i = 0; i < restDishList.size(); i++) {
			dish = new Dish(Dish.TYPE_NOCHECKED, restDishList.get(i).getName(),
					restDishList.get(i).getPrice(), restDishList.get(i)
							.getRestId());
			list.add(dish);
		}
	}

	class DishAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup arg2) {
			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = View.inflate(DishActivity.this,
						R.layout.item_dish, null);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.tv_dish_name = (TextView) convertView
					.findViewById(R.id.tv_dish_name);
			viewHolder.tv_dish_price = (TextView) convertView
					.findViewById(R.id.tv_dish_price);
			viewHolder.checkBox = (CheckBox) convertView
					.findViewById(R.id.checkBox);
			viewHolder.checkBox
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked) {
								list.get(position).setType(Dish.TYPE_CHECKED);
							} else {
								list.get(position).setType(Dish.TYPE_NOCHECKED);
							}
							Dish dish = list.get(position);
							totalMoney += dish.getPrice();
							selectDishList.add(dish);
							tv_money.setText(totalMoney + "");
						}
					});
			viewHolder.tv_dish_name.setText(list.get(position).getName());
			viewHolder.tv_dish_price
					.setText(list.get(position).getPrice() + "");
			if (list.get(position).getType() == Dish.TYPE_CHECKED) {
				viewHolder.checkBox.setChecked(true);
			} else {
				viewHolder.checkBox.setChecked(false);
			}
			return convertView;
		}
	}

	class ViewHolder {
		TextView tv_dish_name;
		TextView tv_dish_price;
		CheckBox checkBox;
	}

}
