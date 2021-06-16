package uw_core;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class Combiner {
    private Combiner() {
    }
    
    private static void anhaengen(ArrayList<PeriodenIntervallKette> result, PeriodenIntervallKette kette, ArrayList<Periode> perioden, int actPeriode){
        if(actPeriode >= perioden.size()){
            result.add(kette);
            return;
        }else{
            Periode p;
            try{
                p = perioden.get(actPeriode);
            }catch (Exception e){
                return;
            }
            if(p == null){
                return;
            }
            for (Iterator<PeriodenIntervall> i = p.iterator(); i.hasNext(); ) {
                PeriodenIntervall pi = i.next();
                PeriodenIntervallKette k = new PeriodenIntervallKette(kette);
                if(pi != null){
                    k.addPeriodenIntervall(pi);
                }
                anhaengen(result,k,perioden, actPeriode+1);
            }
        }
    }
    
    public static ArrayList<PeriodenIntervallKette> kombiniere(ArrayList<Periode> perioden, Periode r){
        Periode p;
        ArrayList<PeriodenIntervallKette> res = new ArrayList<PeriodenIntervallKette>();
        try{
            p = perioden.get(1);
        }catch (Exception e){
            return res; // keine Periode vorhanden
        }
        if(p == null){
            return res; // keine Periode vorhanden
        }
        for(Iterator<PeriodenIntervall> ii = p.iterator(); ii.hasNext(); ){
            //erste Periode durchlaufen
            PeriodenIntervall pi = ii.next();
            if(pi == null){
                continue;
            }
            PeriodenIntervallKette pik = new PeriodenIntervallKette();
            pik.addPeriodenIntervall(pi);
            anhaengen(res,pik,perioden,2);
        }
        
        for(ListIterator<PeriodenIntervallKette> i = res.listIterator(); i.hasNext(); ){
            //alle Ketten durchlaufen und verschiedene Fortführungswerte dranhängen
            PeriodenIntervallKette pik = i.next();
            i.remove();
            if(pik == null){
                continue;
            }
            for(Iterator<PeriodenIntervall> ii = r.iterator();ii.hasNext();){
                //die einzelnen Fortführungswert-Intervalle anhängen.
                PeriodenIntervallKette pikNew = new PeriodenIntervallKette(pik);
                pikNew.setR(ii.next());
                i.add(pikNew);
            }
            
        }
        return res;
    }
}
