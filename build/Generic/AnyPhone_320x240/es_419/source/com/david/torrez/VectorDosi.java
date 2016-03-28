/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.david.torrez;

/**
 *
 * @author David
 */
public class VectorDosi 
{
    private int puntero=0;
    private final char caracteres[];
    
    public VectorDosi(String keyDosage)
    {
        this.caracteres = keyDosage.toCharArray();
    }
    public String getCadena(int cantidad)
    {
        String cadena="";
        while(cantidad >0)
        {
            //validar el tamaño de la llave de dosificacion
            if(puntero<caracteres.length)
            {
            cadena = cadena+caracteres[puntero];
            }
            puntero++;
            cantidad--;
        }
        return cadena;
    }
    
}
