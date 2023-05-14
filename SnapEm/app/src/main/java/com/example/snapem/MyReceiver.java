package com.example.snapem;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import java.time.LocalTime;

public class MyReceiver extends BroadcastReceiver {

    LocalTime currentTime = null;
    private int event=0;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        PowerManager pm = (PowerManager) context.getApplicationContext().getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.FULL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP), "TAG");
        wakeLock.acquire();

        // to release the screen lock

//        if(this.event>=1){
//            Intent k = new Intent(context, MainActivity.class);
//            k.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            k.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            context.getApplicationContext().startActivity(k);



//        }
        System.out.println("awaaa"+event);






        //System.out.println("awaaaaaaaaaaaaaaaaaaaaaaaaaa");
//
//        if(currentTime==null){
//            this.currentTime = LocalTime.now();
//        }
//        else{
//            Duration duration = Duration.between(currentTime, LocalTime.now());
//
//// Get the duration in seconds
//            long durationInSeconds = Math.abs(duration.getSeconds());
//            System.out.println("awaaaaaaaaaaaaaaaaaaaaaaaaaa"+durationInSeconds);
//
//// Check if the duration is within 5 seconds
//
//            if (durationInSeconds <= 10& this.event>1) {
//
//
//                Intent k = new Intent(context, MainActivity.class);
//                k.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                k.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                context.getApplicationContext().startActivity(k);
//
//                // The current time is within 5 seconds of the specific time
//            } else {
//
//                // The current time is more than 5 seconds away from the specific time
//            }
//        }



        this.event++;

        wakeLock.release();
    }
}