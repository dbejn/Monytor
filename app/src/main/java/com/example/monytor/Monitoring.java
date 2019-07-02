package com.example.monytor;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Monitoring extends AppCompatActivity {

    private CheckBox temperature_checkbox;
    private CheckBox humidity_checkbox;
    private CheckBox co2_level_checkbox;
    private CheckBox illumination_checkbox;

    private CardView layout_temperature_chart;
    private CardView layout_humidity_chart;
    private CardView layout_co2_level_chart;
    private CardView layout_illumination_chart;

    private LineChart chart_temperature;
    private LineChart chart_humidity;
    private LineChart chart_co2_level;
    private LineChart chart_illumination;

    private LinearLayout layout_legend;
    private boolean legend_visible;
    private ImageButton button_show_legend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setCheckBoxes();

        layout_legend = findViewById(R.id.layout_legend);
        legend_visible = true;
        button_show_legend = findViewById(R.id.button_show_legend);
        button_show_legend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (legend_visible) {

                    legend_visible = false;
                    layout_legend.setVisibility(View.GONE);
                    button_show_legend.setImageResource(R.drawable.ic_arrow_up);
                }
                else {

                    legend_visible = true;
                    layout_legend.setVisibility(View.VISIBLE);
                    button_show_legend.setImageResource(R.drawable.ic_arrow_down);
                }
            }
        });

        layout_temperature_chart = findViewById(R.id.layout_temperature_chart);
        layout_humidity_chart = findViewById(R.id.layout_humidity_chart);
        layout_co2_level_chart = findViewById(R.id.layout_co2_level_chart);
        layout_illumination_chart = findViewById(R.id.layout_illumination_chart);

        chart_temperature = findViewById(R.id.chart_temerature);
        chart_humidity = findViewById(R.id.chart_humidity);
        chart_co2_level = findViewById(R.id.chart_co2_level);
        chart_illumination = findViewById(R.id.chart_illumination);

        setTemperatureChart();
        setHumidityChart();
        setCO2LevelChart();
        setIlluminationChart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_monitoring, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_check_time_span) {

            Toast.makeText(getApplicationContext(), "check time span...",
                    Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.action_check_average) {

            Toast.makeText(getApplicationContext(), "check average...",
                    Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setCheckBoxes() {

        temperature_checkbox = findViewById(R.id.checkbox_temperature);
        temperature_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    layout_temperature_chart.setVisibility(View.VISIBLE);
                }
                else {

                    layout_temperature_chart.setVisibility(View.GONE);
                }
            }
        });

        humidity_checkbox = findViewById(R.id.checkbox_humidity);
        humidity_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    layout_humidity_chart.setVisibility(View.VISIBLE);
                }
                else {

                    layout_humidity_chart.setVisibility(View.GONE);
                }
            }
        });

        co2_level_checkbox = findViewById(R.id.checkbox_co2_level);
        co2_level_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    layout_co2_level_chart.setVisibility(View.VISIBLE);
                }
                else {

                    layout_co2_level_chart.setVisibility(View.GONE);
                }
            }
        });

        illumination_checkbox = findViewById(R.id.checkbox_illumination);
        illumination_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    layout_illumination_chart.setVisibility(View.VISIBLE);
                }
                else {

                    layout_illumination_chart.setVisibility(View.GONE);
                }
            }
        });
    }

    private List<Float> getTempDay() {

        List<Float> values = new ArrayList<Float>();
        Random random = new Random();

        for (int i = 0; i < 72; i++) {

            values.add(25.0f + random.nextFloat() * 5.0f);
        }

        return values;
    }

    private void setTemperatureChart() {

        // getTemperatureValues();

        List<Entry> temperature_values = new ArrayList<Entry>();

        List<Float> values = getTempDay();
        for (int i = 0; i < values.size(); i++) {

            Entry entry = new Entry(i, values.get(i));
            temperature_values.add(entry);
        }

        LineDataSet temperature_data_set = new LineDataSet(temperature_values, "legend");
        temperature_data_set.setAxisDependency(YAxis.AxisDependency.LEFT);
        temperature_data_set.setColor(Color.rgb(143, 0, 0));
        temperature_data_set.setCircleColor(Color.rgb(143, 0, 0));
        temperature_data_set.setCircleHoleColor(Color.rgb(143, 0, 0));
        temperature_data_set.setValueTextSize(10);
        temperature_data_set.setCircleRadius(5);
        temperature_data_set.setLineWidth(2);
        temperature_data_set.setHighlightLineWidth(2);
        temperature_data_set.setValueTypeface(Typeface.DEFAULT_BOLD);
        temperature_data_set.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        List<ILineDataSet> temperature_line_data_set = new ArrayList<ILineDataSet>();
        temperature_line_data_set.add(temperature_data_set);

        LineData temperature_data = new LineData(temperature_line_data_set);
        chart_temperature.setData(temperature_data);
        chart_temperature.setDescription(null);
        chart_temperature.getAxisLeft().setDrawLabels(false);
        chart_temperature.getAxisRight().setDrawLabels(false);
        chart_temperature.getLegend().setEnabled(false);
        XAxis x_axis = chart_temperature.getXAxis();
        x_axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart_temperature.zoom(72.0f / 8.0f + 0.5f, 1.0f, 0, 0);
        chart_temperature.moveViewToX(100);
        chart_temperature.invalidate();
    }

    private void setHumidityChart() {

        // getHumidityValues();

        List<Entry> humidity_values = new ArrayList<Entry>();

        List<Float> values = getTempDay();
        for (int i = 0; i < values.size(); i++) {

            Entry entry = new Entry(i, values.get(i));
            humidity_values.add(entry);
        }

        LineDataSet humidity_data_set = new LineDataSet(humidity_values, "legend");
        humidity_data_set.setAxisDependency(YAxis.AxisDependency.LEFT);
        humidity_data_set.setColor(Color.rgb(5, 148, 153));
        humidity_data_set.setCircleColor(Color.rgb(5, 148, 153));
        humidity_data_set.setCircleHoleColor(Color.rgb(5, 148, 153));
        humidity_data_set.setValueTextSize(10);
        humidity_data_set.setCircleRadius(5);
        humidity_data_set.setLineWidth(2);
        humidity_data_set.setHighlightLineWidth(2);
        humidity_data_set.setValueTypeface(Typeface.DEFAULT_BOLD);
        humidity_data_set.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        List<ILineDataSet> humidity_line_data_set = new ArrayList<ILineDataSet>();
        humidity_line_data_set.add(humidity_data_set);

        LineData humidity_data = new LineData(humidity_line_data_set);
        chart_humidity.setData(humidity_data);
        chart_humidity.setDescription(null);
        chart_humidity.getAxisLeft().setDrawLabels(false);
        chart_humidity.getAxisRight().setDrawLabels(false);
        chart_humidity.getLegend().setEnabled(false);
        XAxis x_axis = chart_humidity.getXAxis();
        x_axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart_humidity.zoom(72.0f / 8.0f + 0.5f, 1.0f, 0, 0);
        chart_humidity.moveViewToX(100);
        chart_humidity.invalidate();
    }

    private void setCO2LevelChart() {

        // getCO2LevelValues();

        List<Entry> co2_level_values = new ArrayList<Entry>();

        List<Float> values = getTempDay();
        for (int i = 0; i < values.size(); i++) {

            Entry entry = new Entry(i, values.get(i));
            co2_level_values.add(entry);
        }

        LineDataSet co2_level_data_set = new LineDataSet(co2_level_values, "legend");
        co2_level_data_set.setAxisDependency(YAxis.AxisDependency.LEFT);
        co2_level_data_set.setColor(Color.rgb(1, 89, 1));
        co2_level_data_set.setCircleColor(Color.rgb(1, 89, 1));
        co2_level_data_set.setCircleHoleColor(Color.rgb(1, 89, 1));
        co2_level_data_set.setValueTextSize(10);
        co2_level_data_set.setCircleRadius(5);
        co2_level_data_set.setLineWidth(2);
        co2_level_data_set.setHighlightLineWidth(2);
        co2_level_data_set.setValueTypeface(Typeface.DEFAULT_BOLD);
        co2_level_data_set.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        List<ILineDataSet> co2_level_line_data_set = new ArrayList<ILineDataSet>();
        co2_level_line_data_set.add(co2_level_data_set);

        LineData co2_level_data = new LineData(co2_level_line_data_set);
        chart_co2_level.setData(co2_level_data);
        chart_co2_level.setDescription(null);
        chart_co2_level.getAxisLeft().setDrawLabels(false);
        chart_co2_level.getAxisRight().setDrawLabels(false);
        chart_co2_level.getLegend().setEnabled(false);
        XAxis x_axis = chart_co2_level.getXAxis();
        x_axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart_co2_level.zoom(72.0f / 8.0f + 0.5f, 1.0f, 0, 0);
        chart_co2_level.moveViewToX(100);
        chart_co2_level.invalidate();
    }

    private void setIlluminationChart() {

        // getIlluminationValues();

        List<Entry> illumination_values = new ArrayList<Entry>();

        List<Float> values = getTempDay();
        for (int i = 0; i < values.size(); i++) {

            Entry entry = new Entry(i, values.get(i));
            illumination_values.add(entry);
        }

        LineDataSet illumination_data_set = new LineDataSet(illumination_values, "legend");
        illumination_data_set.setAxisDependency(YAxis.AxisDependency.LEFT);
        illumination_data_set.setColor(Color.rgb(232, 140, 2));
        illumination_data_set.setCircleColor(Color.rgb(232, 140, 2));
        illumination_data_set.setCircleHoleColor(Color.rgb(232, 140, 2));
        illumination_data_set.setValueTextSize(10);
        illumination_data_set.setCircleRadius(5);
        illumination_data_set.setLineWidth(2);
        illumination_data_set.setHighlightLineWidth(2);
        illumination_data_set.setValueTypeface(Typeface.DEFAULT_BOLD);
        illumination_data_set.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        List<ILineDataSet> illumination_line_data_set = new ArrayList<ILineDataSet>();
        illumination_line_data_set.add(illumination_data_set);

        LineData illumination_data = new LineData(illumination_line_data_set);
        chart_illumination.setData(illumination_data);
        chart_illumination.setDescription(null);
        chart_illumination.getAxisLeft().setDrawLabels(false);
        chart_illumination.getAxisRight().setDrawLabels(false);
        chart_illumination.getLegend().setEnabled(false);
        XAxis x_axis = chart_illumination.getXAxis();
        x_axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart_illumination.zoom(72.0f / 8.0f + 0.5f, 1.0f, 0, 0);
        chart_illumination.moveViewToX(100);
        chart_illumination.invalidate();
    }
}
