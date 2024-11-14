package com.youngsophomore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.youngsophomore.R;
import com.youngsophomore.data.StatParam;
import com.youngsophomore.data.Training;
import com.youngsophomore.fragments.FinishDialogFragment;
import com.youngsophomore.fragments.StopwatchFragment;
import com.youngsophomore.helpers.PrepHelper;
import com.youngsophomore.helpers.TrainHelper;

import java.util.ArrayList;

@SuppressLint("MissingInflatedId")
public class MahjongTrainingActivity extends AppCompatActivity implements
        FinishDialogFragment.FinishDialogListener {
    CountDownTimer countDownTimer, showCountdownTimer;
    private static final String STOPWATCH_FRAGMENT_TAG = "stopwatch_fragment_tag";
    private ArrayList<ImageButton> flippedTiles;
    private ArrayList<Integer> flippedTilesNum;
    private int removedTilesCount = 0;
    private int mistakesAmount = 0;
    private int trainingDurationSec = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pretrain_countdown_layout);

        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        int mhjTilesAmount = PrepHelper.Mahjong.sgBtnGroupTilesPosToAmount(
                sharedPreferences.getInt(getString(R.string.saved_mahjong_tiles_amount_key), 0));
        int mhjEqualTilesAmount = PrepHelper.Mahjong.sgBtnGroupEqualTilesPosToAmount(
                sharedPreferences.getInt(getString(R.string.saved_mahjong_equal_tiles_amount_key), 0));
        int mhjShowTime = sharedPreferences.getInt(getString(R.string.saved_mahjong_remember_time_key), 2);

        ArrayList<Integer> tilesNum = TrainHelper.Mahjong.generateTiles(mhjTilesAmount, mhjEqualTilesAmount);
        TextView tvCountdown = findViewById(R.id.tv_countdown);
        countDownTimer = new CountDownTimer(3000 + 200, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                
                tvCountdown.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                if(mhjTilesAmount == 12){
                    setContentView(R.layout.activity_mahjong_training_12);
                }
                else{
                    setContentView(R.layout.activity_mahjong_training_24);
                }
                
                ConstraintLayout constraintLayout = findViewById(R.id.cst_lt_chld_mhj);

                ArrayList<ImageButton> btnTiles = new ArrayList<>();
                for(int i = 0; i < constraintLayout.getChildCount(); ++i){
                    btnTiles.add((ImageButton) constraintLayout.getChildAt(i));
                }
                ArrayList<Integer> btnBackgroundResources = tilesNumToBackgroundRes(tilesNum);
                for (int i = 0; i < btnBackgroundResources.size(); ++i){
                    btnTiles.get(i).setImageResource(btnBackgroundResources.get(i));
                }
                showCountdownTimer = new CountDownTimer(mhjShowTime * 1000 + 200, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {}
                    @Override
                    public void onFinish() {
                        Bundle bundle = new Bundle();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .setReorderingAllowed(true)
                                .add((mhjTilesAmount == 12) ? R.id.frt_cnt_v_stopwatch_mhj_12 : R.id.frt_cnt_v_stopwatch_mhj_24,
                                        StopwatchFragment.class, bundle, STOPWATCH_FRAGMENT_TAG)
                                .commit();
                        for (int i = 0; i < btnBackgroundResources.size(); ++i){
                            btnTiles.get(i).setImageResource(R.drawable.tile_back);
                        }
                        flippedTiles = new ArrayList<>();
                        flippedTilesNum = new ArrayList<>();
                        for (int i = 0; i < btnBackgroundResources.size(); ++i){
                            int finalI = i;
                            btnTiles.get(i).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    btnTiles.get(finalI).setImageResource(btnBackgroundResources.get(finalI));
                                    v.setClickable(false);
                                    int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_info_elev);
                                    v.setElevation(elevPx);
                                    if (flippedTiles.size() < mhjEqualTilesAmount - 1){
                                        flippedTiles.add((ImageButton) v);
                                        flippedTilesNum.add(tilesNum.get(finalI));
                                    }
                                    else{
                                        for (ImageButton btnTile : btnTiles){
                                            btnTile.setClickable(false);
                                        }
                                        flippedTiles.add((ImageButton) v);
                                        flippedTilesNum.add(tilesNum.get(finalI));
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                if(isAllTilesEqual()){
                                                    for(ImageButton flippedBtnTile : flippedTiles){
                                                        flippedBtnTile.setClickable(false);
                                                        flippedBtnTile.setVisibility(View.INVISIBLE);
                                                        ++removedTilesCount;
                                                    }
                                                    if (removedTilesCount == mhjTilesAmount){
                                                        StopwatchFragment stopwatchFragment =
                                                                (StopwatchFragment) fragmentManager.findFragmentByTag(STOPWATCH_FRAGMENT_TAG);
                                                        trainingDurationSec = stopwatchFragment.getDecisecond() / 10;
                                                        stopwatchFragment.finishStopwatch();
                                                        TrainHelper.updateStatParams(sharedPreferences,
                                                                new Pair<>(
                                                                        TrainHelper.getStatParamKey(Training.MHJ, StatParam.TOTNUMMOVES, mhjTilesAmount, mhjEqualTilesAmount, mhjShowTime),
                                                                        mistakesAmount
                                                                ),
                                                                new Pair<>(
                                                                        TrainHelper.getStatParamKey(Training.MHJ, StatParam.TOTNUMTIME, mhjTilesAmount, mhjEqualTilesAmount, mhjShowTime),
                                                                        trainingDurationSec
                                                                ),
                                                                new Pair<>(
                                                                        TrainHelper.getStatParamKey(Training.MHJ, StatParam.TOTNUMTRAINS, mhjTilesAmount, mhjEqualTilesAmount, mhjShowTime),
                                                                        1
                                                                ));

                                                        DialogFragment finishFragment = new FinishDialogFragment(
                                                                trainingDurationSec + " с.",
                                                                getResources().getString(R.string.seq_train_mistakes_amount),
                                                                String.valueOf(mistakesAmount)
                                                        );
                                                        finishFragment.show(getSupportFragmentManager(), "FinishDialogFragment");
                                                    }

                                                }
                                                else{
                                                    ++mistakesAmount;
                                                    for(ImageButton flippedBtnTile : flippedTiles){
                                                        flippedBtnTile.setImageResource(R.drawable.tile_back);
                                                        flippedBtnTile.setElevation(0);

                                                    }
                                                }
                                                flippedTiles.clear();
                                                flippedTilesNum.clear();
                                                for (ImageButton btnTile : btnTiles){
                                                    btnTile.setClickable(true);
                                                }
                                            }
                                        }, 600);

                                    }
                                }
                            });
                        }
                    }
                }.start();
            }
        }.start();
    }

    private ArrayList<Integer> tilesNumToBackgroundRes(ArrayList<Integer> tilesNum){
        ArrayList<Integer> tilesBackgroundResources = new ArrayList<>();
        for (Integer tileNum : tilesNum){
            switch(tileNum){
                case (1):
                    tilesBackgroundResources.add(R.drawable.tile_1);
                    break;
                case (2):
                    tilesBackgroundResources.add(R.drawable.tile_2);
                    break;
                case (3):
                    tilesBackgroundResources.add(R.drawable.tile_3);
                    break;
                case (4):
                    tilesBackgroundResources.add(R.drawable.tile_4);
                    break;
                case (5):
                    tilesBackgroundResources.add(R.drawable.tile_5);
                    break;
                case (6):
                    tilesBackgroundResources.add(R.drawable.tile_6);
                    break;
                case (7):
                    tilesBackgroundResources.add(R.drawable.tile_7);
                    break;
                case (8):
                    tilesBackgroundResources.add(R.drawable.tile_8);
                    break;
                case (9):
                    tilesBackgroundResources.add(R.drawable.tile_9);
                    break;
                case (10):
                    tilesBackgroundResources.add(R.drawable.tile_10);
                    break;
                case (11):
                    tilesBackgroundResources.add(R.drawable.tile_11);
                    break;
                case (12):
                    tilesBackgroundResources.add(R.drawable.tile_12);
                    break;
                case (13):
                    tilesBackgroundResources.add(R.drawable.tile_13);
                    break;
            }
        }
        return tilesBackgroundResources;
    }

    private boolean isAllTilesEqual(){
        boolean correctAnswer = true;
        for(int i = 0; i < flippedTilesNum.size() - 1; ++i){
            if (flippedTilesNum.get(i).intValue() != flippedTilesNum.get(i + 1).intValue()){
                correctAnswer = false;
                break;
            }
        }
        return correctAnswer;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (countDownTimer != null){
            countDownTimer.cancel();
        }
        if (showCountdownTimer != null){
            showCountdownTimer.cancel();
        }
    }

    @Override
    public void onFinishPosClick(DialogFragment dialog) {
        onBackPressed();
    }
}