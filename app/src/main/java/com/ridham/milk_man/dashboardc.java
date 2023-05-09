package com.ridham.milk_man;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.navigation.ui.AppBarConfiguration;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dashboardc extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static final int PICK_CONTACT=1;
    DrawerLayout drawerLayout;
    List<customersprovider> customersproviders;
    private AppBarConfiguration mAppBarConfiguration;
    NavigationView navigationView;
    SharedPreferences sharedPreferences;
    Toolbar toolbar;
    TextView customerid;
    String number = "";
    ListView listView;
    String name;
    TextView textView;

    String provider_number_selected;
    //API
    //String mobile = "9924343883";
    //String id = "3";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboardc);
        //Ask for contacts permission if not given
        if(ContextCompat.checkSelfPermission(dashboardc.this, android.Manifest.permission.READ_CONTACTS)!= getPackageManager().PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(dashboardc.this,new String[]{android.Manifest.permission.READ_CONTACTS},1);
        }
        sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        textView = findViewById(R.id.number);
        customerid = findViewById(R.id.customerid);
        customerid.setText(sharedPreferences.getString("customer_id","0"));
        Button btn = findViewById(R.id.getnumber);

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


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, PICK_CONTACT);
                //textView.setText(number);
            }
        });


        listView = findViewById(R.id.listview);
        customersproviders = new ArrayList<>();
        postDataUsingVolleygetproviders();

        Myadapter myadapter = new Myadapter(this,customersproviders);
        listView.setAdapter(myadapter);
    }


    //Back key press


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }

    }

    @SuppressLint("Range")
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c =  managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {

                        String id =c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                        @SuppressLint("Range") String hasPhone =c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,
                                    null, null);
                            phones.moveToFirst();

                            String cname = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            String cNumber = phones.getString(phones.getColumnIndex("data1"));
                            number = cNumber;
                            name = cname;
                            //Select last ten digits of the number
                            cNumber = cNumber.substring(cNumber.length() - 10);
                            textView.setText(cNumber);
                            provider_number_selected = cNumber;
                            //System.out.println("number is:"+cNumber);
                        }
                    }
                }
                break;
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.profile){
            Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(dashboardc.this,profilec.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if(item.getItemId() == R.id.setting){
            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if(item.getItemId() == R.id.logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(dashboardc.this);
            builder.setTitle("Logout");
            builder.setMessage("Are you sure you want to logout?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                Toast.makeText(this, "LogOut Succes", Toast.LENGTH_SHORT).show();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(dashboardc.this,entermobile.class);
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
        return true;
    }


    private void postDataUsingVolleygetproviders() {
        // url to post our data
        String url = "http://meteorrider.socialstuf.com/milkmantra/v1/index_v2.php/get_provider_details";

        RequestQueue queue = Volley.newRequestQueue(dashboardc.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            Log.v("Resposnse","##" + response);
                try {
                    JSONObject respObj = new JSONObject(response);
                    JSONArray randomUsersArray = respObj.getJSONArray("random_users");
                    for(int i = 0;i < randomUsersArray.length();i++) {
                        JSONObject userObject = randomUsersArray.getJSONObject(i);
                        customersprovider temp = new customersprovider();

                        temp.customer_id = userObject.getString("customer_id");
                        temp.customer_vacation_mode = userObject.getString("customer_vacation_mode");
                        temp.customer_morning_cow_volume = userObject.getString("customer_morning_cow_volume");
                        temp.customer_morning_buffelo_volume = userObject.getString("customer_morning_buffelo_volume");
                        temp.customer_morning_other_volume = userObject.getString("customer_morning_other_volume");
                        temp.customer_evening_cow_volume = userObject.getString("customer_evening_cow_volume");
                        temp.customer_evening_buffelo_volume = userObject.getString("customer_evening_buffelo_volume");
                        temp.customer_evening_other_volume = userObject.getString("customer_evening_other_volume");
                        temp.customer_provider_is_active = userObject.getString("customer_provider_is_active");
                        temp.customer_provider_timestamp = userObject.getString("customer_provider_timestamp");
                        temp.provider_name = userObject.getString("provider_name");
                        temp.provider_id = userObject.getString("provider_id");
                        temp.provider_vacation_mode = userObject.getString("provider_vacation_mode");
                        temp.provider_customer_associated = userObject.getString("provider_customer_associated");

                        customersproviders.add(temp);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(dashboardc.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                System.out.println("Error:"+error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                String temp = "";
                params.put("provider_phone_number", "");
                //params.put("customer_id", sharedPreferences.getString("customer_id","0"));
                params.put("customer_id", "3");
                return params;
            }
        };
        queue.add(request);
    }



}