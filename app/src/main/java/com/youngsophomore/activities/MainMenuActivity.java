package com.youngsophomore.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.MotionEventCompat;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.addisonelliott.segmentedbutton.SegmentedButton;
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.youngsophomore.R;
import com.youngsophomore.data.CollectionsStorage;
import com.youngsophomore.data.Question;
import com.youngsophomore.viewgroups.MyMotionLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "Gestures";
    private MyMotionLayout myMotionLayout;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Log.d(DEBUG_TAG, "IN onCreate()");
        init(getApplicationContext());

        myMotionLayout = findViewById(R.id.motion_layout);

        ImageButton btnStats = findViewById(R.id.btn_stats);
        btnStats.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnStats onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnStats onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnStats onTouch. Action was UP. open statistics" +
                                ", R.dimen.btn_stats_elev = " + R.dimen.btn_stats_elev +
                                ", elev = " + elevPx);
                        view.setElevation(elevPx);
                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d(DEBUG_TAG, "btnStats onTouch. Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d(DEBUG_TAG, "btnStats onTouch. Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default:
                        return false;
                }
            }
        });
        ImageButton btnInfo = findViewById(R.id.btn_info);
        btnInfo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnInfo onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnInfo onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_info_elev);
                        Log.d(DEBUG_TAG, "btnInfo onTouch. Action was UP. open info" +
                                ", R.dimen.btn_info_elev = " + R.dimen.btn_info_elev +
                                ", elev = " + elevPx);
                        view.setElevation(elevPx);
                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d(DEBUG_TAG, "btnInfo onTouch. Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d(DEBUG_TAG, "btnInfo onTouch. Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default:
                        return false;
                }
            }
        });
        SegmentedButtonGroup sgBtnGroup = findViewById(R.id.sgbtn_enru);
        sgBtnGroup.setPosition(1, false);
        sgBtnGroup.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(final int position) {
                // Handle stuff here
                Log.d(DEBUG_TAG, "setOnPositionChangedListener: position = " + position);
                //sgBtnGroup.setElevation(0);
            }
        });

        SegmentedButton sgBtnEn = sgBtnGroup.getButton(0);
        sgBtnEn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "sgBtnEn onTouch. Action was DOWN");
                        sgBtnGroup.setElevation(0);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "sgBtnEn onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_elev);
                        Log.d(DEBUG_TAG, "sgBtnEn onTouch. Action was UP. open statistics" +
                                ", R.dimen.sgbtn_elev = " + R.dimen.sgbtn_elev +
                                ", elev = " + elevPx);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        sgBtnGroup.setElevation(elevPx);

                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d(DEBUG_TAG, "sgBtnEn onTouch. Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d(DEBUG_TAG, "sgBtnEn onTouch. Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default:
                        return false;
                }
            }
        });
        SegmentedButton sgBtnRu = sgBtnGroup.getButton(1);
        sgBtnRu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "sgBtnRu onTouch. Action was DOWN");
                        sgBtnGroup.setElevation(0);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "sgBtnRu onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_elev);
                        Log.d(DEBUG_TAG, "sgBtnRu onTouch. Action was UP. open statistics" +
                                ", R.dimen.sgbtn_elev = " + R.dimen.sgbtn_elev +
                                ", elev = " + elevPx);
                        //sgBtnGroup.onTouchEvent(motionEvent);
                        sgBtnGroup.setElevation(elevPx);

                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        Log.d(DEBUG_TAG, "sgBtnRu onTouch. Action was CANCEL");
                        return true;
                    case (MotionEvent.ACTION_OUTSIDE):
                        Log.d(DEBUG_TAG, "sgBtnRu onTouch. Movement occurred outside bounds " +
                                "of current screen element");
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    @Override
    public void onResume(){
        myMotionLayout.transitionToState(R.id.base_state);
        super.onResume();
    }

    private void init(Context context){
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
        boolean firstLaunch =
                sharedPreferences.getBoolean(getString(R.string.first_launch_key), true);
        if(!firstLaunch){
            return;
        }
        // init for words settings
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.first_launch_key), false);
        String wordsCollectionTitle = "first words collection";
        String strWordsCollectionsTitles = "," + wordsCollectionTitle + ",";
        editor.putString(getString(R.string.words_collections_titles_key),
                strWordsCollectionsTitles);
        String initWordsCollection = getString(R.string.init_words_collection);
        editor.putString(wordsCollectionTitle, initWordsCollection);
        // init for phrase settings
        String phrasesCollectionTitle = "first phrases collection";
        String strPhrasesCollectionsTitles = "," + phrasesCollectionTitle + ",";
        editor.putString(getString(R.string.phrases_collections_titles_key),
                strPhrasesCollectionsTitles);

        String questionsCollectionTitle = "first image";
        String strQuestionsCollectionsTitles = "," + questionsCollectionTitle + ",";
        editor.putString(getString(R.string.questions_collections_titles_key),
                strQuestionsCollectionsTitles);
        editor.apply();
        // make necessary directories
        File phrasesDir = new File(getExternalFilesDir(null).getAbsolutePath() + "/phrases");
        if (!phrasesDir.exists() && phrasesDir.mkdir()) {
            Log.d(DEBUG_TAG, "in MainMenuActivity: " + phrasesDir + " created");
        }
        else{
            Log.d(DEBUG_TAG, "in MainMenuActivity: " + phrasesDir + "existed or was NOT created");
        }

        try {
            String fileName = "/" + phrasesCollectionTitle + ".txt";
            File outFile = new File(phrasesDir, fileName);
            if (!outFile.exists() && outFile.createNewFile()) {
                Log.d(DEBUG_TAG, "in MainMenuActivity: " + outFile.getAbsolutePath() +
                        " did NOT exist and was created");
            }
            else{
                Log.d(DEBUG_TAG, "in MainMenuActivity: " + outFile.getAbsolutePath() +
                        " existed or was NOT created");
            }
            FileOutputStream fos = new FileOutputStream(outFile);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Log.d(DEBUG_TAG, "in CollectionStorage: fileName = " + outFile);
            ArrayList<String> phrasesCollection = new ArrayList<>();
            phrasesCollection.add("Спасибо за");
            phrasesCollection.add("использование приложения");
            phrasesCollection.add("надеюсь, вы");
            phrasesCollection.add("найдёте его полезным");
            for(String phrase : phrasesCollection){
                osw.write(phrase);
                osw.write("|");
            }
            osw.flush();
            osw.close();
            fos.close();
        }
        catch (IOException e) {
            Log.d(DEBUG_TAG, "in CollectionStorage: File write failed: " + e.toString());
        }

        // init for details settings
        // make all necessary directories
        File detailsDir = new File(getExternalFilesDir(null).getAbsolutePath() + "/details");
        if (!detailsDir.exists() && detailsDir.mkdir()) {
            Log.d(DEBUG_TAG, "in MainMenuActivity: " + detailsDir + " created");
        }
        else{
            Log.d(DEBUG_TAG, "in MainMenuActivity: " + detailsDir + "existed or was NOT created");
        }
        ArrayList<Question> questionCollection = new ArrayList<>();
        Question question1 = new Question();
        question1.setQuestionText("Текст первого вопроса?");
        ArrayList<String> answers1 = new ArrayList<>();
        answers1.add("Ответ11 +");
        answers1.add("Ответ12 -");
        answers1.add("Ответ13 -");
        question1.setAnswers(answers1);
        question1.setSingleAnswer(true);
        question1.putAnswersInOneString();
        questionCollection.add(question1);
        Question question2 = new Question();
        question2.setQuestionText("Текст второго вопроса?");
        ArrayList<String> answers2 = new ArrayList<>();
        answers2.add("Ответ21 +");
        answers2.add("Ответ22 +");
        answers2.add("Ответ23 -");
        question2.setAnswers(answers2);
        question2.setSingleAnswer(false);
        question2.putAnswersInOneString();
        questionCollection.add(question2);
        try {
            File questionsDir = new File(getExternalFilesDir(null).getAbsolutePath()
                    + "/details" + "/" + questionsCollectionTitle);
            //File phrasesDir = new File(context.getExternalFilesDir(null).getAbsolutePath() + "/phrases");
            if (!questionsDir.exists() && questionsDir.mkdir()) {
                Log.d(DEBUG_TAG, "in MainMenuActivity: " + questionsDir + " created");
            }
            Log.d(DEBUG_TAG, "in MainMenuActivity: questionsDir = " + questionsDir);
            for(int i = 0; i < questionCollection.size(); ++i){
                String questionNum = "question";
                if(i < 10){
                    questionNum += "00" + i;
                }
                else if(i < 100){
                    questionNum += "0" + i;
                }
                else{
                    questionNum += String.valueOf(i);
                }
                String fileName = "/" + questionNum + ".txt";
                File outFile = new File(questionsDir, fileName);
                if (!outFile.exists() && outFile.createNewFile()) {
                    Log.d(DEBUG_TAG, "in MainMenuActivity: " + outFile.getAbsolutePath() +
                            " did NOT exist and was created");
                }
                else{
                    Log.d(DEBUG_TAG, "in MainMenuActivity: " + outFile.getAbsolutePath() +
                            " existed or was NOT created");
                }
                FileOutputStream fos = new FileOutputStream(outFile);
                OutputStreamWriter osw = new OutputStreamWriter(fos);
                Log.d(DEBUG_TAG, "in MainMenuActivity: fileName = " + outFile);
                osw.write(questionCollection.get(i).getQuestionText());
                osw.write("\n");
                osw.write(questionCollection.get(i).getAnswersInOneString(false));
                osw.flush();
                osw.close();
                fos.close();
            }

            String imageName = "/details_test_image.png";
            File testImage = new File(questionsDir, imageName);
            InputStream inputStream = getResources().openRawResource(R.raw.details_test_img);
            OutputStream out = new FileOutputStream(testImage);
            byte buf[] = new byte[1024];
            int len;
            while((len=inputStream.read(buf))>0) {
                out.write(buf, 0, len);
            }
            out.close();
            inputStream.close();
            Uri detailsTestImageUri = Uri.fromFile(testImage);
            editor.putString(questionsCollectionTitle, detailsTestImageUri.toString());
            editor.apply();
        }
        catch (IOException e) {
            Log.d(DEBUG_TAG, "in MainMenuActivity: File write failed: " + e.toString());
        }

    }


}