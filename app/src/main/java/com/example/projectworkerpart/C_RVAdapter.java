package com.example.projectworkerpart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


//C_RVAdapter ADAPTER class is for customer list(Recycler view) layout
public class C_RVAdapter extends RecyclerView.Adapter<C_RVAdapter.CRVHolderClass>{

    ArrayList<NewCustomerModelClass> objectNCMC_list;
    String dateAndTime;
    Context context;

    public C_RVAdapter(ArrayList<NewCustomerModelClass> objectNCMC_list, Context context,String dateAndTime) {
        this.objectNCMC_list = objectNCMC_list;
        this.context = context;
        this.dateAndTime=dateAndTime;
    }

    @NonNull
    @Override
    public C_RVAdapter.CRVHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new C_RVAdapter.CRVHolderClass(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_single_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull C_RVAdapter.CRVHolderClass holder, int position) {

        final NewCustomerModelClass objectNCMC = objectNCMC_list.get(position);
        holder.customer_name.setText(objectNCMC.getName());
        holder.customer_city.setText(objectNCMC.getCity());

        if(!dateAndTime.equals(" "))
        {
            holder.date_time.setText(objectNCMC.getDateAndTime());
        }
        if(dateAndTime.equals(" "))
        {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, CustomerRecordActivity.class);
                intent1.putExtra("customerName", objectNCMC.getName());
                context.startActivity(intent1);
            }
        });
        }
    }

    @Override
    public int getItemCount() {
        return objectNCMC_list.size();
    }
    public class CRVHolderClass extends RecyclerView.ViewHolder {
        TextView customer_name;
        TextView customer_city;
        TextView date_time;
        CardView cardView;

        public CRVHolderClass(@NonNull View itemView) {
            super(itemView);
            customer_name = itemView.findViewById(R.id.csr_name);
            customer_city = itemView.findViewById(R.id.csr_job);
            cardView = itemView.findViewById(R.id.sr_card);
            date_time=itemView.findViewById(R.id.csr_date);

        }
    }
}
