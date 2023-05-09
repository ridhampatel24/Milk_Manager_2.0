package com.ridham.milk_man;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class profilec extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    private AppBarConfiguration mAppBarConfiguration;
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profilec);

        SharedPreferences getShared = getSharedPreferences("user",MODE_PRIVATE);
        mobile = getShared.getString("customer_phone_number","1234567890");
        customer_name = getShared.getString("customer_name","Ridham");
        customer_pincode = getShared.getString("customer_pincode","123456");
        customer_address = getShared.getString("customer_address","123456");


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        editText1 = findViewById(R.id.full_name1);
        areaText1 = findViewById(R.id.area1);
        addressText1 = findViewById(R.id.address1);

        editText1.getEditText().setText(customer_name);
        areaText1.getEditText().setText(customer_pincode);
        addressText1.getEditText().setText(customer_address);


//        String name1 = editText1.getEditText().getText().toString().trim();
//        String area1 = areaText1.getEditText().getText().toString().trim();
//        String address1 = addressText1.getEditText().getText().toString().trim();



        save = findViewById(R.id.btnsavedetails);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editText1.getEditText().getText().toString().trim().isEmpty() && !areaText1.getEditText().getText().toString().trim().isEmpty() && !addressText1.getEditText().getText().toString().trim().isEmpty()){
                    AlertDialog alertDialog = new AlertDialog.Builder(profilec.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("Are you sure you want to save the changes?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                            (dialog, which) -> {
                                Toast.makeText(profilec.this, "Success", Toast.LENGTH_SHORT).show();
                                SharedPreferences.Editor editor = getShared.edit();
                                editor.putString("customer_name",editText1.getEditText().getText().toString().trim());
                                editor.putString("customer_pincode",areaText1.getEditText().getText().toString().trim());
                                editor.putString("customer_address",addressText1.getEditText().getText().toString().trim());
                                editor.apply();
                                postDataUsingVolley(editText1.getEditText().getText().toString().trim(),getShared.getString("customer_phone_number","1234567890"),addressText1.getEditText().getText().toString(),areaText1.getEditText().getText().toString());
                            });
                    //Toast.makeText(profilec.this, editText1.getEditText().getText().toString().trim(), Toast.LENGTH_SHORT).show();
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                            (dialog, which) -> {
                                dialog.dismiss();
                            });
                    alertDialog.show();
                    //postDataUsingVolley(editText1.getEditText().getText().toString().trim(),getShared.getString("customer_phone_number","1234567890"),addressText1.getEditText().getText().toString(),areaText1.getEditText().getText().toString());
                } else {
                    Toast.makeText(profilec.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                }
            }
        });


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.profile);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.home){
            //Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(profilec.this,dashboardc.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if(item.getItemId() == R.id.setting){
            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if(item.getItemId() == R.id.logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(profilec.this);
            builder.setTitle("Logout");
            builder.setMessage("Are you sure you want to logout?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                Toast.makeText(this, "LogOut Succes", Toast.LENGTH_SHORT).show();
                finish();
            });
            builder.setNegativeButton("No", (dialogInterface, i) -> {
                Toast.makeText(this, "LogOut Cancel", Toast.LENGTH_SHORT).show();
            });
            builder.show();
            //Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    private void postDataUsingVolley(String name,String mobile,String customer_address,String customer_pincode) {
        // url to post our data
        String url = "http://meteorrider.socialstuf.com/milkmantra/v1/index_v2.php/create_customer";
        RequestQueue queue = Volley.newRequestQueue(profilec.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject respObj = new JSONObject(response);
                    String error = respObj.getString("error");
                    if(error.equals("false")){
                        Toast.makeText(profilec.this, "Success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(profilec.this, "Fail", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(profilec.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                System.out.println(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("customer_name", name);
                params.put("customer_phone_number", mobile);
                params.put("customer_pincode", customer_pincode);
                params.put("customer_address", customer_address);
                return params;
            }
        };
        queue.add(request);
    }
}