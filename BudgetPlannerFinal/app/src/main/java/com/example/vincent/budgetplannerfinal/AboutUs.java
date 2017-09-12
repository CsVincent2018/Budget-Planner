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

public class AboutUs extends AppCompatActivity {
    DBTools dbTools = new DBTools(this);
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private Toolbar mToolbar;
    DialogInterface.OnClickListener dialogresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout4);
        mtoggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();

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
                        AboutUs.this.finish();
                }
            }
        };

        NavigationView mNavigationView = (NavigationView)findViewById(R.id.nav_menu4);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_Aboutus:
                        Toast.makeText(getApplicationContext(),"This is 'About-Us' Section" ,Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_Home:
                        Intent intent = new Intent(AboutUs.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_Expenses:
                        Intent intent2 = new Intent(AboutUs.this, Expenses.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_Category:
                        Intent intent3 = new Intent(AboutUs.this, Category.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_Reminder:
                        Intent intent4 = new Intent(AboutUs.this, Reminder.class);
                        startActivity(intent4);
                        break;
           //         case R.id.nav_Exit:
             //           AboutUs.this.finish();
                    case R.id.nav_ViewBudget:
                        Intent intent5 = new Intent(AboutUs.this, ViewBudget.class);
                        startActivity(intent5);
                        break;
                }
                return true;
            }
        });
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

        AlertDialog.Builder bldr = new AlertDialog.Builder(AboutUs.this);
        bldr.setMessage("Do you want to Exit ?").setNegativeButton("No", dialogresult).setPositiveButton("Yes", dialogresult).show();

        //moveTaskToBack(true);
        //AboutUs.this.finish();
    }
}