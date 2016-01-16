package com.example.myaccount.uptospeed;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener{

    View mainView;
    Button socialBtn,tasksBtn,infoBtn;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mainView = inflater.inflate(R.layout.fragment_main, container, false);

        socialBtn = (Button) mainView.findViewById(R.id.social_button);
        tasksBtn = (Button) mainView.findViewById(R.id.tasks_button);
        infoBtn = (Button) mainView.findViewById(R.id.info_button);

        socialBtn.setOnClickListener(this);
        tasksBtn.setOnClickListener(this);
        infoBtn.setOnClickListener(this);

        return mainView;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.social_button:
                SocialFragment socialFragment = new SocialFragment();
                android.support.v4.app.FragmentTransaction socialFragmentTransaction =
                        getFragmentManager().beginTransaction();
                socialFragmentTransaction.replace(R.id.fragment_container, socialFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.tasks_button:
                TasksFragment tasksFragment = new TasksFragment();
                android.support.v4.app.FragmentTransaction tasksFragmentTransaction =
                        getFragmentManager().beginTransaction();
                tasksFragmentTransaction.replace(R.id.fragment_container, tasksFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.info_button:
                InfoFragment infoFragment = new InfoFragment();
                android.support.v4.app.FragmentTransaction infoFragmentTransaction =
                        getFragmentManager().beginTransaction();
                infoFragmentTransaction.replace(R.id.fragment_container, infoFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                break;
        }

    }
}
