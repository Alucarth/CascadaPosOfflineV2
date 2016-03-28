/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiwire.print;

import net.sf.microlog.core.Logger;
import net.sf.microlog.core.LoggerFactory;

/**
 * Virtual printer.
 *
 * @author sgherghesanu
 */
public final class Printers {

    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Printers.class);
    /**
     * instance of the virtual printer.
     */
    private static Printers instance = null;

    /**
     * default constructor.
     */
    Printers() {
    }

    /**
     * Factory of the virtual printer.
     *
     * @return instance
     */
    public static Printers getInstance() {
        if (instance == null) {
            instance = new Printers();
        }
        return instance;
    }

    /**
     * Prints string with the size of the font.
     *
     * @param s string to be printed
     * @param size of the font.
     */
    public void printText(String s, int size) {
        instance.nprintText(s, size);
    }

    /**
     * Prints string with zoom.
     *
     * @param s string to be printed.
     * @param width of zoom
     * @param height of zoom
     */
    public void printTextWidthHeightZoom(String s, int width, int height) {
        instance.nprintTextWidthHeightZoom(s, width, height);
    }

    /**
     * Prints a bitmap.
     *
     * @param abyte bitmap
     */
    public void printBitmap(byte[] abyte) {
        instance.nprintBitmap(abyte);
    }

    /**
     * Simulates the native call of printing the bitmap.
     *
     * @param abyte bitmap
     */
    private void nprintBitmap(byte[] abyte) {
        LOGGER.info("[PRINTERS]PRINT BITMAP");
    }

    /**
     * Native fake call.
     *
     * @param s string to be printed
     * @param size of the font.
     * @return the size
     */
    private int nprintText(String s, int size) {

        LOGGER.info(s + "[with size]" + size);
        return size;
    }

    /**
     * Native fake call.
     *
     * @param s string
     * @param w width
     * @param h height
     */
    private void nprintTextWidthHeightZoom(String s, int w, int h) {
        LOGGER.info(s + "[w=" + w + "/h=" + h + "]");

    }

    /**
     * Native fake call.
     *
     * @return status.
     */
    private int nopen() {
        LOGGER.info("[PRINTERS]nOpen ");
        return 1;
    }

    /**
     * Native fake call.
     *
     * @return status.
     */
    private int ngetStatus() {
        LOGGER.info("[PRINTERS]ngetStatus ");
        return 2;
    }

    /**
     * Native fake call.
     */
    private void nclose() {
        LOGGER.info("[PRINTERS]nClose ");

    }
}