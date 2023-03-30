package com.example.calorietrackerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class ChartActivity extends AppCompatActivity{

    TextView textViewCarbs, textViewProtein, textViewFats;
    PieChart diagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        textViewCarbs = findViewById(R.id.viewCarbs);
        textViewProtein = findViewById(R.id.viewProtein);
        textViewFats = findViewById(R.id.viewFats);
        diagram = findViewById(R.id.dataDiagram);

        setData();
    }

    private void setData() {

        textViewCarbs.setText(Integer.toString(45));
        textViewProtein.setText(Integer.toString(35));
        textViewFats.setText(Integer.toString(20));

        diagram.addPieSlice(
                new PieModel(
                        "Carbs",
                        Integer.parseInt(textViewCarbs.getText().toString()),
                        Color.parseColor("#ADD8E6")));
        diagram.addPieSlice(
                new PieModel(
                        "Protein",
                        Integer.parseInt(textViewProtein.getText().toString()),
                        Color.parseColor("#ff8c00")));
        diagram.addPieSlice(
                new PieModel(
                        "Fats",
                        Integer.parseInt(textViewFats.getText().toString()),
                        Color.parseColor("#FFDB58")));

        diagram.startAnimation();
    }
}
