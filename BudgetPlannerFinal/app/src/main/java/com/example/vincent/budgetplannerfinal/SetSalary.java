package com.example.vincent.budgetplannerfinal;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class SetSalary extends AppCompatActivity {
    DBTools dbTools = new DBTools(this);
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private Toolbar mToolbar;

    DialogInterface.OnClickListener dialogresult;
    EditText etsalary, etaddetives;
    Button btnsave, btnsbg, btnebg;
    TextView txtsbg , txtebg, txtdays;

    static final int DATE_DIALOG = 0;
    //int Year, Month, Day, pickdate, uid;
    Calendar c = Calendar.getInstance();
    int Year = c.get(Calendar.YEAR);
    int Month = c.get(Calendar.MONTH);
    int Day = c.get(Calendar.DAY_OF_MONTH);
//    long msDiff = Calendar.getInstance().getTimeInMillis() - c.getTimeInMillis();
  //  long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);
    int pickdate;
    int uid;
  //  String todo, task, sdate, stime, fdate, ftime, id, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_salary);
        etsalary = (EditText) findViewById(R.id.txtYourSalary);
        //etaddetives = (EditText)findViewById(R.id.txtaddetive);
        btnsave = (Button) findViewById(R.id.btner);
        btnsbg = (Button) findViewById(R.id.btnstart);
        btnebg = (Button) findViewById(R.id.btnend);
        txtsbg = (TextView) findViewById(R.id.txtSDATE);
        txtebg = (TextView) findViewById(R.id.txtFDATE);
        //    txtopt = (TextView)findViewById(R.id.txtoptional);
        txtdays = (TextView) findViewById(R.id.txtnumberdays);

        //    String txdays = txtdays.getText().toString();
        //  daysDiff = Integer.parseInt(txdays);
        btnsbg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickdate = v.getId();
                showDialog(DATE_DIALOG);
            }
        });

        btnebg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickdate = v.getId();
                showDialog(DATE_DIALOG);
            }
        });

        dialogresult = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        moveTaskToBack(true);
                        SetSalary.this.finish();
                }
            }
        };

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ets = etsalary.getText().toString();
                //String ts = etsalary.getHint().toString();
                int ns1 = 0;
                if (!ets.equals(""))
                    ns1 = Integer.parseInt(ets);

                //String addts = etaddetives.getText().toString();
                //  int ats = Integer.parseInt(addts);
                String strt = txtsbg.getText().toString();
                String end = txtebg.getText().toString();
                // String opt = txtopt.getText().toString();
                if (ets.length() != 0 && strt.length() != 0 && end.length() != 0) {
                    boolean isInserted = dbTools.insertSalary(ns1, strt, end);
                    if (isInserted == true) {
                        Toast.makeText(getApplicationContext(), "Salary Save!", Toast.LENGTH_LONG).show();
                        etsalary.setText("");
                        //  etaddetives.setText("");
                        txtsbg.setText("");
                        txtebg.setText("");
                        Intent intent = new Intent(SetSalary.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Testing", Toast.LENGTH_LONG).show();
                    }
                } //else if (ets.length() == 0) {
                    //Toast.makeText(getApplicationContext(), "Put Salary Value", Toast.LENGTH_SHORT).show();
                //}
                else {
                    Toast.makeText(getApplicationContext(), "Put Salary Value", Toast.LENGTH_LONG).show();
                }
            }
        });
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout01);
        mtoggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getsalary();
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_menu01);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_Home:
                        Intent i = new Intent(SetSalary.this, MainActivity.class);
                        startActivity(i);
                        break;
                    case R.id.nav_Expenses:
                        Intent intent = new Intent(SetSalary.this, Expenses.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_Category:
                        Intent intent2 = new Intent(SetSalary.this, Category.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_Aboutus:
                        Intent intent3 = new Intent(SetSalary.this, AboutUs.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_Reminder:
                        Intent intent4 = new Intent(SetSalary.this, Reminder.class);
                        startActivity(intent4);
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, dateSetListener, Year, Month, Day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Year = year;
            Month = month;
            Day = dayOfMonth;
            switch (pickdate){
                case R.id.btnstart:
                    txtsbg.setText(new StringBuilder().append(Month + 1).append("-").append(Day).append("-").append(Year).append(" "));
                    break;
                case R.id.btnend:
                    txtebg.setText(new StringBuilder().append(Month + 1).append("-").append(Day).append("-").append(Year).append(" "));
                    break;
            }


        }
    };

    public boolean onOptionsItemSelected(MenuItem item) {//btnitem
        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getsalary(){//get
        Cursor data = dbTools.getMoneyData();
        if(data.getCount() == 0){
            Toast.makeText(getApplicationContext(), "no value", Toast.LENGTH_LONG).show();

        }else{ /*etsalary.setEnabled(false); etsalary.setClickable(false);
        txtsbg.setText("Already Set"); txtebg.setText("Already Set"); btnsbg.setEnabled(false); btnsbg.setClickable(false);
        btnebg.setEnabled(false); btnebg.setClickable(false);*/
        Intent i = new Intent(SetSalary.this, MainActivity.class);
        startActivity(i);}
    }


    @Override//close
    public void onBackPressed() {
        AlertDialog.Builder bldr = new AlertDialog.Builder(SetSalary.this);
        bldr.setMessage("Do you want to Exit ? ").setNegativeButton("No", dialogresult).setPositiveButton("Yes", dialogresult).show();
        //moveTaskToBack(true);
        //SetSalary.this.finish();
    }
}
