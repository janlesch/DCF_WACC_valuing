package uw_gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ZoomChooser extends javax.swing.JPanel{
    
    private double z1=0.0;
    private double z2=1.0;
    private static final double minZoom = 0.0001;
    private int radius = 3;
    private boolean pressedZ1 = false;
    private boolean pressedZ2 = false;
    private boolean pressedMove = false;
    private int pressedX = 0;
    private int pressedY = 0;
    
    private ArrayList<ChangeListener> changeListener = new ArrayList<ChangeListener>(1);
    
    private byte orientation;
    
    public static final byte HORIZONTAL = 0;
    public static final byte VERTICAL = 1;
    
    
    public ZoomChooser() {
        super.setBackground(Color.LIGHT_GRAY);
        addMouseMotionListener( new MouseMotionListener() {
            public void mouseDragged(MouseEvent mouseEvent) {
            }
            public void mouseMoved(MouseEvent mouseEvent) {
                mouseMove(mouseEvent.getX(), mouseEvent.getY());
            }
        });
        
        addMouseListener( new MouseListener() {
            public void mouseClicked(MouseEvent mouseEvent) {
                if(mouseEvent.getButton() == mouseEvent.BUTTON1){
                    if(mouseEvent.getClickCount() > 1){
                        zoomOut();
                    }else{
                        mouseClick(mouseEvent.getX(), mouseEvent.getY());
                    }
                }
                if(mouseEvent.isPopupTrigger() || mouseEvent.getButton() == mouseEvent.BUTTON3){
                    manualInput();
                }
                
            }
            public void mouseEntered(MouseEvent mouseEvent) {
            }
            public void mouseExited(MouseEvent mouseEvent) {
            }
            public void mousePressed(MouseEvent mouseEvent) {
                mousePress(mouseEvent.getX(), mouseEvent.getY());
            }
            public void mouseReleased(MouseEvent mouseEvent) {
                mouseRelease(mouseEvent.getX(), mouseEvent.getY());
            }
        }
        );
    }
    
    private void callListeners(){
        for(Iterator<ChangeListener> i = changeListener.iterator(); i.hasNext();){
            ChangeListener chl = i.next();
            if (chl != null){
                chl.stateChanged(new ChangeEvent(this));
            }
        }
    }
    
    private void zoomOut(){
        z1 = 0.0;
        z2 = 1.0;
        callListeners();
        repaint();
    }
    
    private void manualInput(){
        NumberFormat nf2 = NumberFormat.getNumberInstance();
        
        
        nf2.setMinimumFractionDigits(4);
        nf2.setMaximumFractionDigits(4);
        
        if(orientation == VERTICAL){
            double zoom1 = z1;
            double zoom2 = z2;
            
            try{
                String zoomS = nf2.format(zoom2)+" - "+nf2.format(zoom1);
                zoomS = (String)JOptionPane.showInputDialog(
                        this,
                        "Zoomfaktoren im Format \"oben - unten\"",
                        "Zoomfaktoren bestimmen",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        zoomS
                        );
                String[] split = zoomS.split(" *- *");
                if(split.length >= 2){
                    split[0]=split[0].replace(',','.');
                    split[1]=split[1].replace(',','.');
                    
                    zoom2 = Double.parseDouble(split[0]);
                    zoom1 = Double.parseDouble(split[1]);
                    
                }
                
                if (zoom1 < 0.0){ zoom1 = 0.0; }
                if (zoom1 > 1.0){ zoom1 = 1.0; }
                if (zoom1 > zoom2) {double swap = zoom1;zoom1 = zoom2;zoom2 = swap;}
                if (zoom2-zoom1 < minZoom){zoom1 = zoom2 - minZoom;}
                if (zoom2 < 0.0){zoom2 = 0.0;}
                if (zoom2 > 1.0){zoom2 = 1.0;}
                if (zoom1 > zoom2) {double swap = zoom1;zoom1 = zoom2;zoom2 = swap;}
                if (zoom2-zoom1 < minZoom){zoom2 = zoom1 + minZoom;}
                
                z1 = zoom1;
                z2 = zoom2;
                
                repaint();
                callListeners();
                
                                
            }catch(Exception e){}
        }else{
            double zoom1 = z1;
            double zoom2 = z2;
            
            try{
                String zoomS = nf2.format(zoom1)+" - "+nf2.format(zoom2);
                zoomS = (String)JOptionPane.showInputDialog(
                        this,
                        "Zoomfaktoren im Format \"links - rechts\"",
                        "Zoomfaktoren bestimmen",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        zoomS
                        );
                String[] split = zoomS.split(" *- *");
                if(split.length >= 2){
                    split[0]=split[0].replace(',','.');
                    split[1]=split[1].replace(',','.');
                    
                    zoom1 = Double.parseDouble(split[0]);
                    zoom2 = Double.parseDouble(split[1]);
                    
                }
                
                if (zoom1 < 0.0){ zoom1 = 0.0; }
                if (zoom1 > 1.0){ zoom1 = 1.0; }
                if (zoom1 > zoom2) {double swap = zoom1;zoom1 = zoom2;zoom2 = swap;}
                if (zoom2-zoom1 < minZoom){zoom1 = zoom2 - minZoom;}
                if (zoom2 < 0.0){zoom2 = 0.0;}
                if (zoom2 > 1.0){zoom2 = 1.0;}
                if (zoom1 > zoom2) {double swap = zoom1;zoom1 = zoom2;zoom2 = swap;}
                if (zoom2-zoom1 < minZoom){zoom2 = zoom1 + minZoom;}
                
                z1 = zoom1;
                z2 = zoom2;
                
                repaint();
                callListeners();
                
                                
            }catch(Exception e){}
        }
    }
        
    private void mousePress(int x, int y){
        if(orientation == VERTICAL){
            if((Math.abs(y-getYz1()) <= radius)){
                pressedZ1 = true;
                pressedX = x;
                pressedY = y;
            }else{
                if((Math.abs(y-getYz2()) <= radius )){
                    pressedZ2 = true;
                    pressedX = x;
                    pressedY = y;
                }else{
                    if((y > getYz2()) && (y < getYz1())){
                        pressedMove = true;
                        pressedX = x;
                        pressedY = y;
                    }
                }
            }
        }else{
            if((Math.abs(x-getXz1()) <= radius)){
                pressedZ1 = true;
                pressedX = x;
                pressedY = y;
            }else{
                if((Math.abs(x-getXz2()) <= radius )){
                    pressedZ2 = true;
                    pressedX = x;
                    pressedY = y;
                }else{
                    if((x > getXz1()) && (x < getXz2())){
                        pressedMove = true;
                        pressedX = x;
                        pressedY = y;
                    }
                }
            }
        }
        
    }
    
    private void mouseRelease(int x, int y){
        if(pressedZ1){
            if(orientation == VERTICAL){
                z1 = 1-((double)y / (double)getHeight());
                if(z1 < 0.0){
                    z1 = 0.0;
                }
                if(z1 > 1.0){
                    z1 = 1.0;
                }
                if (z1 > z2){
                    double swap = z1;
                    z1 = z2;
                    z2 = swap;
                }
                if(z2-z1 < minZoom){
                    z1 = z2 - minZoom;
                }
            }else{
                z1 = (double) x / (double)getWidth();
                if(z1 < 0.0){
                    z1 = 0.0;
                }
                if(z1 > 1.0){
                    z1 = 1.0;
                }
                if (z1 > z2){
                    double swap = z1;
                    z1 = z2;
                    z2 = swap;
                }
                if(z2-z1 < minZoom){
                    z1 = z2 - minZoom;
                }
            }
            pressedZ1 = false;
            callListeners();
            repaint();
        }else{
            if(pressedZ2){
                if(orientation == VERTICAL){
                    z2 = 1-((double)y / (double)getHeight());
                    if(z2 < 0.0){
                        z2 = 0.0;
                    }
                    if(z2 > 1.0){
                        z2 = 1.0;
                    }
                    if (z1 > z2){
                        double swap = z1;
                        z1 = z2;
                        z2 = swap;
                    }
                    if(z2-z1 < minZoom){
                        z2 = z1 + minZoom;
                    }
                }else{
                    z2 = (double) x / (double)getWidth();
                    if(z2 < 0.0){
                        z2 = 0.0;
                    }
                    if(z2 > 1.0){
                        z2 = 1.0;
                    }
                    if (z1 > z2){
                        double swap = z1;
                        z1 = z2;
                        z2 = swap;
                    }
                    if(z2-z1 < minZoom){
                        z2 = z1 + minZoom;
                    }
                }
                pressedZ2 = false;
                callListeners();
                repaint(0);
            }else{
                if(pressedMove){
                    if(orientation == VERTICAL){
                        int diffY = pressedY - y;
                        double diffZY = (double) diffY / (double) getHeight();
                        double newZ1 = z1+diffZY;
                        double newZ2 = z2+diffZY;
                        if(newZ1 < 0.0){
                            //zu weit unten
                            newZ1 = 0.0;
                            newZ2 = z2-z1;
                        }
                        if(newZ2 > 1.0){
                            //zu weit oben
                            newZ2 = 1.0;
                            newZ1 = 1.0 - (z2-z1);
                        }
                        z1=newZ1;
                        z2= newZ2;
                    }else{
                        int diffX = x - pressedX;
                        double diffZX = (double) diffX / (double) getWidth();
                        double newZ1 = z1+diffZX;
                        double newZ2 = z2+diffZX;
                        if(newZ1 < 0.0){
                            //zu weit links
                            newZ1 = 0.0;
                            newZ2 = z2-z1;
                        }
                        if(newZ2 > 1.0){
                            //zu weit rechts
                            newZ2 = 1.0;
                            newZ1 = 1.0 - (z2-z1);
                        }
                        z1=newZ1;
                        z2= newZ2;
                    }
                }
                pressedMove = false;
                callListeners();
                repaint(0);
                
            }
        }
    }
    
    private void mouseMove(int x, int y){
        if(!pressedZ1 && !pressedZ2){
            if(orientation == VERTICAL){
                if((Math.abs(y-getYz1()) <= radius)){
                    setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
                }else{
                    if((Math.abs(y-getYz2()) <= radius )){
                        setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
                    }else{
                        if((y > getYz2()) && (y < getYz1())){
                            setCursor(new Cursor(Cursor.MOVE_CURSOR));
                        }else{
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
                        
                    }
                }
            }else{
                if((Math.abs(x-getXz1()) <= radius)){
                    setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
                }else{
                    if((Math.abs(x-getXz2()) <= radius )){
                        setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
                    }else{
                        if((x > getXz1()) && (x < getXz2())){
                            setCursor(new Cursor(Cursor.MOVE_CURSOR));
                        }else{
                            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        }
                    }
                }
            }
        }
    }
    
    private void mouseClick(int x, int y){
        
    }
    
    public void addChangeListener(ChangeListener list){
        changeListener.add(list);
    }
    
    private int getYz1(){
        return (int) Math.round(getHeight()*(1-z1));
    }
    
    private int getYz2(){
        return (int) Math.round(getHeight()*(1-z2));
    }
    
    private int getXz1(){
        return (int) Math.round(getWidth()*z1);
    }
    
    private int getXz2(){
        return (int) Math.round(getWidth()*z2);
    }
    
    private void drawRect(int x1, int y1, int x2, int y2, Graphics g){
        int height = y2-y1;
        int width = x2-x1;
        if(width < 1){
            if(x1 == 0){
                x2 = 1;
            }
            if(x2 == getWidth()){
                x1 = getWidth()-1;
            }
            width = 1;
        }
        if(height < 1){
            if(y1 == getHeight()){
                y1 = getHeight()-1;
            }
            if(y2 == 0){
                y2 = 1;
            }
            height = 1;
        }
        g.fillRect(x1,y1,width,height);
    }
    
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        graphics.setColor(Color.DARK_GRAY);
        if(orientation == VERTICAL){
            drawRect(0,getYz2(),getWidth(),getYz1(),graphics);
        }else{
            drawRect(getXz1(),0,getXz2(),getHeight(),graphics);
        }
        
    }
    
    public double getZ1() {
        return z1;
    }
    
    public void setZ1(double z1) {
        this.z1 = z1;
    }
    
    public double getZ2() {
        return z2;
    }
    
    public void setZ2(double z2) {
        this.z2 = z2;
    }
    
    public byte getOrientation() {
        return orientation;
    }
    
    public void setOrientation(byte orientation) {
        this.orientation = orientation;
    }
    
    
}
