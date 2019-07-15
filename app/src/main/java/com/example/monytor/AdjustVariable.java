package com.example.monytor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AdjustVariable extends AppCompatActivity {

    private TextView adjust_info_label;
    private TextView adjust_info_date_label;
    private EditText adjust_desired_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_variable);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Amphitheatre A3, adjust temperature");

        adjust_info_label = findViewById(R.id.adjust_info_label);
        adjust_info_label.setText(Html.fromHtml(getString(R.string.adjust_info, "temperature", "Amphitheatre A3", "28.59", "\u2103")));

        adjust_info_date_label = findViewById(R.id.adjust_info_date_label);
        adjust_info_date_label.setText(getString(R.string.adjust_info_date, "21.06. 14:37h"));

        adjust_desired_value = findViewById(R.id.adjust_desired_value);
        adjust_desired_value.setText("28.59");
    }
}
