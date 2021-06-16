package uw_core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.ListIterator;
import javax.imageio.ImageIO;

public class LineDiagram extends Diagram {
    
    private Graphics graphic;
    private Color borderColor = Color.LIGHT_GRAY;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int border = 40;
    private int lengthX;
    private int lengthY;
    private double zx1;
    private double zx2;
    private double zy1;
    private double zy2;
    private double zLengthX;
    private double zLengthY;
    
    
    public LineDiagram(Collection<DiagramPoint> data, ResultSpectrum origin){
        super(data, origin);
    }
    
    private Point transform(DiagramPoint p){
        Point res = new Point();
        res.x = x1+(int)Math.round(((p.getX()-zx1)/zLengthX)*lengthX);
        res.y = y2-(int)Math.round(((p.getY()-zy1)/zLengthY)*lengthY);
        return res;
    }
    
    public Result getHittenResult(int x, int y){
        double zx;
        if (x < x1) {
            x = x1;
        }else{
            if(x > x2){
                x = x2;
            }
        }
        x = x - x1;
        zx = zx1+(((double)x / lengthX)*zLengthX);
        return origin.getResultHit(zx);
        
    }
    
    
    
    private byte inHiddenArea(DiagramPoint p){
        //0: Sichtbar
        //1..4: "Quadrant" außerhalb
        if((p.getX() >= zx1)||(p.getX() <= zx2)&&(p.getY() >= zy1)||(p.getY() <= zy2)){
            return 0;
        }
        
        if((p.getY() < zy1)){
            return 1;
        }
        if((p.getX() > zx2)){
            return 2;
        }
        if((p.getY() > zy2)){
            return 3;
        }
//		if ((p.getX() < zx1)){
        return 4;
//		}
        
    }
    
    public void saveToFile(String filename, int sizeX, int sizeY, double zx1, double zx2, double zy1, double zy2) throws IOException{
        BufferedImage bi = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        
        Color oldColor = getBorderColor();
        setBorderColor(Color.WHITE);
        
        draw(g,0,0,sizeX,sizeY,zx1,zx2,zy1,zy2);
        
        setBorderColor(oldColor);
        
        File dest = new File(filename);
        ImageIO.write(bi,"png",dest);
    }
    
    
    private void drawLine(DiagramPoint a, DiagramPoint b){
        byte hidA = inHiddenArea(a);
        byte hidB = inHiddenArea(b);
        if((hidA == 0) || (hidB == 0) || (hidA != hidB)){
            Point ta=transform(a);
            Point tb=transform(b);
            graphic.drawLine(ta.x,ta.y,tb.x,tb.y);
        }
    }
    
    public double getMaxY(){
        double result = 0.0;
        for(ListIterator<DiagramPoint> i = data.listIterator(1); i.hasNext();){
            DiagramPoint cur = i.next();
            result = Math.max(cur.getY(),result);
        }
        return result;
    }
    
    public int getBorder(){
        return border;
    }
    
    public void setBorder(int border){
        this.border = border;
    }
    
    public Color getBorderColor(){
        return borderColor;
    }
    
    public void setBorderColor(Color borderColor){
        this.borderColor = borderColor;
    }
    
    private void drawXMarker(double x, String val){
        int dx = (int)Math.round(x1+x*(double)lengthX);
        graphic.drawLine(dx,y2-5,dx,y2+5);
        int sizeX = graphic.getFontMetrics().stringWidth(val);
        int sizeY = graphic.getFontMetrics().getHeight();
        graphic.drawString(val, dx-(sizeX/2), y2+5+sizeY);
    }
    
    private void drawYMarker(double y, String val){
        int dy = (int)Math.round(y2-y*lengthY);
        graphic.drawLine(x1-5, dy, x1+5, dy);
        int sizeX = graphic.getFontMetrics().stringWidth(val);
        int sizeY = graphic.getFontMetrics().getHeight();
        graphic.drawString(val, x1-sizeX-6, dy+(sizeY/3));
    }
    
    private void drawBorder(){
        graphic.setColor(borderColor);
        //links
        graphic.fillRect(x1-border,y1-border,border,lengthY+border);
        //oben
        graphic.fillRect(x1,y1-border,(x2+border)-(x1-border),border);
        //rechts
        graphic.fillRect(x2+1,y1-border,border,lengthY+border);
        //unten
        graphic.fillRect(x1-border,y2,(x2+border)-(x1-border),border);
        
        graphic.setColor(Color.BLACK);
        
        graphic.drawLine(x1, y1, x1, y2);
        graphic.drawLine(x1, y2, x2, y2);
        
    }
    
    private void drawMarker(){
        int steps = 10;
        double divStep = 1.0/(double)steps;
        double divX = zLengthX / (double) steps;
        double divY = zLengthY / (double) steps;
        NumberFormat nf2 = NumberFormat.getNumberInstance();
        NumberFormat nf4 = NumberFormat.getNumberInstance();
        
        
        nf2.setMinimumFractionDigits(2);
        nf2.setMaximumFractionDigits(2);
        nf4.setMinimumFractionDigits(3);
        nf4.setMaximumFractionDigits(3);
        
        
        for(int i = 0; i < steps; i++){
            
            drawXMarker(divStep*i,nf2.format(origin.getUWContinous(zx1+divX*i)));
            drawYMarker(divStep*i,nf4.format(zy1+divY*i*100.0));
        }
        
        drawXMarker(divStep*steps,nf2.format(origin.getUWContinous(zx1+divX*steps)));
        double lastval = zy1+divY*steps*100.0;
        if(lastval >= 100.0){
            drawYMarker(divStep*steps,"100,00");
        }else{
            drawYMarker(divStep*steps,nf4.format(lastval));
        }
        
        
    }
    
    public void draw(Graphics graphic, int x1, int y1, int x2, int y2, double zx1, double zx2, double zy1, double zy2){
        this.graphic = graphic;
        this.x1 = x1 + border;
        this.y1 = y1 + border;
        this.x2 = x2 - border;
        this.y2 = y2 - border;
        this.lengthX = this.x2 - this.x1;
        this.lengthY = this.y2 - this.y1;
        this.zx1 = zx1;
        this.zx2 = zx2;
        this.zy1 = zy1;
        this.zy2 = zy2;
        this.zLengthX = zx2-zx1;
        this.zLengthY = zy2-zy1;
        
        graphic.setFont(new Font( "SansSerif", Font.PLAIN, 10 ));
        
        graphic.setColor(Color.WHITE);
        graphic.fillRect(this.x1,this.y1,lengthX,lengthY);
        graphic.setColor(Color.BLACK);
        
        
        drawBorder();
        drawMarker();
        
        DiagramPoint prev=null;
        if(data.size() > 0){
            prev = data.get(0);
        }
        for(ListIterator<DiagramPoint> i = data.listIterator(1); i.hasNext();){
            DiagramPoint cur = i.next();
            drawLine(prev,cur);
            prev = cur;
        }
        
        drawBorder();
        drawMarker();
        
        
    }
    
}
