package com.ridham.milk_man;
//change line 341 during final deployment
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class verifyotp extends AppCompatActivity {

    EditText inputnumber1,inputnumber2,inputnumber3, inputnumber4, inputnumber5, inputnumber6;

    String getotpbackend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_verifyotp);

        

        final Button verifybuttonclick = findViewById(R.id.buttonverifyopt);

        inputnumber1 = findViewById(R.id.inputotp1);
        inputnumber2 = findViewById(R.id.inputotp2);
        inputnumber3 = findViewById(R.id.inputotp3);
        inputnumber4 = findViewById(R.id.inputotp4);
        inputnumber5 = findViewById(R.id.inputotp5);
        inputnumber6 = findViewById(R.id.inputotp6);

        TextView textView = findViewById(R.id.textmobileshownumber);
        textView.setText(String.format(
                "+91-%s",getIntent().getStringExtra("mobile")
        ));

        SharedPreferences shrd = getSharedPreferences("user",MODE_PRIVATE);
        SharedPreferences.Editor editor = shrd.edit();
        editor.putString("mobile",getIntent().getStringExtra("mobile"));
        editor.apply();

//        SharedPreferences getShared = getSharedPreferences("user",MODE_PRIVATE);
//        String mobile = getShared.getString("mobile","1234567890");
//        Toast.makeText(this, mobile, Toast.LENGTH_SHORT).show();

        getotpbackend = getIntent().getStringExtra("backendotp");
        final ProgressBar progressBarverify = findViewById(R.id.progressbar_verify_otp);

        verifybuttonclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inputnumber1.getText().toString().trim().isEmpty() && !inputnumber2.getText().toString().trim().isEmpty() && !inputnumber3.getText().toString().trim().isEmpty() && !inputnumber4.getText().toString().trim().isEmpty() && !inputnumber5.getText().toString().trim().isEmpty() && !inputnumber6.getText().toString().trim().isEmpty()){
                    String entercodeotp = inputnumber1.getText().toString() +
                            inputnumber2.getText().toString() +
                            inputnumber3.getText().toString() +
                            inputnumber4.getText().toString() +
                            inputnumber5.getText().toString() +
                            inputnumber6.getText().toString();
                    if(getotpbackend != null){
                        progressBarverify.setVisibility(View.VISIBLE);
                        verifybuttonclick.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(getotpbackend, entercodeotp);
                        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBarverify.setVisibility(View.GONE);
                                verifybuttonclick.setVisibility(View.VISIBLE);

                                if(task.isSuccessful()){
                                    postDataUsingVolley(getIntent().getStringExtra("mobile"));
//                                    Intent intent = new Intent(getApplicationContext(),selector.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(intent);
                                } else{
                                    Toast.makeText(verifyotp.this, "Enter the correct OTP", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(verifyotp.this, "Please check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(verifyotp.this, "Verify_OTP", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(verifyotp.this, "Please enter all numbers", Toast.LENGTH_SHORT).show();
                }
            }
        });


        numberotpmove();

        findViewById(R.id.textresendotp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"),
                        60,
                        TimeUnit.SECONDS,
                        verifyotp.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(verifyotp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newbackentotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                getotpbackend = newbackentotp;
                                Toast.makeText(verifyotp.this, "OTP sent succesfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });



    }

    private void numberotpmove() {
        inputnumber1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputnumber2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputnumber3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputnumber4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        inputnumber5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()){
                    inputnumber6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void postDataUsingVolley(String mobile) {
        // url to post our data
        String url = "http://meteorrider.socialstuf.com/milkmantra/v1/index_v2.php/IsUserExsists";

        RequestQueue queue = Volley.newRequestQueue(verifyotp.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject respObj = new JSONObject(response);
                    String usertype = respObj.getString("user_type");
                    SharedPreferences shrd = getSharedPreferences("user",MODE_PRIVATE);
                    SharedPreferences.Editor editor = shrd.edit();
                    if(usertype.equals("0")){
                        //Selector Page
                        editor.putString("user_type","0");
                        editor.putString("user_phone_number",getIntent().getStringExtra("mobile"));
                        editor.apply();
                        Toast.makeText(verifyotp.this, "User does not exist", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),selector.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else if(usertype.equals("2")){
                        //Customer Page (Buyer Page)
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
                        Toast.makeText(verifyotp.this, "User is Customer", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),dashboardc.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else{
                        //Provider Page (Seller Page)
                        JSONObject userObject = respObj.getJSONObject("user");
                        String providerId = userObject.getString("provider_id");
                        String providerName = userObject.getString("provider_name");
                        String providerPhoneNumber = userObject.getString("provider_phone_number");
                        String providerAddress = userObject.getString("provider_address");
                        String providerPincode = userObject.getString("provider_pincode");
                        String providerIsActive = userObject.getString("provider_is_active");
                        editor.putInt("user_type",1);
                        editor.putString("provider_id",providerId);
                        editor.putString("provider_name",providerName);
                        editor.putString("provider_phone_number",providerPhoneNumber);
                        editor.putString("provider_address",providerAddress);
                        editor.putString("provider_pincode",providerPincode);
                        editor.putString("provider_is_active",providerIsActive);
                        editor.apply();
                        Toast.makeText(verifyotp.this, "User is Provider", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),selector.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(verifyotp.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                System.out.println(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                params.put("phone_number", mobile);
                //params.put("phone_number", "873643444");
                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make.
        // a json object request.
        queue.add(request);
    }
}