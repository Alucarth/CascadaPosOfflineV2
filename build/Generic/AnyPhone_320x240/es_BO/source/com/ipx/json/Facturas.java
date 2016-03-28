/*
 * "id":"39","invoice_number":"0001","amount":"70000.0000","nit":"1028415020","name":"Banco Uni\u00f3n S.A."
 */

package com.ipx.json;

import java.util.Vector;
import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 *
 * @author David
 */
public class Facturas
{
    public static Facturas fromJson(String jsonText)
    {
        Facturas facs =new Facturas();
        try {
            JSONObject json = new JSONObject(jsonText);
            if(json.has("id"))
            {
                facs.setId(json.getString("id"));
                
            }
            if(json.has("invoice_number"))
            {
                facs.setInvoiceNumber(json.getString("invoice_number"));
            }
            if(json.has("amount"))
            {
                facs.setAmount(json.getString("amount"));
            }
            if(json.has("nit"))
            {
                facs.setNit(json.getString("nit"));
            }
            if(json.has("name"))
            {
                facs.setName(json.getString("name"));
            }
        }catch (JSONException e){}
        return facs;
    }
    public static Vector fromJsonArray(String jsonArray)
    {
        Vector vectorFacturas= new Vector();
        try{
            JSONArray array = new JSONArray(jsonArray);
            for(int i=0;i<array.length();i++)
            {
                JSONObject json = array.getJSONObject(i);
                Facturas fac = Facturas.fromJson(json.toString());
               
                vectorFacturas.addElement(fac);
            }
        }catch(JSONException e)
        {
            e.printStackTrace();
        }
        return vectorFacturas;
    }
    private String id,invoice_number,amount,nit,name;
    
    public void setId(String id)
    {
        this.id = id;
    }
    public void setInvoiceNumber(String in)
    {
        this.invoice_number= in;
    }
    public void setAmount(String amount)
    {
        this.amount =amount;
    }
    public void setNit(String nit)
    {
        this.nit =nit;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getId()
    {
        return this.id;
    }
    public String getInvoiceNumber()
    {
        return this.invoice_number;
    }
    public String getAmount()
    {
        return this.amount;
    }
    public String getNit()
    {
        return this.nit;
    }
    public String getName()
    {
        return this.name;
    }
    
}
