package com.ridham.milk_man;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
public class dashboardm extends AppCompatActivity {

    ArrayList<dataModel> customers = new ArrayList<>();
    SearchView searchView;
    recyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboardm);
        searchView = findViewById(R.id.searchView);

//        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    showInputMethod(v.findFocus());
////
//                }
//            }
//        });
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
        adapter = new recyclerAdapter(this,customers);
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
//    private void showInputMethod(View view) {
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm != null) {
//            imm.showSoftInput(view, 0);
//        }
//    }
}