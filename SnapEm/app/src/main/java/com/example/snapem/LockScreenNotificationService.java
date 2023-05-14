package com.example.snapem;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class LockScreenNotificationService extends Service {

    private static final int NOTIFICATION_ID = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Context context = getApplicationContext();

        // Show the notification on the lock screen
        showNotificationOnLockScreen();

        return START_STICKY;
    }

    private void showNotificationOnLockScreen() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationChannel channel = new NotificationChannel(
                "lockscreen_channel_id",
                "Lock Screen Channel",
                NotificationManager.IMPORTANCE_HIGH
        );
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        notificationManager.createNotificationChannel(channel);

        // Create a PendingIntent for the notification's content intent
        Context context = getApplicationContext();
        Intent actionIntent = new Intent(context, MyReceiver.class);

        actionIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, actionIntent,   PendingIntent.FLAG_IMMUTABLE);
        System.out.println(actionPendingIntent);

        @SuppressLint("NotificationTrampoline") NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "lockscreen_channel_id")
                .setSmallIcon(R.drawable.lock)
                .setContentTitle("Panic")
                .setContentText("Send Your current location emergency SMS  and short video")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .addAction(R.drawable.lock, "Send emergency service", actionPendingIntent);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
