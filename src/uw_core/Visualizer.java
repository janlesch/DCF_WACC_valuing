package uw_core;

import java.util.ArrayList;
import java.util.ListIterator;

public class Visualizer {
    
    public Visualizer() {
        
    }
    
    public LineDiagram visualizeResultSpectrumUw(ResultSpectrum rs){
        double minUw;
        double maxUw;
        double length;
        ArrayList<DiagramPoint> scaled;
        if(rs.size() > 0){
            minUw = rs.get(0).getUwA();
            maxUw = rs.get(rs.size()-1).getUwB();
            length = maxUw-minUw;
            scaled = new ArrayList<DiagramPoint>(rs.size());
            
            if(length == 0){
                scaled.add(new DiagramPoint(0.0,rs.get(0).getPUnit()));
                scaled.add(new DiagramPoint(1.0,rs.get(0).getPUnit()));
                return new LineDiagram(scaled, rs);
            }else{
                DiagramPoint p0 = new DiagramPoint(0.0,0.0);
                DiagramPoint p1 = new DiagramPoint();
                DiagramPoint p2 = new DiagramPoint();
                p1.setX((rs.get(0).getUwA()-minUw)/length);
                p1.setY(rs.get(0).getPUnit());
                p2.setX((rs.get(0).getUwB()-minUw)/length);
                p2.setY(rs.get(0).getPUnit());
                
                scaled.add(p0);
                scaled.add(p1);
                scaled.add(p2);
                
                DiagramPoint lastP = p2;
                
                
                for(ListIterator<Result> i = rs.listIterator(1); i.hasNext();){
                    Result cur = i.next();
                    
                    p1 = new DiagramPoint();
                    p2 = new DiagramPoint();
                    p1.setX((cur.getUwA()-minUw)/length);
                    p1.setY(cur.getPUnit());
                    p2.setX((cur.getUwB()-minUw)/length);
                    p2.setY(cur.getPUnit());
                    
                    if(lastP.getX() != p1.getX()){
                        DiagramPoint o1 = new DiagramPoint(lastP.getX(),0.0);
                        DiagramPoint o2 = new DiagramPoint(p1.getX(),0.0);
                        scaled.add(o1);
                        scaled.add(o2);
                        scaled.add(p1);
                        scaled.add(p2);
                    }else{
                        scaled.add(p1);
                        scaled.add(p2);
                    }
                    lastP = p2;
                }
                DiagramPoint pn = new DiagramPoint(1.0,0.0);
                scaled.add(pn);
                return new LineDiagram(scaled, rs);
            }
        }else{
            return new LineDiagram(null, null);
        }
    }
    
}
