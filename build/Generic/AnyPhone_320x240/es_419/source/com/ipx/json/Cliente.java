/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipx.json;

import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 *
 * @author David Torrez
 */
public class Cliente 
{
    private String resultado;
    private Clients cliente;
    public Cliente()
    {}
    public Cliente(String jsonText)
    {
        try {
            JSONObject json = new JSONObject(jsonText);
            if(json.has("resultado"))
            {
                resultado = json.getString("resultado");
            }
            if(json.has("cliente"))
            {
               cliente = Clients.fromJson(json.getString("cliente"));
               
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        
                
    }
    public void setCliente(Clients cliente)
    {
        this.cliente = cliente;
    }
    public String getResultado()
    {
        return this.resultado;
    }
    public Clients getCliente()
    {
        return this.cliente;
    }
}
