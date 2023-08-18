package com.youngsophomore.data;

import java.util.ArrayList;
import java.util.Arrays;

public class CollectionsStorage {
    private static ArrayList<String> wordsCollectionsTitles;
    private static ArrayList<ArrayList<String>> wordsCollections;

    static {
        wordsCollectionsTitles = new ArrayList<>();
        wordsCollectionsTitles.add("FirstCollection");

        wordsCollections = new ArrayList<>();
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


}
