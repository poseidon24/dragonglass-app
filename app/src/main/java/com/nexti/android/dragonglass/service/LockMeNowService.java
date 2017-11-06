package com.nexti.android.dragonglass.service;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageEvents;
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

import com.nexti.android.dragonglass.bo.DgMobileBO;
import com.nexti.android.dragonglass.db.DataSource;
import com.nexti.android.dragonglass.model.entity.DgMobileEntity;
import com.nexti.android.dragonglass.view.activity.unlock.UnlockActivity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LockMeNowService extends Service {
    private final static String TAG = LockMeNowService.class.getSimpleName();

    public static String CURRENT_PACKAGE_NAME;// = "com.nexti.android.dragonglass";
    public static String LAST_LOCKED_PACKAGE_NAME = "com.nexti.android.dragonglass";
    //String lastAppPN = "";
    //boolean noDelay = false;
    public static LockMeNowService instance;
    public static boolean stopped = true;
    public static List<String> userAppsInstalled = new ArrayList<>();
    private DataSource dataSource;
    private DgMobileBO dgMobileBO;

    private static int DG_MOBILE_UNIQUE_ID = 1;
    private List<String> customAppsList =  new ArrayList<>();

    public LockMeNowService() {
        super();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        CURRENT_PACKAGE_NAME = getApplicationContext().getPackageName();
        Log.d(TAG, "Current PN: " + CURRENT_PACKAGE_NAME);
        instance = this;
        stopped = false;

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
        timer.schedule(timerTask, 500, 500); //1000, 1000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                //
                Log.d(TAG,"task " + stopped);
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
    public void stopTimerTask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /*
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
    */

    public void checkRunningApps() {
        Log.d(TAG, "checkRunningApps");
        //List<AndroidAppProcess> appProcessList = AndroidProcesses.getRunningForegroundApps(getBaseContext());
        //AndroidAppProcess appForegroundIndexZero = appProcessList.get(0);
        //String activityOnTop = appForegroundIndexZero.getPackageName();

        //Check first via database if locking apps is enabled
        if (isAppLockEnabledOnDb()) {

            String activityOnTop = getTopAppName(getApplicationContext());
            Log.d(TAG, "activity on Top: " + activityOnTop);

            // Check if the Activity On Top is a User App (not a System App)
            // AND
            // Check if the Activity On Top is not this app itself
            // AND if it is not the last app blocked (to avoid cycling)
            if (userAppsInstalled.contains(activityOnTop)
                    && !activityOnTop.contains(CURRENT_PACKAGE_NAME)
                    && !activityOnTop.contains(LAST_LOCKED_PACKAGE_NAME)) {

                // Check if the specific List of Apps to be locked has not been set (then all apps will be blocked)
                // OR
                // if the specific List of Apps to be locked contains the name of the Activity on Top
                if (customAppsList.size()==0
                        || (customAppsList.size()>0 && customAppsList.contains(activityOnTop))) {
                    // Show Locked by Password Activity
                    Intent unlockActivity = new Intent(this, UnlockActivity.class);
                    unlockActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(unlockActivity);
                    LAST_LOCKED_PACKAGE_NAME = activityOnTop;
                }
            }
        }
    }

    public boolean isAppLockEnabledOnDb(){
        boolean isLockEnabled = false;
        // TO-DO apply logic for check if UPDATE_TIMESTAMP has exceeded the max time allowed
        // (this can expose that mobile has not being connected to internet for a long time
        DgMobileEntity dgMobileEntity = getDgMobileBO().getDgMobileEntity();
        if (dgMobileEntity!=null){
            Log.d(TAG,  dgMobileEntity.toString() );
            // check if there is a Lock-apps instruction on server side established for this device
            switch (dgMobileEntity.getAppsAccessEnabled()){
                case 0: //All Apps Access Disabled
                    //lock all apps
                    isLockEnabled = true;
                    customAppsList = new ArrayList<>();
                    break;
                case 1: //All Apps Access Enabled
                    //allow all apps
                    isLockEnabled = false;
                    customAppsList = new ArrayList<>();
                    break;
                case 2: //List of Apps Disabled
                    //lock certain set of apps
                    if (StringUtils.isNotEmpty(dgMobileEntity.getAppsList()) ) {
                        isLockEnabled = true;
                        customAppsList = Arrays.asList(dgMobileEntity.getAppsList().split("\\s*,\\s*"));
                    }
                    break;
                case 3: //List of Apps Enabled
                default:
                    throw new IllegalStateException("Property AppsAccessEnabled has an unexpected/unimplemented value. " + dgMobileEntity.toString());
            }
        }
        return isLockEnabled;
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
            Log.e(TAG,"err",ex);
        }
        return packageName;
    }
    /*
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
    */

    public static void stop() {
        Log.d(TAG,"Stopping Service");
        instance.stopTimerTask();
        if (instance != null) {
            stopped = true;
            instance.stopSelf();
            Log.d(TAG,"Service stopped");
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


    public DataSource getDataSource() {
        if (dataSource==null){
            dataSource = new DataSource(getApplicationContext());
        }
        if (dataSource.getDb()!=null && !dataSource.getDb().isOpen()){
            dataSource.open(true); // opened as ReadOnly if not available
        }
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DgMobileBO getDgMobileBO() {
        if (dgMobileBO==null){
            dgMobileBO = new DgMobileBO(getApplicationContext(), getDataSource(), DG_MOBILE_UNIQUE_ID);
        }
        return dgMobileBO;
    }

    public void setDgMobileBO(DgMobileBO dgMobileBO) {
        this.dgMobileBO = dgMobileBO;
    }
}
