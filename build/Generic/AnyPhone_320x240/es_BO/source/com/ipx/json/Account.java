/*
 * 
 *
name
nit
address1
address2
num_auto
fecha_limite
* country
 */
package com.ipx.json;

import de.enough.polish.io.Serializable;
import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 *
 * @author David Torrez
 */
public class Account implements Serializable
{
    private String name;
    private String nit;
    private String address1;
    private String address2;
    private String num_auto;
    private String fecha_limite;
    
    public Account()
    {
    }
    public Account(String jsonText)
    {
        try {
            JSONObject json =new  JSONObject(jsonText);
            if(json.has("name"))
            {
                name = json.getString("name");
            }
            if(json.has("nit"))
            {
                nit = json.getString("nit");
            }
            if(json.has("address1"))
            {
                address1=json.getString("address1");
            }
            if(json.has("address2"))
            {
                address2=json.getString("address2");
            }
            if(json.has("num_auto"))
            {
                num_auto=json.getString("num_auto");
            }
            if(json.has("fecha_limite"))
            {
                fecha_limite=json.getString("fecha_limite");
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
    public String getName()
    {
        return this.name;
    }
    public String getNit()
    {
        return this.nit;
    }
    public String getAddress1()
    {
        return this.address1;
    }
    public String getAddress2()
    {
        return this.address2;
    }
    public String getNumAuto()
    {
        return this.num_auto;
    }
    public String getFechaLimite()
    {
        return this.fecha_limite;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }
    
}
