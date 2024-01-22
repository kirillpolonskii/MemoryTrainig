package com.youngsophomore.data;
/*
* Класс оборачивает операции записи коллекций в текстовые файлы
* */

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.ArrayMap;
import android.util.Log;

import com.youngsophomore.R;

import java.util.ArrayList;
import java.util.Arrays;

public class CollectionsStorage {
    private static final String DEBUG_TAG = "Gestures";
    private static ArrayList<String> wordsCollectionsTitles;
    private static ArrayList<ArrayList<String>> wordsCollections;
    private static ArrayList<String> phrasesCollectionsTitles;
    private static ArrayList<ArrayList<String>> phrasesCollections;

    private static ArrayList<String> questionsCollectionsTitles; // collection named after image
    private static ArrayList<ArrayList<Question>> questionsCollections;
    private static ArrayMap<String, Uri> imageNamesForUri;


//    static {
//        Log.d(DEBUG_TAG, "in CollectionsStorage: static worked");
//        wordsCollectionsTitles = new ArrayList<>();
//        wordsCollectionsTitles.add("FirstWordsCollection");
//        wordsCollections = new ArrayList<>();
//
//        phrasesCollectionsTitles = new ArrayList<>();
//        phrasesCollectionsTitles.add("FirstPhrasesCollection");
//        phrasesCollections = new ArrayList<>();
//
//        questionsCollectionsTitles = new ArrayList<>();
//        questionsCollectionsTitles.add("Image1");
//        questionsCollections = new ArrayList<>();
//        imageNamesForUri = new ArrayMap<>();
//    }

    public void addWordsCollection(String title, String newCollection, SharedPreferences sharedPreferences){
        // add title to Set<String> in shared preferences
        //SharedPreferences sharedPreferences =
        //        context.getSharedPreferences(android.content.Context.getString(R.string.preference_file_key), android.content.Context.MODE_PRIVATE);
        // write newCollection to title.txt
        wordsCollectionsTitles.add(title);
        String[] splittedNewCollection = newCollection.split(" ");
        ArrayList<String> newCollectionArray = new ArrayList<>(Arrays.asList(splittedNewCollection));

        wordsCollections.add(newCollectionArray);
    }

    public static ArrayList<String> getWordsCollectionsTitles(SharedPreferences sharedPreferences, String key){
        // get Set<String> from shared preferences
        String strWordsCollectionsTitles = sharedPreferences.getString(key, "");
        String[] splittedWordsCollectionsTitles = strWordsCollectionsTitles.split(",");
        ArrayList<String> newCollectionArray = new ArrayList<>(Arrays.asList(splittedWordsCollectionsTitles));
        return newCollectionArray;
    }

    public static String getWordsCollectionTitle(int position){
        // get collection from shared preferences and get title
        return wordsCollectionsTitles.get(position);
    }

    public static void addPhrasesCollection(String title, ArrayList<String> newCollection){
        // add title to collection in shared preferences

        // write newCollection in title.txt
        phrasesCollectionsTitles.add(title);

        phrasesCollections.add(newCollection);
    }

    public static ArrayList<String> getPhrasesCollectionsTitles(){
        // get collection of titles from shared preferences
        return phrasesCollectionsTitles;
    }

    public static String getPhrasesCollectionTitle(int position){
        // get collection of titles from shared preferences and get the title
        return phrasesCollectionsTitles.get(position);
    }

    public static void addQuestionsCollections(String title, Uri imageUri, ArrayList<Question> newCollection){
        // add a folder named "title"
        // write uri in title/imageUri
        // write questionNN.txt in title/
        questionsCollectionsTitles.add(title);
        questionsCollections.add(newCollection);
        imageNamesForUri.put(title, imageUri);
    }

    public static ArrayList<String> getQuestionsCollectionsTitles(){
        // get collection of titles from shared preferences
        return questionsCollectionsTitles;
    }


}
