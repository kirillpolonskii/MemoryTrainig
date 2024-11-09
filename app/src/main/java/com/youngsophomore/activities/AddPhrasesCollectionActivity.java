package com.youngsophomore.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.youngsophomore.R;
import com.youngsophomore.data.CollectionsStorage;
import com.youngsophomore.fragments.AddPhraseFragment;
import com.youngsophomore.fragments.AddWordsCollectionFragment;
import com.youngsophomore.fragments.DisplayWordsSettingsFragment;
import com.youngsophomore.fragments.InfoDialogFragment;
import com.youngsophomore.fragments.NewPhrasesListFragment;
import com.youngsophomore.helpers.PrepHelper;

import java.util.ArrayList;

public class AddPhrasesCollectionActivity extends AppCompatActivity {
    private final String NEW_PHRASES_LIST_FRAGMENT_TAG = "new_phrases_list_fragment";
    private final String ADD_PHRASE_FRAGMENT_TAG = "add_phrase_fragment";
    private static final String DEBUG_TAG = "Gestures";
    FragmentManager fragmentManager;
    int elevPx;
    ImageButton btnAddPhrase;
    ImageButton btnConfirmPhrase;
    ImageButton btnConfirmPhrasesCollection;
    private ArrayList<CharSequence> newPhrasesCollectionCharS;
    private EditText etPhrasesCollectionTitle;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phrases_collection);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.tbr_phrases_add_collection_title));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = new Bundle();
        newPhrasesCollectionCharS = new ArrayList<>();
        bundle.putCharSequenceArrayList(getString(R.string.new_phrases_collection_key), newPhrasesCollectionCharS);
        elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.frt_cnt_v_tiles, NewPhrasesListFragment.class, bundle, NEW_PHRASES_LIST_FRAGMENT_TAG)
                .commit();

        btnAddPhrase = findViewById(R.id.btn_add_phrase);
        btnConfirmPhrase = findViewById(R.id.btn_confirm_phrase);
        btnConfirmPhrasesCollection = findViewById(R.id.btn_confirm_phrases_collection);

        PrepHelper.deactivateBtn(btnConfirmPhrase);

        btnAddPhrase.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnAddPhrase onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnAddPhrase onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        Log.d(DEBUG_TAG, "btnAddPhrase onTouch. Action was UP");
                        view.setElevation(elevPx);

                        PrepHelper.deactivateBtn(btnAddPhrase);
                        PrepHelper.deactivateBtn(btnConfirmPhrasesCollection);
                        PrepHelper.activateBtn(btnConfirmPhrase, elevPx);

                        fragmentManager.beginTransaction()
                                .replace(R.id.frt_cnt_v_tiles, AddPhraseFragment.class, null, ADD_PHRASE_FRAGMENT_TAG)
                                .setReorderingAllowed(true)
                                .addToBackStack("transaction_add_words_collection_fragment")
                                .commit();

                        /*SharedPreferences.Editor editor = sharedPreferences.edit();*/
                        return true;
                    default:
                        return false;
                }
            }
        });


        btnConfirmPhrase.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnConfirmPhrase onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnConfirmPhrase onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        Log.d(DEBUG_TAG, "btnConfirmPhrase onTouch. Action was UP");
                        view.setElevation(elevPx);
                        PrepHelper.activateBtn(btnAddPhrase, elevPx);
                        PrepHelper.activateBtn(btnConfirmPhrasesCollection, elevPx);
                        PrepHelper.deactivateBtn(btnConfirmPhrase);

                        Fragment newPhraseFragment = fragmentManager.findFragmentByTag(ADD_PHRASE_FRAGMENT_TAG);
                        EditText etNewPhrase = newPhraseFragment.getView().findViewById(R.id.et_new_phrase);
                        if (!etNewPhrase.getText().toString().equals("")){
                            newPhrasesCollectionCharS.add(etNewPhrase.getText().toString());
                        }
                        bundle.putCharSequenceArrayList(getString(R.string.new_phrases_collection_key), newPhrasesCollectionCharS);
                        fragmentManager.beginTransaction()
                                .replace(R.id.frt_cnt_v_tiles, NewPhrasesListFragment.class, bundle, NEW_PHRASES_LIST_FRAGMENT_TAG)
                                .setReorderingAllowed(true)
                                .commit();
                        fragmentManager.popBackStack();
                        /*SharedPreferences.Editor editor = sharedPreferences.edit();*/
                        return true;
                    default:
                        return false;
                }
            }
        });

        etPhrasesCollectionTitle = findViewById(R.id.et_phrases_collection_title);

        btnConfirmPhrasesCollection.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                int action = event.getAction();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN):
                        Log.d(DEBUG_TAG, "btnConfirmPhrasesCollection onTouch. Action was DOWN");
                        view.setElevation(0);
                        return true;
                    case (MotionEvent.ACTION_MOVE):
                        Log.d(DEBUG_TAG, "btnConfirmPhrasesCollection onTouch. Action was MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP):
                        Log.d(DEBUG_TAG, "btnConfirmPhrasesCollection onTouch. Action was UP");
                        view.setElevation(elevPx);
                        ArrayList<String> newPhrasesCollection = new ArrayList<>();
                        for(CharSequence phraseCharS : newPhrasesCollectionCharS){
                            newPhrasesCollection.add(String.valueOf(phraseCharS));
                        }
                        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), MODE_PRIVATE);
                        String newTitle = etPhrasesCollectionTitle.getText().toString();
                        String strPhrasesCollectionsTitles =
                                sharedPreferences.getString(getString(R.string.phrases_collections_titles_key), "");
                        if (newTitle.equals("") || newPhrasesCollectionCharS.size() < 2){
                            onBackPressed();
                        }
                        else{
                            if(PrepHelper.isCollectionTitleUnique(strPhrasesCollectionsTitles, newTitle)){
                                CollectionsStorage.savePhrasesCollection(
                                        newTitle,
                                        newPhrasesCollection,
                                        getString(R.string.phrases_collections_titles_key),
                                        sharedPreferences,
                                        getApplicationContext()
                                );
                                onBackPressed();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),
                                        getString(R.string.msg_collection_title_not_unique),
                                        Toast.LENGTH_LONG).show();
                            }
                        }

                        return true;
                    default:
                        return false;
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.mi_btn_info) {
            Log.d(DEBUG_TAG, "info button in ShapesSettingsActivity");
            showInfoDialog(R.layout.fragment_add_phrases_info);
            return true;
        }
        return false;
    }
    public void showInfoDialog(int layoutResource) {
        DialogFragment newFragment = new InfoDialogFragment(layoutResource);
        newFragment.show(getSupportFragmentManager(), "InfoDialogFragment");
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Log.d(DEBUG_TAG, "in onBackPressed()");
        AddPhraseFragment addPhraseFragment =
                (AddPhraseFragment) fragmentManager.findFragmentByTag(ADD_PHRASE_FRAGMENT_TAG);
        NewPhrasesListFragment newPhrasesListFragment =
                (NewPhrasesListFragment) fragmentManager.findFragmentByTag(NEW_PHRASES_LIST_FRAGMENT_TAG);
        Log.d(DEBUG_TAG, addPhraseFragment + ",, " + newPhrasesListFragment);
        if(addPhraseFragment != null && addPhraseFragment.isVisible()){
            PrepHelper.activateBtn(btnAddPhrase, elevPx);
            PrepHelper.activateBtn(btnConfirmPhrasesCollection, elevPx);
            PrepHelper.deactivateBtn(btnConfirmPhrase);
        }
        super.onBackPressed();
    }
}