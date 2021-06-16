package uw_gui;

import javax.swing.UIManager;

public class Main {
    
    public Main() {
    }
    
    public static void main(String[] args) {
        if(args.length > 0){
            if (args[0] == "winLF"){
                try {
                    UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName() );
                } catch( Exception e ) {}
                
            }
        }
        GUIManager gm = new GUIManager();
        gm.start();
    }
    
}
