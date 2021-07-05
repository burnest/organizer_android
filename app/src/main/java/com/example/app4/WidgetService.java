package com.example.app4;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.app4.ExampleAppWidgetProvider.EXTRA_ITEM_POSITION;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetItemsFactory(getApplicationContext(), intent);
    }

    class WidgetItemsFactory implements RemoteViewsFactory {
        private Context context;
        private int appWidgetId;
        private String[] dataSet;

        WidgetItemsFactory(Context context, Intent intent) {
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            setDataSet(context);
        }

        @Override
        public void onDataSetChanged() {
            setDataSet(context);
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return dataSet.length;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_stack_item);
            views.setTextViewText(R.id.widget_stack_item_text, dataSet[position]);
            Intent fillIntent = new Intent();
            fillIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
            views.setOnClickFillInIntent(R.id.widget_stack_item_text,fillIntent);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
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
                    if (result.equals("<>?")) {
                        textBuild.add(tmp.toString());
                        tmp.delete(0, tmp.length());
                    } else {
                        tmp.append(result);
                        tmp.append('\n');
                    }

                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                int len = textBuild.size();
                dataSet = new String[len];


                for (int i = 0; i < len; i++) {
                    dataSet[i] = textBuild.get(i);
                }

            }
        }
    }
}
