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

    public class Mahjong{
        public static int sgBtnGroupBonesPosToAmount(int selectedPosition){
            switch(selectedPosition){
                case 0:
                    return 12;
                case 1:
                    return 24;
                default:
                    return 12;
            }
        }
        public static int sgBtnGroupEqualBonesPosToAmount(int selectedPosition){
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
