package com.youngsophomore.helpers;

import android.widget.ImageButton;

public class PrepHelper {
    public static void activateBtn(ImageButton btn, int elevPx){
        btn.setEnabled(true);
        btn.setAlpha(1f);
        btn.setElevation(elevPx);
    }
    public static void deactivateBtn(ImageButton btn){
        btn.setEnabled(false);
        btn.setAlpha(0.5f);
        btn.setElevation(0);
    }

    public static boolean isCollectionTitleUnique(String strCollectionsTitles, String title){
        if (!strCollectionsTitles.contains(title)){
            return true;
        }
        else{
            int titleInd = strCollectionsTitles.indexOf(title);
            while (titleInd >= 0) {
                if(strCollectionsTitles.charAt(titleInd - 1) == ',' &&
                        strCollectionsTitles.charAt(titleInd + title.length()) == ','){
                    return false;

                }

                titleInd = strCollectionsTitles.indexOf(title, titleInd + 1);
            }
            return true;

        }

    }

    public class Mahjong{
        public static int sgBtnGroupTilesPosToAmount(int selectedPosition){
            switch(selectedPosition){
                case 0:
                    return 12;
                case 1:
                    return 24;
                default:
                    return 12;
            }
        }
        public static int sgBtnGroupEqualTilesPosToAmount(int selectedPosition){
            switch(selectedPosition){
                case 0:
                    return 2;
                case 1:
                    return 3;
                case 2:
                    return 4;
                case 3:
                    return 6;
                default:
                    return 2;
            }
        }
    }
}
