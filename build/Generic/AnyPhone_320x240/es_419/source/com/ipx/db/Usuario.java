/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ipx.db;

import de.enough.polish.io.Serializable;

/**
 *
 * @author David
 */
public class Usuario implements Serializable
{
      private String usuario="";
      private String password="";
      private boolean sesion =false;
      private String ice="";

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSesion() {
        return sesion;
    }

    public void setSesion(boolean sesion) {
        this.sesion = sesion;
    }

    public String getIce() {
        return ice;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }
    public String getllave()
    {
        return this.usuario+":"+this.password;
    }
    public void borrar()
    {
        this.usuario =null;
        this.password=null;
        this.sesion = false;
        
        this.ice = null;
    }
    
}
