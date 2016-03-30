/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ipx.util;

import com.google.zxing.qrcode.BmpArray;
import com.ipx.db.Usuario;
import com.ipx.json.Cuenta;
import com.ipx.json.Factura;
import com.ipx.json.InvoiceItem;
import com.mobiwire.print.DeviceOps;
import com.nbbse.printer.Printer;
import com.sagereal.utils.BMPGenerator;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Image;

/**
 *
 * @author David
 */
public class Imprimir// extends Thread
{
    
     Image image12;  
     private BmpArray ba;
    
     //para la cascada --------------------------------------------------------------------------------------------
 public void Imprimir(Factura factura, byte[] ImagenQr,Vector DetalleProductos,Vector vec,Cuenta cuenta,Usuario user)
    {
        
//     double monto = Double.parseDouble(factura.getAmount());
        //hacer todos los procesos antes de imprimir XD
        ba = new BmpArray();
        Converter conv= new Converter();
        Vector vnombre = TextLine("NOMBRE: "+factura.getCliente().getRazon(),36);
        Vector literal = TextLine("SON: "+conv.getStringOfNumber(factura.getAmount()),39);
        Vector vactividad = TextLine(cuenta.getSucursal().getActivity_pri(),40);
        Vector s = TextLine("\"ESTA FACTURA CONTRIBUYE AL DESARROLLO DEL PAIS, EL USO ILICITO DE ESTA SERA SANCIONADO DE ACUERDO A LEY\"",40);
        Vector vterceros = TextLine(cuenta.getSucursal().getName(),40);
        double sumatotales = 0;
        byte titulos[]= null;
        //algoritmo de impresion de invoice items
        try {
             titulos = ba.readImage(BMPGenerator.encodeBMP(getInvoiceItemTitulo("Cant.","Precio","Importe")));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
         byte printLine[] =null;
        try {
            printLine = ba.readImage(BMPGenerator.encodeBMP(getLinea()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Vector prods = new Vector();
        
         for(int i=0;i<factura.getInvoiceItems().size();i++)
                                    {   
                                        try {
                                            InvoiceItem invitem = (InvoiceItem) factura.getInvoiceItems().elementAt(i);
                                            
//                                        String concepto =(String) conceptos.elementAt(i);
//                                            imprimir.printText(invitem.getNotes(), 1);
                                            
//                                            double cant = Double.parseDouble(invitem.getQty());
//                                            double subTotal = (Double.parseDouble(invitem.getCost())*cant);
//                                            double costo =Double.parseDouble(invitem.getCost());
                                            double cant = Double.parseDouble(invitem.getQty()); 
                                            double costo = Double.parseDouble(invitem.getCost()); 
                                            double subTotal = costo*cant;
                                            
                                            
//                                            double c = Double.parseDouble(invitem.getQty());
                                            sumatotales = sumatotales+redondeo(subTotal,2);
                                            
//                                        ba = (byte[]) DetalleProductos.elementAt(i);
                                             //Todo: problemas con la converion numerica no imprime XD
                                            byte[] b = ba.readImage(BMPGenerator.encodeBMP(getInvoiceItem(""+redondeo(cant,2),""+redondeo(costo,2),""+redondeo(subTotal,2))));
                                            prods.addElement(b);
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }

        }
        
        
        byte Vs[] = null;
        byte Vvec[] = null;
        try {
            Vs = this.ba.readImage(BMPGenerator.encodeBMP(getLeyenda(s)));
            Vvec = this.ba.readImage(BMPGenerator.encodeBMP(getLeyenda(vec)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
                   Printer imprimir = Printer.getInstance();
                    switch (imprimir.getPaperStatus()) // check paper status
			{
			case Printer.PRINTER_EXIST_PAPER:
				if (imprimir.voltageCheck()) // check voltage, if it is allowed to
											// print
				{
                                    //Imprimiendo Factura
                                    DeviceOps deviceOps = DeviceOps.getInstance();
//                                    imprimir.printBitmap(deviceOps.readImage("/FAC_tigo2.bmp", 0));
//                                    //imprimir.printBitmap(deviceOps.readImage("/viva.bmp", 0));
                                    //Encabezado 
                                    if(cuenta.getSucursal().getTerceros().equals("1")){
                                    for(int j=0;j<vterceros.size();j++)
                                    {
                                         String linea = (String) vterceros.elementAt(j);
                                        imprimir.printText(linea, 1);
                                    }
                                    }
                                    imprimir.printBitmap(deviceOps.readImage("/linea.bmp", 0));
                                    imprimir.printTextWidthHeightZoom(ConstruirFilaA("La Cascada S.A"), 2, 1);
//                                    imprimir.printTextWidthHeightZoom(ConstruirFilaA(), 2, 1);
//                                    imprimir.printText(ConstruirFila(factura.getAccount().getName()), 1);
                                    imprimir.printText(ConstruirFila(factura.getAddress1()), 1);
                                    imprimir.printText(ConstruirFila(factura.getAddress2()), 1);
//                                    imprimir.printText(ConstruirFila("SFC-001"), 1);
                                    if(cuenta.getSucursal().getTerceros().equals("1")){ 
                                        imprimir.printText("            FACTURA POR TERCEROS", 1); 
                                    } 
                                    else { 
                                        if(cuenta.getSucursal().getTerceros().equals("2")){ 
                                            imprimir.printText("                       FACTURA", 1);
                                        } 
                                    } // else{ // imprimir.printText(" FACTURA SHIT", 1); // } }
                                    //imprimir.printText("            FACTURA POR TERCEROS", 1);
                                    imprimir.printBitmap(deviceOps.readImage("/linea.bmp", 0));
                                    //Datos de la Empresa
                                    imprimir.printText(ConstruirFila("NIT: "+factura.getAccount().getNit()), 1);
                                    imprimir.printText(ConstruirFila("No. FACTURA "+factura.getInvoiceNumber()), 1);
                                    imprimir.printText(" No. AUTORIZACION "+factura.getNumAuto(), 1);
                                    imprimir.printBitmap(deviceOps.readImage("/linea.bmp", 0));

                                     for(int j=0;j<vactividad.size();j++)
                                    {
                                        String linea = (String) vactividad.elementAt(j);
                                        imprimir.printText(linea, 1);
                                    }
                                    imprimir.printText("FECHA: "+factura.getInvoiceDate()+"         Hora: "+com.sagereal.utils.DateUtil.dateToString1(), 1);
                                    imprimir.printText("NIT/CI: "+factura.getCliente().getNit()+"        COD.:"+factura.getCliente().getId(), 1);
//                                    imprimir.printText("NOMBRE: "+factura.getCliente().getName(), 1);
                                    for(int j=0;j<vnombre.size();j++)
                                    {
                                        String linea = (String) vnombre.elementAt(j);
                                        imprimir.printText(linea, 1);
                                    }
                                        
                                 
                                    
                                    //productos impresion por texto
                                    imprimir.printBitmap(titulos);
                                    for(int i=0;i<factura.getInvoiceItems().size();i++)
                                    {   
                                        InvoiceItem invitem = (InvoiceItem) factura.getInvoiceItems().elementAt(i);
                                        
                                        Vector vl = TextLine(invitem.getNotes(),36);
                                        for(int y = 0;y<vl.size();y++)
                                        {
                                            String l = (String) vl.elementAt(y);
                                            imprimir.printText(l, 1);
                                        }
//                                        imprimir.printText(invitem.getNotes(), 1);
                                        byte  b[] = (byte[]) prods.elementAt(i);
                                        imprimir.printBitmap(b);

                                    }
                                    
                                    imprimir.printBitmap(printLine);

                                    imprimir.printText("                          TOTAL: Bs "+factura.getSubtotal(), 1);                                 

                                    double descuento = Double.parseDouble(factura.getSubtotal())-Double.parseDouble(factura.getAmount());
//                                    double descuento = sumatotales-Double.parseDouble(factura.getAmount());
                                    imprimir.printText("ICE: "+factura.getIce(), 1);
                                    
                                    imprimir.printText("DESCUENTOS/BONIFICACION: "+redondeo(descuento,2)+"", 1);
                                  
                                    imprimir.printText("IMPORTE BASE CREDITO FISCAL: "+factura.getFiscal(),1);
                                    imprimir.printText("MONTO A PAGAR: Bs "+factura.getAmount(),1);
                                     

                        
                                    for(int j=0;j<literal.size();j++)
                                    {
                                        String linea = (String) literal.elementAt(j);
                                        imprimir.printText(linea, 1);
                                    }
                                    imprimir.printBitmap(deviceOps.readImage("/linea.bmp", 0));
                                    //imprimir impuesto ic 
                                   // imprimir.printText()
                                    
                                    imprimir.printText("CODIGO DE CONTROL: "+factura.getControlCode(),1);
                                    imprimir.printText("FECHA LIMITE EMISION: "+factura.getFechaLimite(),1);
                                    imprimir.printText("USUARIO: "+user.getUsuario(), 1);
                                    
                                  
                                    try { 
 
                                        imprimir.printBitmap(ImagenQr);
                                       

                                    } catch (Exception ex) {
                                       
                                     
                                    }
//                                  BmpArray b = new BmpArray();
//                                      Vector leyenda= TextLine("'ESTA FACTURA CONTRIBUYE AL DESARROLLO DEL PAIS, EL USO ILICITO DE ESTA SERA SANCIONADO DE ACUERDO A LEY'");
                                      imprimir.printBitmap(deviceOps.readImage("/leyenda_generica.bmp", 0));
//                                     try{
//                                         imprimir.printBitmap(Vs);
//                                         
//                                     }catch(Exception e){}

//                                      Vector vec = TextLine(factura.getLaw());
//                                       BmpArray b2 = new BmpArray(this);
                                     
                                    try {
                                        imprimir.printBitmap(Vvec);
                           //                                     imprimir.printBitmap(b.readImage(BMPGenerator.encodeBMP(getLeyenda(vec))),0);
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
//                                     imprimir.printBitmap(deviceOps.readImage("/linea.bmp", 0));
//                                     imprimir.printBitmap(deviceOps.readImage("/logo_pie.bmp", 0));
                                     imprimir.printText(ConstruirFila("www.emizor.com"), 1);
                                     imprimir.printEndLine();
                                    
                                    
                                    
                                    
				} else {
//					tickerLogin.setText("Bateria baja!! ");
				
				}
				break;
			case Printer.PRINTER_NO_PAPER:
//                                tickerLogin.setText("Verifique el estado del papel!! ");
				break;
			case Printer.PRINTER_PAPER_ERROR:
//                                tickerLogin.setText("Error de impresión!! ");
				break;
			}
                   sumatotales=0;
    }
     
     
     
     private double redondeo(double num,int numDecim){
        long p=1;
        for(int i=0; i<numDecim; i++)p*=10;
        return (double)(int)(p * num + 0.5) / p;
    }
     public Vector  TextLine(String texto)
    {
       String vec[] =Split(texto," "); 
//            imprimir.printText("hola mundo vector"+vec.length, 1);
            String linea="";
            String p;
            Vector v = new Vector();
            boolean sw=false;
            for(int i=0;i<vec.length;i++)
            {
                
               p = vec[i]+" ";
               
               if((p.length()+linea.length())<60)
               {
                   linea = linea +p;
                   sw =false;
               }
               else{
                   sw = true;
               }
               if(sw)
               {
//                   imprimir.printText(linea, 1);
                   v.addElement(linea);
                   linea =p;
               }
               
//               imprimir.printText(vec[i], 1);
            }
            if(linea.length()>0)
            {
//               imprimir.printText(linea, 1);
               v.addElement(linea);
            }
//       
        return v;
    }
    public Vector  TextLine(String texto,int caracteres)
    {
       String vec[] =Split(texto," "); 
//            imprimir.printText("hola mundo vector"+vec.length, 1);
            String linea="";
            String p;
            Vector v = new Vector();
            boolean sw=false;
            for(int i=0;i<vec.length;i++)
            {
//               linea = (String) v.elementAt(i);
               p = vec[i]+" ";
               //60 es el valor maximo que se deberia imprimir para las letras tipo Small
               
               if((p.length()+linea.length())<caracteres)
               {
                   linea = linea +p;
                   sw =false;
               }
               else{
                   sw = true;
               }
               if(sw)
               {
//                   imprimir.printText(linea, 1);
                   v.addElement(linea);
                   linea =p;
               }
               
//               imprimir.printText(vec[i], 1);
            }
            if(linea.length()>0)
            {
//               imprimir.printText(linea, 1);
               v.addElement(linea);
            }
//       
        return v;
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
    private Image getInvoiceItemTitulo(String cantidad,String pu,String total)
     {
         int lineNumber = 18+15;
         
         
         //posible error al crear la imagen en el alto esto debido a que no se realmente cual es el parametro  que se tiene qu dimenciona
         javax.microedition.lcdui.Image temp = javax.microedition.lcdui.Image.createImage(getImage12().getWidth(),lineNumber);
         javax.microedition.lcdui.Graphics g = temp.getGraphics();
         g.setColor(0x000000);
         javax.microedition.lcdui.Font myFont;
         myFont = javax.microedition.lcdui.Font.getFont(javax.microedition.lcdui.Font.FACE_SYSTEM,javax.microedition.lcdui.Font.STYLE_BOLD | javax.microedition.lcdui.Font.STYLE_BOLD, javax.microedition.lcdui.Font.SIZE_MEDIUM);
//        Font fontPolish = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);
         g.setFont(myFont);
         
         int c1,c2,c3;
         
          int x=0;
         
         g.drawString(cantidad, x, 5, 0);
         
         x=x+152;
//         c1=x;
//         g.drawString(concepto, x, 0, 0);
//         x=x+184;
         c2 = x;
         g.drawString(pu,x,5,0);
         x=x+100;
         c3 = x;
         g.drawString(total, x, 5, 0);
        
         g.drawLine(0,25, getImage12().getWidth(), 25);
         g.drawLine(0,26, getImage12().getWidth(), 26);
//         g.drawLine(0,25, getImage12().getWidth(), 27);
         g.drawLine(0,1, getImage12().getWidth(), 1); 
         g.drawLine(0,2, getImage12().getWidth(), 2);
         
//         g.drawLine(c1-3,0,c1-3,lineNumber);
//         g.drawLine(c2-3,0,c2-3,lineNumber);
//         g.drawLine(c3-3,0,c3-3,lineNumber);

         return temp;
     }
    
     private Image getLinea()
     {
         javax.microedition.lcdui.Image temp = javax.microedition.lcdui.Image.createImage(getImage12().getWidth(),5);
         javax.microedition.lcdui.Graphics g = temp.getGraphics();
         g.setColor(0x000000);
         javax.microedition.lcdui.Font myFont;
         myFont = javax.microedition.lcdui.Font.getFont(javax.microedition.lcdui.Font.FACE_SYSTEM,javax.microedition.lcdui.Font.STYLE_BOLD | javax.microedition.lcdui.Font.STYLE_BOLD, javax.microedition.lcdui.Font.SIZE_MEDIUM);
//        Font fontPolish = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);
         g.setFont(myFont);
         
         g.drawLine(0,3, getImage12().getWidth(), 3);
         g.drawLine(0,4, getImage12().getWidth(), 4);
         
         return temp;
     }
    public Image getImage12() {
        if (image12 == null) {
                                     
 // write pre-init user code here
try {                                                    
                image12 = Image.createImage("/f3.png");
            } catch (java.io.IOException e) {                                                  
    e.printStackTrace();
            }                                       
 // write post-init user code here
}                           
        return image12;
    }

     /*
     * Parametros Cad1:  
     * Se contruye una fila con 32 caracteres y la variable Cad1 centrado
     * 
     */
    public String ConstruirFilaA(String cad1)
    {
        String fila=cad1;
        String espacio =" ";
        int size = (28-cad1.length())/2;
         for(int i=0;i<size;i++)
        {
            fila = espacio+fila ;
        }
                
        return fila;
    }
    public String ConstruirFila(String cad1)
    {
        String fila=cad1;
        String espacio =" ";
        int size = (56-cad1.length())/2;
         for(int i=0;i<size;i++)
        {
            fila = espacio+fila ;
        }
                
        return fila;
    }
    public String ConstruirFila(String cad1,int espacios)
    {
        String fila=cad1;
        String espacio =" ";
        int size = espacios-cad1.length();
         for(int i=0;i<size;i++)
        {
            fila = fila +espacio;
        }
                
        return fila;
    }
     public String ConstruirFila(String cad1,String cad2)
    {
        String fila=cad1;
        String espacio =" ";
        int size = 32- cad2.length()-cad1.length();
        for(int i=0;i<size;i++)
        {
            fila = fila +espacio;
        }
        fila = fila+cad2;
        
        return fila;
    }
     public String ConstruirFilaA(String cantidad,String concepto,String monto)
     {
         String linea="";
          String espacio =" ";
         linea = ""+cantidad+" ";
         Tokenizer tk = new Tokenizer(concepto," ");
         String con=tk.nextToken();
         int size=15-con.length();
         for(int i=0;i<size;i++)
         {
             con = con+ espacio;
         }
         linea = linea +con;
         size = 7-monto.length();
         String m=monto;
         for(int i=0;i<size;i++)
         {
             m = espacio+m;
         }
         linea = linea+m;
         size = 7-monto.length();
         String m2=monto;
         for(int i=0;i<size;i++)
         {
             m2 = espacio+ m2;
         }
         linea = linea+m2;
         return linea;
     }
     public String ConstruirFila(String cantidad,String concepto,String monto)
     {
         String linea=""+cantidad+" "+concepto;
         String espacio =" ";
                  
         int size=32-linea.length()-monto.length();
         for(int i=0;i<size;i++)
         {
             linea = linea+ espacio;
         }
         linea = linea +monto;
         
         return linea;
     }
     private Image getInvoiceItem(String cantidad,String pu,String total)
     {
         int lineNumber = 18+1;
         
         
         //posible error al crear la imagen en el alto esto debido a que no se realmente cual es el parametro  que se tiene qu dimenciona
         javax.microedition.lcdui.Image temp = javax.microedition.lcdui.Image.createImage(getImage12().getWidth(),lineNumber);
         javax.microedition.lcdui.Graphics g = temp.getGraphics();
         g.setColor(0x000000);
         javax.microedition.lcdui.Font myFont;
//         javax.microedition.lcdui.Font myFont1;
         myFont = javax.microedition.lcdui.Font.getFont(javax.microedition.lcdui.Font.FACE_SYSTEM,javax.microedition.lcdui.Font.STYLE_PLAIN | javax.microedition.lcdui.Font.STYLE_BOLD, javax.microedition.lcdui.Font.SIZE_MEDIUM);
//        Font fontPolish = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);
         g.setFont(myFont);
         int c1,c2,c3;
         int x=0;
         g.drawString(cantidad, x, 0, 0);
         x=x+152;
         c1 = x;
//          myFont1 = javax.microedition.lcdui.Font.getFont(javax.microedition.lcdui.Font.FACE_SYSTEM,javax.microedition.lcdui.Font.STYLE_PLAIN | javax.microedition.lcdui.Font.STYLE_BOLD, javax.microedition.lcdui.Font.SIZE_SMALL);
//        Font fontPolish = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);
//         g.setFont(myFont1);
//         g.drawString(concepto, x, 0, 0);
//         x=x+184;
         c2 = x;
         g.setFont(myFont);
         g.drawString(pu,x,0,0);
         x=x+100;
         c3 = x;
         g.drawString(total, x, 0, 0);
//         g.d
//         g.drawLine(c1-3,0,c1-3,lineNumber);
//         g.drawLine(c2-3,0,c2-3,lineNumber);
//         g.drawLine(c3-3,0,c3-3,lineNumber);
//         g.drawLine(c1-3, 0, c1-3,lineNumber );
//       para formtado de 20 caracteres  47,210,50
//         fomato optimo a 15 caracteres 52,184,59
         return temp;
     }
      private javax.microedition.lcdui.Image getLeyenda(Vector v)
     {
         int lineNumber = (v.size()*18)+1;
         
         
         //posible error al crear la imagen en el alto esto debido a que no se realmente cual es el parametro  que se tiene qu dimenciona
         javax.microedition.lcdui.Image temp = javax.microedition.lcdui.Image.createImage(getImage12().getWidth(),lineNumber);
         javax.microedition.lcdui.Graphics g = temp.getGraphics();
         g.setColor(0x000000);
         javax.microedition.lcdui.Font myFont;
         myFont = javax.microedition.lcdui.Font.getFont(javax.microedition.lcdui.Font.FACE_SYSTEM,javax.microedition.lcdui.Font.STYLE_PLAIN | javax.microedition.lcdui.Font.STYLE_BOLD, javax.microedition.lcdui.Font.SIZE_SMALL);
//        Font fontPolish = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);
         g.setFont(myFont);
//         g.drawString("01234567890123456789012345678901234567890123456789", 0, 0, 0);
         //para el sato de linea se utilizar un valor de 15
         int x=0;
         for(int i =0; i<v.size();i++)
         {
             String cadena = (String) v.elementAt(i);
             g.drawString(cadena, 0, x, 0);
             x= x+15;
         }
         return temp;
     }
//    public void run()
//    {
//    
//    }
}
