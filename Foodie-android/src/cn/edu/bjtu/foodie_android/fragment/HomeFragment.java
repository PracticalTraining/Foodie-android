package cn.edu.bjtu.foodie_android.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cn.edu.bjtu.foodie_android.R;
import cn.edu.bjtu.foodie_android.bean.Restaurant;
import cn.edu.bjtu.foodie_android.manager.ApplicationController;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.Gson;

public class HomeFragment extends Fragment {
	private ListView lv_home_fragment;
	private List<Restaurant> restaurants;
	private MyRestrantAdapter adapter;
	private Handler mHandler;
	private static final int STATUS_CHANGE = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.home_fragment, null);
		lv_home_fragment = (ListView) view.findViewById(R.id.lv_home_fragment);
		adapter = new MyRestrantAdapter();
		lv_home_fragment.setAdapter(adapter);

		lv_home_fragment.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				RestrantDetailInfoFragment restrantDetailInfoFragment = new RestrantDetailInfoFragment();
				Bundle bundle = new Bundle();

				bundle.putInt("restId", ApplicationController.getInstance()
						.getRestaurant_list().get(position).getId());
				bundle.putString("restName", ApplicationController
						.getInstance().getRestaurant_list().get(position)
						.getName());
				bundle.putString("restDescription", ApplicationController
						.getInstance().getRestaurant_list().get(position)
						.getDescription());
				bundle.putString("restPicUrl", ApplicationController
						.getInstance().getRestaurant_list().get(position)
						.getPictureUrl());
				restrantDetailInfoFragment.setArguments(bundle);
				getActivity()
						.getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.ll_mid_content,
								restrantDetailInfoFragment).commit();
			}
		});
		return view;
	}

	/**
	 * 获得餐馆信息
	 */

	/**
	 * 解析获取到的json字符串
	 * 
	 * @param arg0
	 * @throws JSONException
	 */
	protected ArrayList<Restaurant> parseJson(JSONObject json)
			throws JSONException {
		Gson gson = new Gson();
		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		JSONArray jsonArray = json.getJSONArray("restaurants");
		for (int i = 0; i < jsonArray.length(); i++) {
			Restaurant restaurant = gson.fromJson(jsonArray.get(i).toString(),
					Restaurant.class);
			restaurants.add(restaurant);
		}
		return restaurants;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	class MyRestrantAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return ApplicationController.getInstance().getRestaurant_list()
					.size();
		}

		@Override
		public Object getItem(int position) {
			return ApplicationController.getInstance().getRestaurant_list()
					.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view;
			ViewHolder holder;
			ImageLoader mImageLoader = ApplicationController.getInstance()
					.getImageLoader();

			if (convertView == null) {
				view = View.inflate(getActivity(), R.layout.item_home_fragment,
						null);
				holder = new ViewHolder();
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			holder.iv_restrant_icon = (NetworkImageView) view
					.findViewById(R.id.iv_restrant_icon);
			holder.tv_restrant_name = (TextView) view
					.findViewById(R.id.tv_restrant_name);
			holder.tv_restrant_desc = (TextView) view
					.findViewById(R.id.tv_restrant_desc);
			holder.iv_restrant_icon.setImageResource(R.drawable.app_icon);
			// holder.tv_restrant_name.setText("restrant name");
			// holder.tv_restrant_desc.setText("restrant desc");
			holder.iv_restrant_icon.setImageUrl(ApplicationController
					.getInstance().getRestaurant_list().get(position)
					.getPictureUrl(), mImageLoader);
			holder.tv_restrant_name.setText(ApplicationController.getInstance()
					.getRestaurant_list().get(position).getName());
			holder.tv_restrant_desc.setText(ApplicationController.getInstance()
					.getRestaurant_list().get(position).getDescription());
			return view;
		}

	}

	class ViewHolder {
		NetworkImageView iv_restrant_icon;
		TextView tv_restrant_name;
		TextView tv_restrant_desc;
	}
}
