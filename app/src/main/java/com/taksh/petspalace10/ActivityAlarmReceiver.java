package com.taksh.petspalace10;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;

public class ActivityAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (v != null) v.vibrate(2000);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "PET_ALARM_CHANNEL")
                .setSmallIcon(R.drawable.ic_paw)
                .setContentTitle("Goal Achieved!")
                .setContentText(title + " is finished.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify((int) System.currentTimeMillis(), builder.build());
        }
    }
}