package com.example.qr_test.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qr_test.Entity.TypeProduct;
import com.example.qr_test.R;

import java.util.ArrayList;

public class TypeProductAdapter extends ArrayAdapter<TypeProduct> {
    Activity myContext;
    int myLayout;
    ArrayList<TypeProduct> typeProductList;

    public TypeProductAdapter(Activity context, int layout, ArrayList<TypeProduct> typeProductList) {
        super(context, layout, typeProductList);
        this.myContext = context;
        this.myLayout = layout;
        this.typeProductList = typeProductList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = myContext.getLayoutInflater();

        convertView = inflater.inflate(myLayout, null);

        TextView txtName = convertView.findViewById(R.id.textViewNameTP);
        txtName.setText(typeProductList.get(position).getName());

        ImageView imageView = convertView.findViewById(R.id.imageViewP);
        imageView.setImageResource(typeProductList.get(position).getImage());

        return convertView;
    }
}
