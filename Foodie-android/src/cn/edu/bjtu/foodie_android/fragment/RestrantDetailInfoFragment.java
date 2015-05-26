package cn.edu.bjtu.foodie_android.fragment;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import cn.edu.bjtu.foodie_android.LocationActivity;
import cn.edu.bjtu.foodie_android.R;
import cn.edu.bjtu.foodie_android.UI.LoginActivity;
import cn.edu.bjtu.foodie_android.UI.MyAccountActivity;
import cn.edu.bjtu.foodie_android.manager.ApplicationController;
import cn.edu.bjtu.foodie_android.utils.CustomRequest;

public class RestrantDetailInfoFragment extends Fragment {

	private View view;
	private Button 	location;
	private Button 	order;
	public	String 	status;
	public	int		queueNum;
    SharedPreferences settings;
    int				restId;
    Button 			btnCalendar;
    Button			btnTimePicker;
    EditText 		txtDate;
    EditText		txtTime;
    String			full_date;
 
    // Variable for storing current date and time
    private int mYear, mMonth, mDay, mHour, mMinute;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.settings	=	getActivity().getSharedPreferences("LoginPrefFile", 0);
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = View.inflate(getActivity(), R.layout.restrant_detail_info, null);
		order = (Button) view.findViewById(R.id.order);
		location = (Button) view.findViewById(R.id.location);
		btnCalendar = (Button) view.findViewById(R.id.btnCalendar);
        btnTimePicker = (Button) view.findViewById(R.id.btnTimePicker);
        txtDate = (EditText) view.findViewById(R.id.txtDate);
        txtTime = (EditText) view.findViewById(R.id.txtTime);
        TextView	tRestName			=	(TextView) view.findViewById(R.id.restaurantName);
	    TextView	tRestDescription	=	(TextView) view.findViewById(R.id.restaurantDescription);
		
        Bundle bundle = this.getArguments();
		if (bundle != null) {
		    restId = bundle.getInt("restId", 0);
			Log.d("RestaurantText:", bundle.getString("restName") + "+" + bundle.getString("restDescription"));
		    tRestName.setText(bundle.getString("restName"));
		    tRestDescription.setText(bundle.getString("restDescription"));
		    
		}
		location.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						LocationActivity.class);
				startActivity(intent);
			}
		});
		order.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("SharedPref:", settings.getAll().toString());
				boolean		login	=	settings.getBoolean("login", false);
				
				if (login == true)
					bookRequest(settings.getString("foodieId", "0"), String.valueOf(restId), full_date);
				if (login == false)
                   	Toast.makeText(getActivity(), getText(R.string.no_user), Toast.LENGTH_SHORT).show();
			}
		});
		 btnCalendar.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// Process to get Current Date
		            final Calendar c = Calendar.getInstance();
		            mYear = c.get(Calendar.YEAR);
		            mMonth = c.get(Calendar.MONTH);
		            mDay = c.get(Calendar.DAY_OF_MONTH);
		 
		            // Launch Date Picker Dialog
		            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
		                    new DatePickerDialog.OnDateSetListener() {
		 
		                        @Override
		                        public void onDateSet(DatePicker view, int year,
		                                int monthOfYear, int dayOfMonth) {
		                            // Display Selected date in textbox
		                            txtDate.setText(dayOfMonth + "-"
		                                    + (monthOfYear + 1) + "-" + year);
		 
		                        }
		                    }, mYear, mMonth, mDay);
		            dpd.show();				
				}});
	        
	        btnTimePicker.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// Process to get Current Time
		            final Calendar c = Calendar.getInstance();
		            mHour = c.get(Calendar.HOUR_OF_DAY);
		            mMinute = c.get(Calendar.MINUTE);
		 
		            // Launch Time Picker Dialog
		            TimePickerDialog tpd = new TimePickerDialog(getActivity(),
		                    new TimePickerDialog.OnTimeSetListener() {
		 
		                        @Override
		                        public void onTimeSet(TimePicker view, int hourOfDay,
		                                int minute) {
		                            // Display Selected time in textbox
		                            txtTime.setText(hourOfDay + ":" + minute);
		                        }
		                    }, mHour, mMinute, false);
		           /* full_date = String.valueOf(mYear) + "-" + String.valueOf(mMonth) + "-" 
		                    	+ String.valueOf(mDay) + " " + String.valueOf(mHour) + ":" 
		                    	+ String.valueOf(mMinute) + ":00";*/
		            full_date = String.valueOf(mYear) + String.valueOf(mMonth)  
	                    	+ String.valueOf(mDay)  + String.valueOf(mHour)  
	                    	+ String.valueOf(mMinute) + "00";
		            Log.d("fullDate", full_date);
		            tpd.show();				
				}});
		
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
void	bookRequest(String foodieId, String restId, String time) {
		
		String						url 	= getString(R.string.URL) + "book";
		Map<String, String> 		params 	= new HashMap<String, String>();
		
		params.put("time", time);
		params.put("restId", String.valueOf(restId));
		params.put("foodieId", foodieId);
		Log.d("Params:", params.toString());
		
		CustomRequest jsObjRequest = new CustomRequest(Method.POST, url, params, this.createMyReqSuccessListener(), this.createMyReqErrorListener());
        ApplicationController.getInstance().addToRequestQueue(jsObjRequest, "Book");

	}
	
	private Response.Listener<JSONObject> createMyReqSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            	 try {
                     VolleyLog.v("Response:%s%n", response.toString(4));
                     Log.d("Request Result: ", response.toString());
                     if (response.has("booknumber")){
                         Toast.makeText(getActivity(), getText(R.string.booking_successful), Toast.LENGTH_SHORT).show();
                         Toast.makeText(getActivity(), "booknumber: " + response.getString("booknumber"), Toast.LENGTH_LONG).show();
                         SharedPreferences.Editor editor		=	settings.edit();
                         editor.putInt("booknumber", response.getInt("booknumber"));
                         editor.commit();
                      }
                     if (response.has("errorCode"))
                     {
                    	 if (response.getInt("errorCode") == -1) {
                    		 Toast.makeText(getActivity(), getText(R.string.generic_error), Toast.LENGTH_SHORT).show();
                    	 }
                    	 if (response.getInt("errorCode") == -2) {
                    		 Toast.makeText(getActivity(), getText(R.string.bad_parameters), Toast.LENGTH_SHORT).show();
                    	 }
                     } 
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
            };
        };
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            	Toast.makeText(getActivity(), getText(R.string.no_internet), Toast.LENGTH_SHORT).show();
                VolleyLog.e("Error: ", error.getMessage());
            }
        };
    }
}
