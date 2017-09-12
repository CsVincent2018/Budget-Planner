package com.example.vincent.budgetplannerfinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DBTools dbTools = new DBTools(this);
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private Toolbar mToolbar;
    DialogInterface.OnClickListener dialogresult;

   // int Year, Month, Day, pickdate, uid;
   // String todo, task, sdate, stime, fdate, ftime, id, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mtoggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getsalary();

        dialogresult = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        moveTaskToBack(true);
                        MainActivity.this.finish();
                }
            }
        };

                NavigationView mNavigationView = (NavigationView)findViewById(R.id.nav_menu);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_Home:
                        Toast.makeText(getApplicationContext(),"This is Home", Toast.LENGTH_LONG).show();
                        setTitle("Home");
                        break;
                    case R.id.nav_Expenses:
                        Intent intent = new Intent(MainActivity.this, Expenses.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_Category:
                        Intent intent2 = new Intent(MainActivity.this, Category.class);
                        startActivity(intent2);
                        setTitle("Category Section");
                        break;
                    case R.id.nav_Aboutus:
                        Intent intent3 = new Intent(MainActivity.this, AboutUs.class);
                        startActivity(intent3);
                        setTitle("About us");
                        break;
                    case R.id.nav_Reminder:
                        Intent intent4 = new Intent(MainActivity.this, Reminder.class);
                        startActivity(intent4);
                        setTitle("Reminder");
                        break;
                    case R.id.nav_ViewBudget:
                        Intent intent5 = new Intent(MainActivity.this, ViewBudget.class);
                        startActivity(intent5);
                        setTitle("Bugdet Section");
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//btnitem
        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getsalary(){//get
        Cursor data = dbTools.getMoneyData();
        if(data.getCount() == 0){
            Toast.makeText(getApplicationContext(),"Declare your Salary!!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent (MainActivity.this, SetSalary.class);
            startActivity(intent);

        }else{ Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_LONG).show();}
    }

    @Override//close
    public void onBackPressed() {
        AlertDialog.Builder bldr = new AlertDialog.Builder(MainActivity.this);
        bldr.setMessage("Do you want to Exit ?").setNegativeButton("No", dialogresult).setPositiveButton("Yes", dialogresult).show();
        //moveTaskToBack(true);
        //MainActivity.this.finish();
    }
}
