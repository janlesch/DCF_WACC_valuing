package uw_core;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class PeriodenIntervallKette {
    
    private ArrayList<PeriodenIntervall> kette; //Kette von FCFs
    private PeriodenIntervall r; 	//Fortführungswert
    
    public PeriodenIntervallKette() {
        this.kette = new ArrayList<PeriodenIntervall>();
    }
    public PeriodenIntervallKette(PeriodenIntervallKette old) {
        this.kette = new ArrayList<PeriodenIntervall>(old.kette);
        this.r = old.r;
    }
    
    public PeriodenIntervall getR() {
        return r;
    }
    public void setR(PeriodenIntervall r) {
        this.r = r;
    }
    public void addPeriodenIntervall(PeriodenIntervall pi){
        kette.add(pi);
    }
    
    public int size(){
        return kette.size();
    }
    
    public Iterator<PeriodenIntervall> iterator(){
        return kette.iterator();
    }
    
    public void print(){
        NumberFormat nf1 = NumberFormat.getNumberInstance();
        nf1.setMinimumFractionDigits(4);
        nf1.setMaximumFractionDigits(4);
        System.out.println("+-------------------------------------+");
        
        for(Iterator<PeriodenIntervall> i=kette.iterator(); i.hasNext();){
            System.out.println(i.next().toString());
        }
        System.out.println(" -- -- -- -- -- --- --- -- -- -- -- -- ");
        System.out.println(r.toString());
        System.out.println("( "+nf1.format(getP())+" )");
        System.out.println("+-------------------------------------+");
    }
    
    public double getP() {
        double p = 1.0;
        for(Iterator<PeriodenIntervall> i=kette.iterator(); i.hasNext();){
            PeriodenIntervall pi = i.next();
            if(pi != null){
                p*=pi.getP();
            }
        }
        if(r != null){
            p*=r.getP();
        }
        return p;
    }
    
}
