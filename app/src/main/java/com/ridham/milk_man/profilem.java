package com.ridham.milk_man;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

public class profilem extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    private AppBarConfiguration mAppBarConfiguration;
    SharedPreferences sharedPreferences;
    NavigationView navigationView;
    Toolbar toolbar;
    TextInputLayout editText1;
    TextInputLayout areaText1;
    TextInputLayout addressText1;
    Button save;
    //Api
    String mobile;
    String customer_name;
    String customer_pincode;
    String customer_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilem);

        SharedPreferences getShared = getSharedPreferences("user",MODE_PRIVATE);
        mobile = getShared.getString("provider_phone_number","1234567890");
        customer_name = getShared.getString("provider_name","Ridham");
        customer_pincode = getShared.getString("provider_pincode","123456");
        customer_address = getShared.getString("provider_address","123456");
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        editText1 = findViewById(R.id.full_name1);
        areaText1 = findViewById(R.id.area1);
        addressText1 = findViewById(R.id.address1);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.profile);
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.profile){
            Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(profilem.this,profilem.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if(item.getItemId() == R.id.setting){
            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if(item.getItemId() == R.id.logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(profilem.this);
            builder.setTitle("Logout");
            builder.setMessage("Are you sure you want to logout?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                Toast.makeText(this, "LogOut Succes", Toast.LENGTH_SHORT).show();
//            SharedPreferences sharedPreferences;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(profilem.this,entermobile.class);
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
//        if(item.getItemId() == R.id.sales){
//            Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(profilem.this,sales.class);
//            startActivity(intent);
//            drawerLayout.closeDrawer(GravityCompat.START);
//        }
        if(item.getItemId() == R.id.sales){
            Toast.makeText(this, "Sales", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(profilem.this,sales.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if(item.getItemId() == R.id.payment){
            Toast.makeText(this, "Payment", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(profilem.this,paymentList.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if(item.getItemId() == R.id.report){
            Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(profilem.this, reportList.class);
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