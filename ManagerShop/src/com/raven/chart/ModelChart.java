package com.raven.chart;

public class ModelChart {

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public long[] getValues() {
        return values;
    }

    public void setValues(long[] values) {
        this.values = values;
    }

    public ModelChart(String label, long[] values) {
        this.label = label;
        this.values = values;
    }

    public ModelChart() {
    }

    private String label;
    private long values[];

    public long getMaxValues() {
        long max = 0;
        for (long v : values) {
            if (v > max) {
                max = v;
            }
        }
        return max;
    }
}
