/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;

/**
 *
 * @author arno
 */
public class ConsoleHelper {
    public static JTextArea console;
    public static final String NEWLINE = "\n\r";
    public static final String SEPERATOR = "-----------------------------------------------------";
    public static final Color GREEN = new Color(0xb6ff51);
    
    public static long before;
    public static long after;
    
    
    public static void start(String message) {
        console.append(SEPERATOR + NEWLINE);
        before = System.currentTimeMillis(); 
        console.append("Starting " + message + NEWLINE);
    }
    
    public static void append(String message) {
        console.append(message + NEWLINE);
    }
    
    public static void finish(String message) {       
        
        try {
            after = System.currentTimeMillis();
            
            String output = "Completed " + message + NEWLINE;
            console.append(output);
            console.append("Operation took: " + (after - before)/1000.0 + " seconds." + NEWLINE);
//            console.append(SEPERATOR + NEWLINE);
            
            int pos = console.getText().lastIndexOf(output);
            console.getHighlighter().addHighlight(pos,
                    pos + output.length(),
                    new DefaultHighlighter.DefaultHighlightPainter(GREEN));
            
        } catch (BadLocationException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
}
