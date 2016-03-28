/*
 sucursal: {
name: "Cascada Offline"
address1: "Villa Fatima"
address2: "Calle Santa Rosa No. 471"
number_autho: "123456789"
deadline: "2015-01-01"
key_dosage: "9rCB7Sv4X29d)5k7N%3ab89p-3(5[A"
activity_pri: "Elaboracion de bebidas no alcholicas y produccion de aguas minerales."
invoice_number_counter: 5
}
 */

package com.ipx.json;

import de.enough.polish.io.Serializable;
import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 *
 * @author David
 */
public class Sucursal implements Serializable
{
    private String name;
    private String address1;
    private String address2;
    private String number_autho;
    private String deadline;
    private String key_dosage;
    private String activity_pri;
    private String invoice_number_counter;
    private String law;
    private String terceros;
    public Sucursal()
    {
    }
    public Sucursal(String jsonText)
    {
        try {
            JSONObject json =new JSONObject(jsonText);
            if(json.has("name"))
            {
                name = json.getString("name");
            }
            if(json.has("address1"))
            {
                address1=json.getString("address1");
            }
            if(json.has("address2"))
            {
                address2=json.getString("address2");
            }
            if(json.has("deadline"))
            {
                deadline=json.getString("deadline");
            }
            if(json.has("key_dosage"))
            {
                key_dosage=json.getString("key_dosage");
            }
            if(json.has("number_autho"))
            {
                number_autho=json.getString("number_autho");
            }
            if(json.has("economic_activity"))
            {
                activity_pri=json.getString("economic_activity");
            }
            if(json.has("invoice_number_counter"))
            {
                invoice_number_counter= json.getString("invoice_number_counter");
            }
            if(json.has("law"))
            {
                law = json.getString("law");
            }
            if(json.has("type_third")) { 
                terceros = json.getString("type_third"); 
            }
            
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        {
            
        }
    }

    public String getName() {
        return name;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getNumber_autho() {
        return number_autho;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getKey_dosage() {
        return key_dosage;
    }

    public String getActivity_pri() {
        return activity_pri;
    }

    public String getInvoice_number_counter() {
        return invoice_number_counter;
    }

    public String getLaw() {
        return law;
    }
    public String getTerceros(){ 
        return terceros; 
    }

    public void setInvoice_number_counter(String invoice_number_counter) {
        this.invoice_number_counter = invoice_number_counter;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setNumber_autho(String number_autho) {
        this.number_autho = number_autho;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setKey_dosage(String key_dosage) {
        this.key_dosage = key_dosage;
    }

    public void setActivity_pri(String activity_pri) {
        this.activity_pri = activity_pri;
    }

    public void setLaw(String law) {
        this.law = law;
    }
    public void setTerceros(String terceros){ 
        this.terceros = terceros; 
    }
    
    public void borrar()
    {
        this.activity_pri=null;
        this.address1=null;
        this.address2=null;
        this.deadline=null;
        this.deadline=null;
        this.invoice_number_counter=null;
        this.key_dosage=null;
        this.law=null;
        this.name=null;
        this.number_autho=null;
        this.terceros=null;
    }
   
    
}
