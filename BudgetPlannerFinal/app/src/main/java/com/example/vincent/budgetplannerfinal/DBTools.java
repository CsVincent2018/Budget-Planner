package com.example.vincent.budgetplannerfinal;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 1/31/2015.
 */

public class DBTools extends SQLiteOpenHelper {

    SQLiteDatabase db;
    private static final int Db_Version = 1;
    private static final String Db_Name = "BudgetPlanner";

   /* private static final String Tbl_MoneyRate = "Money";
    private static final String Col_Salary = "salary";
    private static final String Col_addedtives = "addetives";
    private static final String Tblcreate_money = "create table Money(salary integer primary key not null , " +
            " addetives integer null )";*/

    private static final String Tbl_Rate = "TableMoney";
    private static final String Col_ID = "salary_id";
    private static final String Col_YourBalance = "balance";
    private static final String Col_Start = "start";
    private static final String Col_End = "end";

    private static final String Tbl_create_MoneyRate = "create table TableMoney(salary_id integer primary key autoincrement , " +
            "balance integer not null  , start text not null , end text not null )";

    //--------------------------------------------------------------------------------------------------------------------
    private static final String Tbl_Category = "TableCategory";
    private static final String Col_CatID = "category_id";
    private static final String Col_Category = "category";
    private static final String Tbl_create_Category = "create table TableCategory(category_id integer primary key autoincrement , " +
            "category text not null)";

    //--------------------------------------------------------------------------------------------------------------------
    private static final String Tbl_Expenses = "TableExpenses";
    private static final String Col_ExpId ="expenses_id";
    private static final String Col_expname = "exp_name";
    private static final String Col_Money ="exp_cost";
    private static final String Col_thatCategory = "exp_category";
    private static final String Col_date = "exp_date";
    private static final String Col_exptotal = "exp_total";

    private static final String Tbl_create_Expenses = "create table TableExpenses(expenses_id integer primary key autoincrement ," +
            "exp_name text not null , exp_cost integer not null , exp_category text not null , exp_date text not null , exp_total integer not null )";

    //--------------------------------------------------------------------------------------------------------------------

    private static final String Tbl_StartEndBudget = "Table_S_E_Budget";
    private static final String Col_SE_ID = "se_id";
    private static final String Col_remainBudget = "remainbudget";
    private static final String Col_StartBugdet = "startbudget";
    private static final String Col_EndBudget = "endbudget";

    private static final String Tbl_create_StartEnd_Budget = "create table Table_S_E_Budget(se_id integer primary key autoincrement , " +
            "remainbudget integer not null , startbudget text not null , endbudget text not null )";

    //---------------------------------------------------------------------------------------------------------------------

    public DBTools(Context context){
        super(context, Db_Name, null ,Db_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      //  db.execSQL(Tblcreate_money);
        db.execSQL(Tbl_create_MoneyRate);
        db.execSQL(Tbl_create_Category);
        db.execSQL(Tbl_create_Expenses);
        db.execSQL(Tbl_create_StartEnd_Budget);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // String query = "DROP TABLE IF EXISTS " + Tbl_MoneyRate;
        //db.execSQL(query);
        String query2 = "DROP TABLE IF EXISTS " + Tbl_Rate;
        db.execSQL(query2);
        String query3 = "DROP TABLE IF EXISTS " + Tbl_Category;
        db.execSQL(query3);
        String query4 = "DROP TABLE IF EXISTS " + Tbl_Expenses;
        db.execSQL(query4);
        String query5 = "DROP TABLE IF EXISTS " + Tbl_StartEndBudget;
        db.execSQL(query5);
        this.onCreate(db);
    }

    public Cursor getMoneyData(){
        db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select * from " + Tbl_Rate, null);
        return data;
    }
    public Cursor getBalance(){
        db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select balance from " + Tbl_Rate, null);
        return data;
    }

    public Cursor getextotal(){
        db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select exp_total from " + Tbl_Expenses, null);
        return data;
    }

    public boolean insertSalary(int balance , String start , String end){
        db = this.getWritableDatabase();
        ContentValues cntvalue = new ContentValues();
        cntvalue.put(Col_YourBalance, balance);
        cntvalue.put(Col_Start, start);
        cntvalue.put(Col_End , end);
        long result  = db.insert(Tbl_Rate , null , cntvalue);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean addcat(String item){
        db = this.getWritableDatabase();
        ContentValues vls = new ContentValues();
        vls.put(Col_Category, item);
        long result = db.insert(Tbl_Category, null, vls);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean deletecat(String item){
        String query = "DELETE FROM " + Tbl_Category + " WHERE " + Col_Category + " = '" + item + "'";
        try{db.execSQL(query); return true;}
        catch (Exception e){return false;}
    }
    public boolean updateexpttl(int ttl){
        String updt = " UPDATE " + Tbl_Rate + " SET " + Col_YourBalance + " = '" + ttl + "'  WHERE salary_id  = '1'";
        try{db.execSQL(updt); return true;}
        catch (Exception e){return false;}
    }
    public Cursor getlistCat(){
        db  = this.getWritableDatabase();
        Cursor data = db.rawQuery("select * from " + Tbl_Category , null);
        return data;
    }

    public Cursor getlistexp(){
        db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select * from " + Tbl_Expenses , null);
        return data;
    }

   /*public boolean Checkdatafrcat(String catitm){
       db = this.getWritableDatabase();
       String query = "select * from " + Tbl_Category + " where "  + Col_Category + " = " + catitm + "'";
       Cursor cursor  = db.rawQuery(query, null);

       boolean Checkdatafrcat = false;
       if(cursor.moveToFirst()){
           Checkdatafrcat = true;
           int count = 0;
           while(cursor.moveToNext()){
               count++;
           }
       }
       cursor.close();
       return Checkdatafrcat;
   }*/

    public ArrayList<Expinfo> getexplist(){
        ArrayList<Expinfo> list = new ArrayList<>();

        Cursor cursor = db.rawQuery("select * from " + Tbl_Expenses , null);
        if(cursor.moveToFirst()){
            do{
                Expinfo exp =  new Expinfo();
                exp.setExpid(cursor.getInt(0) + "");
                exp.setexpname(cursor.getString(1));
                exp.setExpcost(cursor.getString(2));
                exp.setExpcategory(cursor.getString(3));
                exp.setExpdate(cursor.getString(4));
                exp.setExptotal(cursor.getInt(5));
                list.add(exp);
            }
            while(cursor.moveToNext());
        }
        return list;
    }
    public DataPoint[] getdata(){
        String[] columns = {"exp_category", "exp_cost"};

        Cursor cursor = db.query(Tbl_Expenses, columns, null, null,null,null,null);
        DataPoint[] dp = new DataPoint[cursor.getCount()];

        for(int i = 0; i <cursor.getCount(); i++){
            cursor.moveToNext();
            dp[i] = new DataPoint(cursor.getInt(0),cursor.getInt(1));
        }
        return dp;
    }
    public List<String> getAllCat(){
        List<String>categories = new ArrayList<String>();
        String myquery = "select * from " + Tbl_Category ;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(myquery , null);
        if(cursor.moveToFirst()){
            do{
                categories.add(cursor.getString(1));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return categories;
    }



    public long insertexp(String expname , int expammount , String spncat ,String date, int exptotal){
        ContentValues values =  new ContentValues();
        values.put(Col_expname, expname);
        values.put(Col_Money ,expammount);
        values.put(Col_thatCategory , spncat);
        values.put(Col_date , date);
        values.put(Col_exptotal, exptotal);
        long inserted = db.insert(Tbl_Expenses, null , values);
        return inserted;
    }


}