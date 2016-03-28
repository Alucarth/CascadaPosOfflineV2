/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiwire.print;

import com.nbbse.printer.VoltageMonitor;
import net.sf.microlog.core.Logger;
import net.sf.microlog.core.LoggerFactory;

/**
 * Class to implement hardware monitoring.
 *
 * @author sgherghesanu
 */
public class HardwareStatus implements VoltageMonitor {
    /**
     * Logger for this class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HardwareStatus.class);

    /**
     * Voltage level checking.
     */
    private boolean highVoltage = true;

    public boolean isHighVoltage() {
        return highVoltage;
    }

    public void setHighVoltage(boolean highVoltage) {
        this.highVoltage = highVoltage;
    }

    /**
     * Send a power alert.
     */
    public void powerAlert() {
        //if measuring the voltage fails
        if (!isHighVoltage()) {
            LOGGER.error("[HARDWARESTATUS]There is not enough voltage to print!");
            
        }
    }
}
