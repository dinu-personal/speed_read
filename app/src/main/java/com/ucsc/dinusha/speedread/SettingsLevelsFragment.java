package com.ucsc.dinusha.speedread;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ucsc.dinusha.speedread.models.BaseGsonArray;
import com.ucsc.dinusha.speedread.models.WindowsGsonObject;
import com.ucsc.dinusha.speedread.models.WordsGsonObject;
import com.ucsc.dinusha.speedread.sharedPreferenceDB.SettingLevelsSharedPreferences;


public class SettingsLevelsFragment extends Fragment {

    private String mSettingType;
    private String mCustomeSettingName;
    private CardView cardViewEasy;
    private CardView cardViewMedium;
    private CardView cardViewHard;
    private ImageButton imageButtonAddCustomsetting;
    private LinearLayout linearLayoutCustomSettingButtons;
    View rootView;
    private BaseGsonArray gsonArray;

    public static SettingsLevelsFragment newInstance(String title) {
        SettingsLevelsFragment fragment = new SettingsLevelsFragment();
        fragment.mSettingType = title;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_settings_levels, container, false);

        imageButtonAddCustomsetting = rootView.findViewById(R.id.fragment_settings_levels_imageBottom_add_custom_setting);
        cardViewEasy = rootView.findViewById(R.id.fragment_settings_levels_cardView_easy);
        cardViewMedium = rootView.findViewById(R.id.fragment_settings_levels_cardView_medium);
        cardViewHard = rootView.findViewById(R.id.fragment_settings_levels_cardView_hard);

        cardViewEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToReadingActivity("Easy");
            }
        });

        cardViewMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToReadingActivity("Medium");
            }
        });

        cardViewHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToReadingActivity("Hard");
            }
        });

        imageButtonAddCustomsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInputDialog();
            }
        });

        if (mSettingType.equals("Windows"))
            setCustomWindowSettingButtons();
        else
            setCustomWordsSettingButtons();

        return rootView;
    }

    public String getSettingType(){
        return this.mSettingType;
    }

    private void setCustomWindowSettingButtons() {
        SettingLevelsSharedPreferences.getInstance(getContext()).addDefaultWindowInitialyIfThisIsFreshInstallation();
        gsonArray = SettingLevelsSharedPreferences.getInstance(getContext()).getWindowsGsonArray();
        linearLayoutCustomSettingButtons = rootView.findViewById(R.id.fragment_settings_levels_linearLayout_custom_setting_buttons);
        linearLayoutCustomSettingButtons.removeAllViews();

        for (int i = gsonArray.windowJsonArray.size() - 1; i >= 3; i--) {
            final WindowsGsonObject windowsGsonObject = gsonArray.windowJsonArray.get(i);
            View childLayout = getActivity().getLayoutInflater().inflate(R.layout.layout_custom_setting_button, null);
            TextView textViewName = childLayout.findViewById(R.id.layout_cutom_setting_button_textview);
            LinearLayout linearLayoutName = childLayout.findViewById(R.id.layout_cutom_setting_button_linearLayout_name);
            LinearLayout linearLayoutSettings = childLayout.findViewById(R.id.layout_cutom_setting_button_linearLayout_settings);
            LinearLayout linearLayoutDelete = childLayout.findViewById(R.id.layout_cutom_setting_button_linearLayout_delete);

            textViewName.setText(windowsGsonObject.name);
            if (windowsGsonObject.windowPerLine.equals("")) {
                textViewName.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            } else {
                textViewName.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            }

            linearLayoutName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (windowsGsonObject.windowPerLine.equals("")) {
                        Snackbar.make(rootView, "Please add settings first", Snackbar.LENGTH_LONG).show();
                    } else {
                        goToReadingActivity(windowsGsonObject.name);
                    }
                }
            });

            linearLayoutSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewCustomSetting(windowsGsonObject.name);
                }
            });

            linearLayoutDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteCustomSetting(windowsGsonObject.name);
                }
            });

            linearLayoutCustomSettingButtons.addView(childLayout);
        }
    }

    private void setCustomWordsSettingButtons(){
        SettingLevelsSharedPreferences.getInstance(getContext()).addDefaultWordInitialyIfThisIsFreshInstallation();
        gsonArray = SettingLevelsSharedPreferences.getInstance(getContext()).getWordsJsonArray();
        linearLayoutCustomSettingButtons = rootView.findViewById(R.id.fragment_settings_levels_linearLayout_custom_setting_buttons);
        linearLayoutCustomSettingButtons.removeAllViews();

        for(int i = gsonArray.wordsJsonArray.size() - 1; i >=3 ; i--){
            final WordsGsonObject wordsGsonObject = gsonArray.wordsJsonArray.get(i);
            View childLayout = getActivity().getLayoutInflater().inflate(R.layout.layout_custom_setting_button, null);
            TextView textViewName = childLayout.findViewById(R.id.layout_cutom_setting_button_textview);
            LinearLayout linearLayoutName = childLayout.findViewById(R.id.layout_cutom_setting_button_linearLayout_name);
            LinearLayout linearLayoutSettings = childLayout.findViewById(R.id.layout_cutom_setting_button_linearLayout_settings);
            LinearLayout linearLayoutDelete = childLayout.findViewById(R.id.layout_cutom_setting_button_linearLayout_delete);
            textViewName.setText(wordsGsonObject.name);

            if(wordsGsonObject.wordsPerMin.equals("")){
                textViewName.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            }
            else{
                textViewName.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
            }

            linearLayoutName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(wordsGsonObject.wordsPerMin.equals("")){
                        Snackbar.make(rootView, "Please add settings first", Snackbar.LENGTH_LONG).show();
                    }
                    else{
                        goToReadingActivity(wordsGsonObject.name);
                    }
                }
            });

            linearLayoutSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewCustomSetting(wordsGsonObject.name);
                }
            });

            linearLayoutDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteCustomSetting(wordsGsonObject.name);
                }
            });

            linearLayoutCustomSettingButtons.addView(childLayout);
        }
    }


    private void goToReadingActivity(String name) {
        Intent intent = new Intent(getContext(), SettingTypeReadingActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("settingType", mSettingType);
        startActivity(intent);
    }

    private void openInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        final View view = getActivity().getLayoutInflater().inflate(R.layout.layout_alertdialog_custom_setting_name, null);
        final EditText editText = (EditText) view.findViewById(R.id.layout_alertdialog_custom_setting_name_edittext);
        builder.setView(view);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCustomeSettingName = editText.getText().toString() + "";
                if (mCustomeSettingName.trim().equals("")) {
                    Snackbar.make(rootView, "Setting name cannot empty", Snackbar.LENGTH_LONG).show();
                } else if (SettingLevelsSharedPreferences.getInstance(getContext()).getAllWindowsObject(mCustomeSettingName) != null && mSettingType.equals("Windows")) {
                    Snackbar.make(rootView, "Already exists", Snackbar.LENGTH_LONG).show();
                } else if(SettingLevelsSharedPreferences.getInstance(getContext()).getAllWordsObject(mCustomeSettingName) != null && mSettingType.equals("Words")){
                    Snackbar.make(rootView, "Already exists", Snackbar.LENGTH_LONG).show();
                } else {
                    if (mSettingType.equals("Windows")) {
                        SettingLevelsSharedPreferences.getInstance(getContext()).addWindowsObject(mCustomeSettingName, "", "", "", "", "", "");
                        setCustomWindowSettingButtons();
                    } else {
                        SettingLevelsSharedPreferences.getInstance(getContext()).addWordsObject(mCustomeSettingName,"","","","");
                        setCustomWordsSettingButtons();
                    }
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void deleteCustomSetting(final String name) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setMessage("Are you sure ?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (mSettingType.equals("Windows")) {
                            SettingLevelsSharedPreferences.getInstance(getContext()).deleteWindowsObject(name);
                            setCustomWindowSettingButtons();
                        } else {
                            SettingLevelsSharedPreferences.getInstance(getContext()).deleteWordsObject(name);
                            setCustomWordsSettingButtons();
                        }
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void viewCustomSetting(String name) {
        if (mSettingType.equals("Windows")) {
            Intent intent = new Intent(getContext(), WindowsSetSettingsActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
            getActivity().finish();
        } else {
            Intent intent = new Intent(getContext(), WordsSetSettingsActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
            getActivity().finish();
        }

    }

}
