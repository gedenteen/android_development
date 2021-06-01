package com.example.widmetall;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Widget extends AppWidgetProvider {

    private static final String SYNC_CLICKED = "upd";
    private Document doc;
    private Thread secThread;
    private Runnable runnable;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        RemoteViews remote = new RemoteViews(context.getPackageName(), R.layout.widget);
        remote.setOnClickPendingIntent(R.id.button2, getPendingSelfIntent(context, SYNC_CLICKED));
        appWidgetManager.updateAppWidget(appWidgetIds, remote);
        runnable = new Runnable() {
            @Override
            public void run() {
                try {

                    doc = Jsoup.connect("https://www.cbr.ru/hd_base/metall/metall_base_new/").get();
                    Elements tables = doc.getElementsByTag("tbody");
                    Element table = tables.first();
                    Elements el = table.children();
                    Element str = el.get(1);
                    Elements strEl = str.children();
                    remote.setTextViewText(R.id.gold, strEl.get(1).text());
                    remote.setTextViewText(R.id.silver, strEl.get(2).text());
                    remote.setTextViewText(R.id.platinum, strEl.get(3).text());
                    remote.setTextViewText(R.id.pallad, strEl.get(4).text());
                    appWidgetManager.updateAppWidget(appWidgetIds, remote);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        secThread = new Thread(runnable);
        secThread.start();


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(SYNC_CLICKED.equals(intent.getAction())){
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            RemoteViews remote = new RemoteViews(context.getPackageName(), R.layout.widget);
            ComponentName watchWidget = new ComponentName(context, Widget.class);
            appWidgetManager.updateAppWidget(watchWidget, remote);

            runnable = new Runnable() {
                @Override
                public void run() {
                    try {

                        doc = Jsoup.connect("https://www.cbr.ru/hd_base/metall/metall_base_new/").get();
                        Elements tables = doc.getElementsByTag("tbody");
                        Element table = tables.first();
                        Elements el = table.children();
                        Element str = el.get(1);
                        Elements strEl = str.children();
                        remote.setTextViewText(R.id.gold, strEl.get(1).text());
                        remote.setTextViewText(R.id.silver, strEl.get(2).text());
                        remote.setTextViewText(R.id.platinum, strEl.get(3).text());
                        remote.setTextViewText(R.id.pallad, strEl.get(4).text());
                        appWidgetManager.updateAppWidget(watchWidget, remote);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            };
            secThread = new Thread(runnable);
            secThread.start();
        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

}
