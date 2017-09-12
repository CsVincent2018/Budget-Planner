package com.example.vincent.budgetplannerfinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Category extends AppCompatActivity {
    DBTools dbTools = new DBTools(this);
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private Toolbar mToolbar;
    ListView lstcat;
    Button btnaddcat;
    ArrayAdapter lstadpt;
    String category;
    ArrayList lst;

    DialogInterface.OnClickListener dialogresult, dialogforbckpress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        btnaddcat = (Button)findViewById(R.id.btnaddcat);

        lstcat = (ListView)findViewById(R.id.lstcategory);
         lst = new ArrayList<String>();
        Cursor data = dbTools.getlistCat();

        lstcat.setOnItemLongClickListener(new ListViewItemLongClick());
        lstadpt = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,lst);
        getsalary();
        if(data.getCount() == 0){
            Toast.makeText(getApplicationContext(), "The Category was empty", Toast.LENGTH_SHORT).show();
        }else{
            while(data.moveToNext()){
                lst.add(data.getString(1));
                lstcat.setAdapter(lstadpt);
            }
        }

        dialogresult = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Deletedata();
                        Intent in = new Intent(Category.this, Category.class);
                        startActivity(in);
                     //   Toast.makeText(getApplicationContext(), "Successfully deleted", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };


        dialogforbckpress = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        moveTaskToBack(true);
                        Category.this.finish();
                }
            }
        };

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout3);
        mtoggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnaddcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder bldr = new AlertDialog.Builder(Category.this);
                View view = getLayoutInflater().inflate(R.layout.addingcategory, null);
                final EditText etaddcat = (EditText)view.findViewById(R.id.etaddcat);
                Button btnsavecat = (Button)view.findViewById(R.id.btnsavecat);
                Button btncancel = (Button)view.findViewById(R.id.btncancel);
                bldr.setView(view);
                final AlertDialog dialog = bldr.create();
                dialog.show();

                btnsavecat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newcat = etaddcat.getText().toString().toUpperCase();
                        if(etaddcat.length() !=0){
                         //   chkcatdata(newcat);
                            Addcat(newcat);
                            etaddcat.setText("");
                            Intent intent = new Intent(Category.this, Category.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(), "You must put Something", Toast.LENGTH_SHORT).show();
                        }
                    }

                   /* private void chkcatdata(String newcat) {
                        dbTools.Checkdatafrcat(newcat);

                    }*/

                    private void Addcat(String newcat) {
                        boolean inserData = dbTools.addcat(newcat);
                        if(inserData == true){
                            Toast.makeText(getApplicationContext(), "Data Save", Toast.LENGTH_SHORT).show();
                        } else{ Toast.makeText(getApplicationContext(), "Bug", Toast.LENGTH_SHORT).show();}
                    }
                });

                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        NavigationView mNavigationView = (NavigationView)findViewById(R.id.nav_menu3);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_Category:
                        Toast.makeText(getApplicationContext(),"This is Category Section", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_Home:
                        Intent intent = new Intent(Category.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_Expenses:
                        Intent intent2 = new Intent(Category.this, Expenses.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_Aboutus:
                        Intent intent3 = new Intent(Category.this, AboutUs.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_Reminder:
                        Intent intent4 = new Intent(Category.this,Reminder.class);
                        startActivity(intent4);
                        break;
                    //case R.id.nav_Exit:
                      //  Category.this.finish();
                    case R.id.nav_ViewBudget:
                        Intent intent5 = new Intent(Category.this, ViewBudget.class);
                        startActivity(intent5);
                        break;
                }
                return true;
            }
        });
    }

    public void getsalary(){//get
        Cursor data = dbTools.getMoneyData();
        if(data.getCount() == 0){
            Toast.makeText(getApplicationContext(),"Declare your Salary!!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent (Category.this, SetSalary.class);
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
        AlertDialog.Builder bldr = new AlertDialog.Builder(Category.this);
        bldr.setMessage("Do you want to Exit ?").setNegativeButton("No", dialogforbckpress).setPositiveButton("Yes", dialogforbckpress).show();
        //moveTaskToBack(true);
        //Category.this.finish();
    }

    private class ListViewItemLongClick implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            String row = lstadpt.getItem(position).toString();
            category = String.valueOf(row);
        //    Toast.makeText(getApplicationContext(), category , Toast.LENGTH_LONG).show();
            AlertDialog.Builder bldr = new AlertDialog.Builder(Category.this);
            bldr.setMessage("Do you want to delete this Category?").setNegativeButton("Cancel", dialogresult).setPositiveButton("Delete", dialogresult).show();
            return false;
        }
    }
    public void Deletedata(){
        Toast.makeText(getApplicationContext(), category , Toast.LENGTH_LONG).show();
        dbTools.deletecat(category);
        //Toast.makeText(getApplicationContext(),"Category Deleted", Toast.LENGTH_LONG).show();
    }
}
