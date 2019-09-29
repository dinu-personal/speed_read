package com.ucsc.dinusha.speedread;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ucsc.dinusha.speedread.models.WindowsGsonObject;
import com.ucsc.dinusha.speedread.sharedPreferenceDB.SettingLevelsSharedPreferences;

public class WindowsSetSettingsActivity extends AppCompatActivity {

    private Button buttonWindowPerLine1;
    private Button buttonWindowPerLine2;
    private Button buttonWindowPerLine3;
    private Button buttonFontType1;
    private Button buttonFontType2;
    private Button buttonFontType3;
    private SeekBar seekBarOverlapingCharacters;
    private RadioGroup radioButtonSpeedType;
    private SeekBar seekBarSpeedTypeWindow;
    private SeekBar seekBarSpeedTypeWord;
    private SeekBar seekBarFontSize;
    private Button buttonSave;
    private Button buttonEasy;
    private Button buttonMedium;
    private Button buttonHard;

    private String settingName = "";
    private String importSettingName = "";
    private String windowPerLine = "2";
    private String overlapingCharacters = "4";
    private String speedType = "window";
    private String windowsPerMin = "60";
    private String avgWordsPerMin = "250";
    private String textSize = "5";
    private String textStyle = "1";

    private Context mCotext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_windows_set_settings);

        settingName = getIntent().getStringExtra("name");
        mCotext = this;

        buttonWindowPerLine1 = (Button)findViewById(R.id.activity_windows_set_settings_button_window_per_line1);
        buttonWindowPerLine2 = (Button)findViewById(R.id.activity_windows_set_settings_button_window_per_line2);
        buttonWindowPerLine3 = (Button)findViewById(R.id.activity_windows_set_settings_button_window_per_line3);
        buttonFontType1 = (Button)findViewById(R.id.activity_windows_set_settings_button_font1);
        buttonFontType2 = (Button)findViewById(R.id.activity_windows_set_settings_button_font2);
        buttonFontType3 = (Button)findViewById(R.id.activity_windows_set_settings_button_font3);
        seekBarOverlapingCharacters = (SeekBar)findViewById(R.id.activity_main_windowtype_seekbar_overlapping_chars);
        radioButtonSpeedType = (RadioGroup)findViewById(R.id.activity_windows_set_settings_radiogroup_speed);
        seekBarSpeedTypeWindow = (SeekBar)findViewById(R.id.activity_windows_set_settings_seekbar_window_per_min);
        seekBarSpeedTypeWord = (SeekBar)findViewById(R.id.activity_windows_set_settings_seekbar_avg_word_per_min);
        seekBarFontSize = (SeekBar) findViewById(R.id.activity_windows_set_settings_seekbar_font_size);
        buttonSave = (Button)findViewById(R.id.activity_windows_set_settings_button_save);
        buttonEasy = (Button)findViewById(R.id.activity_windows_set_settings_button_easy);
        buttonMedium = (Button)findViewById(R.id.activity_windows_set_settings_button_medium);
        buttonHard = (Button)findViewById(R.id.activity_windows_set_settings_button_hard);

        buttonWindowPerLine1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeWindowPerLine("1");
            }
        });
        buttonWindowPerLine2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeWindowPerLine("2");
            }
        });
        buttonWindowPerLine3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeWindowPerLine("3");
            }
        });

        buttonFontType1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFontType("1");
            }
        });
        buttonFontType2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFontType("2");
            }
        });
        buttonFontType3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFontType("3");
            }
        });

        seekBarOverlapingCharacters.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                Button button = (Button) findViewById(R.id.activity_windows_set_settings_button_overlapping_characters);
                button.setText( String.valueOf((progress)*2) );
                overlapingCharacters = String.valueOf((progress)*2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        radioButtonSpeedType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                LinearLayout linearLayout_window = (LinearLayout)findViewById(R.id.activity_windows_set_settings_linearlayout_window_per_min);
                LinearLayout linearLayout_word = (LinearLayout)findViewById(R.id.activity_windows_set_settings_linearlayout_avg_word_per_min);
                linearLayout_window.setVisibility(View.GONE);
                linearLayout_word.setVisibility(View.GONE);

                if(checkedId == R.id.activity_windows_set_settings_radiobutton_window_per_min){
                    speedType =  "window";
                    linearLayout_window.setVisibility(View.VISIBLE);

                }
                else{
                    speedType = "word";
                    linearLayout_word.setVisibility(View.VISIBLE);
                }

            }
        });

        seekBarSpeedTypeWindow.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView textviewProgress = (TextView)findViewById(R.id.activity_windows_set_settings_textview_speed_window_per_min);
                textviewProgress.setText(String.valueOf(progress+30));
                windowsPerMin = String.valueOf(progress+30);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarSpeedTypeWord.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView textviewProgress = (TextView)findViewById(R.id.activity_windows_set_settings_textview_speed_avg_word_per_min);
                textviewProgress.setText(String.valueOf(progress+50));
                avgWordsPerMin = String.valueOf(progress+50);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarFontSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView textView = (TextView)findViewById(R.id.activity_windows_set_settings_button_size);
                textView.setText(String.valueOf(progress+5));
                textSize = String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        buttonEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImportMode("Easy");
            }
        });

        buttonMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImportMode("Medium");
            }
        });

        buttonHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeImportMode("Hard");
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettings();
            }
        });

        setReleventSettingsIfHave(settingName);
    }

    private void setReleventSettingsIfHave(String settingName){
        if(SettingLevelsSharedPreferences.getInstance(mCotext).getAllWindowsObject(settingName) != null){
            WindowsGsonObject windowsGsonObject = SettingLevelsSharedPreferences.getInstance(mCotext).getAllWindowsObject(settingName);
            if(windowsGsonObject.windowPerLine.length()>0){
                changeWindowPerLine(windowsGsonObject.windowPerLine);
                seekBarOverlapingCharacters.setProgress(Integer.parseInt(windowsGsonObject.overlapingCharacters)/2);
                if(windowsGsonObject.speedType.equals("window")){
                    radioButtonSpeedType.check(R.id.activity_windows_set_settings_radiobutton_window_per_min);
                    seekBarSpeedTypeWindow.setProgress(Integer.parseInt(windowsGsonObject.speed)-30);
                }
                else{
                    radioButtonSpeedType.check(R.id.activity_windows_set_settings_radiobutton_avg_word_per_min);
                    seekBarSpeedTypeWord.setProgress(Integer.parseInt(windowsGsonObject.speed)-50);
                }
                seekBarFontSize.setProgress(Integer.parseInt(windowsGsonObject.fontSize));
                changeFontType(windowsGsonObject.fontStyle);
            }
        }
    }

    private void changeWindowPerLine(String buttonNumber){
        buttonWindowPerLine1.setBackgroundResource(R.drawable.background_white_round);
        buttonWindowPerLine2.setBackgroundResource(R.drawable.background_white_round);
        buttonWindowPerLine3.setBackgroundResource(R.drawable.background_white_round);
        buttonWindowPerLine1.setTextColor(ContextCompat.getColor(mCotext, R.color.colorAccent));
        buttonWindowPerLine2.setTextColor(ContextCompat.getColor(mCotext, R.color.colorAccent));
        buttonWindowPerLine3.setTextColor(ContextCompat.getColor(mCotext, R.color.colorAccent));
        if(buttonNumber.equals("1")){
            buttonWindowPerLine1.setBackgroundResource(R.drawable.background_blue_round);
            buttonWindowPerLine1.setTextColor(ContextCompat.getColor(mCotext, R.color.colorPrimary));
            windowPerLine = "1";
        }
        else if(buttonNumber.equals("2")){
            buttonWindowPerLine2.setBackgroundResource(R.drawable.background_blue_round);
            buttonWindowPerLine2.setTextColor(ContextCompat.getColor(mCotext, R.color.colorPrimary));
            windowPerLine = "2";
        }
        else if(buttonNumber.equals("3")){
            buttonWindowPerLine3.setBackgroundResource(R.drawable.background_blue_round);
            buttonWindowPerLine3.setTextColor(ContextCompat.getColor(mCotext, R.color.colorPrimary));
            windowPerLine = "3";
        }
    }

    private void changeFontType(String fontNumber){
        buttonFontType1.setBackgroundResource(R.drawable.background_white_round);
        buttonFontType2.setBackgroundResource(R.drawable.background_white_round);
        buttonFontType3.setBackgroundResource(R.drawable.background_white_round);
        buttonFontType1.setTextColor(ContextCompat.getColor(mCotext, R.color.colorAccent));
        buttonFontType2.setTextColor(ContextCompat.getColor(mCotext, R.color.colorAccent));
        buttonFontType3.setTextColor(ContextCompat.getColor(mCotext, R.color.colorAccent));
        if(fontNumber.equals("1")){
            buttonFontType1.setBackgroundResource(R.drawable.background_blue_round);
            buttonFontType1.setTextColor(ContextCompat.getColor(mCotext, R.color.colorPrimary));
            textStyle = "1";
        }
        else if(fontNumber.equals("2")){
            buttonFontType2.setBackgroundResource(R.drawable.background_blue_round);
            buttonFontType2.setTextColor(ContextCompat.getColor(mCotext, R.color.colorPrimary));
            textStyle = "2";
        }
        else if(fontNumber.equals("3")){
            buttonFontType3.setBackgroundResource(R.drawable.background_blue_round);
            buttonFontType3.setTextColor(ContextCompat.getColor(mCotext, R.color.colorPrimary));
            textStyle = "3";
        }
    }

    private void changeImportMode(String importMode){
        buttonEasy.setBackgroundResource(R.drawable.background_white_round);
        buttonMedium.setBackgroundResource(R.drawable.background_white_round);
        buttonHard.setBackgroundResource(R.drawable.background_white_round);
        buttonEasy.setTextColor(ContextCompat.getColor(mCotext, R.color.colorAccent));
        buttonMedium.setTextColor(ContextCompat.getColor(mCotext, R.color.colorAccent));
        buttonHard.setTextColor(ContextCompat.getColor(mCotext, R.color.colorAccent));
        if(importMode.equals("Easy")){
            buttonEasy.setBackgroundResource(R.drawable.background_blue_round);
            buttonEasy.setTextColor(ContextCompat.getColor(mCotext, R.color.colorPrimary));
            importSettingName = "Easy";
            setReleventSettingsIfHave(importSettingName);
        }
        else if(importMode.equals("Medium")){
            buttonMedium.setBackgroundResource(R.drawable.background_blue_round);
            buttonMedium.setTextColor(ContextCompat.getColor(mCotext, R.color.colorPrimary));
            importSettingName = "Medium";
            setReleventSettingsIfHave(importSettingName);
        }
        else if(importMode.equals("Hard")){
            buttonHard.setBackgroundResource(R.drawable.background_blue_round);
            buttonHard.setTextColor(ContextCompat.getColor(mCotext, R.color.colorPrimary));
            importSettingName = "Hard";
            setReleventSettingsIfHave(importSettingName);
        }
    }

    private void saveSettings(){
        String speed = "";
        if(speedType.equals("window")){
            speed = windowsPerMin;
        }
        else{
            speed = avgWordsPerMin;
        }
        SettingLevelsSharedPreferences.getInstance(mCotext).updateWindowsObject(settingName, windowPerLine, overlapingCharacters, speedType, speed, textSize, textStyle);
        Intent intent = new Intent(mCotext, SettingsLevelsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(mCotext, SettingsLevelsActivity.class);
        startActivity(intent);
        finish();
    }
}
