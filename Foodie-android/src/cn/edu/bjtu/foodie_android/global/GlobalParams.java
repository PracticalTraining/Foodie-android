package cn.edu.bjtu.foodie_android.global;

import java.util.ArrayList;

import cn.edu.bjtu.foodie_android.bean.BlueToothInfo;

import com.baidu.location.LocationClient;

public class GlobalParams {
	public static LocationClient LOCATIONCLIENT;
	/**
	 * 存放当前的位置
	 */
	public static String LOCATION;
	public static double LAT;
	public static double LONG;
	public static ArrayList<BlueToothInfo> blueInfos = new ArrayList<BlueToothInfo>();
}
