package com.example.vincent.budgetplannerfinal;

/**
 * Created by Vincent on 8/26/2017.
 */

public class Expinfo {

    private int exptotal;
    private String expname, expdate, expcost , expcategory , expid;

    //public int getExpid(){return expid;}
    //public void setExpid(String expid){this.expid = expid;}

    public int getExptotal() {
        return exptotal;
    }

    public void setExptotal(int exptotal) {
        this.exptotal = exptotal;
    }
    public String getExpid() {
        return expid;
    }

    public void setExpid(String expid) {
        this.expid = expid;
    }

    public String getExpname(){return expname;}
    public void setexpname(String expname){this.expname = expname;}

    public String getExpdate(){return  expdate;}
    public void setExpdate(String expdate){this.expdate = expdate;}

    public String getExpcost(){return expcost;}
    public void setExpcost(String expcost){this.expcost = expcost;}

    public String getExpcategory(){return expcategory;}
    public void setExpcategory(String expcategory){this.expcategory = expcategory;}

}
