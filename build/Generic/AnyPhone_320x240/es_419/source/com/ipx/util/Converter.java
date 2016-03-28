/*
 * Nota: no me Hago responsable de su correcto funcionamiento si el codigo fuente es modificado 
 */
package com.ipx.util;

import java.util.Vector;

/**
 *
 * @author David Torrez Salinas
 */
public class Converter {
    
  
    public String getStringOfNumber(String numero)
    {
         String vec[] =Split(numero,".");
        
//        int num = (int) (numero); 
//        System.out.print("num ="+num); 
//        float dec = (float)(numero-num);
//        int s = (int)(dec*100);
//        String c = ""+s;
//        if(s==0)
//        {
//            c="0"+c;
//        }
         if(vec[1].length()<2)
         {
             vec[1]=vec[1]+"0";
         }
        return Literal(Integer.parseInt(vec[0])).toLowerCase()+ " con "+vec[1]+"/100 Bolivianos";
    }
    
    private String Literal(int contador){
        //Limite
        if(contador >2000000)
            return "DOS MILLONES";
        
        switch(contador){
            case 0: return "CERO";
            case 1: return "UN"; //UNO
            case 2: return "DOS";
            case 3: return "TRES";
            case 4: return "CUATRO";
            case 5: return "CINCO"; 
            case 6: return "SEIS";
            case 7: return "SIETE";
            case 8: return "OCHO";
            case 9: return "NUEVE";
            case 10: return "DIEZ";
            case 11: return "ONCE"; 
            case 12: return "DOCE"; 
            case 13: return "TRECE";
            case 14: return "CATORCE";
            case 15: return "QUINCE";
            case 20: return "VEINTE";
            case 30: return "TREINTA";
            case 40: return "CUARENTA";
            case 50: return "CINCUENTA";
            case 60: return "SESENTA";
            case 70: return "SETENTA";
            case 80: return "OCHENTA";
            case 90: return "NOVENTA";
            case 100: return "CIEN";
            
            case 200: return "DOSCIENTOS";
            case 300: return "TRESCIENTOS";
            case 400: return "CUATROCIENTOS";
            case 500: return "QUINIENTOS";
            case 600: return "SEISCIENTOS";
            case 700: return "SETECIENTOS";
            case 800: return "OCHOCIENTOS";
            case 900: return "NOVECIENTOS";
            
            case 1000: return "MIL";
            
            case 1000000: return "UN MILLON";
            case 2000000: return "DOS MILLONES";
        }
        if(contador<20){
            //System.out.println(">15");
            return "DIECI"+ Literal(contador-10);
        }
        if(contador<30){
            //System.out.println(">20");
            return "VEINTI" + Literal(contador-20);
        }
        if(contador<100){
            //System.out.println("<100"); 
            return Literal( (int)(contador/10)*10 ) + " Y " + Literal(contador%10);
        }        
        if(contador<200){
            //System.out.println("<200"); 
            return "CIENTO " + Literal( contador - 100 );
        }         
        if(contador<1000){
            //System.out.println("<1000");
            return Literal( (int)(contador/100)*100 ) + " " + Literal(contador%100);
        } 
        if(contador<2000){
            //System.out.println("<2000");
            return "MIL " + Literal( contador % 1000 );
        } 
        if(contador<1000000){
            String var="";
            //System.out.println("<1000000");
            var = Literal((int)(contador/1000)) + " MIL" ;
            if(contador % 1000!=0){
                //System.out.println(var);
                var += " " + Literal(contador % 1000);
            }
            return var;
        }
        if(contador<2000000){
            return "UN MILLON " + Literal( contador % 1000000 );
        } 
        return "";
    }    
    public static String[] Split(String splitStr, String delimiter) {
     StringBuffer token = new StringBuffer();
     Vector tokens = new Vector();
     // split
     char[] chars = splitStr.toCharArray();
     for (int i=0; i < chars.length; i++) {
         if (delimiter.indexOf(chars[i]) != -1) {
             // we bumbed into a delimiter
             if (token.length() > 0) {
                 tokens.addElement(token.toString());
                 token.setLength(0);
             }
         } else {
             token.append(chars[i]);
         }
     }
     // don't forget the "tail"...
     if (token.length() > 0) {
         tokens.addElement(token.toString());
     }
     // convert the vector into an array
     String[] splitArray = new String[tokens.size()];
     for (int i=0; i < splitArray.length; i++) {
         splitArray[i] = (String)tokens.elementAt(i);
     }
     return splitArray;
 }
}
