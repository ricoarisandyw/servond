package com.servond.reaper.servond.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.servond.reaper.servond.Activity.Mechanic.detailOrderActivity;
import com.servond.reaper.servond.Model.Order;
import com.servond.reaper.servond.R;

import java.util.List;

;


/**
 * Created by Reaper on 8/12/2017.
 */

public class myOrderAdapter extends RecyclerView.Adapter<myOrderAdapter.ClientViewHolder> {

    private List Client;
    final Context context;

    public class ClientViewHolder extends RecyclerView.ViewHolder {
        CardView cv2;
        //ImageView ivListGambarPesanan;
        TextView tvNama, tvKategori, tvDeskripsi, tvId;

        ClientViewHolder(View itemView) {
            super(itemView);
            cv2 = (CardView)itemView.findViewById(R.id.cv2);
            //ivListGambarPesanan = (ImageView)itemView.findViewById(R.id.ivListGambarPesanan);
            tvId = (TextView)itemView.findViewById(R.id.tvId);
            tvNama = (TextView)itemView.findViewById(R.id.tvNama);
            tvKategori = (TextView)itemView.findViewById(R.id.tvKategori);
            tvDeskripsi = (TextView)itemView.findViewById(R.id.tvDeskripsi);
        }
    }

    public myOrderAdapter(List<Order> listPesanan, Context context) {
        this.Client = (List) listPesanan;
        this.context = (Context) context;
    }

    @Override
    public int getItemCount() {
        return Client.size();
    }

    //private final View.OnClickListener mOnClickListener = new MyOnClickListener();

    @Override
    public myOrderAdapter.ClientViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order, viewGroup, false);
        myOrderAdapter.ClientViewHolder pvh = new myOrderAdapter.ClientViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(myOrderAdapter.ClientViewHolder listViewHolder, int position) {
        final Order l = (Order) Client.get(position);
        //personViewHolder.ivListGambarPesanan.setImageResource(Integer.parseInt(l.getGambar()));
        listViewHolder.tvId.setText(Integer.toString(l.getId_order()));
        listViewHolder.tvNama.setText(l.getName());
        listViewHolder.tvDeskripsi.setText(l.getDescription());
        listViewHolder.tvKategori.setText(l.getCategory());
        listViewHolder.cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), detailOrderActivity.class);
                i.putExtra("id", String.valueOf(l.getId_order()));
                i.putExtra("nama", l.getName());
                i.putExtra("kategori", l.getCategory());
                i.putExtra("deskripsi", l.getDescription());
                v.getContext().startActivity(i);
            }
        });
    }
    

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
