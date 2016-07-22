package com.widget.lock;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class MyLockWidget extends AppWidgetProvider {
    //variable declaration part
    private DevicePolicyManager mDevicePolicyManager;
    private ActivityManager mAmaManager;
    private ComponentName mComponentName;
    private String TAG = "MyLockWidget";

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        try {

            mDevicePolicyManager = (DevicePolicyManager)context. getSystemService(
                    Context.DEVICE_POLICY_SERVICE);
            mAmaManager = (ActivityManager)context. getSystemService(
                    Context.ACTIVITY_SERVICE);
            mComponentName = new ComponentName(context, MyAdminReceiver.class);
            for (int i = 0; i < appWidgetIds.length; i++) {
                int widgetId = appWidgetIds[i];
                boolean active = mDevicePolicyManager.isAdminActive(mComponentName);
                Log.i(TAG, TAG + " Lock state::" + active);
                if (active) {
                    Log.i(MyLockWidget.class.toString(), "MyLockWidget Lock");
                    mDevicePolicyManager.lockNow();
                } else {
                    Intent intent = new Intent(context,
                            MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                        R.layout.activity_main);
                Intent intent = new Intent(context, MyLockWidget.class);
                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                        0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                remoteViews.setOnClickPendingIntent(R.id.button, pendingIntent);
                appWidgetManager.updateAppWidget(widgetId, remoteViews);
            }

        } catch (Exception e) {
            Log.e(TAG, TAG + " Exception is::" + e.getMessage());
        }
        mDevicePolicyManager = (DevicePolicyManager) context.getSystemService(
                Context.DEVICE_POLICY_SERVICE);
        mAmaManager = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        mComponentName = new ComponentName(context, MyAdminReceiver.class);
    }
}

