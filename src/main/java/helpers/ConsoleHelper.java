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
    private static final String NEWLINE = "\n\r";
    private static final String SEPERATOR = "-----------------------------------------------------";
    private static final Color GREEN = new Color(0xb6ff51);
    private static final Color RED = new Color(0xed6565);
    private static long before, after;
    
    
    public static void start(String message) {
        console.append(SEPERATOR + NEWLINE);
        before = System.currentTimeMillis(); 
        console.append("Starting " + message + NEWLINE);
    }
    
    public static void append(String message) {
        console.append(message + NEWLINE);
    }
    
    public static void appendError(String message) {
        try {
            console.append(message + NEWLINE);
            
            int pos = console.getText().lastIndexOf(message);
            console.getHighlighter().addHighlight(pos,
                    pos + message.length(),
                    new DefaultHighlighter.DefaultHighlightPainter(RED));
            
            scrollToBottom();
            
        } catch (BadLocationException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public static void appendPercentCompleted(int current, int total) {
        int temp = (int) ((current/(float)total) * 1000);
        ConsoleHelper.append(temp/10.0 + "% completed");
    }
    
    public static void finish(String message) {       
        
        try {
            after = System.currentTimeMillis();
            
            String output = "Completed " + message + NEWLINE;
            console.append(output);
            console.append("Operation took: " + (after - before)/1000.0 + " seconds." + NEWLINE);
            
            int pos = console.getText().lastIndexOf(output);
            console.getHighlighter().addHighlight(pos,
                    pos + output.length(),
                    new DefaultHighlighter.DefaultHighlightPainter(GREEN));
            
            scrollToBottom();
        } catch (BadLocationException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
    private static void scrollToBottom() {
        console.setCaretPosition(console.getDocument().getLength());
    }
}
