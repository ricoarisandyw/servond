package com.servond.reaper.servond.Activity.Common;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.servond.reaper.servond.R;
import java.util.HashMap;
import java.util.Map;

public class loginActivity extends AppCompatActivity {
    Gson gson;
    private ProgressDialog progress;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        Button btnSignup = (Button) findViewById(R.id.btnSignup);
        Button btnLoginMechanic = (Button) findViewById(R.id.btnLoginMechanic);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress=new ProgressDialog(loginActivity.this);
                progress.setMessage("Please Wait...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.setProgress(0);
                progress.setCanceledOnTouchOutside(false);
                progress.show();
                validate(etUsername.getText().toString(), etPassword.getText().toString());

            }
        });

        btnLoginMechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress=new ProgressDialog(loginActivity.this);
                progress.setMessage("Please Wait...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.setProgress(0);
                progress.setCanceledOnTouchOutside(false);
                progress.show();
                validateMechanic(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(loginActivity.this, signupActivity.class);
                startActivity(i);
            }
        });
    }

    public void validate(final String username, final String password){
//        progress.show();
        //open --JSON--
        requestQueue = Volley.newRequestQueue(loginActivity.this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String url = "https://servond.000webhostapp.com/loginUsers.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        responseLogin posts =  gson.fromJson(response, responseLogin.class);
                        Log.d("Response", posts.getResult());
                        progress.hide();
                        if(posts.getStatus()==1){
                            Intent i = new Intent(loginActivity.this, menuActivity.class);
                            i.putExtra("username", username);
                            i.putExtra("userType", "Client");
                            startActivity(i);
                        }else{
                            Toast.makeText(loginActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Response", "Error");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };
        requestQueue.add(postRequest);
        //close --JSON--
    }

    public class responseLogin{
        private int status;
        private String result;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

    public void validateMechanic(final String username, final String password){
//        progress.show();
        //open --JSON--
        requestQueue = Volley.newRequestQueue(loginActivity.this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String url = "https://servond.000webhostapp.com/loginMontir.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        progress.hide();
                        Log.d("Response", response);
                        responseLogin posts =  gson.fromJson(response, responseLogin.class);
                        Log.d("Response", posts.getResult());
                        if(posts.getStatus()==1){
                            Intent i = new Intent(loginActivity.this, menuActivity.class);
                            i.putExtra("username", username);
                            i.putExtra("userType", "Mechanic");
                            startActivity(i);
                        }else{
                            Toast.makeText(loginActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Response", "Error");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };
        requestQueue.add(postRequest);
        //close --JSON--
    }

}
