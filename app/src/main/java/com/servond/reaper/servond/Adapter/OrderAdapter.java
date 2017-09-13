package com.servond.reaper.servond.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;;
import android.widget.TextView;

import com.servond.reaper.servond.Activity.Mechanic.detailOrderActivity;
import com.servond.reaper.servond.Model.Order;
import com.servond.reaper.servond.R;

import java.util.List;


/**
 * Created by Reaper on 8/12/2017.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ClientViewHolder> {

    private java.util.List Client;
    final Context context;

    public class ClientViewHolder extends RecyclerView.ViewHolder {
        CardView cv2;
        TextView tvNama, tvKategori, tvDeskripsi, tvId, tvNeedAt;

        ClientViewHolder(View itemView) {
            super(itemView);
            cv2 = (CardView)itemView.findViewById(R.id.cv2);
            tvId = (TextView)itemView.findViewById(R.id.tvId);
            tvNama = (TextView)itemView.findViewById(R.id.tvNama);
            tvKategori = (TextView)itemView.findViewById(R.id.tvKategori);
            tvDeskripsi = (TextView)itemView.findViewById(R.id.tvDeskripsi);
            tvNeedAt = (TextView)itemView.findViewById(R.id.tvNeedAt);
        }
    }

    public OrderAdapter(List<Order> listPesanan, Context context) {
        this.Client = (List) listPesanan;
        this.context = (Context) context;
    }

    @Override
    public int getItemCount() {
        return Client.size();
    }

    @Override
    public OrderAdapter.ClientViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order, viewGroup, false);
        OrderAdapter.ClientViewHolder pvh = new OrderAdapter.ClientViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(OrderAdapter.ClientViewHolder listViewHolder, int position) {
        final Order order = (Order) Client.get(position);
        listViewHolder.tvId.setText(Integer.toString(order.getId_order()));
        listViewHolder.tvId.setVisibility(View.INVISIBLE);
        listViewHolder.tvNama.append(order.getName());
        listViewHolder.tvDeskripsi.append(order.getDescription());
        if(order.getId_mechanic()==0){}else{
            listViewHolder.tvDeskripsi.append(" (Accepted)");
        }
        listViewHolder.tvKategori.append(order.getCategory());
        listViewHolder.tvNeedAt.append(order.getNeed_at());
        listViewHolder.cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), detailOrderActivity.class);
                i.putExtra("id_order", String.valueOf(order.getId_order()));
                i.putExtra("nama", order.getName());
                i.putExtra("kategori", order.getCategory());
                i.putExtra("deskripsi", order.getDescription());
                i.putExtra("longitude", order.getLongitude());
                i.putExtra("latitude", order.getLatitude());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
