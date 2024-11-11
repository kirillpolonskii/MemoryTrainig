package com.youngsophomore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.addisonelliott.segmentedbutton.SegmentedButton;
import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.youngsophomore.R;
import com.youngsophomore.data.Question;
import com.youngsophomore.fragments.InfoDialogFragment;
import com.youngsophomore.viewgroups.MyMotionLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Locale;

public class MainMenuActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "Gestures";
    private MyMotionLayout mtnLtMainMenu;
    String languageToLoad;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        SharedPreferences sharedPreferences =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        init(sharedPreferences);

        mtnLtMainMenu = findViewById(R.id.mtn_lt_main_m);
        ImageButton btnStats = findViewById(R.id.btn_stats);
        ImageButton btnInfo = findViewById(R.id.btn_info_main_m);
        SegmentedButtonGroup sgBtnGrSwitchLang = findViewById(R.id.sg_btn_gr_switch_lang);

        btnStats.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnStats onTouch. Action was UP. open statistics" +
                                ", R.dimen.btn_stats_elev = " + R.dimen.btn_stats_elev +
                                ", elev = " + elevPx);
                        view.setElevation(elevPx);
                        Intent intent = new Intent(getApplicationContext(), StatisticsActivity.class);
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }
            }
        });

        btnInfo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_info_elev);
                        Log.d(DEBUG_TAG, "btnInfo onTouch. Action was UP. open info" +
                                ", R.dimen.btn_info_elev = " + R.dimen.btn_info_elev +
                                ", elev = " + elevPx);
                        view.setElevation(elevPx);
                        showInfoDialog(R.layout.fragment_main_menu_info);
                        return true;
                    default:
                        return false;
                }
            }
        });

        int switchLangPos = sharedPreferences.getInt(getString(R.string.saved_lang_pos_key), 1);
        sgBtnGrSwitchLang.setPosition(switchLangPos, false);
        sgBtnGrSwitchLang.setOnPositionChangedListener(new SegmentedButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(final int position) {
                // Handle stuff here
                
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(getString(R.string.saved_lang_pos_key), position);
                editor.apply();
                if (position == 1) {
                    languageToLoad = "ru";// russian
                } else {
                    languageToLoad = "en";// english
                }

                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config, null);
                finish();
                startActivity(getIntent());
            }
        });

        SegmentedButton sgBtnEn = sgBtnGrSwitchLang.getButton(0);
        sgBtnEn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        
                        sgBtnGrSwitchLang.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_elev);
                        Log.d(DEBUG_TAG, "sgBtnEn onTouch. Action was UP. open statistics" +
                                ", R.dimen.sgbtn_elev = " + R.dimen.sgbtn_elev +
                                ", elev = " + elevPx);
                        sgBtnGrSwitchLang.setElevation(elevPx);
                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        
                        return true;
                    default:
                        return false;
                }
            }
        });
        SegmentedButton sgBtnRu = sgBtnGrSwitchLang.getButton(1);
        sgBtnRu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        
                        sgBtnGrSwitchLang.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        
                        return true;
                    case (MotionEvent.ACTION_UP):
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.sgbtn_elev);
                        Log.d(DEBUG_TAG, "sgBtnRu onTouch. Action was UP. open statistics" +
                                ", R.dimen.sgbtn_elev = " + R.dimen.sgbtn_elev +
                                ", elev = " + elevPx);
                        sgBtnGrSwitchLang.setElevation(elevPx);

                        return true;
                    case (MotionEvent.ACTION_CANCEL):
                        
                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    @Override
    public void onResume(){
        mtnLtMainMenu.transitionToState(R.id.base_state);
        super.onResume();
    }

    public void showInfoDialog(int layoutResource) {
        DialogFragment newFragment = new InfoDialogFragment(layoutResource);
        newFragment.show(getSupportFragmentManager(), "InfoDialogFragment");
    }

    private void init(SharedPreferences sharedPreferences){
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
        // create necessary directories
        File phrasesDir = new File(getExternalFilesDir(null).getAbsolutePath() + "/phrases");
        if (!phrasesDir.exists() && phrasesDir.mkdir()) {
            
        }
        else{
            
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
            
        }

        // init for details settings
        // make all necessary directories
        File detailsDir = new File(getExternalFilesDir(null).getAbsolutePath() + "/details");
        if (!detailsDir.exists() && detailsDir.mkdir()) {
            
        }
        else{
            
        }
        ArrayList<Question> questionCollection = new ArrayList<>();
        Question question1 = new Question();
        question1.setQuestionText("Что изображено на картине?");
        ArrayList<String> answers1 = new ArrayList<>();
        answers1.add("Квадрат +");
        answers1.add("Круг -");
        answers1.add("Треугольник -");
        question1.setAnswers(answers1);
        question1.setSingleAnswer(true);
        question1.putAnswersInOneString();
        questionCollection.add(question1);
        Question question2 = new Question();
        question2.setQuestionText("Какие цвета присутствуют на изображении?");
        ArrayList<String> answers2 = new ArrayList<>();
        answers2.add("Чёрный +");
        answers2.add("Белый +");
        answers2.add("Розовый -");
        question2.setAnswers(answers2);
        question2.setSingleAnswer(false);
        question2.putAnswersInOneString();
        questionCollection.add(question2);
        try {
            File questionsDir = new File(getExternalFilesDir(null).getAbsolutePath()
                    + "/details" + "/" + questionsCollectionTitle);
            //File phrasesDir = new File(context.getExternalFilesDir(null).getAbsolutePath() + "/phrases");
            if (!questionsDir.exists() && questionsDir.mkdir()) {
                
            }
            
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
            
        }

    }

}