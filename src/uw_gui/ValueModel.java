package uw_gui;

import java.text.NumberFormat;
import javax.swing.table.AbstractTableModel;
import uw_core.Enterprise;

public class ValueModel extends AbstractTableModel{
    
    private Enterprise enterprise;
    
    public ValueModel(Enterprise enterprise) {
        this.enterprise=enterprise;
    }
    
    public int getRowCount() {
        return 11;
    }
    
    public int getColumnCount() {
        return 2;
    }
    
    public String getColumnName(int col){
        switch(col){
            case 0: return "Bezeichner";
            case 1: return "Wert";
            default: return "";
        }
    }
    
    public boolean isCellEditable(int row, int col){
        if(col == 1 && row != 9){
            return true;
        }else{
            return false;
        }
    }
    
    public void valuesChanged(){
        fireTableDataChanged();
    }
    
    public void setValueAt(Object aValue, int row, int col) {
        if (col == 1){
            try {
                double value = Double.parseDouble(((String) aValue).replace(',','.'));
                switch(row){
                    case(0): enterprise.setI(value); break;
                    case(1): enterprise.setRm(value); break;
                    case(2): enterprise.setBeta(value); break;
                    case(3): enterprise.setSg(value); break;
                    case(4): enterprise.setSe(value); break;
                    case(5): enterprise.setSk(value); break;
                    case(6): enterprise.setFk(value); break;
                    case(7): enterprise.setIfk(value); break;
                    case(8): enterprise.setG(value); break;
                    case(9): break;
                    case(10): enterprise.setInitH(value); break;
                    default: break;
                }
            }catch (Exception ex) {}
        }
        
    }
    
    
    public Object getValueAt(int row, int col) {
        if (col == 0){
            switch(row){
                case(0): return "i (Zinssatz für risikoloses Fremdkapital)";
                case(1): return "Rm (Renditeerwartung des Gesamtmarktes)";
                case(2): return "beta (Risikoschwankungsfaktor)";
                case(3): return "sg (effektive Gewerbesteuer)";
                case(4): return "se (effektive Einkommenssteuer)";
                case(5): return "sk (effektive Körperschaftssteuer)";
                case(6): return "FK (Marktwert des Fremdkapitals)";
                case(7): return "iFK (Renditeerwartung der Fremdkapitalgeber)";
                case(8): return "g (Wachstumsfaktor des Residual-FCFs)";
                case(9): return "n (Planungshorizont)";
                case(10): return "initH (Initiales FK/GK)";
                default: return "";
            }
        }else{
            NumberFormat nf2 = NumberFormat.getNumberInstance();
            NumberFormat nf4 = NumberFormat.getNumberInstance();
            
            nf2.setMinimumFractionDigits(2);
            nf2.setMaximumFractionDigits(2);
            nf2.setGroupingUsed(false);
            nf4.setMinimumFractionDigits(2);
            nf4.setMaximumFractionDigits(8);
            nf4.setGroupingUsed(false);
            
            switch(row){
                case(0): return nf4.format(enterprise.getI());
                case(1): return nf4.format(enterprise.getRm());
                case(2): return nf4.format(enterprise.getBeta());
                case(3): return nf4.format(enterprise.getSg());
                case(4): return nf4.format(enterprise.getSe());
                case(5): return nf4.format(enterprise.getSk());
                case(6): return nf2.format(enterprise.getFk());
                case(7): return nf4.format(enterprise.getIfk());
                case(8): return nf4.format(enterprise.getG());
                case(9): return enterprise.getN();
                case(10): return nf4.format(enterprise.getInitH());
                default: return "";
            }
        }
        
    }
    
}
