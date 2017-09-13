package com.servond.reaper.servond.Activity.Mechanic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.servond.reaper.servond.GPSTracker;
import com.servond.reaper.servond.Haversine;
import com.servond.reaper.servond.Model.Order;
import com.servond.reaper.servond.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class detailOrderActivity extends AppCompatActivity{

    String newId = null;
    String mIdMechanic, longitude, latitude;
    GPSTracker gps = new GPSTracker(this);
    String mMyLocLong;
    Button mBtnAcceptOrder;
    String mMyLocLat;
    Gson gson;
    TextView tvDeskripsi;
    double distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);

        tvDeskripsi = (TextView) findViewById(R.id.tvDeskripsiDetailClient);

        String newNama = null;
        String newDeskripsi = null;
        String newKategori = null;

        //GET SHARED DATA
        newId = getIntent().getStringExtra("id_order");
        newKategori = getIntent().getStringExtra("kategori");
        newDeskripsi = getIntent().getStringExtra("deskripsi");
        newNama = getIntent().getStringExtra("nama");
        longitude = getIntent().getStringExtra("longitude");
        latitude = getIntent().getStringExtra("latitude");

        mIdMechanic = loadData("id_mechanic");

        Log.d("Response Id : ", newId);
        //CHECK MY LOCATION USING GPS
        gps = new GPSTracker(detailOrderActivity.this);
        if(gps.canGetLocation()){
            mMyLocLat = Double.toString(gps.getLatitude());
            mMyLocLong = Double.toString(gps.getLongitude());
        }else{
            gps.showSettingsAlert();
        }
        //COUNT DISTANCE BEETWEN MY LOCATION AND ORDER LOCATION
        Haversine haversine = new Haversine();
        distance = haversine.getDistanceFromLatLonInKm(
                Double.parseDouble(mMyLocLat),
                Double.parseDouble(mMyLocLong),
                Double.parseDouble(latitude),
                Double.parseDouble(longitude)
        );
        if(distance>10){
            Log.d("Price :", "Mahal"+distance);
        }else if(distance>100){
            Log.d("Price :", "Mahal Banget"+distance);
        }else{
            Log.d("Price :", "Kejauhan"+distance);
        }

        mBtnAcceptOrder = (Button) findViewById(R.id.detailclient_btn_acceptorder);
        mBtnAcceptOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptedClient();
            }
        });

        Button mBtnLocation = (Button) findViewById(R.id.btnLocation);
        mBtnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "http://maps.google.com/maps?saddr="+mMyLocLat+","+mMyLocLong+"&daddr="+latitude+","+longitude;
                Toast.makeText(detailOrderActivity.this,uri,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });

        orderInit();
    }

    public void acceptedClient(){
        RequestQueue requestQueue = Volley.newRequestQueue(detailOrderActivity.this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        Gson gson = gsonBuilder.create();

        String INPUT_PESANAN_URL = "https://servond.000webhostapp.com/order_accepted.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, INPUT_PESANAN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(detailOrderActivity.this,newId+":::"+mIdMechanic,Toast.LENGTH_LONG).show();
                        Intent i = new Intent(detailOrderActivity.this, myClientActivity.class);
                        startActivity(i);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(detailOrderActivity.this,"Cek Koneksi Internet"+"\n",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("id_order",newId);
                params.put("id_mechanic",mIdMechanic);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void cancelClient(){
        RequestQueue requestQueue = Volley.newRequestQueue(detailOrderActivity.this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String INPUT_PESANAN_URL = "https://servond.000webhostapp.com/cancel_his_order.php?id_order="+newId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, INPUT_PESANAN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(detailOrderActivity.this,"Cek Koneksi Internet"+"\n",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("id_order",newId);
                params.put("id_mechanic",mIdMechanic);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void orderInit(){
        RequestQueue requestQueue = Volley.newRequestQueue(detailOrderActivity.this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String INPUT_PESANAN_URL = "https://servond.000webhostapp.com/detail_order.php?id_order="+newId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, INPUT_PESANAN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response :", response);
                        responseInit postResponse =  gson.fromJson(response, responseInit.class);
                        String rest =
                                "\nName : "+postResponse.getOrders().get(0).getName() +
                                        "\nCategory : "+postResponse.getOrders().get(0).getCategory() +
                                        "\nNeed at : "+postResponse.getOrders().get(0).getNeed_at() +
                                        "\nDistance :" + Double.toString(distance) +
                                        "\nMotor :" + postResponse.getOrders().get(0).getMotor() +
                                        "\nYear :" + postResponse.getOrders().get(0).getYear()
                                ;
                        tvDeskripsi.setText(rest);
                        if(postResponse.getOrders().get(0).getId_mechanic()==0){

                        }else {
                            mBtnAcceptOrder.setText("Cancel Order");
                            mBtnAcceptOrder.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    cancelClient();
                                }
                            });
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(detailOrderActivity.this,"Cek Koneksi Internet"+"\n",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public String loadData(String name){
        SharedPreferences prefs = getSharedPreferences("UserData", 0);
        String data = prefs.getString(name,"");
        Log.d(name + " keluar:", data);
        return data;
    }

    public class responseInit{
        private int status;
        private ArrayList<Order> orders;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public ArrayList<Order> getOrders() {
            return orders;
        }

        public void setOrders(ArrayList<Order> orders) {
            this.orders = orders;
        }

    }
}
