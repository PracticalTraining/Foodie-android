package cn.edu.bjtu.foodie_android.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.bjtu.foodie_android.R;
import cn.edu.bjtu.foodie_android.bean.Restaurant;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class HomeFragment extends Fragment {
	private ListView lv_home_fragment;
	private ArrayList<Restaurant> restaurants;
	private MyRestrantAdapter adapter;

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
		initData();

		lv_home_fragment.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				RestrantDetailInfoFragment restrantDetailInfoFragment = new RestrantDetailInfoFragment();
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
	private void initData() {
		// TODO Auto-generated method stub
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET,
				"http://123.57.36.82/foodie/ws/restaurant/searchall", null,
				new Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject arg0) {
						try {
							restaurants = parseJson(arg0);
							adapter = new MyRestrantAdapter(restaurants);
							lv_home_fragment.setAdapter(adapter);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						// TODO Auto-generated method stub
						Toast.makeText(getActivity(), "shibai", 0).show();
					}
				});
		requestQueue.add(jsonObjectRequest);
	}

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
		private ArrayList<Restaurant> restaurants;

		public MyRestrantAdapter(ArrayList<Restaurant> restaurants) {
			super();
			this.restaurants = restaurants;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return restaurants.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view;
			ViewHolder holder;
			if (convertView == null) {
				view = View.inflate(getActivity(), R.layout.item_home_fragment,
						null);
				holder = new ViewHolder();
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			holder.iv_restrant_icon = (ImageView) view
					.findViewById(R.id.iv_restrant_icon);
			holder.tv_restrant_name = (TextView) view
					.findViewById(R.id.tv_restrant_name);
			holder.tv_restrant_desc = (TextView) view
					.findViewById(R.id.tv_restrant_desc);
			holder.iv_restrant_icon.setImageResource(R.drawable.app_icon);
			// holder.tv_restrant_name.setText("restrant name");
			// holder.tv_restrant_desc.setText("restrant desc");
			Restaurant restaurant = restaurants.get(position);
			holder.tv_restrant_name.setText(restaurant.getName());
			holder.tv_restrant_desc.setText(restaurant.getDescription());
			return view;
		}

	}

	class ViewHolder {
		ImageView iv_restrant_icon;
		TextView tv_restrant_name;
		TextView tv_restrant_desc;
	}
}
