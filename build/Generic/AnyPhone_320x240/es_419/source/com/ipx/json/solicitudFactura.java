/*
 * armando solicitud json al servidor
 * 
 * {
    "client_id": 1,
     "invoice_items": [{
     "product_id": 1,
     "qty": "7.0000"},
      {
     "product_id": 2,
     "qty": "2.0000"},
      {
     "product_id":3,
     "qty": "4.0000"}]
}

{"invoice_items":[{"qty":"2","id":"2","boni":"1","desc":"3"}],
"client_id":"1",
"nit":"6047054",
"name":"torrez",
"public_id":"1",
"invoice_date":"14-10-2014",
"invoice_number":"0001","cod_control":"C1-E1-31-A4"}
* nota el orden de las variables no altera la solicitud jajaja
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
public class solicitudFactura {

private String client_id;
private Vector productos;
private String nit;
private String name;
private String invoice_number;
private String cod_control;
private String date;



 /**
     * Allows to get the JSON String from the Client passed as parameter
     * @param client Client object to convert to JSON
     * @return JSON String of the Client passed as parameter
     */
    public static String toJSON(solicitudFactura solfac)
    {
        return toJSONObject(solfac).toString();
    }
     /**
     * This method should be used by this class only, that's why it is private.
     * Allows to get a JSONObject from the Client passed as parameter
     * @param client Client Object to convert to JSONObject
     * @return JSONObject representation of the Client passed as parameter
     */
    public static JSONObject toJSONObject(solicitudFactura solfac) 
    {
        JSONObject json = new JSONObject();
        try {
//            json.put("invoice_items",solfac.getProductos() );
            json.put("client_id", solfac.getClient_id());
            json.put("nit",solfac.getNit());
            json.put("name", solfac.getName());
            json.put("invoice_number", solfac.getInvoice_number());
            json.put("cod_control", solfac.getCod_control());
            json.put("fecha", solfac.getDate());
            json.put("invoice_items",solfac.getProductos() );
            
      } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return json;
    }
    public void setClient_id(String client_id)
    {
        this.client_id = client_id;
    }
    public void setProductos(Vector productos)
    {
        this.productos = productos;
    }
    public String getClient_id()
    {
        return this.client_id;
    }
    public JSONArray getProductos()
    {
        return Products.toJSONs(this.productos);
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public String getCod_control() {
        return cod_control;
    }

    public void setCod_control(String cod_control) {
        this.cod_control = cod_control;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
}
