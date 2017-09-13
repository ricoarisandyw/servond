package com.servond.reaper.servond.Activity.Common;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.servond.reaper.servond.Activity.Mechanic.listOrderActivity;
import com.servond.reaper.servond.Activity.Client.myOrderActivity;
import com.servond.reaper.servond.Activity.Client.urgentActivity;
import com.servond.reaper.servond.Activity.Mechanic.myClientActivity;
import com.servond.reaper.servond.GPSTracker;
import com.servond.reaper.servond.Model.Mechanic;
import com.servond.reaper.servond.Model.Users;
import com.servond.reaper.servond.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class menuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TimePickerDialog.OnTimeSetListener {

    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    String userType;
    Gson gson;
    TextView tvName;
    String username;
    String name;
    String time;
    String date;
    int id_user = 0;
    GPSTracker gps = new GPSTracker(this);
    double longitude = 0, latitude = 0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //CHECK MY LOCATIO USING GPS
        gps = new GPSTracker(menuActivity.this);
        if(gps.canGetLocation()){
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        }else{
            gps.showSettingsAlert();
        }

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        Button btnClient = (Button) findViewById(R.id.btnSearchClient);
        Button btnUrgent = (Button) findViewById(R.id.btnUrgent);
        Button btnBook = (Button) findViewById(R.id.btnBook);
        Button btnHelp = (Button) findViewById(R.id.btnHelp);
        tvName = (TextView) findViewById(R.id.tv_name_menu);

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(menuActivity.this);
                builder2.setMessage("Mau Order Kapan?")
                        .setPositiveButton("Sekarang", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(menuActivity.this, urgentActivity.class);
                                i.putExtra("STRING_I_NEED", "Service");
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("Nanti", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                showDialog(999);
                            }
                        });
                // Create the AlertDialog object and return it
                builder2.create();
                builder2.show();
            }
        });

        btnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menuActivity.this, listOrderActivity.class);
                startActivity(i);
            }
        });

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menuActivity.this, AboutusActivity.class);
                startActivity(i);
            }
        });

        btnUrgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(menuActivity.this);
                builder.setMessage("Apa yang darurat?")
                        .setPositiveButton("Tambal", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(menuActivity.this, urgentActivity.class);
                                i.putExtra("STRING_I_NEED", "Tambal");
                                startActivity(i);
                            }
                        })
                        .setNegativeButton("Mogok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(menuActivity.this, urgentActivity.class);
                                i.putExtra("STRING_I_NEED", "Mogok");
                                startActivity(i);
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create();
                builder.show();
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        userType= intent.getStringExtra("userType");
        Toast.makeText(getApplicationContext(), userType,
                Toast.LENGTH_SHORT)
                .show();
        if(userType.equalsIgnoreCase("Mechanic")){
            ((ViewManager)btnBook.getParent()).removeView(btnBook);
            ((ViewManager)btnUrgent.getParent()).removeView(btnUrgent);
            initializeMechanic();
        }else{
            ((ViewManager)btnClient.getParent()).removeView(btnClient);
            initializeUsers();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_myOrder) {
            if(userType.equalsIgnoreCase("Mechanic")){
                Toast.makeText(getApplicationContext(), "Not Avalaible For Mechanic",
                        Toast.LENGTH_SHORT)
                        .show();
            }else {
                Intent i = new Intent(menuActivity.this, myOrderActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }
        } else if (id == R.id.nav_myClient) {
            if(userType.equalsIgnoreCase("Mechanic")){
                Intent i = new Intent(menuActivity.this, myClientActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            }else {
                Toast.makeText(getApplicationContext(), "Not Avalaible For Client",
                        Toast.LENGTH_SHORT)
                        .show();
            }

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }
//AFTER DATE GET, SHOW TIME DIALOG
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    Dialog timeDialog = showTime();
                    timeDialog.show();
                    saveDate(arg1, arg2+1, arg3);
                }
            };

    public  void saveDate(final int year, final int month, final int day){
        date = year+"-"+month+"-"+day;
    }
    private void showDate() {
        RequestQueue requestQueue = Volley.newRequestQueue(menuActivity.this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        final Gson gson = gsonBuilder.create();

        String INPUT_PESANAN_URL = "https://servond.000webhostapp.com/input_order.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, INPUT_PESANAN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response :", response);
                        responseInit posts =  gson.fromJson(response, responseInit.class);
                        if(posts.getStatus()==1){
                            Toast.makeText(menuActivity.this,"Book Success",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(menuActivity.this,"Book Failed",Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(menuActivity.this,"Cek Koneksi Internet"+"\n",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
//                GET LOCATION EVERY YOU TAKE THE KEY
                if(gps.canGetLocation()){
                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                }else{
                    gps.showSettingsAlert();
                }
                Map<String, String> params = new HashMap<String, String>();
                if(longitude!=0) {
                    params.put("need_at", date);
                    params.put("need_time", time);
                    params.put("id_customer", Integer.toString(id_user));
                    params.put("description", "Booking Service");
                    params.put("category", "Service");
                    params.put("longitude", Double.toString(longitude));
                    params.put("latitude", Double.toString(latitude));
                }
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void initializeUsers(){
        Intent intent = getIntent();
        username= intent.getStringExtra("username");
        //open --JSON--
        RequestQueue requestQueue = Volley.newRequestQueue(menuActivity.this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        String url;
        url = "https://servond.000webhostapp.com/detailUsers.php?username="+username;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        responseInit posts =  gson.fromJson(response, responseInit.class);
                        Log.d("Response", posts.getResult().get(0).getName());
                        name = posts.getResult().get(0).getName();
                        id_user = posts.getResult().get(0).getId_user();
                        tvName = (TextView) findViewById(R.id.tv_name_menu);
                        tvName.setText(name);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Response", "Error");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                return params;
            }
        };
        requestQueue.add(postRequest);
        //close --JSON--
    }

    public void initializeMechanic(){
        Intent intent = getIntent();
        username= intent.getStringExtra("username");
        //open --JSON--
        RequestQueue requestQueue = Volley.newRequestQueue(menuActivity.this);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();
        String url;
        url = "https://servond.000webhostapp.com/detailMechanic.php?username="+username;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        responseMecha posts =  gson.fromJson(response, responseMecha.class);
                        Log.d("Response", posts.getResult().get(0).getName());
                        name = posts.getResult().get(0).getName();
                        saveData("username", name);
                        saveData("id_mechanic", Integer.toString(posts.getResult().get(0).getId_mechanic()));
                        id_user = posts.getResult().get(0).getId_mechanic();
                        tvName = (TextView) findViewById(R.id.tv_name_menu);
                        tvName.setText(name);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Response", "Error");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                return params;
            }
        };
        requestQueue.add(postRequest);
        //close --JSON--
    }


    public class responseInit{
        private int status;
        private ArrayList<Users> result;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public ArrayList<Users> getResult() {
            return result;
        }

        public void setResult(ArrayList<Users> result) {
            this.result = result;
        }
    }

    public class responseMecha{
        private int status;
        private ArrayList<Mechanic> result;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public ArrayList<Mechanic> getResult() {
            return result;
        }

        public void setResult(ArrayList<Mechanic> result) {
            this.result = result;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Dialog showTime(){
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return new TimePickerDialog(menuActivity.this, this, hour, minute,
                DateFormat.is24HourFormat(menuActivity.this));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        time = Integer.toString(hourOfDay) + ":" + Integer.toString(minute);
        showDate();
    }

    public void saveData(String name, String value){
        SharedPreferences prefs = getSharedPreferences("UserData", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(name, value);
        Log.d(name + " masuk :", value);
        editor.commit();
    }
}