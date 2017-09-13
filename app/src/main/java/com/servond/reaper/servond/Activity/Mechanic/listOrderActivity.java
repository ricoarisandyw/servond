package com.servond.reaper.servond.Activity.Mechanic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.servond.reaper.servond.Adapter.OrderAdapter;
import com.servond.reaper.servond.Model.Order;
import com.servond.reaper.servond.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class listOrderActivity extends AppCompatActivity {

    Context context;
    private List<Order> listOrders = new ArrayList<>();
    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private RequestQueue requestQueue;
    private Gson gson;
    private ProgressBar spinner;

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_order);

        spinner=(ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.VISIBLE);

        adapter = new OrderAdapter(listOrders, context);
        recyclerView = (RecyclerView) findViewById(R.id.listPesananku);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        String name = loadData("username");
        Toast.makeText(listOrderActivity.this, name, Toast.LENGTH_SHORT).show();

        requestQueue = Volley.newRequestQueue(this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String url = "https://servond.000webhostapp.com/show_order.php";
        StringRequest req = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Log.i("Response : ", response);
                            responseInit postResponse =  gson.fromJson(response, responseInit.class);
                            ArrayList<Order> posts = postResponse.getOrders();
                            for (Order post : posts) {
                                Log.i("Post :", posts.toString() );
                                listOrders.add(new Order(
                                        post.getId_order(),
                                        post.getName(),
                                        post.getCategory(),
                                        post.getDescription(),
                                        post.getLatitude(),
                                        post.getLongitude(),
                                        post.getNeed_at(),
                                        post.getId_mechanic()
                                ));
                            }
                            spinner.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Get Data : ", error.toString());
                        Toast.makeText(listOrderActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
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

    public String loadData(String name){
        SharedPreferences prefs = getSharedPreferences("UserData", 0);
        String data = prefs.getString(name,"");
        Log.d(name + " keluar:", data);
        return data;
    }
}
