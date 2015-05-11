package cn.edu.bjtu.foodie_android.fragment;

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

public class HomeFragment extends Fragment {
	private ListView lv_home_fragment;

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
		MyRestrantAdapter adapter = new MyRestrantAdapter();
		lv_home_fragment.setAdapter(adapter);
		lv_home_fragment.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

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

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	class MyRestrantAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 20;
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
			holder.tv_restrant_name.setText("餐馆名称");
			holder.tv_restrant_desc.setText("这家餐馆不错");
			return view;
		}

	}

	class ViewHolder {
		ImageView iv_restrant_icon;
		TextView tv_restrant_name;
		TextView tv_restrant_desc;
	}
}
