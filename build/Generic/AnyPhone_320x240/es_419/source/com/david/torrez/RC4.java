/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.david.torrez;

/**
 *
 * @author Ing.David Torrez Salinas
 */
public class RC4 
{
    private int state []=new int[256];
    private int x=0,y=0,index1=0,index2=0,Nmen=0,i=0;
    private String mensajeCifrado="";
    private String mensaje,key;
    
    public RC4(String mensaje,String key)
    {
        this.mensaje = mensaje;
        this.key = key;
        for(i=0;i<256;i++)
        {
            state[i]=i;
        }
    }
    private void Intercambiar(int n,int m)
    {
        int aux=state[n];
        state[n]=state[m];
        state[m]=aux;
    }
    public String getRC4()
    {
       int k;
        char keys[] = key.toCharArray();
        for(i=0;i<256;i++)
        {
            k= (int) keys[index1];
            index2 =(k+state[i]+index2)%256;
            Intercambiar(i,index2);
            index1 =(index1+1)%key.length();
        }
        char mensajes[]=mensaje.toCharArray();
        for(int i=0;i<mensaje.length();i++)
        {
            x=(x+1)%256;
            y= (state[x]+y)%256;
            Intercambiar(x,y);
//            k= key.charAt(i % key.length());
            k=(int) mensajes[i];
            Nmen = k^(state[(state[x]+state[y])%256]);
//            mensajeCifrado = mensajeCifrado+"-"+decToHex(Nmen);
            mensajeCifrado = mensajeCifrado+""+getRellenarCeros(Integer.toHexString(Nmen).toUpperCase());
            
        }
        mensajeCifrado = mensajeCifrado.substring(0, mensajeCifrado.length());
        return this.mensajeCifrado;
    }
    public String getMensajeCifrado()
    {
        int k;
        char keys[] = key.toCharArray();
        for(i=0;i<256;i++)
        {
            k= (int) keys[index1];
            index2 =(k+state[i]+index2)%256;
            Intercambiar(i,index2);
            index1 =(index1+1)%key.length();
        }
        char mensajes[]=mensaje.toCharArray();
        for(int i=0;i<mensaje.length();i++)
        {
            x=(x+1)%256;
            y= (state[x]+y)%256;
            Intercambiar(x,y);
//            k= key.charAt(i % key.length());
            k=(int) mensajes[i];
            Nmen = k^(state[(state[x]+state[y])%256]);
//            mensajeCifrado = mensajeCifrado+"-"+decToHex(Nmen);
            mensajeCifrado = mensajeCifrado+"-"+getRellenarCeros(Integer.toHexString(Nmen).toUpperCase());
            
        }
        mensajeCifrado = mensajeCifrado.substring(1, mensajeCifrado.length());
        return this.mensajeCifrado;
    }
    
    private String getRellenarCeros(String hexadecimal)
    {
        String nuevoHexadecimal;
        if( hexadecimal.length() ==1)
        {
            nuevoHexadecimal = "0"+hexadecimal;
        }
        else
        {
            nuevoHexadecimal = hexadecimal;
        }
        return nuevoHexadecimal;
    }
}
