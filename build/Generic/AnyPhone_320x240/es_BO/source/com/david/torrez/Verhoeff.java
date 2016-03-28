/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.david.torrez;

/**
 *
 * @author Ing.David Torrez Salinas
 */
public class Verhoeff {
 
//    private String cambiar(char caracterABuscar,char caracterAReemplazar,String cadena)
//    {
//        String nuevaCadena;
//        nuevaCadena = cadena.replace(caracterABuscar, caracterABuscar);
//        return nuevaCadena;
//    }
    public static String getInvierteNumero(String cadena)
    {
        String numeroInvertido="";
        char[] caracteres = cadena.toCharArray();
        for(int i=0;i<caracteres.length;i++)
        {
         numeroInvertido =caracteres[i]+numeroInvertido;
        }
        return numeroInvertido;
    }
    public static int getVerhoeff(String cifra)
    {
        int Mul[][]={{0,1,2,3,4,5,6,7,8,9}, 
                    {1,2,3,4,0,6,7,8,9,5}, 
                    {2,3,4,0,1,7,8,9,5,6}, 
                    {3,4,0,1,2,8,9,5,6,7}, 
                    {4,0,1,2,3,9,5,6,7,8}, 
                    {5,9,8,7,6,0,4,3,2,1}, 
                    {6,5,9,8,7,1,0,4,3,2}, 
                    {7,6,5,9,8,2,1,0,4,3}, 
                    {8,7,6,5,9,3,2,1,0,4}, 
                    {9,8,7,6,5,4,3,2,1,0}};
        int Per[][]={{0,1,2,3,4,5,6,7,8,9},
                    {1,5,7,6,2,8,3,0,9,4}, 
                    {5,8,0,3,7,9,6,1,4,2}, 
                    {8,9,1,6,0,4,3,5,2,7}, 
                    {9,4,5,3,1,2,6,8,7,0}, 
                    {4,2,8,6,5,7,3,9,0,1}, 
                    {2,7,9,3,8,0,6,4,1,5}, 
                    {7,0,4,6,9,1,3,2,5,8}};
        int Inv[]={0,4,3,2,1,5,6,7,8,9};
        int Check=0;
        String cifraInvertida=getInvierteNumero(cifra);
        char [] caracteresInvertidos=cifraInvertida.toCharArray();
        for(int i=0;i<cifraInvertida.length();i++)
        {
            Check=Mul[Check][Per[((i+1)%8)][Integer.parseInt(caracteresInvertidos[i]+"")]];
        }
        
        return Inv[Check];
    }
}
