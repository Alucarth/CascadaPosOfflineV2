/*
 * Clase que trata la respuesta json
 * "invoice_number": "0012",
    "control_code": "D5-BE-A6-00",
    * "invoice_date": "2014-04-17"
    * "amount": "140.0000",
 */
package com.ipx.json;

import java.util.Vector;
import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 *
 * @author David Torrez
 */
public class Factura 
{
    private String resultado;
    private String invoice_number;
    private String control_code;
    private String invoice_date;
    private String amount;
    private String subtotal;
    private String fiscal;
    private String ice;
    private String law;
    //falta cliente, cuenta y invoice items
    private Account account;
    private Vector items;
//    private String invoiceitem;
    private Clients cliente;
    //
  
    private String address1;
    private String address2;
    private String num_auto;
    private String fecha_limite;
    
    private Vector listaProductos;
    public Factura()
    {
        
    }
    public Factura(String jsonText)
    {
        try {
            JSONObject json = new JSONObject(jsonText);
            if(json.has("resultado"))
            {
                resultado = json.getString("resultado");
            }
            if(json.has("invoice_number"))
            {
                invoice_number=json.getString("invoice_number");
            }
            if(json.has("control_code"))
            {
                control_code = json.getString("control_code");
            }
            if(json.has("invoice_date"))
            {
                invoice_date = json.getString("invoice_date");
            }
            if(json.has("amount"))
            {
                amount = json.getString("amount");
            }
            if(json.has("subtotal"))
            {
                subtotal = json.getString("subtotal");
            }
            if(json.has("fiscal"))
            {
                fiscal = json.getString("fiscal");
            }
            if(json.has("client"))
            {
                cliente = Clients.fromJson(json.getString("client"));
            }
            if(json.has("account"))
            {
                account = new Account(json.getString("account"));
                               
            }
            if(json.has("invoice_items"))
            {
                items = InvoiceItem.fromJsonArray(json.getString("invoice_items"));
//                items = InvoiceItems.fromJsonArray(json.getString("invoice_items"));
//                System.out.print("\ninvoice_items = "+json.getString("invoice_items"));
//                invoiceitem = json.getString("invoice_items");
                
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
            if(json.has("ice"))
            {
                ice =json.getString("ice");
            }
            if(json.has("law"))
            {
                law = json.getString("law");
            }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        
    }
    public String getResultado()
    {
        return this.resultado;
    }
    public String getInvoiceNumber()
    {
        return this.invoice_number;
    }
    public String getControlCode()
    {
        return this.control_code;
    }
    public String getInvoiceDate()
    {
        return this.invoice_date;
    }
    public String getAmount()
    {
        return this.amount;
    }
    public String getSubtotal()
    {
        return this.subtotal;
    }
    public String getFiscal()
    {
        return this.fiscal;
    }
    public Account getAccount()
    {
        return this.account;
    }
    public Vector getInvoiceItems()
    {
        return this.items;
    }
    public Clients getCliente()
    {
        return  this.cliente;
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
    public String getIce()
    {
        return this.ice;
    }
    public String getLaw()
    {
        return this.law;
    }

    //seteando codigo
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public void setInvoice_number(String invoice_number) {
        this.invoice_number = invoice_number;
    }

    public void setControl_code(String control_code) {
        this.control_code = control_code;
    }

    public void setInvoice_date(String invoice_date) {
        this.invoice_date = invoice_date;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public void setFiscal(String fiscal) {
        this.fiscal = fiscal;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }

    public void setLaw(String law) {
        this.law = law;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setItems(Vector items) {
        this.items = items;
    }

    public void setCliente(Clients cliente) {
        this.cliente = cliente;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public void setNum_auto(String num_auto) {
        this.num_auto = num_auto;
    }

    public void setFecha_limite(String fecha_limite) {
        this.fecha_limite = fecha_limite;
    }

    public Vector getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(Vector listaProductos) {
        this.listaProductos = listaProductos;
    }
    
}
