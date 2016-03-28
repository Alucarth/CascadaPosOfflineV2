/*
 *      "notes": "Servicios",
        "cost": "20.0000",
        "qty": "2.0000",
 */
package com.ipx.json;

import java.util.Vector;
import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 *
 * @author David Torrez
 */
public class InvoiceItems
{
    private String notes;
    private String cost;
    private String qty;
    
    public static InvoiceItems fromJson(String jsonText)
    {
        InvoiceItems invoiceItem = new InvoiceItems();
        try {
            JSONObject json = new JSONObject(jsonText);
            if(json.has("notes"))
            {
                invoiceItem.setNotes(json.getString("notes"));
            }
            if(json.has("cost"))
            {
                invoiceItem.setCost(json.getString("cost"));
            }
            if(json.has("qty"))
            {
                invoiceItem.setQty(json.getString("qty"));
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        
        return invoiceItem;
    }
    public static Vector fromJsonArray(String jsonArray)
    {
        Vector vectorInvoiceItems= new Vector();
        try{
            JSONArray array = new JSONArray(jsonArray);
            for(int i=0;i<array.length();i++)
            {
                JSONObject json = array.getJSONObject(i);
                InvoiceItems it = InvoiceItems.fromJson(json.toString());
//                Products product = Products.fromJson(json.toString());
//                vectorProducts.addElement(product);
                vectorInvoiceItems.addElement(it);
            }
        }catch(JSONException e)
        {
            e.printStackTrace();
        }
        return vectorInvoiceItems;
    }
  
    public void setNotes(String notes)
    {
        this.notes = notes;
    }
    public void setCost(String cost)
    {
        this.cost = cost;
    }
    public void setQty(String qty)
    {
        this.qty=qty;
    }
    public String getNotes()
    {
        return this.notes;
    }
    public String getCost()
    {
        return this.cost;
    }
    public String getQty()
    {
        return this.qty;
    }
}
