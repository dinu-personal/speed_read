package com.ucsc.dinusha.speedread;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ucsc.dinusha.speedread.javaScriptInterface.SettingLevelJavaScriptInterface;
import com.ucsc.dinusha.speedread.models.WindowsGsonObject;
import com.ucsc.dinusha.speedread.models.WordsGsonObject;
import com.ucsc.dinusha.speedread.sharedPreferenceDB.SettingLevelsSharedPreferences;
import com.ucsc.dinusha.speedread.sharedPreferenceDB.WebViewTextSharedPreferences;

import java.text.DecimalFormat;

public class SettingTypeReadingActivity extends AppCompatActivity {

    private WindowsGsonObject windowsGsonObject;
    private WordsGsonObject wordsGsonObject;
    private WebView mWebView;
    private Context mContext;
    private RelativeLayout relativeLayoutLoading;
    private TextView textViewWordsPrMin;
    private TextView textViewWindowsPerLine;
    private TextView textViewTotalRunningTime;
    private Button buttonStartStop;

    private TextView configTitleOne;
    private TextView configTitleTwo;

    private int mTotalWindows;
    private int mTotalWords;
    private int mTotalLines;
    private int singleWindowduratrion;
    private Long remainingTimeInMills;
    private boolean isNowRunningState = false;
    private String settingLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_type_reading);

        mContext = this;

        String settingName = getIntent().getStringExtra("name");
        settingLevel = getIntent().getStringExtra("settingType");

        mWebView = findViewById(R.id.activity_reading_webview);
        relativeLayoutLoading = findViewById(R.id.activity_reading_relativeLayout_loading);
        buttonStartStop = findViewById(R.id.activity_reading_button_start_stop);

        textViewWordsPrMin = findViewById(R.id.activity_reading_textView_words_per_min);
        textViewWindowsPerLine = findViewById(R.id.activity_reading_textView_windows_per_line);
        textViewTotalRunningTime = findViewById(R.id.activity_reading_textView_total_running_time);

        configTitleOne = findViewById(R.id.activity_reading_textview_configuration_one);
        configTitleTwo = findViewById(R.id.activity_reading_textview_configuration_two);

        relativeLayoutLoading.setVisibility(View.VISIBLE);
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());

        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setScrollbarFadingEnabled(false);

        mWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        if (settingLevel.equals("Windows")) {
            configTitleOne.setText("Avg. words per\nminute");
            configTitleTwo.setText("Windows per\nline");

            mWebView.loadUrl("file:///android_asset/windowType.html");
            SettingLevelJavaScriptInterface jsInterface = new SettingLevelJavaScriptInterface(this);
            mWebView.addJavascriptInterface(jsInterface, "JSInterface");

            windowsGsonObject = SettingLevelsSharedPreferences.getInstance(mContext).getAllWindowsObject(settingName);

            jsInterface.getJavaScriptNativeInterface(new SettingLevelJavaScriptInterface.SettingLevelJavaScriptNativeInterface() {
                @Override
                public void hideProgressNative() {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    relativeLayoutLoading.setVisibility(View.GONE);
                                    mWebView.loadUrl("javascript:calcTotNoOfWindows('" + windowsGsonObject.windowPerLine + "')");
                                }
                            });

                        }
                    }, 500);
                }


                @Override
                public void getTextJava() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String webViewText = WebViewTextSharedPreferences.getInstance(mContext).getWebViewText();
                            webViewText = webViewText.replaceAll("'", "");
                            webViewText = webViewText.replaceAll("\"", "");
                            mWebView.loadUrl("javascript:addNewTextContent('" + webViewText + "', '" + windowsGsonObject.fontSize + "', '" + windowsGsonObject.fontStyle + "')");
                        }
                    });
                }

                @Override
                public void getTotWindowsToJava(final int totWindows, final int wordCount) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTotalWindows = totWindows;
                            mTotalWords = wordCount;
                            calculateConfigurations();
                        }
                    });
                }

                @Override
                public void updateWhenWindowMoveJava() {

                }

                @Override
                public void finishHighlightingAndShowPanelJava() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startStopReading();
                        }
                    });
                }

                @Override
                public void sendWordCountAndLineCountJava(int wordCount, int lineCount) {

                }
            });

        } else {
            configTitleOne.setText("Words per\nminute");
            configTitleTwo.setText("Avg. windows\nper line");

            mWebView.loadUrl("file:///android_asset/wordType.html");
            SettingLevelJavaScriptInterface jsInterface = new SettingLevelJavaScriptInterface(this);
            mWebView.addJavascriptInterface(jsInterface, "JSInterface");

            wordsGsonObject = SettingLevelsSharedPreferences.getInstance(mContext).getAllWordsObject(settingName);

            jsInterface.getJavaScriptNativeInterface(new SettingLevelJavaScriptInterface.SettingLevelJavaScriptNativeInterface() {
                @Override
                public void hideProgressNative() {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable(){
                        @Override
                        public void run(){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        }
                    }, 500);
                }

                @Override
                public void getTextJava() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String webViewText = WebViewTextSharedPreferences.getInstance(mContext).getWebViewText();
                            webViewText = webViewText.replaceAll("'","");
                            webViewText = webViewText.replaceAll("\"", "");
                            mWebView.loadUrl("javascript:addNewTextContent('" + webViewText + "', '" + wordsGsonObject.fontSize + "', '" + wordsGsonObject.fontStyle + "')");
                        }
                    });
                }

                @Override
                public void getTotWindowsToJava(int totWindows, int wordCount) {

                }

                @Override
                public void updateWhenWindowMoveJava() {

                }

                @Override
                public void finishHighlightingAndShowPanelJava() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startStopReading();
                        }
                    });
                }

                @Override
                public void sendWordCountAndLineCountJava(final int wordCount, final int lineCount) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTotalWords = wordCount;
                            mTotalLines = lineCount;
                            relativeLayoutLoading.setVisibility(View.GONE);
                            calculateConfigurations();
                        }
                    });
                }
            });
        }

        buttonStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStopReading();
            }
        });

        try {
            Analytics.getInstance(this).pushToAnalytics("Setting Type Reading Screen");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calculateConfigurations(){
        if (settingLevel.equals("Windows")) {

            if(windowsGsonObject.speedType.equals("window")){
                singleWindowduratrion = 60000 / (Integer.parseInt(windowsGsonObject.speed));
                long totalRuningTimeInMills = (long) (singleWindowduratrion*mTotalWindows);
                remainingTimeInMills = totalRuningTimeInMills;
                long minutes = (totalRuningTimeInMills / 1000) / 60;
                long seconds = (totalRuningTimeInMills / 1000) % 60;
                String minutesString = minutes+"";
                String secondsString = seconds+"";
                if(minutesString.length() == 1){
                    minutesString = "0"+minutesString;
                }
                if(secondsString.length() == 1){
                    secondsString = "0"+secondsString;
                }
                textViewTotalRunningTime.setText(minutesString+":"+secondsString);
                remainingTimeInMills = (long) (remainingTimeInMills - singleWindowduratrion);

                textViewWindowsPerLine.setText(windowsGsonObject.windowPerLine+"");
                int wordsPerMin = (int) ((60000*mTotalWords)/totalRuningTimeInMills);
                textViewWordsPrMin.setText(wordsPerMin+"");
            }
            else if(windowsGsonObject.speedType.equals("word")){
                textViewWordsPrMin.setText(windowsGsonObject.speed+"");
                long totalRuningTimeInMills = (mTotalWords*60000)/Integer.parseInt(windowsGsonObject.speed);
                remainingTimeInMills = totalRuningTimeInMills;
                singleWindowduratrion = (int) (totalRuningTimeInMills / mTotalWindows);
                long minutes = (totalRuningTimeInMills / 1000) / 60;
                long seconds = (totalRuningTimeInMills / 1000) % 60;
                String minutesString = minutes+"";
                String secondsString = seconds+"";
                if(minutesString.length() == 1){
                    minutesString = "0"+minutesString;
                }
                if(secondsString.length() == 1){
                    secondsString = "0"+secondsString;
                }
                textViewTotalRunningTime.setText(minutesString+":"+secondsString);

                remainingTimeInMills = (long) (remainingTimeInMills -singleWindowduratrion);
                textViewWindowsPerLine.setText(windowsGsonObject.windowPerLine+"");
            }

        } else {

            TextView textView = (TextView)findViewById(R.id.activity_reading_textView_windows_per_line);

            float totWindows = mTotalWords/ Integer.parseInt(wordsGsonObject.wordsPerWindow);

            if( ( totWindows ) < mTotalLines){
                float windowsPerLine = totWindows/(float)mTotalLines;
                textView.setText(windowsPerLine+"");

                DecimalFormat df = new DecimalFormat("#.##");
                textView.setText(df.format(windowsPerLine)+"");
            }
            else{
                int windowsPerLine = (int) (totWindows/mTotalLines);
                textView.setText(windowsPerLine+"");
            }
            textViewWordsPrMin.setText(wordsGsonObject.wordsPerMin);
            long totalRuningTimeInMills = (mTotalWords*60000)/Integer.parseInt(wordsGsonObject.wordsPerMin);
            singleWindowduratrion = (int) (totalRuningTimeInMills / totWindows);
            long minutes = (totalRuningTimeInMills / 1000) / 60;
            long seconds = (totalRuningTimeInMills / 1000) % 60;
            String minutesString = minutes+"";
            String secondsString = seconds+"";
            if(minutesString.length() == 1){
                minutesString = "0"+minutesString;
            }
            if(secondsString.length() == 1){
                secondsString = "0"+secondsString;
            }
            textViewTotalRunningTime.setText(minutesString+":"+secondsString);
        }

        buttonStartStop.setClickable(true);
        buttonStartStop.setEnabled(true);

    }

    private void startStopReading() {
        if (settingLevel.equals("Windows")) {
            if (isNowRunningState == false) {
                isNowRunningState = true;
                buttonStartStop.setText("STOP");
                mWebView.loadUrl("javascript:startHighlighting('" + windowsGsonObject.windowPerLine + "','" + singleWindowduratrion + "','" + windowsGsonObject.overlapingCharacters + "')");
            } else {
                isNowRunningState = false;
                buttonStartStop.setText("Play");
                mWebView.loadUrl("javascript:stopHighlighting()");
            }
        } else {
            if(isNowRunningState == false){
                isNowRunningState = true;
                buttonStartStop.setText("STOP");
                mWebView.loadUrl("javascript:startHighlighting__wordType('"+wordsGsonObject.wordsPerWindow+"','"+singleWindowduratrion+"')");
            }
            else{
                isNowRunningState = false;
                mWebView.loadUrl("javascript:stopHighlighting__wordType()");
                buttonStartStop.setText("PLAY");
            }
        }
    }
}
