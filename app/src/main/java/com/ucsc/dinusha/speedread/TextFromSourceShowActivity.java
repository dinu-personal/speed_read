package com.ucsc.dinusha.speedread;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ucsc.dinusha.speedread.sharedPreferenceDB.WebViewTextSharedPreferences;

public class TextFromSourceShowActivity extends AppCompatActivity {

    private TextView mTextViewText;
    private Button mButtonNext;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_from_source_show);

        mContext = this;

        // Share with external
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.equals("text/plain")) {
                WebViewTextSharedPreferences.getInstance(mContext).addWebViewText(intent.getStringExtra(Intent.EXTRA_TEXT)+"");
            }
        }


        mTextViewText = (TextView)findViewById(R.id.activity_text_from_source_show_textView);
        mTextViewText.setText(WebViewTextSharedPreferences.getInstance(mContext).getWebViewText());

        mButtonNext = (Button)findViewById(R.id.activity_text_from_source_show_button_next);
        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SettingsLevelsActivity.class);
                startActivity(intent);
            }
        });

        try {
            Analytics.getInstance(this).pushToAnalytics("Text Preview Screen");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), TextSourcesActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
