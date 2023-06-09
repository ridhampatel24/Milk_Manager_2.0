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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import java.util.List;

public class Myadapter extends ArrayAdapter {

    List<String> listname;
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

    public Myadapter(@NonNull Context context, List<String> name) {
        super(context, R.layout.items,name);
        this.listname = name;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.items,parent,false);
        textView = view.findViewById(R.id.milkprovider);
        textView.setText(listname.get(position));
        return view;
    }
}
