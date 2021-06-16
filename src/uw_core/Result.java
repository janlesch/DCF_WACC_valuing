package uw_core;

import java.text.NumberFormat;

public class Result {
    
    private double uwA;
    private double uwB;
    private double iWaccA;
    private double iWaccB;
    private double p;
    private PeriodenIntervallKette pik;
    
    public Result(double uwA, double uwB, double iwaccA, double iwaccB, PeriodenIntervallKette pik, double p) {
        super();
        this.uwA = uwA;
        this.uwB = uwB;
        this.iWaccA = iwaccA;
        this.iWaccB = iwaccB;
        this.pik = pik;
        this.p = p;
    }
    
    public String toString(){
        NumberFormat nf1 = NumberFormat.getNumberInstance();
        nf1.setMinimumFractionDigits(10);
        nf1.setMaximumFractionDigits(10);
        NumberFormat nf2 = NumberFormat.getNumberInstance();
        nf2.setMinimumFractionDigits(2);
        nf2.setMaximumFractionDigits(2);
        
        return  "+-------------------------------------+\n"+
                " uwA    [GE]= "+nf2.format(uwA)+"\n"+
                " uwB    [GE]= "+nf2.format(uwB)+"\n"+
                " iWaccA [ %]= "+nf1.format(iWaccA*100.0)+"\n"+
                " iWaccB [ %]= "+nf1.format(iWaccB*100.0)+"\n"+
                " p      [ %]= "+nf1.format(p*100.0)+"\n"+
                " p/GE   [ %]= "+nf1.format(getPUnit()*100.0)+"\n"+
                "+-------------------------------------+\n";
        
    }
    
    public double getIWaccA() {
        return iWaccA;
    }
    
    public void setIWaccA(double wacc) {
        iWaccA = wacc;
    }
    
    public double getUwB() {
        return uwB;
    }
    
    public void setUwB(double maxUW) {
        this.uwB = maxUW;
    }
    
    public double getUwA() {
        return uwA;
    }
    
    public void setUwA(double minUW) {
        this.uwA = minUW;
    }
    
    public PeriodenIntervallKette getPik() {
        return pik;
    }
    
    public void setPik(PeriodenIntervallKette pik) {
        this.pik = pik;
    }
    
    
    public double getIWaccB() {
        return iWaccB;
    }
    
    
    public void setIWaccB(double waccB) {
        iWaccB = waccB;
    }
    
    public double getPIntervall() {
        return p;
    }
    
    public double getPUnit(){
        double length = (uwB-uwA);
        if(length <= 0){
            length = 1;
        }
        return p/length;
    }
    
    public void setP(double p) {
        this.p = p;
    }
    
    public Result splitByUwB(double newUwB){
        //a(=this) wird in A(=this) und B(=return) aufgeteilt
        if (newUwB < uwA){
            return null;
        }
        double ratioA = (newUwB-uwA) / (uwB-uwA); //(A/a)
        double ratioB = 1-ratioA;				  //(B/a)
        Result B = new Result(newUwB,uwB,ratioA*(iWaccB-iWaccA)+iWaccA,iWaccB,pik,p*ratioB);
        this.iWaccB=B.getIWaccA();
        this.uwB=newUwB;
        this.p=p*ratioA;
        return B;
    }
    
    public Result splitByUwA(double newUwA){
        //a(=this) wird in A(=return) und B(=this) aufgeteilt
        if (newUwA > uwB){
            return null;
        }
        double ratioA = (newUwA-uwA) / (uwB-uwA); //(A/a)
        double ratioB = 1-ratioA;				  //(B/a)
        Result A = new Result(uwA,newUwA,iWaccA,ratioA*(iWaccB-iWaccA)+iWaccA,pik,p*ratioA);
        this.iWaccA=A.getIWaccB();
        this.uwA=newUwA;
        this.p=p*ratioB;
        return A;
    }
    
    public void union(Result newResult){
        iWaccA = (iWaccA+newResult.iWaccA)/2;
        iWaccB = (iWaccB+newResult.iWaccB)/2;
        p = p + newResult.getPIntervall();
    }
    
}
