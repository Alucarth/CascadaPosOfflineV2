/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ipx.http;

import com.mobiwire.startup.StartApp;
import java.io.IOException;

/**
 *
 * @author David
 */
public class RestProductor extends Thread
{
    StartApp startApp;
    BufferRest bufferRest;
    int consultaHttp;
    String parametros;
    String llave;
    
    public RestProductor(StartApp startApp,BufferRest bufferRest,int consultaHttp,String parametros,String llave)
    {
        this.bufferRest = bufferRest;
        this.consultaHttp = consultaHttp;
        this.parametros = parametros;
        this.llave = llave;
        this.startApp = startApp;
    }

    public void run() {
        
        try {
            this.startApp.switchDisplayable(null,startApp.getFormLoading());
            ConectorRest cr = new ConectorRest();
            
              String resultado = cr.EnviarGet(ConexionIpx.getURL(consultaHttp), this.llave);
            switch(cr.getCodigoRespuesta())
            {
                case 200: 
                     this.bufferRest.setRespuesta(resultado);
                    break;
                
                case 401:
                     this.startApp.switchDisplayable(null,startApp.getFormLogin());
                     this.startApp.getAlerta("Autentificacion Fallida","Verifique que el Usuario y Password sean CORRECTOS");    
                    break;
                
                case 404:
                     this.startApp.switchDisplayable(null,startApp.getFormLogin());
                     this.startApp.getAlerta("Error 404","Se perdio la coneccion con el servidor");    
                    break;    
                
                case 500:
                     this.startApp.switchDisplayable(null,startApp.getFormLogin());
                     this.startApp.getAlerta("Error del Servidor ","Conflictos internos con el servidor \n favor de comunicarse con el soporte tecnico");    
                    break;
                
                    
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
