/*
name
nit
 */
package com.ipx.json;

import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 *
 * @author David Torrez
 */
public class Client 
{
    private String name;
    private String nit;
    public Client(String jsonText)
    {
        try {
            JSONObject json = new JSONObject(jsonText);
            if(json.has("name"))
            {
                name = json.getString("name");
            }
            if(json.has("nit"))
            {
                nit = json.getString("nit");
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
}
