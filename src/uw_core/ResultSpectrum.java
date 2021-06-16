package uw_core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class ResultSpectrum {
    
    //zusammenhängendes Spektrum
    private LinkedList<Result> spec;
    //ursprüngliche Ergebnisse
    private ArrayList<Result> res;
    
    public ResultSpectrum() {
        spec = new LinkedList<Result>();
        res = new ArrayList<Result>();
    }
    
    
    public Result getResultHit(double x){
        if(res.size() > 0){
            double minUW = spec.get(0).getUwA();
            double maxUW = spec.get(spec.size()-1).getUwB();
            double length = maxUW - minUW;
            x = (x * length)+minUW;
            for(ListIterator<Result> i = spec.listIterator(); i.hasNext(); ){
                Result cur = i.next();
                if((cur.getUwA() <= x) && (x <= cur.getUwB())){
                    return cur;
                }
                
            }
            return null;
        }else{
            return null;
        }
    }
    
    public double getUWContinous(double x){
        if(res.size() > 0){
            double minUW = spec.get(0).getUwA();
            double maxUW = spec.get(spec.size()-1).getUwB();
            double length = maxUW - minUW;
            x = (x * length)+minUW;
            return x;
        }else{
            return x;
        }
    }
    
    public void addResult(Result result){
        res.add(result);
        insertResult(result);
    }
    
    public ListIterator<Result> listIterator(){
        return spec.listIterator();
    }
    
    public ListIterator<Result> listIterator(int start){
        return spec.listIterator(start);
    }
    
    public Result get(int index){
        return spec.get(index);
    }
    
    public int size(){
        return spec.size();
    }
    
    private void insertResult(Result ins){
        if(spec.isEmpty()){
            spec.add(ins);
            return;
        }
        if(spec.get(0).getUwA() >= ins.getUwB()){
            spec.add(0, ins);
            return;
        }
        if(spec.get(spec.size()-1).getUwB() <= ins.getUwA()){
            spec.add(ins);
            return;
        }
        boolean inserted = false;
        for(ListIterator<Result> i = spec.listIterator(); i.hasNext(); ){
            Result cur = i.next();
            if(ins.getUwB() < cur.getUwA()){
                //1
                i.previous();
                i.add(ins);
                i.next();
                inserted = true;
                break;
            }
            if(ins.getUwB() == cur.getUwA()){
                //2
                i.previous();
                i.add(ins);
                i.next();
                inserted = true;
                break;
            }
            if(ins.getUwB() <  cur.getUwB() && ins.getUwA() <  cur.getUwA() && ins.getUwB() > cur.getUwA()){
                //3
                i.previous();
                Result x = ins.splitByUwA(cur.getUwA());
                ins.union(cur.splitByUwA(ins.getUwB()));
                i.add(x);
                i.add(ins);
                i.next();
                inserted = true;
                break;
            }
            if(ins.getUwB() <  cur.getUwB() && ins.getUwA() == cur.getUwA()){
                //4
                i.previous();
                ins.union(cur.splitByUwA(ins.getUwB()));
                i.add(ins);
                i.next();
                inserted = true;
                break;
            }
            if(ins.getUwB() == cur.getUwB() && ins.getUwA() == cur.getUwA()){
                //5
                cur.union(ins);
                inserted = true;
                break;
            }
            if(ins.getUwB() <  cur.getUwB() && ins.getUwA() >  cur.getUwA()){
                //6
                i.previous();
                Result x = cur.splitByUwA(ins.getUwB());
                ins.union(x.splitByUwB(ins.getUwA()));
                i.add(x);
                i.add(ins);
                i.next();
                inserted = true;
                break;
            }
            if(ins.getUwB() == cur.getUwB() && ins.getUwA() >  cur.getUwA()){
                //7
                i.previous();
                Result x = cur.splitByUwA(ins.getUwA());
                cur.union(ins);
                i.add(x);
                i.next();
                inserted = true;
                break;
            }
            if(ins.getUwB() > cur.getUwB() && ins.getUwA() ==  cur.getUwA()){
                //8
                cur.union(ins.splitByUwA(cur.getUwB()));
                continue;
            }
            if(ins.getUwB() >  cur.getUwB() && ins.getUwA() <  cur.getUwA()){
                //9
                i.previous();
                Result x = ins.splitByUwA(cur.getUwA());
                cur.union(ins.splitByUwA(cur.getUwB()));
                i.add(x);
                i.next();
                continue;
            }
            if(ins.getUwB() >  cur.getUwB() && ins.getUwA() >  cur.getUwA() && ins.getUwA() < cur.getUwB()){
                //10
                i.previous();
                Result x = cur.splitByUwA(ins.getUwA());
                cur.union(ins.splitByUwA(cur.getUwB()));
                i.add(x);
                i.next();
                continue;
            }
            if(ins.getUwA() == cur.getUwB()){
                //11
                continue;
            }
            if(ins.getUwA() > cur.getUwB()){
                //12
                continue;
            }
        }
        if(!inserted){
            spec.add(ins);
        }
    }
    
    public String toString(){
        StringBuffer sb = new StringBuffer(spec.size()*30);
        for(Iterator<Result> i=spec.iterator(); i.hasNext();){
            sb.append(i.next().toString()+"\n");
        }
        return sb.toString();
        
    }
    
    
}
