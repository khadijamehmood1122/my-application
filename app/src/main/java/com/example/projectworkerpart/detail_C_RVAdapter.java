package com.example.projectworkerpart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class detail_C_RVAdapter extends RecyclerView.Adapter<detail_C_RVAdapter.RVHolderClass>
{
    ArrayList<detailWorkerRecord> objectNCMC_list;
    Context context;

    public detail_C_RVAdapter(ArrayList<detailWorkerRecord> objectNCMC_list, Context context) {
        this.objectNCMC_list = objectNCMC_list;
        this.context = context;
    }

    @NonNull
    @Override
    public detail_C_RVAdapter.RVHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new RVHolderClass(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_c_single_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull detail_C_RVAdapter.RVHolderClass holder, int position) {

        final detailWorkerRecord objectNCMC = objectNCMC_list.get(position);
        holder.customer_name.setText(objectNCMC.getName());
        holder.customer_city.setText(objectNCMC.getCity());
        holder.date_time.setText(objectNCMC.getDateAndTime());
        holder.C_rating.setText(objectNCMC.getWrating());
    }

    @Override
    public int getItemCount() {

        return objectNCMC_list.size();
    }
    public class RVHolderClass extends RecyclerView.ViewHolder {
        TextView customer_name;
        TextView customer_city;
        TextView date_time;
        CardView cardView;
        TextView C_rating;

        public RVHolderClass(@NonNull View itemView) {
            super(itemView);
            customer_name = itemView.findViewById(R.id.csr_name);
            customer_city = itemView.findViewById(R.id.csr_job);
            cardView = itemView.findViewById(R.id.sr_card);
            date_time=itemView.findViewById(R.id.csr_date);
            C_rating=itemView.findViewById(R.id.csr_rating);


        }

    }
}



