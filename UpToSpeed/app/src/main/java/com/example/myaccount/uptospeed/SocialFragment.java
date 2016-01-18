package com.example.myaccount.uptospeed;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;


public class SocialFragment extends Fragment  {


    //to new project
    private int port = 5503;

    private TextView gsmInfoView;
    private TextView userInput;
    private Button sendToServer;
    private Button showBtn;
    private Button talkBtn;


    View tasksView;


    String phoneNum = "";



    public static String[] responseNums = new String[10];

    public SocialFragment() {


        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        tasksView = inflater.inflate(R.layout.fragment_social, container, false);

        gsmInfoView = (TextView) tasksView.findViewById(R.id.serverInfo);
        userInput = (TextView) tasksView.findViewById(R.id.etMsg);
        sendToServer = (Button) tasksView.findViewById(R.id.bSend);
        showBtn = (Button) tasksView.findViewById(R.id.bShow);

        userInput.setText(MainActivity.m_Text);

        final int cellid = MainActivity.cellid;


        sendToServer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                phoneNum = String.valueOf(userInput.getText());
                Clients myClient = new Clients("192.168.0.104", port, gsmInfoView, String.valueOf(cellid), MainActivity.m_Text);
                myClient.execute();

            }
        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                ContactsFragment fragment = new ContactsFragment();
                fragmentTransaction.add(R.id.fragment_container, fragment);
                fragmentTransaction.commit();



            }
        });




        // Inflate the layout for this fragment
        return tasksView;

    }



    //CONNECTION TO SERVER

    public class Clients extends AsyncTask<Void, Void, Void> {

        String dstAddress;
        int dstPort;
        String response = "";
        String endCheck = "";
        TextView serverInfoView = null;
        String cellTowerID;
        String clientNum;



        Clients(String addr, int port, TextView serverInfoView, String cellTowerID, String clientNum) {
            dstAddress = addr;
            dstPort = port;
            this.serverInfoView = serverInfoView;
            this.cellTowerID = cellTowerID;
            this.clientNum = clientNum;
        }


        @Override
        protected Void doInBackground(Void... arg0) {

            Socket connection = null;
            Scanner socketIn = null;
            PrintWriter socketOut = null;


            try {

                connection = new Socket(dstAddress, dstPort);
                socketIn = new Scanner(new BufferedReader(new InputStreamReader(connection.getInputStream())));
                socketOut = new PrintWriter(connection.getOutputStream(), true);

                socketOut.println(clientNum);       //Send phone number
                socketOut.flush();

                socketOut.println(cellTowerID);    // send tower id
                socketOut.flush();

                int i =0;
                while (true) {

                    endCheck = socketIn.nextLine();

                    if (endCheck.equals("end")) {
                        break;
                    }


                    if(!(endCheck.equals("There is nobody around you") || endCheck.equals("Friends list around you: "))){

                        responseNums[i] = endCheck;
                        i++;

                    }

                    response += "\n" + endCheck;

                }


            } catch (UnknownHostException e) {

                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {

                e.printStackTrace();
                response = "IOException: " + e.toString();
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            serverInfoView.setText(response);


            super.onPostExecute(result);

        }

    }






}



