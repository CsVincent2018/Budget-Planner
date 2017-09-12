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
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ViewBudget extends AppCompatActivity {
    DBTools dbTools = new DBTools(this);
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private Toolbar mToolbar;

    GraphView grp;
    DialogInterface.OnClickListener dialogresult;
    TextView txtvbgt;
    LineGraphSeries<DataPoint>series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_budget);

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayoutbdgt);
        mtoggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        txtvbgt = (TextView)findViewById(R.id.txtviewbudget) ;
        grp = (GraphView)findViewById(R.id.grph) ;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getsalary();

        Cursor balance  = dbTools.getBalance();
        if(balance.getCount() == 0){Toast.makeText(getApplicationContext(),"We have problem", Toast.LENGTH_LONG).show();}
        else{while(balance.moveToNext()){
            int blnc = balance.getInt(0);
            txtvbgt.setText(String.valueOf(blnc));
            }
        }

        series = new LineGraphSeries<DataPoint>(getData());
        grp.addSeries(series);

        dialogresult = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        moveTaskToBack(true);
                        ViewBudget.this.finish();
                }
            }
        };
        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_menubdgt);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_Home:
                        Intent i = new Intent(ViewBudget.this, MainActivity.class);
                        startActivity(i);
                        break;
                    case R.id.nav_Expenses:
                        Intent intent = new Intent(ViewBudget.this, Expenses.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_Category:
                        Intent intent2 = new Intent(ViewBudget.this, Category.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_Aboutus:
                        Intent intent3 = new Intent(ViewBudget.this, AboutUs.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_Reminder:
                        Intent intent4 = new Intent(ViewBudget.this, Reminder.class);
                        startActivity(intent4);
                        break;
                    case R.id.nav_ViewBudget:
                        Toast.makeText(getApplicationContext(),"This is View Budget-Section", Toast.LENGTH_LONG).show();
                        break;
                }
                return true;
            }
        });
    }

    private DataPoint[] getData() {
    DataPoint[] cursor = dbTools.getdata();
        return cursor;
    }

    public void getsalary(){//get
        Cursor data = dbTools.getMoneyData();
        if(data.getCount() == 0){
            Toast.makeText(getApplicationContext(),"Declare your Salary!!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent (ViewBudget.this, SetSalary.class);
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
        AlertDialog.Builder bldr = new AlertDialog.Builder(ViewBudget.this);
        bldr.setMessage("Do you want to Exit ?").setNegativeButton("No", dialogresult).setPositiveButton("Yes", dialogresult).show();
        //moveTaskToBack(true);
        //ViewBudget.this.finish();
    }
}
