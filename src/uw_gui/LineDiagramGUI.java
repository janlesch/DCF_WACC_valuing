package uw_gui;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import javax.swing.JPopupMenu;
import uw_core.LineDiagram;
import uw_core.Result;

public class LineDiagramGUI extends javax.swing.JPanel{
    
    private LineDiagram lineDiagram;
    
    private JPopupMenu pm_Values;
    
    private double zx1 = 0.0;
    private double zx2 = 1.0;
    private double zy1 = 0.0;
    private double zy2 = 1.0;
    
    
    public LineDiagramGUI() {
        this.setLineDiagram(null);
        
        pm_Values = new JPopupMenu();
        
        addMouseListener(new MouseAdapter() {
            public void mouseReleased( MouseEvent me ) {
                if ( me.getButton() == MouseEvent.BUTTON1 )
                    showValues( me.getX(), me.getY() );
            }
            
        });
    }
    
    private void showValues(int x, int y){
        if(lineDiagram != null){
            pm_Values = new JPopupMenu();
            Result hit = lineDiagram.getHittenResult(x,y);
            if(hit != null){
                NumberFormat nf2 = NumberFormat.getNumberInstance();
                NumberFormat nf4 = NumberFormat.getNumberInstance();
                
                nf2.setMinimumFractionDigits(2);
                nf2.setMaximumFractionDigits(2);
                nf4.setMinimumFractionDigits(4);
                nf4.setMaximumFractionDigits(4);
                
                pm_Values.setFont(new Font( "SansSerif", Font.PLAIN, 20 ));
                
                pm_Values.add(
                        nf2.format(hit.getUwA())+
                        " - "+
                        nf2.format(hit.getUwB())+
                        "  (UW)");
                pm_Values.add(
                        nf4.format(hit.getIWaccA()*100.0)
                        +" - "+
                        nf4.format(hit.getIWaccB()*100.0)+
                        "  (i wacc)");
                pm_Values.add(
                        nf4.format(hit.getPIntervall()*100.0)+
                        "%  (p)");
                pm_Values.add(
                        nf4.format(hit.getPUnit()*100.0)+
                        "%  (p/GE)");
                
                pm_Values.show(this,x,y);
            }else{
                pm_Values.add("Kein Intervall");
                pm_Values.show(this,x,y);
            }
        }
    }
    
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if((getLineDiagram() != null)){
            getLineDiagram().draw(
                    graphics,
                    0,
                    0,
                    getWidth(),
                    getHeight(), getZx1(), getZx2(), getZy1(), getZy2());
        }
        
    }
    
    public void zoomExtents(){
        if(lineDiagram != null) {
            zx1=0.0;
            zx2=1.0;
            zy1=0.0;
            zy2=lineDiagram.getMaxY();
        }
    }
    
    public LineDiagram getLineDiagram() {
        return lineDiagram;
    }
    
    public void setLineDiagram(LineDiagram lineDiagram) {
        this.lineDiagram = lineDiagram;
    }
    
    public double getZx1() {
        return zx1;
    }
    
    public void setZx1(double zx1) {
        this.zx1 = zx1;
    }
    
    public double getZx2() {
        return zx2;
    }
    
    public void setZx2(double zx2) {
        this.zx2 = zx2;
    }
    
    public double getZy1() {
        return zy1;
    }
    
    public void setZy1(double zy1) {
        this.zy1 = zy1;
    }
    
    public double getZy2() {
        return zy2;
    }
    
    public void setZy2(double zy2) {
        this.zy2 = zy2;
    }
    
}
