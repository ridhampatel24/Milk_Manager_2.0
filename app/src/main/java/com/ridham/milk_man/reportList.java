package com.ridham.milk_man;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class reportList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<dataModel> customers = new ArrayList<>();
    SearchView searchView;
    reportAdapter adapter;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        searchView = findViewById(R.id.searchviewreport);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.home);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.profile){
            Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(reportList.this,profilem.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if(item.getItemId() == R.id.setting){
            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if(item.getItemId() == R.id.logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(reportList.this);
            builder.setTitle("Logout");
            builder.setMessage("Are you sure you want to logout?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                Toast.makeText(this, "LogOut Succes", Toast.LENGTH_SHORT).show();
//            SharedPreferences sharedPreferences;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(reportList.this,entermobile.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            });
            builder.setNegativeButton("No", (dialogInterface, i) -> {
                Toast.makeText(this, "LogOut Cancel", Toast.LENGTH_SHORT).show();
            });
            builder.show();
            //Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawer(GravityCompat.START);

        }
        if(item.getItemId() == R.id.sales){
            Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(reportList.this,sales.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
//    if(item.getItemId() == R.id.sales){
//        Toast.makeText(this, "Sales", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(dashboardm.this,sales.class);
//        startActivity(intent);
//        drawerLayout.closeDrawer(GravityCompat.START);
//    }
        if(item.getItemId() == R.id.payment){
            Toast.makeText(this, "Payment", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(reportList.this,paymentList.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if(item.getItemId() == R.id.report){
            Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(reportList.this, reportList.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}