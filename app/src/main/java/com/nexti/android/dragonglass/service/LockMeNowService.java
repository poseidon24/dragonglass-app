package com.nexti.android.dragonglass.service;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.nexti.android.dragonglass.view.activity.unlock.UnlockActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LockMeNowService extends Service {
    public LockMeNowService() {
        super();
    }

    public static String CURRENT_PACKAGE_NAME = "com.commonsware.android.com.nexti.dragonglass";
    public static String LAST_LOCKED_PACKAGE_NAME = "com.commonsware.android.com.nexti.dragonglass";
    String lastAppPN = "";
    boolean noDelay = false;
    public static LockMeNowService instance;
    public static boolean stopped = false;
    public static List<String> userAppsInstalled = new ArrayList<>();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        CURRENT_PACKAGE_NAME = getApplicationContext().getPackageName();
        Log.e("Current PN", "" + CURRENT_PACKAGE_NAME);
        instance = this;

        userAppsInstalled = getUserApplicationsInstalled(getApplicationContext());
        startTimer();

        return START_STICKY;
    }

    private Timer timer;
    private TimerTask timerTask;
    long oldTime=0;
    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                //
                Log.d("task","task " + stopped);
                if (stopped)
                    stop();
                else
                    checkRunningApps();
            }
        };
    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void scheduleMethod() {

        ScheduledExecutorService scheduler = Executors
                .newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {

            @Override
            public void run() {
                if (stopped)
                    stop();
                else
                    checkRunningApps();
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    public void checkRunningApps() {
        Log.d("checkRunningApss", "checkRunningApss");
        //List<AndroidAppProcess> appProcessList = AndroidProcesses.getRunningForegroundApps(getBaseContext());
        //AndroidAppProcess appForegroundIndexZero = appProcessList.get(0);
        //String activityOnTop = appForegroundIndexZero.getPackageName();
        String activityOnTop = getTopAppName(getApplicationContext());

        Log.d("activity on TOp", "" + activityOnTop);

        // Provide the packagename(s) of apps here, you want to show password activity
        if (userAppsInstalled.contains(activityOnTop)
                //activityOnTop.contains("whatsapp")  // you can make this check even better
                && !activityOnTop.contains(CURRENT_PACKAGE_NAME)
                && !activityOnTop.contains(LAST_LOCKED_PACKAGE_NAME)) {
            // Show Password Activity
            Intent unlockActivity = new Intent(this, UnlockActivity.class);
            unlockActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(unlockActivity);
            LAST_LOCKED_PACKAGE_NAME = activityOnTop;
        } else {
            // DO nothing
        }
    }

    public static String getTopAppName(Context context) {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String strName = "";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                strName = getLollipopForegroundAppPkgNameByEvent(context);//getLollipopFGAppPackageName(context);
            } else {
                //noinspection deprecation
                strName = mActivityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strName;
    }

    private static String getLollipopForegroundAppPkgNameByEvent(Context ctx){
        String packageName = "";
        try {
            UsageStatsManager usageStatsManager = (UsageStatsManager) ctx.getSystemService(Context.USAGE_STATS_SERVICE);
            long milliSecs = 60 * 1000;
            Date date = new Date();
            UsageEvents usageEvents = usageStatsManager.queryEvents(date.getTime() - milliSecs, date.getTime());

            long highestMoveToForegroundTimeStamp = 0;
            while (usageEvents.hasNextEvent()) {
                UsageEvents.Event event = new UsageEvents.Event();
                usageEvents.getNextEvent(event);
                if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                    long timeStamp = event.getTimeStamp();
                    if (timeStamp > highestMoveToForegroundTimeStamp) {
                        packageName = event.getPackageName();
                        highestMoveToForegroundTimeStamp = timeStamp;
                    }
                }
            }
        }catch (Exception ex){
            Log.e("err","err",ex);
        }
        return packageName;
    }
    private static String getLollipopFGAppPackageName(Context ctx) {

        try {
            UsageStatsManager usageStatsManager = (UsageStatsManager) ctx.getSystemService(Context.USAGE_STATS_SERVICE);
            long milliSecs = 60 * 1000;
            Date date = new Date();
            List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, date.getTime() - milliSecs, date.getTime());
            if (queryUsageStats.size() > 0) {
                Log.i("LPU", "queryUsageStats size: " + queryUsageStats.size());
            }
            long recentTime = 0;
            String recentPkg = "";
            for (int i = 0; i < queryUsageStats.size(); i++) {
                UsageStats stats = queryUsageStats.get(i);

                //TODO: skip system apps if they shall not be included
                //ApplicationInfo a = stats.applicationInfo;
                //if((stats.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                //    continue;
                //}

                if (i == 0 && !CURRENT_PACKAGE_NAME.equals(stats.getPackageName())) {
                    Log.i("LPU", "PackageName: " + stats.getPackageName() + " " + stats.getLastTimeStamp());
                }
                if (stats.getLastTimeStamp() > recentTime) {
                    recentTime = stats.getLastTimeStamp();
                    recentPkg = stats.getPackageName();
                }
            }
            return recentPkg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void stop() {
        Log.d("Stopping Service","Stopping Service");
        instance.stoptimertask();
        if (instance != null) {
            stopped = true;
            instance.stopSelf();
            Log.d("Service stopped","Service stopped");
        }
    }

    public static List<String> getUserApplicationsInstalled(Context context){
        List<String> apps = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> appList = packageManager.queryIntentActivities(mainIntent, 0);
        Collections.sort(appList, new ResolveInfo.DisplayNameComparator(packageManager));
        List<PackageInfo> packs = packageManager.getInstalledPackages(0);
        for(int i=0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            ApplicationInfo a = p.applicationInfo;
            // skip system apps if they shall not be included
            if((a.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                continue;
            }
            apps.add(p.packageName);
        }
        return apps;
    }
}
