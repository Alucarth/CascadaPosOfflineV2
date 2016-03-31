/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ipx.http;

import com.ipx.util.Base64;
import com.ipx.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

/**
 *
 * @author David
 */
public class ConexionIpx extends Thread
{
//    private final String SERVIDOR="sigcfactu.com.bo";
//    public static String SERVIDOR="162.242.239.72";
    public static final String SERVIDOR="cascada.emizor.com";
   
    public static final String PROTOCOLO="http://";

    
    public static final String URL_AUTENTIFICACION = "/pos";
    public static final String URL_LOGOUT ="/logoutPOS";
//    private final String URL_AUTENTIFICACION="http://nofunciona.org.zs";
    public static final String URL_CLIENTE="/clientesPOS";
    
//    private final String URL_CLIENTES="http://192.168.1.10/cascada3/public/clientes/";
//    public static final String URL_GUARDARFACTURA="/guardarFacturaOffline";
     public static final String URL_GUARDARFACTURA="/pos";
    
    public static final String URL_VERSION="/version";
    public static final String URL_REGISTRARCLIENTE="/registrarCliente";
    public static final String URL_FACTURAS ="/facturas";
    public static final String URL_PRINTFACTURA = "/printFactura/";
    public static final String URL_GETFACTURA = "/obtenerFactura/";
      
   public static final int AUTENTIFICAZION=0;
   public static final int CLIENTE=1;
   public static final int GUARDARFACTURA=2;
   public static final int VERSION=3;
   public static final int REGISTRARCLIENTE=5;
   public static final int FACTURAS=6;
   public static final int PRINTFACTURA=7;
   public static final int GETFACTURA=8;
 
    
    private String Respuesta=null;
    protected int respCode=0;
    private int id;
    private String parametros;


    public String llave;

    private Thread t;
    public static final String TAG= "Conexion";
    public ConexionIpx()
    {
    }

   
    public void EnviarGet(int id, String parametros,String parametrosAutentificacion)
    {
       this.id = id;
       this.parametros = parametros;
       this.llave = parametrosAutentificacion;
//       gauge=false;
    }
    public void EnviarGet(int id, String parametros,String parametrosAutentificacion,Thread t)
    {
       this.id = id;
       this.parametros = parametros;
       this.llave = parametrosAutentificacion;
       this.t = t;

    }

     public void EnviarPost(int id,String parametros, String parametrosAutentificacion,Thread t)
    {
        this.id = id;
        this.parametros = parametros;
        this.llave = parametrosAutentificacion;
        this.t = t;

    }
    
    public  void run()
    {
               
                Enviar();
                t.start();
        
               
    }  
  
    private  void Enviar()
    {
        Respuesta = "vacio";
        String url="";
        try{
           
             switch(id)
            {
                case -1:
                     url=PROTOCOLO+SERVIDOR+URL_LOGOUT;
                     EnviarRestGet(url);
                    break;
                case AUTENTIFICAZION:
                    try{
                    url =PROTOCOLO+SERVIDOR+URL_AUTENTIFICACION;
                    EnviarRestGet(url+this.parametros);
               
                     

                    }catch(IOException e){
                        System.out.println("Error al tratar  recoger los datos ");
                    }
                    break;
                case CLIENTE:
                    url = PROTOCOLO+SERVIDOR+URL_CLIENTE;
                    EnviarRestGet(url);
                    break;
                case GUARDARFACTURA:
                    url = PROTOCOLO+SERVIDOR+URL_GUARDARFACTURA;
                    EnviarRestPost(url,this.parametros);
                    break;
                case VERSION:
                    url=PROTOCOLO+SERVIDOR+URL_VERSION;
                    EnviarRestGet(url+this.parametros);
                    break;
                case REGISTRARCLIENTE:
                    url = PROTOCOLO+SERVIDOR+URL_REGISTRARCLIENTE;
                    EnviarRestPost(url,parametros);
                    break;
                case FACTURAS:
                    url = PROTOCOLO+SERVIDOR+URL_FACTURAS;
                    EnviarRestGet(url);
                    break;
                case PRINTFACTURA:
                    url = PROTOCOLO+SERVIDOR+URL_PRINTFACTURA;
                    EnviarRestGet(url+this.parametros);
                    break;
                case GETFACTURA:
                    url =PROTOCOLO+SERVIDOR+ URL_GETFACTURA;
                    EnviarRestGet(url+this.parametros);
                    break;
                    
            }
  

        }catch(IOException e)
        {
            e.printStackTrace();

        }
    }
    public void EnviarRestGet(String url) throws IOException
    {

        HttpConnection httpConn = null;
        InputStream is = null;
        try {


          httpConn = (HttpConnection)Connector.open(url);

          // Setup HTTP Request
          httpConn.setRequestMethod(HttpConnection.GET);
          httpConn.setRequestProperty("Authorization",getClave());
          httpConn.setRequestProperty("User-Agent","Profile/MIDP-1.0 Confirguration/CLDC-1.0");
          httpConn.setRequestProperty("Accept_Language","en-US");
      
     
          httpConn.setRequestProperty("Content-Type", "application/json");

          // This function retrieves the information of this connection

          /** Initiate connection and check for the response code. If the
            response code is HTTP_OK then get the content from the target
          **/
           this.respCode = httpConn.getResponseCode();
           Log.i(TAG, "respCode: "+this.respCode);
           System.out.println("\nCodigo de Respues ");
          if (respCode == httpConn.HTTP_OK) {// si se envio correctamente los parametros y la direccion, el servidor responde un codigo 200
           


            is = httpConn.openDataInputStream();
            int chr;
            String cadena="";
            while ((chr = is.read()) != -1)
            {
                cadena=cadena+""+(char) chr;
            }
              
            
             this.Respuesta = Conexion.convertiraISO(cadena);
             Log.i(TAG, this.Respuesta);
    
      }
      else {
        System.out.println("Error in opening HTTP Connection. Error#" + respCode);
      }

      } finally {
        if(is!= null)
           is.close();
//          if(os != null)
//            os.close();
      if(httpConn != null)
            httpConn.close();
    }
    
    }
    public String getClave()
    {
        String llave_base64 = "Basic " + Base64.encode(this.llave);
        Log.i("Conexion ", "llave "+this.llave);
//        Log.w("Conexion", "llave "+this.llave);
        return llave_base64;
    }
    public void EnviarRestPost(String url,String parametros)  throws IOException
    {
        HttpConnection httpConn = null;

        InputStream is = null;
        OutputStream os = null;
//        String authorizationHeader= "Basic " + Base64.encode(rest.getLlave());
//        String authorizationHeader= "Basic " + Base64.encode(llave);
    try {
      // Open an HTTP Connection object
      httpConn = (HttpConnection)Connector.open(url);
      // Setup HTTP Request to POST
      httpConn.setRequestMethod(HttpConnection.POST);
      httpConn.setRequestProperty("User-Agent","Profile/MIDP-1.0 Confirguration/CLDC-1.0");
      httpConn.setRequestProperty("Accept_Language","en-US");
      
      //Content-Type is must to pass parameters in POST Request
      httpConn.setRequestProperty("Authorization",getClave());
      httpConn.setRequestProperty("Content-Type", "application/json");

      os = httpConn.openOutputStream();

      String cadenaconvertida=convertiraUTF(parametros);
      os.write(cadenaconvertida.getBytes());
      os.flush();

      /**Caution: os.flush() is controversial. It may create unexpected behavior
            on certain mobile devices. Try it out for your mobile device **/

      //os.flush();

      // Read Response from the Server

      
      StringBuffer sb = new StringBuffer();
      is = httpConn.openDataInputStream();
      this.respCode = httpConn.getResponseCode();
      Log.i(TAG, "respCode: "+this.respCode);
      if (this.respCode == httpConn.HTTP_OK) {
                int chr;
           String cadena="";
            while ((chr = is.read()) != -1)
            {
                cadena=cadena+""+(char) chr;
            }

//      while ((chr = is.read()) != -1)
//        sb.append((char) chr);

      // Converitmos la respuesta de utf-8 a ISO-8859-1
      
      this.Respuesta = Conexion.convertiraISO(cadena);
       Log.i(TAG, this.Respuesta);
      
      }
      
      } finally {
        if(is!= null)
           
            is.close();
           
          if(os != null)
            os.close();
      if(httpConn != null)
            httpConn.close();
           
      }
    }

    
   
    public static String convertiraISO(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("ISO-8859-1"), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
    public static String convertiraUTF(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }
    
    public int getCodigoRespuesta()
    {
        
        return this.respCode;
        
    }
    public String getRespuesta()
    {
        return this.Respuesta;
    }
    /*
     *  Funion utilizada para uso de threads 
     */
    
//    public void setLlave(String llave)
//    {
//        this.llave = llave;
//    }
//    public String getLlave()
//    {
//        return this.llave; 
//    }
    private String getParametros()
    {
        return this.parametros;
    }
    public  static String getURL(int identificador)
    {
//          Respuesta = "vacio";
        String url="";
       
           
             switch(identificador)
            {
                case -1:
                     url=PROTOCOLO+SERVIDOR+URL_LOGOUT;
                
                    break;
                case AUTENTIFICAZION:
                    
                    url =PROTOCOLO+SERVIDOR+URL_AUTENTIFICACION;
                   
                    break;
                case CLIENTE:
                    url = PROTOCOLO+SERVIDOR+URL_CLIENTE;
                   
                    break;
                case GUARDARFACTURA:
                    url = PROTOCOLO+SERVIDOR+URL_GUARDARFACTURA;
                  
                    break;
                case VERSION:
                    url=PROTOCOLO+SERVIDOR+URL_VERSION;
                   
                    break;
                case REGISTRARCLIENTE:
                    url = PROTOCOLO+SERVIDOR+URL_REGISTRARCLIENTE;
                    
                    break;
                case FACTURAS:
                    url = PROTOCOLO+SERVIDOR+URL_FACTURAS;
                  
                    break;
                case PRINTFACTURA:
                    url = PROTOCOLO+SERVIDOR+URL_PRINTFACTURA;
                   
                    break;
                case GETFACTURA:
                    url =PROTOCOLO+SERVIDOR+ URL_GETFACTURA;
                    
                    break;
                    
            }
             return url;
    }
}
