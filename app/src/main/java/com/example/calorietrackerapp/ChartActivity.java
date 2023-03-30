package com.example.calorietrackerapp;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import android.content.Intent;
import android.view.View;

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

        Button goToHome = findViewById(R.id.back_to_home);
        goToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChartActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
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
