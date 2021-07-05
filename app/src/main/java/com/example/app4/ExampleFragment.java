package com.example.app4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

public class ExampleFragment  extends Fragment {


    public ExampleFragment() {
        super(R.layout.example_fragment);
    }

    @Nullable
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {





         Button b1=(Button)view.findViewById(R.id.button);
         Button b2=(Button)view.findViewById(R.id.button2);
        NavController c = NavHostFragment.findNavController(ExampleFragment.this);
        /*
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("klikam przycisk 1");
                c.navigate(R.id.action_exampleFragment_to_fragment_one);
                System.out.println("klikam przycisk 1");
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("klikam przycisk 2");
                c.navigate(R.id.action_exampleFragment_to_fragment_two);
                System.out.println("klikam przycisk 2");
            }
        });

        */



    };




}
