package com.example.vincent.budgetplannerfinal;

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
import android.widget.Toast;

public class Reminder extends AppCompatActivity {
    DBTools dbTools = new DBTools(this);
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private Toolbar mToolbar;
    DialogInterface.OnClickListener dialogresult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout5);
        mtoggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getsalary();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialogresult = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        moveTaskToBack(true);
                        Reminder.this.finish();
                }
            }
        };

        NavigationView mNavigationView = (NavigationView)findViewById(R.id.nav_menu5);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_Reminder:
                        Toast.makeText(getApplicationContext(),"This is 'Reminder' Section" ,Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_Aboutus:
                        Intent intent0 = new Intent(Reminder.this, AboutUs.class);
                        startActivity(intent0);
                        break;
                    case R.id.nav_Home:
                        Intent intent = new Intent(Reminder.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_Expenses:
                        Intent intent2 = new Intent(Reminder.this, Expenses.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_Category:
                        Intent intent3 = new Intent(Reminder.this, Category.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_ViewBudget:
                        Intent intent5 = new Intent(Reminder.this, ViewBudget.class);
                        startActivity(intent5);
                        break;
                 //   case R.id.nav_Exit:
                   //     Reminder.this.finish();
                }
                return true;
            }
        });
    }
    public void getsalary(){//get
        Cursor data = dbTools.getMoneyData();
        if(data.getCount() == 0){
            Toast.makeText(getApplicationContext(),"Declare your Salary!!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent (Reminder.this, SetSalary.class);
            startActivity(intent);

        }else{ Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_LONG).show();}
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder bldr = new AlertDialog.Builder(Reminder.this);
        bldr.setMessage("Do you want to Exit ?").setNegativeButton("No", dialogresult).setPositiveButton("Yes", dialogresult).show();

        //moveTaskToBack(true);
        //Reminder.this.finish();
    }
}