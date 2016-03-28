/*
 * respuesta a tratar 
"account_id": "12",
        "user_id": "12",
        "invoice_id": "39",
        "product_id": "14",
        "product_key": "800",
        "notes": "Licencia Corporativa Win Calc Ilimitada (Winchadora\nDigital). Incluye Soporte Preventivo y Correctivo por\n(12) meses.",
        "cost": "70000.0000",
        "qty": "1.0000",
        "tax_name": "",
        "tax_rate": "0.0000",
        "public_id": "1"
 */

package com.ipx.json;

import de.enough.polish.io.Serializable;
import java.util.Vector;
import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 *
 * @author David
 */
public class InvoiceItem implements Serializable
{   
    private String notes;
    private String qty;
    private String cost;
    private String boni;
    private String desc;
    
    public String getBoni() {
        return boni;
    }

    public String getDesc() {
        return desc;
    }
   

    public void setBoni(String boni) {
        this.boni = boni;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
  
    public static InvoiceItem fromJson(String jsonText)
    {   
        InvoiceItem  it = new InvoiceItem();
        try {
            JSONObject json = new JSONObject(jsonText);
            
            if(json.has("notes"))
            {
                it.setNotes(json.getString("notes"));
            }
            if(json.has("cost"))
            {
                it.setCost(json.getString("cost"));
            }
            if(json.has("qty"))
            {
                it.setQty(json.getString("qty"));
            }
            if(json.has("boni"))
            {
                it.setBoni(json.getString("boni"));
            }
            if(json.has("desc"))
            {
                it.setDesc(json.getString("desc"));
            }
            
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        
        
        return it;
    }
    
     public static Vector fromJsonArray(String jsonArray)
    {
        Vector vectorItems= new Vector();
        try{
            JSONArray array = new JSONArray(jsonArray);
            for(int i=0;i<array.length();i++)
            {
                JSONObject json = array.getJSONObject(i);
                InvoiceItem it = InvoiceItem.fromJson(json.toString());
//                Products product = Products.fromJson(json.toString());
//                vectorProducts.addElement(product);
                vectorItems.addElement(it);
            }
        }catch(JSONException e)
        {
            e.printStackTrace();
        }
        return vectorItems;
    }
    public void setNotes(String notes)
    {
        this.notes = notes;
    }
    public void setQty(String qty)
    {
        this.qty = qty;
    }
    public void setCost(String cost)
    {
        this.cost = cost;
    }
    public String getNotes()
    {
        return this.notes;
    }
    public String getQty()
    {
        return this.qty;
    }
    public String getCost()
    {
        return this.cost;
    }
        
}
