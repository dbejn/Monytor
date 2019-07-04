package com.example.monytor.mpandroidchart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class DateValueFormatter implements IAxisValueFormatter {

    private List<String> dates;

    public DateValueFormatter(List<String> ds) {

        dates = ds;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        int counter = (int) value;

        if (counter % 3 == 0) {

            return dates.get(counter) + "h";
        }

        return "";

        // return dates.get(counter) + "h";
    }
}
