package com.example.snapem;

import static android.service.notification.Condition.SCHEME;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;


import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 123;
    static final int ACTIVATION_REQUEST = 47;
    DevicePolicyManager devicePolicyManager;
    ComponentName demoDeviceAdmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home3);

        Button myButton = findViewById(R.id.start);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                devicePolicyManager.lockNow();
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button myGButton = findViewById(R.id.guid);
        myGButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchYouTubeVideo();
            }
        });

        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        demoDeviceAdmin = new ComponentName(this, DemoDeviceAdminReceiver.class);

        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(this.KEYGUARD_SERVICE);
        if (keyguardManager.isKeyguardSecure()) {
            if(keyguardManager.inKeyguardRestrictedInputMode()){
                Log.wtf("screen lock","There is no screen lock");
                Toast.makeText(this, "You have to add pin or password only", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                startActivity(intent);
            }
            else {
                Log.wtf("screen lock","There is screen lock");
                getAllPermisions();

                requestManageExternalStoragePermission();





            }

        } else{
            Log.wtf("screen lock","There is no screen lock");
            Toast.makeText(this, "You have to add pin or password", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
            startActivity(intent);



            // The device doesn't have a screen lock enabled
        }
    }



/////////////////////////////////////////////////////////////////////////////////////req manage seen eka//////////////////////////////////////////////////////////////////////////////////////
    private void requestManageExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
                // Permission is already granted, do your work here
            } else {
                // Permission is not granted, request it using ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION intent
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, PERMISSION_REQUEST_CODE);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            } else {
                // Permission is already granted, do your work here
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, do your work here
            } else {
                // Permission denied, handle the situation accordingly
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // Permission granted, do your work here
                } else {
                    // Permission denied, handle the situation accordingly
                }
            }
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void getAllPermisions(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            // Check if the app has the necessary permission
            if (getSystemService(KeyguardManager.class).isDeviceLocked()) {
                if (!getSystemService(NotificationManager.class).isNotificationPolicyAccessGranted()) {
                    // Permission is not granted, request it using ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS intent
                    Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent);
                } else {
                    // Permission is already granted, do your work here

                }
            } else {
                // The device is not locked, permission is not required

            }
        } else {
            // Show on Lock screen is not available on devices below Android O_MR1
            // Do your work here
        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);

        }
        // Check if the permission is already granted


        // Define a list of permissions to request
        String[] permissions = {
                Manifest.permission.CAMERA,
                Manifest.permission.USE_FULL_SCREEN_INTENT,
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                Manifest.permission.DISABLE_KEYGUARD,
                Manifest.permission.VIBRATE,
                Manifest.permission.WAKE_LOCK,
                Manifest.permission.FOREGROUND_SERVICE,
                Manifest.permission.RECORD_AUDIO,


        };

// Check if the app has the necessary permissions
        List<String> permissionsToRequest = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }

// Request permissions from the user
        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    permissionsToRequest.toArray(new String[0]), PERMISSION_REQUEST_CODE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MANAGE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
        else {
            System.out.println("it didnt get permisions ");
        }


        ///ACTIVATING DEVICE ADMIN

        Intent intent = new Intent(
                DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                demoDeviceAdmin);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "Your boss told you to do this");
        startActivityForResult(intent, ACTIVATION_REQUEST);




    }

    private void launchYouTubeVideo() {
        String videoUrl = "https://www.youtube.com/watch?v=8_sBkJP3uo4";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
        startActivity(intent);
    }


    @Override
    protected void onPause() {
        super.onPause();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        Intent intent = new Intent(this, LockScreenNotificationService.class);
        startService(intent);
    }
}