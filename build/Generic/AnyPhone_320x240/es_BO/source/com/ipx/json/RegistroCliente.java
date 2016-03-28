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
public class RegistroCliente 
{
    private String nit="";
    private String nombre="";
    private String telefono="";
    private String email="";
     //armando solicitud de usuario a cliente
      /**
     * Allows to get the JSON String from the Client passed as parameter
     * @param client Client object to convert to JSON
     * @return JSON String of the Client passed as parameter
     */
    public void setNit(String nit)
    {
        this.nit=nit;
    }
    public void setNombre(String nombre)
    {
        this.nombre=nombre;
    }
    public void setTelefono(String telefono)
    {
        this.telefono=telefono;
    }
    public void setEmail(String email)
    {
      
        this.email = email;
    }
    public String getNit()
    {
        return this.nit;
    }
    public String getNombre()
    {
        return this.nombre;
    }
    public String getTelefono()
    {
        return this.telefono;
    }
    public String getEmail()
    {
        return this.email;
    }
    
    public static String toJSON(RegistroCliente rc)
    {
        return toJSONObject(rc).toString();
    }
     /**
     * This method should be used by this class only, that's why it is private.
     * Allows to get a JSONObject from the Client passed as parameter
     * @param client Client Object to convert to JSONObject
     * @return JSONObject representation of the Client passed as parameter
     * 
     * {"nit":1234567,"name":"clientepos","phone":70000004,"email":"clientepos@clientepos.com"}
     */
    private static JSONObject toJSONObject(RegistroCliente rc) {
        JSONObject json = new JSONObject();
        try {
            json.put("nit", rc.getNit());
            json.put("name",rc.getNombre());
            json.put("phone",rc.getTelefono());
            json.put("email",rc.getEmail());
            
      } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return json;
    }
}
