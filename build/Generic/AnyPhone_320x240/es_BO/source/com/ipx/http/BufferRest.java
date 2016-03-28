/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ipx.http;

/**
 *
 * @author David
 */
public class BufferRest 
{
    private String contenido;
    private boolean disponible=false;
    public synchronized String getRespuesta()
    {
        while(!disponible)
        {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        disponible=false;
        notify();
        return this.contenido;
    }
    public synchronized void setRespuesta(String contenido)
    {
        while(disponible)
        {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
      
        this.contenido = contenido;
        disponible=true;
        notify();
    }
    
}
