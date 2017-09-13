package com.servond.reaper.servond.Activity.Common;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.servond.reaper.servond.Activity.MainActivity;
import com.servond.reaper.servond.Model.UserValidation;
import com.servond.reaper.servond.Model.Users;
import com.servond.reaper.servond.R;

import java.util.HashMap;
import java.util.Map;

public class signupActivity extends AppCompatActivity {
    Gson gson;
    EditText etUsername, etName, etPassword, etEmail, etMotor, etYear, etPhone;
    Users user = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = (EditText) findViewById(R.id.etUsernameSU);
        etName = (EditText) findViewById(R.id.etName_signup);
        etPassword = (EditText) findViewById(R.id.etPasswordSU);
        etEmail = (EditText) findViewById(R.id.etEmail_signup);
        etMotor = (EditText) findViewById(R.id.etMotor_signup);
        etYear = (EditText) findViewById(R.id.etYear_signup);
        etPhone = (EditText) findViewById(R.id.etPhone_signup);

        Button btnSignup = (Button) findViewById(R.id.btnSignupSU);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            user.setUsername(etUsername.getText().toString());
            user.setName(etName.getText().toString());
            user.setEmail(etEmail.getText().toString());
            user.setMotor(etMotor.getText().toString());
            user.setYear(etYear.getText().toString());
            user.setPhone(etPhone.getText().toString());
            validate(user, etPassword.getText().toString());
            }
        });
    }
    public void validate(final Users user, final String password){
        //open --JSON--
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String Url = "https://servond.000webhostapp.com/signupUsers.php";
        StringRequest req = new StringRequest(Request.Method.POST, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Log.i("Response POST : ", response);
                            responseLogin mgu = gson.fromJson(response, responseLogin.class);
                            if(mgu.getStatus()==1){
                                Intent i = new Intent(signupActivity.this, MainActivity.class);
                                i.putExtra("username", user.getUsername());
                                i.putExtra("userType", "Client");
                                startActivity(i);
                            }else{
                                Log.i("Response POST : ", "Failed to input Data");
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("GetUser : ", error.toString());
                        Toast.makeText(signupActivity.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("username", user.getUsername());
                params.put("password", password);
                params.put("email", user.getEmail());
                params.put("name", user.getName());
                params.put("motor", user.getMotor());
                params.put("year", user.getYear());
                params.put("phone", user.getPhone());
                return params;
            }
        };
        requestQueue.add(req);
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
}
