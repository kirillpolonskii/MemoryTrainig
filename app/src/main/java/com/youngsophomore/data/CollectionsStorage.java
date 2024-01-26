package com.youngsophomore.data;
/*
* Класс оборачивает операции записи коллекций в текстовые файлы
* */

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.util.ArrayMap;
import android.util.Log;

import com.youngsophomore.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

    public static void addWordsCollection(
            String title,
            String newCollection,
            String strWordsCollectionsTitles,
            SharedPreferences sharedPreferences,
            String wordsCollectionsTitlesKey){
        // add title to Set<String> in shared preferences
        strWordsCollectionsTitles += title + ",";
        Log.d(DEBUG_TAG, "in CollectionStorage: strWordsCollectionsTitles = " + strWordsCollectionsTitles);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(wordsCollectionsTitlesKey, strWordsCollectionsTitles);
        editor.putString(title, newCollection);
        editor.apply();
    }

    public static ArrayList<String> getWordsCollectionsTitles(SharedPreferences sharedPreferences, String key){
        // get Set<String> from shared preferences
        String strWordsCollectionsTitles = sharedPreferences.getString(key, "");
        String[] splittedWordsCollectionsTitles = strWordsCollectionsTitles.split(",");
        ArrayList<String> newCollectionArray = new ArrayList<>(Arrays.asList(splittedWordsCollectionsTitles));
        newCollectionArray.remove(0);
        return newCollectionArray;
    }

    public static String getWordsCollectionTitle(int position){
        // get collection from shared preferences and get title
        return wordsCollectionsTitles.get(position);
    }

    public static void addPhrasesCollection(String title,
                                            ArrayList<String> newCollection,
                                            String phrasesCollectionsTitlesKey,
                                            SharedPreferences sharedPreferences,
                                            Context context){
        // add title to collection in shared preferences
        String strPhrasesCollectionsTitles =
                sharedPreferences.getString(phrasesCollectionsTitlesKey, "");
        strPhrasesCollectionsTitles += title + ",";
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(phrasesCollectionsTitlesKey, strPhrasesCollectionsTitles);
        editor.apply();
        // write newCollection in title.txt
        try {
            File filePath = new File(context.getExternalFilesDir(null).getAbsolutePath() + "/phrases");
            Log.d(DEBUG_TAG, "in CollectionStorage: filePath = " + filePath);
            String fileName = "/" + title + ".txt";
            File outFile = new File(filePath, fileName);
            if (!outFile.exists() && outFile.createNewFile()) {
                Log.d(DEBUG_TAG, "in CollectionStorage: " + outFile.getAbsolutePath() +
                        " did NOT exist and was created");
            }
            else{
                Log.d(DEBUG_TAG, "in CollectionStorage: " + outFile.getAbsolutePath() +
                        " existed or was NOT created");
            }
            FileOutputStream fos = new FileOutputStream(outFile);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Log.d(DEBUG_TAG, "in CollectionStorage: fileName = " + outFile);
            for(String phrase : newCollection){
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
    }

    public static ArrayList<String> getPhrasesCollectionsTitles(SharedPreferences sharedPreferences,
                                                                String strPhrasesCollectionsTitlesKey){
        // get collection of titles from shared preferences
        String strPhrasesCollectionsTitle = sharedPreferences.getString(strPhrasesCollectionsTitlesKey, "");
        Log.d(DEBUG_TAG, "in CollectionStorage: strPhrasesCollectionsTitle = " + strPhrasesCollectionsTitle);
        String[] splittedPhrasesCollectionsTitles = strPhrasesCollectionsTitle.split(",");
        ArrayList<String> phraseCollectionsTitles = new ArrayList<>(Arrays.asList(splittedPhrasesCollectionsTitles));
        Log.d(DEBUG_TAG, "in CollectionStorage: phraseCollectionsTitles = " + phraseCollectionsTitles);
        phraseCollectionsTitles.remove(0);
        return phraseCollectionsTitles;
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
