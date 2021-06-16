package uw_gui;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import javax.swing.table.AbstractTableModel;
import uw_core.Enterprise;
import uw_core.Periode;
import uw_core.PeriodenIntervall;

public class IntervallModel extends AbstractTableModel{
    
    private class Intervall{
        //In PeriodenIntervall ist keine PeriodenNr angegeben, daher muﬂ sie
        //mitgespeichert werden.
        public PeriodenIntervall periodenIntervall;
        public int periode;
        
        public Intervall(PeriodenIntervall periodenIntervall, int periode){
            this.periodenIntervall = periodenIntervall;
            this.periode = periode;
        }
        
    }
    
    private Enterprise e;
    private ArrayList<Intervall> intervalle;
    
    public IntervallModel(Enterprise e) {
        this.e=e;
        intervalle = new ArrayList<Intervall>();
        actIntervalle();
    }
    
    public int getRowCount() {
        return intervalle.size();
    }
    
    public int getColumnCount() {
        return 5;
    }
    
    private void actIntervalle(){
        intervalle.clear();
        for(ListIterator<Periode> i = e.getPerioden().listIterator(); i.hasNext();){
            Periode p = i.next();
            if (p!= null){
                for(Iterator<PeriodenIntervall> ii = p.iterator(); ii.hasNext();){
                    PeriodenIntervall pi = ii.next();
                    intervalle.add(new Intervall(pi,i.previousIndex()));
                }
            }else{
            }
        }
    }
    
    public boolean isCellEditable(int row, int col){
        return true;
    }
    
    public String getColumnName(int col){
        switch(col){
            case 0: return "Periode [1..n]";
            case 1: return "von [GE]";
            case 2: return "bis [GE]";
            case 3: return "W-keit [0..1]";
            case 4: return "Kommentar";
            default: return "";
        }
    }
    
    public Object getValueAt(int row, int col) {
        if(row < intervalle.size()){
            Intervall i = intervalle.get(row);
            NumberFormat nf2 = NumberFormat.getNumberInstance();
            NumberFormat nf4 = NumberFormat.getNumberInstance();
            
            nf2.setMinimumFractionDigits(2);
            nf2.setMaximumFractionDigits(2);
            nf2.setGroupingUsed(false);
            nf4.setMinimumFractionDigits(2);
            nf4.setMaximumFractionDigits(8);
            nf4.setGroupingUsed(false);

            switch(col){
                case 0: return i.periode;
                case 1: return nf2.format(i.periodenIntervall.getA());
                case 2: return nf2.format(i.periodenIntervall.getB());
                case 3: return nf4.format(i.periodenIntervall.getP());
                case 4: return i.periodenIntervall.getBemerkung();
                default: return "";
            }
        }else{
            return "";
        }
    }    
    
    private void moveIntervall(PeriodenIntervall pi, int toPeriode){
        try {
            e.movePeriodenIntervall(pi, toPeriode);
            actIntervalle();
            fireTableDataChanged();
        } catch (Exception ex) {}
    }
    
    public void addIntervall(){
        try {
            
            e.addPeriodenIntervall(0.0,0.0,0.5,"",e.getN());
        } catch (Exception ex) {
        }
        actIntervalle();
        fireTableDataChanged();
    }
        
    private double getDouble(Object aValue)throws Exception{
            int intval = 0;
            double doubleval = 0.0;
            boolean intErrorFlag = false;
            boolean doubleErrorFlag = false;
            try {
                intval = Integer.parseInt((String) aValue);
            } catch (NumberFormatException ex) {
                intErrorFlag = true;
            }
            try {
                doubleval = Double.parseDouble(((String) aValue).replace(',','.'));
            } catch (NumberFormatException ex) {
                    doubleErrorFlag = true;
                }
            
            
            if(!doubleErrorFlag){
                return doubleval;
            }else{
                if(!intErrorFlag){
                    return intval;
                }else{
                    throw new Exception();
                }
            }
    
    }
    
    public void setValueAt(Object aValue, int row, int col) {
       if(row < intervalle.size()){
            Intervall i = intervalle.get(row);

            try {
                
                switch(col){
                    
                    case 0: moveIntervall(i.periodenIntervall, Integer.parseInt((String) aValue));   break;
                    case 1: i.periodenIntervall.setA(getDouble(aValue)); fireTableDataChanged();break;
                    case 2: i.periodenIntervall.setB(getDouble(aValue)); fireTableDataChanged();break;
                    case 3: i.periodenIntervall.setP(getDouble(aValue)); break;
                    case 4: i.periodenIntervall.setBemerkung((String)aValue); break;
                    default: ;
                }
                
            } catch (Exception ex) {
            
            }
        }else{
         }
        
    }
}


