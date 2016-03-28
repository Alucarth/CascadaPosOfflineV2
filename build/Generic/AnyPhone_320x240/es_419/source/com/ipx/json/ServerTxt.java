/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ipx.json;

import de.enough.polish.io.Serializable;

/**
 *
 * @author David
 */
public class ServerTxt implements Serializable
{
    
    private String clientesTxt;
    private String productosTxt;

    public String getClientesTxt() {
        return clientesTxt;
    }

    public void setClientesTxt(String clientesTxt) {
        this.clientesTxt = clientesTxt;
    }

    public String getProductosTxt() {
        return productosTxt;
    }

    public void setProductosTxt(String productosTxt) {
        this.productosTxt = productosTxt;
    }
    
}
