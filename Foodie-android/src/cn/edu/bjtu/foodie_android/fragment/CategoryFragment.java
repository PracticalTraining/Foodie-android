package cn.edu.bjtu.foodie_android.fragment;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.edu.bjtu.foodie_android.NearRestrantActivity;
import cn.edu.bjtu.foodie_android.R;
import cn.edu.bjtu.foodie_android.global.GlobalParams;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;

public class CategoryFragment extends Fragment {
	String locationStr;
	private List<PoiInfo> allPoi;
	private ListView lv_near_restrant;
	private LinearLayout ll_loading;
	private final int SCUESS = 1;
	private final int FAILED = 2;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SCUESS:
				ll_loading.setVisibility(View.GONE);
				List<PoiInfo> allPoi = (List<PoiInfo>) msg.obj;
				RestrantAdpter adpter = new RestrantAdpter(allPoi);
				lv_near_restrant.setAdapter(adpter);
				break;
			case FAILED:
				Toast.makeText(getActivity(), "抱歉，未找到结果", Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}
		};
	};
	private EditText ed_key;
	private EditText ed_near;
	private Button btn_search;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.category_fragment,
				null);
		lv_near_restrant = (ListView) view.findViewById(R.id.lv_near_restrant);
		ed_key = (EditText) view.findViewById(R.id.ed_key);
		btn_search = (Button) view.findViewById(R.id.btn_search);
		ll_loading = (LinearLayout) view.findViewById(R.id.ll_loading);
		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final String keyName = ed_key.getText().toString().trim();
				if (keyName == null) {
					Toast.makeText(getActivity(), "关键字或者范围不能为空", 0).show();
				} else {
					ll_loading.setVisibility(View.VISIBLE);
					final PoiSearch poiSarch = PoiSearch.newInstance();
					new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
								private Message message;

								@Override
								public void onGetPoiResult(PoiResult result) {
									if (result.error == SearchResult.ERRORNO.NO_ERROR) {
										allPoi = result.getAllPoi();
										message = Message.obtain();
										message.obj = allPoi;
										message.what = SCUESS;
										handler.sendMessage(message);
									} else if (result == null
											|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
										message.what = FAILED;
										handler.sendMessage(message);
									}
								}

								@Override
								public void onGetPoiDetailResult(
										PoiDetailResult result) {
								}
							};
							PoiNearbySearchOption option = new PoiNearbySearchOption();
							option.keyword(keyName);
							LatLng loc = new LatLng(GlobalParams.LAT,
									GlobalParams.LONG);
							option.location(loc);
							option.radius(2000);
							poiSarch.searchNearby(option);
							poiSarch.setOnGetPoiSearchResultListener(listener);
						}
					}).start();
				}
			}
		});
		lv_near_restrant.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				PoiInfo poiInfo = allPoi.get(position);
				double lat = poiInfo.location.latitude;
				double lon = poiInfo.location.longitude;
				Intent intent = new Intent(getActivity(),
						NearRestrantActivity.class);
				intent.putExtra("lat", lat);
				intent.putExtra("lon", lon);
				intent.putExtra("name", poiInfo.name);
				startActivity(intent);
			}
		});
		return view;
	}

	private void search(final String key) {

	}

	class RestrantAdpter extends BaseAdapter {
		private List<PoiInfo> allPoi;

		public RestrantAdpter(List<PoiInfo> allPoi) {
			super();
			this.allPoi = allPoi;
		}

		@Override
		public int getCount() {
			return allPoi.size();
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
				convertView = View.inflate(getActivity(),
						R.layout.item_new_restrant, null);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			viewHolder.tv_restrant_name = (TextView) convertView
					.findViewById(R.id.tv_restrant_name);
			viewHolder.tv_restrant_address = (TextView) convertView
					.findViewById(R.id.tv_restrant_address);
			viewHolder.iv_restrant_icon = (ImageView) convertView
					.findViewById(R.id.iv_restrant_icon);
			viewHolder.tv_restrant_name.setText(allPoi.get(position).name);
			viewHolder.tv_restrant_address
					.setText(allPoi.get(position).address);
			return convertView;
		}
	}

	class ViewHolder {
		ImageView iv_restrant_icon;
		TextView tv_restrant_name;
		TextView tv_restrant_address;
	}
}
