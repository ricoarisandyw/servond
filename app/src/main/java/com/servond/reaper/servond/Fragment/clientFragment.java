package com.servond.reaper.servond.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.Arrays;
import java.util.List;


public class clientFragment extends Fragment {

    //defineDatabaseContext
    Context CTX;

    //parse json
    private RequestQueue requestQueue;
    private Gson gson;

    private RecyclerView recyclerView;
    private List listData = new ArrayList<>();
    private OrderAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        CTX = container.getContext();
        View rootView = inflater.inflate(R.layout.fragment_client, container, false);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        String GETLOC = "servond.000webhostapp.com/show_client.php";
        StringRequest req = new StringRequest(Request.Method.POST, GETLOC,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Log.i("Response : ", response);
                            //Toast.makeText(CTX, response, Toast.LENGTH_SHORT).show();
                            List<Order> posts = Arrays.asList(gson.fromJson(response, Order[].class));
//                            for (Order post : posts) {
//                                listData.add(new Order(
//                                        post.getId_mechanic(),
//                                        post.getId_user(),
//                                        post.getName(),
//                                        post.getPrice()
//                                ));
//                            };
                            mAdapter.notifyDataSetChanged();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Get Data : ", error.toString());
                    }
                }
        );
        requestQueue.add(req);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new OrderAdapter(listData, CTX);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
