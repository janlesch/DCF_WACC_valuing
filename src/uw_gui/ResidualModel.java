package uw_gui;

import java.text.NumberFormat;
import javax.swing.table.AbstractTableModel;
import uw_core.Enterprise;
import uw_core.PeriodenIntervall;

public class ResidualModel extends AbstractTableModel{
    
    private Enterprise e;
    
    public ResidualModel(Enterprise e) {
        this.e=e;
    }
    
    public int getRowCount() {
        return e.getResidualWertIntervalle().size();
    }
    
    public int getColumnCount() {
        return 4;
    }
    
    
    public boolean isCellEditable(int row, int col){
        return true;
    }
    
    public String getColumnName(int col){
        switch(col){
            case 0: return "von [GE]";
            case 1: return "bis [GE]";
            case 2: return "W-keit [0..1]";
            case 3: return "Kommentar";
            default: return "";
        }
    }
    
    public Object getValueAt(int row, int col) {
        if(row < e.getResidualWertIntervalle().size()){
            PeriodenIntervall i = e.getResidualWertIntervalle().get(row);
            NumberFormat nf2 = NumberFormat.getNumberInstance();
            NumberFormat nf4 = NumberFormat.getNumberInstance();
            
            nf2.setMinimumFractionDigits(2);
            nf2.setMaximumFractionDigits(2);
            nf2.setGroupingUsed(false);
            nf4.setMinimumFractionDigits(2);
            nf4.setMaximumFractionDigits(8);
            nf4.setGroupingUsed(false);
            
            switch(col){
                case 0: return nf2.format(i.getA());
                case 1: return nf2.format(i.getB());
                case 2: return nf4.format(i.getP());
                case 3: return i.getBemerkung();
                default: return "";
            }
        }else{
            return "";
        }
    }
    
    public void addIntervall(){
        try {
            e.getResidualWertIntervalle().addPeriodenIntervall(0.0,0.0,0.5,"");
        } catch (Exception ex) {
            
        }
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
        if(row < e.getResidualWertIntervalle().size()){
            PeriodenIntervall i = e.getResidualWertIntervalle().get(row);
            try {
                switch(col){
                    
                    case 0: i.setA(getDouble(aValue)); fireTableDataChanged();break;
                    case 1: i.setB(getDouble(aValue)); fireTableDataChanged();break;
                    case 2: i.setP(getDouble(aValue)); break;
                    case 3: i.setBemerkung((String)aValue); break;
                    default: ;
                    
                }
                
            } catch (Exception ex) {}
        }else{}
        
    }
}


