package uw_core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;


public class Periode implements Serializable{
    
    /**
     *
     */
    private static final long serialVersionUID = 3764186519819106218L;
    private ArrayList<PeriodenIntervall> intervalle;
    
    public Periode(int anz_intervalle) {
        intervalle = new ArrayList<PeriodenIntervall>(anz_intervalle);
    }
    
    public void addPeriodenIntervall(PeriodenIntervall pi){
        intervalle.add(pi);
    }
    
    public void removePeriodenIntervall(PeriodenIntervall pi){
        intervalle.remove(pi);
    }
    
    public void addPeriodenIntervall(double a, double b, double p, String bemerkung) throws Exception{
        PeriodenIntervall pi = new PeriodenIntervall(a,b,p,bemerkung);
        intervalle.add(pi);
    }
    
    public int size(){
        return intervalle.size();
    }
    
    public void normalizeP(){
        double sum = 0;
        for(Iterator<PeriodenIntervall> i=intervalle.iterator(); i.hasNext();){
            sum += i.next().getP();
        }
        for(Iterator<PeriodenIntervall> i=intervalle.iterator(); i.hasNext();){
            PeriodenIntervall pi = i.next();
            try{
                pi.setP(pi.getP()/sum);
            }catch(Exception e){
                
            }
        }
    }
    
    public boolean hasNormalizedP(){
        double sum = 0;
        for(Iterator<PeriodenIntervall> i=intervalle.iterator(); i.hasNext();){
            sum += i.next().getP();
        }
        return (sum == 1);
    }
    
    public Iterator<PeriodenIntervall> iterator(){
        return intervalle.iterator();
    }
    
    public PeriodenIntervall get(int pos){
        return intervalle.get(pos);
    }
    
    public String toString(){
        StringBuffer res = new StringBuffer(intervalle.size()*30);
        for(Iterator<PeriodenIntervall> i=intervalle.iterator(); i.hasNext();){
            res.append(" "+i.next().toString()+"\n");
        }
        return res.toString();
    }
}
