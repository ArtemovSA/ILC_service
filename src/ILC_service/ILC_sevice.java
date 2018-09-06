/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILC_service;

import com.jtattoo.plaf.fast.FastLookAndFeel;
import java.util.Properties;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author user
 */
public class ILC_sevice {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Установка темы оформления
        Properties p = new Properties();
        p.put("windowDecoration", "off");

        try {
            FastLookAndFeel.setCurrentTheme(p);
            UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
          //UIManager.setLookAndFeel("com.bulenkov.darcula.DarculaLaf");//new TinyLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Can't use the specified look and feel on this platform.");
        } catch (Exception e) {
            System.err.println("Couldn't get specified look and feel, for some reason.");
        }
        
        ILC_main_form form = new ILC_main_form();
        form.setLocationRelativeTo(null);
        form.setVisible(true); 
    }  
    
}
