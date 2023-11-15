package com.example.zooalarm.database;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.zooalarm.R;
import com.example.zooalarm.ui.activities.MainActivity;

public class AlarmReceiver extends BroadcastReceiver {
    public static Ringtone r;
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_IMMUTABLE);
        Uri customSoundUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.birds);

        long[] vibrate = new long[] { 1000, 1000, 1000, 1000, 1000 };
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "zooalarm")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("ZooAlarm")
                .setContentText("Alarm!!!")
                .setVibrate(vibrate)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
        }else{
            notificationManagerCompat.notify(123, builder.build());
        }

        r = RingtoneManager.getRingtone(context, customSoundUri);
        r.setLooping(true);
        r.play();



    }


}
