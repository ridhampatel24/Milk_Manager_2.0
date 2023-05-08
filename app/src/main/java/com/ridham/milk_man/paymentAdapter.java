package com.ridham.milk_man;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class paymentAdapter extends RecyclerView.Adapter<paymentAdapter.MyViewHolder> {

    Context context;
    ArrayList<dataModel> customers;
    public paymentAdapter(Context context, ArrayList<dataModel> customers){
        this.context=context;
        this.customers=customers;
    }
    @NonNull
    @Override
    public paymentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.payment,parent,false);
        return new paymentAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull paymentAdapter.MyViewHolder holder, int position) {
        holder.customerName.setText(customers.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }
    public void setFilteredList(ArrayList<dataModel> filteredList){
        this.customers = filteredList;
        for(dataModel obj : customers){
            Toast.makeText(context, obj.getName(), Toast.LENGTH_SHORT).show();
        }
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView customerName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customername);
        }
    }
}
