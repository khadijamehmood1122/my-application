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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class detail_W_RVAdapter extends RecyclerView.Adapter<detail_W_RVAdapter.RVHolderClass> {
    ArrayList<NewWorkerModelList> objectModelClassList;

    Context context;

    public detail_W_RVAdapter(ArrayList<NewWorkerModelList> objectModelClassList, Context context) {
        this.objectModelClassList = objectModelClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public RVHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RVHolderClass(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_single_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RVHolderClass holder, int position) {

        final NewWorkerModelList objectModelClass = objectModelClassList.get(position);
        holder.imagename.setText(objectModelClass.getWorkerName());
        holder.jobTitle.setText(objectModelClass.getWorkerJob());
        holder.dateAndtime.setText(objectModelClass.getDateAndTime());
        holder.workerRating.setText(objectModelClass.getWRating());
        holder.imageP.setImageBitmap(objectModelClass.getImage());
    }

    @Override
    public int getItemCount() {
        return objectModelClassList.size();
    }


    public class RVHolderClass extends RecyclerView.ViewHolder {
        TextView imagename;
        TextView jobTitle;
        ImageView imageP;
        TextView dateAndtime;
        TextView workerRating;
        CardView cardView;

        public RVHolderClass(@NonNull View itemView) {
            super(itemView);
            imagename = itemView.findViewById(R.id.sr_name);
            jobTitle = itemView.findViewById(R.id.sr_job);
            imageP = itemView.findViewById(R.id.sr_image);
            dateAndtime=itemView.findViewById(R.id.sr_dateAndTime);
            cardView = itemView.findViewById(R.id.sr_card);
            workerRating=itemView.findViewById(R.id.sr_rating);


        }

    }
}
