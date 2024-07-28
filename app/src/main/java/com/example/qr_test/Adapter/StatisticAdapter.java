package com.example.qr_test.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qr_test.Entity.Product;
import com.example.qr_test.R;
import com.example.qr_test.Entity.ResultStatistic;
import com.example.qr_test.api.API;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticAdapter extends ArrayAdapter<ResultStatistic> {
    private static final String TAG = "StatisticAdapter";
    Context myContext;
    int myLayout;
    List<ResultStatistic> statistics;

    public StatisticAdapter(@NonNull Context context, int resource, @NonNull List<ResultStatistic> objects) {
        super(context, resource, objects);
        myContext = context;
        myLayout = resource;
        statistics = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(myLayout, null);

        ImageView img = convertView.findViewById(R.id.imageViewScan);
        setImageView(img, position);

        TextView name = convertView.findViewById(R.id.textViewScanName);
        setName(name, position);

        TextView timesScan = convertView.findViewById(R.id.textViewScanTimes);
        timesScan.setText(statistics.get(position).getTimesScan().toString());

        return convertView;
    }
    public void setImageView(ImageView imageView, int position) {
        API.API.getImageProduct(statistics.get(position).getId()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                byte[] image_data;
                try {
                    image_data = response.body().bytes();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (image_data != null) {
                    // Convert byte array to Bitmap
                    Bitmap bitmap = BitmapFactory.decodeByteArray(image_data, 0, image_data.length);
                    // Set the Bitmap to the ImageView
                    imageView.setImageBitmap(bitmap);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    public void setName(TextView name, int position) {
        API.API.getProduct(statistics.get(position).getId()).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product product = response.body();
                if (product != null) {
                    name.setText(product.getName());
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable throwable) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }
}

