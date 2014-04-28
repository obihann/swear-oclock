package com.swear;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.swear.EnglishNumberToWords;


public class SwearWidgetProvider extends AppWidgetProvider {

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int appWidgetId : appWidgetIds) {
            drawWidget(context, appWidgetId);
        }
    }

    private void redrawWidgets(Context context) {
        int[] appWidgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(
                new ComponentName(context, SwearWidgetProvider.class));
        
        for (int appWidgetId : appWidgetIds) {
            drawWidget(context, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        
        redrawWidgets(context);
    }
    
    private void drawWidget(Context context, int appWidgetId) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widgetlayout);
        
        Date now = new Date();
        SimpleDateFormat hourFormat = new SimpleDateFormat("H", Locale.CANADA);
        SimpleDateFormat minuteFormat = new SimpleDateFormat("m", Locale.CANADA);
        
        int hour = Integer.parseInt(hourFormat.format(now));       
        int minute = Integer.parseInt(minuteFormat.format(now));
        
        if (hour > 12) {
        	hour = hour - 12;
        }
        
        rv.setTextViewText(R.id.hourTextView, EnglishNumberToWords.convert(hour));
        rv.setTextViewText(R.id.minuteTextView, EnglishNumberToWords.convert(minute));
        
        appWidgetManager.updateAppWidget(appWidgetId, rv);
    }
 
}
