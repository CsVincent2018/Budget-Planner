package com.example.vincent.budgetplannerfinal;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 8/26/2017.
 */

public class CustomLstAdapterExp extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Expinfo> expinfolist;
    Context context;

    public CustomLstAdapterExp(Activity activity, List<Expinfo> expinfolist) {
        this.activity = activity;
        this.expinfolist = expinfolist;
    }


    @Override
    public int getCount() {
        return expinfolist.size();
    }

    @Override
    public Object getItem(int position) {
        return expinfolist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(view ==null)
            view = inflater.inflate(R.layout.activity_custom_lst_expenses, null);

        TextView exptitle = (TextView)view.findViewById(R.id.txtexptitle);
        TextView expdate = (TextView)view.findViewById(R.id.txtexpdt);
        TextView expcost  = (TextView)view.findViewById(R.id.txtexpcost);
        TextView expcat = (TextView)view.findViewById(R.id.txtexpcat);
        TextView expid = (TextView)view.findViewById(R.id.txtexpid);
        TextView expttl = (TextView)view.findViewById(R.id.txtclstblnc);


        Expinfo exp = expinfolist.get(position);
        expid.setText(String.valueOf(exp.getExpid()));
        exptitle.setText(exp.getExpname());
        expcost.setText(exp.getExpcost());
        expcat.setText(exp.getExpcategory());
        expdate.setText(exp.getExpdate());
        expttl.setText(String.valueOf(exp.getExptotal()));
        return view;
    }
}
