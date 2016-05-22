package com.spadatech.mobile.android.foodframer.providers;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.spadatech.mobile.android.foodframer.R;
import com.spadatech.mobile.android.foodframer.activities.SplashScreenActivity;

/**
 * Created by Felipe S. Pereira on 5/22/16.
 */
public class WidgetProvider extends AppWidgetProvider{

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        for (int i = 0; i < count; i++) {
            int appWidgetId = appWidgetIds[i];

            Intent intent = new Intent(context, SplashScreenActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.plan_widget);
            views.setOnClickPendingIntent(R.id.btn_go_to_plans, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }


}
