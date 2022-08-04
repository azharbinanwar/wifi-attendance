package dev.azsoft.wifiattendance.services;


import static dev.azsoft.wifiattendance.global.global.WifiAttendance.CHANNEL_ID;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.os.Process;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import dev.azsoft.wifiattendance.R;
import dev.azsoft.wifiattendance.Repository;
import dev.azsoft.wifiattendance.activities.dashboard.DashboardActivity;
import dev.azsoft.wifiattendance.databasehelper.DatabaseHelper;
import dev.azsoft.wifiattendance.databasehelper.SharedPrefs;
import dev.azsoft.wifiattendance.global.Const;
import dev.azsoft.wifiattendance.global.PermissionStatus;
import dev.azsoft.wifiattendance.interfaces.OnComplete;
import dev.azsoft.wifiattendance.models.Attendance;
import dev.azsoft.wifiattendance.models.Employee;
import dev.azsoft.wifiattendance.models.PrivateNetwork;


public class TimeRecordingService extends Service {
    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    Timer timer = new Timer();
    private final static String TAG = "TimeRecordingService";
    private final Repository repository = Repository.getInstance();
    private SharedPrefs prefs = SharedPrefs.getInstance();
    private DatabaseHelper databaseHelper;

    int interval = 1000 * 60;

    @Override
    public void onCreate() {
        super.onCreate();

        prefs.Initialize(this);
        prefs = SharedPrefs.getInstance();
        databaseHelper = new DatabaseHelper(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, DashboardActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.your_time_is_recording))
                .setSmallIcon(R.drawable.ic_wifi)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);
        //do heavy work on a background thread
        //stopSelf();


        timer.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        Log.d(TAG, "Service Running");
                        onStartRecordingTime();
                    }
                }, interval, interval
        );

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }


    void onStartRecordingTime() {
        if (hasPermission()) {
            WifiManager wifiManager = (WifiManager) getApplicationContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiManager.isWifiEnabled()) {
                PrivateNetwork privateNetwork = databaseHelper.getARecord(wifiInfo.getBSSID());
                if (privateNetwork != null) {
                    String cu = prefs.getString(Const.CURRENT_USER);
                    if (!cu.isEmpty()) {
                        Employee employee = Employee.fromString(cu);
                        Date date = new Date();
                        String attendanceId = employee.getUid().substring(0, employee.getUid().length() / 2) + date.getDay() + date.getMonth() + date.getYear();
                        repository.fetchADocument(attendanceId,
                                Const.ATTENDANCES, ob -> {
                                    if (ob instanceof DocumentSnapshot) {
                                        if (((DocumentSnapshot) ob).exists()) {
                                            Attendance attendance = ((DocumentSnapshot) ob).toObject(Attendance.class);
                                            attendance.setLastTrackTimestamp(new Date().getTime());
                                            attendance.setWorkingMinutes(attendance.getWorkingMinutes() + interval / (1000 * 60));
                                            if (attendance.getCheckedOut() == true) {
                                                return;
                                            }
                                            repository.onUpdateDocument(attendance.getId(), attendance, Const.ATTENDANCES, new OnComplete() {
                                                @Override
                                                public void onResult(Object ob) {
                                                    Log.d(TAG, "TimeRecordingService.onResult: Time updated");
                                                }
                                            });
                                        } else {
                                            Attendance attendance = new Attendance(
                                                    attendanceId, employee.getUid(), new Date().getTime(),
                                                    new Date().getTime(), interval / (1000 * 60),
                                                    privateNetwork.getAddedByUID(), privateNetwork.getSsid(),
                                                    new Date().getTime(), false
                                            );
                                            repository.onCreateDocument(attendance.getId(), attendance, Const.ATTENDANCES, new OnComplete() {
                                                @Override
                                                public void onResult(Object ob) {
                                                    Log.d(TAG, "TimeRecordingService.onResult: Time logged");
                                                }
                                            });

                                        }
                                    }
                                });
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "You're not connected with private network!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    Boolean hasPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

}