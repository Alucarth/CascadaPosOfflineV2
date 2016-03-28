/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiwire.print;

/**
 * Controller of printing access.
 *
 * @author sgherghesanu
 */
public class PrintController implements Printable {

    /**
     * Instance of controller.
     */
    private static PrintController instance = null;
    /**
     * Layout to be used by the printer agent.
     */
    private PrintLayout layout;
    /**
     * Work flow id.
     */
    private int sid;
    /**
     * Response code from the printer agent.
     */
    private int responseCode = 0;//printing not started

    /**
     * Default controller.
     */
    PrintController() {
        layout = new PrintLayout();

    }

    /**
     * Factory of controller.
     *
     * @return instance of controller
     */
    public static PrintController getInstance() {
        if (instance == null) {

            instance = new PrintController();
        }
        return instance;

    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public PrintLayout getLayout() {
        return layout;
    }

    public void setLayout(PrintLayout layout) {
        this.layout = layout;
    }

    /**
     * Sending the print request in a different thread.
     */
    public void sendPrintRequest() {
      
    }

    /**
     * Notifies if the printing is started.
     *
     * @param sid work flow id.
     * @param responseCode coming from the printer agent.
     */
    public void printStarted(int sid, int responseCode) {
        this.responseCode = responseCode;
    }
}
