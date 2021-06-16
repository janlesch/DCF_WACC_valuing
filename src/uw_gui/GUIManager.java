package uw_gui;

//import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileOutputStream;
//import java.io.StringWriter;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import uw_core.Computer;
import uw_core.Enterprise;
import uw_core.ResultSpectrum;
import uw_core.Visualizer;

public class GUIManager {
    
    private F_Chooser chooser;
    private ArrayList<F_Analyse> analysisList = new ArrayList(1);
    private Computer computer;
    private Visualizer visualizer;
    
    public GUIManager() {
        computer = new Computer();
        visualizer = new Visualizer();
    }
    
    public void start(){
        chooser = new F_Chooser(this);
        chooser.setVisible(true);
    }
    
    public void newAnalysis(){
        Enterprise newEnt = new Enterprise(true);
        F_Analyse newF_A = new F_Analyse(this, newEnt);
        actualizeAnalysis(newF_A);
        analysisList.add(newF_A);
        newF_A.setVisible(true);
    }
    
    public void closeApplication(){
        int answer = JOptionPane.showOptionDialog(null,"Beenden?","Beenden?",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null,null,null);
        if (answer == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }
    
    public void openSavedAnalysis(){
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter( new FileFilter() {
            @Override public boolean accept( File f ) {
                return f.isDirectory() ||
                        f.getName().toLowerCase().endsWith( ".wacc" );
            }
            @Override public String getDescription() {
                return "wacc-Analyse-Unternehmungen (*.wacc)";
            }
        } );
        int state = fc.showOpenDialog( null );
        if ( state == JFileChooser.APPROVE_OPTION ) {
            File file = fc.getSelectedFile();
            newSavedAnalysis( file.getPath() );
        }
    }
    
    public void saveAnalysis(F_Analyse f_a){
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter( new FileFilter() {
            @Override public boolean accept( File f ) {
                return f.isDirectory() ||
                        f.getName().toLowerCase().endsWith( ".wacc" );
            }
            @Override public String getDescription() {
                return "wacc-Analyse-Unternehmungen (*.wacc)";
            }
        } );
        int state = fc.showSaveDialog( null );
        if ( state == JFileChooser.APPROVE_OPTION ) {
            File file = fc.getSelectedFile();
            String filename = file.getPath();
            if(!filename.endsWith(".wacc")){
                filename+=".wacc";
            }
            if(!f_a.getEnterprise().writeEnterprise(filename)){
                JOptionPane.showOptionDialog(
                        null,
                        "Ein Fehler ist beim Speichern von '"+filename+"' aufgetreten.",
                        "Fehler",
                        JOptionPane.CLOSED_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        null,
                        null);
            }
        }
        
    }
    
    public void saveDiagramAsImage(F_Analyse f_a){
        if(f_a.getLineDiagram() == null){
            return;
        }
        int sizeX = 1200;
        int sizeY = 600;
        try{
            String sizeS = sizeX+", "+sizeY;
            sizeS = (String)JOptionPane.showInputDialog(
                    f_a,
                    "Größe des Ausgabe-Bildes in Pixel im Format \"Breite, Höhe\"",
                    "Größe bestimmen",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    sizeS
                    );
            String[] split = sizeS.split(" *, *");
            if(split.length >= 2){
                
                sizeX = Integer.parseInt(split[0]);
                sizeY = Integer.parseInt(split[1]);
            }
        }catch(Exception e){}
        
        
        
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter( new FileFilter() {
            @Override public boolean accept( File f ) {
                return f.isDirectory() ||
                        f.getName().toLowerCase().endsWith( ".png" );
            }
            @Override public String getDescription() {
                return "PNG-Bild (*.png)";
            }
        } );
        int state = fc.showSaveDialog( null );
        if ( state == JFileChooser.APPROVE_OPTION ) {
            File file = fc.getSelectedFile();
            String filename = file.getPath();
            if(!filename.endsWith(".png")){
                filename+=".png";
            }
            boolean error = true;
            try {
                f_a.getLineDiagram().saveToFile(
                        filename,
                        sizeX,
                        sizeY,
                        f_a.getZx1(),
                        f_a.getZx2(),
                        f_a.getZy1(),
                        f_a.getZy2()
                        );
                
                
                error = false;
            } catch (Exception e) {
                error = true;
            }
            if(error){
                JOptionPane.showOptionDialog(
                        null,
                        "Ein Fehler ist beim Speichern von \""+filename+"\" aufgetreten.",
                        "Fehler",
                        JOptionPane.CLOSED_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        null,
                        null);
            }
        }
        
        
    }
     
    
    
    public void saveDiagramAsText(F_Analyse f_a){
        if(f_a.getLineDiagram() == null){
            return;
        }
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter( new FileFilter() {
            @Override public boolean accept( File f ) {
                return f.isDirectory() ||
                        f.getName().toLowerCase().endsWith( ".txt" );
            }
            @Override public String getDescription() {
                return "Text-Datei (*.txt)";
            }
        } );
        int state = fc.showSaveDialog( null );
        if ( state == JFileChooser.APPROVE_OPTION ) {
            File file = fc.getSelectedFile();
            String filename = file.getPath();
            if(!filename.endsWith(".txt")){
                filename+=".txt";
            }
            boolean error = true;
            try {
                FileOutputStream fos = new FileOutputStream(filename);
                fos.write(f_a.getLineDiagram().toString().getBytes());
                fos.close();
                error = false;
            } catch (Exception e) {
                error = true;
            }
            if(error){
                JOptionPane.showOptionDialog(
                        null,
                        "Ein Fehler ist beim Speichern von \""+filename+"\" aufgetreten.",
                        "Fehler",
                        JOptionPane.CLOSED_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        null,
                        null);
            }
        }
        
    }
    
    public void zoomDiagramExtents(F_Analyse f_a){
        f_a.getLineDiagramGUI().zoomExtents();
        f_a.setZx1(f_a.getLineDiagramGUI().getZx1());
        f_a.setZx2(f_a.getLineDiagramGUI().getZx2());
        f_a.setZy1(f_a.getLineDiagramGUI().getZy1());
        f_a.setZy2(f_a.getLineDiagramGUI().getZy2());
        f_a.repaintZX();
        f_a.repaintZY();
        f_a.getLineDiagramGUI().repaint();
    }
    
    public void saveAnalysisAsText(F_Analyse f_a){
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter( new FileFilter() {
            @Override public boolean accept( File f ) {
                return f.isDirectory() ||
                        f.getName().toLowerCase().endsWith( ".txt" );
            }
            @Override public String getDescription() {
                return "Text-Datei (*.txt)";
            }
        } );
        int state = fc.showSaveDialog( null );
        if ( state == JFileChooser.APPROVE_OPTION ) {
            File file = fc.getSelectedFile();
            String filename = file.getPath();
            if(!filename.endsWith(".txt")){
                filename+=".txt";
            }
            boolean error = true;
            try {
                FileOutputStream fos = new FileOutputStream(filename);
                fos.write(f_a.getEnterprise().toString().getBytes());
                fos.close();
                error = false;
            } catch (Exception e) {
                error = true;
            }
            if(error){
                JOptionPane.showOptionDialog(
                        null,
                        "Ein Fehler ist beim Speichern von \""+filename+"\" aufgetreten.",
                        "Fehler",
                        JOptionPane.CLOSED_OPTION,
                        JOptionPane.ERROR_MESSAGE,
                        null,
                        null,
                        null);
            }
        }
        
    }
    
    public void actualizeAnalysis(F_Analyse f_a){
        Enterprise e = f_a.getEnterprise();
        f_a.setUpdating(true);
        
        f_a.setSteuer(e.getBesteuerungsForm());
        
        f_a.setResidual(e.getResidual());
        
        f_a.setName(e.getName());
        
        f_a.setUpdating(false);
    }
    
    
    public void setSteuer(F_Analyse f_a, byte s){
        Enterprise e = f_a.getEnterprise();
        try {
            e.setBesteuerungsForm(s);
        } catch (Exception ex) {}
        actualizeAnalysis(f_a);
    }
    
    public void setResidual(F_Analyse f_a, byte r){
        Enterprise e = f_a.getEnterprise();
        try {
            e.setResidual(r);
        } catch (Exception ex) {}
        actualizeAnalysis(f_a);
    }
    
    
    
    private void newSavedAnalysis(String filename){
        Enterprise newEnt = Enterprise.readEnterprise(filename);
        if (newEnt == null){
            JOptionPane.showOptionDialog(null,"Datei \""+filename+"\" könnte nicht geöffnet werden!","Datei fehlerhaft",JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE,null,null,null);
            newEnt = new Enterprise(true);
        }
        F_Analyse newF_A = new F_Analyse(this, newEnt);
        actualizeAnalysis(newF_A);
        analysisList.add(newF_A);
        newF_A.setVisible(true);
    }
    
    public void setName(F_Analyse f_a, String name) {
        f_a.getEnterprise().setName(name);
    }
       
    public void compute(F_Analyse f_a){
        Enterprise e = f_a.getEnterprise();
        ResultSpectrum res = null;
        try {
            res = computer.computeUW1(e);
        } catch (Exception ex) {
            JOptionPane.showOptionDialog(
                    null,
                    "Das Berechnen ist fehlgeschlagen, überprüfen Sie die Eingaben.",
                    "Fehler",
                    JOptionPane.CLOSED_OPTION,
                    JOptionPane.ERROR_MESSAGE,
                    null,
                    null,
                    null);
        }
        f_a.setResultSpectrum(res);
        
        f_a.setLineDiagram(visualizer.visualizeResultSpectrumUw(res));
    }
    
    
    
}
