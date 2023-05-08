package com.ridham.milk_man;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class reportList extends AppCompatActivity {
    ArrayList<dataModel> customers = new ArrayList<>();
    SearchView searchView;
    reportAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        searchView = findViewById(R.id.searchviewreport);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        RecyclerView recyclerView = findViewById(R.id.customerList);
        setUpCustomer();
        adapter = new reportAdapter(this,customers);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void setUpCustomer(){
        String[] names = getResources().getStringArray(R.array.customer_names);
        for(int i=0;i<names.length;i++){
            customers.add(new dataModel(names[i]));
        }
    }
    private void filterList(String text) {
        ArrayList<dataModel> filteredList = new ArrayList<>();
        for(dataModel item : customers ){
//            Toast.makeText(this, item, Toast.LENGTH_SHORT).show();
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
//                Toast.makeText(this, "entered true loop", Toast.LENGTH_SHORT).show();
                filteredList.add(item);
            }
            if(filteredList.isEmpty()){
                Toast.makeText(this, "No data Found", Toast.LENGTH_SHORT).show();
            }else{
                adapter.setFilteredList(filteredList);
            }
        }
    }
}