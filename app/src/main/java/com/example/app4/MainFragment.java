package com.example.app4;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    public boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public boolean isExternalStorageReadable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
                Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    }
    File appSpecificExternalDir=null;
    FileOutputStream fos=null;
    String[] dataSet;
    CustomAdapter testAdapter;
    RecyclerView testView;
    RecyclerView.LayoutManager testLayout;

    public MainFragment() {

        super(R.layout.fragment_two);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setDataSet();
        testView = (RecyclerView)view.findViewById(R.id.recycler_view);
        NavController c = NavHostFragment.findNavController(MainFragment.this);
        testAdapter = new CustomAdapter(dataSet);
        testAdapter.setNavCon(c);
        testLayout = new LinearLayoutManager(getContext());
        testView.setLayoutManager(testLayout);

        testView.scrollToPosition(0);
        testView.setAdapter(testAdapter);

        Button addButton = (Button)view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.navigate(R.id.action_fragment_two_to_addFragment);
            }
        });
    }

    private void setDataSet() {
        FileReader hmm = null;
        File appSpecificExternalDir = new File(getActivity().getExternalFilesDir(null), "test_app4.txt");
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
