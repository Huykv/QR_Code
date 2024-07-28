package com.example.qr_test.ActivityStatistic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qr_test.ActivityQRGenerate.GenerateQR;
import com.example.qr_test.Adapter.StatisticAdapter;
import com.example.qr_test.Entity.ResultStatistic;
import com.example.qr_test.ActivityListProduct.ListTypeProductActivity;
import com.example.qr_test.MainActivity;
import com.example.qr_test.R;
import com.example.qr_test.api.API;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Statistic extends AppCompatActivity {
    private static final String TAG = "Statistic";
    private TextView generateQR, listTypeProduct, logOut;
    private ListView lvStatic;
    private List<ResultStatistic> statistics;
    private Button btnMonth, btnYear;
    private EditText edtMonth, edtYear;
    private StatisticAdapter statisticAdapter;
    private int totalCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        initUI();
        getStatistics(2024);


        btnMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int month, year;
                if (edtMonth.getText().toString().equals("")) {
                    Toast.makeText(Statistic.this, "Hãy nhập tháng", Toast.LENGTH_SHORT).show();
                } else if (edtYear.getText().toString().equals("")) {
                    Toast.makeText(Statistic.this, "Hãy nhập năm", Toast.LENGTH_SHORT).show();
                } else {
                    month = Integer.parseInt(edtMonth.getText().toString());
                    year = Integer.parseInt(edtYear.getText().toString());
                    if (month < 1 || month > 12) {
                        Toast.makeText(Statistic.this, "Tháng không phù hợp", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    API.API.getTimesScan(year, month).enqueue(new Callback<Map<Long, Integer>>() {
                        @Override
                        public void onResponse(Call<Map<Long, Integer>> call, Response<Map<Long, Integer>> response) {
                            Map<Long, Integer> m = response.body();
                            List<Map.Entry<Long, Integer>> list = new ArrayList<>(m.entrySet());

                            // Sắp xếp danh sách theo giá trị giảm dần
                            list.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

                            // Tạo một LinkedHashMap từ danh sách đã sắp xếp để duy trì thứ tự
                            Map<Long, Integer> sortedMap = new LinkedHashMap<>();
                            for (Map.Entry<Long, Integer> entry : list) {
                                sortedMap.put(entry.getKey(), entry.getValue());
                            }
                            m = sortedMap;
                            Set<Long> keys = m.keySet();
                            statistics = new ArrayList<>();
                            for (Long k:keys) {
                                statistics.add(new ResultStatistic(k, m.get(k)));
                            }
                            statisticAdapter = new StatisticAdapter(
                                    Statistic.this,
                                    R.layout.line_scan,
                                    statistics
                            );
                            lvStatic.setAdapter(statisticAdapter);

                            for (Integer value: m.values()) {
                                totalCount += value;
                            }
                            Log.d(TAG, String.valueOf(totalCount));
                        }

                        @Override
                        public void onFailure(Call<Map<Long, Integer>> call, Throwable throwable) {
                            Log.d(TAG, "onFailure: ");
                        }
                    });
                }
            }
        });

        btnYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year;
                if (edtYear.getText().toString().equals("")) {
                    Toast.makeText(Statistic.this, "Hãy nhập năm", Toast.LENGTH_SHORT).show();
                    return;
                }
                year = Integer.parseInt(edtYear.getText().toString());
                API.API.getTimesScan(year).enqueue(new Callback<Map<Long, Integer>>() {
                    @Override
                    public void onResponse(Call<Map<Long, Integer>> call, Response<Map<Long, Integer>> response) {
                        Map<Long, Integer> m = response.body();
                        List<Map.Entry<Long, Integer>> list = new ArrayList<>(m.entrySet());

                        // Sắp xếp danh sách theo giá trị giảm dần
                        list.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

                        // Tạo một LinkedHashMap từ danh sách đã sắp xếp để duy trì thứ tự
                        Map<Long, Integer> sortedMap = new LinkedHashMap<>();
                        for (Map.Entry<Long, Integer> entry : list) {
                            sortedMap.put(entry.getKey(), entry.getValue());
                        }
                        m = sortedMap;
                        Set<Long> keys = m.keySet();
                        statistics = new ArrayList<>();
                        for (Long k:keys) {
                            statistics.add(new ResultStatistic(k, m.get(k)));
                        }
                        statisticAdapter = new StatisticAdapter(
                                Statistic.this,
                                R.layout.line_scan,
                                statistics
                        );
                        lvStatic.setAdapter(statisticAdapter);

                        for (Integer value: m.values()) {
                            totalCount += value;
                        }
                        Log.d(TAG, String.valueOf(totalCount));
                    }

                    @Override
                    public void onFailure(Call<Map<Long, Integer>> call, Throwable throwable) {
                        Log.d(TAG, "onFailure: ");
                    }
                });
            }
        });

        generateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Statistic.this, GenerateQR.class);
                startActivity(intent);
                finish();
            }
        });

        listTypeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Statistic.this, ListTypeProductActivity.class);
                startActivity(intent);
                finish();
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Statistic.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void getStatistics(int year) {
        API.API.getTimesScan(year).enqueue(new Callback<Map<Long, Integer>>() {
            @Override
            public void onResponse(Call<Map<Long, Integer>> call, Response<Map<Long, Integer>> response) {
                Map<Long, Integer> map = response.body();
                List<Map.Entry<Long, Integer>> list = new ArrayList<>(map.entrySet());

                // Sắp xếp danh sách theo giá trị giảm dần
                list.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

                // Tạo một LinkedHashMap từ danh sách đã sắp xếp để duy trì thứ tự
                Map<Long, Integer> sortedMap = new LinkedHashMap<>();
                for (Map.Entry<Long, Integer> entry : list) {
                    sortedMap.put(entry.getKey(), entry.getValue());
                }
                map = sortedMap;
                Set<Long> keys = map.keySet();
                statistics = new ArrayList<>();
                for (Long k:keys) {
                    statistics.add(new ResultStatistic(k, map.get(k)));
                }
                statisticAdapter = new StatisticAdapter(
                        Statistic.this,
                        R.layout.line_scan,
                        statistics
                );
                lvStatic.setAdapter(statisticAdapter);

                for (Integer value: map.values()) {
                    totalCount += value;
                }
                Log.d(TAG, String.valueOf(totalCount));
            }

            @Override
            public void onFailure(Call<Map<Long, Integer>> call, Throwable throwable) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    public void initUI() {
        lvStatic = findViewById(R.id.listViewScan);
        generateQR = findViewById(R.id.tao_ma);
        listTypeProduct = findViewById(R.id.list);
        logOut = findViewById(R.id.logout);
        btnMonth = findViewById(R.id.buttonMonth);
        btnYear = findViewById(R.id.buttonYear);
        edtMonth = findViewById(R.id.editTextMonth);
        edtYear = findViewById(R.id.editTextYear);
    }
}
