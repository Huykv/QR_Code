package com.example.qr_test.api;

import com.example.qr_test.Entity.Beverage;
import com.example.qr_test.Entity.DryFood;
import com.example.qr_test.Entity.IceCream;
import com.example.qr_test.Entity.Milk;
import com.example.qr_test.Entity.Product;
import com.example.qr_test.Entity.Snack;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    // Nhập IPv4 address wifi
    public static final String URL = "http://192.168.1.2:8080/";
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    API API = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(API.class);

    @GET("api/v1/products/{id}")
    Call<Product> getProduct(@Path("id") Long id);

    @GET("api/v1/products/types")
    Call<List<String>> getTypeProducts();

    @GET("api/v1/products/type/{type}")
    Call<List<Product>> getProducts(@Path("type") String type);

    @GET("api/v1/images/{product_id}")
    Call<ResponseBody> getImageProduct(@Path("product_id") Long product_id);

    @GET("api/v1/images")
    Call<String> getImageProduct1(@Query("product_id") Long product_id);


    @GET("api/v1/history_scans/year")
    Call<Map<Long, Integer>> getTimesScan(@Query("year") Integer year);

    @GET("api/v1/history_scans/month")
    Call<Map<Long, Integer>> getTimesScan(@Query("year") Integer year, @Query("month") Integer month);

    @GET("api/v1/products/next_id")
    Call<Long> getNextIdProduct();

    @POST("api/v1/beverages")
    Call<Beverage> createBeverage(@Body Beverage product);

    @POST("api/v1/dry_foods")
    Call<DryFood> createDryFood(@Body DryFood product);

    @POST("api/v1/ice_creams")
    Call<IceCream> createIceCream(@Body IceCream product);

    @POST("api/v1/milks")
    Call<Milk> createMilk(@Body Milk product);

    @POST("api/v1/snacks")
    Call<Snack> createSnack(@Body Snack product);

    @POST("api/v1/history_scans/{id}")
    Call<Long> scanQR(@Path("id") Long id);

    @DELETE("api/v1/products/{id}")
    Call<Product> deleteProduct(@Path("id") Long id);

    @PUT("api/v1/beverages/{id}")
    Call<Product> updateBeverage(@Path("id") Long id, @Body Product product);

    @PUT("api/v1/dry_foods/{id}")
    Call<Product> updateDryFood(@Path("id") Long id, @Body Product product);

    @PUT("api/v1/ice_creams/{id}")
    Call<Product> updateIceCream(@Path("id") Long id, @Body Product product);

    @PUT("api/v1/milks/{id}")
    Call<Product> updateMilk(@Path("id") Long id, @Body Product product);

    @PUT("api/v1/snacks/{id}")
    Call<Product> updateSnack(@Path("id") Long id, @Body Product product);

    @Multipart
    @POST("api/v1/images/{id}")
    Call<String> uploadImage(@Part MultipartBody.Part img, @Path("id") Long id);

}
