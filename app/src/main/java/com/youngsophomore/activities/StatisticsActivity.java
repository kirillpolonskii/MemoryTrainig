package com.youngsophomore.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.divider.MaterialDividerItemDecoration;
import com.youngsophomore.R;
import com.youngsophomore.adapters.DetailsStatAdapter;
import com.youngsophomore.data.CollectionsStorage;
import com.youngsophomore.data.StatParam;
import com.youngsophomore.data.Training;
import com.youngsophomore.helpers.PrepHelper;
import com.youngsophomore.helpers.TrainHelper;

import java.util.ArrayList;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "Gestures";
    SharedPreferences sharedPreferences;
    // MHJ
    int[] mhjTilesAmounts = {12, 24};
    int[] mhjEqualTilesAmounts = {2, 3, 4, 6};
    int[] mhjShowTimeSecs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    List<BarEntry> entriesByTiles12;
    List<BarEntry> entriesByTiles24;
    List<BarEntry> entriesByEqTiles2;
    List<BarEntry> entriesByEqTiles3;
    List<BarEntry> entriesByEqTiles4;
    List<BarEntry> entriesByEqTiles6;
    double avTimeTiles12, avTimeTiles24, avTimeEqTiles2, avTimeEqTiles3, avTimeEqTiles4, avTimeEqTiles6;
    // CLR
    int[] colorsAmounts = {6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
    int[] distinctColorsAmounts = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    int[] colorShowTimeSec = {1, 2, 3, 4, 5, 6};
    List<BarEntry> entriesColorsByTime1;
    List<BarEntry> entriesColorsByTime2;
    List<BarEntry> entriesColorsByTime3;
    List<BarEntry> entriesColorsByTime4;
    List<BarEntry> entriesColorsByTime5;
    List<BarEntry> entriesColorsByTime6;
    List<BarEntry> entriesDistColorsByTime1;
    List<BarEntry> entriesDistColorsByTime2;
    List<BarEntry> entriesDistColorsByTime3;
    List<BarEntry> entriesDistColorsByTime4;
    List<BarEntry> entriesDistColorsByTime5;
    List<BarEntry> entriesDistColorsByTime6;
    ArrayList<Double> avTimeColors;
    ArrayList<Double> avTimeDistColors;
    // SHP
    int[] shapesAmounts = {4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
    int[] distinctShapesAmounts = {2, 3, 4, 5, 6, 7, 8, 9};
    int[] shapeShowTimeSec = {1, 2, 3, 4, 5, 6};
    List<BarEntry> entriesShapesByTime1;
    List<BarEntry> entriesShapesByTime2;
    List<BarEntry> entriesShapesByTime3;
    List<BarEntry> entriesShapesByTime4;
    List<BarEntry> entriesShapesByTime5;
    List<BarEntry> entriesShapesByTime6;
    List<BarEntry> entriesDistShapesByTime1;
    List<BarEntry> entriesDistShapesByTime2;
    List<BarEntry> entriesDistShapesByTime3;
    List<BarEntry> entriesDistShapesByTime4;
    List<BarEntry> entriesDistShapesByTime5;
    List<BarEntry> entriesDistShapesByTime6;
    ArrayList<Double> avTimeShapes;
    ArrayList<Double> avTimeDistShapes;
    // WRD
    double[][] avTimeWrd;
    double[][] avMovesWrd;
    // PHR
    double[][] avTimePhr;
    double[][] avMovesPhr;
    // DET
    ArrayList<String> questionsCollectionsTitles;
    ArrayList<String> questionsCollectionsSecsMoves;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.tbr_stat_title));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        // MHJ
        BarChart bcTimeToMovesMhjTiles = findViewById(R.id.bc_time_to_moves_mhj_tiles);
        BarChart bcTimeToMovesMhjEqTiles = findViewById(R.id.bc_time_to_moves_mhj_eq_tiles);
        TextView tvAvTimeTiles12 = findViewById(R.id.tv_av_time_tiles_12);
        TextView tvAvTimeTiles24 = findViewById(R.id.tv_av_time_tiles_24);
        TextView tvAvTimeEqTiles2 = findViewById(R.id.tv_av_time_eq_tiles_2);
        TextView tvAvTimeEqTiles3 = findViewById(R.id.tv_av_time_eq_tiles_3);
        TextView tvAvTimeEqTiles4 = findViewById(R.id.tv_av_time_eq_tiles_4);
        TextView tvAvTimeEqTiles6 = findViewById(R.id.tv_av_time_eq_tiles_6);
        
        fillDataMhj();
        float grSpaceTilesMhj = 0.06f;
        float barSpaceTilesMhj = 0.02f; // x2 dataset
        float barWidthTilesMhj = 0.45f; // x2 dataset
        // (0.02 + 0.45) * 2 + 0.06 = 1.00 -> interval per group
        BarDataSet setTiles12 = new BarDataSet(entriesByTiles12, getString(R.string.lbl_stat_tiles_12));
        BarDataSet setTiles24 = new BarDataSet(entriesByTiles24, getString(R.string.lbl_stat_tiles_24));
        setTiles12.setColor(getResources().getColor(R.color.blue));
        setTiles24.setColor(getResources().getColor(R.color.black));
        BarData bDataTimeToMovesMhjTiles = new BarData(setTiles12, setTiles24);
        bDataTimeToMovesMhjTiles.setBarWidth(barWidthTilesMhj);
        bcTimeToMovesMhjTiles.setData(bDataTimeToMovesMhjTiles);
        bcTimeToMovesMhjTiles.groupBars(1f, grSpaceTilesMhj, barSpaceTilesMhj);
        XAxis xAxisTiles = bcTimeToMovesMhjTiles.getXAxis();
        xAxisTiles.setAxisMinimum(1);
        xAxisTiles.setAxisMaximum(11);
        xAxisTiles.setLabelCount(10);
        Description descTimeToMovesTiles = bcTimeToMovesMhjTiles.getDescription();
        descTimeToMovesTiles.setEnabled(true);
        descTimeToMovesTiles.setText(getString(R.string.bc_time_to_moves_desc_mhj));
        bcTimeToMovesMhjTiles.invalidate();

        float grSpaceEqTilesMhj = 0.06f;
        float barSpaceEqTilesMhj = 0.02f; // x4 dataset
        float barWidthEqTilesMhj = 0.215f; // x4 dataset
        // (0.02 + 0.215) * 4 + 0.06 = 1.00 -> interval per group
        BarDataSet setEqTiles2 = new BarDataSet(entriesByEqTiles2, getString(R.string.lbl_stat_eq_tiles_2));
        BarDataSet setEqTiles3 = new BarDataSet(entriesByEqTiles3, getString(R.string.lbl_stat_eq_tiles_3));
        BarDataSet setEqTiles4 = new BarDataSet(entriesByEqTiles4, getString(R.string.lbl_stat_eq_tiles_4));
        BarDataSet setEqTiles6 = new BarDataSet(entriesByEqTiles6, getString(R.string.lbl_stat_eq_tiles_6));
        setEqTiles2.setColor(getResources().getColor(R.color.blue));
        setEqTiles3.setColor(getResources().getColor(R.color.c_training_color_10));
        setEqTiles4.setColor(getResources().getColor(R.color.c_training_color_12));
        setEqTiles6.setColor(getResources().getColor(R.color.c_training_color_9));
        BarData bDataTimeToMovesMhjEqTiles = new BarData(setEqTiles2, setEqTiles3, setEqTiles4, setEqTiles6);
        bDataTimeToMovesMhjEqTiles.setBarWidth(barWidthEqTilesMhj);
        bcTimeToMovesMhjEqTiles.setData(bDataTimeToMovesMhjEqTiles);
        bcTimeToMovesMhjEqTiles.groupBars(1, grSpaceEqTilesMhj, barSpaceEqTilesMhj);
        XAxis xAxisEqTiles = bcTimeToMovesMhjEqTiles.getXAxis();
        xAxisEqTiles.setAxisMinimum(1);
        xAxisEqTiles.setAxisMaximum(11);
        xAxisEqTiles.setLabelCount(10);
        Description descTimeToMovesEqTiles = bcTimeToMovesMhjEqTiles.getDescription();
        descTimeToMovesEqTiles.setEnabled(true);
        descTimeToMovesEqTiles.setText(getString(R.string.bc_time_to_moves_desc_mhj));
        bcTimeToMovesMhjEqTiles.invalidate();

        String strAvTimeTiles12 = tvAvTimeTiles12.getText() +
                String.valueOf(avTimeTiles12) + " " +
                getString(R.string.sec);
        tvAvTimeTiles12.setText(strAvTimeTiles12);
        String strAvTimeTiles24 = tvAvTimeTiles24.getText() +
                String.valueOf(avTimeTiles24) + " " +
                getString(R.string.sec);
        tvAvTimeTiles24.setText(strAvTimeTiles24);
        String strAvTimeEqTiles2 = tvAvTimeEqTiles2.getText() +
                String.valueOf(avTimeEqTiles2) + " " +
                getString(R.string.sec);
        tvAvTimeEqTiles2.setText(strAvTimeEqTiles2);
        String strAvTimeEqTiles3 = tvAvTimeEqTiles3.getText() +
                String.valueOf(avTimeEqTiles3) + " " +
                getString(R.string.sec);
        tvAvTimeEqTiles3.setText(strAvTimeEqTiles3);
        String strAvTimeEqTiles4 = tvAvTimeEqTiles4.getText() +
                String.valueOf(avTimeEqTiles4) + " " +
                getString(R.string.sec);
        tvAvTimeEqTiles4.setText(strAvTimeEqTiles4);
        String strAvTimeEqTiles6 = tvAvTimeEqTiles6.getText() +
                String.valueOf(avTimeEqTiles6) + " " +
                getString(R.string.sec);
        tvAvTimeEqTiles6.setText(strAvTimeEqTiles6);
        // CLR
        BarChart bcColorsToMoves = findViewById(R.id.bc_colors_to_moves);
        BarChart bcDistColorsToMoves = findViewById(R.id.bc_dist_colors_to_moves);
        NumberPicker numPckColors = findViewById(R.id.num_pck_colors_stat);
        TextView tvAvTimeColors = findViewById(R.id.tv_av_time_colors);
        NumberPicker numPckDistColors = findViewById(R.id.num_pck_dist_colors);
        TextView tvAvTimeDistColors = findViewById(R.id.tv_av_time_dist_colors);
        fillDataClr();
        float grSpaceColors = 0.04f;
        float barSpaceColors = 0.02f; // x6 dataset
        float barWidthColors = 0.14f; // x6 dataset
        // (0.02 + 0.14) * 6 + 0.04 = 1.00 -> interval per group
        BarDataSet setColorsTime1 = new BarDataSet(entriesColorsByTime1, getString(R.string.lbl_stat_time_1));
        BarDataSet setColorsTime2 = new BarDataSet(entriesColorsByTime2, getString(R.string.lbl_stat_time_2));
        BarDataSet setColorsTime3 = new BarDataSet(entriesColorsByTime3, getString(R.string.lbl_stat_time_3));
        BarDataSet setColorsTime4 = new BarDataSet(entriesColorsByTime4, getString(R.string.lbl_stat_time_4));
        BarDataSet setColorsTime5 = new BarDataSet(entriesColorsByTime5, getString(R.string.lbl_stat_time_5));
        BarDataSet setColorsTime6 = new BarDataSet(entriesColorsByTime6, getString(R.string.lbl_stat_time_6));
        setColorsTime1.setColor(getResources().getColor(R.color.c_training_color_4));
        setColorsTime2.setColor(getResources().getColor(R.color.c_training_color_5));
        setColorsTime3.setColor(getResources().getColor(R.color.c_training_color_6));
        setColorsTime4.setColor(getResources().getColor(R.color.c_training_color_7));
        setColorsTime5.setColor(getResources().getColor(R.color.c_training_color_8));
        setColorsTime6.setColor(getResources().getColor(R.color.c_training_color_9));
        BarData bDataColorsToMoves = new BarData(setColorsTime1, setColorsTime2, setColorsTime3,
                setColorsTime4, setColorsTime5, setColorsTime6);
        bDataColorsToMoves.setBarWidth(barWidthColors);
        bcColorsToMoves.setData(bDataColorsToMoves);
        bcColorsToMoves.groupBars(6, grSpaceColors, barSpaceColors);
        XAxis xAxisColors = bcColorsToMoves.getXAxis();
        xAxisColors.setAxisMinimum(6);
        xAxisColors.setAxisMaximum(25);
        xAxisColors.setLabelCount(19);
        Description descAmountToMovesClr = bcColorsToMoves.getDescription();
        descAmountToMovesClr.setEnabled(true);
        descAmountToMovesClr.setText(getString(R.string.bc_amount_to_moves_desc_clr));
        bcColorsToMoves.invalidate();

        BarDataSet setDistColorsTime1 = new BarDataSet(entriesDistColorsByTime1, getString(R.string.lbl_stat_time_1));
        BarDataSet setDistColorsTime2 = new BarDataSet(entriesDistColorsByTime2, getString(R.string.lbl_stat_time_2));
        BarDataSet setDistColorsTime3 = new BarDataSet(entriesDistColorsByTime3, getString(R.string.lbl_stat_time_3));
        BarDataSet setDistColorsTime4 = new BarDataSet(entriesDistColorsByTime4, getString(R.string.lbl_stat_time_4));
        BarDataSet setDistColorsTime5 = new BarDataSet(entriesDistColorsByTime5, getString(R.string.lbl_stat_time_5));
        BarDataSet setDistColorsTime6 = new BarDataSet(entriesDistColorsByTime6, getString(R.string.lbl_stat_time_6));
        setDistColorsTime1.setColor(getResources().getColor(R.color.c_training_color_4));
        setDistColorsTime2.setColor(getResources().getColor(R.color.c_training_color_5));
        setDistColorsTime3.setColor(getResources().getColor(R.color.c_training_color_6));
        setDistColorsTime4.setColor(getResources().getColor(R.color.c_training_color_7));
        setDistColorsTime5.setColor(getResources().getColor(R.color.c_training_color_8));
        setDistColorsTime6.setColor(getResources().getColor(R.color.c_training_color_9));
        BarData bDataDistColorsToMoves = new BarData(setDistColorsTime1, setDistColorsTime2, setDistColorsTime3,
                setDistColorsTime4, setDistColorsTime5, setDistColorsTime6);
        bDataDistColorsToMoves.setBarWidth(barWidthColors);
        bcDistColorsToMoves.setData(bDataDistColorsToMoves);
        bcDistColorsToMoves.groupBars(2, grSpaceColors, barSpaceColors);
        XAxis xAxisDistColors = bcDistColorsToMoves.getXAxis();
        xAxisDistColors.setAxisMinimum(2);
        xAxisDistColors.setAxisMaximum(16);
        xAxisDistColors.setLabelCount(14);
        Description descDistAmountToMovesClr = bcDistColorsToMoves.getDescription();
        descDistAmountToMovesClr.setEnabled(true);
        descDistAmountToMovesClr.setText(getString(R.string.bc_dist_amount_to_moves_desc_clr));
        bcDistColorsToMoves.invalidate();

        numPckColors.setMinValue(6);
        numPckColors.setMaxValue(24);
        numPckDistColors.setMinValue(2);
        numPckDistColors.setMaxValue(15);
        numPckColors.setValue(6);
        numPckDistColors.setValue(2);
        String strAvTimeColors = avTimeColors.get(0) + " " +
                getString(R.string.sec);
        tvAvTimeColors.setText(strAvTimeColors);
        String strAvTimeDistColors = avTimeDistColors.get(0) + " " +
                getString(R.string.sec);
        tvAvTimeDistColors.setText(strAvTimeDistColors);
        numPckColors.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String strAvTimeColors = avTimeColors.get(newVal - 6) + " " +
                        getString(R.string.sec);
                tvAvTimeColors.setText(strAvTimeColors);
            }
        });
        numPckDistColors.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String strAvTimeDistColors = avTimeDistColors.get(newVal - 2) + " " +
                        getString(R.string.sec);
                tvAvTimeDistColors.setText(strAvTimeDistColors);
            }
        });

        // SHP
        BarChart bcShapesToMoves = findViewById(R.id.bc_shapes_to_moves);
        BarChart bcDistShapesToMoves = findViewById(R.id.bc_dist_shapes_to_moves);
        NumberPicker numPckShapes = findViewById(R.id.num_pck_shapes_stat);
        TextView tvAvTimeShapes = findViewById(R.id.tv_av_time_shapes);
        NumberPicker numPckDistShapes = findViewById(R.id.num_pck_dist_shapes);
        TextView tvAvTimeDistShapes = findViewById(R.id.tv_av_time_dist_shapes);
        fillDataShp();
        float grSpaceShapes = 0.04f;
        float barSpaceShapes = 0.02f; // x6 dataset
        float barWidthShapes = 0.14f; // x6 dataset
        // (0.02 + 0.14) * 6 + 0.04 = 1.00 -> interval per group
        BarDataSet setShapesTime1 = new BarDataSet(entriesShapesByTime1, getString(R.string.lbl_stat_time_1));
        BarDataSet setShapesTime2 = new BarDataSet(entriesShapesByTime2, getString(R.string.lbl_stat_time_2));
        BarDataSet setShapesTime3 = new BarDataSet(entriesShapesByTime3, getString(R.string.lbl_stat_time_3));
        BarDataSet setShapesTime4 = new BarDataSet(entriesShapesByTime4, getString(R.string.lbl_stat_time_4));
        BarDataSet setShapesTime5 = new BarDataSet(entriesShapesByTime5, getString(R.string.lbl_stat_time_5));
        BarDataSet setShapesTime6 = new BarDataSet(entriesShapesByTime6, getString(R.string.lbl_stat_time_6));
        setShapesTime1.setColor(getResources().getColor(R.color.c_training_color_4));
        setShapesTime2.setColor(getResources().getColor(R.color.c_training_color_5));
        setShapesTime3.setColor(getResources().getColor(R.color.c_training_color_6));
        setShapesTime4.setColor(getResources().getColor(R.color.c_training_color_7));
        setShapesTime5.setColor(getResources().getColor(R.color.c_training_color_8));
        setShapesTime6.setColor(getResources().getColor(R.color.c_training_color_9));
        BarData bDataShapesToMoves = new BarData(setShapesTime1, setShapesTime2, setShapesTime3,
                setShapesTime4, setShapesTime5, setShapesTime6);
        bDataShapesToMoves.setBarWidth(barWidthShapes);
        bcShapesToMoves.setData(bDataShapesToMoves);
        bcShapesToMoves.groupBars(4, grSpaceShapes, barSpaceShapes);
        XAxis xAxisShapes = bcShapesToMoves.getXAxis();
        xAxisShapes.setAxisMinimum(4);
        xAxisShapes.setAxisMaximum(25);
        xAxisShapes.setLabelCount(21);
        Description descAmountToMovesShp = bcShapesToMoves.getDescription();
        descAmountToMovesShp.setEnabled(true);
        descAmountToMovesShp.setText(getString(R.string.bc_amount_to_moves_desc_shp));
        bcShapesToMoves.invalidate();

        BarDataSet setDistShapesTime1 = new BarDataSet(entriesDistShapesByTime1, getString(R.string.lbl_stat_time_1));
        BarDataSet setDistShapesTime2 = new BarDataSet(entriesDistShapesByTime2, getString(R.string.lbl_stat_time_2));
        BarDataSet setDistShapesTime3 = new BarDataSet(entriesDistShapesByTime3, getString(R.string.lbl_stat_time_3));
        BarDataSet setDistShapesTime4 = new BarDataSet(entriesDistShapesByTime4, getString(R.string.lbl_stat_time_4));
        BarDataSet setDistShapesTime5 = new BarDataSet(entriesDistShapesByTime5, getString(R.string.lbl_stat_time_5));
        BarDataSet setDistShapesTime6 = new BarDataSet(entriesDistShapesByTime6, getString(R.string.lbl_stat_time_6));
        setDistShapesTime1.setColor(getResources().getColor(R.color.c_training_color_4));
        setDistShapesTime2.setColor(getResources().getColor(R.color.c_training_color_5));
        setDistShapesTime3.setColor(getResources().getColor(R.color.c_training_color_6));
        setDistShapesTime4.setColor(getResources().getColor(R.color.c_training_color_7));
        setDistShapesTime5.setColor(getResources().getColor(R.color.c_training_color_8));
        setDistShapesTime6.setColor(getResources().getColor(R.color.c_training_color_9));
        BarData bDataDistShapesToMoves = new BarData(setDistShapesTime1, setDistShapesTime2, setDistShapesTime3,
                setDistShapesTime4, setDistShapesTime5, setDistShapesTime6);
        bDataDistShapesToMoves.setBarWidth(barWidthShapes);
        bcDistShapesToMoves.setData(bDataDistShapesToMoves);
        bcDistShapesToMoves.groupBars(2, grSpaceShapes, barSpaceShapes);
        XAxis xAxisDistShapes = bcDistShapesToMoves.getXAxis();
        xAxisDistShapes.setAxisMinimum(2);
        xAxisDistShapes.setAxisMaximum(10);
        xAxisDistShapes.setLabelCount(8);
        Description descDistAmountToMovesShp = bcDistShapesToMoves.getDescription();
        descDistAmountToMovesShp.setEnabled(true);
        descDistAmountToMovesShp.setText(getString(R.string.bc_dist_amount_to_moves_desc_shp));
        bcDistShapesToMoves.invalidate();

        numPckShapes.setMinValue(4);
        numPckShapes.setMaxValue(24);
        numPckDistShapes.setMinValue(2);
        numPckDistShapes.setMaxValue(9);
        numPckShapes.setValue(4);
        numPckDistShapes.setValue(2);
        String strAvTimeShapes = avTimeShapes.get(0) + " " +
                getString(R.string.sec);
        tvAvTimeShapes.setText(strAvTimeShapes);
        String strAvTimeDistShapes = avTimeDistShapes.get(0) + " " +
                getString(R.string.sec);
        tvAvTimeDistShapes.setText(strAvTimeDistShapes);
        numPckShapes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String strAvTimeShapes = avTimeShapes.get(newVal - 4) + " " +
                        getString(R.string.sec);
                tvAvTimeShapes.setText(strAvTimeShapes);
            }
        });
        numPckDistShapes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String strAvTimeDistShapes = avTimeDistShapes.get(newVal - 2) + " " +
                        getString(R.string.sec);
                tvAvTimeDistShapes.setText(strAvTimeDistShapes);
            }
        });

        // WRD
        NumberPicker numPckSizeWrd = findViewById(R.id.num_pck_stat_size_wrd);
        NumberPicker numPckShowTimeWrd = findViewById(R.id.num_pck_stat_show_time_wrd);
        TextView tvAvTimeWrd = findViewById(R.id.tv_av_time_wrd);
        TextView tvAvMovesWrd = findViewById(R.id.tv_av_moves_wrd);
        
        fillDataWrd();
        numPckSizeWrd.setMinValue(1);
        numPckSizeWrd.setMaxValue(100);
        numPckShowTimeWrd.setMinValue(1);
        numPckShowTimeWrd.setMaxValue(6);
        numPckSizeWrd.setValue(1);
        numPckShowTimeWrd.setValue(1);
        String strAvTimeWrd = avTimeWrd[1][1] + " " + getString(R.string.sec);
        tvAvTimeWrd.setText(strAvTimeWrd);
        String strAvMovesWrd = String.valueOf(avMovesWrd[1][1]);
        tvAvMovesWrd.setText(strAvMovesWrd);
        numPckSizeWrd.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String strAvTimeWrd = avTimeWrd[newVal][numPckShowTimeWrd.getValue()] + " " +
                        getString(R.string.sec);
                tvAvTimeWrd.setText(strAvTimeWrd);
                String strAvMovesWrd = String.valueOf(avMovesWrd[newVal][numPckShowTimeWrd.getValue()]);
                tvAvMovesWrd.setText(strAvMovesWrd);
            }
        });
        numPckShowTimeWrd.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String strAvTimeWrd = avTimeWrd[numPckSizeWrd.getValue()][newVal] + " " +
                        getString(R.string.sec);
                tvAvTimeWrd.setText(strAvTimeWrd);
                String strAvMovesWrd = String.valueOf(avMovesWrd[numPckSizeWrd.getValue()][newVal]);
                tvAvMovesWrd.setText(strAvMovesWrd);
            }
        });

        // PHR
        NumberPicker numPckSizePhr = findViewById(R.id.num_pck_stat_size_phr);
        NumberPicker numPckShowTimePhr = findViewById(R.id.num_pck_stat_show_time_phr);
        TextView tvAvTimePhr = findViewById(R.id.tv_av_time_phr);
        TextView tvAvMovesPhr = findViewById(R.id.tv_av_moves_phr);
        
        fillDataPhr();
        numPckSizePhr.setMinValue(1);
        numPckSizePhr.setMaxValue(35);
        numPckShowTimePhr.setMinValue(1);
        numPckShowTimePhr.setMaxValue(6);
        numPckSizePhr.setValue(1);
        numPckShowTimePhr.setValue(1);
        String strAvTimePhr = avTimePhr[1][1] + " " + getString(R.string.sec);
        tvAvTimePhr.setText(strAvTimePhr);
        String strAvMovesPhr = String.valueOf(avMovesPhr[1][1]);
        tvAvMovesPhr.setText(strAvMovesPhr);
        numPckSizePhr.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String strAvTimePhr = avTimePhr[newVal][numPckShowTimePhr.getValue()] + " " +
                        getString(R.string.sec);
                tvAvTimePhr.setText(strAvTimePhr);
                String strAvMovesPhr = String.valueOf(avMovesPhr[newVal][numPckShowTimePhr.getValue()]);
                tvAvMovesPhr.setText(strAvMovesPhr);
            }
        });
        numPckShowTimePhr.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String strAvTimePhr = avTimePhr[numPckSizePhr.getValue()][newVal] + " " +
                        getString(R.string.sec);
                tvAvTimePhr.setText(strAvTimePhr);
                String strAvMovesPhr = String.valueOf(avMovesPhr[numPckSizePhr.getValue()][newVal]);
                tvAvMovesPhr.setText(strAvMovesPhr);
            }
        });

        // DET
        RecyclerView rvCollectTime = findViewById(R.id.rv_collect_time);
        fillDataDet();
        DetailsStatAdapter detailsStatAdapter = new DetailsStatAdapter(questionsCollectionsTitles,
                questionsCollectionsSecsMoves);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvCollectTime.setLayoutManager(layoutManager);
        rvCollectTime.setAdapter(detailsStatAdapter);
        MaterialDividerItemDecoration dividerItemDecoration = new MaterialDividerItemDecoration(rvCollectTime.getContext(),
                layoutManager.getOrientation());
        dividerItemDecoration.setDividerColorResource(this, R.color.blue);
        rvCollectTime.addItemDecoration(dividerItemDecoration);

    }

    private void fillDataMhj(){
        ArrayList<Float> grAvMovesTiles12 = new ArrayList<>();
        ArrayList<Float> grAvMovesTiles24 = new ArrayList<>();
        for(int i = 0; i < mhjShowTimeSecs.length; ++i){
            int curTimeTotNumMoves12 = 0, curTimeTotNumMoves24 = 0;
            int curTimeTotTrain12 = 0, curTimeTotTrain24 = 0;
            for(int j = 0; j < mhjEqualTilesAmounts.length; ++j){
                String curKeyMoves12 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMMOVES, 12, mhjEqualTilesAmounts[j], mhjShowTimeSecs[i]);
                curTimeTotNumMoves12 += sharedPreferences.getInt(curKeyMoves12, 0);
                String curKeyTrain12 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMTRAINS, 12, mhjEqualTilesAmounts[j], mhjShowTimeSecs[i]);
                curTimeTotTrain12 += sharedPreferences.getInt(curKeyTrain12, 0);

                String curKeyMoves24 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMMOVES, 24, mhjEqualTilesAmounts[j], mhjShowTimeSecs[i]);
                curTimeTotNumMoves24 += sharedPreferences.getInt(curKeyMoves24, 0);
                String curKeyTrain24 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMTRAINS, 24, mhjEqualTilesAmounts[j], mhjShowTimeSecs[i]);
                curTimeTotTrain24 += sharedPreferences.getInt(curKeyTrain24, 0);
            }
            grAvMovesTiles12.add(
                    (curTimeTotTrain12 == 0) ? 0 : (float) curTimeTotNumMoves12 / (float) curTimeTotTrain12);
            grAvMovesTiles24.add(
                    (curTimeTotTrain24 == 0) ? 0 : (float) curTimeTotNumMoves24 / (float) curTimeTotTrain24);
        }
        entriesByTiles12 = new ArrayList<>();
        entriesByTiles24 = new ArrayList<>();
        for(int i = 0; i < grAvMovesTiles12.size(); ++i) {
            entriesByTiles12.add(new BarEntry(mhjShowTimeSecs[i], grAvMovesTiles12.get(i)));
            entriesByTiles24.add(new BarEntry(mhjShowTimeSecs[i], grAvMovesTiles24.get(i)));
        }

        ArrayList<Float> grAvMovesEqTiles2 = new ArrayList<>();
        ArrayList<Float> grAvMovesEqTiles3 = new ArrayList<>();
        ArrayList<Float> grAvMovesEqTiles4 = new ArrayList<>();
        ArrayList<Float> grAvMovesEqTiles6 = new ArrayList<>();
        for(int i = 0; i < mhjShowTimeSecs.length; ++i){
            int curTimeTotNumMoves2 = 0, curTimeTotNumMoves3 = 0, curTimeTotNumMoves4 = 0, curTimeTotNumMoves6 = 0;
            int curTimeTotTrain2 = 0, curTimeTotTrain3 = 0, curTimeTotTrain4 = 0, curTimeTotTrain6 = 0;
            for(int j = 0; j < mhjTilesAmounts.length; ++j){
                String curKeyMoves2 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMMOVES, mhjTilesAmounts[j], 2, mhjShowTimeSecs[i]);
                curTimeTotNumMoves2 += sharedPreferences.getInt(curKeyMoves2, 0);
                String curKeyTrain2 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMTRAINS, mhjTilesAmounts[j], 2, mhjShowTimeSecs[i]);
                curTimeTotTrain2 += sharedPreferences.getInt(curKeyTrain2, 0);

                String curKeyMoves3 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMMOVES, mhjTilesAmounts[j], 3, mhjShowTimeSecs[i]);
                curTimeTotNumMoves3 += sharedPreferences.getInt(curKeyMoves3, 0);
                String curKeyTrain3 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMTRAINS, mhjTilesAmounts[j], 3, mhjShowTimeSecs[i]);
                curTimeTotTrain3 += sharedPreferences.getInt(curKeyTrain3, 0);

                String curKeyMoves4 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMMOVES, mhjTilesAmounts[j], 4, mhjShowTimeSecs[i]);
                curTimeTotNumMoves4 += sharedPreferences.getInt(curKeyMoves4, 0);
                String curKeyTrain4 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMTRAINS, mhjTilesAmounts[j], 4, mhjShowTimeSecs[i]);
                curTimeTotTrain4 += sharedPreferences.getInt(curKeyTrain4, 0);

                String curKeyMoves6 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMMOVES, mhjTilesAmounts[j], 6, mhjShowTimeSecs[i]);
                curTimeTotNumMoves6 += sharedPreferences.getInt(curKeyMoves6, 0);
                String curKeyTrain6 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMTRAINS, mhjTilesAmounts[j], 6, mhjShowTimeSecs[i]);
                curTimeTotTrain6 += sharedPreferences.getInt(curKeyTrain6, 0);
            }
            grAvMovesEqTiles2.add(
                    (curTimeTotTrain2 == 0) ? 0 : (float) curTimeTotNumMoves2 / (float) curTimeTotTrain2);
            grAvMovesEqTiles3.add(
                    (curTimeTotTrain3 == 0) ? 0 : (float) curTimeTotNumMoves3 / (float) curTimeTotTrain3);
            grAvMovesEqTiles4.add(
                    (curTimeTotTrain4 == 0) ? 0 : (float) curTimeTotNumMoves4 / (float) curTimeTotTrain4);
            grAvMovesEqTiles6.add(
                    (curTimeTotTrain6 == 0) ? 0 : (float) curTimeTotNumMoves6 / (float) curTimeTotTrain6);
        }
        entriesByEqTiles2 = new ArrayList<>();
        entriesByEqTiles3 = new ArrayList<>();
        entriesByEqTiles4 = new ArrayList<>();
        entriesByEqTiles6 = new ArrayList<>();
        for(int i = 0; i < grAvMovesEqTiles2.size(); ++i) {
            entriesByEqTiles2.add(new BarEntry(mhjShowTimeSecs[i], grAvMovesEqTiles2.get(i)));
            entriesByEqTiles3.add(new BarEntry(mhjShowTimeSecs[i], grAvMovesEqTiles3.get(i)));
            entriesByEqTiles4.add(new BarEntry(mhjShowTimeSecs[i], grAvMovesEqTiles4.get(i)));
            entriesByEqTiles6.add(new BarEntry(mhjShowTimeSecs[i], grAvMovesEqTiles6.get(i)));
        }

        int totTime12 = 0, totTime24 = 0;
        int totTrain12 = 0, totTrain24 = 0;
        for(int i = 0; i < mhjEqualTilesAmounts.length; ++i){
            for(int j = 0; j < mhjShowTimeSecs.length; ++j){
                String curKeyTime12 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMTIME, 12, mhjEqualTilesAmounts[i], mhjShowTimeSecs[j]);
                totTime12 += sharedPreferences.getInt(curKeyTime12, 0);
                String curKeyTrain12 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMTRAINS, 12, mhjEqualTilesAmounts[i], mhjShowTimeSecs[j]);
                totTrain12 += sharedPreferences.getInt(curKeyTrain12, 0);

                String curKeyTime24 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMTIME, 24, mhjEqualTilesAmounts[i], mhjShowTimeSecs[j]);
                totTime24 += sharedPreferences.getInt(curKeyTime24, 0);
                String curKeyTrain24 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMTRAINS, 24, mhjEqualTilesAmounts[i], mhjShowTimeSecs[j]);
                totTrain24 += sharedPreferences.getInt(curKeyTrain24, 0);
            }
        }
        avTimeTiles12 = round2((totTrain12 == 0) ? 0 : (double)totTime12 / (double)totTrain12);
        avTimeTiles24 = round2((totTrain24 == 0) ? 0 : (double)totTime24 / (double)totTrain24);
        int totTime2 = 0, totTime3 = 0, totTime4 = 0, totTime6 = 0;
        int totTrain2 = 0, totTrain3 = 0, totTrain4 = 0, totTrain6 = 0;
        for(int i = 0; i < mhjTilesAmounts.length; ++i){
            for(int j = 0; j < mhjShowTimeSecs.length; ++j){
                String curKeyTime2 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMTIME, mhjTilesAmounts[i], 2, mhjShowTimeSecs[j]);
                totTime2 += sharedPreferences.getInt(curKeyTime2, 0);
                String curKeyTrain2 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMTRAINS, mhjTilesAmounts[i], 2, mhjShowTimeSecs[j]);
                totTrain2 += sharedPreferences.getInt(curKeyTrain2, 0);

                String curKeyTime3 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMTIME, mhjTilesAmounts[i], 3, mhjShowTimeSecs[j]);
                totTime3 += sharedPreferences.getInt(curKeyTime3, 0);
                String curKeyTrain3 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMTRAINS, mhjTilesAmounts[i], 3, mhjShowTimeSecs[j]);
                totTrain3 += sharedPreferences.getInt(curKeyTrain3, 0);

                String curKeyTime4 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMTIME, mhjTilesAmounts[i], 4, mhjShowTimeSecs[j]);
                totTime4 += sharedPreferences.getInt(curKeyTime4, 0);
                String curKeyTrain4 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMTRAINS, mhjTilesAmounts[i], 4, mhjShowTimeSecs[j]);
                totTrain4 += sharedPreferences.getInt(curKeyTrain4, 0);

                String curKeyTime6 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMTIME, mhjTilesAmounts[i], 6, mhjShowTimeSecs[j]);
                totTime6 += sharedPreferences.getInt(curKeyTime6, 0);
                String curKeyTrain6 = TrainHelper.getStatParamKey(
                        Training.MHJ, StatParam.TOTNUMTRAINS, mhjTilesAmounts[i], 6, mhjShowTimeSecs[j]);
                totTrain6 += sharedPreferences.getInt(curKeyTrain6, 0);
            }
        }
        avTimeEqTiles2 = round2((totTrain2 == 0) ? 0 : (double)totTime2 / (double)totTrain2);
        avTimeEqTiles3 = round2((totTrain3 == 0) ? 0 : (double)totTime3 / (double)totTrain3);
        avTimeEqTiles4 = round2((totTrain4 == 0) ? 0 : (double)totTime4 / (double)totTrain4);
        avTimeEqTiles6 = round2((totTrain6 == 0) ? 0 : (double)totTime6 / (double)totTrain6);
    }

    private void fillDataClr(){
        ArrayList<Float> grAvMovesColors1 = new ArrayList<>();
        ArrayList<Float> grAvMovesColors2 = new ArrayList<>();
        ArrayList<Float> grAvMovesColors3 = new ArrayList<>();
        ArrayList<Float> grAvMovesColors4 = new ArrayList<>();
        ArrayList<Float> grAvMovesColors5 = new ArrayList<>();
        ArrayList<Float> grAvMovesColors6 = new ArrayList<>();

        for(int i = 0; i < colorsAmounts.length; ++i){
            int curColorTotNumMoves1 = 0, curColorTotNumMoves2 = 0, curColorTotNumMoves3 = 0,
                    curColorTotNumMoves4 = 0, curColorTotNumMoves5 = 0, curColorTotNumMoves6 = 0;
            int curColorTotNumTrain1 = 0, curColorTotNumTrain2 = 0, curColorTotNumTrain3 = 0,
                    curColorTotNumTrain4 = 0, curColorTotNumTrain5 = 0, curColorTotNumTrain6 = 0;
            for(int j = 0; j < distinctColorsAmounts.length; ++j){
                String curKeyMoves1 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMMOVES, colorsAmounts[i], distinctColorsAmounts[j], 1);
                curColorTotNumMoves1 += sharedPreferences.getInt(curKeyMoves1, 0);
                String curKeyTrain1 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMTRAINS, colorsAmounts[i], distinctColorsAmounts[j], 1);
                curColorTotNumTrain1 += sharedPreferences.getInt(curKeyTrain1, 0);

                String curKeyMoves2 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMMOVES, colorsAmounts[i], distinctColorsAmounts[j], 2);
                curColorTotNumMoves2 += sharedPreferences.getInt(curKeyMoves2, 0);
                String curKeyTrain2 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMTRAINS, colorsAmounts[i], distinctColorsAmounts[j], 2);
                curColorTotNumTrain2 += sharedPreferences.getInt(curKeyTrain2, 0);

                String curKeyMoves3 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMMOVES, colorsAmounts[i], distinctColorsAmounts[j], 3);
                curColorTotNumMoves3 += sharedPreferences.getInt(curKeyMoves3, 0);
                String curKeyTrain3 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMTRAINS, colorsAmounts[i], distinctColorsAmounts[j], 3);
                curColorTotNumTrain3 += sharedPreferences.getInt(curKeyTrain3, 0);

                String curKeyMoves4 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMMOVES, colorsAmounts[i], distinctColorsAmounts[j], 4);
                curColorTotNumMoves4 += sharedPreferences.getInt(curKeyMoves4, 0);
                String curKeyTrain4 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMTRAINS, colorsAmounts[i], distinctColorsAmounts[j], 4);
                curColorTotNumTrain4 += sharedPreferences.getInt(curKeyTrain4, 0);

                String curKeyMoves5 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMMOVES, colorsAmounts[i], distinctColorsAmounts[j], 5);
                curColorTotNumMoves5 += sharedPreferences.getInt(curKeyMoves5, 0);
                String curKeyTrain5 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMTRAINS, colorsAmounts[i], distinctColorsAmounts[j], 5);
                curColorTotNumTrain5 += sharedPreferences.getInt(curKeyTrain5, 0);

                String curKeyMoves6 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMMOVES, colorsAmounts[i], distinctColorsAmounts[j], 6);
                curColorTotNumMoves6 += sharedPreferences.getInt(curKeyMoves6, 0);
                String curKeyTrain6 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMTRAINS, colorsAmounts[i], distinctColorsAmounts[j], 6);
                curColorTotNumTrain6 += sharedPreferences.getInt(curKeyTrain6, 0);
            }

            grAvMovesColors1.add(
                    (curColorTotNumTrain1 == 0) ? 0 : (float) curColorTotNumMoves1 / (float) curColorTotNumTrain1);
            grAvMovesColors2.add(
                    (curColorTotNumTrain2 == 0) ? 0 : (float) curColorTotNumMoves2 / (float) curColorTotNumTrain2);
            grAvMovesColors3.add(
                    (curColorTotNumTrain3 == 0) ? 0 : (float) curColorTotNumMoves3 / (float) curColorTotNumTrain3);
            grAvMovesColors4.add(
                    (curColorTotNumTrain4 == 0) ? 0 : (float) curColorTotNumMoves4 / (float) curColorTotNumTrain4);
            grAvMovesColors5.add(
                    (curColorTotNumTrain5 == 0) ? 0 : (float) curColorTotNumMoves5 / (float) curColorTotNumTrain5);
            grAvMovesColors6.add(
                    (curColorTotNumTrain6 == 0) ? 0 : (float) curColorTotNumMoves6 / (float) curColorTotNumTrain6);
        }

        entriesColorsByTime1 = new ArrayList<>();
        entriesColorsByTime2 = new ArrayList<>();
        entriesColorsByTime3 = new ArrayList<>();
        entriesColorsByTime4 = new ArrayList<>();
        entriesColorsByTime5 = new ArrayList<>();
        entriesColorsByTime6 = new ArrayList<>();
        for(int i = 0; i < grAvMovesColors1.size(); ++i){
            entriesColorsByTime1.add(new BarEntry(colorsAmounts[i], grAvMovesColors1.get(i)));
            entriesColorsByTime2.add(new BarEntry(colorsAmounts[i], grAvMovesColors2.get(i)));
            entriesColorsByTime3.add(new BarEntry(colorsAmounts[i], grAvMovesColors3.get(i)));
            entriesColorsByTime4.add(new BarEntry(colorsAmounts[i], grAvMovesColors4.get(i)));
            entriesColorsByTime5.add(new BarEntry(colorsAmounts[i], grAvMovesColors5.get(i)));
            entriesColorsByTime6.add(new BarEntry(colorsAmounts[i], grAvMovesColors6.get(i)));
        }

        ArrayList<Float> grAvMovesDistColors1 = new ArrayList<>();
        ArrayList<Float> grAvMovesDistColors2 = new ArrayList<>();
        ArrayList<Float> grAvMovesDistColors3 = new ArrayList<>();
        ArrayList<Float> grAvMovesDistColors4 = new ArrayList<>();
        ArrayList<Float> grAvMovesDistColors5 = new ArrayList<>();
        ArrayList<Float> grAvMovesDistColors6 = new ArrayList<>();

        for(int i = 0; i < distinctColorsAmounts.length; ++i){
            int curDistColorTotNumMoves1 = 0, curDistColorTotNumMoves2 = 0, curDistColorTotNumMoves3 = 0,
                    curDistColorTotNumMoves4 = 0, curDistColorTotNumMoves5 = 0, curDistColorTotNumMoves6 = 0;
            int curDistColorTotNumTrain1 = 0, curDistColorTotNumTrain2 = 0, curDistColorTotNumTrain3 = 0,
                    curDistColorTotNumTrain4 = 0, curDistColorTotNumTrain5 = 0, curDistColorTotNumTrain6 = 0;
            for(int j = 0; j < colorsAmounts.length; ++j){
                String curKeyMoves1 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMMOVES, colorsAmounts[j], distinctColorsAmounts[i], 1);
                curDistColorTotNumMoves1 += sharedPreferences.getInt(curKeyMoves1, 0);
                String curKeyTrain1 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMTRAINS, colorsAmounts[j], distinctColorsAmounts[i], 1);
                curDistColorTotNumTrain1 += sharedPreferences.getInt(curKeyTrain1, 0);

                String curKeyMoves2 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMMOVES, colorsAmounts[j], distinctColorsAmounts[i], 2);
                curDistColorTotNumMoves2 += sharedPreferences.getInt(curKeyMoves2, 0);
                String curKeyTrain2 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMTRAINS, colorsAmounts[j], distinctColorsAmounts[i], 2);
                curDistColorTotNumTrain2 += sharedPreferences.getInt(curKeyTrain2, 0);

                String curKeyMoves3 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMMOVES, colorsAmounts[j], distinctColorsAmounts[i], 3);
                curDistColorTotNumMoves3 += sharedPreferences.getInt(curKeyMoves3, 0);
                String curKeyTrain3 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMTRAINS, colorsAmounts[j], distinctColorsAmounts[i], 3);
                curDistColorTotNumTrain3 += sharedPreferences.getInt(curKeyTrain3, 0);

                String curKeyMoves4 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMMOVES, colorsAmounts[j], distinctColorsAmounts[i], 4);
                curDistColorTotNumMoves4 += sharedPreferences.getInt(curKeyMoves4, 0);
                String curKeyTrain4 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMTRAINS, colorsAmounts[j], distinctColorsAmounts[i], 4);
                curDistColorTotNumTrain4 += sharedPreferences.getInt(curKeyTrain4, 0);

                String curKeyMoves5 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMMOVES, colorsAmounts[j], distinctColorsAmounts[i], 5);
                curDistColorTotNumMoves5 += sharedPreferences.getInt(curKeyMoves5, 0);
                String curKeyTrain5 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMTRAINS, colorsAmounts[j], distinctColorsAmounts[i], 5);
                curDistColorTotNumTrain5 += sharedPreferences.getInt(curKeyTrain5, 0);

                String curKeyMoves6 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMMOVES, colorsAmounts[j], distinctColorsAmounts[i], 6);
                curDistColorTotNumMoves6 += sharedPreferences.getInt(curKeyMoves6, 0);
                String curKeyTrain6 = TrainHelper.getStatParamKey(
                        Training.CLR, StatParam.TOTNUMTRAINS, colorsAmounts[j], distinctColorsAmounts[i], 6);
                curDistColorTotNumTrain6 += sharedPreferences.getInt(curKeyTrain6, 0);
            }

            grAvMovesDistColors1.add(
                    (curDistColorTotNumTrain1 == 0) ? 0 : (float) curDistColorTotNumMoves1 / (float) curDistColorTotNumTrain1);
            grAvMovesDistColors2.add(
                    (curDistColorTotNumTrain2 == 0) ? 0 : (float) curDistColorTotNumMoves2 / (float) curDistColorTotNumTrain2);
            grAvMovesDistColors3.add(
                    (curDistColorTotNumTrain3 == 0) ? 0 : (float) curDistColorTotNumMoves3 / (float) curDistColorTotNumTrain3);
            grAvMovesDistColors4.add(
                    (curDistColorTotNumTrain4 == 0) ? 0 : (float) curDistColorTotNumMoves4 / (float) curDistColorTotNumTrain4);
            grAvMovesDistColors5.add(
                    (curDistColorTotNumTrain5 == 0) ? 0 : (float) curDistColorTotNumMoves5 / (float) curDistColorTotNumTrain5);
            grAvMovesDistColors6.add(
                    (curDistColorTotNumTrain6 == 0) ? 0 : (float) curDistColorTotNumMoves6 / (float) curDistColorTotNumTrain6);
        }
        entriesDistColorsByTime1 = new ArrayList<>();
        entriesDistColorsByTime2 = new ArrayList<>();
        entriesDistColorsByTime3 = new ArrayList<>();
        entriesDistColorsByTime4 = new ArrayList<>();
        entriesDistColorsByTime5 = new ArrayList<>();
        entriesDistColorsByTime6 = new ArrayList<>();
        for(int i = 0; i < grAvMovesDistColors1.size(); ++i){
            entriesDistColorsByTime1.add(new BarEntry(distinctColorsAmounts[i], grAvMovesDistColors1.get(i)));
            entriesDistColorsByTime2.add(new BarEntry(distinctColorsAmounts[i], grAvMovesDistColors2.get(i)));
            entriesDistColorsByTime3.add(new BarEntry(distinctColorsAmounts[i], grAvMovesDistColors3.get(i)));
            entriesDistColorsByTime4.add(new BarEntry(distinctColorsAmounts[i], grAvMovesDistColors4.get(i)));
            entriesDistColorsByTime5.add(new BarEntry(distinctColorsAmounts[i], grAvMovesDistColors5.get(i)));
            entriesDistColorsByTime6.add(new BarEntry(distinctColorsAmounts[i], grAvMovesDistColors6.get(i)));
        }

        avTimeColors = new ArrayList<>();
        avTimeDistColors = new ArrayList<>();
        int[] totTimeColors = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] totTrainColors = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for(int i = 0; i < colorsAmounts.length; ++i){
            for(int j = 0; j < distinctColorsAmounts.length; ++j){
                for(int k = 0; k < colorShowTimeSec.length; ++k){
                    String curKeyTime = TrainHelper.getStatParamKey(
                            Training.CLR, StatParam.TOTNUMTIME, colorsAmounts[i], distinctColorsAmounts[j], colorShowTimeSec[k]);
                    totTimeColors[i] += sharedPreferences.getInt(curKeyTime, 0);
                    String curKeyTrain = TrainHelper.getStatParamKey(
                            Training.CLR, StatParam.TOTNUMTRAINS, colorsAmounts[i], distinctColorsAmounts[j], colorShowTimeSec[k]);
                    totTrainColors[i] += sharedPreferences.getInt(curKeyTrain, 0);
                }
            }
            avTimeColors.add(round2((totTrainColors[i] == 0) ? 0 : (double) totTimeColors[i] / (double) totTrainColors[i]));
        }
        int[] totTimeDistColors = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] totTrainDistColors = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for(int i = 0; i < distinctColorsAmounts.length; ++i){
            for(int j = 0; j < colorsAmounts.length; ++j){
                for(int k = 0; k < colorShowTimeSec.length; ++k){
                    String curKeyTime = TrainHelper.getStatParamKey(
                            Training.CLR, StatParam.TOTNUMTIME, colorsAmounts[j], distinctColorsAmounts[i], colorShowTimeSec[k]);
                    totTimeDistColors[i] += sharedPreferences.getInt(curKeyTime, 0);
                    String curKeyTrain = TrainHelper.getStatParamKey(
                            Training.CLR, StatParam.TOTNUMTRAINS, colorsAmounts[j], distinctColorsAmounts[i], colorShowTimeSec[k]);
                    totTrainDistColors[i] += sharedPreferences.getInt(curKeyTrain, 0);
                }
            }
            avTimeDistColors.add(round2((totTrainDistColors[i] == 0) ? 0 : (double) totTimeDistColors[i] / (double) totTrainDistColors[i]));
        }
    }

    private void fillDataShp(){
        ArrayList<Float> grAvMovesShapes1 = new ArrayList<>();
        ArrayList<Float> grAvMovesShapes2 = new ArrayList<>();
        ArrayList<Float> grAvMovesShapes3 = new ArrayList<>();
        ArrayList<Float> grAvMovesShapes4 = new ArrayList<>();
        ArrayList<Float> grAvMovesShapes5 = new ArrayList<>();
        ArrayList<Float> grAvMovesShapes6 = new ArrayList<>();

        for(int i = 0; i < shapesAmounts.length; ++i){
            int curShapeTotNumMoves1 = 0, curShapeTotNumMoves2 = 0, curShapeTotNumMoves3 = 0,
                    curShapeTotNumMoves4 = 0, curShapeTotNumMoves5 = 0, curShapeTotNumMoves6 = 0;
            int curShapeTotNumTrain1 = 0, curShapeTotNumTrain2 = 0, curShapeTotNumTrain3 = 0,
                    curShapeTotNumTrain4 = 0, curShapeTotNumTrain5 = 0, curShapeTotNumTrain6 = 0;
            for(int j = 0; j < distinctShapesAmounts.length; ++j){
                String curKeyMoves1 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMMOVES, shapesAmounts[i], distinctShapesAmounts[j], 1);
                curShapeTotNumMoves1 += sharedPreferences.getInt(curKeyMoves1, 0);
                String curKeyTrain1 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMTRAINS, shapesAmounts[i], distinctShapesAmounts[j], 1);
                curShapeTotNumTrain1 += sharedPreferences.getInt(curKeyTrain1, 0);

                String curKeyMoves2 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMMOVES, shapesAmounts[i], distinctShapesAmounts[j], 2);
                curShapeTotNumMoves2 += sharedPreferences.getInt(curKeyMoves2, 0);
                String curKeyTrain2 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMTRAINS, shapesAmounts[i], distinctShapesAmounts[j], 2);
                curShapeTotNumTrain2 += sharedPreferences.getInt(curKeyTrain2, 0);

                String curKeyMoves3 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMMOVES, shapesAmounts[i], distinctShapesAmounts[j], 3);
                curShapeTotNumMoves3 += sharedPreferences.getInt(curKeyMoves3, 0);
                String curKeyTrain3 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMTRAINS, shapesAmounts[i], distinctShapesAmounts[j], 3);
                curShapeTotNumTrain3 += sharedPreferences.getInt(curKeyTrain3, 0);

                String curKeyMoves4 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMMOVES, shapesAmounts[i], distinctShapesAmounts[j], 4);
                curShapeTotNumMoves4 += sharedPreferences.getInt(curKeyMoves4, 0);
                String curKeyTrain4 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMTRAINS, shapesAmounts[i], distinctShapesAmounts[j], 4);
                curShapeTotNumTrain4 += sharedPreferences.getInt(curKeyTrain4, 0);

                String curKeyMoves5 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMMOVES, shapesAmounts[i], distinctShapesAmounts[j], 5);
                curShapeTotNumMoves5 += sharedPreferences.getInt(curKeyMoves5, 0);
                String curKeyTrain5 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMTRAINS, shapesAmounts[i], distinctShapesAmounts[j], 5);
                curShapeTotNumTrain5 += sharedPreferences.getInt(curKeyTrain5, 0);

                String curKeyMoves6 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMMOVES, shapesAmounts[i], distinctShapesAmounts[j], 6);
                curShapeTotNumMoves6 += sharedPreferences.getInt(curKeyMoves6, 0);
                String curKeyTrain6 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMTRAINS, shapesAmounts[i], distinctShapesAmounts[j], 6);
                curShapeTotNumTrain6 += sharedPreferences.getInt(curKeyTrain6, 0);
            }

            grAvMovesShapes1.add(
                    (curShapeTotNumTrain1 == 0) ? 0 : (float) curShapeTotNumMoves1 / (float) curShapeTotNumTrain1);
            grAvMovesShapes2.add(
                    (curShapeTotNumTrain2 == 0) ? 0 : (float) curShapeTotNumMoves2 / (float) curShapeTotNumTrain2);
            grAvMovesShapes3.add(
                    (curShapeTotNumTrain3 == 0) ? 0 : (float) curShapeTotNumMoves3 / (float) curShapeTotNumTrain3);
            grAvMovesShapes4.add(
                    (curShapeTotNumTrain4 == 0) ? 0 : (float) curShapeTotNumMoves4 / (float) curShapeTotNumTrain4);
            grAvMovesShapes5.add(
                    (curShapeTotNumTrain5 == 0) ? 0 : (float) curShapeTotNumMoves5 / (float) curShapeTotNumTrain5);
            grAvMovesShapes6.add(
                    (curShapeTotNumTrain6 == 0) ? 0 : (float) curShapeTotNumMoves6 / (float) curShapeTotNumTrain6);
        }

        entriesShapesByTime1 = new ArrayList<>();
        entriesShapesByTime2 = new ArrayList<>();
        entriesShapesByTime3 = new ArrayList<>();
        entriesShapesByTime4 = new ArrayList<>();
        entriesShapesByTime5 = new ArrayList<>();
        entriesShapesByTime6 = new ArrayList<>();
        for(int i = 0; i < grAvMovesShapes1.size(); ++i){
            entriesShapesByTime1.add(new BarEntry(shapesAmounts[i], grAvMovesShapes1.get(i)));
            entriesShapesByTime2.add(new BarEntry(shapesAmounts[i], grAvMovesShapes2.get(i)));
            entriesShapesByTime3.add(new BarEntry(shapesAmounts[i], grAvMovesShapes3.get(i)));
            entriesShapesByTime4.add(new BarEntry(shapesAmounts[i], grAvMovesShapes4.get(i)));
            entriesShapesByTime5.add(new BarEntry(shapesAmounts[i], grAvMovesShapes5.get(i)));
            entriesShapesByTime6.add(new BarEntry(shapesAmounts[i], grAvMovesShapes6.get(i)));
        }

        ArrayList<Float> grAvMovesDistShapes1 = new ArrayList<>();
        ArrayList<Float> grAvMovesDistShapes2 = new ArrayList<>();
        ArrayList<Float> grAvMovesDistShapes3 = new ArrayList<>();
        ArrayList<Float> grAvMovesDistShapes4 = new ArrayList<>();
        ArrayList<Float> grAvMovesDistShapes5 = new ArrayList<>();
        ArrayList<Float> grAvMovesDistShapes6 = new ArrayList<>();
        for(int i = 0; i < distinctShapesAmounts.length; ++i){
            int curDistShapeTotNumMoves1 = 0, curDistShapeTotNumMoves2 = 0, curDistShapeTotNumMoves3 = 0,
                    curDistShapeTotNumMoves4 = 0, curDistShapeTotNumMoves5 = 0, curDistShapeTotNumMoves6 = 0;
            int curDistShapeTotNumTrain1 = 0, curDistShapeTotNumTrain2 = 0, curDistShapeTotNumTrain3 = 0,
                    curDistShapeTotNumTrain4 = 0, curDistShapeTotNumTrain5 = 0, curDistShapeTotNumTrain6 = 0;
            for(int j = 0; j < shapesAmounts.length; ++j){
                String curKeyMoves1 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMMOVES, shapesAmounts[j], distinctShapesAmounts[i], 1);
                curDistShapeTotNumMoves1 += sharedPreferences.getInt(curKeyMoves1, 0);
                String curKeyTrain1 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMTRAINS, shapesAmounts[j], distinctShapesAmounts[i], 1);
                curDistShapeTotNumTrain1 += sharedPreferences.getInt(curKeyTrain1, 0);

                String curKeyMoves2 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMMOVES, shapesAmounts[j], distinctShapesAmounts[i], 2);
                curDistShapeTotNumMoves2 += sharedPreferences.getInt(curKeyMoves2, 0);
                String curKeyTrain2 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMTRAINS, shapesAmounts[j], distinctShapesAmounts[i], 2);
                curDistShapeTotNumTrain2 += sharedPreferences.getInt(curKeyTrain2, 0);

                String curKeyMoves3 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMMOVES, shapesAmounts[j], distinctShapesAmounts[i], 3);
                curDistShapeTotNumMoves3 += sharedPreferences.getInt(curKeyMoves3, 0);
                String curKeyTrain3 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMTRAINS, shapesAmounts[j], distinctShapesAmounts[i], 3);
                curDistShapeTotNumTrain3 += sharedPreferences.getInt(curKeyTrain3, 0);

                String curKeyMoves4 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMMOVES, shapesAmounts[j], distinctShapesAmounts[i], 4);
                curDistShapeTotNumMoves4 += sharedPreferences.getInt(curKeyMoves4, 0);
                String curKeyTrain4 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMTRAINS, shapesAmounts[j], distinctShapesAmounts[i], 4);
                curDistShapeTotNumTrain4 += sharedPreferences.getInt(curKeyTrain4, 0);

                String curKeyMoves5 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMMOVES, shapesAmounts[j], distinctShapesAmounts[i], 5);
                curDistShapeTotNumMoves5 += sharedPreferences.getInt(curKeyMoves5, 0);
                String curKeyTrain5 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMTRAINS, shapesAmounts[j], distinctShapesAmounts[i], 5);
                curDistShapeTotNumTrain5 += sharedPreferences.getInt(curKeyTrain5, 0);

                String curKeyMoves6 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMMOVES, shapesAmounts[j], distinctShapesAmounts[i], 6);
                curDistShapeTotNumMoves6 += sharedPreferences.getInt(curKeyMoves6, 0);
                String curKeyTrain6 = TrainHelper.getStatParamKey(
                        Training.SHP, StatParam.TOTNUMTRAINS, shapesAmounts[j], distinctShapesAmounts[i], 6);
                curDistShapeTotNumTrain6 += sharedPreferences.getInt(curKeyTrain6, 0);
            }

            grAvMovesDistShapes1.add(
                    (curDistShapeTotNumTrain1 == 0) ? 0 : (float) curDistShapeTotNumMoves1 / (float) curDistShapeTotNumTrain1);
            grAvMovesDistShapes2.add(
                    (curDistShapeTotNumTrain2 == 0) ? 0 : (float) curDistShapeTotNumMoves2 / (float) curDistShapeTotNumTrain2);
            grAvMovesDistShapes3.add(
                    (curDistShapeTotNumTrain3 == 0) ? 0 : (float) curDistShapeTotNumMoves3 / (float) curDistShapeTotNumTrain3);
            grAvMovesDistShapes4.add(
                    (curDistShapeTotNumTrain4 == 0) ? 0 : (float) curDistShapeTotNumMoves4 / (float) curDistShapeTotNumTrain4);
            grAvMovesDistShapes5.add(
                    (curDistShapeTotNumTrain5 == 0) ? 0 : (float) curDistShapeTotNumMoves5 / (float) curDistShapeTotNumTrain5);
            grAvMovesDistShapes6.add(
                    (curDistShapeTotNumTrain6 == 0) ? 0 : (float) curDistShapeTotNumMoves6 / (float) curDistShapeTotNumTrain6);
        }
        entriesDistShapesByTime1 = new ArrayList<>();
        entriesDistShapesByTime2 = new ArrayList<>();
        entriesDistShapesByTime3 = new ArrayList<>();
        entriesDistShapesByTime4 = new ArrayList<>();
        entriesDistShapesByTime5 = new ArrayList<>();
        entriesDistShapesByTime6 = new ArrayList<>();
        for(int i = 0; i < grAvMovesDistShapes1.size(); ++i){
            entriesDistShapesByTime1.add(new BarEntry(distinctShapesAmounts[i], grAvMovesDistShapes1.get(i)));
            entriesDistShapesByTime2.add(new BarEntry(distinctShapesAmounts[i], grAvMovesDistShapes2.get(i)));
            entriesDistShapesByTime3.add(new BarEntry(distinctShapesAmounts[i], grAvMovesDistShapes3.get(i)));
            entriesDistShapesByTime4.add(new BarEntry(distinctShapesAmounts[i], grAvMovesDistShapes4.get(i)));
            entriesDistShapesByTime5.add(new BarEntry(distinctShapesAmounts[i], grAvMovesDistShapes5.get(i)));
            entriesDistShapesByTime6.add(new BarEntry(distinctShapesAmounts[i], grAvMovesDistShapes6.get(i)));
        }

        avTimeShapes = new ArrayList<>();
        avTimeDistShapes = new ArrayList<>();
        int[] totTimeShapes = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int[] totTrainShapes = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for(int i = 0; i < shapesAmounts.length; ++i){
            for(int j = 0; j < distinctShapesAmounts.length; ++j){
                for(int k = 0; k < shapeShowTimeSec.length; ++k){
                    String curKeyTime = TrainHelper.getStatParamKey(
                            Training.SHP, StatParam.TOTNUMTIME, shapesAmounts[i], distinctShapesAmounts[j], shapeShowTimeSec[k]);
                    totTimeShapes[i] += sharedPreferences.getInt(curKeyTime, 0);
                    String curKeyTrain = TrainHelper.getStatParamKey(
                            Training.SHP, StatParam.TOTNUMTRAINS, shapesAmounts[i], distinctShapesAmounts[j], shapeShowTimeSec[k]);
                    totTrainShapes[i] += sharedPreferences.getInt(curKeyTrain, 0);
                }
            }
            avTimeShapes.add(round2((totTrainShapes[i] == 0) ? 0 : (double) totTimeShapes[i] / (double) totTrainShapes[i]));
        }
        int[] totTimeDistShapes = {0, 0, 0, 0, 0, 0, 0, 0};
        int[] totTrainDistShapes = {0, 0, 0, 0, 0, 0, 0, 0};
        for(int i = 0; i < distinctShapesAmounts.length; ++i){
            for(int j = 0; j < shapesAmounts.length; ++j){
                for(int k = 0; k < shapeShowTimeSec.length; ++k){
                    String curKeyTime = TrainHelper.getStatParamKey(
                            Training.SHP, StatParam.TOTNUMTIME, shapesAmounts[j], distinctShapesAmounts[i], shapeShowTimeSec[k]);
                    totTimeDistShapes[i] += sharedPreferences.getInt(curKeyTime, 0);
                    String curKeyTrain = TrainHelper.getStatParamKey(
                            Training.SHP, StatParam.TOTNUMTRAINS, shapesAmounts[j], distinctShapesAmounts[i], shapeShowTimeSec[k]);
                    totTrainDistShapes[i] += sharedPreferences.getInt(curKeyTrain, 0);
                }
            }
            avTimeDistShapes.add(round2((totTrainDistShapes[i] == 0) ? 0 : (double) totTimeDistShapes[i] / (double) totTrainDistShapes[i]));
        }
    }

    private void fillDataWrd(){
        avTimeWrd = new double[101][7];
        avMovesWrd = new double[101][7];
        for(int i = 0; i < 101; ++i){
            for (int j = 0; j < 7; ++j){
                String curKeyTime = TrainHelper.getStatParamKey(Training.WRD, StatParam.TOTNUMTIME, i, j);
                String curKeyMoves = TrainHelper.getStatParamKey(Training.WRD, StatParam.TOTNUMMOVES, i, j);
                String curKeyTrain = TrainHelper.getStatParamKey(Training.WRD, StatParam.TOTNUMTRAINS, i, j);
                if (sharedPreferences.getInt(curKeyTrain, 0) != 0){
                    avTimeWrd[i][j] = round2((double) sharedPreferences.getInt(curKeyTime, 0) /
                            (double) sharedPreferences.getInt(curKeyTrain, 0));
                    avMovesWrd[i][j] = round2((double) sharedPreferences.getInt(curKeyMoves, 0) /
                            (double) sharedPreferences.getInt(curKeyTrain, 0));
                }

            }

        }
    }

    private void fillDataPhr(){
        avTimePhr = new double[36][7];
        avMovesPhr = new double[36][7];
        for(int i = 0; i < 36; ++i){
            for (int j = 0; j < 7; ++j){
                String curKeyTime = TrainHelper.getStatParamKey(Training.PHR, StatParam.TOTNUMTIME, i, j);
                String curKeyMoves = TrainHelper.getStatParamKey(Training.PHR, StatParam.TOTNUMMOVES, i, j);
                String curKeyTrain = TrainHelper.getStatParamKey(Training.PHR, StatParam.TOTNUMTRAINS, i, j);
                if (sharedPreferences.getInt(curKeyTrain, 0) != 0){
                    avTimePhr[i][j] = round2((double) sharedPreferences.getInt(curKeyTime, 0) /
                            (double) sharedPreferences.getInt(curKeyTrain, 0));
                    avMovesPhr[i][j] = round2((double) sharedPreferences.getInt(curKeyMoves, 0) /
                            (double) sharedPreferences.getInt(curKeyTrain, 0));
                }

            }

        }
    }

    private void fillDataDet(){
        questionsCollectionsTitles = CollectionsStorage.getCollectionsTitles(
                sharedPreferences, getString(R.string.questions_collections_titles_key)
        );
        questionsCollectionsSecsMoves = new ArrayList<>();
        for(int i = 0; i < questionsCollectionsTitles.size(); ++i){
            int totNumSingleMist = 0, totNumMultipMist = 0, totNumTime = 0, totNumTrain = 0;
            String curKeySingleMist = TrainHelper.getStatParamKey(Training.DET, StatParam.TOTNUMSINGANS, questionsCollectionsTitles.get(i));
            totNumSingleMist = sharedPreferences.getInt(curKeySingleMist, 0);
            String curKeyMultipMist = TrainHelper.getStatParamKey(Training.DET, StatParam.TOTNUMMULTANS, questionsCollectionsTitles.get(i));
            totNumMultipMist = sharedPreferences.getInt(curKeyMultipMist, 0);
            String curKeyTime = TrainHelper.getStatParamKey(Training.DET, StatParam.TOTNUMTIME, questionsCollectionsTitles.get(i));
            totNumTime = sharedPreferences.getInt(curKeyTime, 0);
            String curKeyTrain = TrainHelper.getStatParamKey(Training.DET, StatParam.TOTNUMTRAINS, questionsCollectionsTitles.get(i));
            totNumTrain = sharedPreferences.getInt(curKeyTrain, 0);
            String rowStr = round2((totNumTrain == 0) ? 0 : (double) totNumTime / (double) totNumTrain) + " " +
                    getString(R.string.sec) + ", " +
                    round2((totNumTrain == 0) ? 0 : (double) totNumSingleMist / (double) totNumTrain) + " / " +
                    round2((totNumTrain == 0) ? 0 : (double) totNumMultipMist / (double) totNumTrain);
            questionsCollectionsSecsMoves.add(rowStr);
        }

    }

    private double round2(double num){
        return (double)Math.round(num * 100) / 100.0;
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}