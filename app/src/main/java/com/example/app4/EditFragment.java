package com.example.app4;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EditFragment extends Fragment {
    List<String> fileContent;

    public boolean isExternalStorageWritable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public boolean isExternalStorageReadable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
                Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED_READ_ONLY);
    }

    File appSpecificExternalDir = null;

    public EditFragment() {
        super(R.layout.edit_fragment);
    }

    @Nullable
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        Button save = (Button) view.findViewById(R.id.buttonSave1);
        Button delete = (Button) view.findViewById(R.id.deleteButton);
        EditText text = (EditText) view.findViewById(R.id.textToEdit);
        Bundle hmm = this.getArguments();
        String co = hmm.getString("co");
        text.setText(co);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceLines(co,text.getText().toString());
                Toast.makeText(getContext(),"edytowano tekst",Toast.LENGTH_LONG).show();

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceLines(co,null);
                Toast.makeText(getContext(),"usunieto tekst",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void replaceLines(String input,String output) {

        fileContent = new ArrayList<String>();
        FileReader hmm = null;
        FileWriter hmm1 = null;
        BufferedWriter bw = null;
        File appSpecificExternalDir = new File(getActivity().getExternalFilesDir(null), "test_app4.txt");
        StringBuilder tmp = new StringBuilder();
        String result;


            try {
                hmm = new FileReader(appSpecificExternalDir);

                BufferedReader br = new BufferedReader(hmm);

                while ((result = br.readLine()) != null) {
                  if(result.equals("<>?"))
                  {   tmp.deleteCharAt(tmp.length()-1);
                      fileContent.add(tmp.toString());
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
            input = input.substring(0,input.length()-1);
            for(int i=0;i<fileContent.size();i++)
            {
                if(output!=null)
                {
                    if((fileContent.get(i)).equals(input))
                        fileContent.set(i,output);
                }
                else
                {
                    if((fileContent.get(i)).equals(input))
                        fileContent.remove(i);
                }

            }



            try {
                hmm1 = new FileWriter(appSpecificExternalDir);
                bw = new BufferedWriter(hmm1);

                for(int i=0;i<fileContent.size();i++)
                {
                    bw.write(fileContent.get(i));
                    bw.newLine();
                    bw.write("<>?");
                    bw.newLine();
                }

                bw.close();
                hmm.close();
            } catch (IOException e) {
                e.printStackTrace();
            }



    }
}
