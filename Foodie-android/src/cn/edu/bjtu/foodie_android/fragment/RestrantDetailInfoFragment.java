package cn.edu.bjtu.foodie_android.fragment;

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
import android.widget.Toast;
import cn.edu.bjtu.foodie_android.LocationActivity;
import cn.edu.bjtu.foodie_android.R;
import cn.edu.bjtu.foodie_android.UI.LoginActivity;
import cn.edu.bjtu.foodie_android.UI.MyAccountActivity;
import cn.edu.bjtu.foodie_android.manager.ApplicationController;

public class RestrantDetailInfoFragment extends Fragment {

	private View view;
	private Button 	location;
	private Button 	order;
	public	String 	status;
	public	int		queueNum;
    SharedPreferences settings;


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
					bookRequest(6, 2,"11");
				if (login == false)
                   	Toast.makeText(getActivity(), getText(R.string.no_user), Toast.LENGTH_SHORT).show();
			}
		});
		
		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	
	void				bookRequest(int foodieId, int restId, String time) {
      
		String						url 	= getString(R.string.URL) + "book";
		Date						date	= new Date();					
		Map<String, String> 	params 		= new HashMap<String, String>();
		
		params.put("foodieId", String.valueOf(foodieId));
		params.put("restId", String.valueOf(restId));
		Log.d("Time Test:", date.toString());
		params.put("time", date.toString());
		
		 JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params),
                 new Response.Listener<JSONObject>() {
                     @Override
                     public void onResponse(JSONObject response) {
                         try {
                             VolleyLog.v("Response:%s%n", response.toString(4));
                             Log.d("Request Result: ", response.toString());
                             if (response.has("booknumber")){
                                 Toast.makeText(getActivity(), getText(R.string.booking_successful), Toast.LENGTH_SHORT).show();
                                 SharedPreferences.Editor editor		=	settings.edit();
                                 editor.putInt("booknumber", response.getInt("booknumber"));
                                 editor.commit();
                              }
                             if (response.getInt("errorCode") == -1) {
                                 Toast.makeText(getActivity(), getText(R.string.generic_error), Toast.LENGTH_SHORT).show();
                             }
                             if (response.getInt("errorCode") == -2) {
                                 Toast.makeText(getActivity(), getText(R.string.bad_parameters), Toast.LENGTH_SHORT).show();
                             }
                            
                         } catch (JSONException e) {
                             e.printStackTrace();
                         }
                     }
                 }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Toast.makeText(getActivity(), getText(R.string.no_internet), Toast.LENGTH_SHORT).show();
                 VolleyLog.e("Error: ", error.getMessage());
             }
            
         });
         ApplicationController.getInstance().addToRequestQueue(req, "Book");
	}
}
