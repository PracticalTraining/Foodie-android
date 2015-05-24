package cn.edu.bjtu.foodie_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import cn.edu.bjtu.foodie_android.global.GlobalParams;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

public class NearRestrantActivity extends Activity implements OnClickListener {
	private MapView mMapView;
	private EditText ed_start;
	private EditText ed_end;
	private Button btn_end;
	private Button btn_start;
	private LinearLayout ll_title;
	private LinearLayout ll_location;
	private LinearLayout ll_detail;
	private LinearLayout ll_route;
	private Button btn_transit;
	private Button btn_driving;
	private Button btn_walking;
	private ImageButton btn_search;
	private BaiduMap baiDuMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_location);
		mMapView = (MapView) findViewById(R.id.bmapView);
		baiDuMap = mMapView.getMap();
		Intent intent = getIntent();
		double lat = intent.getDoubleExtra("lat", 0);
		double lon = intent.getDoubleExtra("lon", 0);
		String name = intent.getStringExtra("name");
		BaiduMap map = mMapView.getMap();
		LatLng cenpt = new LatLng(lat, lon);
		// 定义地图状态
		MapStatus mMapStatus = new MapStatus.Builder().target(cenpt).zoom(19)
				.build();
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);
		// 改变地图状态
		map.setMapStatus(mMapStatusUpdate);
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.eat_icon);
		// 构建MarkerOption，用于在地图上添加Marker
		OverlayOptions option = new MarkerOptions().position(cenpt)
				.icon(bitmap);
		// 在地图上添加Marker，并显示
		map.addOverlay(option);
		map.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public boolean onMapPoiClick(MapPoi result) {
				// TODO Auto-generated method stub
				Toast.makeText(NearRestrantActivity.this, result.getName(), 0)
						.show();
				return false;
			}

			@Override
			public void onMapClick(LatLng arg0) {
				// TODO Auto-generated method stub

			}
		});
		initView();
	}

	private String addStr;
	private String city;
	private int stteNum;
	private String province;
	private String num;
	private String time;
	private String longAndLan;
	private float radius;
	private String code;

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if (location == null) {
				Toast.makeText(NearRestrantActivity.this, "定位失败", 0).show();
				return;
			}
			addStr = location.getAddrStr();
			city = location.getCity();
			String district = location.getDistrict();
			stteNum = location.getSatelliteNumber();
			province = location.getProvince();
			num = location.getStreetNumber();
			time = location.getTime();
			longAndLan = "(" + location.getLongitude() + ","
					+ location.getLatitude() + ")";
			radius = location.getRadius();
			code = location.getCityCode();
		}
	}

	private void initView() {
		ed_start = (EditText) findViewById(R.id.ed_start);
		ed_end = (EditText) findViewById(R.id.ed_end);
		btn_end = (Button) findViewById(R.id.btn_end);
		btn_start = (Button) findViewById(R.id.btn_start);
		ll_title = (LinearLayout) findViewById(R.id.ll_title);
		ll_location = (LinearLayout) findViewById(R.id.ll_location);
		ll_detail = (LinearLayout) findViewById(R.id.ll_detail);
		ll_route = (LinearLayout) findViewById(R.id.ll_route);
		btn_transit = (Button) findViewById(R.id.btn_transit);
		btn_driving = (Button) findViewById(R.id.btn_driving);
		btn_walking = (Button) findViewById(R.id.btn_walking);
		btn_search = (ImageButton) findViewById(R.id.btn_search);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

	/**
	 * 关闭popupwindow
	 */
	// private void dismissPopupWindow() {
	// if (menu != null && menu.isShowing()) {
	// menu.dismiss();
	// menu = null;
	// }
	// }

	@Override
	public void onClick(View v) {
		LayoutInflater inflater;
		View layout;
		int flag;
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_start:
			Toast.makeText(NearRestrantActivity.this, "选择提点", 0).show();
			// dismissPopupWindow();
			baiDuMap.setOnMapClickListener(new OnMapClickListener() {

				@Override
				public boolean onMapPoiClick(MapPoi poi) {
					// TODO Auto-generated method stub
					String name = poi.getName();
					Toast.makeText(NearRestrantActivity.this, name, 0).show();
					ed_start.setText(name);
					return true;
				}

				@Override
				public void onMapClick(LatLng arg0) {
				}
			});
			break;
		case R.id.btn_end:
			Toast.makeText(NearRestrantActivity.this, "选择终点", 0).show();
			// dismissPopupWindow();
			baiDuMap.setOnMapClickListener(new OnMapClickListener() {

				@Override
				public boolean onMapPoiClick(MapPoi poi) {
					// TODO Auto-generated method stub
					String name = poi.getName();
					Toast.makeText(NearRestrantActivity.this, name, 0).show();
					ed_end.setText(name);
					return true;
				}

				@Override
				public void onMapClick(LatLng arg0) {
					// TODO Auto-generated method stub

				}
			});
			break;
		case R.id.ll_location:
			ll_title.setVisibility(View.GONE);
			Toast.makeText(NearRestrantActivity.this, addStr, 0).show();
			GlobalParams.LOCATIONCLIENT.stop();
			// dismissPopupWindow();
			break;
		// case R.id.ll_detail:
		// ll_title.setVisibility(View.GONE);
		// // 获取LayoutInflater实例
		// inflater = (LayoutInflater) NearRestrantActivity.this
		// .getSystemService(NearRestrantActivity.this.LAYOUT_INFLATER_SERVICE);
		// // 获取弹出菜单的布局
		// layout = inflater.inflate(R.layout.pop, null);
		// tv_latAndlong = (TextView)
		// layout.findViewById(R.id.tv_latAndlong);
		// tv_accurce = (TextView) layout.findViewById(R.id.tv_accurce);
		// tv_time = (TextView) layout.findViewById(R.id.tv_time);
		// tv_code = (TextView) layout.findViewById(R.id.tv_code);
		// tv_addess = (TextView) layout.findViewById(R.id.tv_addess);
		// tv_distruct = (TextView) layout.findViewById(R.id.tv_distruct);
		// menu = new PopupWindow(layout, 600, LayoutParams.WRAP_CONTENT,
		// true);
		// menu.setBackgroundDrawable(getResources().getDrawable(
		// R.drawable.list_activated_holo));
		// menu.showAtLocation(view.findViewById(R.id.map_fragment),
		// Gravity.CENTER, 0, 0);
		// GlobalParam.LOCATIONCLIENT.start();
		// // 给pop设置数据
		// tv_accurce.setText("精确度:" + radius);
		// tv_latAndlong.setText("经纬度:" + longAndLan);
		// tv_addess.setText("详细地址" + addStr);
		// tv_code.setText("城市编码:" + code);
		// tv_distruct.setText("区:" + district);
		// tv_time.setText("定位时间:" + time);
		// GlobalParam.LOCATIONCLIENT.stop();
		// break;
		// case R.id.ll_route:
		// dismissPopupWindow();
		// ll_title.setVisibility(View.VISIBLE);
		// break;
		case R.id.btn_transit:
			// dismissPopupWindow();
			btn_transit.setBackgroundResource(R.drawable.mode_transit_on);
			btn_driving.setBackgroundResource(R.drawable.mode_driving_off);
			btn_walking.setBackgroundResource(R.drawable.mode_walk_off);
			flag = 1;
			break;
		case R.id.btn_driving:
			// dismissPopupWindow();
			btn_transit.setBackgroundResource(R.drawable.mode_transit_off);
			btn_driving.setBackgroundResource(R.drawable.mode_driving_on);
			btn_walking.setBackgroundResource(R.drawable.mode_walk_off);
			flag = 2;
			break;
		case R.id.btn_walking:
			// dismissPopupWindow();
			btn_transit.setBackgroundResource(R.drawable.mode_transit_off);
			btn_driving.setBackgroundResource(R.drawable.mode_driving_off);
			btn_walking.setBackgroundResource(R.drawable.mode_walk_on);
			flag = 3;
			break;
		// case R.id.btn_search:
		// // dismissPopupWindow();
		// String start = ed_start.getText().toString().trim();
		// String end = ed_end.getText().toString().trim();
		// if (TextUtils.isEmpty(start) || TextUtils.isEmpty(end)) {
		// Toast.makeText(NearRestrantActivity.this, "请输入起点或者终点", 0)
		// .show();
		// }
		// if (flag == 1) {
		// RoutePlanSearch search = RoutePlanSearch.newInstance();
		// OnGetRoutePlanResultListener listener = new
		// OnGetRoutePlanResultListener() {
		//
		// @Override
		// public void onGetWalkingRouteResult(WalkingRouteResult arg0) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void onGetTransitRouteResult(
		// TransitRouteResult result) {
		// // TODO Auto-generated method stub
		// if (result == null
		// || result.error != SearchResult.ERRORNO.NO_ERROR) {
		// Toast.makeText(NearRestrantActivity.this,
		// "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
		// }
		// if (result.error == SearchResult.ERRORNO.NO_ERROR) {
		// TransitRouteOverlay overlay = new TransitRouteOverlay(
		// baiDuMap);
		// baiDuMap.setOnMarkerClickListener(overlay);
		// overlay.setData(result.getRouteLines().get(0));
		// overlay.addToMap();
		// overlay.zoomToSpan();
		// }
		// }
		//
		// @Override
		// public void onGetDrivingRouteResult(DrivingRouteResult arg0) {
		// // TODO Auto-generated method stub
		//
		// }
		// };
		// PlanNode stNode = PlanNode
		// .withCityNameAndPlaceName(city, start);
		// PlanNode enNode = PlanNode.withCityNameAndPlaceName(city, end);
		// search.transitSearch((new TransitRoutePlanOption())
		// .from(stNode).city(city).to(enNode));
		// search.setOnGetRoutePlanResultListener(listener);
		// } else if (flag == 2) {
		// RoutePlanSearch mSearch = RoutePlanSearch.newInstance();
		// OnGetRoutePlanResultListener listener = new
		// OnGetRoutePlanResultListener() {
		// public void onGetWalkingRouteResult(
		// WalkingRouteResult result) {
		// // 获取步行线路规划结果
		// }
		//
		// public void onGetTransitRouteResult(
		// TransitRouteResult result) {
		// // 获取公交换乘路径规划结果
		// }
		//
		// public void onGetDrivingRouteResult(
		// DrivingRouteResult result) {
		// // 获取驾车线路规划结果
		// if (result == null
		// || result.error != SearchResult.ERRORNO.NO_ERROR) {
		// Toast.makeText(NearRestrantActivity.this,
		// "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
		// }
		// if (result.error == SearchResult.ERRORNO.NO_ERROR) {
		// DrivingRouteOverlay overlay = new DrivingRouteOverlay(
		// baiDuMap);
		// baiDuMap.setOnMarkerClickListener(overlay);
		// overlay.setData(result.getRouteLines().get(0));
		// overlay.addToMap();
		// overlay.zoomToSpan();
		// }
		// }
		// };
		// mSearch.setOnGetRoutePlanResultListener(listener);
		// PlanNode stNode = PlanNode
		// .withCityNameAndPlaceName("北京", "西直门");
		// PlanNode enNode = PlanNode
		// .withCityNameAndPlaceName("北京", "西二旗");
		// mSearch.drivingSearch((new DrivingRoutePlanOption()).from(
		// stNode).to(enNode));
		// }
		// // } else if (flag == 3) {
		// // Toast.makeText(NearRestrantActivity.this, "步行", 0).show();
		// // }
		// break;
		// default:
		// break;
		}
	}
}
