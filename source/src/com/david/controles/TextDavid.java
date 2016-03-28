/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.david.controles;
import de.enough.polish.ui.Canvas;
import de.enough.polish.ui.TextField;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author David
 */

public class TextDavid extends TextField 
{
    static final char[] KEY_NUM1_CHARS = {'.', '?', '!'};
	static final char[] KEY_NUM2_CHARS = {'a', 'b', 'c'};
	static final char[] KEY_NUM3_CHARS = {'d', 'e', 'f'};
	static final char[] KEY_NUM4_CHARS = {'g', 'h', 'i'};
	static final char[] KEY_NUM5_CHARS = {'j', 'k', 'l'};
	static final char[] KEY_NUM6_CHARS = {'m', 'n', 'o'};
	static final char[] KEY_NUM7_CHARS = {'p', 'q', 'r', 's'};
	static final char[] KEY_NUM8_CHARS = {'t', 'u', 'v'};
	static final char[] KEY_NUM9_CHARS = {'w', 'x', 'y', 'z'};
	static final char[] KEY_NUM0_CHARS = {' '};

    public TextDavid(String label, String text, int maxSize, int constraints) {
        super(label, text, maxSize, constraints);
        Canvas canvas = new Canvas() { // anonymous class     

         public void paint(Graphics g) {
         }

         protected void keyPressed(int keyCode) {
           if (keyCode > 0) {
             System.out.println("keyPressed " +((char)keyCode));
           } else {
             System.out.println("keyPressed action " +getGameAction(keyCode));
           }                  
         }

         protected void keyReleased(int keyCode) {
           if (keyCode > 0) {
             System.out.println("keyReleased " +((char)keyCode));
           } else {
             System.out.println("keyReleased action " +getGameAction(keyCode));
           } 
         }

            protected void paint(de.enough.polish.ui.Graphics grphcs) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
      }; // end of anonymous class
       
//        this.set
      
    }

    protected boolean handleKeyPressed(int keyCode, int gameAction) {
         this.setText("Presiono "+keyCode);
         return true;
    }
    
   
}
