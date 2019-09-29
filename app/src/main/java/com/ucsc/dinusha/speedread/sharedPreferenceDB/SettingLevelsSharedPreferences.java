package com.ucsc.dinusha.speedread.sharedPreferenceDB;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.ucsc.dinusha.speedread.models.BaseGsonArray;
import com.ucsc.dinusha.speedread.models.WindowsGsonObject;
import com.ucsc.dinusha.speedread.models.WordsGsonObject;

import java.util.ArrayList;

public class SettingLevelsSharedPreferences {

    public static SettingLevelsSharedPreferences sInstance;

    public Context mContext;
    public SharedPreferences mSharedPreferences;
    public SharedPreferences.Editor mEditor;

    public SettingLevelsSharedPreferences(Context context) {
        this.mContext = context;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mEditor = mSharedPreferences.edit();
    }

    public static SettingLevelsSharedPreferences getInstance(Context context){
        if(sInstance == null){
            sInstance = new SettingLevelsSharedPreferences(context);
        }
        return sInstance;
    }

    public WindowsGsonObject addWindowsObject(String name, String windowsPerLine, String overlappingCharacters, String speedType, String speed, String fontSize, String fontStyle){
        BaseGsonArray windowsGsonArray = getWindowsGsonArray();
        WindowsGsonObject windowsGsonObject = new WindowsGsonObject();
        windowsGsonObject.name = name;
        windowsGsonObject.windowPerLine = windowsPerLine;
        windowsGsonObject.overlapingCharacters = overlappingCharacters;
        windowsGsonObject.speedType = speedType;
        windowsGsonObject.speed = speed;
        windowsGsonObject.fontSize = fontSize;
        windowsGsonObject.fontStyle = fontStyle;
        windowsGsonArray.windowJsonArray.add(windowsGsonObject);
        Gson gson = new Gson();
        mEditor.putString("windowsJsonArrayInString", gson.toJson(windowsGsonArray));
        mEditor.commit();
        return windowsGsonObject;
    }

    public WordsGsonObject addWordsObject(String name, String wordsPerMin, String wordsPerWindow, String fontSize, String fontStyle){
        BaseGsonArray wordsGsonArray = getWordsJsonArray();
        WordsGsonObject wordsGsonObject = new WordsGsonObject();
        wordsGsonObject.name = name;
        wordsGsonObject.wordsPerMin = wordsPerMin;
        wordsGsonObject.wordsPerWindow = wordsPerWindow;
        wordsGsonObject.fontSize = fontSize;
        wordsGsonObject.fontStyle = fontStyle;
        wordsGsonArray.wordsJsonArray.add(wordsGsonObject);
        Gson gson = new Gson();
        mEditor.putString("wordsJsonArrayInString", gson.toJson(wordsGsonArray));
        mEditor.commit();
        return wordsGsonObject;
    }

    public void updateWindowsObject(String name, String windowsPerLine, String overlappingCharacters, String speedType, String speed, String fontSize, String fontStyle){
        BaseGsonArray windowsGsonArray;
        String arrayString = mSharedPreferences.getString("windowsJsonArrayInString", null);
        if(arrayString != null) {
            Gson gson = new Gson();
            windowsGsonArray = gson.fromJson(arrayString, BaseGsonArray.class);
            for (int i = 0; i < windowsGsonArray.windowJsonArray.size(); i++){
                if(name.equals(windowsGsonArray.windowJsonArray.get(i).name)){
                    windowsGsonArray.windowJsonArray.get(i).windowPerLine = windowsPerLine;
                    windowsGsonArray.windowJsonArray.get(i).overlapingCharacters = overlappingCharacters;
                    windowsGsonArray.windowJsonArray.get(i).speedType = speedType;
                    windowsGsonArray.windowJsonArray.get(i).speed = speed;
                    windowsGsonArray.windowJsonArray.get(i).fontSize = fontSize;
                    windowsGsonArray.windowJsonArray.get(i).fontStyle = fontStyle;
                    mEditor.putString("windowsJsonArrayInString", gson.toJson(windowsGsonArray));
                    mEditor.commit();
                }
            }
        }
    }

    public void updateWordsObject(String name, String wordsPerMin, String wordsPerWindow, String fontSize, String fontStyle){
        BaseGsonArray wordsGsonArray;
        String arrayString = mSharedPreferences.getString("wordsJsonArrayInString", null);
        if(arrayString != null){
            Gson gson = new Gson();
            wordsGsonArray = gson.fromJson(arrayString, BaseGsonArray.class);
            for (int i = 0; i < wordsGsonArray.wordsJsonArray.size(); i++){
                if(name.equals(wordsGsonArray.wordsJsonArray.get(i).name)){
                    wordsGsonArray.wordsJsonArray.get(i).wordsPerMin = wordsPerMin;
                    wordsGsonArray.wordsJsonArray.get(i).wordsPerWindow = wordsPerWindow;
                    wordsGsonArray.wordsJsonArray.get(i).fontSize = fontSize;
                    wordsGsonArray.wordsJsonArray.get(i).fontStyle = fontStyle;
                    mEditor.putString("wordsJsonArrayInString", gson.toJson(wordsGsonArray));
                    mEditor.commit();
                }
            }
        }
    }

    public void deleteWindowsObject(String name){
        BaseGsonArray windowsGsonArray;
        String arrayString = mSharedPreferences.getString("windowsJsonArrayInString", null);
        if(arrayString != null){
            Gson gson = new Gson();
            windowsGsonArray = gson.fromJson(arrayString, BaseGsonArray.class);
            for (int i = 0; i < windowsGsonArray.windowJsonArray.size(); i++){
                if(name.equals(windowsGsonArray.windowJsonArray.get(i).name)){
                    windowsGsonArray.windowJsonArray.remove(i);
                    mEditor.putString("windowsJsonArrayInString", gson.toJson(windowsGsonArray));
                    mEditor.commit();
                }
            }
        }
    }

    public void deleteWordsObject(String name){
        BaseGsonArray wordsGsonArray;
        String arrayString = mSharedPreferences.getString("wordsJsonArrayInString", null);
        if(arrayString != null){
            Gson gson = new Gson();
            wordsGsonArray = gson.fromJson(arrayString, BaseGsonArray.class);
            for (int i = 0; i < wordsGsonArray.wordsJsonArray.size(); i++){
                if(name.equals(wordsGsonArray.wordsJsonArray.get(i).name)){
                    wordsGsonArray.wordsJsonArray.remove(i);
                    mEditor.putString("wordsJsonArrayInString", gson.toJson(wordsGsonArray));
                    mEditor.commit();
                }
            }
        }
    }

    public WindowsGsonObject getAllWindowsObject(String name){
        BaseGsonArray windowsGsonArray;
        String arrayString = mSharedPreferences.getString("windowsJsonArrayInString", null);
        if(arrayString != null){
            Gson gson = new Gson();
            windowsGsonArray = gson.fromJson(arrayString, BaseGsonArray.class);
            for (int i = 0; i < windowsGsonArray.windowJsonArray.size(); i++){
                if(name.equals(windowsGsonArray.windowJsonArray.get(i).name)){
                    return windowsGsonArray.windowJsonArray.get(i);
                }
            }
        }
        return null;
    }

    public WordsGsonObject getAllWordsObject(String name){
        BaseGsonArray wordsGsonArray;
        String arrayString = mSharedPreferences.getString("wordsJsonArrayInString", null);
        if(arrayString != null){
            Gson gson = new Gson();
            wordsGsonArray = gson.fromJson(arrayString, BaseGsonArray.class);
            for (int i = 0; i < wordsGsonArray.wordsJsonArray.size(); i++){
                if(name.equals(wordsGsonArray.wordsJsonArray.get(i).name)){
                    return wordsGsonArray.wordsJsonArray.get(i);
                }
            }
        }
        return null;
    }

    public WindowsGsonObject getCustomWindowsObject(String name){
        BaseGsonArray windowsGsonArray;
        String arrayString = mSharedPreferences.getString("windowsJsonArrayInString", null);
        if(arrayString != null){
            Gson gson = new Gson();
            windowsGsonArray = gson.fromJson(arrayString, BaseGsonArray.class);
            for (int i = 3; i < windowsGsonArray.windowJsonArray.size(); i++){
                if(name.equals(windowsGsonArray.windowJsonArray.get(i).name)){
                    return windowsGsonArray.windowJsonArray.get(i);
                }
            }
        }
        return null;
    }

    public WordsGsonObject getCustomWordsObject(String name){
        BaseGsonArray wordsGsonArray;
        String arrayString = mSharedPreferences.getString("wordsJsonArrayInString", null);
        if(arrayString != null){
            Gson gson = new Gson();
            wordsGsonArray = gson.fromJson(arrayString, BaseGsonArray.class);
            for (int i = 3; i < wordsGsonArray.wordsJsonArray.size(); i++){
                if(name.equals(wordsGsonArray.wordsJsonArray.get(i).name)){
                    return wordsGsonArray.wordsJsonArray.get(i);
                }
            }
        }
        return null;
    }

    public BaseGsonArray getWindowsGsonArray(){
        BaseGsonArray windowsGsonArray;
        String arrayString = mSharedPreferences.getString("windowsJsonArrayInString", null);
        if(arrayString == null){
            windowsGsonArray = new BaseGsonArray();
            windowsGsonArray.windowJsonArray = new ArrayList<>();
        } else {
            Gson gson = new Gson();
            windowsGsonArray = gson.fromJson(arrayString, BaseGsonArray.class);
        }
        return  windowsGsonArray;
    }

    public BaseGsonArray getWordsJsonArray(){
        BaseGsonArray wordsGsonArray;
        String arrayString = mSharedPreferences.getString("wordsJsonArrayInString", null);
        if(arrayString == null){
            wordsGsonArray = new BaseGsonArray();
            wordsGsonArray.wordsJsonArray = new ArrayList<>();
        } else {
            Gson gson = new Gson();
            wordsGsonArray = gson.fromJson(arrayString, BaseGsonArray.class);
        }
        return  wordsGsonArray;
    }

    public void addDefaultWindowInitialyIfThisIsFreshInstallation(){
        String arrayString = mSharedPreferences.getString("windowsJsonArrayInString", null);
        if(arrayString == null){
            addWindowsObject("Easy","1","0","window","30","7","1");
            addWindowsObject("Medium","2","2","window","60","5","1");
            addWindowsObject("Hard","3","4","window","85","0","1");
        }
    }

    public void addDefaultWordInitialyIfThisIsFreshInstallation(){
        String arrayString = mSharedPreferences.getString("wordsJsonArrayInString", null);
        if(arrayString == null){
            addWordsObject("Easy","100","4","5","1");
            addWordsObject("Medium","200","6","3","1");
            addWordsObject("Hard","250","10","0","1");
        }
    }
}
