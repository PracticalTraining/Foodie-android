package cn.edu.bjtu.foodie_android.UI;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.edu.bjtu.foodie_android.R;
import cn.edu.bjtu.foodie_android.manager.ApplicationController;

public class LoginActivity extends BaseActivity {
	private 			Button 					register;
    private 			Button 					login;
    private 			LoginActivity           instance 	= this;
    public static final String  				PREFS_NAME 	= "LoginPrefFile";
    SharedPreferences	settings;						    
    SharedPreferences.Editor editor;




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		settings		=	this.getSharedPreferences(PREFS_NAME, 0);
		editor = settings.edit();
		initView();
		setListener();
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		register = (Button) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);

	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
			}
		});
		 login.setOnClickListener(new OnClickListener() {

             @Override
             public void onClick(View v) {
                     // TODO Auto-generated method stub                                                                                                                                                 
                     login();
                     }
     });

	}
	
	 protected void  login()  {
         EditText text = (EditText)findViewById(R.id.loginaccount);
         String username = text.getText().toString();
         text = (EditText)findViewById(R.id.loginpassword);
         String password = text.getText().toString();
         loginRequest(username, password);
	 }
	 
	 protected void  loginRequest(String username, String password)  {
         String                  finalurl = getString(R.string.URL) + "foodie/login";

         finalurl = finalurl + "?name=";
         finalurl = finalurl + username;
         finalurl = finalurl + "&password=";
         finalurl = finalurl + password;
         VolleyLog.d("URL: ", finalurl);
         editor.putString("username", username);
         editor.putString("password", password);
         JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, finalurl, null,
                 new Response.Listener<JSONObject>() {
                     @Override
                     public void onResponse(JSONObject response) {
                         try {
                             VolleyLog.v("Response:%s%n", response.toString(4));
                             Log.d("Response:", response.toString());
                             if (response.has("id")) {
                                 Toast.makeText(instance, getText(R.string.login_success), Toast.LENGTH_SHORT).show();
                                 editor.putBoolean("login", true);
                                 editor.commit();
                                 Intent intent = new Intent(LoginActivity.this, MyAccountActivity.class);
                                 startActivity(intent);
                             } 
                             if (response.getString("errorCode").equals("-1")) {
                                         int duration = Toast.LENGTH_LONG;
                                         Toast toast = Toast.makeText(getApplicationContext(), getText(R.string.user_not_exists), duration);
                                         toast.show();
                                 }
                             if (response.getInt("errorCode") == -2) {
                                         int duration = Toast.LENGTH_LONG;
                                         Toast toast = Toast.makeText(getApplicationContext(), getText(R.string.password_incorect), duration);
                                         toast.show();
                             }
                             if (response.getInt("errorCode") == -3) {
                                 int duration = Toast.LENGTH_LONG;
                                 Toast toast = Toast.makeText(getApplicationContext(), getText(R.string.bad_parameters), duration);
                                 toast.show();
                     }
                         } catch (JSONException e) {
                             e.printStackTrace();
                         }
                     }
                 }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 Toast.makeText(getApplicationContext(), getText(R.string.generic_error), Toast.LENGTH_SHORT).show();
                 VolleyLog.e("Error: ", error.getMessage());
             }
         });
          // add the request object to the queue to be executed                                                                                                                                              
          ApplicationController.getInstance().addToRequestQueue(req, "Login");

  }


}
