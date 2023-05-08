package com.ridham.milk_man;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class selector extends AppCompatActivity {

    Button btnuser;
    TextView calltext;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_selector);



        ImageView call = findViewById(R.id.image_call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seller(view);
                //Toast.makeText(selector.this, "Call", Toast.LENGTH_SHORT).show();
//                String number = getString(R.string.adminno);
//                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + number));
//                startActivity(intent);
            }
        });

        calltext = findViewById(R.id.test);


        //Temp
        btnuser = findViewById(R.id.milkproviderpage);
        btnuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), milkmanDashboard.class);
//                startActivity(intent);
                String rand = "9924343883";
                postDataUsingVolley(rand);
            }
        });


        Button btnskip = findViewById(R.id.buttonskip);
        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skip(view);
            }
        });
    }

    public void skip(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(selector.this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to Buy Milk? \n\nIf you are a Milk Provider, Please click on 'No'");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            Toast.makeText(this, "Buyer Page", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),registeruser.class);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            Toast.makeText(this, "Selector page", Toast.LENGTH_SHORT).show();
        });
        builder.show();
    }

    public void seller(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(selector.this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to Seller Milk? \n\nIf you are a Buyer, Please click on 'No'");
        builder.setPositiveButton("Call Admin", (dialogInterface, i) -> {
            Toast.makeText(this, "Seller Page", Toast.LENGTH_SHORT).show();
            String number = getString(R.string.adminno);
            Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + number));
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
            Toast.makeText(this, "Selector page", Toast.LENGTH_SHORT).show();
        });
        builder.show();

    }


    private void postDataUsingVolley(String mobile) {
        // url to post our data
        String url = "http://meteorrider.socialstuf.com/milkmantra/v1/index_v2.php/IsUserExsists";
        //loadingPB.setVisibility(View.VISIBLE);

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(selector.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject respObj = new JSONObject(response);
                    String usertype = respObj.getString("user_type");

                    if(usertype.equals("0")){
                        //Selector Page
                        Toast.makeText(selector.this, "User does not exist", Toast.LENGTH_SHORT).show();
                    } else if(usertype.equals("2")){
                        //Customer Page (Buyer Page
                        Toast.makeText(selector.this, "User is Customer", Toast.LENGTH_SHORT).show();
                    } else{
                        //Provider Page (Seller Page)
                        Toast.makeText(selector.this, "User is Provider", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(selector.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                System.out.println(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("phone_number", mobile);
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }





//    private void postDataUsingVolley() {
//        // url to post our data
//        String url = "http://meteorrider.socialstuf.com/milkmantra/v1/index_v2.php/create_customer";
//        //loadingPB.setVisibility(View.VISIBLE);
//
//        // creating a new variable for our request queue
//        RequestQueue queue = Volley.newRequestQueue(selector.this);
//        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                try {
//                    // on below line we are parsing the response
//                    // to json object to extract data from it.
//                    JSONObject respObj = new JSONObject(response);
//
//                    // below are the strings which we
//                    // extract from our json object.
//                    boolean error = respObj.getBoolean("error");
//
//                    if (error == false) {
//                        // if the error is true we are displaying
//                        JSONObject userObject = respObj.getJSONObject("user");
//                        String id = userObject.getString("customer_id");
//                        Toast.makeText(selector.this,id, Toast.LENGTH_SHORT).show();
//                    } else {
//                        // if the error is false we are displaying success message.
//                        Toast.makeText(selector.this, "Data added to API", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // method to handle errors.
//                calltext.setText("Fail to get response = " + error);
//                Toast.makeText(selector.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
//                System.out.println(error.getMessage());
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                // below line we are creating a map for
//                // storing our values in key and value pair.
//                Map<String, String> params = new HashMap<String, String>();
//
//                // on below line we are passing our key
//                // and value pair to our parameters.
//                params.put("customer_name", "Honey");
//                params.put("customer_phone_number", "8320123423");
//                params.put("customer_address", "Modasa");
//                params.put("customer_pincode", "383315");
//
//                // at last we are
//                // returning our params.
//                return params;
//            }
//        };
//        // below line is to make
//        // a json object request.
//        queue.add(request);
//    }


}