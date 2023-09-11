package com.youngsophomore.data;

import java.util.ArrayList;
import java.util.Arrays;

public class CollectionsStorage {
    private static ArrayList<String> wordsCollectionsTitles;
    private static ArrayList<ArrayList<String>> wordsCollections;
    private static ArrayList<String> phrasesCollectionsTitles;
    private static ArrayList<ArrayList<String>> phrasesCollections;

    private static ArrayList<String> questionsCollectionsTitles; // collection named after image
    private static ArrayList<ArrayList<Question>> questionsCollections;


    static {
        wordsCollectionsTitles = new ArrayList<>();
        wordsCollectionsTitles.add("FirstWordsCollection");

        wordsCollections = new ArrayList<>();

        phrasesCollectionsTitles = new ArrayList<>();
        phrasesCollectionsTitles.add("FirstPhrasesCollection");

        phrasesCollections = new ArrayList<>();
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

    public static void addQuestionsCollections(String title, ArrayList<Question> newCollection){
        questionsCollectionsTitles.add(title);
        questionsCollections.add(newCollection);
    }


}
