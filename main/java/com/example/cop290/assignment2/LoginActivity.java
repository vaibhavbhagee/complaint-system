package com.example.cop290.assignment2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {


    public static boolean login_control;
    public static final String SharedPref = "MahPrefs";
    Context thisContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText kerbID = (EditText) findViewById(R.id.kerbID);
        final EditText password = (EditText) findViewById(R.id.password);

        //SharedPreferences sharedpreferences = getSharedPreferences(SharedPref,Context.MODE_PRIVATE);

        SharedPreferences sharedpreferences = getSharedPreferences(SharedPref, Context.MODE_PRIVATE);
        kerbID.setText(sharedpreferences.getString("kerbID", ""));
        password.setText(sharedpreferences.getString("password", ""));

        if( !(sharedpreferences.getString("kerbID","") == "") && !(sharedpreferences.getString("password","") == "") )
            login(kerbID,password);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 login(kerbID,password);

                // Unbypassed           -> // Kya hae yaar??
                // By pass login
                /*Intent intent = new Intent(thisContext, MainActivity.class);
                startActivity(intent);*/

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void login(EditText kerbID, EditText password)
    {
        String kerberosIDString  = kerbID.getText().toString();
        String passwordString = password.getText().toString();

        SharedPreferences sharedpreferences = getSharedPreferences(SharedPref, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("kerbID", kerberosIDString);
        editor.putString("password", passwordString);
        editor.commit();


        LoadData loadDataObject = new LoadData();
        loadDataObject.setContext(thisContext);
        loadDataObject.login_request(kerberosIDString, passwordString);
        timer(1, loadDataObject);
        loadDataObject.flag[0] = false;
        loadDataObject.loginResponded = false;
        //TODO: Always loggs in

        /*Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);*/

        //TODO: Unsuccesful login, Store token in Shared Preferences

    }






    public boolean timer(final int x, final LoadData l){

        new CountDownTimer(50, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                if(x==100){
                    Toast.makeText(LoginActivity.this,"Connection Timed Out", Toast.LENGTH_LONG).show();
                }
                else if(l.flag[0]){
                    SharedPreferences sharedpreferences = getSharedPreferences(SharedPref, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("token", l.token);
                    editor.commit();

                    l.pseudo_login_request();
                    timer2(0, l);
                    l.flag[11] = false;


//                        JSONObject loginR = l.loginResponseJSON;
//                        try{
//                            String[] c_list = new String[loginR.getJSONArray("complaint_list").length()];
//                            for(int i = 0 ; i < loginR.getJSONArray("complaint_list").length(); i ++ )
//                                c_list[i] = loginR.getJSONArray("complaint_list").getString(i);
//
//                            l.get_complaint_details_request(c_list);
//                            timercomplaint(1, l, 9,c_list);
//                            l.flag[9] = false;
//
//                        }catch(Exception e){e.printStackTrace();}

//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);

                } else if(l.loginResponded == true)
                {
                    Toast.makeText(LoginActivity.this,"Incorrect Credentials", Toast.LENGTH_LONG).show();
                }
                else
                {
                    timer(x+1,l);
                }
            }
        }.start();
        return true;
    }

    public boolean timer2(final int x, final LoadData l){

        new CountDownTimer(50, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                if(x==100){
                    Toast.makeText(LoginActivity.this,"Connection Timed Out22", Toast.LENGTH_LONG).show();
                }
                else if(l.flag[11]){
                    System.out.print("ho gaya!!");
                    Toast.makeText(LoginActivity.this,"Logged in successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    timer2(x + 1, l);
                }
            }
        }.start();
        return true;
    }
/*

    public boolean timercomplaint(final int x, final LoadData l, final int whichflag, final String[] c_list){

        new CountDownTimer(50, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                if(x==100){
                    //Toast.makeText(LoginActivity.this, "Connection Timed Out", Toast.LENGTH_LONG).show();
                }
                else if(l.flag[whichflag]){
                    if(whichflag == 9)
                    {
                        Log.i("gaand", "sajdas");
                        try {
                            JSONObject cdr = l.complaintDetailsResponse;
                            Log.i("gaandu","sajdaus");

                            if (cdr.getBoolean("success")) {
                                Log.i("gaandusuc","sajdaussuc");

                                JSONArray comparr = (JSONArray) cdr.get("complaints");
                                Log.i("sandj","sadjsakdas");
                                l.complaintDetailsArray = new JSONObject[comparr.length()];
                                for ( int i = 0 ; i < comparr.length(); i ++ )
                                {
                                    l.complaintDetailsArray[i] = comparr.getJSONObject(i);
                                }

                                //Calling notifications timer
                                l.get_notifications_request(c_list);
                                timernotifications(1, l, 10);
                                l.flag[10] = false;

                            }
                            //list.add(new fraud("Title ka naam kya hona chaiyeh?? Shreyan kya hai.\n New line karke kya milega tujhe? " + i, "Lodger " + i, "bla"));
                        }catch(Exception e)
                        {
                            e.printStackTrace();
                        }

                    }

                } else {
                    timercomplaint(x + 1, l, whichflag, c_list);
                }
            }
        }.start();
        return true;
    }


    public boolean timernotifications(final int x, final LoadData l, final int whichflag){

        new CountDownTimer(50, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                if(x==100){
                    //Toast.makeText(LoginActivity.this, "Connection Timed Out", Toast.LENGTH_LONG).show();
                }
                else if(l.flag[whichflag]){
                    if(whichflag == 10)
                    {
                        Log.i("gaand", "sajdas");
                        try {
                            JSONObject cdr = l.notificationsJSON;

                            if (cdr.getBoolean("success")) {

                                JSONArray comparr = (JSONArray) cdr.get("notifications");
                                Log.i("sandj", "sadjsakdas");
                                l.notificationsArray = new JSONObject[comparr.length()];
                                for ( int i = 0 ; i < comparr.length(); i ++ )
                                {
                                    l.notificationsArray[i] = comparr.getJSONObject(i);
                                }



                            }
                            //list.add(new fraud("Title ka naam kya hona chaiyeh?? Shreyan kya hai.\n New line karke kya milega tujhe? " + i, "Lodger " + i, "bla"));
                        }catch(Exception e)
                        {
                            e.printStackTrace();
                        }

                    }

                } else {
                    timernotifications(x + 1, l, whichflag);
                }
            }
        }.start();
        return true;
    }*/
}
