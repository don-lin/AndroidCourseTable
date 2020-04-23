package com.course;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.provider.FontsContract;
import android.util.Log;

import androidx.core.provider.FontsContractCompat;

public class SettingManager {
    public static int getBackgroundColorSetting(Context context){
        switch (get(context,"bgc")){
            case 0: return Color.TRANSPARENT;
            case 1: return Color.BLUE;
            case 2: return Color.BLACK;
            case 3: return Color.GREEN;
            case 4: return Color.DKGRAY;
            case 5: return Color.RED;
        };
        return  Color.TRANSPARENT;
    }

    public static int getFontColorSetting(Context context){

        switch (get(context,"fc")){
            case 0: return Color.BLACK;
            case 1: return Color.BLUE;
            case 2: return Color.WHITE;
            case 3: return Color.GREEN;
            case 4: return Color.DKGRAY;
            case 5: return Color.RED;
        };
        return  Color.BLACK;
    }

    public static Typeface getFontStyleSetting(Context context){

        switch (get(context,"fs")) {
            case 0:return Typeface.DEFAULT;
            case 1:return Typeface.MONOSPACE;
            case 2:return Typeface.DEFAULT_BOLD;
            case 3:return Typeface.SANS_SERIF;
            case 4:return Typeface.SERIF;
        }
        return Typeface.DEFAULT;
    }

    public static void setBackgroundColor(Context context,int set){
        set(context,"bgc",set);
    }
    public static void setFontColor(Context context,int set){
        set(context,"fc",set);
    }
    public static void setFontStyle(Context context,int set){
        set(context,"fs",set);
    }

    private static void set(Context context,String name,int set){
        SharedPreferences.Editor editor=context.getSharedPreferences("set",context.MODE_PRIVATE).edit();
        editor.putInt(name,set);
        editor.commit();
    }
    public static int get(Context context,String name){

        SharedPreferences reader=context.getSharedPreferences("set",context.MODE_PRIVATE);
        int i=reader.getInt(name,0);
        return i;
    }
}
