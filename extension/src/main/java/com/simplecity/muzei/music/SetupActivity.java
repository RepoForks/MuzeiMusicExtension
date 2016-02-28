package com.simplecity.muzei.music;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.apps.muzei.api.MuzeiArtSource;

public class SetupActivity extends AppCompatActivity {
    private static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            // If we have permission
            setResult(RESULT_OK);
            finish();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != REQUEST_READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setResult(RESULT_OK);
            finish();
        } else {
            // Push the user to the GallerySettingsActivity to see inline rationale or just
            // select individual photos
            Intent intent = new Intent(this, SettingsActivity.class);
            if (getIntent().getBooleanExtra(MuzeiArtSource.EXTRA_FROM_MUZEI_SETTINGS, false)) {
                intent.putExtra(MuzeiArtSource.EXTRA_FROM_MUZEI_SETTINGS, true);
            }
            startActivity(intent);
        }
    }
}