package com.youngsophomore.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.youngsophomore.R;
import com.youngsophomore.data.CollectionsStorage;

public class DisplayWordsSettingsFragment extends Fragment
implements DeleteCollectionDialogFragment.DeleteCollectionDialogListener{
    private static final String DEBUG_TAG = "Gestures";
    Spinner sprWordsCollection;
    SharedPreferences sharedPreferences;
    ArrayAdapter<String> adapter;

    public DisplayWordsSettingsFragment() {
        // Required empty public constructor
    }


    public static DisplayWordsSettingsFragment newInstance(String param1, String param2) {
        return new DisplayWordsSettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment = inflater.inflate(R.layout.fragment_display_words_settings, container, false);

        sharedPreferences =
                getContext().getSharedPreferences(getString(R.string.preference_file_key), android.content.Context.MODE_PRIVATE);
        int wordsCollectionPosition = sharedPreferences.getInt(getString(R.string.saved_words_collection_position_key), 0);
        int wordShowTime = sharedPreferences.getInt(getString(R.string.saved_word_show_time_key), 2);

        sprWordsCollection = fragment.findViewById(R.id.spr_wrd_collection);
        NumberPicker pckrWordShowTime = fragment.findViewById(R.id.num_pck_wrd_show_time);

        adapter = new ArrayAdapter<>(fragment.getContext(),
                R.layout.custom_spinner_item,
                CollectionsStorage.getCollectionsTitles(sharedPreferences, getString(R.string.words_collections_titles_key)));
        adapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        sprWordsCollection.setAdapter(adapter);
        sprWordsCollection.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d(DEBUG_TAG, "In DisplayWordsSettingsFragment: Long click on spinner itself");
                if(adapter.getCount() > 1){
                    showDeleteCollectionDialog();
                }
                else{
                    Toast.makeText(getContext(), getString(R.string.msg_forbid_delete_collection),
                            Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });

        sprWordsCollection.setSelection(wordsCollectionPosition);

        pckrWordShowTime.setMinValue(1);
        pckrWordShowTime.setMaxValue(6);
        pckrWordShowTime.setValue(wordShowTime);
        return fragment;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void showDeleteCollectionDialog(){
        DialogFragment newFragment = new DeleteCollectionDialogFragment();
        newFragment.show(getChildFragmentManager(), "DeleteCollectionDialogFragment");
    }
    @Override
    public void onDeleteCollectionPosClick(DialogFragment dialog) {
        Log.d(DEBUG_TAG, "In DisplayWordsSettingsFragment: Pos button clicked, slctd= " +
                sprWordsCollection.getSelectedItem());
        CollectionsStorage.deleteWordsCollection((String) sprWordsCollection.getSelectedItem(),
                getString(R.string.words_collections_titles_key),
                getString(R.string.saved_words_collection_position_key),
                sharedPreferences);
        adapter.remove((String) sprWordsCollection.getSelectedItem());
        adapter.notifyDataSetChanged();
        sprWordsCollection.setSelection(0);
    }

    @Override
    public void onDeleteCollectionNegClick(DialogFragment dialog) {
        Log.d(DEBUG_TAG, "In DisplayWordsSettingsFragment: Neg button clicked");
    }
}