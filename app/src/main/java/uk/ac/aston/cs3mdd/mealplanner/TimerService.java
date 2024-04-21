package uk.ac.aston.cs3mdd.mealplanner;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class TimerService extends Service {

    private CountDownTimer countDownTimer;
    public static final String TIMER_UPDATE_ACTION = "uk.ac.aston.cs3mdd.mealplanner.TIMER_TICK";
    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "timer_channel";
    private NotificationManager notificationManager;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.hasExtra("millisInFuture")) {
            long totalMillis = intent.getLongExtra("millisInFuture", 0);

            // Start the service as a foreground service
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel();
            }

            // Cancel any previous notification
            cancelNotification();

            // Start the countdown timer
            countDownTimer = new CountDownTimer(totalMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    // Broadcast the remaining time to any interested components
                    Intent tickIntent = new Intent(TIMER_UPDATE_ACTION);

                    tickIntent.putExtra("timeLeftInMillis", millisUntilFinished);
                    sendBroadcast(tickIntent);
                }

                @Override
                public void onFinish() {
                    // Timer finished, send notification
                    sendNotification();
                    stopSelf(); // Stop the service
                }
            }.start();
        } else {
            stopSelf(); // Stop the service if the intent or its extras are null
        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Cancel the timer to prevent memory leaks
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel for API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Timer ";
            String description = "Notification for timer";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendNotification() {
        // Create Intent
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Creates a notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_meal)
                .setContentTitle("Timer Complete")
                .setContentText("Quick check on your food!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Get the notification manager
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Display the notification
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void cancelNotification() {
        if (notificationManager != null) {
            notificationManager.cancel(NOTIFICATION_ID);
        }
    }
}
