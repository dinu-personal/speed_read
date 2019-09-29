package com.ucsc.dinusha.speedread.javaScriptInterface;

import android.app.Activity;
import android.webkit.JavascriptInterface;

public class SettingLevelJavaScriptInterface {
    private Activity activity;

    public SettingLevelJavaScriptInterface(Activity activiy) {
        this.activity = activiy;
    }

    @JavascriptInterface
    public void hideProgressJs(){
        typeJavaScriptNativeInterface.hideProgressNative();
    }

    @JavascriptInterface
    public void getTotWindowsFromJs(int totWindows, int wordCount){
        typeJavaScriptNativeInterface.getTotWindowsToJava(totWindows, wordCount);
    }

    @JavascriptInterface
    public void updateWhenWindowMoveJs(){
        typeJavaScriptNativeInterface.updateWhenWindowMoveJava();
    }

    @JavascriptInterface
    public void finishHighlightingAndShowPanelJs(){
        typeJavaScriptNativeInterface.finishHighlightingAndShowPanelJava();
    }

    @JavascriptInterface
    public void getTextJs(){
        typeJavaScriptNativeInterface.getTextJava();
    }

    @JavascriptInterface
    public void sendWordCountAndLineCountJs(int wordCount, int lineCount){
        typeJavaScriptNativeInterface.sendWordCountAndLineCountJava(wordCount, lineCount);
    }

    public void getJavaScriptNativeInterface(SettingLevelJavaScriptInterface.SettingLevelJavaScriptNativeInterface levelsJavaScriptNativeInterface){
        this.typeJavaScriptNativeInterface = levelsJavaScriptNativeInterface;
    }

    private SettingLevelJavaScriptInterface.SettingLevelJavaScriptNativeInterface typeJavaScriptNativeInterface = null;

    public interface SettingLevelJavaScriptNativeInterface {
        void hideProgressNative();
        void getTextJava();
        void getTotWindowsToJava(int totWindows, int wordCount);
        void updateWhenWindowMoveJava();
        void finishHighlightingAndShowPanelJava();
        void sendWordCountAndLineCountJava(int wordCount, int lineCount);
    }
}
