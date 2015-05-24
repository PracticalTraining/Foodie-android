package cn.edu.bjtu.foodie_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
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
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;

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
	private LocationClient mLocClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_location);
		mMapView = (MapView) findViewById(R.id.bmapView);
		baiDuMap = mMapView.getMap();
		initView();
		Intent intent = getIntent();
		lat = intent.getDoubleExtra("lat", 0);
		lon = intent.getDoubleExtra("lon", 0);
		endName = intent.getStringExtra("name");
		endCity = intent.getStringExtra("city");
		map = mMapView.getMap();
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
		// 定位
		mLocClient = new LocationClient(getApplicationContext());
		LocationClientOption option1 = new LocationClientOption();
		option1.setLocationMode(LocationMode.Hight_Accuracy);// 设置定位模式
		option1.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option1.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option1.setIsNeedAddress(true);// 返回的定位结果包含地址信息
		option1.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
		option1.setOpenGps(true);// 打开gps
		option1.SetIgnoreCacheException(true);
		mLocClient.setLocOption(option1);
		myListener = new MyLocationListener();
		mLocClient.registerLocationListener(myListener);
		mLocClient.start();

	}

	private String addStr;
	private String city;
	private String district;
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
			district = location.getDistrict();
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
		ll_location = (LinearLayout) findViewById(R.id.ll_location);
		ll_detail = (LinearLayout) findViewById(R.id.ll_detail);
		ll_route = (LinearLayout) findViewById(R.id.ll_route);
		ll_title = (LinearLayout) findViewById(R.id.ll_title);
		btn_transit = (Button) findViewById(R.id.btn_transit);
		btn_driving = (Button) findViewById(R.id.btn_driving);
		btn_walking = (Button) findViewById(R.id.btn_walking);
		btn_search = (ImageButton) findViewById(R.id.btn_search);
		btn_start.setOnClickListener(this);
		btn_end.setOnClickListener(this);
		btn_transit.setOnClickListener(this);
		btn_driving.setOnClickListener(this);
		btn_walking.setOnClickListener(this);
		btn_search.setOnClickListener(this);
		ll_location.setOnClickListener(this);
		ll_detail.setOnClickListener(this);
		ll_route.setOnClickListener(this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
		mLocClient.unRegisterLocationListener(myListener);
		mLocClient.stop();
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

	int flag = 1;
	PopupWindow menu;
	private MyLocationListener myListener;
	private LayoutInflater inflater;
	private String endName;
	private BaiduMap map;
	private String endCity;
	private double lat;
	private double lon;

	@Override
	public void onClick(View v) {
		View layout;
		TextView tv_latAndlong;
		TextView tv_accurce;
		TextView tv_time;
		TextView tv_code;
		TextView tv_addess;
		TextView tv_distruct;

		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_start:
			Toast.makeText(NearRestrantActivity.this, "点击兴趣点，选择提点", 0).show();
			dismissPopupWindow();
			baiDuMap.setOnMapClickListener(new OnMapClickListener() {

				@Override
				public boolean onMapPoiClick(MapPoi poi) {
					// TODO Auto-generated method stub
					String startName = poi.getName();
					Toast.makeText(NearRestrantActivity.this, startName, 0)
							.show();
					ed_start.setText(startName);
					return true;
				}

				@Override
				public void onMapClick(LatLng arg0) {
					// TODO Auto-generated method stub

				}
			});
			break;
		case R.id.btn_end:
			Toast.makeText(NearRestrantActivity.this, "点击兴趣点，选择终点", 0).show();
			dismissPopupWindow();
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
			dismissPopupWindow();
			break;
		case R.id.ll_detail:
			ll_title.setVisibility(View.GONE);
			// 获取LayoutInflater实例
			inflater = (LayoutInflater) NearRestrantActivity.this
					.getSystemService(NearRestrantActivity.this.LAYOUT_INFLATER_SERVICE);
			// 获取弹出菜单的布局
			layout = inflater.inflate(R.layout.pop, null);
			tv_latAndlong = (TextView) layout.findViewById(R.id.tv_latAndlong);
			tv_accurce = (TextView) layout.findViewById(R.id.tv_accurce);
			tv_time = (TextView) layout.findViewById(R.id.tv_time);
			tv_code = (TextView) layout.findViewById(R.id.tv_code);
			tv_addess = (TextView) layout.findViewById(R.id.tv_addess);
			tv_distruct = (TextView) layout.findViewById(R.id.tv_distruct);
			menu = new PopupWindow(layout, 600, LayoutParams.WRAP_CONTENT, true);
			menu.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.list_activated_holo));
			menu.showAtLocation(findViewById(R.id.map_fragment),
					Gravity.CENTER, 0, 0);
			mLocClient.registerLocationListener(myListener);
			mLocClient.start();
			// 给pop设置数据
			tv_accurce.setText("精确度:" + radius);
			tv_latAndlong.setText("经纬度:" + longAndLan);
			tv_addess.setText("详细地址" + addStr);
			tv_code.setText("城市编码:" + code);
			tv_distruct.setText("区:" + district);
			tv_time.setText("定位时间:" + time);
			mLocClient.stop();
			break;
		case R.id.ll_route:
			dismissPopupWindow();
			ll_title.setVisibility(View.VISIBLE);
			ed_start.setText(addStr);
			ed_end.setText(endName);
			break;
		case R.id.btn_transit:
			dismissPopupWindow();
			btn_transit.setBackgroundResource(R.drawable.mode_transit_on);
			btn_driving.setBackgroundResource(R.drawable.mode_driving_off);
			btn_walking.setBackgroundResource(R.drawable.mode_walk_off);
			flag = 1;
			break;
		case R.id.btn_driving:
			dismissPopupWindow();
			btn_transit.setBackgroundResource(R.drawable.mode_transit_off);
			btn_driving.setBackgroundResource(R.drawable.mode_driving_on);
			btn_walking.setBackgroundResource(R.drawable.mode_walk_off);
			flag = 2;
			break;
		case R.id.btn_walking:
			dismissPopupWindow();
			btn_transit.setBackgroundResource(R.drawable.mode_transit_off);
			btn_driving.setBackgroundResource(R.drawable.mode_driving_off);
			btn_walking.setBackgroundResource(R.drawable.mode_walk_on);
			flag = 3;
			break;
		case R.id.btn_search:
			dismissPopupWindow();
			String start = ed_start.getText().toString().trim();
			String end = ed_end.getText().toString().trim();
			if (TextUtils.isEmpty(start) || TextUtils.isEmpty(end)) {
				Toast.makeText(NearRestrantActivity.this, "请输入起点或者终点", 0)
						.show();
			}
			if (flag == 1) {
				RoutePlanSearch mSearch = RoutePlanSearch.newInstance();
				OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
					public void onGetWalkingRouteResult(
							WalkingRouteResult result) {
					}

					public void onGetTransitRouteResult(
							TransitRouteResult result) {
						if (result == null
								|| result.error != SearchResult.ERRORNO.NO_ERROR) {
							Toast.makeText(NearRestrantActivity.this,
									"抱歉，未找到结果", Toast.LENGTH_SHORT).show();
						}
						if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
							return;
						}
						if (result.error == SearchResult.ERRORNO.NO_ERROR) {
							TransitRouteOverlay overlay = new TransitRouteOverlay(
									map);
							map.setOnMarkerClickListener(overlay);
							overlay.setData(result.getRouteLines().get(0));
							overlay.addToMap();
							overlay.zoomToSpan();
						}
					}

					public void onGetDrivingRouteResult(
							DrivingRouteResult result) {
					}
				};
				mSearch.setOnGetRoutePlanResultListener(listener);
				String add = ed_start.getText().toString().trim();
				PlanNode startPoint = PlanNode.withCityNameAndPlaceName(city,
						add);
				// PlanNode endPoint = PlanNode.withCityNameAndPlaceName(city,
				// endName);
				PlanNode endPoint = PlanNode.withLocation(new LatLng(lat, lon));
				mSearch.transitSearch((new TransitRoutePlanOption())
						.from(startPoint).city(city).to(endPoint));
			} else if (flag == 2) {
				RoutePlanSearch mSearch = RoutePlanSearch.newInstance();
				OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
					public void onGetWalkingRouteResult(
							WalkingRouteResult result) {
						// 获取步行线路规划结果
					}

					public void onGetTransitRouteResult(
							TransitRouteResult result) {
						// 获取公交换乘路径规划结果
					}

					public void onGetDrivingRouteResult(
							DrivingRouteResult result) {
						// 获取驾车线路规划结果
						if (result == null
								|| result.error != SearchResult.ERRORNO.NO_ERROR) {
							Toast.makeText(NearRestrantActivity.this,
									"抱歉，未找到结果", Toast.LENGTH_SHORT).show();
						}
						if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
							return;
						}
						if (result.error == SearchResult.ERRORNO.NO_ERROR) {
							DrivingRouteOverlay overlay = new DrivingRouteOverlay(
									map);
							map.setOnMarkerClickListener(overlay);
							overlay.setData(result.getRouteLines().get(0));
							overlay.addToMap();
							overlay.zoomToSpan();
						}
					}
				};
				mSearch.setOnGetRoutePlanResultListener(listener);
				String add = ed_start.getText().toString();
				PlanNode stNode = PlanNode.withCityNameAndPlaceName(city, add);
				// PlanNode enNode = PlanNode.withCityNameAndPlaceName(endCity,
				// endName);
				PlanNode enNode = PlanNode.withLocation(new LatLng(lat, lon));
				mSearch.drivingSearch((new DrivingRoutePlanOption()).from(
						stNode).to(enNode));

			} else if (flag == 3) {
				RoutePlanSearch search = RoutePlanSearch.newInstance();
				OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {

					@Override
					public void onGetWalkingRouteResult(
							WalkingRouteResult result) {
						if (result == null
								|| result.error != SearchResult.ERRORNO.NO_ERROR) {
							Toast.makeText(NearRestrantActivity.this,
									"抱歉，未找到结果", Toast.LENGTH_SHORT).show();
						}
						if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
							return;
						}
						if (result.error == SearchResult.ERRORNO.NO_ERROR) {
							WalkingRouteOverlay overlay = new WalkingRouteOverlay(
									map);
							map.setOnMarkerClickListener(overlay);
							overlay.setData(result.getRouteLines().get(0));
							overlay.addToMap();
							overlay.zoomToSpan();
						}
					}

					@Override
					public void onGetTransitRouteResult(TransitRouteResult arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onGetDrivingRouteResult(DrivingRouteResult arg0) {
						// TODO Auto-generated method stub

					}
				};
				search.setOnGetRoutePlanResultListener(listener);
				Toast.makeText(getApplicationContext(), city, 0).show();
				String add = ed_start.getText().toString();
				PlanNode startPoint = PlanNode.withCityNameAndPlaceName(city,
						add);
				// PlanNode endPoint = PlanNode.withCityNameAndPlaceName(city,
				// endName);
				PlanNode endPoint = PlanNode.withLocation(new LatLng(lat, lon));
				WalkingRoutePlanOption walkingOption = new WalkingRoutePlanOption();
				walkingOption.from(startPoint);
				walkingOption.to(endPoint);
				search.walkingSearch(walkingOption);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 关闭popupwindow
	 */
	private void dismissPopupWindow() {
		if (menu != null && menu.isShowing()) {
			menu.dismiss();
			menu = null;
		}
	}

}
