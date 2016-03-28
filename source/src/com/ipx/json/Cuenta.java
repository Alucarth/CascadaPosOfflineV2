/*
 *  Esta clase trata una respuesta json con la siguiente estructura:
 * {
    "clientes": [{
        "id": 1,
        "name": "Pollos Copacabana",
        "nit": "987654321"
    }, {
        "id": 2,
        "name": "APLP",
        "nit": "123456798"
    }, {
        "id": 3,
        "name": "Coca Cola",
        "nit": "2308379"
    }],
    "productos": [{
        "id": 1,
        "notes": "Servicios",
        "cost": "253.4000"
    }, {
        "id": 2,
        "notes": "consumo",
        "cost": "300.0000"
    }]
}
* se quito los clientes de la respuesta debido a excesivo trafico de datos
 */
package com.ipx.json;

import com.ipx.util.Log;
import java.util.Vector;
import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 *
 * @author David Torrez
 */
public class Cuenta 
{
    private Vector productos;
    private String jsonText;
    private String ice;
    private Vector clientes;
    private Sucursal sucursal;
    private String productosJson;
    public static String TAG  ="cuenta";
   
    public Cuenta(){}
    public Cuenta(String jsonText)
    {
         Log.i(TAG, "entrando respuesta");
       //this.jsonText = jsonText;
        try {
            
           JSONObject json = new JSONObject(jsonText);
           Log.i(TAG, "terminio de parsear");
           if(json.has("productos"))
           {
//               this.productosJson = json.getString("productos");
               this.productos = Products.fromJsonArray(json.getString("productos"));
               Log.i(TAG, "tiene productos size ");
//               Log.i(TAG, "(\"\",parseado productos size"+this.productos.size());
           }
           if(json.has("ice"))
           {
               this.ice = json.getString("ice");
           }
//           if(json.has("clientes"))
//           {
//               this.clientes = Clients.fromJsonArray(json.getString("clientes"));
//               Log.i(TAG, "tiene clientes size");
//           }
           if(json.has("sucursal"))
           {
               this.sucursal = new Sucursal(json.getString("sucursal"));
               Log.i(TAG, "tiene  sucursal ");
           }
        } catch (JSONException ex) {
            ex.printStackTrace();
        }   
    }
    public Vector getProductos()
    {
        return this.productos;
    }
    public String getIce()
    {
        return this.ice;
    }
    public Vector getClientes()
    {
        return this.clientes;
    }
    public Sucursal getSucursal()
    {
        return this.sucursal;
    }

    public String getProductosJson() {
        return this.productosJson;
    }
    
//    public void run()
//    {
//         try {
//            JSONObject json = new JSONObject(jsonText);
//           if(json.has("productos"))
//           {
//               this.productos = Products.fromJsonArray(json.getString("productos"));
//           }
////           if(json.has("clientes"))
////           {
////               this.clientes = Clients.fromJsonArray(json.getString("clientes"));
////           }
//        } catch (JSONException ex) {
//            ex.printStackTrace();
//        }
//    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public void setProductos(Vector productos) {
        this.productos = productos;
    }

    public void setIce(String ice) {
        this.ice = ice;
    }
    
}
