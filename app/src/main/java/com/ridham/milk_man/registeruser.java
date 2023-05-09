package com.ridham.milk_man;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class registeruser extends AppCompatActivity {

    Boolean registeredUser = false;
    TextInputLayout editText1;
    SharedPreferences shrd;
    TextInputLayout areaText1;
    TextInputLayout addressText1;
    TextInputLayout pincodeText1;
    String userid;
    String mobile;
    String name;
    String area;
    String address;
    String pincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registeruser);

        editText1 = findViewById(R.id.full_name1);
        areaText1 = findViewById(R.id.area1);
        addressText1 = findViewById(R.id.address1);
        pincodeText1 = findViewById(R.id.pincode1);
        shrd = getSharedPreferences("user",MODE_PRIVATE);
        mobile = shrd.getString("user_phone_number","1234567809");


        EditText editText = editText1.getEditText();
        EditText areaText = areaText1.getEditText();
        EditText addressText = addressText1.getEditText();
        EditText pincodeText = pincodeText1.getEditText();


        Button btn = findViewById(R.id.btnregister);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = editText.getText().toString().trim();
                area = areaText.getText().toString().trim();
                address = addressText.getText().toString().trim();
                pincode = pincodeText.getText().toString().trim();


                if(!name.isEmpty()){
                    if(!area.isEmpty()){
                        if(!address.isEmpty()){
                            if(!pincode.isEmpty()){
                                AlertDialog.Builder builder = new AlertDialog.Builder(registeruser.this);
                                builder.setTitle("Register User");
                                builder.setMessage("Please Confirm your Details? \nName: "+name+" \nArea: "+area+"\nAddress: "+address+"\nPincode: "+pincode);
                                builder.setPositiveButton("OK", (dialogInterface, i) -> {
                                    postDataUsingVolley(name, mobile, address, pincode);
                                    Intent intent = new Intent(getApplicationContext(), dashboardc.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                });
                                builder.setNegativeButton("No", (dialogInterface, i) -> {
                                    dialogInterface.dismiss();
                                });
                                builder.show();

                            } else{
                                Toast.makeText(registeruser.this, "Enter Pincode", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(registeruser.this, "Enter Address", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(registeruser.this, "Enter Area", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(registeruser.this, "Enter Name", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void postDataUsingVolley(String name,String mobile,String customer_address,String customer_pincode) {
        // url to post our data
        String url = "http://meteorrider.socialstuf.com/milkmantra/v1/index_v2.php/create_customer";
        RequestQueue queue = Volley.newRequestQueue(registeruser.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject respObj = new JSONObject(response);
                    String error = respObj.getString("error");
                    if(error.equals("false")){
                        Toast.makeText(registeruser.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = shrd.edit();
                        JSONObject userObject = respObj.getJSONObject("user");
                        String customerId = userObject.getString("customer_id");
                        String customerName = userObject.getString("customer_name");
                        String customerPhoneNumber = userObject.getString("customer_phone_number");
                        String customerAddress = userObject.getString("customer_address");
                        String customerPincode = userObject.getString("customer_pincode");
                        String customerIsActive = userObject.getString("customer_is_active");
                        editor.putString("customer_id",customerId);
                        editor.putString("customer_name",customerName);
                        editor.putString("customer_phone_number",customerPhoneNumber);
                        editor.putString("customer_address",customerAddress);
                        editor.putString("customer_pincode",customerPincode);
                        editor.putString("customer_is_active",customerIsActive);
                        editor.putInt("user_type",2);
                        editor.apply();
                    } else{
                        Toast.makeText(registeruser.this, "Check Your Internet", Toast.LENGTH_SHORT).show();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(registeruser.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
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


