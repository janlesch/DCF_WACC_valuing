package uw_core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import com.thoughtworks.xstream.XStream;

public class Enterprise implements Serializable {
    
    /**
     *
     */
    private static final long serialVersionUID = -2716167414259826580L;
    
    public static final byte c_natPerson_kapGesellschaft = 0; // natürliche Person hält Kapitalgesellschaft
    
    public static final byte c_kapGesellschaft_kapGesellschaft = 1; // Kapitalgesellschaft hält Kapitalgesellschaft
    
    public static final byte c_natPerson_persGesellschaft = 2; // natürliche Person hält Personengesellschaft bzw Einzelunternehmen
    
    public static final byte c_taxCAPM = 3; // Besteuerung über Tax-CAPM
    
    public static final byte c_Residual = 0; // Vorgegebener Residualwert
    
    public static final byte c_ewigerFCF = 1; // Ewiger Free Cash Flow
    
    private String name; // Name der Unternehmung
    
    private double i; // Zinssatz für risikoloses Fremdkapital
    
    private double rm; // Renditeerwartung des Gesamtmarktes
    
    private double beta; // Risikoschwankungsfaktor des Bewertungsobjektes
    
    private double sg, se, sk; // effektive Gewerbesteuer, effektive Einkommenssteuer, effektive Körperschaftssteuer
    
    private byte besteuerungsForm; // Form zur Berechnung der Steuer
    
    private double fk; // Marktwert des Fremdkapitals
    
    private double ifk; // Renditeerwartung der Fremdkapitalgeber
    
    private double g; // Wachstumsfaktor des n-FCF
    
    private byte residual; // Methode zur Berechnung des Residualwertes
    
    private double initH;// Initiales FK/GK
    
    private ArrayList<Periode> perioden; // Detailplanungsperioden
    
    private Periode residualWertIntervalle; // Residualwert- = Fortführungswert- = "Terminal Value"- Intervalle
    
    public Enterprise(boolean demo) {
        perioden = new ArrayList<Periode>(1);
        perioden.add(null);
        if (demo) {
            this.name = "wacc-AG";
            this.i = 0.04;
            this.rm = 0.065;
            this.beta = 1.15;
            this.sg = 0.1667;
            this.se = 0.35;
            this.sk = 0.25;
            this.g = 0.0;
            this.besteuerungsForm = c_natPerson_kapGesellschaft;
            this.residual = c_Residual;
            this.fk = 10000.0;
            this.ifk = 0.055;
            this.residualWertIntervalle = new Periode(1);
            try {
                this.residualWertIntervalle.addPeriodenIntervall(1000.0,
                        1000.0, 1.0, "Fortführungswert");
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.initH = (2.0 / 3.0);
            Periode p = new Periode(3);
            try {
                p.addPeriodenIntervall(100.0, 200.0, 0.2, "Erstes Intervall");
                p.addPeriodenIntervall(300.0, 400.0, 0.6, "Zweites Intervall");
                p.addPeriodenIntervall(500.0, 600.0, 0.2, "Drittes Intervall");
            } catch (Exception e) {
                e.printStackTrace();
            }
            perioden.add(1, p);
        } else {
            this.name="";
            this.i = 0.0;
            this.rm = 0.0;
            this.beta = 0.0;
            this.sg = 0.0;
            this.se = 0.0;
            this.sk = 0.0;
            this.besteuerungsForm = c_natPerson_kapGesellschaft;
            this.residual = c_Residual;
            this.fk = 0;
            this.ifk = 0.0;
            this.initH = 0.0;
        }
    }
    
    public static Enterprise readEnterprise(String filename) {
        Enterprise result = null;
        try {
            FileInputStream fis = new FileInputStream(filename);
            
            XStream xstream = new XStream();
            
            result = (Enterprise) xstream.fromXML(fis);
        } catch (Exception e) {
            result = null;
        }
        return result;
    }
    
    public boolean writeEnterprise(String filename) {
        boolean result = false;
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            XStream xstream = new XStream();
            
            xstream.toXML(this, fos);
            
            fos.close();
            result = true;
            
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
    
    public double getBeta() {
        return beta;
    }
    
    public void setBeta(double beta) {
        this.beta = beta;
    }
    
    public double getFk() {
        return fk;
    }
    
    public void setFk(double fk) {
        this.fk = fk;
    }
    
    public double getI() {
        return i;
    }
    
    public void setI(double i) throws Exception {
        if ((i < 0)) {
            throw new Exception("i muß größer 0 sein!");
        }
        this.i = i;
    }
    
    public int getN() {
        return perioden.size() - 1;
    }
    
    public double getRm() {
        return rm;
    }
    
    public void setRm(double rm) throws Exception {
        if ((rm < 0)) {
            throw new Exception("rm muß größer 0 sein!");
        }
        this.rm = rm;
    }
    
    public double getSe() {
        return se;
    }
    
    public void setSe(double se) throws Exception {
        if ((se < 0)) {
            throw new Exception("se muß größer 0 sein!");
        }
        this.se = se;
    }
    
    public double getSg() {
        return sg;
    }
    
    public void setSg(double sg) throws Exception {
        if ((sg < 0)) {
            throw new Exception("sg muß größer 0 sein!");
        }
        this.sg = sg;
    }
    
    public double getSk() {
        return sk;
    }
    
    public void setSk(double sk) throws Exception {
        if (sk < 0) {
            throw new Exception("sk muß größer 0 sein!");
        }
        this.sk = sk;
    }
    
    public double getIfk() {
        return ifk;
    }
    
    public void setIfk(double ifk) throws Exception {
        if (ifk < 0) {
            throw new Exception("ifk muß größergleich 0 sein!");
        }
        this.ifk = ifk;
    }
    
    public void addPeriode(Periode p, int n) throws Exception {
        if (n <= perioden.size() - 1) {
            Periode per = perioden.get(n);
            if (per != null) {
                throw new Exception(
                        "Perioden können nicht überschrieben werden!");
            } else {
                perioden.set(n, p);
            }
        } else {
            perioden.ensureCapacity(n + 1);
            int c = (n - (perioden.size() - 1)) - 1;
            for (int i = 1; i <= c; i++) {
                perioden.add(null);
            }
            perioden.add(p);
        }
    }
    
    public void addPeriodenIntervall(double a, double b, double p,
            String bemerkung, int n) throws Exception {
        Periode per;
        if (n < 0 || n >= perioden.size()) {
            per = null;
        } else {
            per = perioden.get(n);
        }
        if (per == null) {
            per = new Periode(1);
            addPeriode(per, n);
        }
        per.addPeriodenIntervall(a, b, p, bemerkung);
    }
    
    public void addPeriodenIntervall(PeriodenIntervall pi, int n)
    throws Exception {
        Periode per;
        if (n < 0 || n >= perioden.size()) {
            // Noch nicht vorhandene Periode angesprochen
            per = null;
        } else {
            per = perioden.get(n);
        }
        if (per == null) {
            per = new Periode(1);
            addPeriode(per, n);
        }
        per.addPeriodenIntervall(pi);
    }
    
    public void movePeriodenIntervall(PeriodenIntervall pi, int toN)
    throws Exception {
        if (pi == null) {// Mehr als n+1 Perioden, damit entstünde Lücke
            throw new Exception("pi == null || toN > perioden.size()");
        } else {
            outer: for (Iterator<Periode> i = perioden.iterator(); i.hasNext();) {
                Periode p = i.next();
                if (p != null) {
                    for (Iterator<PeriodenIntervall> ii = p.iterator(); ii
                            .hasNext();) {
                        PeriodenIntervall cur = ii.next();
                        if (pi == cur) {
                            p.removePeriodenIntervall(pi);
                            break outer;
                        }
                        
                    }
                }
                
            }
            
            addPeriodenIntervall(pi, toN);
            
        }
        
    }
    
    public String toString() {
        StringBuffer res = new StringBuffer(0xFFF);
        NumberFormat nf1 = NumberFormat.getNumberInstance();
        nf1.setMinimumFractionDigits(4);
        nf1.setMaximumFractionDigits(4);
        NumberFormat nf2 = NumberFormat.getNumberInstance();
        nf2.setMinimumFractionDigits(2);
        nf2.setMaximumFractionDigits(2);
        
        res.append("+-------------------------------------+\n");
        res.append("+-------------------------------------+" + "\n"
                + " Name = \""+ getName()        +"\"\n"
                + " i    = " + nf1.format(i)    + "\n"
                + " Rm   = " + nf1.format(rm)   + "\n"
                + " beta = " + nf1.format(beta) + "\n"
                + " sg   = " + nf1.format(sg)   + "\n"
                + " se   = " + nf1.format(se)   + "\n"
                + " sk   = " + nf1.format(sk)   + "\n"
                + " FK   = " + nf2.format(fk)   + "\n"
                + " i(FK)= " + nf1.format(ifk)  + "\n"
                + " g    = " + nf1.format(g)    + "\n"
                + " initH= " + nf1.format(initH)+ "\n"
                + " n    = " + getN()           + "\n"
                + "+-------------------------------------+\n");
        for (ListIterator<Periode> i = perioden.listIterator(1); i.hasNext();) {
            Periode p = i.next();
            if (p == null) {
                res.append(" **Periode " + i.previousIndex()+ " nicht vorhanden**\n");
            } else {
                res.append(" (" + i.previousIndex() + ") \n");
                res.append(p);
                
            }
        }
        res.append(" ( Residual ) \n");
        res.append(residualWertIntervalle);
        res.append("+-------------------------------------+\n");
        res.append("+-------------------------------------+\n");
        res.append("\n");
        return res.toString();
    }
    
    public boolean hasNormalizedP() {
        for (Iterator<Periode> i = perioden.iterator(); i.hasNext();) {
            Periode p = i.next();
            if (p != null) {
                if (!p.hasNormalizedP()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public void normalize() {
        for (Iterator<Periode> i = perioden.iterator(); i.hasNext();) {
            Periode p = i.next();
            if (p != null) {
                p.normalizeP();
            }
        }
    }
    
    public void testKombinator() {
        ArrayList<PeriodenIntervallKette> pik = Combiner.kombiniere(perioden,
                residualWertIntervalle);
        for (Iterator<PeriodenIntervallKette> i = pik.iterator(); i.hasNext();) {
            i.next().print();
        }
    }
    
    public double getInitH() {
        return initH;
    }
    
    public void setInitH(double inith) {
        this.initH = inith;
    }
    
    public ArrayList<Periode> getPerioden() {
        return perioden;
    }
    
    public double getG() {
        return g;
    }
    
    public void setG(double g) {
        this.g = g;
    }
    
    public byte getResidual() {
        return residual;
    }
    
    public void setResidual(byte residual) {
        this.residual = residual;
    }
    
    public byte getBesteuerungsForm() {
        return besteuerungsForm;
    }
    
    public void setBesteuerungsForm(byte besteuerungsForm) {
        this.besteuerungsForm = besteuerungsForm;
    }
    
    public Periode getResidualWertIntervalle() {
        return residualWertIntervalle;
    }
    
    public void setResidualWertIntervalle(Periode residualWertIntervalle) {
        this.residualWertIntervalle = residualWertIntervalle;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
}
