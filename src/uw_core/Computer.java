package uw_core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class Computer {
    
    private double iek_s;
    private double ifk_s;
    
    private double precision;
    
    public Computer(double precision) {
        super();
        this.precision = precision;
    }
    
    public Computer() {
        precision = 0.0000000001;
    }
    
    private double iWaccUW1(Enterprise u, double gk){
        double ek = gk-u.getFk();
        return iek_s*(ek/gk)+ifk_s*(u.getFk()/gk);
    }
    
    private Result computeFCFsUW1(Enterprise u, ArrayList<Double> fcf, double r){
        Result res = new Result(0.0,0.0,0.0,0.0,null,0.0);
        double gk = u.getFk()/u.getInitH();
        double iwacc_new = iWaccUW1(u,gk);
        for(double iwacc_old = iwacc_new -1;(Math.abs(iwacc_old-iwacc_new) > precision);){
            iwacc_old = iwacc_new;
            gk = 0.0;
            for(ListIterator<Double> i = fcf.listIterator();i.hasNext();){
                double t = i.nextIndex()+1;
                double fcf_t = i.next().doubleValue();
                gk+=(fcf_t/Math.pow((1+iwacc_new),t));
            }
            
            if(u.getResidual() == Enterprise.c_Residual){
                //Gesamtkapital+= Fortführungswert/(iwacc^(n))
                gk+=r/Math.pow(iwacc_new,u.getN());
            }else{ //u.getResidual() == Enterprise.c_ewigerFCF
                //Gesamtkapital+= (Fortführungswert/(iwacc-g))/(iwacc^(n))
                gk+=(r/(iwacc_new-u.getG()))/Math.pow(iwacc_new,u.getN());
            }
            iwacc_new = iWaccUW1(u,gk);
        }
        res.setIWaccA(iwacc_new);
        res.setIWaccB(iwacc_new);
        res.setUwA(gk-u.getFk());
        res.setUwB(gk-u.getFk());
        return res;
    }
    
    private Result computePeriodenIntervallKetteUW1(Enterprise u, PeriodenIntervallKette pik){
        Result res = new Result(0.0,0.0,0.0,0.0,pik,0.0);
        boolean equals = true;
        
        ArrayList<Double> fcfA = new ArrayList<Double>(pik.size());
        ArrayList<Double> fcfB = new ArrayList<Double>(pik.size());
        double rA = pik.getR().getA();
        double rB = pik.getR().getB();
        
        for(Iterator<PeriodenIntervall> i = pik.iterator(); i.hasNext();){
            PeriodenIntervall pi = i.next();
            double a =pi.getA();
            double b =pi.getB();
            fcfA.add(new Double(a));
            fcfB.add(new Double(b));
            if(a!=b){
                equals = false;
            }
        }
        if(rA != rB){
            equals = false;
        }
        if(equals){
            Result resA = computeFCFsUW1(u,fcfA,rA);
            res.setIWaccA(resA.getIWaccA());
            res.setIWaccB(resA.getIWaccB());
            res.setUwA(resA.getUwA());
            res.setUwB(resA.getUwB());
        }else{
            Result resA = computeFCFsUW1(u,fcfA,rA);
            Result resB = computeFCFsUW1(u,fcfB,rB);
            
            res.setIWaccA(resA.getIWaccA());
            res.setIWaccB(resB.getIWaccB());
            res.setUwA(resA.getUwA());
            res.setUwB(resB.getUwB());
            res.setP(pik.getP());
        }
        return res;
    }
    
    public ResultSpectrum computeUW1(Enterprise u) throws Exception{
        ResultSpectrum res = new ResultSpectrum();
        
        switch(u.getBesteuerungsForm()) {
            case Enterprise.c_natPerson_kapGesellschaft:{
                iek_s = u.getI()*(1-u.getSe())+((1-0.5*u.getSe())*u.getRm()+0.5*u.getSe()*u.getI())*u.getBeta();
                ifk_s = u.getIfk()*(1-u.getSk())*(1-0.5*u.getSe());
                break;
            }
            case Enterprise.c_kapGesellschaft_kapGesellschaft:{
                double skg = 1-(1-u.getSg())*(1-u.getSk()); //Steuersatz für Kapitalgesellschaft
                iek_s = u.getI()*(1-skg)+((1-0.05*skg)*u.getRm()+0.95*skg*u.getI())*u.getBeta();
                ifk_s = u.getIfk()*(1-u.getSk())*(1-0.05*skg);
                break;
            }
            case Enterprise.c_natPerson_persGesellschaft:{
                iek_s = u.getI()+ (u.getRm()-u.getI()) * u.getBeta();
                ifk_s = u.getIfk()*( (1-u.getSg()) * (1-u.getSe()) + (u.getSg()*0.45) );
                break;
            }
            case Enterprise.c_taxCAPM:{
                iek_s = u.getI()+(u.getRm()-u.getI())*u.getBeta();
                ifk_s = u.getIfk()*(1-u.getSk());
                break;
            }
            default:{
                throw new Exception("Nicht vorhandener Eigner ausgewählt.");
            }
        }
        
        ArrayList<PeriodenIntervallKette> piks = Combiner.kombiniere(u.getPerioden(), u.getResidualWertIntervalle());
        
        for(Iterator<PeriodenIntervallKette> i = piks.iterator(); i.hasNext();){
            PeriodenIntervallKette pik = i.next();
            res.addResult(computePeriodenIntervallKetteUW1(u,pik));
        }
        
        return res;
    }
    
}
