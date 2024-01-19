package com.youngsophomore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.youngsophomore.R;
import com.youngsophomore.adapters.PhrasesAdapter;
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

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.frgt_view, NewPhrasesListFragment.class, bundle, NEW_PHRASES_LIST_FRAGMENT_TAG)
                .commit();

        ImageButton btnAddPhrase = findViewById(R.id.btn_add_phrase);
        ImageButton btnConfirmPhrase = findViewById(R.id.btn_confirm_phrase);
        ImageButton btnConfirmPhrasesCollection = findViewById(R.id.btn_confirm_phrases_collection);

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
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnAddPhrase onTouch. Action was UP");
                        view.setElevation(elevPx);

                        PrepHelper.deactivateBtn(btnAddPhrase);
                        PrepHelper.deactivateBtn(btnConfirmPhrasesCollection);
                        PrepHelper.activateBtn(btnConfirmPhrase, elevPx);

                        fragmentManager.beginTransaction()
                                .replace(R.id.frgt_view, AddPhraseFragment.class, null, ADD_PHRASE_FRAGMENT_TAG)
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
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnConfirmPhrase onTouch. Action was UP");
                        view.setElevation(elevPx);
                        PrepHelper.activateBtn(btnAddPhrase, elevPx);
                        PrepHelper.activateBtn(btnConfirmPhrase, elevPx);
                        PrepHelper.deactivateBtn(btnConfirmPhrase);

                        Fragment newPhraseFragment = fragmentManager.findFragmentByTag(ADD_PHRASE_FRAGMENT_TAG);
                        EditText etNewPhrase = newPhraseFragment.getView().findViewById(R.id.et_new_phrase);
                        newPhrasesCollectionCharS.add(etNewPhrase.getText().toString());
                        bundle.putCharSequenceArrayList(getString(R.string.new_phrases_collection_key), newPhrasesCollectionCharS);
                        fragmentManager.beginTransaction()
                                .replace(R.id.frgt_view, NewPhrasesListFragment.class, bundle, NEW_PHRASES_LIST_FRAGMENT_TAG)
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
                        int elevPx = getResources().getDimensionPixelSize(R.dimen.btn_stats_elev);
                        Log.d(DEBUG_TAG, "btnConfirmPhrasesCollection onTouch. Action was UP");
                        view.setElevation(elevPx);
                        ArrayList<String> newPhrasesCollection = new ArrayList<>();
                        for(CharSequence phraseCharS : newPhrasesCollectionCharS){
                            newPhrasesCollection.add(String.valueOf(phraseCharS));
                        }
                        CollectionsStorage.addPhrasesCollection(
                                etPhrasesCollectionTitle.getText().toString(), newPhrasesCollection);
                        onBackPressed();
                        /*SharedPreferences.Editor editor = sharedPreferences.edit();*/
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
}