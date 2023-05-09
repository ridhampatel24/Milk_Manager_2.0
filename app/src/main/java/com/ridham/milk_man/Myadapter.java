package com.ridham.milk_man;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Myadapter extends ArrayAdapter {

    List<String> listname;
    List<customersprovider> list;
    Context context;
    TextView textView;
    SwitchCompat switchCompat;
    ImageView call;
    ImageView delete;
    EditText mc;
    EditText mb;
    EditText mo;
    EditText ec;
    EditText eb;
    EditText eo;
    Button save;
    Button report;
    Button payment;

    public Myadapter(@NonNull Context context, List<customersprovider> list) {
        super(context, R.layout.items,list);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.items,parent,false);
        customersprovider temp = list.get(position);
        textView = view.findViewById(R.id.milkprovider);
        textView.setText(temp.provider_name);
        switchCompat = view.findViewById(R.id.vacationmode);
        if(temp.provider_vacation_mode.equals("1"))
            switchCompat.setChecked(true);
        else
            switchCompat.setChecked(false);
        call = view.findViewById(R.id.call);
        delete = view.findViewById(R.id.delete);
        mc = view.findViewById(R.id.mc);
        mc.setText(temp.customer_morning_cow_volume);
        mb = view.findViewById(R.id.mb);
        mb.setText(temp.customer_morning_buffelo_volume);
        mo = view.findViewById(R.id.mo);
        mo.setText(temp.customer_morning_other_volume);
        ec = view.findViewById(R.id.ec);
        ec.setText(temp.customer_evening_cow_volume);
        eb = view.findViewById(R.id.eb);
        eb.setText(temp.customer_evening_buffelo_volume);
        eo = view.findViewById(R.id.eo);
        eo.setText(temp.customer_evening_other_volume);
        save = view.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String switch1 = switchCompat.isChecked()?"1":"0";
                String mc1 = mc.getText().toString();
                String mb1 = mb.getText().toString();
                String mo1 = mo.getText().toString();
                String ec1 = ec.getText().toString();
                String eb1 = eb.getText().toString();
                String eo1 = eo.getText().toString();
                String customer_id = temp.customer_id;
                String provider_id = temp.provider_id;
                postDataUsingVolley(customer_id,provider_id,switch1,mc1,mb1,mo1,ec1,eb1,eo1);

            }
        });
        report = view.findViewById(R.id.report);
        payment = view.findViewById(R.id.payment);



        return view;
    }

    private void postDataUsingVolley(String customer_id, String provider_id,String switch1, String mc1, String mb1, String mo1, String ec1, String eb1, String eo1) {
        // url to post our data
        String url = "http://meteorrider.socialstuf.com/milkmantra/v1/index_v2.php/update_customer_provider_association_with_milk_details";

        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject respObj = new JSONObject(response);

                        if (!respObj.getString("error").equals("true")) {
                            Toast.makeText(context.getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context.getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(context.getApplicationContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
                System.out.println(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("provider_id", provider_id);
                params.put("customer_id", customer_id);
                params.put("customer_vacation_mode", switch1);
                params.put("customer_morning_cow_volume", mc1);
                params.put("customer_morning_buffelo_volume", mb1);
                params.put("customer_morning_other_volume", mo1);
                params.put("customer_evening_cow_volume", ec1);
                params.put("customer_evening_buffelo_volume", eb1);
                params.put("customer_evening_other_volume", eo1);
                return params;
            }
        };
        queue.add(request);
    }


}
