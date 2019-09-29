package com.ucsc.dinusha.speedread;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.ucsc.dinusha.speedread.sharedPreferenceDB.WebViewTextSharedPreferences;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Objects;

public class TextSourcesActivity extends AppCompatActivity {

    private CardView mCardViewPasteFromExternal;

    private Context mContext;

    private static final int RC_OCR_CAPTURE = 9003;
    private static final int REQUEST_CODE_OPENER = 2;
    private static final int REQUEST_CODE_PERMISSION_EXTERNAL_STORAGE = 689;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_sources);

        mContext = this;

        CardView mCardViewPreLoadedText = findViewById(R.id.activity_text_sources_cardView_preloaded_text);
        mCardViewPasteFromExternal = findViewById(R.id.activity_text_sources_cardView_paste_from_external);
        CardView mCardViewCamera = findViewById(R.id.activity_text_sources_cardView_camera);
        CardView mCardViewGoogleDrive = findViewById(R.id.activity_text_sources_cardView_google_drive);

        mCardViewPreLoadedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPreloadedText();
            }
        });

        mCardViewPasteFromExternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTapTargetView();
            }
        });

        mCardViewCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOCR();
            }
        });

        mCardViewGoogleDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStorage();
            }
        });

        try {
            Analytics.getInstance(this).pushToAnalytics("Text Sources Screen");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void goToTextShowActivity() {
        Intent intent = new Intent(getBaseContext(), TextFromSourceShowActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToPreloadedText() {
        Intent intent = new Intent(getBaseContext(), PreLoadTextTemplateActivity.class);
        startActivity(intent);
        finish();
    }

    private void showTapTargetView() {
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(mCardViewPasteFromExternal, "Clip texts from other apps", "Go to any other app, select a text and open in Speed Read using Share option")
                        // All options below are optional
                        .outerCircleColor(android.R.color.transparent)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.7f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.colorAccent)   // Specify a color for the target circle
                        .titleTextSize(30)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.lightWhite)      // Specify the color of the title text
                        .descriptionTextSize(20)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.lightWhite)  // Specify the color of the description text
                        .textColor(R.color.lightWhite)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.lightWhite)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(true)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(false)           // Specify whether the target is transparent (displays the content underneath)
                        // Specify a custom drawable to draw as the target
                        .targetRadius(250),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
                    }
                });

    }

    private void openOCR() {
        Intent intent = new Intent(mContext, OcrCaptureActivity.class);
        intent.putExtra(OcrCaptureActivity.AutoFocus, true);
        intent.putExtra(OcrCaptureActivity.UseFlash, false);

        startActivityForResult(intent, RC_OCR_CAPTURE);
    }

    private void openStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // start runtime permission
            boolean hasPermission = (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED);
            if (!hasPermission) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION_EXTERNAL_STORAGE);
            } else {
                showFileChooser();
            }
        } else {
            showFileChooser();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showFileChooser();
            } else {
                Toast.makeText(mContext, "To read text, this permission is needed.", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath());
        intent.setDataAndType(uri, "text/plain");
        startActivityForResult(Intent.createChooser(intent, "Select a text file to read"), REQUEST_CODE_OPENER);
    }

    public void readFile(File file) {
        // File file = new File(Environment.getExternalStorageDirectory(), "mytextfile.txt");
        StringBuilder builder = new StringBuilder();
        Log.e("main", "read start");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            br.close();
        } catch (Exception e) {
            Log.e("main", " error is " + e.toString());
        }
        Log.e("main", " read text is " + builder.toString());
        WebViewTextSharedPreferences.getInstance(mContext).addWebViewText(builder.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RC_OCR_CAPTURE:
                if (resultCode == CommonStatusCodes.SUCCESS) {
                    if (data != null) {
                        if (!data.getStringExtra(OcrCaptureActivity.TextBlockObject).toString().trim().equals("")) {
                            WebViewTextSharedPreferences.getInstance(mContext).addWebViewText(data.getStringExtra(OcrCaptureActivity.TextBlockObject).toString().trim());
                            goToTextShowActivity();

                        } else {
                            Toast.makeText(mContext, "No Text captured.", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(mContext, "Error reading text.", Toast.LENGTH_LONG).show();
                }
                break;

            case REQUEST_CODE_OPENER:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        readFile(getFile(uri));
                        goToTextShowActivity();
                        break;
                    }
                }
                Toast.makeText(mContext, "No Text captured.", Toast.LENGTH_LONG).show();
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private File getFile(Uri uri) {
        File file = new File("");
        switch (Objects.requireNonNull(uri.getAuthority())) {
            case "com.android.externalstorage.documents": {
                String filePath;
                String pathID = DocumentsContract.getDocumentId(uri);
                String[] split = pathID.split(":");
                String type = split[0];
                String id = split[1];
                if (type.equals("primary")) {
                    filePath = Environment.getExternalStorageDirectory() + "/" + id;
                    file = new File(filePath);
                }
                break;
            }
            case "com.android.providers.downloads.documents": {
                String filePath = uri.getPath();
                String pathID = filePath.substring(Objects.requireNonNull(filePath).lastIndexOf("/") + 1);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(pathID));
                file = new File(Objects.requireNonNull(contentUri.getPath()));
            }
            case "com.google.android.apps.docs.storage": {
                try {
                    file = saveFileIntoExternalStorageByUri(uri);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return file;
    }

    private File saveFileIntoExternalStorageByUri(Uri uri) throws Exception {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        int originalSize = inputStream.available();

        BufferedInputStream bis;
        BufferedOutputStream bos;
        String fileName = getFileName(uri);
        File file = makeEmptyFileIntoExternalStorageWithTitle(fileName);
        bis = new BufferedInputStream(inputStream);
        bos = new BufferedOutputStream(new FileOutputStream(
                file, false));

        byte[] buf = new byte[originalSize];
        bis.read(buf);
        do {
            bos.write(buf);
        } while (bis.read(buf) != -1);

        bos.flush();
        bos.close();
        bis.close();

        return file;
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (Objects.equals(uri.getScheme(), "content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public static File makeEmptyFileIntoExternalStorageWithTitle(String title) {
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        return new File(root, title);
    }
}
