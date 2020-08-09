package com.example.projectworkerpart;


import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;


//CustomerDashboard class is menu for the customer
public class CustomerDashboard extends AppCompatActivity {

    String names[] = {"Automobile_mechnician","Beautician","Blacksmith","Butcher","Carpenter","Car Washer","Cobbler",
            "Driver","Electrician","Engineer","Gardener","Hair Dresses","HouseKeeper/maid","Labour","Mechanic","Milkman","Painter",
            "Photographer","Plumber","Security Guard","Tailor"};

    int images[] = {R.drawable.automobile_mechnician,R.drawable.beautician,R.drawable.blacksmith,
            R.drawable.butcher_qasai,R.drawable.carpenter,R.drawable.carwashing,R.drawable.cobbler_mochi,R.drawable.driver2,
            R.drawable.electrician_technician,R.drawable.engineer_supervisor,R.drawable.gardener,R.drawable.hairdresser,
            R.drawable.housekeeper_maid,R.drawable.labour,R.drawable.mechanic,R.drawable.milkman,R.drawable.painter,
            R.drawable.photographer,R.drawable.plumber,R.drawable.securitygaurdservice,R.drawable.tailor};

    List<ItemsModel> itemsModelList = new ArrayList<ItemsModel>();

    GridView GridView;

    CustomAdapter customAdapter;

    String CustomerEmailId;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        GridView = findViewById(R.id.gridview);


        for(int i = 0;i < names.length; i++){

            ItemsModel itemsModel = new ItemsModel(names[i],images[i]);

            itemsModelList.add(itemsModel);

        }

        customAdapter = new CustomAdapter(itemsModelList,this);
        GridView.setAdapter(customAdapter);

        CustomerEmailId=getIntent().getStringExtra("CustomerEmailId");


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.search_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.searchView);

        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Log.e("Main"," data search"+newText);

                customAdapter.getFilter().filter(newText);



                return true;
            }
        });


        return true;

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.searchView){

            return true;
        }

        if(id==R.id.id_Account)
        {
            Intent intent1= new Intent(this,CustomerAccountActivity.class);
            intent1.putExtra("cId",CustomerEmailId);
            startActivity(intent1);

        }
        if(id==R.id.id_UpdateInfo)
        {
            Intent intent2= new Intent(this,updateCustomerActivity.class);

            startActivity(intent2);
        }

        if(id==R.id.id_records)
        {
            Intent intent2= new Intent(this,Customer_E_Activity.class);
            intent2.putExtra("CustomerID",CustomerEmailId);
            startActivity(intent2);
        }
        if(id==R.id.id_log_out)
        {
            Intent intent2= new Intent(this,CustomerLoginActivity.class);
            startActivity(intent2);
        }
        return super.onOptionsItemSelected(item);
    }

    //CustomAdapter class is for grid view layout
    public class CustomAdapter extends BaseAdapter implements Filterable {

        private List<ItemsModel> itemsModelsl;
        private List<ItemsModel> itemsModelListFiltered;
        private Context context;

        public CustomAdapter(List<ItemsModel> itemsModelsl, Context context) {
            this.itemsModelsl = itemsModelsl;
            this.itemsModelListFiltered = itemsModelsl;
            this.context = context;
        }

        @Override
        public int getCount() {
            return itemsModelListFiltered.size();
        }

        @Override
        public Object getItem(int position) {
            return itemsModelListFiltered.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.row_item,null);


            TextView names = view.findViewById(R.id.name);

            ImageView imageView = view.findViewById(R.id.images);

            names.setText(itemsModelListFiltered.get(position).getName());

            imageView.setImageResource(images[position]);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("main","item clicked");
                   Intent intent2= new Intent(CustomerDashboard.this,ShowImageActivity.class);
                   intent2.putExtra("items",itemsModelListFiltered.get(position));
                   intent2.putExtra("CustomerID",CustomerEmailId);
                   startActivity(intent2);
                }
            });

            return view;
        }



        @Override
        //getFilter() function is used for filter functionality when we search for any thing and it will update arraylist accordingly
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {

                    FilterResults filterResults = new FilterResults();
                    if(constraint == null || constraint.length() == 0){
                        filterResults.count = itemsModelsl.size();
                        filterResults.values = itemsModelsl;

                    }else{
                        List<ItemsModel> resultsModel = new ArrayList<>();
                        String searchStr = constraint.toString().toLowerCase();

                        for(ItemsModel itemsModel:itemsModelsl){
                            if(itemsModel.getName().toLowerCase().contains(searchStr)){
                                resultsModel.add(itemsModel);
                                filterResults.count = resultsModel.size();
                                filterResults.values = resultsModel;
                            }

                        }
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {

                    itemsModelListFiltered = (List<ItemsModel>) results.values;
                    notifyDataSetChanged();

                }
            };
            return filter;
        }
    }




}
