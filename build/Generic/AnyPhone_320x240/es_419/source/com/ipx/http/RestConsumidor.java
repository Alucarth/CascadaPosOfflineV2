/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ipx.http;

import com.mobiwire.startup.StartApp;

/**
 *
 * @author David
 */
public class RestConsumidor extends Thread
{
    StartApp startApp;
    BufferRest bufferRest;
    String resultado;
    public RestConsumidor(StartApp startpApp, BufferRest bufferRest)
    {
        this.startApp = startpApp;
        this.bufferRest = bufferRest;
    }

    public void run() {
        
        this.startApp.procesarLogin(this.bufferRest.getRespuesta());
        this.startApp.cambiarPantalla();
        
    }

    
    
    
}
