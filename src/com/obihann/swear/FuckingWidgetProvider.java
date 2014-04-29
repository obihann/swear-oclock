package com.obihann.swear;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.obihann.swearoclock.R;

public class FuckingWidgetProvider extends AppWidgetProvider {

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) { 
		Intent intent = new Intent(context, ShittyBroadcastReceiver.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
		
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		
		AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, ShittyBroadcastReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		
		//After after 3 seconds
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+ 100 * 3, 1000 , pi);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		ComponentName thisWidget = new ComponentName(context, FuckingWidgetProvider.class);

		for (int widgetId : appWidgetManager.getAppWidgetIds(thisWidget)) {
			Bundle myOptions = appWidgetManager.getAppWidgetOptions (widgetId);
			int category = myOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_HOST_CATEGORY, -1);
			boolean isKeyguard = category == AppWidgetProviderInfo.WIDGET_CATEGORY_KEYGUARD;
			int baseLayout = isKeyguard ? R.layout.sluty_layout_lock : R.layout.sluty_layout;
			
			//Get the remote views
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), baseLayout);
			// Set the text with the current time.
	
			Date now = new Date();
			SimpleDateFormat hourFormat = new SimpleDateFormat("H", Locale.CANADA);
			SimpleDateFormat minuteFormat = new SimpleDateFormat("m", Locale.CANADA);
			SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.CANADA);
	
			int hour = Integer.parseInt(hourFormat.format(now));
			int minute = Integer.parseInt(minuteFormat.format(now));
	
			if (hour > 12) {
				hour = hour - 12;
			}
			
			if (hour == 0) {
				hour = 12;
			}
	
			remoteViews.setTextViewText(R.id.hourTextView, EnglishNumberToWords.convert(hour));
			remoteViews.setTextViewText(R.id.minuteTextView, EnglishNumberToWords.convert(minute));
			remoteViews.setTextViewText(R.id.dayTextView, "Bitch, it's " + dayFormat.format(now));
			
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	}

	@Override
	public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
		//Do some operation here, once you see that the widget has change its size or position.
	}

}
