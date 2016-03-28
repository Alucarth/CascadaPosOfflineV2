/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ipx.json;

import com.ipx.http.ConectorRest;
import java.io.IOException;
import java.util.Vector;
import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 *
 * @author David
 */
public class SendInvoices 
{
//    private Vector Factura;
    
//     public static String toJSON(SendInvoices solfac)
//    {
//        return (solfac).toString();
//    }
     /**
     * This method should be used by this class only, that's why it is private.
     * Allows to get a JSONObject from the Client passed as parameter
     * @param client Client Object to convert to JSONObject
     * @return JSONObject representation of the Client passed as parameter
     */
    public static String toJSONObjects(Vector facturas) 
    {
//        JSONObject json = new JSONArra();
        JSONArray jsonArray = new JSONArray ();
      
//            json.put("invoice_items",solfac.getProductos() );
            for(int i=0;i<facturas.size();i++)
//            for(int i=0;i<5;i++)
            {
//                JSONObject objeto =;
                FacturaOffline  factura = (FacturaOffline) facturas.elementAt(i);
                jsonArray.put(factura.getFactura());
            }
            
        return jsonArray.toString();
    }
    public static String sendjsonObject(Vector facturas,String llave) 
    {
//        JSONObject json = new JSONArra();
        JSONArray jsonArray = new JSONArray ();
      
//            json.put("invoice_items",solfac.getProductos() );
        try{
            for(int i=0;i<facturas.size();i++)
            {
//                JSONObject objeto =;
                ConectorRest c = new ConectorRest();
                FacturaOffline  factura = (FacturaOffline) facturas.elementAt(i);
                c.EnviarRestPost("http://www.sigcfactu.com.bo/pos",factura.getFactura().toString(),llave);
                try {
                    Thread.sleep(2000);
//                jsonArray.put(factura.getFactura());
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }catch(IOException e){
        }
        return jsonArray.toString();
    }
    public static String sendjsonObject(Vector facturas,int index,String llave) 
    {
//        JSONObject json = new JSONArra();
        JSONArray jsonArray = new JSONArray ();
      
//            json.put("invoice_items",solfac.getProductos() );
        try{
//            for(int i=0;i<facturas.size();i++)
//            {
//                JSONObject objeto =;
                ConectorRest c = new ConectorRest();
                FacturaOffline  factura = (FacturaOffline) facturas.elementAt(index);
                c.EnviarRestPost("http://www.sigcfactu.com.bo/pos",factura.getFactura().toString(),llave);
                try {
                    Thread.sleep(2000);
//                jsonArray.put(factura.getFactura());
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
//            }
        }catch(IOException e){
        }
        return jsonArray.toString();
    }
    
    
//   public JSONArray getFacturas()
//    {
//        return FacturaOffline.toJSONs(this.Factura);
//    }
//
//    public void setFactura(Vector Factura) {
//        this.Factura = Factura;
//    }
    
   
}
