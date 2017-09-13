package com.servond.reaper.servond.Activity.Client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.servond.reaper.servond.Activity.detailMechanicActivity;
import com.servond.reaper.servond.GPSTracker;
import com.servond.reaper.servond.Model.ListService;
import com.servond.reaper.servond.Model.Order;
import com.servond.reaper.servond.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class urgentActivity extends AppCompatActivity {
    public int inter = 0;
    private RequestQueue requestQueue;
    private Gson gson;
    GPSTracker gps = new GPSTracker(this);
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urgent);

        gps = new GPSTracker(urgentActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(urgentActivity.this, "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        String newString = null;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("STRING_I_NEED");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("STRING_I_NEED");
        }
        TextView tvStatus = (TextView) findViewById(R.id.tvStatusUrgent);
        tvStatus.setText(newString);

        final String name = "5";
        final String category = newString;
        final String deskripsi = "butuh cepat";

        requestQueue = Volley.newRequestQueue(urgentActivity.this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String INPUT_PESANAN_URL = "https://servond.000webhostapp.com/input_service.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, INPUT_PESANAN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(urgentActivity.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(urgentActivity.this,"Cek Koneksi Internet"+"\n",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("id_user", name);
                params.put("category",category);
                params.put("description",deskripsi);
                params.put("latitude", String.valueOf(latitude));
                params.put("longitude", String.valueOf(longitude));
                return params;
            }
        };
        requestQueue.add(stringRequest);

        Timer timer = new Timer();
        timer.schedule(new checkOrder(), 0, 5000);
    }

    class checkOrder extends TimerTask{
        @Override
        public void run() {
            inter++;
            if(inter==2){
                Intent i = new Intent(urgentActivity.this, detailMechanicActivity.class);
                startActivity(i);
                urgentActivity.this.finish();
            }
            requestQueue = Volley.newRequestQueue(urgentActivity.this);
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");
            gson = gsonBuilder.create();

            //Toast.makeText(listOrderActivity.this, "USER_ID : "+user_id, Toast.LENGTH_SHORT).show();
            String url = "https://servond.000webhostapp.com/show_client.php";
            StringRequest req = new StringRequest(url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                Log.i("Response : ", response);
                                ListService postsA =  gson.fromJson(response, ListService.class);
                                List<Order> posts = postsA.getClients();
                                boolean enable = true;
                                Log.i("PostActivity", posts.size() + " posts loaded.");
                                for (Order post : posts) {
                                    Log.i("PostActivity", post + ": " + post.getDescription());
                                    if(post.getId_customer()==5){
                                        enable = true;
                                    }
                                }
                                if(enable==false){
                                    Intent i = new Intent(urgentActivity.this, detailMechanicActivity.class);
                                    startActivity(i);
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Get Data : ", error.toString());
                            Toast.makeText(urgentActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
            ){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    return params;
                }
            };
            requestQueue.add(req);
        }

    }
}
