/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipx.json;

import com.ipx.util.Log;
import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 *
 * @author David Torrez
 */
public class ResponseSave
{
//    {"resultado ":"0
//","respuesta":"1
//"}
    private String resultado;
    private String respuesta;
    public ResponseSave(String jsonText)
    {
        try {
            JSONObject json = new JSONObject(jsonText);
             Log.i("resultado", json.getString("resultado"));
            if(json.has("resultado"))
            {   
                Log.i("resultado", json.getString("resultado"));
                resultado=json.getString("resultado");
            }
            if(json.has("respuesta"))
            {
                 Log.i("respuesta", json.getString("respuesta"));
                setRespuesta(json.getString("respuesta"));
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

    public int getRespuesta() {
        return Integer.parseInt(this.respuesta);
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    
}
