package com.example.projetoprtico_controledefinanas;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerformanceChartActivity extends AppCompatActivity {

    private PieChart pieChart;
    private TransactionViewModel transactionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_performance_chart);

        pieChart = findViewById(R.id.pie_chart);
        transactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        transactionViewModel.getAllTransactions().observe(this, this::populatePieChart);

    }

    private void populatePieChart(List<Transaction> transactions) {
        Map<String, Double> categoryTotals = new HashMap<>();

        for (Transaction transaction : transactions) {
            if (transaction.getValue() < 0) {
                String category = transaction.getCategory();
                double value = Math.abs(transaction.getValue());
                categoryTotals.put(category, categoryTotals.getOrDefault(category, 0.0) + value);
            }
        }

        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            entries.add(new PieEntry(entry.getValue().floatValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expenses by Category");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(14f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.setCenterText("Expenses by Category");
        pieChart.setCenterTextSize(16f);
        pieChart.setHoleRadius(45f);

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setTextSize(12f);

        pieChart.invalidate();
    }
}