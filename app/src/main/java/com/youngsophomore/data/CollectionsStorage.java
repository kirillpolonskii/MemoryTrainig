package com.youngsophomore.data;

import android.net.Uri;
import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.Arrays;

public class CollectionsStorage {
    private static ArrayList<String> wordsCollectionsTitles;
    private static ArrayList<ArrayList<String>> wordsCollections;
    private static ArrayList<String> phrasesCollectionsTitles;
    private static ArrayList<ArrayList<String>> phrasesCollections;

    private static ArrayList<String> questionsCollectionsTitles; // collection named after image
    private static ArrayList<ArrayList<Question>> questionsCollections;
    private static ArrayMap<String, Uri> imageNamesForUri;


    static {
        wordsCollectionsTitles = new ArrayList<>();
        wordsCollectionsTitles.add("FirstWordsCollection");
        wordsCollections = new ArrayList<>();

        phrasesCollectionsTitles = new ArrayList<>();
        phrasesCollectionsTitles.add("FirstPhrasesCollection");
        phrasesCollections = new ArrayList<>();

        questionsCollectionsTitles = new ArrayList<>();
        questionsCollectionsTitles.add("Image1");
        questionsCollections = new ArrayList<>();
        imageNamesForUri = new ArrayMap<>();
    }

    public static void addWordsCollection(String title, String newCollection){
        wordsCollectionsTitles.add(title);
        String[] splittedNewCollection = newCollection.split(" ");
        ArrayList<String> newCollectionArray = new ArrayList<>(Arrays.asList(splittedNewCollection));

        wordsCollections.add(newCollectionArray);
    }

    public static ArrayList<String> getWordsCollectionsTitles(){
        return wordsCollectionsTitles;
    }

    public static String getWordsCollectionTitle(int position){
        return wordsCollectionsTitles.get(position);
    }

    public static void addPhrasesCollection(String title, ArrayList<String> newCollection){
        phrasesCollectionsTitles.add(title);

        phrasesCollections.add(newCollection);
    }

    public static ArrayList<String> getPhrasesCollectionsTitles(){
        return phrasesCollectionsTitles;
    }

    public static String getPhrasesCollectionTitle(int position){
        return phrasesCollectionsTitles.get(position);
    }

    public static void addQuestionsCollections(String title, Uri imageUri, ArrayList<Question> newCollection){
        questionsCollectionsTitles.add(title);
        questionsCollections.add(newCollection);
        imageNamesForUri.put(title, imageUri);
    }

    public static ArrayList<String> getQuestionsCollectionsTitles(){
        return questionsCollectionsTitles;
    }


}
