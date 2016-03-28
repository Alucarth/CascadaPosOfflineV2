/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipx.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.io.Connector;
import javax.microedition.io.OutputConnection;
import javax.microedition.rms.RecordStore;

/**
 *
 * @author David Torrez
 */
public class Log {
  
     private RecordStore recordstore=null;
    public static void i(final String titulo, final String mensaje) {
 
    System.out.println("\n"+titulo+": "+mensaje);

    }
//     public static void w(final String titulo, final String mensaje){
//        
//         try {
//             
//             
//             try{
//             OutputConnection connection = (OutputConnection)
//                     Connector.open("file:/miFichero.txt;append=true", Connector.WRITE );
//             OutputStream out = connection.openOutputStream();
//             PrintStream output = new PrintStream( out );
//             output.println("\n"+titulo+": "+mensaje);
//            
//             out.close();
//             
//             connection.close();
//             }
//              catch( ConnectionNotFoundException error )
//             {
//             }
//           
////        recordstore = 
//         } catch (IOException ex) {
//             ex.printStackTrace();
//         }
//    }
}