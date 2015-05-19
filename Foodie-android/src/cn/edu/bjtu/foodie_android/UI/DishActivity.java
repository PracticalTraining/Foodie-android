package cn.edu.bjtu.foodie_android.UI;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.bjtu.foodie_android.R;
import cn.edu.bjtu.foodie_android.bean.Dish;

public class DishActivity extends Activity {
	private ArrayList<Dish> list;
	private ListView lv_dish;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_dish);
		lv_dish = (ListView) findViewById(R.id.lv_dish);
		initData();
		DishAdapter adapter = new DishAdapter();
		lv_dish.setAdapter(adapter);
	}

	private void initData() {
		list = new ArrayList<Dish>();
		Dish dish;
		for (int i = 0; i < 40; i++) {
			if (i % 2 == 0) {
				dish = new Dish(Dish.TYPE_NOCHECKED, i + "号位", i);
				list.add(dish);
			} else {
				dish = new Dish(Dish.TYPE_CHECKED, i + "号位", i);
				list.add(dish);
			}
		}
	}

	class DishAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
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
