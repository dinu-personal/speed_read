package com.ucsc.dinusha.speedread;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ucsc.dinusha.speedread.models.WordsGsonObject;
import com.ucsc.dinusha.speedread.sharedPreferenceDB.SettingLevelsSharedPreferences;

public class WordsSetSettingsActivity extends AppCompatActivity {

    private Button buttonFontType1;
    private Button buttonFontType2;
    private Button buttonFontType3;
    private SeekBar seekBarWordsPerMin;
    private SeekBar seekBarWordsPerWindow;
    private SeekBar seekBarFontSize;
    private Button buttonSave;
    private Button buttonEasy;
    private Button buttonMedium;
    private Button buttonHard;

    private String settingName = "";
    private String importSettingName = "";
    private String wordsPerMin = "300";
    private String wordsPerWindow = "3";
    private String textSize = "5";
    private String textStyle = "1";

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_set_settings);

        settingName = getIntent().getStringExtra("name");
        mContext = this;

        buttonFontType1 = (Button)findViewById(R.id.activity_words_set_settings_button_font1);
        buttonFontType2 = (Button)findViewById(R.id.activity_words_set_settings_button_font2);
        buttonFontType3 = (Button)findViewById(R.id.activity_words_set_settings_button_font3);
        seekBarWordsPerMin = (SeekBar)findViewById(R.id.activity_words_set_settings_seekbar_words_per_min);
        seekBarWordsPerWindow = (SeekBar)findViewById(R.id.activity_words_set_settings_seekbar_words_per_window);
        seekBarFontSize = (SeekBar)findViewById(R.id.activity_words_set_settings_seekbar_font_size);
        buttonSave = (Button)findViewById(R.id.activity_words_set_settings_button_save);
        buttonEasy = (Button)findViewById(R.id.activity_words_set_settings_button_easy);
        buttonMedium = (Button)findViewById(R.id.activity_words_set_settings_button_medium);
        buttonHard = (Button)findViewById(R.id.activity_words_set_settings_button_hard);

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

        seekBarWordsPerMin.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                TextView textView = (TextView) findViewById(R.id.activity_words_set_settings_textview_speed_words_per_min);
                textView.setText( String.valueOf((progress)+50) );
                wordsPerMin = String.valueOf((progress)+50);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarWordsPerWindow.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                TextView textviewProgress = (TextView)findViewById(R.id.activity_words_set_settings_textview_speed_window_per_window);
                textviewProgress.setText(String.valueOf(progress+1));
                wordsPerWindow = String.valueOf(progress+1);
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
                TextView textView = (TextView)findViewById(R.id.activity_words_set_settings_button_size);
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

    private void changeFontType(String fontNumber){
        buttonFontType1.setBackgroundResource(R.drawable.background_white_round);
        buttonFontType2.setBackgroundResource(R.drawable.background_white_round);
        buttonFontType3.setBackgroundResource(R.drawable.background_white_round);
        buttonFontType1.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        buttonFontType2.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        buttonFontType3.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        if(fontNumber.equals("1")){
            buttonFontType1.setBackgroundResource(R.drawable.background_blue_round);
            buttonFontType1.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            textStyle = "1";
        }
        else if(fontNumber.equals("2")){
            buttonFontType2.setBackgroundResource(R.drawable.background_blue_round);
            buttonFontType2.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            textStyle = "2";
        }
        else if(fontNumber.equals("3")){
            buttonFontType3.setBackgroundResource(R.drawable.background_blue_round);
            buttonFontType3.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            textStyle = "3";
        }
    }

    private void changeImportMode(String importMode){
        buttonEasy.setBackgroundResource(R.drawable.background_white_round);
        buttonMedium.setBackgroundResource(R.drawable.background_white_round);
        buttonHard.setBackgroundResource(R.drawable.background_white_round);
        buttonEasy.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        buttonMedium.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        buttonHard.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        if(importMode.equals("Easy")){
            buttonEasy.setBackgroundResource(R.drawable.background_blue_round);
            buttonEasy.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            importSettingName = "Easy";
            seekBarWordsPerMin.setEnabled(true);
            seekBarWordsPerWindow.setEnabled(false);
            seekBarFontSize.setEnabled(false);
            buttonFontType1.setEnabled(true);
            buttonFontType2.setEnabled(false);
            buttonFontType3.setEnabled(false);
            setReleventSettingsIfHave(importSettingName);
        }
        else if(importMode.equals("Medium")){
            seekBarWordsPerMin.setEnabled(true);
            seekBarWordsPerWindow.setEnabled(true);
            seekBarFontSize.setEnabled(true);
            buttonFontType1.setEnabled(true);
            buttonFontType2.setEnabled(true);
            buttonFontType3.setEnabled(true);
            buttonMedium.setBackgroundResource(R.drawable.background_blue_round);
            buttonMedium.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            importSettingName = "Medium";
            setReleventSettingsIfHave(importSettingName);
        }
        else if(importMode.equals("Hard")){
            seekBarWordsPerMin.setEnabled(true);
            seekBarWordsPerWindow.setEnabled(true);
            seekBarFontSize.setEnabled(true);
            buttonFontType1.setEnabled(true);
            buttonFontType2.setEnabled(true);
            buttonFontType3.setEnabled(true);
            buttonHard.setBackgroundResource(R.drawable.background_blue_round);
            buttonHard.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
            importSettingName = "Hard";
            setReleventSettingsIfHave(importSettingName);
        }
    }

    private void setReleventSettingsIfHave(String settingName){
        if(SettingLevelsSharedPreferences.getInstance(mContext).getAllWordsObject(settingName) != null){
            WordsGsonObject wordsGsonObject = SettingLevelsSharedPreferences.getInstance(mContext).getAllWordsObject(settingName);
            if(wordsGsonObject.wordsPerMin.length()>0){
                seekBarWordsPerMin.setProgress(Integer.parseInt(wordsGsonObject.wordsPerMin)-50);
                seekBarWordsPerWindow.setProgress(Integer.parseInt(wordsGsonObject.wordsPerWindow)-1);
                seekBarFontSize.setProgress(Integer.parseInt(wordsGsonObject.fontSize));
                changeFontType(wordsGsonObject.fontStyle);
            }
        }
    }

    private void saveSettings(){
        SettingLevelsSharedPreferences.getInstance(mContext).updateWordsObject(settingName, wordsPerMin, wordsPerWindow, textSize, textStyle);
        Intent intent = new Intent(mContext, SettingsLevelsActivity.class);
        intent.putExtra("commingFromWindowOrWord", "word");
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(mContext, SettingsLevelsActivity.class);
        intent.putExtra("commingFromWindowOrWord", "word");
        startActivity(intent);
        finish();
    }
}
