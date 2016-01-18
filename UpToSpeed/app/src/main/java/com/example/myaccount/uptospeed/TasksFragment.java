package com.example.myaccount.uptospeed;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.gesture.Gesture;
import android.view.MotionEvent;
import static android.view.GestureDetector.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class TasksFragment extends Fragment {

    View tasksView;
    ToggleButton waterToggle, groceriesToggle;
    Button foodButton;
    Intent intent = null;


    public TasksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tasksView = inflater.inflate(R.layout.fragment_tasks, container, false);

        waterToggle = (ToggleButton) tasksView.findViewById(R.id.water);
        groceriesToggle = (ToggleButton) tasksView.findViewById(R.id.groceries);
        foodButton = (Button) tasksView.findViewById(R.id.food);


        foodButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast toast;
                CharSequence text = "Here is a toast! Try long press!";
                toast = Toast.makeText(getActivity().getApplicationContext(), text, Toast.LENGTH_LONG);
                toast.show();
            }
        });
        foodButton.setOnLongClickListener(new View.OnLongClickListener(){
            public boolean onLongClick(View v){
                intent = new Intent(android.content.Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:42.650444,23.3788408,18z?q=restaurants"));
                startActivity(intent);
                return false;
            }
        });

        waterToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ((MainActivity)getActivity()).alarmMethod();
                    Snackbar.make(buttonView, "You have enabled hourly water reminder!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(buttonView, "You have disabled hourly water reminder!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                }
            }

            );
            groceriesToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()

            {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Snackbar.make(buttonView, "Groceries reminder enabled! Double tap to edin groceries list!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        Snackbar.make(buttonView, "You have disabled daily groceries reminder!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            });

        return tasksView;
    }
}
