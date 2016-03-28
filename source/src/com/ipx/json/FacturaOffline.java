/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ipx.json;

import de.enough.polish.io.Serializable;
//import de.enough.polish.util.Comparable;
//import java.util.Comparator;
import java.util.Vector;
import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 *
 * @author David
 */
public class FacturaOffline implements Serializable
{
    private String codCli;
    
//    private String factura;
    
    private String invoice_number;
    private String control_code;
    private String invoice_date;
    private String amount;
    private String subtotal;
    private String fiscal;
    private String ice;
    private String law;
    private String address1;
    private String address2;
    private String num_auto;
    private String fecha_limite;
    private String nameCuenta;
    private String niCuenta;
    private String nitCliente;
    private String nameCliente;
    private String clienteId;
    private boolean isInvoice;
    
    //guardar credenciales de la factura
    
    private String llaveUsuario;
    
//    private Factura invoice;
    private Vector items;
    private Vector listaProductos;
    public String getCodCli() {
        return codCli;
    }

    public void setCodCli(String codCli) {
        this.codCli = codCli;
    }

    
    //MODIFICANDO LA PUTA FACTURA xd jajajaja no renieges ya lo estoy acbando XD
    public JSONObject getFactura() {
        
        
        solicitudFactura sf= new solicitudFactura();
        if(this.isInvoice)
        {
            sf.setClient_id(this.clienteId+"1");
//            sf.setClient_id(this.clienteId);
        }
        else
        {
            sf.setClient_id(this.clienteId+"0");
//            sf.setClient_id(this.clienteId);
        }
        
        sf.setCod_control(this.control_code);
        sf.setDate(this.invoice_date);
        sf.setName(this.nameCliente);
        sf.setNit(this.nitCliente);
        sf.setInvoice_number(this.invoice_number);
        sf.setProductos(this.listaProductos);
        //guardando datos del cliente
     
        
        /*guardar esta factura para el offline XD
         estos son los datos a guardar 
            {"invoice_items":[{"qty":"2","id":"2","boni":"1","desc":"3"}],"client_id":"1","nit":"6047054","name":"torrez","public_id":"1","invoice_date":"14-10-2014","invoice_number":"0001"}
        */
        
        /*guardando factura para recuperarlo de manera offline*/
         
        return solicitudFactura.toJSONObject(sf);
//        return this.factura;
    }
    public String getFacturaString()
    {
               solicitudFactura sf= new solicitudFactura();
        sf.setClient_id(this.clienteId);
        sf.setCod_control(this.control_code);
        sf.setDate(this.invoice_date);
        sf.setName(this.nameCliente);
        sf.setNit(this.nitCliente);
        sf.setInvoice_number(this.invoice_number);
        sf.setProductos(this.listaProductos);
        return solicitudFactura.toJSON(sf);
    }

//    public void setFactura(String factura) {
//        this.factura = factura;
//    }

//    public Factura getInvoice() {
//        return invoice;
//    }
//
//    public void setInvoice(Factura invoice) {
//        this.invoice = invoice;
//    }
//    
    
    public Vector getItems() {
        return items;
    }

    public void setItems(Vector items) {
        this.items = items;
    }

    public String getInvoice_number() {
        return invoice_number;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public String getControl_code() {
        return control_code;
    }

    public void setControl_code(String control_code) {
        this.control_code = control_code;
    }

    public String getInvoice_date() {
        return invoice_date;
    }

    public void setInvoice_date(String invoice_date) {
        this.invoice_date = invoice_date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getFiscal() {
        return fiscal;
    }

    public void setFiscal(String fiscal) {
        this.fiscal = fiscal;
    }

    public String getIce() {
        return ice;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public String getLaw() {
        return law;
    }

    public void setLaw(String law) {
        this.law = law;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getNum_auto() {
        return num_auto;
    }

    public void setNum_auto(String num_auto) {
        this.num_auto = num_auto;
    }

    public String getFecha_limite() {
        return fecha_limite;
    }

    public void setFecha_limite(String fecha_limite) {
        this.fecha_limite = fecha_limite;
    }

    public String getNameCuenta() {
        return nameCuenta;
    }

    public void setNameCuenta(String nameCuenta) {
        this.nameCuenta = nameCuenta;
    }

    public String getNiCuenta() {
        return niCuenta;
    }

    public void setNiCuenta(String niCuenta) {
        this.niCuenta = niCuenta;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public String getNameCliente() {
        return nameCliente;
    }

    public void setNameCliente(String nameCliente) {
        this.nameCliente = nameCliente;
    }

    public String getLlaveUsuario() {
        return llaveUsuario;
    }

    public void setLlaveUsuario(String llaveUsuario) {
        this.llaveUsuario = llaveUsuario;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public boolean isInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(boolean isInvoice) {
        this.isInvoice = isInvoice;
    }
    
    
    //armando las facturas para enviarlas
    
     public static String toJSON(FacturaOffline factura)
    {
        return toJSONObject(factura).toString();
    }
     /**
     * This method should be used by this class only, that's why it is private.
     * Allows to get a JSONObject from the Client passed as parameter
     * @param client Client Object to convert to JSONObject
     * @return JSONObject representation of the Client passed as parameter
     */
    private static JSONObject toJSONObject(FacturaOffline factura) {
        JSONObject json = new JSONObject();
        try{
            
            json.put("factura", factura.getFactura());
//            json.put("qty",producto.getQty());
//           
//            json.put("boni", producto.getBoni());
//            json.put("desc", producto.getDesc());
            
        }catch (JSONException ex) {
            ex.printStackTrace();
        }
        return json;
    }
    
     /**
     * Allows to get JSON String from a Vector of Clients
     * @param clients Vector of clients to convert to JSON
     * @return JSON String of the Vector of clients
     */
    public static JSONArray toJSONs(Vector facturas) {
        JSONArray facturasArray = new JSONArray();
        for (int i = 0; i < facturas.size(); i++) {
            FacturaOffline factura = (FacturaOffline) facturas.elementAt(i);

            JSONObject jsonObject = toJSONObject(factura);
            facturasArray.put(jsonObject);
        }
//        return productsArray.toString();
        return facturasArray;
    }

    public Vector getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(Vector listaProductos) {
        this.listaProductos = listaProductos;
    }



//    public int compareTo(Object o) {
//         FacturaOffline f = (FacturaOffline) o;
//         if(Integer.parseInt(this.getInvoice_number())>Integer.parseInt(f.getInvoice_number()))
//         {
//             return 1;
//         }
//        return 0;
//    }
    
}
