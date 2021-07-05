package com.example.app4;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ExampleAppWidgetProvider extends AppWidgetProvider {
    public final static String EXTRA_ITEM_POSITION = "extra item position";
   // private String[] dataSet;
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        //setDataSet(context);
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
           // Intent intent = new Intent(context, MainActivity.class);
           // PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            Intent serviceIntent = new Intent(context,WidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            Intent onClickIntent = new Intent(context,MainActivity.class);
            onClickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
            PendingIntent onClickPending = PendingIntent.getActivity(context,0,onClickIntent,0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_appwidget);
          //  views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
            views.setRemoteAdapter(R.id.widget_stack_view,serviceIntent);
            views.setPendingIntentTemplate(R.id.widget_stack_view,onClickPending);

            appWidgetManager.updateAppWidget(appWidgetId, views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_stack_view);

        }


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        appWidgetManager.notifyAppWidgetViewDataChanged(intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID),R.id.widget_stack_view);
        super.onReceive(context, intent);
    }

    private void setDataSet(Context context) {
        FileReader hmm = null;
        File appSpecificExternalDir = new File(context.getExternalFilesDir(null), "test_app4.txt");
        List<String> textBuild = new ArrayList<String>();
        String result;
        StringBuilder tmp = new StringBuilder();

        try {
            hmm = new FileReader(appSpecificExternalDir);

            BufferedReader br = new BufferedReader(hmm);

            while ((result = br.readLine()) != null) {
                if(result.equals("<>?"))
                {
                    textBuild.add(tmp.toString());
                    tmp.delete(0,tmp.length());
                }

                else
                {
                    tmp.append(result);
                    tmp.append('\n');
                }

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            int len = textBuild.size();
            dataSet = new String[len];


            for(int i=0;i<len;i++)
            {
                dataSet[i]=textBuild.get(i);
            }

        }
    }

    
}
