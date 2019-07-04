package com.example.monytor;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.monytor.mpandroidchart.DateValueFormatter;
import com.example.monytor.mpandroidchart.UnitValueFormatter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Monitoring extends AppCompatActivity {

    private CheckBox temperature_checkbox;
    private CheckBox humidity_checkbox;
    private CheckBox co2_level_checkbox;
    private CheckBox illumination_checkbox;
    private CheckBox pressure_checkbox;

    private CardView layout_temperature_chart;
    private CardView layout_humidity_chart;
    private CardView layout_co2_level_chart;
    private CardView layout_illumination_chart;
    private CardView layout_pressure_chart;

    private LineChart chart_temperature;
    private LineChart chart_humidity;
    private LineChart chart_co2_level;
    private LineChart chart_illumination;
    private LineChart chart_pressure;

    private LinearLayout layout_legend;
    private boolean legend_visible;
    private ImageButton button_show_legend;

    private RequestQueue request_queue;
    private StringRequest string_request;
    private static final String url_get_data = "http://192.168.0.103:80/get_data.php";

    private List<Integer> temperature_data;
    private List<Integer> humidity_data;
    private List<Integer> co2_level_data;
    private List<Integer> illumination_data;
    private List<Integer> pressure_data;
    private List<String> date_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Station: Amphitheatre A3, 24h");

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
        layout_pressure_chart = findViewById(R.id.layout_pressure_chart);

        chart_temperature = findViewById(R.id.chart_temerature);
        chart_humidity = findViewById(R.id.chart_humidity);
        chart_co2_level = findViewById(R.id.chart_co2_level);
        chart_illumination = findViewById(R.id.chart_illumination);
        chart_pressure = findViewById(R.id.chart_pressure);

        temperature_data = new ArrayList<>();
        humidity_data = new ArrayList<>();
        pressure_data = new ArrayList<>();
        co2_level_data = new ArrayList<>();
        illumination_data = new ArrayList<>();
        date_data = new ArrayList<>();


        // Get data from server
        request_queue = Volley.newRequestQueue(this);
        string_request = new StringRequest(Request.Method.POST, url_get_data, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray data_array = new JSONArray(response);
                    for (int i = 0; i < data_array.length(); i++) {

                        JSONObject data = data_array.getJSONObject(i);
                        temperature_data.add(data.getInt("Temperature"));
                        humidity_data.add(data.getInt("Humidity"));
                        pressure_data.add(data.getInt("Pressure"));
                        co2_level_data.add(data.getInt("CO2"));
                        illumination_data.add(data.getInt("Light"));

                        try {

                            SimpleDateFormat string_to_date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            SimpleDateFormat date_to_string_format = new SimpleDateFormat("dd.MM. HH:mm");
                            Date date = string_to_date_format.parse(data.getString("Date"));
                            String string_date = date_to_string_format.format(date);
                            date_data.add(string_date);
                        }
                        catch (ParseException parse_e) {

                            parse_e.printStackTrace();
                        }

                        // data.getString("Date").substring(5, 16);
                        // string_date = string_date.replace('-', '.');
                        // string_date = string_date.replace(" ", ", ");
                    }
                }
                catch (JSONException json_e) {

                    json_e.printStackTrace();
                }

                setTemperatureChart(temperature_data);
                setHumidityChart(humidity_data);
                setCO2LevelChart(co2_level_data);
                setIlluminationChart(illumination_data);
                setPressureChart(pressure_data);

                Toast.makeText(Monitoring.this, "Finished fetching data"
                        , Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Monitoring.this, "Could not fetch data"
                        , Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String, String> hashMap = new HashMap<>();

                return hashMap;
            }
        };
        request_queue.add(string_request);

        // setTemperatureChart(getTempDay());
        // setHumidityChart(getTempDay());
        // setCO2LevelChart(getTempDay());
        // setIlluminationChart(getTempDay());
        // setPressureChart(getTempDay());
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
        if (id == R.id.action_change_station) {

            AlertDialog.Builder dialog_builder = new AlertDialog.Builder(Monitoring.this);

            dialog_builder.setTitle("Choose station");
            dialog_builder.setItems(R.array.stations, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Toast.makeText(Monitoring.this, which + ". station"
                            , Toast.LENGTH_SHORT).show();
                }
            });
            dialog_builder.create().show();
        }
        else if (id == R.id.action_adjust) {

            AlertDialog.Builder dialog_builder = new AlertDialog.Builder(Monitoring.this);

            dialog_builder.setTitle("Choose variable");
            dialog_builder.setItems(R.array.variables, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Toast.makeText(Monitoring.this, which + ". variable"
                            , Toast.LENGTH_SHORT).show();
                }
            });
            dialog_builder.create().show();
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

        pressure_checkbox = findViewById(R.id.checkbox_pressure);
        pressure_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    layout_pressure_chart.setVisibility(View.VISIBLE);
                }
                else {

                    layout_pressure_chart.setVisibility(View.GONE);
                }
            }
        });
    }

    private List<Integer> getTempDay() {

        List<Integer> values = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 72; i++) {

            values.add(25 + (int)(random.nextFloat() * 3.0f));
        }

        return values;
    }

    private void setTemperatureChart(List<Integer> temp_data) {

        List<Entry> temperature_values = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < temp_data.size(); i++) {

            Entry entry = new Entry(i, temp_data.get(i) + random.nextFloat());
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

        List<ILineDataSet> temperature_line_data_set = new ArrayList<>();
        temperature_line_data_set.add(temperature_data_set);

        LineData temperature_data = new LineData(temperature_line_data_set);
        chart_temperature.setData(temperature_data);
        chart_temperature.setDescription(null);
        chart_temperature.getAxisLeft().setDrawLabels(true);
        chart_temperature.getAxisRight().setDrawLabels(false);
        chart_temperature.getLegend().setEnabled(false);
        XAxis x_axis = chart_temperature.getXAxis();
        x_axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        x_axis.setValueFormatter(new DateValueFormatter(date_data));
        YAxis y_axis = chart_temperature.getAxisLeft();
        y_axis.setValueFormatter(new UnitValueFormatter("\u2103"));
        chart_temperature.zoom(temp_data.size() / 8.0f + 0.5f, 1.0f, 0, 0);
        chart_temperature.moveViewToX(temp_data.size());
        chart_temperature.invalidate();
    }

    private void setHumidityChart(List<Integer> hum_data) {

        List<Entry> humidity_values = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < hum_data.size(); i++) {

            Entry entry = new Entry(i, hum_data.get(i) + random.nextFloat());
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

        List<ILineDataSet> humidity_line_data_set = new ArrayList<>();
        humidity_line_data_set.add(humidity_data_set);

        LineData humidity_data = new LineData(humidity_line_data_set);
        chart_humidity.setData(humidity_data);
        chart_humidity.setDescription(null);
        chart_humidity.getAxisLeft().setDrawLabels(true);
        chart_humidity.getAxisRight().setDrawLabels(false);
        chart_humidity.getLegend().setEnabled(false);
        XAxis x_axis = chart_humidity.getXAxis();
        x_axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        x_axis.setValueFormatter(new DateValueFormatter(date_data));
        YAxis y_axis = chart_humidity.getAxisLeft();
        y_axis.setValueFormatter(new UnitValueFormatter("RH\u0025"));
        chart_humidity.zoom(hum_data.size() / 8.0f + 0.5f, 1.0f, 0, 0);
        chart_humidity.moveViewToX(hum_data.size());
        chart_humidity.invalidate();
    }

    private void setCO2LevelChart(List<Integer> co2_data) {

        List<Entry> co2_level_values = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < co2_data.size(); i++) {

            Entry entry = new Entry(i, co2_data.get(i) + random.nextFloat());
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

        List<ILineDataSet> co2_level_line_data_set = new ArrayList<>();
        co2_level_line_data_set.add(co2_level_data_set);

        LineData co2_level_data = new LineData(co2_level_line_data_set);
        chart_co2_level.setData(co2_level_data);
        chart_co2_level.setDescription(null);
        chart_co2_level.getAxisLeft().setDrawLabels(true);
        chart_co2_level.getAxisRight().setDrawLabels(false);
        chart_co2_level.getLegend().setEnabled(false);
        XAxis x_axis = chart_co2_level.getXAxis();
        x_axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        x_axis.setValueFormatter(new DateValueFormatter(date_data));
        YAxis y_axis = chart_co2_level.getAxisLeft();
        y_axis.setValueFormatter(new UnitValueFormatter("\u0025"));
        chart_co2_level.zoom(co2_data.size() / 8.0f + 0.5f, 1.0f, 0, 0);
        chart_co2_level.moveViewToX(co2_data.size());
        chart_co2_level.invalidate();
    }

    private void setIlluminationChart(List<Integer> illu_data) {

        List<Entry> illumination_values = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < illu_data.size(); i++) {

            Entry entry = new Entry(i, illu_data.get(i) + random.nextFloat());
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
        chart_illumination.getAxisLeft().setDrawLabels(true);
        chart_illumination.getAxisRight().setDrawLabels(false);
        chart_illumination.getLegend().setEnabled(false);
        XAxis x_axis = chart_illumination.getXAxis();
        x_axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        x_axis.setValueFormatter(new DateValueFormatter(date_data));
        YAxis y_axis = chart_illumination.getAxisLeft();
        y_axis.setValueFormatter(new UnitValueFormatter("lx"));
        chart_illumination.zoom(illu_data.size() / 8.0f + 0.5f, 1.0f, 0, 0);
        chart_illumination.moveViewToX(illu_data.size());
        chart_illumination.invalidate();
    }

    private void setPressureChart(List<Integer> press_data) {

        List<Entry> pressure_values = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < press_data.size(); i++) {

            Entry entry = new Entry(i, press_data.get(i) + random.nextFloat());
            pressure_values.add(entry);
        }

        LineDataSet pressure_data_set = new LineDataSet(pressure_values, "legend");
        pressure_data_set.setAxisDependency(YAxis.AxisDependency.LEFT);
        pressure_data_set.setColor(Color.rgb(95, 0, 130));
        pressure_data_set.setCircleColor(Color.rgb(95, 0, 130));
        pressure_data_set.setCircleHoleColor(Color.rgb(95, 0, 130));
        pressure_data_set.setValueTextSize(10);
        pressure_data_set.setCircleRadius(5);
        pressure_data_set.setLineWidth(2);
        pressure_data_set.setHighlightLineWidth(2);
        pressure_data_set.setValueTypeface(Typeface.DEFAULT_BOLD);
        pressure_data_set.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        List<ILineDataSet> pressure_line_data_set = new ArrayList<>();
        pressure_line_data_set.add(pressure_data_set);

        LineData illumination_data = new LineData(pressure_line_data_set);
        chart_pressure.setData(illumination_data);
        chart_pressure.setDescription(null);
        chart_pressure.getAxisLeft().setDrawLabels(true);
        chart_pressure.getAxisRight().setDrawLabels(false);
        chart_pressure.getLegend().setEnabled(false);
        XAxis x_axis = chart_pressure.getXAxis();
        x_axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        x_axis.setValueFormatter(new DateValueFormatter(date_data));
        YAxis y_axis = chart_pressure.getAxisLeft();
        y_axis.setValueFormatter(new UnitValueFormatter("hPa"));
        chart_pressure.zoom(press_data.size() / 8.0f + 0.5f, 1.0f, 0, 0);
        chart_pressure.moveViewToX(press_data.size());
        chart_pressure.invalidate();
    }
}
