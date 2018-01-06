package capstone.heartbeat.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.MyAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import capstone.heartbeat.R;

public class ResultsFragment extends Fragment{

    public ResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_results, container, false);

        LineChart chart = (LineChart) view.findViewById(R.id.chart1);

        // creating list of entry (y-axis)
        ArrayList <Entry> entries = new ArrayList<>();
            entries.add(new Entry(4, 0));
            entries.add(new Entry(8, 1));
            entries.add(new Entry(6, 2));
            entries.add(new Entry(2, 3));

        Collections.sort(entries, new EntryXComparator());

        //creating list of labels (x-axis)
        /*ArrayList <String> labels = new ArrayList<>();
            labels.add("10");
            labels.add("20");
            labels.add("30");
            labels.add("40");
            labels.add("50");*/


        LineDataSet dataSet = new LineDataSet(entries, "Dataset 1"); // add entries to dataset
        dataSet.setColor(Color.BLACK);
        dataSet.setCircleColor(Color.BLACK);
        dataSet.setLineWidth(3f);
        dataSet.setCircleRadius(5f);
        dataSet.setDrawValues(false);
        dataSet.setDrawCircleHole(false);
        dataSet.setValueTextSize(12f);
        dataSet.setDrawFilled(true);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        final String[] xValues = new String[] { "Week 1", "Week 2", "Week 3", "Week 4" };

        chart.getXAxis().setValueFormatter(new MyAxisValueFormatter(xValues) {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xValues[(int) value % xValues.length];
            }

        });
        chart.getXAxis().setGranularity(1);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(dataSet); // add the datasets

        LineData lineData = new LineData(dataSets);
        chart.setData(lineData);
        chart.setDrawGridBackground(false);
        chart.setDrawBorders(false);
        chart.setAutoScaleMinMaxEnabled(true);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getAxisRight().setEnabled(false);
        chart.invalidate(); // refresh

        return view;
    }

}
