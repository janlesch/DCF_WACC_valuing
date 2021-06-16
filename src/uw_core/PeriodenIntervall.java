package uw_core;

import java.io.Serializable;
import java.text.NumberFormat;


public class PeriodenIntervall implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 7970972555376221046L;
    private double a, b; //Minimaler Wert a, Maximaler Wert b
    private String bemerkung; //Bemerkung
    private double p; //Wahrscheinlickeit b für Annehmen eines Wertes in [a..b]
    
    public PeriodenIntervall(double a, double b, double p, String bemerkung) throws Exception {
        if(a > b){
            this.a = b;
            this.b = a;
        }else{
            this.a = a;
            this.b = b;
        }
        if((p > 1)||(p < 0)){
            throw new Exception("p muß zwischen 0 und 1 liegen!");
        }else{
            this.p = p;
        }
        this.bemerkung=bemerkung;
    }
    
    public double getA(){
        return a;
    }
    public double getB(){
        return b;
    }
    public double getP(){
        return p;
    }
    public String toString(){
        NumberFormat nf1 = NumberFormat.getNumberInstance();
        nf1.setMinimumFractionDigits(4);
        nf1.setMaximumFractionDigits(4);
        NumberFormat nf2 = NumberFormat.getNumberInstance();
        nf2.setMinimumFractionDigits(2);
        nf2.setMaximumFractionDigits(2);
        
        return "[ "+nf2.format(a)+" ; "+nf2.format(b)+" ] ( "+nf1.format(p)+" ) '"+bemerkung+"'";
    }
    
    public String getBemerkung() {
        return bemerkung;
    }
    
    public void setBemerkung(String bemerkung) {
        this.bemerkung = bemerkung;
    }
    
    private void checkOrder(){
        if(a > b){
            double swap = a;
            a  = b;
            b = swap;
        }
    }
    
    public void setA(double a) {
        this.a = a;
        checkOrder();
    }
    
    public void setB(double b) {
        this.b = b;
        checkOrder();
        
    }
    
    public void setP(double p) throws Exception{
        if((p > 1)||(p < 0)){
            throw new Exception("p muß zwischen 0 und 1 liegen!");
        }else{
            this.p = p;
        }
    }
    
}
