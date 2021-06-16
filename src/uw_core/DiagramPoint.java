package uw_core;

public class DiagramPoint {
    
    private double x;
    private double y;
    
    public DiagramPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public DiagramPoint() {
        this.x = 0.0;
        this.y = 0.0;
    }
    
    public double getX() {
        return x;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public double getY() {
        return y;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    
    public boolean equals(Object o){
        if(o  instanceof DiagramPoint){
            return ((((DiagramPoint)o).x == this.x) && (((DiagramPoint)o).y == this.y));
        }else{
            return super.equals(o);
        }
    }
    
}
