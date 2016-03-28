/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiwire.print;

/**
 * Class to assemble the label and the text of a StringItem.
 *
 * @author sgherghesanu
 */
public class StringItemHelper {

    /**
     * Label of StringItem.
     */
    private String label;
    /**
     * Text of StringItem.
     */
    private String text;

    /**
     * Default constructor.
     */
    public StringItemHelper() {
    }

    /**
     * Another constructor.
     *
     * @param label of StringItem
     * @param text  of StringItem
     */
    public StringItemHelper(String label, String text) {
        this.label = label;
        this.text = text;

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * Defines toString() method.
     * 
     * @return String
     */
    public String toString() {
        return (this.label + " " + this.text);
    }
}
