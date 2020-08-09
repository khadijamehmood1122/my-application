package com.example.projectworkerpart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//RVAdapter is adapter class used for worker info list
class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVHolderClass> {


    ArrayList<NewModelClass> objectModelClassList;
    String customerId;
    String activity_name;
    Context context;

    public RVAdapter(ArrayList<NewModelClass> objectModelClassList,Context context,String id,String activity_name) {
        this.objectModelClassList = objectModelClassList;
        this.customerId=id;
        this.context=context;
        this.activity_name=activity_name;

    }
    @NonNull
    @Override
    public RVHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new RVHolderClass(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RVHolderClass holder, int position) {

        final NewModelClass objectModelClass = objectModelClassList.get(position);
        holder.imagename.setText(objectModelClass.getImagename());
        holder.jobTitle.setText(objectModelClass.getJobtitle());
        holder.imageP.setImageBitmap(objectModelClass.getImage());

        if(activity_name.equals("showimageactivity"))
        {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(context, DetailsActivity.class);
                    intent1.putExtra("imagename", objectModelClass.getImagename());
                    intent1.putExtra("LoginEmailID", customerId);
                    context.startActivity(intent1);
                }
            });
        }
        else if(activity_name.equals("recordsactivity"))
        {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent(context, WorkerRecordActivity.class);
                    intent2.putExtra("workername", objectModelClass.getImagename());
                    context.startActivity(intent2);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return objectModelClassList.size();
    }

    public class RVHolderClass extends RecyclerView.ViewHolder {
        TextView imagename;
        TextView jobTitle;
        ImageView imageP;
        CardView cardView;

        public RVHolderClass(@NonNull View itemView) {
            super(itemView);
            imagename = itemView.findViewById(R.id.sr_name);
            jobTitle = itemView.findViewById(R.id.sr_job);
            imageP = itemView.findViewById(R.id.sr_image);
            cardView = itemView.findViewById(R.id.sr_card);


        }

    }


}