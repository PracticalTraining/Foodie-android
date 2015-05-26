package cn.edu.bjtu.foodie_android.fragment;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import android.content.Intent;
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
import cn.edu.bjtu.foodie_android.R;
import cn.edu.bjtu.foodie_android.UI.DishActivity;
import cn.edu.bjtu.foodie_android.manager.ApplicationController;

public class OrderFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.search_fragment, null);
		ListView lv_home_fragment = (ListView) view
				.findViewById(R.id.lv_retrant_fragment);
		MyRestrantAdapter adapter = new MyRestrantAdapter();
		lv_home_fragment.setAdapter(adapter);
		lv_home_fragment.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(), DishActivity.class);
				intent.putExtra("restId", ApplicationController.getInstance().getRestaurant_list().get(position).getId());
				intent.putExtra("restName", ApplicationController.getInstance().getRestaurant_list().get(position).getName());
				intent.putExtra("restDescription", ApplicationController.getInstance().getRestaurant_list().get(position).getDescription());
				startActivity(intent);
			}
		});
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	class MyRestrantAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return ApplicationController.getInstance().getRestaurant_list().size();
		}

		@Override
		public Object getItem(int position) {
			return ApplicationController.getInstance().getRestaurant_list().get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View 				view;
			ViewHolder 			holder;
			ImageLoader			mImageLoader = ApplicationController.getInstance().getImageLoader();
			
			if (convertView == null) {
				view = View
						.inflate(getActivity(), R.layout.item_restrant, null);
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
			holder.iv_restrant_icon.setImageUrl(ApplicationController.getInstance().getRestaurant_list().get(position).getPictureUrl() 
												,mImageLoader);
			holder.tv_restrant_name.setText(ApplicationController.getInstance().getRestaurant_list().get(position).getName());
			holder.tv_restrant_desc.setText("order dishes");
			return view;
		}

	}

	class ViewHolder {
		NetworkImageView iv_restrant_icon;
		TextView tv_restrant_name;
		TextView tv_restrant_desc;
	}
}
