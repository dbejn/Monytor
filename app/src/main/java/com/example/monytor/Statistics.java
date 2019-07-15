package com.example.monytor;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Statistics extends AppCompatActivity {

    private TextView temperature_minimum;
    private TextView temperature_maximum;
    private TextView temperature_average;

    private TextView pressure_minimum;
    private TextView pressure_maximum;
    private TextView pressure_average;

    private TextView humidity_minimum;
    private TextView humidity_maximum;
    private TextView humidity_average;

    private TextView co2_level_minimum;
    private TextView co2_level_maximum;
    private TextView co2_level_average;

    private TextView illumination_minimum;
    private TextView illumination_maximum;
    private TextView illumination_average;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setTitle("Amphitheatre A3, last 24h");

        temperature_minimum = findViewById(R.id.temperature_minimum);
        temperature_minimum.setText(Html.fromHtml(getString(R.string.temperature_minimum)));
        temperature_maximum = findViewById(R.id.temperature_maximum);
        temperature_maximum.setText(Html.fromHtml(getString(R.string.temperature_maximum)));
        temperature_average = findViewById(R.id.temperature_average);
        temperature_average.setText(Html.fromHtml(getString(R.string.temperature_average)));

        pressure_minimum = findViewById(R.id.pressure_minimum);
        pressure_minimum.setText(Html.fromHtml(getString(R.string.pressure_minimum)));
        pressure_maximum = findViewById(R.id.pressure_maximum);
        pressure_maximum.setText(Html.fromHtml(getString(R.string.pressure_maximum)));
        pressure_average = findViewById(R.id.pressure_average);
        pressure_average.setText(Html.fromHtml(getString(R.string.pressure_average)));

        humidity_minimum = findViewById(R.id.humidity_minimum);
        humidity_minimum.setText(Html.fromHtml(getString(R.string.humidity_minimum)));
        humidity_maximum = findViewById(R.id.humidity_maximum);
        humidity_maximum.setText(Html.fromHtml(getString(R.string.humidity_maximum)));
        humidity_average = findViewById(R.id.humidity_average);
        humidity_average.setText(Html.fromHtml(getString(R.string.humidity_average)));

        co2_level_minimum = findViewById(R.id.co2_level_minimum);
        co2_level_minimum.setText(Html.fromHtml(getString(R.string.co2_level_minimum)));
        co2_level_maximum = findViewById(R.id.co2_level_maximum);
        co2_level_maximum.setText(Html.fromHtml(getString(R.string.co2_level_maximum)));
        co2_level_average = findViewById(R.id.co2_level_average);
        co2_level_average.setText(Html.fromHtml(getString(R.string.co2_level_average)));

        illumination_minimum = findViewById(R.id.illumination_minimum);
        illumination_minimum.setText(Html.fromHtml(getString(R.string.illumination_minimum)));
        illumination_maximum = findViewById(R.id.illumination_maximum);
        illumination_maximum.setText(Html.fromHtml(getString(R.string.illumination_maximum)));
        illumination_average = findViewById(R.id.illumination_average);
        illumination_average.setText(Html.fromHtml(getString(R.string.illumination_average)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_statistics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_change_station) {

            AlertDialog.Builder dialog_builder = new AlertDialog.Builder(Statistics.this);

            dialog_builder.setTitle("Choose station");
            dialog_builder.setItems(R.array.statistics, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Toast.makeText(Statistics.this, which + ". station"
                            , Toast.LENGTH_SHORT).show();
                }
            });
            dialog_builder.create().show();
        }

        return super.onOptionsItemSelected(item);
    }
}
