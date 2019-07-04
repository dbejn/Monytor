package com.example.monytor.mpandroidchart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class UnitValueFormatter implements IAxisValueFormatter {

    private String unit;

    public UnitValueFormatter(String u) {

        unit = u;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {

        float[] axis_entries = axis.mEntries;

        if (value == axis_entries[axis_entries.length - 1]) {

            return unit;
        }

        return "";
    }
}
