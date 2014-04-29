package com.swear;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.swear.R;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.RemoteViews;

public class ShittyBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
		
		//Acquire the lock
		wl.acquire();

		try {
			//You can do the processing here update the widget/remote views.
			RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.sluty_layout);
	
			Date now = new Date();
			SimpleDateFormat hourFormat = new SimpleDateFormat("H", Locale.CANADA);
			SimpleDateFormat minuteFormat = new SimpleDateFormat("m", Locale.CANADA);
	
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
			
			ComponentName thiswidget = new ComponentName(context, FuckingWidgetProvider.class);
			AppWidgetManager manager = AppWidgetManager.getInstance(context);
			manager.updateAppWidget(thiswidget, remoteViews);
		}
		finally {
			//Release the lock
			wl.release();
		}

	}
	public void setOnetimeTimer(Context context){
		AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, ShittyBroadcastReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
	}
}