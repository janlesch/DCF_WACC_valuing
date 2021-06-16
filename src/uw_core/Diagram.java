package uw_core;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;

public abstract class Diagram {
    
    protected ArrayList<DiagramPoint> data;
    protected ResultSpectrum origin;
    
    public Diagram(Collection<DiagramPoint> data, ResultSpectrum origin){
        this.data = new ArrayList<DiagramPoint>(data);
        this.origin = origin;
    };
    
    public void draw(Graphics graphic, int x1, int y1, int x2, int y2, double zx1, double zx2, double zy1, double zy2){
        
    };
    
    public void drawAll(Graphics graphic, int x1, int y1, int x2, int y2){
        draw(graphic,x1,y1,x2,y2,0.0,1.0,0.0,1.0);
    };
    
    public String toString(){
        return origin.toString();
    }
    
    
}
