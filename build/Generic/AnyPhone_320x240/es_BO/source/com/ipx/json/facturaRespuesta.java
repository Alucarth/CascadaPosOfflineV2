/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ipx.json;

import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 *
 * @author David
 */
public class facturaRespuesta 
{
    private String resultado;
    private String invoice_number;
    public facturaRespuesta(String jsonText)
    {
        try {
            JSONObject json = new JSONObject(jsonText);
            
            if(json.has("resultado"))
            {
                this.setResultado(json.getString("resultado"));
            }
            if(json.has("invoice_number"))
            {
                this.setInvoice_number(json.getString("invoice_number"));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        
    }        

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }
    
    
}
