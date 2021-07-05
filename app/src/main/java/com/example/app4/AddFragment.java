package com.example.app4;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AddFragment extends Fragment {
    String fileContent;
    public boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public boolean isExternalStorageReadable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
                Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    }
    File appSpecificExternalDir=null;

    public AddFragment() {
        super(R.layout.fragment_one);
       // appSpecificExternalDir = new File(context.getExternalFilesDir(null), "test_app4");

    };
    @Nullable
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        Button save = (Button)view.findViewById(R.id.buttonSave);

        EditText text = (EditText)view.findViewById(R.id.textIn);

        if(!isExternalStorageWritable())
        {
            save.setEnabled(false);
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileContent = text.getText().toString();
                File appSpecificExternalDir = new File(getActivity().getExternalFilesDir(null), "test_app4.txt");

                try
                {
                    FileWriter hmm = new FileWriter(appSpecificExternalDir, true);
                    BufferedWriter bw = new BufferedWriter(hmm);
                    bw.write(fileContent);
                    bw.newLine();
                    bw.write("<>?");
                    bw.newLine();
                    bw.close();
                    hmm.close();

/*
                    fos = new FileOutputStream(appSpecificExternalDir);
                    fos.write(fileContent.getBytes());
*/

                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                text.setText("");
                Toast.makeText(getContext(),"zapiaslem tekst",Toast.LENGTH_LONG).show();


            }
        });

    }


}
