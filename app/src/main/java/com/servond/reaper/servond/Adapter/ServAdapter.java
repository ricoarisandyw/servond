package com.servond.reaper.servond.Adapter;

/**
 * Created by Reaper on 8/12/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.servond.reaper.servond.Model.Order;
import com.servond.reaper.servond.R;

import java.util.ArrayList;

public class ServAdapter extends BaseAdapter {

    private ArrayList<Order> serv;
    private LayoutInflater songInf;

    public ServAdapter(Context c, ArrayList<Order> theServ){
        serv= theServ;
        songInf=LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return serv.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        //map to song layout
        final LinearLayout songLay = (LinearLayout)songInf.inflate
                (R.layout.listservice, parent, false);
        //get title and artist views
        TextView servName = (TextView)songLay.findViewById(R.id.serviceName);
        TextView servPrice = (TextView)songLay.findViewById(R.id.servicePrice);
        //get song using position
        final Order currServ = serv.get(position);
        //get title and artist strings
        servName.setText(currServ.getName());
//        servPrice.setText(currServ.getPrice());
        //set position as tag
        songLay.setTag(position);
        songLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(), serv.get(position).getName()+" telah dipilih", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(parent.getContext(), MenuActivity.class);
//                parent.getContext().startActivity(i);
//                ((Activity)parent.getContext()).finish();
            }
        });
        return songLay;
    }
}
