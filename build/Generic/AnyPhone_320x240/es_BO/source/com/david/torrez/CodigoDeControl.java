/*
 * Esta clase fue creada con la finalidad de poder utilizarla para mis propios beneficios jeje XD 

 */
package com.david.torrez;

import com.ipx.util.Tokenizer;
import java.util.Vector;

/**
 *
 * @author Ing. David Torrez Salinas.
 */
public class CodigoDeControl {

    /**
     * @param nit
     * @param numFac
     * @param montoDecimal
     * @param numAuto
     * @param dosificacion
     * @param fecha
     * @return codigodecontrol
     */
//    private String numFac,nit,fecha;
    public  static String getCodigoDeControl(String nit,String numFac,String fecha,String montoDecimal,String numAuto,String dosificacion)
    {
//      
//        String montoEntero[] =Split(montoDecimal,".");
//        String monto = montoEntero[0];
        String codigodecontrol="";
        //redondear el monto a 50 centavos 
        float floatamount = Float.parseFloat(montoDecimal);
        Tokenizer tk = new Tokenizer(montoDecimal,".");
        String monto = ""+redondear(floatamount);
        System.out.println("*****************Parametros de Entrada**********************************");
//       	 $numAutho='29040011007';
//         $numInvoice='1503';
//         $nit='4189179011';
//          $date ='20070702';
//          $amount='2500';
//          $keyDosage='9rCB7Sv4X29d)5k7N%3ab89p-3(5[A';
        System.out.println("$numAutho='"+numAuto+"';");
        System.out.println("$numInvoice='"+numFac+"';");
        System.out.println("$nit='"+nit+"';");
        System.out.println("$date='"+fecha+"';");
        System.out.println("$amount='"+monto+"';");
        System.out.println("$keyDosage='"+dosificacion+"';");
        
        System.out.println("************************************************************************");
        numFac= generarDigitosVerhoeff(numFac,2);
        nit = generarDigitosVerhoeff(nit,2);
        fecha = generarDigitosVerhoeff(fecha,2);
        monto = generarDigitosVerhoeff(monto,2);
        System.out.println("numfac="+numFac);
        System.out.println("nit="+nit);
        System.out.println("fecha="+fecha);
        System.out.println("monto="+monto);
        long suma = Long.parseLong(numFac)+Long.parseLong(nit)+Long.parseLong(fecha)+Long.parseLong(monto);
        String s = ""+suma;
        s = generarDigitosVerhoeff(s,5);
        System.out.println(s);
        String digitosVerhoeff = s.substring(s.length()-5, s.length());
        System.out.println(digitosVerhoeff);
//        System.out.println(Verhoeff.getInvierteNumero(s.substring(s.length()-5, s.length())));
//        int cincodig = Integer.parseInt(Verhoeff.getInvierteNumero(s.substring(s.length()-5, s.length())));
        int cincodig = Integer.parseInt(digitosVerhoeff);
       
        String c1="",c2="",c3="",c4="",c5="";
        int d=0,p=0;
        int c=0;
        System.out.println("Dosificacion ="+dosificacion+" tamaño="+dosificacion.length());
        VectorDosi vdosi = new VectorDosi(dosificacion);
        
        char digitos[]=digitosVerhoeff.toCharArray();
        for(int i =0;i<digitos.length;i++)
        {
            d = Integer.parseInt(digitos[i]+"");
            d++;
            switch(i)
            {
                 case 0: 
                   c1 = numAuto+vdosi.getCadena(d);
                   System.out.println(i+"-"+c1+" d:"+d);
                        break;
               case 1: 
                   c2 = numFac+vdosi.getCadena(d);
                   System.out.println(i+"-"+c2+" d:"+d);
                        break;
               case 2: 
                    c3 = nit+vdosi.getCadena(d);
                   System.out.println(i+"-"+c3+" d:"+d);
                    break;
                   
               case 3:
                    c4 = fecha+vdosi.getCadena(d);
                   System.out.println(i+"-"+c4+" d:"+d);
                   break;
               case 4:
                   c5 = monto+vdosi.getCadena(d);
                   System.out.println(i+"-"+c5+" d:"+d);
                    break;
            }
           
        }
//        while(n>0)
//        {   
//           d= n %10;
//           d=d+1;
//           System.out.print("\n digitp "+d);
//           n=n/10;
//           
//           switch(c)
//           {
//               case 0: 
//                   c1=numAuto+dosificacion.substring(p, p-1+d);
//                   System.out.println(c1+" p="+p+" p+d="+(p-1+d));
//                        break;
//               case 1: 
//                   c2=numFac+dosificacion.substring(p, p-1+d);
//                   System.out.println(c2+" p="+p+" p+d="+(p-1+d));
//                        break;
//               case 2: 
//                   c3=nit+dosificacion.substring(p, p+d);
//                    System.out.println(c3+" p="+p+" p+d="+(p-1+d));
//                   break;
//                   
//               case 3:
//                   c4=fecha+dosificacion.substring(p, p+d);
//                   System.out.println(c4+" p="+p+" p+d="+(p-1+d));
//                   break;
//               case 4:
//                   c5=monto+dosificacion.substring(p,p+d);
//                    System.out.println(c5+" p="+p+" p+d="+(p-1+d));
//                   break;
//                               
//           }
//           p=p+d;
//           System.out.print("\n p="+p);
//           c++;
//        }
        String llaveCifrado=dosificacion+digitosVerhoeff;
//       dosificacion = dosificacion+digitosVerhoeff;
       String mensaje = c1+c2+c3+c4+c5;
       System.out.println("mensaje="+mensaje);
       System.out.println("LlaveCifrado="+llaveCifrado);
       RC4 r= new RC4(mensaje,llaveCifrado);
       String rc4=r.getRC4();
       System.out.println("rc4="+rc4);
       char cArray []= rc4.toCharArray();
       long st=0;
       int n1=0,n2=1,n3=2,n4=3,n5=4,sp1=0,sp2=0,sp3=0,sp4=0,sp5=0;
       for(int i=0;i<cArray.length;i++)
       {
           st=st+((int)cArray[i]);
           
           if(n1 ==i)
           {
               n1=n1+5;
               sp1=sp1+((int)cArray[i]);
           }
           if(n2==i)
           {
               n2=n2+5;
               sp2=sp2+((int)cArray[i]);
           }
           if(n3==i)
           {
               n3=n3+5;
               sp3=sp3+((int)cArray[i]);
           }
           if(n4==i)
           {
               n4=n4+5;
               sp4=sp4+((int)cArray[i]);
           }
           if(n5==i)
           {
               n5=n5+5;
               sp5=sp5+((int)cArray[i]);
           }
           
       }
       System.out.println("st="+st);
       System.out.println("sp1="+sp1);
       System.out.println("sp2="+sp2);
       System.out.println("sp3="+sp3);
       System.out.println("sp4="+sp4);
       System.out.println("sp5="+sp5);
       
       
       for(int i =0;i<digitos.length;i++)
        {
            d = Integer.parseInt(digitos[i]+"");
            d++;
           switch(i)
           {
               case 0: 
                   sp1=(int) (sp1*st)/d;
                        break;
               case 1: 
                   sp2=(int) (sp2*st)/d;
                        break;
               case 2: 
                   sp3=(int) (sp3*st)/d;
                   break;
                   
               case 3:
                   sp4=(int) (sp4*st)/d;
                   break;
               case 4:
                   sp5=(int) (sp5*st)/d;
                   break;
                               
           }
           
        }
       System.out.println("segundo proceso******************************");
        System.out.println("st="+st);
       System.out.println("sp1="+sp1);
       System.out.println("sp2="+sp2);
       System.out.println("sp3="+sp3);
       System.out.println("sp4="+sp4);
       System.out.println("sp5="+sp5);
//       n =cincodig;
//         d=0;
//         p=0;
//         int i=0;
//         i=1;
//       
//        while(n>0)
//        {   
//           d= n %10;
//           d=d+1;
//           System.out.print("\n digitp "+d);
//           n=n/10;
//           
//           switch(i)
//           {
//               case 1: 
//                   sp1=(int) (sp1*st)/d;
//                        break;
//               case 2: 
//                   sp2=(int) (sp2*st)/d;
//                        break;
//               case 3: 
//                   sp3=(int) (sp3*st)/d;
//                   break;
//                   
//               case 4:
//                   sp4=(int) (sp4*st)/d;
//                   break;
//               case 5:
//                   sp5=(int) (sp5*st)/d;
//                   break;
//                               
//           }
//           p=p+d;
//           System.out.print("\n p="+p);
//           i++;
//        }
        int total=sp1+sp2+sp3+sp4+sp5;
//        String msj=Base64.encode(total+"");
        String msj =""+getBase64(total);
        System.out.println("base 64="+msj);
        RC4 generadorContro= new RC4(msj,llaveCifrado);
//        return "numFac = "+numFac+" nit "+nit+" fecha "+fecha+" monto "+monto+" s="+s+" 5dig ="+cincodig+"\n"+
//                c1+" "+c2+" "+c3+" "+c4+" "+c5+" \n dosif= "+dosificacion +"\nmensaje ="+mensaje+"\nRC4: "+r.getRC4()+"\n Ascii st="+st 
//                +"\nsp1= "+sp1+" sp2="+sp2+" sp3= "+sp3+" sp4="+sp4+" sp5="+sp5+"\n total:"+total+" base64: "+msj+" \n**********Codigo de Control="+generadorContro.getMensajeCifrado();
       codigodecontrol=generadorContro.getMensajeCifrado();
       System.out.println("Codigo de Control:"+codigodecontrol);
        return codigodecontrol;
    }
    private static String generarDigitosVerhoeff(String num,int cantidad)
    {   
        String numero = num;
        int c = cantidad;
//        int c=cantidad;
        while(c >0)
        {
            numero = numero+ Verhoeff.getVerhoeff(numero);
            c--;
        }
        
        
        return numero;
    }
    public static String getBase64(int num)
    {       
        int numero =num;
//            
            String dic = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz+/";
            char dicionario[] =dic.toCharArray();
            int cociente =1,resto=0;
            String palabra="";
            while(cociente>0)
            {
             cociente = (int)(numero /64);
             System.out.print("\ncociente = "+cociente);
             resto = numero%64;
             System.out.print("\nresto = "+resto);
             System.out.print("\n dicionario letra:"+dicionario[resto]);
             palabra=dicionario[resto]+palabra;
             numero = cociente;
            }
          
             
            return palabra;
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
//    public static void main(String[] args) {
//        // TODO code application logic here
//        RC4 r = new RC4("d3Ir6","sesamo");
//        System.out.print("Resultado: "+r.getMensajeCifrado());
//         Verhoeff v = new Verhoeff();
//        System.out.print("\n Verhoeff de  "+v.getVerhoeff("41891790115"));
//        System.out.print("\n fecha Actual "+DateUtil.getFechaActual()+" fecha codigo"+DateUtil.getCodigoControFecha());
//        System.out.print("\n Codigod  de control "+CodigoDeControl.getCodigoDeControl("1904008691195", "978256", "0", "20080201", "26006","pPgiFS%)v}@N4W3aQqqXCEHVS2[aDw_n%3)pFyU%bEB9)YXt%xNBub4@PZ4S9)ct"));
//        
//    }
     
     public static int redondear( float val )
    {
        return (int) Math.floor( val + ( float )0.5 );
    }
}
