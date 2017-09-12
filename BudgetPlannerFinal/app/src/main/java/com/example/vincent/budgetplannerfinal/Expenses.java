package com.example.vincent.budgetplannerfinal;

import android.content.ContentResolver;
import android.content.ContentValues;
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
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.vincent.budgetplannerfinal.R.id.parent;
import static com.example.vincent.budgetplannerfinal.R.id.select_dialog_listview;
import static com.example.vincent.budgetplannerfinal.R.id.spnnrcat;
import static com.example.vincent.budgetplannerfinal.R.id.txtremainblnc;

public class Expenses extends AppCompatActivity {
    DBTools dbTools = new DBTools(this);
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private Toolbar mToolbar;
    DialogInterface.OnClickListener dialogresult;

    List<Expinfo> expinfolist;
    ListView listView;
    Cursor cursor;
    Button btnaddexp;
    TextView txtv;
    String slct , xname, nm;
    int bdgt , amnt, ttl;
    //ContentResolver resolver;
    CustomLstAdapterExp adapterExp;
    Expinfo expinfo;
    int frsttotal = 0;
    String expdate;
    int etxammount = 0 , totalexp = 0;
   // int rmainblnc , etxammount, currentblnc , totalexp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
//  resolver = this.getContentResolver();
        getsalary();
        txtv = (TextView)findViewById(R.id.txtmsg);
        btnaddexp = (Button)findViewById(R.id.btnaddexp);
     //  dbTools = new DBTools(getApplicationContext());
        listView = (ListView) findViewById(R.id.lstexp);
        expinfolist = dbTools.getexplist();
        adapterExp = new CustomLstAdapterExp(this, expinfolist);
       // listView.setAdapter(adapterExp);
       // listView.setOnItemClickListener(new lstonitmCLick());


        Cursor myexp = dbTools.getlistexp();
        if(myexp.getCount() == 0){
            Toast.makeText(getApplicationContext(), "The Exp was empty", Toast.LENGTH_SHORT).show();
        }else{
            while(myexp.moveToNext()){
                listView.setAdapter(adapterExp);
            }
        }

        dialogresult = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which){
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        moveTaskToBack(true);
                        Expenses.this.finish();
                }
            }
        };

        mToolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout2);
        mtoggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnaddexp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder bdlr = new AlertDialog.Builder(Expenses.this);
                View view = getLayoutInflater().inflate(R.layout.addingexp, null);
                TextView txttitle = (TextView)view.findViewById(R.id.txtexptitle);
                TextView txtexpdate = (TextView)view.findViewById(R.id.txtexpdate);
                final TextView txtaddblnc = (TextView)view.findViewById(R.id.txtaddblnc);
                final TextView Myrmainblnc = (TextView)view.findViewById(R.id.txtremainblnc);
                final TextView txtexttl = (TextView)view.findViewById(R.id.txtexptotal);
                Spinner spncatt = (Spinner)view.findViewById(R.id.spnnrcat);
                final EditText etexpname = (EditText)view.findViewById(R.id.etexpname);
                final EditText etexpamount = (EditText)view.findViewById(R.id.etexpamount);
                Button btnsave = (Button)view.findViewById(R.id.btnsaveexp);
                Button btncancel = (Button)view.findViewById(R.id.btncancelexp);
                bdlr.setView(view);


                long date = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy ");//h:mm a
                String dateString = sdf.format(date);
                txtexpdate.setText(dateString);

                Cursor balance  = dbTools.getBalance();
                if(balance.getCount() == 0){Toast.makeText(getApplicationContext(),"We have problem", Toast.LENGTH_LONG).show();}
                else{while(balance.moveToNext()){
                    int blnc = balance.getInt(0);
                    txtaddblnc.setText(String.valueOf(blnc));
                    }
                }

                List<String> datas  = dbTools.getAllCat();
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Expenses.this,android.R.layout.simple_spinner_item,datas);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spncatt.setAdapter(dataAdapter);
                final AlertDialog dialog = bdlr.create();
                dialog.show();

             spncatt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                  @Override
                  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                      String selctitm = parent.getItemAtPosition(position).toString();
                      Toast.makeText(parent.getContext(), "You Select: " + selctitm, Toast.LENGTH_LONG).show();
                      slct = selctitm;
                  }
                  @Override
                  public void onNothingSelected(AdapterView<?> parent) {

                  }
              });

                expdate = txtexpdate.getText().toString();
                //final String etxname = etexpname.getText().toString().toUpperCase();
                //xname = etxname.toString();\

                btnsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getApplicationContext(), "fix date bug", Toast.LENGTH_LONG).show();
                        LoadData();
                    }

                    private void LoadData() {
                        String curblnc = txtaddblnc.getText().toString();
                        String foramnt = etexpamount.getText().toString();
                        final int currentblnc = Integer.parseInt(curblnc);
                        int fexm = 0;
                        if(!foramnt.equals(""))
                        fexm = Integer.parseInt(foramnt);

                        //etexpamount.setText(String.valueOf(fexm));
                        //String stamnt = etexpamount.getText().toString();
                        //etxammount = Integer.parseInt(stamnt);


                        int rmainblnc = currentblnc - fexm;
                        Myrmainblnc.setText(String.valueOf(rmainblnc));
                       // String rminblnc = Myrmainblnc.getText().toString();
                       // rmainblnc = Integer.parseInt(rminblnc);
                        dbTools.updateexpttl(Integer.parseInt(Myrmainblnc.getText().toString()));

                        Cursor thattotal = dbTools.getextotal();
                        if(thattotal.getCount() == 0){
                            totalexp = 0;
                        }else{while(thattotal.moveToNext()){
                            totalexp = thattotal.getInt(0);
                        }}

                       // frsttotal = totalexp;
                        frsttotal = totalexp + fexm;
                        txtexttl.setText(String.valueOf(frsttotal));
                        //String exptttl = txtexttl.getText().toString();
                        frsttotal = Integer.parseInt(txtexttl.getText().toString());

                        //int exm = Integer.parseInt(etexpamount.getText().toString());
                        if(etexpname.length()!=0 && etexpamount.length()!=0 && currentblnc >= fexm && fexm != 0 && currentblnc !=0){
                            dbTools.insertexp(etexpname.getText().toString(),fexm, slct , expdate, frsttotal);

                            Toast.makeText(getApplicationContext(), "Yehey :))", Toast.LENGTH_LONG).show();

                            Intent in = new Intent(Expenses.this, Expenses.class);
                            startActivity(in);

                        }else{Toast.makeText(getApplicationContext(),"Your Balance is " + currentblnc + " " , Toast.LENGTH_LONG).show();}
                    }
                });

                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

              /*  txtexpdate.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {}

                    @Override
                    public void afterTextChanged(Editable s) {
                        //String mygettervariable = txtexpdate.getText().toString();
                       // psdt = mygettervariable;
                    }
                });*/
            }

        });

        NavigationView mNavigationView = (NavigationView)findViewById(R.id.nav_menu2);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_Expenses:
                        Toast.makeText(getApplication(),"This is Expenses Section", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.nav_Home:
                        Intent intent = new Intent(Expenses.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_Category:
                        Intent intent1 = new Intent(Expenses.this, Category.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_Aboutus:
                        Intent intent3 = new Intent(Expenses.this, Category.class);
                        startActivity(intent3);
                        break;
                    case R.id.nav_Reminder:
                        Intent intent4 = new Intent(Expenses.this, Reminder.class);
                        startActivity(intent4);
                        break;
                    case R.id.nav_ViewBudget:
                        Intent intent5 = new Intent(Expenses.this, ViewBudget.class);
                        startActivity(intent5);
                        break;
                    //case R.id.nav_Exit:
                      //  Expenses.this.finish();
                }
                return true;
            }
        });
    }

    /*public void getlistexp(){
        //dbTools
    }*/
    public void getsalary(){//get
        Cursor data = dbTools.getMoneyData();
        if(data.getCount() == 0){
            Toast.makeText(getApplicationContext(),"Declare your Salary!!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent (Expenses.this, SetSalary.class);
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

        AlertDialog.Builder bldr = new AlertDialog.Builder(Expenses.this);
        bldr.setMessage("Do you want to Exit ?").setNegativeButton("No", dialogresult).setPositiveButton(Html.fromHtml("<font color ='#FF7F27'>Yes</font>"), dialogresult).show();
        //moveTaskToBack(true);
        //Expenses.this.finish();
    }

    /* class lstonitmCLick implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(),".....zz.....", Toast.LENGTH_SHORT).show();
        }
    }*/
}
