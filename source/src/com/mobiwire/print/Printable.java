/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiwire.print;

/**
 * To be implemented by the classes who have to print.
 *
 * @author sgherghesanu
 */
public interface Printable {

    /**
     * Notifies the printer started.
     * 
     * @param sid identifies the caller of the printer.
     * @param responseCode is signaling the start of printing.
     */
    void printStarted(int sid, int responseCode);
}
