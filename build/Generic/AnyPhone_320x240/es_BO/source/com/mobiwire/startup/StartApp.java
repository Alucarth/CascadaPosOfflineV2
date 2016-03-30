package com.mobiwire.startup;



import com.david.torrez.CodigoDeControl;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.BmpArray;
import com.google.zxing.qrcode.QRCodeWriter;
import com.ipx.db.Usuario;
import com.ipx.http.BufferRest; 
import com.ipx.http.ConectorRest;
import com.ipx.http.ConexionIpx;
import com.ipx.http.RestConsumidor;
import com.ipx.http.RestProductor;
import com.ipx.http.switchDisplay;  
import com.ipx.json.Account;
import com.ipx.json.Cliente;
import com.ipx.json.Clients;
import com.ipx.json.Cuenta;
import com.ipx.json.Factura;
import com.ipx.json.FacturaOffline;
import com.ipx.json.InvoiceItem;
import com.ipx.json.Products;
import com.ipx.json.ResponseSave;
import com.ipx.json.SendInvoices;
import com.ipx.json.ServerTxt;
import com.ipx.json.Sucursal;
import com.ipx.json.facturaRespuesta;
import com.ipx.util.Converter;
import com.ipx.util.Log;
import com.ipx.util.Tokenizer;
import com.mobiwire.print.DeviceOps;
import com.nbbse.printer.Printer;
import com.sagereal.utils.BMPGenerator;
import com.sagereal.utils.DateUtil;
import de.enough.polish.io.RmsStorage;
import de.enough.polish.ui.*;

//import de.enough.polish.ui.TableItem;
import de.enough.polish.ui.SplashScreen;
import de.enough.polish.util.Arrays;
import de.enough.polish.util.TableData;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
import net.sf.microlog.core.Logger;
import net.sf.microlog.core.LoggerFactory;
import net.sf.microlog.core.PropertyConfigurator;
import org.json.me.JSONArray;
import org.json.me.JSONException;
import org.json.me.JSONObject;
import org.netbeans.microedition.util.SimpleCancellableTask;


public class StartApp extends MIDlet implements CommandListener {

    // logger instance for this class
    private static final Logger logme = LoggerFactory.getLogger(StartApp.class);
    //constantes para la comunicacion 
    private final int AUTENTIFICACION = 0;
    private final int CLIENTE=1;
    private final int GUARDARFACTURA=2;
    private final int VERSION=3;
    private final int CANTPROD=4;
    private final int REGISTRARCLIENTE=5;
    private final int FACTURAS=6;
    private final int PRINTFACTURA=7;
    private final int GETFACTURA =8;
    private final int SINCRONIZAR =10;
    private final int LISTMENU=11;
    private final int CLIENTES=12;
    private final int PRODNOTFOUND=9;
    
    private final int PAQUETE=1;
    private final int UNIDAD=2;
    private final int SINPRODUCTO=0;
        public static final String MIDLET_URL = "http://pos.sigcfactu.com.bo/offline/CascadaOfflinePOS.jad";
     private String version ="3.9.5";
    
    private boolean midletPaused = false;
    //variables de comunicacion
       
    private int puntero;
    private Cliente cliente;
    private Factura factura;
    //datos Cuenta
//    public String llave;
    private Cuenta cuenta;
    private Printer imprimir;
    private Vector listaProductos;

    private TableItem table;

    TableItem tp;
    int z=0;
   //variable de envio Rest

//    private Conexion conexion;
    private ConexionIpx conexion;
   
    // varibales para impresion
  

    //flags 
 

    public static int pantalla;
//    private Rest rest;
    private String mensaje;
    private String titulo;
    private boolean estaRegistrado=false;
    
    private Image qrCodeImage;
   
    private static final int BACK = 0xFF000000;
    private static final int WHTIE = 0xFFFFFFFF;
    
    /*variables de persistencia de datos***********************/
//    private final Vector notes;
    private final RmsStorage storage;
    private final Usuario user;
    private final Sucursal sucursal;
    private final Vector productos;
    private final Vector clientes;
    private final Vector facturas;
    private final Vector facturasbackup;
    private final ServerTxt serverInfo;
    /**********************************************************/
    private Vector clientesTemp;
    /*imagenes para la factura*/
//    private javax.microedition.lcdui.Image imgActividad;
    
    private TextField tnativo;
    public String clientId="-1";
//    private Canvas keyCanvas;
    
    //alert para la aplicacion
    public Alert  alerta;
    public String alertaTitulo="";
    public String alertaMensaje="";
    public int formulario;
    
    public Alert alertaConfirm ;
    public boolean swalert= false;
//
    private int punteroModificar=0;
    public Products productoTemporal;
    private StringItem str2;
    private byte[] leyenda;
    private byte[] actividad;
    public BmpArray ba;
    int index;
    Vector v;
    private Vector facturasEliminadas;
//    private Thread t;
    
    /// para el offline
    
    private Gauge gauge;
    int max = 100;
    int current = 1;
    boolean isInteractive = true;
    
    
    
    int  n =1;
    int punteroCliente = 0;
    int punteroFactura =0;
    //factura offline
    FacturaOffline of=null;
    ConectorRest conector;
     String cadena =""; 
      facturaRespuesta fr;
      private StringItem str1;
      boolean swpz=false;
      
      Form formActualizar;
      
      StringItem contenidoActualizar;
     
      Form formClientes;
      StringItem contenidoClientes;
      Command cmdClientes;
      
      //variable para imprimir los subtotales
      double sumatotales;
      //variables de comunicacion
      BufferRest br;
       RestProductor restProductor;
       RestConsumidor restConsumidor;
       
       //variable de manejo de tipo de producto
       int product_state=0;
      
      
//<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private Command okOpciones;
    private Command okCliente;
    private Command okLista;
    private Command backMenu;
    private Command okCommand;
    private Command okCommand2;
    private Command okCommand1;
    private Command ImprimirFactura;
    private Command okCommand8;
    private Command stopCommand;
    private Command backCommand2;
    private Command okCommand9;
    private Command screenCommand;
    private Command cancelCommand1;
    private Command cancelCommand2;
    private Command back;
    private Command okRegistrar;
    private Command backDatos;
    private Command okDatos;
    private Command okCommand10;
    private Command okMenu;
    private Command cancelCommand;
    private Command backSalir;
    private Command okSelOp;
    private Command backFactura;
    private Command backCliente;
    private Command okCommand4;
    private Command okCommand3;
    private Command backCommand;
    private Command okCommand5;
    private Command okCommand6;
    private Command backCommand1;
    private Command okCommand7;
    private Command okCommand21;
    private Command okCommand20;
    private Command okCommand19;
    private Command okCommand18;
    private Command okLogin;
    private Command stopCommand1;
    private Command backCommand6;
    private Command okCommand25;
    private Command backCommand5;
    private Command okCommand24;
    private Command okCommand23;
    private Command okCommand22;
    private Command exitCommand;
    private Command okCommand12;
    private Command exitCommand1;
    private Command okCommand13;
    private Command okCommand11;
    private Command okProducto;
    private Command okCommand16;
    private Command okCommand17;
    private Command backCommand4;
    private Command backCommand3;
    private Command okCommand15;
    private Command okCommand14;
    private Command backProducto;
    private Command backCommand8;
    private Command okUpdate;
    private Command okCommand28;
    private Command okCommand29;
    private Command okCommand26;
    private Command backCommand7;
    private Command okCommand27;
    private Command okCommand30;
    private Command okCommand31;
    private Command okCommand32;
    private Command exitCommand2;
    private Form formFactura;
    private StringItem strNomCli;
    private StringItem strNitCli;
    private Form formCliente;
    private Spacer spacer;
    private TextField txtNit;
    private SplashScreen splashScreen;
    private List listProductos;
    private Form formLoading;
    private Form formRegistro;
    private TextField txtEmailCli;
    private TextField txtTelCli;
    private TextField txtNomCli;
    private TextField txtNitCli;
    private Form formDatosCliente;
    private TextField txtNomDat;
    private TextField txtNitDat;
    private Form formVistaFactura;
    private StringItem strFactura;
    private List listMenu;
    private Alert Problemas;
    private List listPrincipal;
    private Form formRClient;
    private TextField txtCodCliente;
    private Form formLogin;
    private TextField TxtUsuario;
    private TextField TxtPassword;
    private ImageItem imageItem;
    private Form formSincronizacion;
    private StringItem strNumFacturas;
    private StringItem strNumClientes;
    private Form form1;
    private TextField textField;
    private StringItem txt;
    private Form form;
    private TextField txt2;
    private TextField txt1;
    private Form formCant;
    private TextField txtB;
    private TextField txtD;
    private StringItem stringItem4;
    private StringItem stringItem3;
    private TextField txtU;
    private TextField txtP;
    private Form formProd;
    private TextField txtProductKey;
    private List notesList;
    private Form informacion;
    private StringItem stringItem2;
    private StringItem stringItem5;
    private StringItem stringItem1;
    private StringItem stringItem6;
    private Form formLogout;
    private StringItem stringItem;
    private TextField textField1;
    private Image image;
    private Image image2;
    private Image image11;
    private Ticker tickerLogin;
    private Image image12;
    private Image image1;
    private Image image4;
    private Image image3;
    private Image image10;
    private Image image5;
    private Image image6;
    private Image image7;
    private Image image23;
    private Image image21;
    private Image image22;
    private Image image17;
    private Image image18;
    private Ticker ticker;
    private Image image19;
    private Image image20;
    private Image image14;
    private Image image15;
    private Image image16;
    private SimpleCancellableTask task1;
    private Image image9;
    private Image image13;
    private SimpleCancellableTask task;
    private Image image8;
    private SimpleCancellableTask task2;
    private Image image24;
    private Ticker ticker1;
    private Font font;
//</editor-fold>//GEN-END:|fields|0|

    //SMS ENVIO
    /*
     * The StartApp constructor.
     */
    public StartApp() {
        // configuration for the logger
       
        PropertyConfigurator.configure();
        logme.info("Midlet started.");
         listaProductos = new Vector();
          ba = new BmpArray();
//        rest = new Rest();
          index=-1;
    
        
        // loading store
           this.storage = new RmsStorage();
//		Loading User :)
                Usuario userSaved;
		try {
			userSaved = (Usuario) this.storage.read("usuario");
			Log.i("Base de datos"," usuario guardado "+userSaved.getUsuario());
                  
			
		} catch (IOException e) {
                 
                // storage does not yet exist
			userSaved = new Usuario();
                     Log.i("Base de datos"," usuario nuevo "+userSaved.getUsuario());
                        
		}
		this.user = userSaved;
                
                ServerTxt serverSaved;
                try {
			serverSaved = (ServerTxt) this.storage.read("servertxt");
			Log.i("Base de datos"," server txt "+serverSaved.getClientesTxt());
                        
			
		} catch (IOException e) {
                
                // storage does not yet exist
			serverSaved = new ServerTxt();
                     Log.i("Base de datos"," server txt vacio "+serverSaved.getClientesTxt());
                        
		}
		this.serverInfo = serverSaved;
               
                
//              Loading Branch
              
                Sucursal sucursalSaved;
                
                try{
                    sucursalSaved = (Sucursal) this.storage.read("sucursal");
                    Log.i("Base de datos"," sucursal "+sucursalSaved.getName());
                    
                }catch(IOException e)
                {
                    sucursalSaved = new Sucursal();
                     Log.i("Base de datos"," sucursal nueva "+sucursalSaved.getName());
                    
                    
                }
               
                sucursal = sucursalSaved;
              
//              Loading Products
                
                Vector productosSaved;
                
                try
                {
                    productosSaved=(Vector) this.storage.read("productos");
                    Log.i("Base de datos"," productos size "+productosSaved.size());
                }catch(IOException e)
                {
                    productosSaved = new Vector();
                }
                
                productos = productosSaved;
                
//                Loading Clientes
                Vector clientesSaved;
                
                try{
                    clientesSaved =(Vector) this.storage.read("clientes");
                    Log.i("Base de datos"," clientes size "+productosSaved.size());
                    
                }catch(IOException e){
                    clientesSaved = new Vector();
                     Log.i("Base de datos"," clientes nuevo size "+productosSaved.size());
                }
                
                clientes = clientesSaved;
                
                Vector facturasSaved;
                
                try{
                    facturasSaved = (Vector) this.storage.read("facturas");
                    Log.i("Base de datos"," facturas size "+facturasSaved.size());
                }catch(IOException e){
                    facturasSaved = new Vector();
                    Log.i("Base de datos"," facturas nuevas size "+facturasSaved.size());
                }
                
                facturas = facturasSaved;
                
                
                Vector facturasbackupSaved;
                try{
                    facturasbackupSaved = (Vector) this.storage.read("facturascopia");
                    Log.i("Base de datos"," facturascopia size "+facturasbackupSaved.size());
                }catch(IOException e){
                    facturasbackupSaved = new Vector();
                    Log.i("Base de datos"," facturas copia vacia  size "+facturasbackupSaved.size());
                }
                facturasbackup = facturasbackupSaved;
                
			
    }

//<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
//</editor-fold>//GEN-END:|methods|0|
//<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">//GEN-BEGIN:|0-initialize|0|0-preInitialize
    /**
     * Initializes the application. It is called only once when the MIDlet is
     * started. The method is called before the <code>startMIDlet</code> method.
     */
    private void initialize() {
//GEN-END:|0-initialize|0|0-preInitialize



        textField = new TextField("codigo de control generado", null, 32, TextField.ANY);//GEN-LINE:|0-initialize|1|0-postInitialize
      

//        gau = new Gauge(null, false, Gauge.INDEFINITE, Gauge.CONTINUOUS_RUNNING);
//        ScreenInfo.setItem(gau);
//        gau.setVisible(true);
//        ScreenInfo.setVisible(false);
       
        
//        conexion = new Conexion(rest);
//        conexion.start();
        
        
//        String version =conexion.getRespuesta();
}//GEN-BEGIN:|0-initialize|2|
//</editor-fold>//GEN-END:|0-initialize|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">//GEN-BEGIN:|3-startMIDlet|0|3-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {
//GEN-END:|3-startMIDlet|0|3-preAction
        // write pre-action user code here

        switchDisplayable(null, getSplashScreen());//GEN-LINE:|3-startMIDlet|1|3-postAction
        
    }//GEN-BEGIN:|3-startMIDlet|2|
//</editor-fold>//GEN-END:|3-startMIDlet|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">//GEN-BEGIN:|4-resumeMIDlet|0|4-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet() {
//GEN-END:|4-resumeMIDlet|0|4-preAction
        // write pre-action user code here
//GEN-LINE:|4-resumeMIDlet|1|4-postAction
        // write post-action user code here
}//GEN-BEGIN:|4-resumeMIDlet|2|
//</editor-fold>//GEN-END:|4-resumeMIDlet|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">//GEN-BEGIN:|5-switchDisplayable|0|5-preSwitch
    /**
     * Switches a current displayable in a display. The <code>display</code>
     * instance is taken from <code>getDisplay</code> method. This method is
     * used by all actions in the design for switching displayable.
     *
     * @param alert the Alert which is temporarily set to the display; if
     * <code>null</code>, then <code>nextDisplayable</code> is set immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {
//GEN-END:|5-switchDisplayable|0|5-preSwitch
        // write pre-switch user code here
        
        Display display = getDisplay();//GEN-BEGIN:|5-switchDisplayable|1|5-postSwitch
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            Alert.setCurrent( display, alert, nextDisplayable );
        }//GEN-END:|5-switchDisplayable|1|5-postSwitch
        // write post-switch user code here
//        keyCanvas = new Canvas(){
//
//            protected void paint(javax.microedition.lcdui.Graphics grphcs) {
//               
//            }
//            
//            protected void keyPressed(int keycode)
//            {
//                switch(keycode)
//                {
//                    case 102: getTextField1().setText("imprime");
//                     break;
//                    case 101: getTextField1().setText("atras");
//                     break;
//                    case 1028: getTextField1().setText("eliminar");
//                     break;
//                    case -5: getTextField1().setText("Enter");
//                        break;
//                }
//                
//                   
//            }
//            
//        }; 
//        display.setCurrent(keyCanvas);
        //  display.setCurrent(nextDisplayable);
}//GEN-BEGIN:|5-switchDisplayable|2|
//</editor-fold>//GEN-END:|5-switchDisplayable|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">//GEN-BEGIN:|7-commandAction|0|7-preCommandAction
    /**
     * Called by a system to indicated that a command has been invoked on a
     * particular displayable.
     *
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {
//GEN-END:|7-commandAction|0|7-preCommandAction
        // write pre-action user code here
if (displayable == form) {//GEN-BEGIN:|7-commandAction|1|1312-preAction
            if (command == exitCommand1) {//GEN-END:|7-commandAction|1|1312-preAction
                // write pre-action user code here
exitMIDlet();//GEN-LINE:|7-commandAction|2|1312-postAction
 // write post-action user code here
} else if (command == okCommand12) {//GEN-LINE:|7-commandAction|3|1308-preAction
 // guarda un objeto en persistencia de datos
//            notes.addElement(new Note(txt1.getText()));
//GEN-LINE:|7-commandAction|4|1308-postAction
 // write post-action user code here
} else if (command == okCommand13) {//GEN-LINE:|7-commandAction|5|1310-preAction
 // abre un objeto de la persistencia de datos
    
                switchDisplayable(null, getNotesList());//GEN-LINE:|7-commandAction|6|1310-postAction
 // write post-action user code here
}//GEN-BEGIN:|7-commandAction|7|1388-preAction
} else if (displayable == form1) {
    if (command == okCommand22) {//GEN-END:|7-commandAction|7|1388-preAction
 // write pre-action user code here
      
         textField.setText("Procesando :");
//        InputStream inputStreamTxt =null;
//        inputStreamTxt = this.getClass().getResourceAsStream("file:///Teléfono:/archivo.txt");
//        StringBuffer buf = new StringBuffer();
//        int c;
//        try {
//            while ((c = inputStreamTxt.read()) != -1)
//            {
//                char ch = (char)c;
//                if (ch == '\n') 
//                { 
////                    lines.addElement(buf.toString());
//                       
//                    textField.setText(textField.getString()+"{"+buf.toString()+"}");
//                    buf.delete(0,buf.length());
//                }
//                else
//                {
//                    buf.append(ch);
//                }
//                
//                
//            }
//        }catch(IOException e){
//            textField.setText("error "+e.getMessage());
//        }
            
//abriendo archivo
            
        InputStream is = null;
        FileConnection fc = null;
        StringBuffer buf = new StringBuffer();
        String str = "";
        try
        {
            fc = (FileConnection)Connector.open("file:///Teléfono:/archivo.txt", Connector.READ_WRITE);
            is = fc.openInputStream();
            int c;
            if(fc.exists())
            {
//                is= fc.openInputStream();
                while((c = is.read()) != -1)
                {
                     char ch = (char)c;
                    if (ch == '\n') 
                    { 
    //                    lines.addElement(buf.toString());
                        Tokenizer token = new Tokenizer(buf.toString(),"|");
                        buf.delete(0,buf.length());
                        
                        String numAutho=token.nextToken();
                        String numInvoice=token.nextToken();
                        String nit=token.nextToken();
                        String date =token.nextToken();
                        Tokenizer tk = new Tokenizer(date,"/");
                        date = tk.nextToken()+tk.nextToken()+tk.nextToken();
                        
                        String amount=token.nextToken().replace(',', '.');
                        String keyDosage=token.nextToken();
                        String verhoef=token.nextToken();
                        String cadenap=token.nextToken();
                        String sumatoria=token.nextToken();
                        String base64=token.nextToken();
                        String codigocontrol=token.nextToken();
                        
//                        str=str+"{"+buf.toString()+"}";
                        String codigoGenerado = CodigoDeControl.getCodigoDeControl(nit, numInvoice, date, amount, numAutho, keyDosage);
                        if(codigocontrol.equals(codigoGenerado))
                        {
//                            str =str+"\n "+" si "+codigocontrol+" "+codigoGenerado;
                        }
                        else
                        {
                            str =str+"\n "+" no "+codigocontrol+" "+codigoGenerado;
                        }
                        getTxt().setText(str);
                        
                    }
                    else
                    {
                        buf.append(ch);
                    }
                }
                str=str+"termino XD"+buf.toString()+"}";
                 buf.delete(0,buf.length());
                 getTxt().setText("-> "+str);
//                int size = (int)fc.fileSize();
//                is= fc.openInputStream();
//                
//                byte bytes[] = new byte[size];
//                is.read(bytes, 0, size);
//                str = new String(bytes, 0, size);
            }
        }
        catch (IOException ioe)
        {
        Alert error = new Alert("Error", ioe.getMessage(), null, AlertType.INFO);
        error.setTimeout(1212313123);
        Display.getDisplay(this).setCurrent(error);
        }
        finally
        {
            try
            {
                if (null != is)
                    is.close();
                if (null != fc)
                    fc.close();
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        }
//        getTxt().setText("-> "+str);
//GEN-LINE:|7-commandAction|8|1388-postAction
 // write post-action user code here
} else if (command == okCommand23) {//GEN-LINE:|7-commandAction|9|1390-preAction
 // write pre-action user code here
    
//    asdasd
//            CodigoDeControl cd = new CodigoDeControl();
                
//            String numAutho="29040011007";
//            String numInvoice="1503";
//            String nit="4189179011";
//            String date ="20070702";
//            String amount="2500";
//            String keyDosage="9rCB7Sv4X29d)5k7N%3ab89p-3(5[A";
            
//            String numAutho="79040011859";
//            String numInvoice="152";
//            String nit="1026469026";
//            String date ="20070728";
//            String amount="135";
//            String keyDosage="A3Fs4s$)2cvD(eY667A5C4A2rsdf53kw9654E2B23s24df35F5";
                
            String numAutho="7904004313753";
            String numInvoice="826384";
            String nit="1666982";
            String date ="20080622";
            String amount="61102.7";
            String keyDosage="Ebs[$c2d2NCg5FYj@6nU5y##a5d]eDVz%]xW6bzcd}Kd)\\w\\=c+)dZHneF#bqVL@";
            textField.setText(CodigoDeControl.getCodigoDeControl(nit, numInvoice, date, amount, numAutho, keyDosage));
//GEN-LINE:|7-commandAction|10|1390-postAction
 // write post-action user code here
}//GEN-BEGIN:|7-commandAction|11|1337-preAction
} else if (displayable == formCant) {
    if (command == okCommand16) {//GEN-END:|7-commandAction|11|1337-preAction
 // write pre-action user code here
//        **************************************************************************************************************
               
                if (estaVacio(txtP) && estaVacio(txtU))
                {
                    
                    getAlerta("Sin producto", "Por favor ingrese la cantidad del producto");
                }
                else
                {
        /*
switchDisplayable (null, getListProductos ());//GEN-BEGIN:|7-commandAction|12|1337-postAction
//GEN-END:|7-commandAction|12|1337-postAction
          */    
//                cambiarPantalla();
                switchDisplayable (null, getListProductos ());  
                Products productoSeleccionado =(Products) cuenta.getProductos().elementAt(puntero);
                Products pro = new Products();
                pro.setId(productoSeleccionado.getId());
                pro.setCC(productoSeleccionado.getCC());
                pro.setKey(productoSeleccionado.getKey());
                pro.setNotes(productoSeleccionado.getNotes());
//                pro.setPaquete(productoSeleccionado.getPaquete());
//                pro.setUnidad(productoSeleccionado.getUnidad());
                pro.setUnits(productoSeleccionado.getUnits());
                pro.setCost(productoSeleccionado.getCost());
                pro.setIce(productoSeleccionado.getIce());
                pro.setSeleccionado(productoSeleccionado.getSeleccionado());
//                int cantidad = (int)(Integer.parseInt(txtP.getString())*Integer.parseInt(pro.getUnits()))+Integer.parseInt(txtU.getString());
//                if(txtP.getString().equals(""))
//                {
//                    txtP.setText("0");
//                }
//                if(txtU.getString())
                
                 switch(product_state)
                {
                    case PAQUETE:
                        int p=0;
                        if(!txtP.getString().equals(""))
                        {
                            p=Integer.parseInt(txtP.getString());
                            pro.setQty(p+"");
                            pro.setPaquete(p+"");
                            pro.setUnidad("0");
                        }
                        break;
                    case UNIDAD:
                          int u=0;
                          
                        if(!txtU.getString().equals(""))
                        {
                           u=Integer.parseInt(txtU.getString());
                        }
                        pro.setQty(u+"");
                        pro.setPaquete("0");
                        pro.setUnidad(u+"");
                        break;
                    case SINPRODUCTO:
                            switchDisplayable(null, getListMenu());
                        break;
                    default: switchDisplayable(null, getListMenu());            
                }
                
                 if(!txtB.getString().equals(""))
                 {
                   pro.setBoni(txtB.getString());
                 }
//              
                 if(!txtD.getString().equals(""))
                 {
                     pro.setDesc(txtD.getString().replace(',', '.'));
                 }
                  listProductos.append(pro.getKey()+" "+pro.getNotes()+"-"+pro.getUnits()+" cant.:"+pro.getQty()+"p y "+pro.getBoni()+"b",null);
                  listaProductos.addElement(pro);
                }
    } else if (command == okCommand17) {//GEN-LINE:|7-commandAction|13|1344-preAction
 // write pre-action user code here
        
switchDisplayable(null, getFormProd());//GEN-LINE:|7-commandAction|14|1344-postAction
 if(swalert)
 {
       listProductos.append(productoTemporal.getKey()+" "+productoTemporal.getNotes()+"-"+productoTemporal.getUnits()+" cant.:"+productoTemporal.getQty(),null);
       listaProductos.addElement(productoTemporal);
       swalert=false;
 }

// write post-action user code here
//asdasda
        
    }//GEN-BEGIN:|7-commandAction|15|1185-preAction
} else if (displayable == formCliente) {
    if (command == backCliente) {//GEN-END:|7-commandAction|15|1185-preAction
                // write pre-action user code here
switchDisplayable(null, getListMenu());//GEN-LINE:|7-commandAction|16|1185-postAction
                // write post-action user code here
} else if (command == okCliente) {//GEN-LINE:|7-commandAction|17|1093-preAction
                // write pre-action user code here
methodCliente();//GEN-LINE:|7-commandAction|18|1093-postAction
//                Clients cliente = getCliente(txtNit.getText());
              //  getAppMenu().setTitle(cliente.getName()+" Nit:"+cliente.getNit());
                // write post-action user code here
}//GEN-BEGIN:|7-commandAction|19|1263-preAction
} else if (displayable == formDatosCliente) {
    if (command == backDatos) {//GEN-END:|7-commandAction|19|1263-preAction
 // write pre-action user code here
switchDisplayable(null, getFormCliente());//GEN-LINE:|7-commandAction|20|1263-postAction
 // write post-action user code here
} else if (command == okDatos) {//GEN-LINE:|7-commandAction|21|1261-preAction
 // write pre-action user code here
switchDisplayable(null, getListMenu());//GEN-LINE:|7-commandAction|22|1261-postAction
 // write post-action user code here
}//GEN-BEGIN:|7-commandAction|23|1140-preAction
} else if (displayable == formFactura) {
    if (command == ImprimirFactura) {//GEN-END:|7-commandAction|23|1140-preAction
                // write pre-action user code here
              
        methodImprimir();//GEN-LINE:|7-commandAction|24|1140-postAction
                // write post-action user code here
} else if (command == backFactura) {//GEN-LINE:|7-commandAction|25|1179-preAction
                // write pre-action user code here
switchDisplayable(null, getListMenu());//GEN-LINE:|7-commandAction|26|1179-postAction
                // write post-action user code here
}//GEN-BEGIN:|7-commandAction|27|1244-preAction
} else if (displayable == formLoading) {
    if (command == cancelCommand2) {//GEN-END:|7-commandAction|27|1244-preAction
                // write pre-action user code here
                retornarPantalla();
                
                
//GEN-LINE:|7-commandAction|28|1244-postAction

    }//GEN-BEGIN:|7-commandAction|29|1038-preAction
} else if (displayable == formLogin) {
    if (command == exitCommand) {//GEN-END:|7-commandAction|29|1038-preAction
                // write pre-action user code here
exitMIDlet();//GEN-LINE:|7-commandAction|30|1038-postAction
                // write post-action user code here
} else if (command == okLogin) {//GEN-LINE:|7-commandAction|31|802-preAction
                // write pre-action user code here
                 
        methodLogin();//GEN-LINE:|7-commandAction|32|802-postAction
    }//GEN-BEGIN:|7-commandAction|33|1414-preAction
} else if (displayable == formLogout) {
    if (command == backCommand7) {//GEN-END:|7-commandAction|33|1414-preAction
 // write pre-action user code here
switchDisplayable(null, getListPrincipal());//GEN-LINE:|7-commandAction|34|1414-postAction
 // write post-action user code here
} else if (command == okCommand26) {//GEN-LINE:|7-commandAction|35|1412-preAction
 // write pre-action user code here
        if(getTextField1().getString().equals("10069"))
        {
           borrarInformacion();
           getTextField1().setText("");
            
            switchDisplayable(null, getFormLogin());//GEN-LINE:|7-commandAction|36|1412-postAction
        // write post-action user code here
            
        
            LimpiarLogin();
//            facturas.removeAllElements();
//            EliminarFacturas();
        }
        else
        {
           switchDisplayable(getAlerta("Contraseña Invalida","Contraseña incorrecta por favor verifique si su  contraseña es la de esta cuenta! ",-11), getFormLogout());
        }
        
    }//GEN-BEGIN:|7-commandAction|37|1330-preAction
} else if (displayable == formProd) {
    if (command == backCommand4) {//GEN-END:|7-commandAction|37|1330-preAction
 // write pre-action user code here
switchDisplayable(null, getListProductos());//GEN-LINE:|7-commandAction|38|1330-postAction
 // write post-action user code here
} else if (command == okCommand15) {//GEN-LINE:|7-commandAction|39|1328-preAction
 // write pre-action user code here
    //ok del formProd para la seleccion del producto
        //txtProductKey.setText(buscarProducto(txtProductKey.getString())+"");
        
    if(!buscarListaProductos(txtProductKey.getString()))
    {
//    
        if(buscarProducto(txtProductKey.getString()))
        {
            
        /*    
switchDisplayable(null, getFormCant());//GEN-LINE:|7-commandAction|40|1328-postAction
          */
            switchDisplayable(null, getFormCantidad()); 
            formCant.setTitle(nombreProducto());
            
            
            
        }
        else
        {  

             switchDisplayable(null, getFormProd());
             getAlerta("Producto no Encontrado","Codigo de producto no valido! ");
       
        }
    }
    else
    {
         switchDisplayable(getAlertaConfirmacion("Alerta de Duplicidad","Este producto ya se agrego la lista de Productos\n ¿Desea Modificar el Producto? "), getFormProd());
    }
    }//GEN-BEGIN:|7-commandAction|41|1357-preAction
} else if (displayable == formRClient) {
    if (command == okCommand18) {//GEN-END:|7-commandAction|41|1357-preAction
 // write pre-action user code here
methodFactura();//GEN-LINE:|7-commandAction|42|1357-postAction
 // write post-action user code here
} else if (command == okCommand19) {//GEN-LINE:|7-commandAction|43|1359-preAction
 // write pre-action user code here
switchDisplayable(null, getListPrincipal());//GEN-LINE:|7-commandAction|44|1359-postAction
 // write post-action user code here
}//GEN-BEGIN:|7-commandAction|45|1255-preAction
} else if (displayable == formRegistro) {
    if (command == back) {//GEN-END:|7-commandAction|45|1255-preAction
 // write pre-action user code here
switchDisplayable(null, getFormCliente());//GEN-LINE:|7-commandAction|46|1255-postAction
 // write post-action user code here
} else if (command == okRegistrar) {//GEN-LINE:|7-commandAction|47|1253-preAction
 // write pre-action user code here
methodRegistrarCliente();//GEN-LINE:|7-commandAction|48|1253-postAction
 // write post-action user code here
}//GEN-BEGIN:|7-commandAction|49|1399-preAction
} else if (displayable == formSincronizacion) {
    if (command == backCommand5) {//GEN-END:|7-commandAction|49|1399-preAction
 // write pre-action user code here
switchDisplayable(null, getListPrincipal());//GEN-LINE:|7-commandAction|50|1399-postAction
 // write post-action user code here
} else if (command == okCommand24) {//GEN-LINE:|7-commandAction|51|1395-preAction
 // consultando lista de clientes
//    pantalla = CLIENTES;
        
//        Cargando();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
        switchDisplayable(null,getFormObtenerClientes());
        
        ConectorRest c = new ConectorRest();
        try{
             getContenidoClientes().setText(" Solicitando Informacion ");
           String res= c.EnviarGet(ConexionIpx.getURL(ConexionIpx.CLIENTE),user.getllave());
    //    c.EnviarRestPost("http://www.sigcfactu.com.bo/session",SendInvoices.toJSONObjects(facturasbackup),user.getllave());
    //    SendInvoices.toJSONObjects(facturas)
           String r="";
           serverInfo.setClientesTxt(res);
            try {
                        storage.save( serverInfo, "servertxt");
                        r="informacion registrada";
                } catch (IOException e) {
                        r="no se pudo registrar la informacion";
                        System.out.println("Unable to store serverInfo XD" + e );
                }
           getContenidoClientes().setText(" Informacion obtenida contenido total "+res.length()+" \n "+r);
    //        getStringItem6().setText("resultado de clientes: "+c.getRespuesta());
        }catch(IOException e){
        getContenidoClientes().setText(" No se pudo obtener respuesta del servidor "+c.getCodigoRespuesta() );

        }
//        if(conexion!=null)
//        {
//            conexion =null;
//        }
//           conexion = new ConexionIpx();
           
//        Thread t = new Thread()
//        {
//            public void run()
//            {
////                   switchDisplayable(null,getFormLoading());
//                System.out.println(" thred consumidor  del cliente");
//                if(conexion.getCodigoRespuesta()==200)
//                {
//                    serverInfo.setClientesTxt(conexion.getRespuesta());
//                    
////                    if(clientesTemp!=null)
////                    {
////                        clientesTemp=null;
////                    }
////                    clientesTemp = Clients.fromJsonArray(conexion.getRespuesta());
////                    
////                    clientes.removeAllElements();
////                    for(int i=0;i<clientesTemp.size();i++)
////                    {
////                        Clients cli = (Clients) clientesTemp.elementAt(i);
////                        clientes.addElement(cli);
////                    }
//                       try {
//                                    storage.save( serverInfo, "servertxt");
//                            } catch (IOException e) {
//
//                                    System.out.println("Unable to store serverInfo XD" + e );
//                            }
//                       getFormLoading().setTitle("termino de sincronizar");
//////                    cambiarPantalla();
////                       switchDisplayable(null, getFormSincronizacion());
////                    getStrNumClientes().setText(""+clientes.size());
////            
//                    cambiarPantalla();
//                                  
//                    
//                    
//                    //Cargando el titulo de la lista
//                    
//                }
//                else
//                {   
//                    //Repinta la pantalla antes de que esta esetes
//                    switchDisplayable(null, getFormSincronizacion());
//                    switchDisplayable(getProblemas(), getFormSincronizacion());
//                }   
//
//            
//            }
//      
//        };       
        
//        conexion.EnviarGet(ConexionIpx.CLIENTE,"",this.user.getllave(),t);
////        conexion.Lenvantate();
//        conexion.start();
//GEN-LINE:|7-commandAction|52|1395-postAction
 // write post-action user code here
} else if (command == okCommand25) {//GEN-LINE:|7-commandAction|53|1397-preAction
 // Enviar Facturas XD ---------------------------Nota estoy cansado jejeje ---------------------------------------------------------
        pantalla = SINCRONIZAR;
        if(!facturas.isEmpty())
        {  
            switchDisplayable(null, getFormEnviar()); 
        }
        else
        {
            switchDisplayable(alerta("Sin Facturas!","No tiene facturas para enviar"),getFormSincronizacion());  
        }
//GEN-LINE:|7-commandAction|54|1397-postAction
 // write post-action user code here
} else if (command == okCommand27) {//GEN-LINE:|7-commandAction|55|1423-preAction
 // write pre-action user code here
    imprimirReporte();
//GEN-LINE:|7-commandAction|56|1423-postAction
 // write post-action user code here
} else if (command == okCommand28) {//GEN-LINE:|7-commandAction|57|1430-preAction
 // write pre-action user code here
switchDisplayable(null, getInformacion());//GEN-LINE:|7-commandAction|58|1430-postAction
 // write post-action user code here
    String infoSucursal = ""+sucursal.getName()+"\n deadline:"+sucursal.getDeadline()+"\n contador:"+sucursal.getInvoice_number_counter()+"\n llave:"+sucursal.getKey_dosage()+"$a'-.1 \n terceros:"+sucursal.getTerceros();
    getStringItem1().setText(infoSucursal);
    int ifacturas=0;
    int iventas=0;
    for(int i=0;i<facturas.size();i++)
        {
           if(((FacturaOffline)facturas.elementAt(i)).isInvoice())
        {
            ifacturas++;
        }
        else{
            iventas++;
        }
    }
    
    String facturas_backup= " backup:"+facturasbackup.size()+"\n  f: "+ifacturas+" v:"+iventas;
    
    getStringItem2().setText(facturas_backup);
    
    String informacion_adicional=" clientes:"+clientes.size()+" productos:"+productos.size();
    getStringItem5().setText(informacion_adicional);
    getStringItem6().setText(SendInvoices.toJSONObjects(facturas));
    
    
    } else if (command == okCommand30) {//GEN-LINE:|7-commandAction|59|1441-preAction
 // write pre-action user code here
//        asd}
             
        pantalla = CLIENTES;
//        asdas
        Actualizando();
//        getForm1()
//                switchDisplayable(null, getActualizarClientes());
        
            
        Thread t = new Thread()
        {
            public void run()
            {
//                 if(clientesTemp!=null)
//                    {
//                        clientesTemp=null;
//                    }
//                    clientesTemp = Clients.fromJsonArray(conexion.getRespuesta());
                    clientes.removeAllElements();
                     try{
                    JSONArray array = new JSONArray(serverInfo.getClientesTxt());
                    Log.i("Clientes en total ", ""+array.length());
//                    getFormActualizar().setTitle("Clientes total "+array.length());
                   
                        gauge.setMaxValue(array.length());
                        gauge.setValue(1);
//                        getFormActualizar().append(gauge);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject json = array.getJSONObject(i);
                        Clients client = Clients.fromJson(json.toString());
                        clientes.addElement(client);
                        Log.i("cliente id: ", client.getId()+" adicionado");
                        getContenidoActulizar().setText("Cliente "+i+" de "+array.length());
                        gauge.setValue(gauge.getValue()+1);
                        gauge.updateInternalArea();
//                        getFormActualizar().setTitle("Actualizando Clienetes "+i+" de "+array.length());
                    }
                    }catch(JSONException e)
                    {
                        e.printStackTrace();
                    }

                    
                    
                    
                       try {
                                    storage.save( clientes, "clientes");
                            } catch (IOException e) {

                                    System.out.println("Unable to store clientes XD" + e );
                            }
                    cambiarPantalla();
//                       switchDisplayable(null, getFormSincronizacion());
                    getStrNumClientes().setText(""+clientes.size());
            }
        };
        
        t.start();
        //formulario de sincronizacion de clientes aqui 
//GEN-LINE:|7-commandAction|60|1441-postAction
 // write post-action user code here
} else if (command == okUpdate) {//GEN-LINE:|7-commandAction|61|1425-preAction
 // write pre-action user code here
    pantalla = SINCRONIZAR;
        
         Cargando();
        if(conexion!=null)
        {
            conexion =null;
        }
        conexion = new ConexionIpx();
      
      
       
        Thread t = new Thread()
        {
            public void run()
            {
                   
                Log.i("Login "," thred consumidor activo");
                if(conexion.getCodigoRespuesta()==200)
                {
                    try {
                        JSONObject json = new JSONObject(conexion.getRespuesta());
                        
                        if(json.has("version"))
                        {
                            if(json.getString("version").equals(version))
                            {
                                  //Repinta la pantalla antes de que esta esetes
                                    switchDisplayable(null, getFormSincronizacion());
                                    switchDisplayable(alerta("Version actual ","La apliacion esta corriendo en su version mas actual v."+version), getFormSincronizacion());
                            }
                            else
                            {
                                if(facturas.size()==0)
                                {   
                                    Actualizando();
                                    new Thread( new Runnable() { public void run() { try{platformRequest(MIDLET_URL ); exitMIDlet();} catch(Exception e) {} } }).start();
                                }
                                else
                                {
                                     switchDisplayable(null, getFormSincronizacion());
                                    switchDisplayable(alerta("Actualizar ","Antes de actualizar a la v."+json.getString("version")+" debe sincronizar sus facturar" ), getFormSincronizacion());
                                }
                                
                            }
                                 
                        }
                        
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }
                else
                {   
                    //Repinta la pantalla antes de que esta esetes
                    switchDisplayable(null, getFormSincronizacion());
                    switchDisplayable(alerta("Error: "+conexion.getCodigoRespuesta()," No se pudo realizar la actualizacion verifique su conexion a internet y vuela a intentarlo"), getFormSincronizacion());
                }   

            
            }
      
        };       
        Log.i("actualizando al user ", user.getllave());
        conexion.EnviarGet(ConexionIpx.VERSION,"",this.user.getllave(),t);
        conexion.start();
        
       
        
        
        
//    
//GEN-LINE:|7-commandAction|62|1425-postAction
 // write post-action user code here
}//GEN-BEGIN:|7-commandAction|63|1297-preAction
} else if (displayable == formVistaFactura) {
    if (command == back) {//GEN-END:|7-commandAction|63|1297-preAction
 // write pre-action user code here
switchDisplayable(null, getFormRClient());//GEN-LINE:|7-commandAction|64|1297-postAction
 // write post-action user code here
} else if (command == okCommand11) {//GEN-LINE:|7-commandAction|65|1290-preAction
 // write pre-action user code here
methodPrintFactura();//GEN-LINE:|7-commandAction|66|1290-postAction
 // write post-action user code here
}//GEN-BEGIN:|7-commandAction|67|1428-preAction
} else if (displayable == informacion) {
    if (command == backCommand8) {//GEN-END:|7-commandAction|67|1428-preAction
 // write pre-action user code here
switchDisplayable(null, getFormSincronizacion());//GEN-LINE:|7-commandAction|68|1428-postAction
 // write post-action user code here
} else if (command == okCommand29) {//GEN-LINE:|7-commandAction|69|1439-preAction
 // write pre-action user code here
    
    ConectorRest c = new ConectorRest();
    try{
//        String texto =SendInvoices.toJSONObjects(facturas);
//        SendInvoices.sendjsonObject(facturas,9, user.getllave());
         getStringItem6().setText("se envio consulta  ");
//       String res= c.EnviarGet("http://www.sigcfactu.com.bo/clientesPOS",user.getllave());
     c.EnviarRestPost("http://www.sigcfactu.com.bo/backup",SendInvoices.toJSONObjects(facturasbackup),user.getllave());
//    SendInvoices.toJSONObjects(facturas)
//       serverInfo.setClientesTxt(res);
//        try {
//                    storage.save( serverInfo, "servertxt");
//                    
//            } catch (IOException e) {
//
//                    System.out.println("Unable to store serverInfo XD" + e );
//            }
       getStringItem6().setText(" respuesta del servidor:"+c.getRespuesta());
//        getStringItem6().setText("resultado de clientes: "+c.getRespuesta());
    }catch(IOException e){
    getStringItem6().setText("no se pudo enviar");
  
    }
//GEN-LINE:|7-commandAction|70|1439-postAction
 // write post-action user code here
} else if (command == okCommand31) {//GEN-LINE:|7-commandAction|71|1443-preAction
 // write pre-action user code here
    facturasbackup.removeAllElements();
        try {
            storage.save(facturasbackup, "facturascopia");
            getStringItem2().setText("vacio "+facturasbackup.size());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
//GEN-LINE:|7-commandAction|72|1443-postAction
 // write post-action user code here
}//GEN-BEGIN:|7-commandAction|73|1155-preAction
} else if (displayable == listMenu) {
    if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|73|1155-preAction
                // write pre-action user code here
listMenuAction();//GEN-LINE:|7-commandAction|74|1155-postAction
                // write post-action user code here
} else if (command == backSalir) {//GEN-LINE:|7-commandAction|75|1166-preAction
                // write pre-action user code here
switchDisplayable(null, getListPrincipal());//GEN-LINE:|7-commandAction|76|1166-postAction
             
                // write post-action user code here
} else if (command == okMenu) {//GEN-LINE:|7-commandAction|77|1161-preAction
                // write pre-action user code here
listMenuAction();//GEN-LINE:|7-commandAction|78|1161-postAction
                // write post-action user code here
}//GEN-BEGIN:|7-commandAction|79|1216-preAction
} else if (displayable == listPrincipal) {
    if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|79|1216-preAction
                // write pre-action user code here
listPrincipalAction();//GEN-LINE:|7-commandAction|80|1216-postAction
                // write post-action user code here
} else if (command == backCommand2) {//GEN-LINE:|7-commandAction|81|1228-preAction
//                Cargando();
//        if(conexion!=null)
//        {
//            conexion = null;
//        }
//        conexion = new ConexionIpx(rest);
//       
//        Thread t = new Thread()
//        {
//            public void run()
//            {
switchDisplayable(null, getFormLogout());//GEN-LINE:|7-commandAction|82|1228-postAction
//        
//        }
//
//        };       
//        
//        conexion.EnviarGet(-1,"",llave,t);
//        conexion.start();


    } else if (command == okCommand8) {//GEN-LINE:|7-commandAction|83|1224-preAction
                // write pre-action user code here
listPrincipalAction();//GEN-LINE:|7-commandAction|84|1224-postAction
                // write post-action user code here
}//GEN-BEGIN:|7-commandAction|85|1125-preAction
} else if (displayable == listProductos) {
    if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|85|1125-preAction
                // write pre-action user code here
listProductosAction();//GEN-LINE:|7-commandAction|86|1125-postAction
                // write post-action user code here
} else if (command == backMenu) {//GEN-LINE:|7-commandAction|87|1133-preAction
                // write pre-action user code here
switchDisplayable(null, getListMenu());//GEN-LINE:|7-commandAction|88|1133-postAction
                
//                strProductos.setText("entro");
//                String p="lista de Items:\nCANT CONCEPTO      BS";
//                double total=0;
//                for (int i=0;i<listaProductos.size();i++)
//                {
//                    Products pro = (Products) listaProductos.elementAt(i);
//                    p = p +"\n "+Double.parseDouble(pro.getQty())+" "+pro.getNotes()+" "+(Double.parseDouble(pro.getCost())*Double.parseDouble(pro.getQty()));
//                    total = total + (Double.parseDouble(pro.getCost())*Double.parseDouble(pro.getQty()));
//                    
//                }
//                strProductos.setText(p);
////                strProductos.setText(""+total);
//                strTotal.setText(""+total);
                
                // write post-action user code here
} else if (command == okCommand4) {//GEN-LINE:|7-commandAction|89|1192-preAction
                // write pre-action user code here
                   Products pro = (Products) listaProductos.elementAt(listProductos.getSelectedIndex());
//                   seleccionarProducto(pro,false);
                   listaProductos.removeElementAt(listProductos.getSelectedIndex());
                 
                   listProductos.delete(listProductos.getSelectedIndex());
//GEN-LINE:|7-commandAction|90|1192-postAction
                // write post-action user code here
} else if (command == okOpciones) {//GEN-LINE:|7-commandAction|91|1127-preAction
                // write pre-action user code here
    //liberar objeto de memoria
//    lista = null;
switchDisplayable(null, getFormProd());//GEN-LINE:|7-commandAction|92|1127-postAction
                // write post-action user code here
//Limpiando items para productos

        LimpiarItems();
    }//GEN-BEGIN:|7-commandAction|93|1315-preAction
} else if (displayable == notesList) {
    if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|93|1315-preAction
 // write pre-action user code here
notesListAction();//GEN-LINE:|7-commandAction|94|1315-postAction
 // write post-action user code here
} else if (command == backCommand3) {//GEN-LINE:|7-commandAction|95|1321-preAction
 // write pre-action user code here
//GEN-LINE:|7-commandAction|96|1321-postAction
 // write post-action user code here
} else if (command == okCommand14) {//GEN-LINE:|7-commandAction|97|1319-preAction
 // write pre-action user code here
switchDisplayable(null, getForm());//GEN-LINE:|7-commandAction|98|1319-postAction
 // write post-action user code here
}//GEN-BEGIN:|7-commandAction|99|24-preAction
} else if (displayable == splashScreen) {
    if (command == SplashScreen.DISMISS_COMMAND) {//GEN-END:|7-commandAction|99|24-preAction
                // write pre-action user code here
    

        switchDisplayable(null, getFormLogin()); 
//        switchDisplayable(null, getForm1()); 
        
        if(!user.getUsuario().equals(""))
        {
              //si no esta con session borrar toda la informacion para nuevo usuario
                        
            
            switchDisplayable(null, getListPrincipal());
             getListPrincipal().setTitle("Usuario:"+user.getUsuario());
             if(cuenta!=null)
             {
                 cuenta = null;
             }
             cuenta = new Cuenta();
             cuenta.setSucursal(sucursal);
             cuenta.setProductos(productos);
             cuenta.setIce(user.getIce());
                         
        }
        
       
//            borrarInformacion();
      /*      
switchDisplayable (null, getForm1 ());//GEN-BEGIN:|7-commandAction|100|24-postAction
//GEN-END:|7-commandAction|100|24-postAction
       */
                    
//aqui la validacion del loing 
       
         
// write post-action user code here
}//GEN-BEGIN:|7-commandAction|101|7-postCommandAction
        }//GEN-END:|7-commandAction|101|7-postCommandAction
        // write post-action user code here
}//GEN-BEGIN:|7-commandAction|102|
//</editor-fold>//GEN-END:|7-commandAction|102|




//<editor-fold defaultstate="collapsed" desc=" Generated Getter: splashScreen ">//GEN-BEGIN:|22-getter|0|22-preInit
    /**
     * Returns an initialized instance of splashScreen component.
     *
     * @return the initialized component instance
     */
    public SplashScreen getSplashScreen() {
        if (splashScreen == null) {
//GEN-END:|22-getter|0|22-preInit
            // write pre-init user code here


            splashScreen = new SplashScreen(getDisplay());//GEN-BEGIN:|22-getter|1|22-postInit
            splashScreen.setTitle("");
            splashScreen.setCommandListener(this);
            splashScreen.setFullScreenMode(true);
            splashScreen.setImage(getImage10());
            splashScreen.setTimeout(2000);//GEN-END:|22-getter|1|22-postInit
            splashScreen.repaint();
        }//GEN-BEGIN:|22-getter|2|
        return splashScreen;
    }
//</editor-fold>//GEN-END:|22-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: font ">//GEN-BEGIN:|487-getter|0|487-preInit
    /**
     * Returns an initialized instance of font component.
     *
     * @return the initialized component instance
     */
    public Font getFont() {
        if (font == null) {
//GEN-END:|487-getter|0|487-preInit
            // write pre-init user code here
font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);//GEN-LINE:|487-getter|1|487-postInit
            // write post-init user code here
}//GEN-BEGIN:|487-getter|2|
        return font;
    }
//</editor-fold>//GEN-END:|487-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image ">//GEN-BEGIN:|519-getter|0|519-preInit
    /**
     * Returns an initialized instance of image component.
     *
     * @return the initialized component instance
     */
    public Image getImage() {
        if (image == null) {
//GEN-END:|519-getter|0|519-preInit
            // write pre-init user code here
try {//GEN-BEGIN:|519-getter|1|519-@java.io.IOException
                image = Image.createImage("/splash_128x160.PNG");
            } catch (java.io.IOException e) {//GEN-END:|519-getter|1|519-@java.io.IOException
                e.printStackTrace();
            }//GEN-LINE:|519-getter|2|519-postInit
            // write post-init user code here
}//GEN-BEGIN:|519-getter|3|
        return image;
    }
//</editor-fold>//GEN-END:|519-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: tickerLogin ">//GEN-BEGIN:|644-getter|0|644-preInit
    /**
     * Returns an initialized instance of tickerLogin component.
     *
     * @return the initialized component instance
     */
    public Ticker getTickerLogin() {
        if (tickerLogin == null) {
//GEN-END:|644-getter|0|644-preInit
            // write pre-init user code here
tickerLogin = new Ticker("");//GEN-LINE:|644-getter|1|644-postInit
            // write post-init user code here
}//GEN-BEGIN:|644-getter|2|
        return tickerLogin;
    }
//</editor-fold>//GEN-END:|644-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image1 ">//GEN-BEGIN:|671-getter|0|671-preInit
    /**
     * Returns an initialized instance of image1 component.
     *
     * @return the initialized component instance
     */
    public Image getImage1() {
        if (image1 == null) {
//GEN-END:|671-getter|0|671-preInit
            // write pre-init user code here
try {//GEN-BEGIN:|671-getter|1|671-@java.io.IOException
                image1 = Image.createImage("/print.png");
            } catch (java.io.IOException e) {//GEN-END:|671-getter|1|671-@java.io.IOException
                e.printStackTrace();
            }//GEN-LINE:|671-getter|2|671-postInit
            // write post-init user code here
}//GEN-BEGIN:|671-getter|3|
        return image1;
    }
//</editor-fold>//GEN-END:|671-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image2 ">//GEN-BEGIN:|683-getter|0|683-preInit
    /**
     * Returns an initialized instance of image2 component.
     *
     * @return the initialized component instance
     */
    public Image getImage2() {
        if (image2 == null) {
//GEN-END:|683-getter|0|683-preInit
            // write pre-init user code here
try {//GEN-BEGIN:|683-getter|1|683-@java.io.IOException
                image2 = Image.createImage("/talkin.png");
            } catch (java.io.IOException e) {//GEN-END:|683-getter|1|683-@java.io.IOException
                e.printStackTrace();
            }//GEN-LINE:|683-getter|2|683-postInit
            // write post-init user code here
}//GEN-BEGIN:|683-getter|3|
        return image2;
    }
//</editor-fold>//GEN-END:|683-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image3 ">//GEN-BEGIN:|704-getter|0|704-preInit
    /**
     * Returns an initialized instance of image3 component.
     *
     * @return the initialized component instance
     */
    public Image getImage3() {
        if (image3 == null) {
//GEN-END:|704-getter|0|704-preInit
            // write pre-init user code here
try {//GEN-BEGIN:|704-getter|1|704-@java.io.IOException
                image3 = Image.createImage("/usuario.png");
            } catch (java.io.IOException e) {//GEN-END:|704-getter|1|704-@java.io.IOException
                e.printStackTrace();
            }//GEN-LINE:|704-getter|2|704-postInit
            // write post-init user code here
}//GEN-BEGIN:|704-getter|3|
        return image3;
    }
//</editor-fold>//GEN-END:|704-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image4 ">//GEN-BEGIN:|712-getter|0|712-preInit
    /**
     * Returns an initialized instance of image4 component.
     *
     * @return the initialized component instance
     */
    public Image getImage4() {
        if (image4 == null) {
//GEN-END:|712-getter|0|712-preInit
            // write pre-init user code here
try {//GEN-BEGIN:|712-getter|1|712-@java.io.IOException
                image4 = Image.createImage("/waiting.png");
            } catch (java.io.IOException e) {//GEN-END:|712-getter|1|712-@java.io.IOException
                e.printStackTrace();
            }//GEN-LINE:|712-getter|2|712-postInit
            // write post-init user code here
}//GEN-BEGIN:|712-getter|3|
        return image4;
    }
//</editor-fold>//GEN-END:|712-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image5 ">//GEN-BEGIN:|722-getter|0|722-preInit
    /**
     * Returns an initialized instance of image5 component.
     *
     * @return the initialized component instance
     */
    public Image getImage5() {
        if (image5 == null) {
//GEN-END:|722-getter|0|722-preInit
            // write pre-init user code here
try {//GEN-BEGIN:|722-getter|1|722-@java.io.IOException
                image5 = Image.createImage("/Others_32x32.png");
            } catch (java.io.IOException e) {//GEN-END:|722-getter|1|722-@java.io.IOException
                e.printStackTrace();
            }//GEN-LINE:|722-getter|2|722-postInit
            // write post-init user code here
}//GEN-BEGIN:|722-getter|3|
        return image5;
    }
//</editor-fold>//GEN-END:|722-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image6 ">//GEN-BEGIN:|723-getter|0|723-preInit
    /**
     * Returns an initialized instance of image6 component.
     *
     * @return the initialized component instance
     */
    public Image getImage6() {
        if (image6 == null) {
//GEN-END:|723-getter|0|723-preInit
            // write pre-init user code here
try {//GEN-BEGIN:|723-getter|1|723-@java.io.IOException
                image6 = Image.createImage("/Voucher 2_32x24.png");
            } catch (java.io.IOException e) {//GEN-END:|723-getter|1|723-@java.io.IOException
                e.printStackTrace();
            }//GEN-LINE:|723-getter|2|723-postInit
            // write post-init user code here
}//GEN-BEGIN:|723-getter|3|
        return image6;
    }
//</editor-fold>//GEN-END:|723-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image7 ">//GEN-BEGIN:|724-getter|0|724-preInit
    /**
     * Returns an initialized instance of image7 component.
     *
     * @return the initialized component instance
     */
    public Image getImage7() {
        if (image7 == null) {
//GEN-END:|724-getter|0|724-preInit
            // write pre-init user code here
try {//GEN-BEGIN:|724-getter|1|724-@java.io.IOException
                image7 = Image.createImage("/Top up 2_32x26.png");
            } catch (java.io.IOException e) {//GEN-END:|724-getter|1|724-@java.io.IOException
                e.printStackTrace();
            }//GEN-LINE:|724-getter|2|724-postInit
            // write post-init user code here
}//GEN-BEGIN:|724-getter|3|
        return image7;
    }
//</editor-fold>//GEN-END:|724-getter|3|



//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand ">//GEN-BEGIN:|776-getter|0|776-preInit
    /**
     * Returns an initialized instance of exitCommand component.
     *
     * @return the initialized component instance
     */
    public Command getExitCommand() {
        if (exitCommand == null) {
//GEN-END:|776-getter|0|776-preInit
            // write pre-init user code here
exitCommand = new Command("Exit", "Exit", Command.EXIT, 1);//GEN-LINE:|776-getter|1|776-postInit
            // write post-init user code here
}//GEN-BEGIN:|776-getter|2|
        return exitCommand;
    }
//</editor-fold>//GEN-END:|776-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: ticker ">//GEN-BEGIN:|780-getter|0|780-preInit
    /**
     * Returns an initialized instance of ticker component.
     *
     * @return the initialized component instance
     */
    public Ticker getPolishTicker() {
        if (ticker == null) {
//GEN-END:|780-getter|0|780-preInit
            // write pre-init user code here
ticker = new Ticker("tickerError");//GEN-LINE:|780-getter|1|780-postInit
            // write post-init user code here
}//GEN-BEGIN:|780-getter|2|
        return ticker;
    }
//</editor-fold>//GEN-END:|780-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okLogin ">//GEN-BEGIN:|801-getter|0|801-preInit
    /**
     * Returns an initialized instance of okLogin component.
     *
     * @return the initialized component instance
     */
    public Command getOkLogin() {
        if (okLogin == null) {
//GEN-END:|801-getter|0|801-preInit
            // write pre-init user code here
okLogin = new Command("Aceptar", Command.OK, 0);//GEN-LINE:|801-getter|1|801-postInit
            // write post-init user code here
}//GEN-BEGIN:|801-getter|2|
        return okLogin;
    }
//</editor-fold>//GEN-END:|801-getter|2|



//<editor-fold defaultstate="collapsed" desc=" Generated Getter: formLogin ">//GEN-BEGIN:|797-getter|0|797-preInit
    /**
     * Returns an initialized instance of formLogin component.
     *
     * @return the initialized component instance
     */
    public Form getFormLogin() {
        if (formLogin == null) {
//GEN-END:|797-getter|0|797-preInit
            // write pre-init user code here
            
            formLogin = new Form("Autentificaci\u00F3n v3.9.5", new Item[]{getTxtUsuario(), getTxtPassword(), getImageItem()});//GEN-BEGIN:|797-getter|1|797-postInit
            formLogin.setTicker(getTickerLogin());
            formLogin.addCommand(getOkLogin());
            formLogin.addCommand(getExitCommand());
            formLogin.setCommandListener(this);//GEN-END:|797-getter|1|797-postInit
            // write post-init user code here
            
//            TableData data = new TableData(3,2);
//            data.set( 1, 0, "1,0");
//            data.set( 2, 0, "2,0");
//            data.set( 0, 1, "0,1");
//            data.set( 1, 1, "xxxx1,1");
//            data.set( 2, 1, "2,1");
//            //#style defaultTable
//            TableItem table = new TableItem(data);
//            formLogin.append( table );
}//GEN-BEGIN:|797-getter|2|
        return formLogin;
    }
//</editor-fold>//GEN-END:|797-getter|2|
//<editor-fold defaultstate="collapsed" desc=" Generated Getter: task ">//GEN-BEGIN:|853-getter|0|853-preInit

    /**
     * Returns an initialized instance of task component.
     *
     * @return the initialized component instance
     */
    public SimpleCancellableTask getTask() {
        if (task == null) {
//GEN-END:|853-getter|0|853-preInit
            // write pre-init user code here
task = new SimpleCancellableTask();//GEN-BEGIN:|853-getter|1|853-execute
            task.setExecutable(new org.netbeans.microedition.util.Executable() {
                public void execute() throws Exception {//GEN-END:|853-getter|1|853-execute
// write task-execution user code here
}//GEN-BEGIN:|853-getter|2|853-postInit
            });//GEN-END:|853-getter|2|853-postInit
            // write post-init user code here
}//GEN-BEGIN:|853-getter|3|
        return task;
    }
//</editor-fold>//GEN-END:|853-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image8 ">//GEN-BEGIN:|858-getter|0|858-preInit
    /**
     * Returns an initialized instance of image8 component.
     *
     * @return the initialized component instance
     */
    public Image getImage8() {
        if (image8 == null) {
//GEN-END:|858-getter|0|858-preInit
            // write pre-init user code here
try {//GEN-BEGIN:|858-getter|1|858-@java.io.IOException
                image8 = Image.createImage("/logoipx.png");
            } catch (java.io.IOException e) {//GEN-END:|858-getter|1|858-@java.io.IOException
                e.printStackTrace();
            }//GEN-LINE:|858-getter|2|858-postInit
            // write post-init user code here
}//GEN-BEGIN:|858-getter|3|
        return image8;
    }
//</editor-fold>//GEN-END:|858-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image9 ">//GEN-BEGIN:|865-getter|0|865-preInit
    /**
     * Returns an initialized instance of image9 component.
     *
     * @return the initialized component instance
     */
    public Image getImage9() {
        if (image9 == null) {
//GEN-END:|865-getter|0|865-preInit
            // write pre-init user code here
try {//GEN-BEGIN:|865-getter|1|865-@java.io.IOException
                image9 = Image.createImage("/login.png");
            } catch (java.io.IOException e) {//GEN-END:|865-getter|1|865-@java.io.IOException
                e.printStackTrace();
            }//GEN-LINE:|865-getter|2|865-postInit
            // write post-init user code here
}//GEN-BEGIN:|865-getter|3|
        return image9;
    }
//</editor-fold>//GEN-END:|865-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okProducto ">//GEN-BEGIN:|870-getter|0|870-preInit
    /**
     * Returns an initialized instance of okProducto component.
     *
     * @return the initialized component instance
     */
    public Command getOkProducto() {
        if (okProducto == null) {
//GEN-END:|870-getter|0|870-preInit
            // write pre-init user code here
okProducto = new Command("Adicionar Producto", Command.OK, 0);//GEN-LINE:|870-getter|1|870-postInit
            // write post-init user code here
}//GEN-BEGIN:|870-getter|2|
        return okProducto;
    }
//</editor-fold>//GEN-END:|870-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: task1 ">//GEN-BEGIN:|894-getter|0|894-preInit
    /**
     * Returns an initialized instance of task1 component.
     *
     * @return the initialized component instance
     */
    public SimpleCancellableTask getTask1() {
        if (task1 == null) {
//GEN-END:|894-getter|0|894-preInit
            // write pre-init user code here
task1 = new SimpleCancellableTask();//GEN-BEGIN:|894-getter|1|894-execute
            task1.setExecutable(new org.netbeans.microedition.util.Executable() {
                public void execute() throws Exception {//GEN-END:|894-getter|1|894-execute
// write task-execution user code here
}//GEN-BEGIN:|894-getter|2|894-postInit
            });//GEN-END:|894-getter|2|894-postInit
            // write post-init user code here
}//GEN-BEGIN:|894-getter|3|
        return task1;
    }
//</editor-fold>//GEN-END:|894-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: backProducto ">//GEN-BEGIN:|946-getter|0|946-preInit
    /**
     * Returns an initialized instance of backProducto component.
     *
     * @return the initialized component instance
     */
    public Command getBackProducto() {
        if (backProducto == null) {
//GEN-END:|946-getter|0|946-preInit
            // write pre-init user code here
backProducto = new Command("Volver", Command.BACK, 0);//GEN-LINE:|946-getter|1|946-postInit
            // write post-init user code here
}//GEN-BEGIN:|946-getter|2|
        return backProducto;
    }
//</editor-fold>//GEN-END:|946-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: ticker1 ">//GEN-BEGIN:|968-getter|0|968-preInit
    /**
     * Returns an initialized instance of ticker1 component.
     *
     * @return the initialized component instance
     */
    public Ticker getTicker1() {
        if (ticker1 == null) {
//GEN-END:|968-getter|0|968-preInit
            // write pre-init user code here
ticker1 = new Ticker("");//GEN-LINE:|968-getter|1|968-postInit
            // write post-init user code here
}//GEN-BEGIN:|968-getter|2|
        return ticker1;
    }
//</editor-fold>//GEN-END:|968-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: task2 ">//GEN-BEGIN:|1003-getter|0|1003-preInit
    /**
     * Returns an initialized instance of task2 component.
     *
     * @return the initialized component instance
     */
    public SimpleCancellableTask getTask2() {
        if (task2 == null) {
//GEN-END:|1003-getter|0|1003-preInit
            // write pre-init user code here
task2 = new SimpleCancellableTask();//GEN-BEGIN:|1003-getter|1|1003-execute
            task2.setExecutable(new org.netbeans.microedition.util.Executable() {
                public void execute() throws Exception {//GEN-END:|1003-getter|1|1003-execute
// write task-execution user code here
}//GEN-BEGIN:|1003-getter|2|1003-postInit
            });//GEN-END:|1003-getter|2|1003-postInit
            // write post-init user code here
}//GEN-BEGIN:|1003-getter|3|
        return task2;
    }
//</editor-fold>//GEN-END:|1003-getter|3|





//<editor-fold defaultstate="collapsed" desc=" Generated Method: methodLogin ">//GEN-BEGIN:|1049-entry|0|1050-preAction
    /**
     * Performs an action assigned to the methodLogin entry-point.
     */
    public void methodLogin() {
//GEN-END:|1049-entry|0|1050-preAction
        // write pre-action user code here
        
        //seteando la llave de nuevo
//        this.llave =getTxtUsuario().getText()+":"+getTxtPassword().getString(); 
        borrarInformacion();
        if(user!=null)
        {
            user.borrar();
        }
        
        user.setUsuario(getTxtUsuario().getText());
        user.setPassword(getTxtPassword().getString());
        
          if(!user.getUsuario().equals("")&&!user.getPassword().equals(""))
        {
            pantalla = AUTENTIFICACION;
            if(br!=null )
            {
                br=null;
            }
            br = new BufferRest();
            
            if(restProductor!=null)
            {
                restProductor =null;
            }
            restProductor = new RestProductor(this,br,ConexionIpx.AUTENTIFICAZION,null,user.getllave());
            restProductor.start();

            if(restConsumidor!=null)
            {
                restConsumidor = null;
            }
            restConsumidor = new RestConsumidor(this,br);
            restConsumidor.start();
        }
        else
        {
            this.getAlerta("Error ", "veririfque su usuario y contraseña");
        }
        
//        Cargando();
//        if(conexion!=null)
//        {
//            conexion =null;
//        }
//        conexion = new ConexionIpx();
//      
//      
//       
//        Thread t = new Thread()
//        {
//            public void run()
//            {
//                   
//                Log.i("Login "," thred consumidor activo");
//                if(conexion.getCodigoRespuesta()==200)
//                {
//                    
//                   cuenta = new Cuenta(conexion.getRespuesta());
//                        
//                   cambiarPantalla();
//                   getListPrincipal().setTitle("Usuario:"+getTxtUsuario().getText());
////                   Log.i("metho Login", rest.getRespuesta());
//                   
//                   //guardando usuario 
//                   
//                   user.setUsuario(getTxtUsuario().getText());
//                   user.setPassword(getTxtPassword().getString());
//                   user.setSesion(true);
//                   user.setIce(cuenta.getIce());
//                   
//                    try {
//                                storage.save( user, "usuario");
////                                Log.i("metho Login", "usuario guardado "+user.getUsuario());
//                        } catch (IOException e) {
//
//                                
////                                Log.i("metho Login", "Unable to store user XD");
//                        }
//                    
//                    //guardando sucursal
//                    sucursal.setActivity_pri(cuenta.getSucursal().getActivity_pri());
//                    sucursal.setAddress1(cuenta.getSucursal().getAddress1());
//                    sucursal.setAddress2(cuenta.getSucursal().getAddress2());
//                    sucursal.setDeadline(cuenta.getSucursal().getDeadline());
//                    sucursal.setInvoice_number_counter(cuenta.getSucursal().getInvoice_number_counter());
//                    sucursal.setKey_dosage(cuenta.getSucursal().getKey_dosage());
//                    sucursal.setLaw(cuenta.getSucursal().getLaw());
//                    sucursal.setName(cuenta.getSucursal().getName());
//                    sucursal.setNumber_autho(cuenta.getSucursal().getNumber_autho());
//                    sucursal.setTerceros(cuenta.getSucursal().getTerceros());
//                    
//                     try {
//                            storage.save( sucursal, "sucursal");
//                            Log.i("metho Login", "sucursales sucursal");
//                        } catch (IOException e) {
//                                Log.i("metho Login", "Unable to store sucursal XD");
//
//                        }
//                    productos.removeAllElements();
//                    for(int i=0;i<cuenta.getProductos().size();i++)
//                    {
//                        Products pr = (Products) cuenta.getProductos().elementAt(i);
//                        productos.addElement(pr);
//                    }
//                    
//                      try {
//                                storage.save( productos, "productos");
////                                 Log.i("metho Login", "productos guardados");
//                		} catch (IOException e) {
////                			Log.i("metho Login", "Unable to store productos XD");
//                			
//                		}
//                    
//                    //Cargando el titulo de la lista
//                        DateUtil.VerificarFecha(cuenta.getSucursal().getDeadline());
//                    
//                }
//                else
//                {   
//                    //Repinta la pantalla antes de que esta esetes
//                    switchDisplayable(null, getFormLogin());
//                    switchDisplayable(getProblemas(), getFormLogin());
//                }   
////                conexion = null;
//            
//            }
//      
//        };       
//        Log.i("llave de usuario", user.getllave());
//        conexion.EnviarGet(ConexionIpx.AUTENTIFICAZION,"",this.user.getllave(),t);
//        conexion.start();
            /*
switchDisplayable (null, getListPrincipal ());//GEN-BEGIN:|1049-entry|1|1050-postAction
//GEN-END:|1049-entry|1|1050-postAction
            */
                    
                    
    }//GEN-BEGIN:|1049-entry|2|
//</editor-fold>//GEN-END:|1049-entry|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: TxtUsuario ">//GEN-BEGIN:|1056-getter|0|1056-preInit
    /**
     * Returns an initialized instance of TxtUsuario component.
     *
     * @return the initialized component instance
     */
    public TextField getTxtUsuario() {
        if (TxtUsuario == null) {
//GEN-END:|1056-getter|0|1056-preInit
            // write pre-init user code here
TxtUsuario = new TextField("Usuario:", "", 32, TextField.NUMERIC);//GEN-LINE:|1056-getter|1|1056-postInit
            // write post-init user code here
}//GEN-BEGIN:|1056-getter|2|
        return TxtUsuario;
    }
//</editor-fold>//GEN-END:|1056-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: TxtPassword ">//GEN-BEGIN:|1057-getter|0|1057-preInit
    /**
     * Returns an initialized instance of TxtPassword component.
     *
     * @return the initialized component instance
     */
    public TextField getTxtPassword() {
        if (TxtPassword == null) {
//GEN-END:|1057-getter|0|1057-preInit
            // write pre-init user code here
TxtPassword = new TextField("Contrase\u00F1a:", "", 32, TextField.NUMERIC | TextField.PASSWORD);//GEN-LINE:|1057-getter|1|1057-postInit
            // write post-init user code here
}//GEN-BEGIN:|1057-getter|2|
        return TxtPassword;
    }
//</editor-fold>//GEN-END:|1057-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCliente ">//GEN-BEGIN:|1092-getter|0|1092-preInit
    /**
     * Returns an initialized instance of okCliente component.
     *
     * @return the initialized component instance
     */
    public Command getOkCliente() {
        if (okCliente == null) {
//GEN-END:|1092-getter|0|1092-preInit
            // write pre-init user code here
okCliente = new Command("Ok", Command.OK, 0);//GEN-LINE:|1092-getter|1|1092-postInit
            // write post-init user code here
}//GEN-BEGIN:|1092-getter|2|
        return okCliente;
    }
//</editor-fold>//GEN-END:|1092-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: formCliente ">//GEN-BEGIN:|1091-getter|0|1091-preInit
    /**
     * Returns an initialized instance of formCliente component.
     *
     * @return the initialized component instance
     */
    public Form getFormCliente() {
        if (formCliente == null) {
//GEN-END:|1091-getter|0|1091-preInit
            // write pre-init user code here
formCliente = new Form("Cliente", new Item[]{getTxtNit(), getSpacer()});//GEN-BEGIN:|1091-getter|1|1091-postInit
            formCliente.addCommand(getOkCliente());
            formCliente.addCommand(getBackCliente());
            formCliente.setCommandListener(this);//GEN-END:|1091-getter|1|1091-postInit
            // write post-init user code here
}//GEN-BEGIN:|1091-getter|2|
        return formCliente;
    }
//</editor-fold>//GEN-END:|1091-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtNit ">//GEN-BEGIN:|1095-getter|0|1095-preInit
    /**
     * Returns an initialized instance of txtNit component.
     *
     * @return the initialized component instance
     */
    public TextField getTxtNit() {
        if (txtNit == null) {
//GEN-END:|1095-getter|0|1095-preInit
            // write pre-init user code here
txtNit = new TextField("Numero de Cliente:", null, 32, TextField.NUMERIC);//GEN-BEGIN:|1095-getter|1|1095-postInit
            txtNit.setLayout(ImageItem.LAYOUT_LEFT | Item.LAYOUT_TOP | Item.LAYOUT_VCENTER);//GEN-END:|1095-getter|1|1095-postInit
            // write post-init user code here
}//GEN-BEGIN:|1095-getter|2|
        return txtNit;
    }
//</editor-fold>//GEN-END:|1095-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: spacer ">//GEN-BEGIN:|1097-getter|0|1097-preInit
    /**
     * Returns an initialized instance of spacer component.
     *
     * @return the initialized component instance
     */
    public Spacer getSpacer() {
        if (spacer == null) {
//GEN-END:|1097-getter|0|1097-preInit
            // write pre-init user code here
spacer = new Spacer(16, 1);//GEN-LINE:|1097-getter|1|1097-postInit
            // write post-init user code here
}//GEN-BEGIN:|1097-getter|2|
        return spacer;
    }
//</editor-fold>//GEN-END:|1097-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okOpciones ">//GEN-BEGIN:|1099-getter|0|1099-preInit
    /**
     * Returns an initialized instance of okOpciones component.
     *
     * @return the initialized component instance
     */
    public Command getOkOpciones() {
        if (okOpciones == null) {
//GEN-END:|1099-getter|0|1099-preInit
            // write pre-init user code here
okOpciones = new Command("A\u00F1adir Item", Command.OK, 0);//GEN-LINE:|1099-getter|1|1099-postInit
            // write post-init user code here
}//GEN-BEGIN:|1099-getter|2|
        return okOpciones;
    }
//</editor-fold>//GEN-END:|1099-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: formFactura ">//GEN-BEGIN:|1098-getter|0|1098-preInit
    /**
     * Returns an initialized instance of formFactura component.
     *
     * @return the initialized component instance
     */
    public Form getFormFactura() {
        if (formFactura == null) {
//GEN-END:|1098-getter|0|1098-preInit
            // write pre-init user code here
            
            formFactura = new Form("Factura", new Item[]{getStrNitCli(), getStrNomCli()});//GEN-BEGIN:|1098-getter|1|1098-postInit
            formFactura.addCommand(getImprimirFactura());
            formFactura.addCommand(getBackFactura());
            formFactura.setCommandListener(this);//GEN-END:|1098-getter|1|1098-postInit
            
//            TableData data = new TableData(3,2);
//            data.set( 1, 0, "1,0");
//          
//            data.set( 2, 0, "2,0");
//            data.set( 0, 1, "0,1");
//            data.set( 1, 1, "xxxx1,1");
//            data.set( 2, 1, "2,1");
//            data.addRow();
//           
//            if (table != null)
//                {
//                    table=null;
//                }
//            //#style defaultTable
//            table = new TableItem(data);
//            formFactura.append( table );
             if (table != null)
                {
                    table=null;
                }
            //#style defaultTable   
                table = new TableItem(2,listProductos.size()+1, de.enough.polish.ui.StyleSheet.defaulttableStyle );
                
                table.clear();
          
 table.setSelectionMode( TableItem.SELECTION_MODE_CELL );
                
                
                table.addRow();
                table.addColumn();
                table.addColumn();
               //#style heading
                table.set(0, 0,"Cant.", de.enough.polish.ui.StyleSheet.headingStyle );
                //#style heading
                table.set(1, 0,"Producto", de.enough.polish.ui.StyleSheet.headingStyle );
//                table.set(2, 0,"Bs");
                 double total=0;
                 double fiscal=0;
                 double bonidesc=0;
                 double subtotal=0;
                 double ice = 0;
//                 String cad ="";
               Vector ItemsOffline = new Vector();
               Vector ListaProductosFactura = new Vector();
               for(int i=0;i<listaProductos.size();i++)
                {
                  

//                  table.addRow();
                   
                    Products pro = (Products) listaProductos.elementAt(i);
                    ListaProductosFactura.addElement(pro);
                     InvoiceItem item = new InvoiceItem();
                     item.setBoni(pro.getBoni());
                     double costo=(double)(Double.parseDouble(pro.getCost())/Integer.parseInt(pro.getUnits()));
//                     costo = redondeo(costo,2);
                     item.setCost(""+costo);
                     item.setDesc(pro.getDesc());
                     item.setNotes(pro.getNotes());
                     item.setQty(pro.getQty());
                     ItemsOffline.addElement(item);
                    //#style bodys
                   table.set(0, i+1,""+Integer.parseInt(pro.getQty()), de.enough.polish.ui.StyleSheet.bodysStyle );     
                   //#style bodys
                   table.set(1, i+1, pro.getNotes(), de.enough.polish.ui.StyleSheet.bodysStyle );
                    //pruebas 
//                    TextField tf =  new TextField("cantidad ",null, 32, TextField.NUMERIC); 
//                    table.add(tf);
//                    table.set(i+1, tf);
//                  table.ad
                    
                    
//                   table.set(2, i+1, Double.parseDouble(pro.getCost())*Double.parseDouble(pro.getQty())+"");
    //                    p = p +"\n "+Double.parseDouble(pro.getQty())+" "+pro.getNotes()+" "+(Double.parseDouble(pro.getCost())*Double.parseDouble(pro.getQty()));
//                   double costo=(double)(Double.parseDouble(pro.getCost())/Integer.parseInt(pro.getUnits()));
                   
                   subtotal = subtotal +(costo*Double.parseDouble(pro.getQty()));
//                   String descuento = pro.getDesc().replace(',','.');
                   total = total + (costo*Double.parseDouble(pro.getQty()))-((costo*Double.parseDouble(pro.getBoni()))+Double.parseDouble(pro.getDesc()));
//                   total = total + (costo*Double.parseDouble(pro.getQty()))-((costo*Double.parseDouble(pro.getBoni()))+Double.parseDouble(descuento));
                   bonidesc = bonidesc +(double)( Double.parseDouble(pro.getBoni())*costo)+Double.parseDouble(pro.getDesc());
                 
//                   cad = cad +"\nproducto:"+pro.getNotes()+"ice:"+pro.getIce();
                   
                   if(pro.getIce().equals("1"))
                   {
//
////
////                             //caluclo ice bruto 
                       double cc= (double)(Double.parseDouble(pro.getCC())/1000);
                       System.out.println("cc:"+cc);
                       System.out.println("taxIce:"+cuenta.getIce());
                       double iceBruto = (double)(Integer.parseInt(pro.getQty())*cc*Double.parseDouble(cuenta.getIce()));
                       System.out.println("iceBruto:"+iceBruto);
                       double iceNeto = (double)(Integer.parseInt(pro.getBoni())*cc*Double.parseDouble(cuenta.getIce()));
                       System.out.println("iceNeto:"+iceNeto);
                       ice = ice + (iceBruto-iceNeto);
                       System.out.println("ice:"+ice);
////                             $iceBruto = ($qty *($pr->cc/1000)*$ice->rate);
////                             $iceNeto = (((int)$item['boni']) *($pr->cc/1000)*$ice->rate);
////                             $icetotal = $icetotal +($iceBruto-$iceNeto) ;
////                             // $fiscal = $fiscal + ($amount - ($item['qty'] *($pr->cc/1000)*$ice->rate) );
//
                   }
                
                }
               fiscal = subtotal-bonidesc-ice;
               System.out.println("subtotal:"+subtotal);
               System.out.println("bonidesc:"+bonidesc);
               System.out.println("ice:"+ice);
//                $fiscal = $amount -$bonidesc-$icetotal;
               
//               table.addRow();
//               table.set(0,listaProductos.size()+1, "Total");
//               table.set(1,listaProductos.size()+1, "");
//               table.set(2,listaProductos.size()+1, ""+total);
               
//               data.set(0,listaProductos.size()+1, "Total");
////                 data.set(1,listaProductos.size()+1,"");
////                 data.set(2,listaProductos.size()+1, ""+total);
               //total = total-bonidesc;
//             getStr1().setText("");
//               table.setLineStyle( TableItem.LINE_STYLE_SOLID, 0x000000 ); 
             getStr2().setText(""+redondeo(total,2));
             

             formFactura.append( table );
             formFactura.append(getStr2());
             table.setSelectedCell(0, 0);
//             
             Account account = new Account();
             account.setName(cuenta.getSucursal().getName());
             account.setNit("1006909025");
             if(factura!=null)
             {
                 factura = null;
             }   
             factura = new Factura();
             
             factura.setAccount(account);
            
             
             factura.setAmount(""+redondeo(total,2));
             factura.setSubtotal(""+redondeo(subtotal,2));
             factura.setFiscal(""+redondeo(fiscal,2));
             factura.setIce(""+redondeo(ice,2));
//             
             factura.setNum_auto(cuenta.getSucursal().getNumber_autho());
             factura.setAddress1(cuenta.getSucursal().getAddress1());
             factura.setAddress2(cuenta.getSucursal().getAddress2());
             factura.setCliente(cliente.getCliente());
             factura.setFecha_limite(cuenta.getSucursal().getDeadline());
             factura.setInvoice_number(cuenta.getSucursal().getInvoice_number_counter());
             factura.setLaw(cuenta.getSucursal().getLaw());
             factura.setItems(ItemsOffline);
             factura.setListaProductos(ListaProductosFactura);
             factura.setInvoice_date(com.david.torrez.DateUtil.getFechaActual());
            
            String numAutho=cuenta.getSucursal().getNumber_autho();
            String numInvoice=cuenta.getSucursal().getInvoice_number_counter();
            String nit=cliente.getCliente().getNit();
//            int nitnumerico=null;
            String nuevonit = "";
            try{
                Log.i("convertiendo a anumero :", nit);
                nuevonit=nit;
                int nitnumerico = Integer.parseInt(nit);
            }catch(Exception e){
                Log.i("no pudo convertir  ",nit);
                nuevonit = nit.substring(0, nit.length()-1);
                
                Log.i(" nuevo nit", nuevonit);
            }
            
            String date =com.david.torrez.DateUtil.getCodigoControFecha();
            String amount=factura.getAmount();
            String keyDosage=cuenta.getSucursal().getKey_dosage();
//            textField.setText(CodigoDeControl.getCodigoDeControl(nit, numInvoice, date, amount, numAutho, keyDosage));
//            CodigoDeControl cd = new CodigoDeControl();
//            String cod= cd.getCodigoDeControl(cuenta.getSucursal().getNumber_autho(),"000"+cuenta.getSucursal().getInvoice_number_counter(), cliente.getCliente().getNit(), com.david.torrez.DateUtil.getCodigoControFecha(), factura.getAmount(),cuenta.getSucursal().getKey_dosage() );
            try{
            factura.setControl_code(CodigoDeControl.getCodigoDeControl(nuevonit, numInvoice, date, amount, numAutho, keyDosage));
            }catch(Exception e){
                System.out.println("Error en el Codigo de Control:" );
                System.out.println("Numero de autorizacion:"+numAutho );
                System.out.println("numInvoice:"+numInvoice );
                System.out.println("Nit:"+nit );
                System.out.println("Fecha:"+date );
                System.out.println("Monto:"+amount );
                System.out.println("Llave de Dosificacion:"+keyDosage );
            }
//             getStr2().setText("numautho:"+cuenta.getSucursal().getNumber_autho()+"\n numFac:000"+cuenta.getSucursal().getInvoice_number_counter()+"\nnit:"+ account.getNit()+"\nfecha:"+ com.david.torrez.DateUtil.getCodigoControFecha() +"\ntotal:"+factura.getAmount()+"\ndosificacion:"+cuenta.getSucursal().getKey_dosage()+"\n CodContro:" );
    //revisar este trae problemas XD
//             factura.setControl_code(CodigoDeControl.getCodigoDeControl(cuenta.getSucursal().getNumber_autho(),"000"+cuenta.getSucursal().getInvoice_number_counter(), account.getNit(), com.david.torrez.DateUtil.getCodigoControFecha(), ""+total,cuenta.getSucursal().getKey_dosage() ));
//             
             //espero no falte nada estaria listo para imprimir
             
//             factura.setInvoice_number(mensaje);
           
//             formFactura.append(getStr1());
//             getStr2().setText("Total a Pagar:");
//             formFactura.append(getStr1());
             
             
        }//GEN-BEGIN:|1098-getter|2|
        return formFactura;
    }
//</editor-fold>//GEN-END:|1098-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: methodCliente ">//GEN-BEGIN:|1108-entry|0|1109-preAction
    /**
     * Performs an action assigned to the methodCliente entry-point.
     */
    public void methodCliente() {
//GEN-END:|1108-entry|0|1109-preAction
        // Buscador de clientes
        if(!estaVacio(txtNit))
        {
        pantalla = CLIENTE;
        
//        Cargando();
       
        Thread t = new Thread()
        {
            public void run()
            {
                   
                
                Log.i("cliente ", "thread  cliente consumido");
                index = buscarCliente(clientes,txtNit.getString());
                if(index!=-1)
                {
                    
                    cliente = new Cliente();
                    cliente.setCliente((Clients)clientes.elementAt(index));
                    index=-1;
                        cambiarPantalla();
                        
                       txtNitDat.setText(cliente.getCliente().getNit());
                       txtNomDat.setText(cliente.getCliente().getRazon());
                    
                }
                else
                {   
                    //Repinta la pantalla antes de que esta esetes
                    switchDisplayable(null,getFormCliente());
                    switchDisplayable(alerta("Cliente No Encontrado"," No se pudo encontrar al cliente "+txtNit.getString()+", verifique que se encuentre en su grupo de clientes"),getFormCliente());
//                    switchDisplayable(getAlerta("No se encontro al cliente","Cliente invalido o no registrado!",CLIENTE), getFormCliente());
                }   

                
            }
      
        };       
        t.start();

        
        }
        else
        {
           switchDisplayable(getAlerta("Cuadro de Texto Vacio","Debe ingresar un codigo de cliente para la busqueda",CLIENTE), getFormCliente());
        }
        
        /*
switchDisplayable (null, getFormDatosCliente ());//GEN-BEGIN:|1108-entry|1|1109-postAction
//GEN-END:|1108-entry|1|1109-postAction
        */
                 
            
        
        
        // write post-action user code here
}//GEN-BEGIN:|1108-entry|2|
//</editor-fold>//GEN-END:|1108-entry|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: strNomCli ">//GEN-BEGIN:|1113-getter|0|1113-preInit
    /**
     * Returns an initialized instance of strNomCli component.
     *
     * @return the initialized component instance
     */
    public StringItem getStrNomCli() {
        if (strNomCli == null) {
//GEN-END:|1113-getter|0|1113-preInit
            // write pre-init user code here
strNomCli = new StringItem("Nombre:", null);//GEN-BEGIN:|1113-getter|1|1113-postInit
            strNomCli.setLayout(ImageItem.LAYOUT_LEFT);//GEN-END:|1113-getter|1|1113-postInit
            // write post-init user code here
}//GEN-BEGIN:|1113-getter|2|
        return strNomCli;
    }
//</editor-fold>//GEN-END:|1113-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: strNitCli ">//GEN-BEGIN:|1114-getter|0|1114-preInit
    /**
     * Returns an initialized instance of strNitCli component.
     *
     * @return the initialized component instance
     */
    public StringItem getStrNitCli() {
        if (strNitCli == null) {
//GEN-END:|1114-getter|0|1114-preInit
            // write pre-init user code here
strNitCli = new StringItem("Nit:", null);//GEN-BEGIN:|1114-getter|1|1114-postInit
            strNitCli.setLayout(ImageItem.LAYOUT_LEFT);//GEN-END:|1114-getter|1|1114-postInit
            // write post-init user code here
}//GEN-BEGIN:|1114-getter|2|
        return strNitCli;
    }
//</editor-fold>//GEN-END:|1114-getter|2|

public StringItem getStr2()
{   
    if(str2==null)
    {
        str2 = new StringItem("Total a Pagar Bs: ",null);
    }
    return str2;
}
public StringItem getStr1()
{   
    if(str1==null)
    {
        str1 = new StringItem("",null);
    }
    return str1;
}

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okLista ">//GEN-BEGIN:|1116-getter|0|1116-preInit
    /**
     * Returns an initialized instance of okLista component.
     *
     * @return the initialized component instance
     */
    public Command getOkLista() {
        if (okLista == null) {
//GEN-END:|1116-getter|0|1116-preInit
            // write pre-init user code here
okLista = new Command("Ok", Command.OK, 0);//GEN-LINE:|1116-getter|1|1116-postInit
            // write post-init user code here
}//GEN-BEGIN:|1116-getter|2|
        return okLista;
    }
//</editor-fold>//GEN-END:|1116-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand ">//GEN-BEGIN:|1121-getter|0|1121-preInit
    /**
     * Returns an initialized instance of okCommand component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand() {
        if (okCommand == null) {
//GEN-END:|1121-getter|0|1121-preInit
            // write pre-init user code here
okCommand = new Command("Ok", Command.OK, 0);//GEN-LINE:|1121-getter|1|1121-postInit
            // write post-init user code here
}//GEN-BEGIN:|1121-getter|2|
        return okCommand;
    }
//</editor-fold>//GEN-END:|1121-getter|2|



//<editor-fold defaultstate="collapsed" desc=" Generated Getter: listProductos ">//GEN-BEGIN:|1124-getter|0|1124-preInit
    /**
     * Returns an initialized instance of listProductos component.
     *
     * @return the initialized component instance
     */
    public List getListProductos() {
        if (listProductos == null) {
//GEN-END:|1124-getter|0|1124-preInit
            // write pre-init user code here
listProductos = new List("Productos Seleccionados", Choice.IMPLICIT);//GEN-BEGIN:|1124-getter|1|1124-postInit
            listProductos.addCommand(getOkOpciones());
            listProductos.addCommand(getBackMenu());
            listProductos.addCommand(getOkCommand4());
            listProductos.setCommandListener(this);//GEN-END:|1124-getter|1|1124-postInit
            // write post-init user code here
}//GEN-BEGIN:|1124-getter|2|
        return listProductos;
    }
//</editor-fold>//GEN-END:|1124-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: listProductosAction ">//GEN-BEGIN:|1124-action|0|1124-preAction
    /**
     * Performs an action assigned to the selected list element in the
     * listProductos component.
     */
    public void listProductosAction() {
//GEN-END:|1124-action|0|1124-preAction
        // enter pre-action user code here
String __selectedString = getListProductos().getString(getListProductos().getSelectedIndex());//GEN-LINE:|1124-action|1|1124-postAction
        // enter post-action user code here
}//GEN-BEGIN:|1124-action|2|
//</editor-fold>//GEN-END:|1124-action|2|



//<editor-fold defaultstate="collapsed" desc=" Generated Getter: backMenu ">//GEN-BEGIN:|1132-getter|0|1132-preInit
    /**
     * Returns an initialized instance of backMenu component.
     *
     * @return the initialized component instance
     */
    public Command getBackMenu() {
        if (backMenu == null) {
//GEN-END:|1132-getter|0|1132-preInit
            // write pre-init user code here
backMenu = new Command("Atras", Command.BACK, 0);//GEN-LINE:|1132-getter|1|1132-postInit
            // write post-init user code here
}//GEN-BEGIN:|1132-getter|2|
        return backMenu;
    }
//</editor-fold>//GEN-END:|1132-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: ImprimirFactura ">//GEN-BEGIN:|1139-getter|0|1139-preInit
    /**
     * Returns an initialized instance of ImprimirFactura component.
     *
     * @return the initialized component instance
     */
    public Command getImprimirFactura() {
        if (ImprimirFactura == null) {
//GEN-END:|1139-getter|0|1139-preInit
            // write pre-init user code here
ImprimirFactura = new Command("Imprimir ", Command.OK, 0);//GEN-LINE:|1139-getter|1|1139-postInit
            // write post-init user code here
}//GEN-BEGIN:|1139-getter|2|
        return ImprimirFactura;
    }
//</editor-fold>//GEN-END:|1139-getter|2|







//<editor-fold defaultstate="collapsed" desc=" Generated Method: methodImprimir ">//GEN-BEGIN:|1142-entry|0|1143-preAction
    /**
     * Performs an action assigned to the methodImprimir entry-point.
     */
    public void methodImprimir() {
//GEN-END:|1142-entry|0|1143-preAction
        // write pre-action user code here
        pantalla=GUARDARFACTURA;
        
        
        
        //#style mailAlert
        Alert alert = new Alert("Emitir Factura", "Esta seguro de emitir factura?", null, AlertType.CONFIRMATION, de.enough.polish.ui.StyleSheet.mailalertStyle );
        final Command cmdYes = new Command("Yes", Command.OK, 1);
        final Command cmdNo =   new Command("No", Command.CANCEL, 1);
        alert.addCommand(cmdYes);
        alert.addCommand(cmdNo);
        alert.setCommandListener(new CommandListener() {
            public void commandAction(Command c, Displayable d) {
                if (c == cmdYes) {
                    if(!DateUtil.VerificarFecha(cuenta.getSucursal().getDeadline()))
                    {
                        guardarFactura(true);
                    }
                    else
                    {
                        switchDisplayable (alerta("Fecha Vencida"," La fecha limite de emision de la sucursal esta vencida por lo cual no puede emitir facturas"), getFormFactura()); 
                    }
                    
                } else {
//                    
                     if(!DateUtil.VerificarFecha(cuenta.getSucursal().getDeadline()))
                    {
                        guardarFactura(false);
                    }
                    else
                    {
                        switchDisplayable (alerta("Fecha Vencida"," La fecha limite de emision de la sucursal esta vencida por lo cual no puede emitir facturas"), getFormFactura()); 
                    }
////               
                }               
            }

            private void guardarFactura(boolean isInvoice) {
                
//                solicitudFactura sf= new solicitudFactura();
//                sf.setClient_id(factura.getCliente().getId());
//                sf.setCod_control(factura.getControlCode());
//                sf.setDate(factura.getInvoiceDate());
//                sf.setName(factura.getCliente().getName());
//                sf.setNit(factura.getCliente().getNit());
//                if(isInvoice)
//                {
//                     sf.setInvoice_number(factura.getInvoiceNumber());
//                }
//                else
//                {
//                    sf.setInvoice_number("0");
//                }
//                
//                sf.setProductos(listaProductos);
                //guardando datos del cliente
                clientId=cliente.getCliente().getId();

                /*guardar esta factura para el offline XD
                 estos son los datos a guardar 
                    {"invoice_items":[{"qty":"2","id":"2","boni":"1","desc":"3"}],"client_id":"1","nit":"6047054","name":"torrez","public_id":"1","invoice_date":"14-10-2014","invoice_number":"0001"}
                */

                /*guardando factura para recuperarlo de manera offline*/
//                 String guardar =solicitudFactura.toJSON(sf);
                 FacturaOffline fac = new FacturaOffline();

        //         fac.setFactura(guardar);
                 fac.setListaProductos(factura.getListaProductos());
               
                 fac.setClienteId(factura.getCliente().getId());
                 fac.setItems(factura.getInvoiceItems());
                 fac.setCodCli(cliente.getCliente().getId());
                 fac.setAddress1(factura.getAddress1());
                 fac.setAddress2(factura.getAddress2());
                 fac.setAmount(factura.getAmount());
                 fac.setControl_code(factura.getControlCode());
                 fac.setFecha_limite(factura.getFechaLimite());
                 fac.setFiscal(factura.getFiscal());
                 fac.setIce(factura.getIce());
                 fac.setInvoice_date(factura.getInvoiceDate());
                 if(isInvoice)
                 {
                     fac.setInvoice_number(factura.getInvoiceNumber());
                 }else
                 {
                     fac.setInvoice_number("0");
                 }
                 fac.setLaw(factura.getLaw());
                 fac.setNameCliente(factura.getCliente().getRazon());
                 fac.setNameCuenta(factura.getAccount().getName());
                 fac.setNiCuenta(factura.getAccount().getNit());
                 fac.setNitCliente(factura.getCliente().getNit());
                 fac.setNum_auto(factura.getNumAuto());
                 fac.setSubtotal(factura.getSubtotal());
                 fac.setIsInvoice(isInvoice);
                 //llave de usuario 
//                 fac.setLlaveUsuario(llave);
                 

        //         fac.setInvoice(factura);
                 facturas.addElement(fac);
                 backup(fac);
                   try {
                                storage.save( facturas, "facturas");
                        } catch (IOException e) {

                                System.out.println("Unable to store facturas XD" + e );
                        }
                   if(isInvoice)
                   {
                       int contador= Integer.parseInt(cuenta.getSucursal().getInvoice_number_counter());
        //              
                                contador++;
                                cuenta.getSucursal().setInvoice_number_counter(""+contador);
                                sucursal.setInvoice_number_counter(""+contador);
                                 try {
                                                storage.save( sucursal, "sucursal");
                                        } catch (IOException e) {

                                                System.out.println("Unable to store sucursal XD" + e );
                                        }
                   }
                    
                 /**************************************************************/
                 
                if(isInvoice)
                {
                 Cargando();
                
                Thread t;       
                t = new Thread()
                {
                    public void run()
                    {

                            try {
        //                        factura = new Factura(rest.getRespuesta()); se inicializo en el getFormFactura
                                 
                                    
                                String nit1=factura.getAccount().getNit();
                                String invoice_number2=factura.getInvoiceNumber();
                                String num_auto3=factura.getNumAuto();
                                String fecha_emision4=factura.getInvoiceDate();
                                String total5=factura.getAmount();
                                String credito_fiscal6=factura.getFiscal();
                                String cod_control7=factura.getControlCode();
                                String ci_8=factura.getCliente().getNit();
                                String ice_9=factura.getIce();
                                String importe_ventas10="0.0";
                                String sincredito_fiscal11="0.0";
                                double descuento = Double.parseDouble(factura.getSubtotal())-Double.parseDouble(factura.getAmount());
                                String descuentos_bonificacion=""+redondeo(descuento,2);//;
                                
                                
                                String datos =nit1+"|"+invoice_number2+"|"+num_auto3+"|"+fecha_emision4+"|"+total5+"|"
                                             +credito_fiscal6+"|"+cod_control7+"|"+ci_8+"|"+ice_9+"|"+importe_ventas10+"|"
                                             +sincredito_fiscal11+"|"+descuentos_bonificacion;
                                
                                
//                                qrCodeImage = encode(datos);

                                byte imagen[] =  ba.readImage(BMPGenerator.encodeBMP(qrCodeImage));
        
//                                Vector v= VectorProductos(factura.getInvoiceItems());
                                //mejorando la velocidad de impresion
   
                                 Vector vec = TextLine(factura.getLaw());
//                                Imprimir(factura,imagen,v,vec);
                                cambiarPantalla();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }


                    }

                };
                
                t.start();
                }
                else
                {
                    cambiarPantalla();
                }
            }
        });
        switchDisplayable (alert, getFormFactura()); 
        
        /* Linea deshabilitada debido a funcion optimizada cambiarPantalla.
switchDisplayable (null, getListMenu ());//GEN-BEGIN:|1142-entry|1|1143-postAction
//GEN-END:|1142-entry|1|1143-postAction
         */
    
        
        
        // write post-action user code here
}//GEN-BEGIN:|1142-entry|2|
//</editor-fold>//GEN-END:|1142-entry|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand1 ">//GEN-BEGIN:|1147-getter|0|1147-preInit
    /**
     * Returns an initialized instance of okCommand1 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand1() {
        if (okCommand1 == null) {
//GEN-END:|1147-getter|0|1147-preInit
            // write pre-init user code here
okCommand1 = new Command("ingresar nombre", Command.OK, 0);//GEN-LINE:|1147-getter|1|1147-postInit
            // write post-init user code here
}//GEN-BEGIN:|1147-getter|2|
        return okCommand1;
    }
//</editor-fold>//GEN-END:|1147-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand2 ">//GEN-BEGIN:|1150-getter|0|1150-preInit
    /**
     * Returns an initialized instance of okCommand2 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand2() {
        if (okCommand2 == null) {
//GEN-END:|1150-getter|0|1150-preInit
            // write pre-init user code here
okCommand2 = new Command("Ok", Command.OK, 0);//GEN-LINE:|1150-getter|1|1150-postInit
            // write post-init user code here
}//GEN-BEGIN:|1150-getter|2|
        return okCommand2;
    }
//</editor-fold>//GEN-END:|1150-getter|2|





//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okMenu ">//GEN-BEGIN:|1160-getter|0|1160-preInit
    /**
     * Returns an initialized instance of okMenu component.
     *
     * @return the initialized component instance
     */
    public Command getOkMenu() {
        if (okMenu == null) {
//GEN-END:|1160-getter|0|1160-preInit
            // write pre-init user code here
okMenu = new Command("OK", Command.OK, 0);//GEN-LINE:|1160-getter|1|1160-postInit
            // write post-init user code here
}//GEN-BEGIN:|1160-getter|2|
        return okMenu;
    }
//</editor-fold>//GEN-END:|1160-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: cancelCommand ">//GEN-BEGIN:|1163-getter|0|1163-preInit
    /**
     * Returns an initialized instance of cancelCommand component.
     *
     * @return the initialized component instance
     */
    public Command getCancelCommand() {
        if (cancelCommand == null) {
//GEN-END:|1163-getter|0|1163-preInit
            // write pre-init user code here
cancelCommand = new Command("Cancel", Command.CANCEL, 0);//GEN-LINE:|1163-getter|1|1163-postInit
            // write post-init user code here
}//GEN-BEGIN:|1163-getter|2|
        return cancelCommand;
    }
//</editor-fold>//GEN-END:|1163-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: backSalir ">//GEN-BEGIN:|1165-getter|0|1165-preInit
    /**
     * Returns an initialized instance of backSalir component.
     *
     * @return the initialized component instance
     */
    public Command getBackSalir() {
        if (backSalir == null) {
//GEN-END:|1165-getter|0|1165-preInit
            // write pre-init user code here
backSalir = new Command("Atras", Command.BACK, 0);//GEN-LINE:|1165-getter|1|1165-postInit
            // write post-init user code here
}//GEN-BEGIN:|1165-getter|2|
        return backSalir;
    }
//</editor-fold>//GEN-END:|1165-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: listMenu ">//GEN-BEGIN:|1154-getter|0|1154-preInit
    /**
     * Returns an initialized instance of listMenu component.
     *
     * @return the initialized component instance
     */
    public List getListMenu() {
        if (listMenu == null) {
//GEN-END:|1154-getter|0|1154-preInit
            // write pre-init user code here
            
            listMenu = new List("list", Choice.IMPLICIT);//GEN-BEGIN:|1154-getter|1|1154-postInit
            listMenu.append("   A\u00F1adir producto", getImage21());
            listMenu.append("  A\u00F1adir Cliente", getImage19());
            listMenu.append("  Facturar", getImage15());
            listMenu.addCommand(getOkMenu());
            listMenu.addCommand(getBackSalir());
            listMenu.setCommandListener(this);
            listMenu.setSelectedFlags(new boolean[]{false, false, false});//GEN-END:|1154-getter|1|1154-postInit
            // write post-init user code here
            
            listMenu.setTitle("Menu Factura Usuario:"+this.user.getUsuario());
        }//GEN-BEGIN:|1154-getter|2|
        return listMenu;
    }
//</editor-fold>//GEN-END:|1154-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: listMenuAction ">//GEN-BEGIN:|1154-action|0|1154-preAction
    /**
     * Performs an action assigned to the selected list element in the listMenu
     * component.
     */
    public void listMenuAction() {
//GEN-END:|1154-action|0|1154-preAction
        // enter pre-action user code here
String __selectedString = getListMenu().getString(getListMenu().getSelectedIndex());//GEN-BEGIN:|1154-action|1|1158-preAction
        if (__selectedString != null) {
            if (__selectedString.equals("   A\u00F1adir producto")) {//GEN-END:|1154-action|1|1158-preAction
                // write pre-action user code here
                if(product_state==SINPRODUCTO)
                {
                    switchDisplayable(getSelectVenta(), getListProductos());
                }
                else
                {
                switchDisplayable(null, getListProductos());
                }
                
                /*
switchDisplayable (null, getListProductos ());//GEN-BEGIN:|1154-action|2|1158-postAction
//GEN-END:|1154-action|2|1158-postAction
                // write post-action user code here
                */
            } else if (__selectedString.equals("  A\u00F1adir Cliente")) {//GEN-LINE:|1154-action|3|1159-preAction
                // write pre-action user code here
    if(cliente==null)
    {
        switchDisplayable(null, getFormCliente());//GEN-LINE:|1154-action|4|1159-postAction
        LimpiarCliente();
    }else
    {
        switchDisplayable(alertaCliente(), getFormCliente());
    
    }
// write post-action user code here
} else if (__selectedString.equals("  Facturar")) {//GEN-LINE:|1154-action|5|1157-preAction
                // write pre-action user code here
               pantalla = LISTMENU;
              if(cliente!=null&&listaProductos.size()>0)
              {
                formFactura = null;
                  switchDisplayable(null, getFormFactura());//GEN-LINE:|1154-action|6|1157-postAction
              strNitCli.setText(cliente.getCliente().getNit());
              strNomCli.setText(cliente.getCliente().getName());
              }
              else
              {
                  String msg="para facturar necesita: ";
                  if(cliente==null)
                  {
                      msg = msg+"\n -Adicionar Cliente.";
                  }
                  if(listaProductos.size()==0)
                  {
                      msg= msg+"\n -Adicionar al menos un producto.";
                  }
                
                  switchDisplayable(alerta("Antes de Facturar",msg), getListMenu());
              }
              
//              Object[] data;
//                while ( (data = getNextData()) != null ) {
//                        int row = table.addRow();
//                        for (int i=0; i<data.length; i++ ) {
//                                if (i  >= table.getNumberOfColumns() ) {
//                                        table.addColumn();
//                                }
//                                table.set( i, row, data[i] );
//                        }
//                }
              
//               table.addRow();
//               table.addColumn();
//               table.addColumn();
//               table.addColumn();
//               table.set(0, 0,"Cant.");
//               table.set(1, 0,"Producto");
//               table.set(2, 0,"Bs");
//               double total=0;
//          
//               for(int i=0;i<listaProductos.size();i++)
//                {
//                  table.addRow();
//                    Products pro = (Products) listaProductos.elementAt(i);
//                   table.set(0, i+1, pro.getQty());
//                   table.set(1, i+1, pro.getNotes());
//                   table.set(2, i+1, Double.parseDouble(pro.getCost())*Double.parseDouble(pro.getQty())+"");
//    //                    p = p +"\n "+Double.parseDouble(pro.getQty())+" "+pro.getNotes()+" "+(Double.parseDouble(pro.getCost())*Double.parseDouble(pro.getQty()));
//                   total = total + (Double.parseDouble(pro.getCost())*Double.parseDouble(pro.getQty()));
//                }
//                table.addRow();
//                table.set(0, listaProductos.size(),"Cant.");
//                table.set(1, listaProductos.size(),"Producto");
//                table.set(2, listaProductos.size(),"Bs");
//              // columna fila
//              if(data!=null)
//              {
//                  data = null;
//              }
//               data = new TableData(3,(listaProductos.size()+3));
//  //            formFactura.setTitle(""+listaProductos.size());
//  //            Products pro = (Products) listaProductos.elementAt(0);
//  //            data.set( 1, 0, "1,0");
//  //            data.set( 2, 0, "2,0");
//  //            data.set( 0, 1, "0,1");
//  //            data.set( 1, 1, "xxxx1,1");
//  //            data.set( 2, 1, "2,1");
//               data.set(0,0, "Cant.");
//               data.set(1,0,"Producto");
//               data.set(2,0, "Bs");
//               double total=0;
//                for(int i=0;i<listaProductos.size();i++)
//                {
//                   Products pro = (Products) listaProductos.elementAt(i);
//                   data.set(0, i+1, pro.getQty());
//                   data.set(1, i+1, pro.getNotes());
//                   data.set(2, i+1, Double.parseDouble(pro.getCost())*Double.parseDouble(pro.getQty())+"");
//    //                    p = p +"\n "+Double.parseDouble(pro.getQty())+" "+pro.getNotes()+" "+(Double.parseDouble(pro.getCost())*Double.parseDouble(pro.getQty()));
//                        total = total + (Double.parseDouble(pro.getCost())*Double.parseDouble(pro.getQty()));
//                }
//                 data.set(0,listaProductos.size()+1, "Total");
//                 data.set(1,listaProductos.size()+1,"");
//                 data.set(2,listaProductos.size()+1, ""+total);
//                //#style defaultTable
//                TableItem table = new TableItem(data);
//                formFactura.append( table );            

                // write post-action user code here
}//GEN-BEGIN:|1154-action|7|1154-postAction
        }//GEN-END:|1154-action|7|1154-postAction
        // enter post-action user code here
}//GEN-BEGIN:|1154-action|8|
//</editor-fold>//GEN-END:|1154-action|8|






//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okSelOp ">//GEN-BEGIN:|1174-getter|0|1174-preInit
    /**
     * Returns an initialized instance of okSelOp component.
     *
     * @return the initialized component instance
     */
    public Command getOkSelOp() {
        if (okSelOp == null) {
//GEN-END:|1174-getter|0|1174-preInit
            // write pre-init user code here
okSelOp = new Command("Ok", Command.OK, 0);//GEN-LINE:|1174-getter|1|1174-postInit
            // write post-init user code here
}//GEN-BEGIN:|1174-getter|2|
        return okSelOp;
    }
//</editor-fold>//GEN-END:|1174-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: backFactura ">//GEN-BEGIN:|1178-getter|0|1178-preInit
    /**
     * Returns an initialized instance of backFactura component.
     *
     * @return the initialized component instance
     */
    public Command getBackFactura() {
        if (backFactura == null) {
//GEN-END:|1178-getter|0|1178-preInit
            // write pre-init user code here
backFactura = new Command("Cancelar", Command.BACK, 0);//GEN-LINE:|1178-getter|1|1178-postInit
            // write post-init user code here
}//GEN-BEGIN:|1178-getter|2|
        return backFactura;
    }
//</editor-fold>//GEN-END:|1178-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCliente ">//GEN-BEGIN:|1184-getter|0|1184-preInit
    /**
     * Returns an initialized instance of backCliente component.
     *
     * @return the initialized component instance
     */
    public Command getBackCliente() {
        if (backCliente == null) {
//GEN-END:|1184-getter|0|1184-preInit
            // write pre-init user code here
backCliente = new Command("Atras", Command.OK, 0);//GEN-LINE:|1184-getter|1|1184-postInit
            // write post-init user code here
}//GEN-BEGIN:|1184-getter|2|
        return backCliente;
    }
//</editor-fold>//GEN-END:|1184-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand3 ">//GEN-BEGIN:|1188-getter|0|1188-preInit
    /**
     * Returns an initialized instance of okCommand3 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand3() {
        if (okCommand3 == null) {
//GEN-END:|1188-getter|0|1188-preInit
            // write pre-init user code here
okCommand3 = new Command("Adicionar", Command.OK, 0);//GEN-LINE:|1188-getter|1|1188-postInit
            // write post-init user code here
}//GEN-BEGIN:|1188-getter|2|
        return okCommand3;
    }
//</editor-fold>//GEN-END:|1188-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand4 ">//GEN-BEGIN:|1191-getter|0|1191-preInit
    /**
     * Returns an initialized instance of okCommand4 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand4() {
        if (okCommand4 == null) {
//GEN-END:|1191-getter|0|1191-preInit
            // write pre-init user code here
okCommand4 = new Command("Eliminar Item", Command.OK, 0);//GEN-LINE:|1191-getter|1|1191-postInit
            // write post-init user code here
}//GEN-BEGIN:|1191-getter|2|
        return okCommand4;
    }
//</editor-fold>//GEN-END:|1191-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand ">//GEN-BEGIN:|1193-getter|0|1193-preInit
    /**
     * Returns an initialized instance of backCommand component.
     *
     * @return the initialized component instance
     */
    public Command getBackCommand() {
        if (backCommand == null) {
//GEN-END:|1193-getter|0|1193-preInit
            // write pre-init user code here
backCommand = new Command("Atras", Command.BACK, 0);//GEN-LINE:|1193-getter|1|1193-postInit
            // write post-init user code here
}//GEN-BEGIN:|1193-getter|2|
        return backCommand;
    }
//</editor-fold>//GEN-END:|1193-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image10 ">//GEN-BEGIN:|1196-getter|0|1196-preInit
    /**
     * Returns an initialized instance of image10 component.
     *
     * @return the initialized component instance
     */
    public Image getImage10() {
        if (image10 == null) {
//GEN-END:|1196-getter|0|1196-preInit
            // write pre-init user code here
try {//GEN-BEGIN:|1196-getter|1|1196-@java.io.IOException
                image10 = Image.createImage("/FacturaVirtual.png");
            } catch (java.io.IOException e) {//GEN-END:|1196-getter|1|1196-@java.io.IOException
                e.printStackTrace();
            }//GEN-LINE:|1196-getter|2|1196-postInit
            // write post-init user code here
}//GEN-BEGIN:|1196-getter|3|
        return image10;
    }
//</editor-fold>//GEN-END:|1196-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand5 ">//GEN-BEGIN:|1198-getter|0|1198-preInit
    /**
     * Returns an initialized instance of okCommand5 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand5() {
        if (okCommand5 == null) {
//GEN-END:|1198-getter|0|1198-preInit
            // write pre-init user code here
okCommand5 = new Command("Ok", Command.OK, 0);//GEN-LINE:|1198-getter|1|1198-postInit
            // write post-init user code here
}//GEN-BEGIN:|1198-getter|2|
        return okCommand5;
    }
//</editor-fold>//GEN-END:|1198-getter|2|



//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand6 ">//GEN-BEGIN:|1203-getter|0|1203-preInit
    /**
     * Returns an initialized instance of okCommand6 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand6() {
        if (okCommand6 == null) {
//GEN-END:|1203-getter|0|1203-preInit
            // write pre-init user code here
okCommand6 = new Command("Crear", Command.OK, 0);//GEN-LINE:|1203-getter|1|1203-postInit
            // write post-init user code here
}//GEN-BEGIN:|1203-getter|2|
        return okCommand6;
    }
//</editor-fold>//GEN-END:|1203-getter|2|



//<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand1 ">//GEN-BEGIN:|1206-getter|0|1206-preInit
    /**
     * Returns an initialized instance of backCommand1 component.
     *
     * @return the initialized component instance
     */
    public Command getBackCommand1() {
        if (backCommand1 == null) {
//GEN-END:|1206-getter|0|1206-preInit
            // write pre-init user code here
backCommand1 = new Command("Limpiar", Command.BACK, 0);//GEN-LINE:|1206-getter|1|1206-postInit
            // write post-init user code here
}//GEN-BEGIN:|1206-getter|2|
        return backCommand1;
    }
//</editor-fold>//GEN-END:|1206-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand7 ">//GEN-BEGIN:|1208-getter|0|1208-preInit
    /**
     * Returns an initialized instance of okCommand7 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand7() {
        if (okCommand7 == null) {
//GEN-END:|1208-getter|0|1208-preInit
            // write pre-init user code here
okCommand7 = new Command("AgregarProducto", Command.OK, 0);//GEN-LINE:|1208-getter|1|1208-postInit
            // write post-init user code here
}//GEN-BEGIN:|1208-getter|2|
        return okCommand7;
    }
//</editor-fold>//GEN-END:|1208-getter|2|



//<editor-fold defaultstate="collapsed" desc=" Generated Getter: Problemas ">//GEN-BEGIN:|1211-getter|0|1211-preInit
    /**
     * Returns an initialized instance of Problemas component.
     *
     * @return the initialized component instance
     */
    public Alert getProblemas() {
        if (Problemas == null) {
//GEN-END:|1211-getter|0|1211-preInit
           
            //#style mailAlert

            Problemas = new Alert(getAlertTitulo(),getAlertMensaje(),getImage11(),AlertType.CONFIRMATION, de.enough.polish.ui.StyleSheet.mailalertStyle );
            final Command cmdYes = new Command("Aceptar", Command.OK, 1);
           
            Problemas.addCommand(cmdYes);
        
            Problemas.setCommandListener(new CommandListener() {
                    public void commandAction(Command c, Displayable d) {
                            if (c == cmdYes) {
                                
                              retornarPantalla();
                               
                            }			
                    }
            });
                   
           
            /* Codigo de netbeans 
Problemas = new Alert ("alert", "Problemas XD", null, null);//GEN-BEGIN:|1211-getter|1|1211-postInit
             Problemas.setTimeout (Alert.FOREVER);
//GEN-END:|1211-getter|1|1211-postInit
            */
            // write post-init user code here
            
        }//GEN-BEGIN:|1211-getter|2|
        return Problemas;
    }
//</editor-fold>//GEN-END:|1211-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: stopCommand ">//GEN-BEGIN:|1218-getter|0|1218-preInit
    /**
     * Returns an initialized instance of stopCommand component.
     *
     * @return the initialized component instance
     */
    public Command getStopCommand() {
        if (stopCommand == null) {
//GEN-END:|1218-getter|0|1218-preInit
            // write pre-init user code here
stopCommand = new Command("Stop", Command.STOP, 0);//GEN-LINE:|1218-getter|1|1218-postInit
            // write post-init user code here
}//GEN-BEGIN:|1218-getter|2|
        return stopCommand;
    }
//</editor-fold>//GEN-END:|1218-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: listPrincipal ">//GEN-BEGIN:|1215-getter|0|1215-preInit
    /**
     * Returns an initialized instance of listPrincipal component.
     *
     * @return the initialized component instance
     */
    public List getListPrincipal() {
        if (listPrincipal == null) {
//GEN-END:|1215-getter|0|1215-preInit
            // write pre-init user code here
listPrincipal = new List("list", Choice.IMPLICIT);//GEN-BEGIN:|1215-getter|1|1215-postInit
            listPrincipal.append("Nueva Factura", getImage14());
            listPrincipal.append("Reimpresion de Factura", getImage22());
            listPrincipal.append("Sincronizar Informacion", getImage23());
            listPrincipal.append("Salir de la Aplicacion", getImage24());
            listPrincipal.addCommand(getOkCommand8());
            listPrincipal.addCommand(getBackCommand2());
            listPrincipal.setCommandListener(this);
            listPrincipal.setSelectedFlags(new boolean[]{false, false, false, false});//GEN-END:|1215-getter|1|1215-postInit
            // write post-init user code here
            listPrincipal.setTitle("Usuario:"+this.user.getUsuario());
        }//GEN-BEGIN:|1215-getter|2|
        return listPrincipal;
    }
//</editor-fold>//GEN-END:|1215-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: listPrincipalAction ">//GEN-BEGIN:|1215-action|0|1215-preAction
    /**
     * Performs an action assigned to the selected list element in the
     * listPrincipal component.
     */
    public void listPrincipalAction() {
//GEN-END:|1215-action|0|1215-preAction
        // enter pre-action user code here
String __selectedString = getListPrincipal().getString(getListPrincipal().getSelectedIndex());//GEN-BEGIN:|1215-action|1|1220-preAction
        if (__selectedString != null) {
            if (__selectedString.equals("Nueva Factura")) {//GEN-END:|1215-action|1|1220-preAction
                // write pre-action user code here
switchDisplayable(null, getListMenu());//GEN-LINE:|1215-action|2|1220-postAction
            NuevaFactura();
// write post-action user code here
} else if (__selectedString.equals("Reimpresion de Factura")) {//GEN-LINE:|1215-action|3|1221-preAction
                // write pre-action user code here
switchDisplayable(null, getFormRClient());//GEN-LINE:|1215-action|4|1221-postAction
                // write post-action user code here
      getTxtCodCliente().setText("");
            } else if (__selectedString.equals("Sincronizar Informacion")) {//GEN-LINE:|1215-action|5|1392-preAction
 // write pre-action user code here
                pantalla=SINCRONIZAR;
                switchDisplayable(null, getFormSincronizacion());//GEN-LINE:|1215-action|6|1392-postAction
 getStrNumClientes().setText(""+clientes.size()); 
 getStrNumFacturas().setText(""+facturas.size());
 verificarFecha();
// write post-action user code here
} else if (__selectedString.equals("Salir de la Aplicacion")) {//GEN-LINE:|1215-action|7|1418-preAction
 // write pre-action user code here
exitMIDlet();//GEN-LINE:|1215-action|8|1418-postAction
 // write post-action user code here
}//GEN-BEGIN:|1215-action|9|1215-postAction
        }//GEN-END:|1215-action|9|1215-postAction
        // enter post-action user code here
}//GEN-BEGIN:|1215-action|10|
//</editor-fold>//GEN-END:|1215-action|10|



//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand8 ">//GEN-BEGIN:|1223-getter|0|1223-preInit
    /**
     * Returns an initialized instance of okCommand8 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand8() {
        if (okCommand8 == null) {
//GEN-END:|1223-getter|0|1223-preInit
            // write pre-init user code here
okCommand8 = new Command("Seleccionar", "<null>", Command.OK, 0);//GEN-LINE:|1223-getter|1|1223-postInit
            // write post-init user code here
}//GEN-BEGIN:|1223-getter|2|
        return okCommand8;
    }
//</editor-fold>//GEN-END:|1223-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand2 ">//GEN-BEGIN:|1227-getter|0|1227-preInit
    /**
     * Returns an initialized instance of backCommand2 component.
     *
     * @return the initialized component instance
     */
    public Command getBackCommand2() {
        if (backCommand2 == null) {
//GEN-END:|1227-getter|0|1227-preInit
            // write pre-init user code here
backCommand2 = new Command("Cerrar Sesion", Command.BACK, 0);//GEN-LINE:|1227-getter|1|1227-postInit
            // write post-init user code here
}//GEN-BEGIN:|1227-getter|2|
        return backCommand2;
    }
//</editor-fold>//GEN-END:|1227-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: screenCommand ">//GEN-BEGIN:|1235-getter|0|1235-preInit
    /**
     * Returns an initialized instance of screenCommand component.
     *
     * @return the initialized component instance
     */
    public Command getScreenCommand() {
        if (screenCommand == null) {
//GEN-END:|1235-getter|0|1235-preInit
            // write pre-init user code here
screenCommand = new Command("Screen", Command.SCREEN, 0);//GEN-LINE:|1235-getter|1|1235-postInit
            // write post-init user code here
}//GEN-BEGIN:|1235-getter|2|
        return screenCommand;
    }
//</editor-fold>//GEN-END:|1235-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand9 ">//GEN-BEGIN:|1237-getter|0|1237-preInit
    /**
     * Returns an initialized instance of okCommand9 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand9() {
        if (okCommand9 == null) {
//GEN-END:|1237-getter|0|1237-preInit
            // write pre-init user code here
okCommand9 = new Command("activar gauge", Command.OK, 0);//GEN-LINE:|1237-getter|1|1237-postInit
            // write post-init user code here
}//GEN-BEGIN:|1237-getter|2|
        return okCommand9;
    }
//</editor-fold>//GEN-END:|1237-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: formLoading ">//GEN-BEGIN:|1239-getter|0|1239-preInit
    /**
     * Returns an initialized instance of formLoading component.
     *
     * @return the initialized component instance
     */
    public Form getFormLoading() {
        if (formLoading == null) {
//GEN-END:|1239-getter|0|1239-preInit
            // write pre-init user code here
            //#style mailAlert
formLoading = new Form("Por favor espere", de.enough.polish.ui.StyleSheet.mailalertStyle );
            formLoading.addCommand(getCancelCommand2());
            formLoading.setCommandListener(this);//GEN-END:|1239-getter|1|1239-postInit
            // write post-init user code here
            //#style .busyIndicator
            Gauge busyIndicator = new Gauge( null, false,Gauge.INDEFINITE, Gauge.CONTINUOUS_RUNNING , de.enough.polish.ui.StyleSheet.busyindicatorStyle );
            formLoading.append(busyIndicator);
        }//GEN-BEGIN:|1239-getter|2|
        return formLoading;
    }
//</editor-fold>//GEN-END:|1239-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: cancelCommand1 ">//GEN-BEGIN:|1240-getter|0|1240-preInit
    /**
     * Returns an initialized instance of cancelCommand1 component.
     *
     * @return the initialized component instance
     */
    public Command getCancelCommand1() {
        if (cancelCommand1 == null) {
//GEN-END:|1240-getter|0|1240-preInit
            // write pre-init user code here
cancelCommand1 = new Command("Cancel", Command.CANCEL, 0);//GEN-LINE:|1240-getter|1|1240-postInit
            // write post-init user code here
}//GEN-BEGIN:|1240-getter|2|
        return cancelCommand1;
    }
//</editor-fold>//GEN-END:|1240-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: cancelCommand2 ">//GEN-BEGIN:|1243-getter|0|1243-preInit
    /**
     * Returns an initialized instance of cancelCommand2 component.
     *
     * @return the initialized component instance
     */
    public Command getCancelCommand2() {
        if (cancelCommand2 == null) {
//GEN-END:|1243-getter|0|1243-preInit
            // write pre-init user code here
cancelCommand2 = new Command("Cancelar", Command.CANCEL, 0);//GEN-LINE:|1243-getter|1|1243-postInit
            // write post-init user code here
}//GEN-BEGIN:|1243-getter|2|
        return cancelCommand2;
    }
//</editor-fold>//GEN-END:|1243-getter|2|



//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image11 ">//GEN-BEGIN:|1246-getter|0|1246-preInit
    /**
     * Returns an initialized instance of image11 component.
     *
     * @return the initialized component instance
     */
    public Image getImage11() {
        if (image11 == null) {
//GEN-END:|1246-getter|0|1246-preInit
            // write pre-init user code here
try {//GEN-BEGIN:|1246-getter|1|1246-@java.io.IOException
                image11 = Image.createImage("/alert2.png");
            } catch (java.io.IOException e) {//GEN-END:|1246-getter|1|1246-@java.io.IOException
                e.printStackTrace();
            }//GEN-LINE:|1246-getter|2|1246-postInit
            // write post-init user code here
}//GEN-BEGIN:|1246-getter|3|
        return image11;
    }
//</editor-fold>//GEN-END:|1246-getter|3|
    public Image getImageInfo() {
        Image  imagen=null;
                 try {                                                    
                     imagen = Image.createImage("/info.png");
                    } catch (java.io.IOException e) {                                                  
                        e.printStackTrace();
                    }                                       
                    // write post-init user code here
                                  
        return imagen;
    }
     public Image getImageQuestion() {
        Image  imagen=null;
                 try {                                                    
                     imagen = Image.createImage("/msg_question.png");
                    } catch (java.io.IOException e) {                                                  
                        e.printStackTrace();
                    }                                       
                    // write post-init user code here
                                  
        return imagen;
    }
//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okRegistrar ">//GEN-BEGIN:|1252-getter|0|1252-preInit

    /**
     * Returns an initialized instance of okRegistrar component.
     *
     * @return the initialized component instance
     */
    public Command getOkRegistrar() {
        if (okRegistrar == null) {
//GEN-END:|1252-getter|0|1252-preInit
 // write pre-init user code here
okRegistrar = new Command("Ok", Command.OK, 0);//GEN-LINE:|1252-getter|1|1252-postInit
 // write post-init user code here
}//GEN-BEGIN:|1252-getter|2|
        return okRegistrar;
    }
//</editor-fold>//GEN-END:|1252-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: back ">//GEN-BEGIN:|1254-getter|0|1254-preInit
    /**
     * Returns an initialized instance of back component.
     *
     * @return the initialized component instance
     */
    public Command getBack() {
        if (back == null) {
//GEN-END:|1254-getter|0|1254-preInit
 // write pre-init user code here
back = new Command("Atras", Command.BACK, 0);//GEN-LINE:|1254-getter|1|1254-postInit
 // write post-init user code here
}//GEN-BEGIN:|1254-getter|2|
        return back;
    }
//</editor-fold>//GEN-END:|1254-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okDatos ">//GEN-BEGIN:|1260-getter|0|1260-preInit
    /**
     * Returns an initialized instance of okDatos component.
     *
     * @return the initialized component instance
     */
    public Command getOkDatos() {
        if (okDatos == null) {
//GEN-END:|1260-getter|0|1260-preInit
 // write pre-init user code here
okDatos = new Command("Ok", Command.OK, 0);//GEN-LINE:|1260-getter|1|1260-postInit
 // write post-init user code here
}//GEN-BEGIN:|1260-getter|2|
        return okDatos;
    }
//</editor-fold>//GEN-END:|1260-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: formRegistro ">//GEN-BEGIN:|1247-getter|0|1247-preInit
    /**
     * Returns an initialized instance of formRegistro component.
     *
     * @return the initialized component instance
     */
    public Form getFormRegistro() {
        if (formRegistro == null) {
//GEN-END:|1247-getter|0|1247-preInit
 // write pre-init user code here
formRegistro = new Form("Registro de Cliente", new Item[]{getTxtNitCli(), getTxtNomCli(), getTxtTelCli(), getTxtEmailCli()});//GEN-BEGIN:|1247-getter|1|1247-postInit
            formRegistro.addCommand(getOkRegistrar());
            formRegistro.addCommand(getBack());
            formRegistro.setCommandListener(this);//GEN-END:|1247-getter|1|1247-postInit
 // write post-init user code here
}//GEN-BEGIN:|1247-getter|2|
        return formRegistro;
    }
//</editor-fold>//GEN-END:|1247-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtNitCli ">//GEN-BEGIN:|1248-getter|0|1248-preInit
    /**
     * Returns an initialized instance of txtNitCli component.
     *
     * @return the initialized component instance
     */
    public TextField getTxtNitCli() {
        if (txtNitCli == null) {
//GEN-END:|1248-getter|0|1248-preInit
 // write pre-init user code here
txtNitCli = new TextField("Nit:", null, 32, TextField.NUMERIC);//GEN-LINE:|1248-getter|1|1248-postInit
 // write post-init user code here
}//GEN-BEGIN:|1248-getter|2|
        return txtNitCli;
    }
//</editor-fold>//GEN-END:|1248-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtNomCli ">//GEN-BEGIN:|1249-getter|0|1249-preInit
    /**
     * Returns an initialized instance of txtNomCli component.
     *
     * @return the initialized component instance
     */
    public TextField getTxtNomCli() {
        if (txtNomCli == null) {
//GEN-END:|1249-getter|0|1249-preInit
 // write pre-init user code here
txtNomCli = new TextField("Nombre:", null, 32, TextField.ANY);//GEN-LINE:|1249-getter|1|1249-postInit
 // write post-init user code here
}//GEN-BEGIN:|1249-getter|2|
        return txtNomCli;
    }
//</editor-fold>//GEN-END:|1249-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtTelCli ">//GEN-BEGIN:|1250-getter|0|1250-preInit
    /**
     * Returns an initialized instance of txtTelCli component.
     *
     * @return the initialized component instance
     */
    public TextField getTxtTelCli() {
        if (txtTelCli == null) {
//GEN-END:|1250-getter|0|1250-preInit
 // write pre-init user code here
txtTelCli = new TextField("Telefono:", null, 32, TextField.NUMERIC);//GEN-LINE:|1250-getter|1|1250-postInit
 // write post-init user code here
}//GEN-BEGIN:|1250-getter|2|
        return txtTelCli;
    }
//</editor-fold>//GEN-END:|1250-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtEmailCli ">//GEN-BEGIN:|1251-getter|0|1251-preInit
    /**
     * Returns an initialized instance of txtEmailCli component.
     *
     * @return the initialized component instance
     */
    public TextField getTxtEmailCli() {
        if (txtEmailCli == null) {
//GEN-END:|1251-getter|0|1251-preInit
 // write pre-init user code here
txtEmailCli = new TextField("Email:", null, 32, TextField.ANY);//GEN-LINE:|1251-getter|1|1251-postInit
 // write post-init user code here
}//GEN-BEGIN:|1251-getter|2|
        return txtEmailCli;
    }
//</editor-fold>//GEN-END:|1251-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: formDatosCliente ">//GEN-BEGIN:|1256-getter|0|1256-preInit
    /**
     * Returns an initialized instance of formDatosCliente component.
     *
     * @return the initialized component instance
     */
    public Form getFormDatosCliente() {
        if (formDatosCliente == null) {
//GEN-END:|1256-getter|0|1256-preInit
 // write pre-init user code here
formDatosCliente = new Form("Cliente", new Item[]{getTxtNitDat(), getTxtNomDat()});//GEN-BEGIN:|1256-getter|1|1256-postInit
            formDatosCliente.addCommand(getOkDatos());
            formDatosCliente.addCommand(getBackDatos());
            formDatosCliente.setCommandListener(this);//GEN-END:|1256-getter|1|1256-postInit
 // write post-init user code here
}//GEN-BEGIN:|1256-getter|2|
        return formDatosCliente;
    }
//</editor-fold>//GEN-END:|1256-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtNitDat ">//GEN-BEGIN:|1258-getter|0|1258-preInit
    /**
     * Returns an initialized instance of txtNitDat component.
     *
     * @return the initialized component instance
     */
    public TextField getTxtNitDat() {
        if (txtNitDat == null) {
//GEN-END:|1258-getter|0|1258-preInit
 // write pre-init user code here
txtNitDat = new TextField("Nit:", null, 32, TextField.ANY);//GEN-LINE:|1258-getter|1|1258-postInit
 // write post-init user code here
}//GEN-BEGIN:|1258-getter|2|
        return txtNitDat;
    }
//</editor-fold>//GEN-END:|1258-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtNomDat ">//GEN-BEGIN:|1259-getter|0|1259-preInit
    /**
     * Returns an initialized instance of txtNomDat component.
     *
     * @return the initialized component instance
     */
    public TextField getTxtNomDat() {
        if (txtNomDat == null) {
//GEN-END:|1259-getter|0|1259-preInit
 // write pre-init user code here
txtNomDat = new TextField("Nombre:", null, 32, TextField.ANY);//GEN-LINE:|1259-getter|1|1259-postInit
 // write post-init user code here
}//GEN-BEGIN:|1259-getter|2|
        return txtNomDat;
    }
//</editor-fold>//GEN-END:|1259-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: backDatos ">//GEN-BEGIN:|1262-getter|0|1262-preInit
    /**
     * Returns an initialized instance of backDatos component.
     *
     * @return the initialized component instance
     */
    public Command getBackDatos() {
        if (backDatos == null) {
//GEN-END:|1262-getter|0|1262-preInit
 // write pre-init user code here
backDatos = new Command("Ok", Command.OK, 0);//GEN-LINE:|1262-getter|1|1262-postInit
 // write post-init user code here
}//GEN-BEGIN:|1262-getter|2|
        return backDatos;
    }
//</editor-fold>//GEN-END:|1262-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: methodRegistrarCliente ">//GEN-BEGIN:|1266-entry|0|1267-preAction
    /**
     * Performs an action assigned to the methodRegistrarCliente entry-point.
     */
    public void methodRegistrarCliente() {
//GEN-END:|1266-entry|0|1267-preAction
//    pantalla=REGISTRARCLIENTE;
//    RegistroCliente rc = new RegistroCliente();
//    rc.setNit(txtNitCli.getText());
//    rc.setNombre(txtNomCli.getText());
//    rc.setTelefono(txtTelCli.getText());
//    if (txtEmailCli.getString().equals(""))
//    {
//        txtEmailCli.setString("sinemail");
//    }
//    
//    rc.setEmail(txtEmailCli.getText());
//    estaRegistrado =false;    
//    Cargando();
//     if(conexion !=null)
//        {
//            conexion = null;
//        } 
//    conexion = new ConexionIpx(rest);
//        Thread t = new Thread()
//        {
//            public void run()
//            {
//                   
//                System.out.println(" thred consumidor activo");
//                if(rest.getCodigoRespuesta()==200)
//                {
//                    cliente = new Cliente(rest.getRespuesta());
//                    //colocar mensaje de guardado
//                    estaRegistrado = true;
//                    
//           
//                    switchDisplayable (null, getListMenu ());                                         
//                    switchDisplayable(getProblemas(), getListMenu());
//                    
//                    
////                    cambiarPantalla();
//                
//                    //Cargando el titulo de la lista
//                    
//                }
//                else
//                {   
//                    //Repinta la pantalla antes de que esta esetes
//                    switchDisplayable(null, getFormLogin());
//                    switchDisplayable(getProblemas(), getFormLogin());
//                }   
//
//            
//            }
//      
//        };       
//        
//        conexion.EnviarPost(REGISTRARCLIENTE,RegistroCliente.toJSON(rc),this.llave,t);
////        conexion.Lenvantate();
//        conexion.start();
//    
         
  /*      
switchDisplayable (null, getListMenu ());//GEN-BEGIN:|1266-entry|1|1267-postAction
//GEN-END:|1266-entry|1|1267-postAction
 */
    }//GEN-BEGIN:|1266-entry|2|
//</editor-fold>//GEN-END:|1266-entry|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image12 ">//GEN-BEGIN:|1271-getter|0|1271-preInit
    /**
     * Returns an initialized instance of image12 component.
     *
     * @return the initialized component instance
     */
    public Image getImage12() {
        if (image12 == null) {
//GEN-END:|1271-getter|0|1271-preInit
 // write pre-init user code here
try {//GEN-BEGIN:|1271-getter|1|1271-@java.io.IOException
                image12 = Image.createImage("/f3.png");
            } catch (java.io.IOException e) {//GEN-END:|1271-getter|1|1271-@java.io.IOException
    e.printStackTrace();
            }//GEN-LINE:|1271-getter|2|1271-postInit
 // write post-init user code here
}//GEN-BEGIN:|1271-getter|3|
        return image12;
    }
//</editor-fold>//GEN-END:|1271-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: formVistaFactura ">//GEN-BEGIN:|1272-getter|0|1272-preInit
    /**
     * Returns an initialized instance of formVistaFactura component.
     *
     * @return the initialized component instance
     */
    public Form getFormVistaFactura() {
        if (formVistaFactura == null) {
//GEN-END:|1272-getter|0|1272-preInit
 // write pre-init user code here
formVistaFactura = new Form("Factura", new Item[]{getStrFactura()});//GEN-BEGIN:|1272-getter|1|1272-postInit
            formVistaFactura.addCommand(getOkCommand11());
            formVistaFactura.addCommand(getBack());
            formVistaFactura.setCommandListener(this);//GEN-END:|1272-getter|1|1272-postInit
 // write post-init user code here
//                Facturas f = (Facturas) listaFacturas.elementAt(getListFacturas().getSelectedIndex());
                strFactura.setText("Nit: "+factura.getCliente().getNit()+"\n Nombre:"+factura.getCliente().getName());
                formVistaFactura.setTitle("Factura No. "+factura.getInvoiceNumber());
//             
            //nuevo formato de factura
            
                if(table != null)
                {
                    table=null;
                }
            //#style defaultTable
               
                table = new TableItem(2,factura.getInvoiceItems().size()+1, de.enough.polish.ui.StyleSheet.defaulttableStyle );
                
                formVistaFactura.append( table );
//                table.clear();
              
               
                table.addRow();
                table.addColumn();
                table.addColumn();
                table.setSelectionMode( TableItem.SELECTION_MODE_CELL );
                
                //#style heading
                table.set(0, 0,"Cant", de.enough.polish.ui.StyleSheet.headingStyle );
//                table.setSelectionMode( TableItem.SELECTION_MODE_ROW );
                
                 //#style heading
                table.set(1, 0,"Producto", de.enough.polish.ui.StyleSheet.headingStyle );
//                table.set(2, 0,"Bs");
                 double total=0;
                 double bonidesc=0;
//         
               for(int i=0;i<factura.getInvoiceItems().size();i++)
                {
                 // table.addRow();
                  InvoiceItem invitem = (InvoiceItem) factura.getInvoiceItems().elementAt(i);
                    double qty = Double.parseDouble(invitem.getQty());
//                    table.setSelectionMode( TableItem.SELECTION_MODE_ROW  );
                 //#style bodys
                   table.set(0, i+1, ""+(int)qty, de.enough.polish.ui.StyleSheet.bodysStyle );     
//                   table.setSelectionMode( TableItem.SELECTION_MODE_ROW  );
                 //#style bodys
                   table.set(1, i+1, invitem.getNotes(), de.enough.polish.ui.StyleSheet.bodysStyle );
                   
                   double costo=(double)(Double.parseDouble(invitem.getCost()));
                   total = total + (costo*Double.parseDouble(invitem.getQty()))-((costo*Double.parseDouble(invitem.getBoni()))+Double.parseDouble(invitem.getDesc()));
                     
                }
                
////         
//               getTextField1().setText(""+redondeo(total,2));
             getStr2().setText(""+redondeo(total,2));
//            // getStr2().setText("Total a Pagar:");
////             formFactura.append(getStr1());
             
             formVistaFactura.append(getStr2());
             table.setSelectedCell(0, 0);
        }//GEN-BEGIN:|1272-getter|2|
        return formVistaFactura;
    }
//</editor-fold>//GEN-END:|1272-getter|2|







//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand10 ">//GEN-BEGIN:|1279-getter|0|1279-preInit
    /**
     * Returns an initialized instance of okCommand10 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand10() {
        if (okCommand10 == null) {
//GEN-END:|1279-getter|0|1279-preInit
 // write pre-init user code here
okCommand10 = new Command("Seleccionar", Command.OK, 0);//GEN-LINE:|1279-getter|1|1279-postInit
 // write post-init user code here
}//GEN-BEGIN:|1279-getter|2|
        return okCommand10;
    }
//</editor-fold>//GEN-END:|1279-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image13 ">//GEN-BEGIN:|1282-getter|0|1282-preInit
    /**
     * Returns an initialized instance of image13 component.
     *
     * @return the initialized component instance
     */
    public Image getImage13() {
        if (image13 == null) {
//GEN-END:|1282-getter|0|1282-preInit
 // write pre-init user code here
try {//GEN-BEGIN:|1282-getter|1|1282-@java.io.IOException
                image13 = Image.createImage("/factura.png");
            } catch (java.io.IOException e) {//GEN-END:|1282-getter|1|1282-@java.io.IOException
    e.printStackTrace();
            }//GEN-LINE:|1282-getter|2|1282-postInit
 // write post-init user code here
}//GEN-BEGIN:|1282-getter|3|
        return image13;
    }
//</editor-fold>//GEN-END:|1282-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: methodPrintFactura ">//GEN-BEGIN:|1283-entry|0|1284-preAction
    /**
     * Performs an action assigned to the methodPrintFactura entry-point.
     */
    public void methodPrintFactura() {
//GEN-END:|1283-entry|0|1284-preAction
 // write pre-action user code here
        pantalla = PRINTFACTURA;
        Cargando();
        
        
        
        
        Thread t = new Thread()
        {
            public void run()
            {
                   
                System.out.println(" thred consumidor activo");
//                if(rest.getCodigoRespuesta()==200)
//                {
                   try {
                     
//                        
                        
                                        
//                       String datos =factura.getAccount().getNit()+"|"+factura.getInvoiceNumber()+"|"+factura.getNumAuto()+"|"+factura.getFechaLimite()+"|"+factura.getAmount()+"|"+factura.getFiscal()+"|"+factura.getControlCode()+"|"+factura.getCliente().getNit()+"|"+factura.getIce()+"|0|0|"+(Double.parseDouble(factura.getSubtotal())-Double.parseDouble(factura.getAmount()));
//                       String datos =factura.getAccount().getNit()+"|"+factura.getInvoiceNumber()+"|"+factura.getNumAuto()+"|"+factura.getFechaLimite()+"|"+factura.getAmount()+"|"+factura.getFiscal()+"|"+factura.getControlCode()+"|"+factura.getCliente().getNit()+"|"+factura.getIce()+"|0|"+redondeo((Double.parseDouble(factura.getSubtotal())-Double.parseDouble(factura.getAmount())),6)+"|"+redondeo((Double.parseDouble(factura.getSubtotal())-Double.parseDouble(factura.getAmount())-Double.parseDouble(factura.getIce())),6);
                       String nit1=factura.getAccount().getNit();
                                String invoice_number2=factura.getInvoiceNumber();
                                String num_auto3=factura.getNumAuto();
                                String fecha_emision4=factura.getInvoiceDate();
                                String total5=factura.getAmount();
                                String credito_fiscal6=factura.getFiscal();
                                String cod_control7=factura.getControlCode();
                                String ci_8=factura.getCliente().getNit();
                                String ice_9=factura.getIce();
                                String importe_ventas10="0.0";
                                String sincredito_fiscal11="0.0";
                                double descuento = Double.parseDouble(factura.getSubtotal())-Double.parseDouble(factura.getAmount());
                                String descuentos_bonificacion=""+redondeo(descuento,2);//;
                                
                                
                                String datos =nit1+"|"+invoice_number2+"|"+num_auto3+"|"+fecha_emision4+"|"+total5+"|"
                                             +credito_fiscal6+"|"+cod_control7+"|"+ci_8+"|"+ice_9+"|"+importe_ventas10+"|"
                                             +sincredito_fiscal11+"|"+descuentos_bonificacion;
                                
                                
                            
//                         qrCodeImage = encode(datos);
                                        
                                     //   imprimir.printBitmap(ba.readImage(BMPGenerator.encodeBMP(qrCodeImage)));
                                
//                        javax.microedition.lcdui.Image ImagenQr=ImprimirQr(factura);
//                        BmpArray ba = new BmpArray();
                        
                        byte imagen[] =  ba.readImage(BMPGenerator.encodeBMP(qrCodeImage));
//                        byte imagenActividad[] =ba.readImage(BMPGenerator.encodeBMP(imgActividad));
//                        Vector v= VectorProductos(factura.getInvoiceItems());
                        
                        //mejorando velocidad de impresion
//                          ByteIpx bi[]=new ByteIpx[v.size()];
//                        byte bas[];
//                        for (int i=0;i<bi.length;i++)
//                        {
//                            bas = (byte[]) v.elementAt(i);
//                            ByteIpx ipx = new ByteIpx();
//                            ipx.setBi(bas);
//                        }
                         Vector vec = TextLine(factura.getLaw());
//                        Imprimir(factura,imagen,v,vec);
                        cambiarPantalla();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
//                    listaFacturas = null;
//                    listaFacturas = Facturas.fromJsonArray(rest.getRespuesta());
////                    cuenta = new Cuenta(rest.getRespuesta());
//                    getListFacturas().deleteAll();
//                    for(int i =0;i<listaFacturas.size();i++)
//                    {
//                        Facturas factura = (Facturas) listaFacturas.elementAt(i);
//                        getListFacturas().append("No. Factura: "+factura.getInvoiceNumber()+"\nNit: "+factura.getNit()+"\nNombre: "+factura.getName()+"\nMonto: "+Double.parseDouble(factura.getAmount()), getImage13());
//                        
//                    }
                            
//                    cambiarPantalla();
//                    listMenu.setTitle("Usuario:"+TxtUsuario.getText());
                    //Cargando el titulo de la lista
                    
//                }
//                else
//                {   
//                    //Repinta la pantalla antes de que esta esetes
//                    switchDisplayable(null, getListFacturas());
//                    switchDisplayable(getProblemas(), getListFacturas());
//                }   

            
            }
      
        }; 
        t.start();
//        Facturas f =(Facturas) listaFacturas.elementAt(getListFacturas().getSelectedIndex());
//        conexion.EnviarGet(PRINTFACTURA,f.getInvoiceNumber(),llave,t);
//        conexion.Lenvantate();
        
 /*      
switchDisplayable (null, getListPrincipal ());//GEN-BEGIN:|1283-entry|1|1284-postAction
//GEN-END:|1283-entry|1|1284-postAction
*/ 
        
        // write post-action user code here
}//GEN-BEGIN:|1283-entry|2|
//</editor-fold>//GEN-END:|1283-entry|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand11 ">//GEN-BEGIN:|1289-getter|0|1289-preInit
    /**
     * Returns an initialized instance of okCommand11 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand11() {
        if (okCommand11 == null) {
//GEN-END:|1289-getter|0|1289-preInit
 // write pre-init user code here
okCommand11 = new Command("Imprimir", Command.OK, 0);//GEN-LINE:|1289-getter|1|1289-postInit
 // write post-init user code here
}//GEN-BEGIN:|1289-getter|2|
        return okCommand11;
    }
//</editor-fold>//GEN-END:|1289-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: methodFactura ">//GEN-BEGIN:|1292-entry|0|1293-preAction
    /**
     * Performs an action assigned to the methodFactura entry-point.
     */
    public void methodFactura() {
//GEN-END:|1292-entry|0|1293-preAction
 // write pre-action user code here
        
        pantalla = GETFACTURA;
        
        if (!estaVacio(getTxtCodCliente()))
        {

//                Cargando();
                
//                 if(conexion !=null)
//                {
//                    conexion = null;
//                }
//                conexion = new ConexionIpx(rest);
                Thread t = new Thread()
                {
                    public void run()
                    {
                         
                           
//                        System.out.println(" thred consumidor activo");
                        if(buscarFactura(facturas,getTxtCodCliente().getString()))
                        {
                        
                            try{
                                //armando factura offline otra vez XD
                                
                                FacturaOffline fg= (FacturaOffline) facturas.elementAt(punteroFactura);
                                
                                Account account = new Account();
                                account.setName(fg.getNameCuenta());
                                account.setNit(fg.getNiCuenta());
                                
                                Clients c = new Clients();
                                c.setId(fg.getClienteId());
                                c.setName(fg.getNameCliente());
                                c.setNit(fg.getNitCliente());
                                c.setRazon(fg.getNameCliente());
                                c.setPublic_id(fg.getClienteId());
                                
                                factura = new Factura();
                                factura.setAccount(account);
                                factura.setAddress1(fg.getAddress1());
                                factura.setAddress2(fg.getAddress2());
                                factura.setAmount(fg.getAmount());
                                factura.setCliente(c);
                                factura.setControl_code(fg.getControl_code());
                                factura.setFecha_limite(fg.getFecha_limite());
                                factura.setFiscal(fg.getFiscal());
                                factura.setIce(fg.getIce());
                                factura.setInvoice_date(fg.getInvoice_date());
                                factura.setInvoice_number(fg.getInvoice_number());
                                factura.setItems(fg.getItems());
                                factura.setLaw(fg.getLaw());
                                factura.setNum_auto(fg.getNum_auto());
                                factura.setResultado("0");
                                factura.setSubtotal(fg.getSubtotal());
                                
                                
                                formVistaFactura = null;
                                cambiarPantalla();
                                
                            
                            }catch(Exception e)
                            {
                                 switchDisplayable(null, getFormRClient());
                                 switchDisplayable(getAlerta("Surgio un Problema","Por favor vuelva intentarlo en 5 segundos",0), getFormRClient());
                            }
                            
                        }
                        else
                        {   
                            //Repinta la pantalla antes de que esta esetes
                                switchDisplayable(null, getFormRClient());
                                switchDisplayable(getAlerta("Cliente Sin Facturas","Cliente sin Facturas emitidas",0), getFormRClient());
                        }   
        
                         
                    }
              
                }; 
                t.start();
//                Facturas f =(Facturas) listaFacturas.elementAt(getListFacturas().getSelectedIndex());
                
//                conexion.EnviarGet(GETFACTURA,getTxtCodCliente().getString(),this.llave,t);
////                conexion.Lenvantate();
//                conexion.start();
//     
            
        }
        //por else mandar mensaje de casilla vacia y debe ingresar un cliente
        
//        else
//        {
//        
//        
//       
        /*
switchDisplayable (null, getFormVistaFactura ());//GEN-BEGIN:|1292-entry|1|1293-postAction
//GEN-END:|1292-entry|1|1293-postAction
        */
        // write post-action user code here
}//GEN-BEGIN:|1292-entry|2|
//</editor-fold>//GEN-END:|1292-entry|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: strFactura ">//GEN-BEGIN:|1291-getter|0|1291-preInit
    /**
     * Returns an initialized instance of strFactura component.
     *
     * @return the initialized component instance
     */
    public StringItem getStrFactura() {
        if (strFactura == null) {
//GEN-END:|1291-getter|0|1291-preInit
 // write pre-init user code here
strFactura = new StringItem("", null);//GEN-LINE:|1291-getter|1|1291-postInit
 // write post-init user code here
}//GEN-BEGIN:|1291-getter|2|
        return strFactura;
    }
//</editor-fold>//GEN-END:|1291-getter|2|





//<editor-fold defaultstate="collapsed" desc=" Generated Getter: form ">//GEN-BEGIN:|1304-getter|0|1304-preInit
    /**
     * Returns an initialized instance of form component.
     *
     * @return the initialized component instance
     */
    public Form getForm() {
        if (form == null) {
//GEN-END:|1304-getter|0|1304-preInit
 // write pre-init user code here
form = new Form("form", new Item[]{getTxt1(), getTxt2()});//GEN-BEGIN:|1304-getter|1|1304-postInit
            form.addCommand(getOkCommand12());
            form.addCommand(getOkCommand13());
            form.addCommand(getExitCommand1());
            form.setCommandListener(this);//GEN-END:|1304-getter|1|1304-postInit
 // write post-init user code here
}//GEN-BEGIN:|1304-getter|2|
        return form;
    }
//</editor-fold>//GEN-END:|1304-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand12 ">//GEN-BEGIN:|1307-getter|0|1307-preInit
    /**
     * Returns an initialized instance of okCommand12 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand12() {
        if (okCommand12 == null) {
//GEN-END:|1307-getter|0|1307-preInit
 // write pre-init user code here
okCommand12 = new Command("grabar", Command.OK, 0);//GEN-LINE:|1307-getter|1|1307-postInit
 // write post-init user code here
}//GEN-BEGIN:|1307-getter|2|
        return okCommand12;
    }
//</editor-fold>//GEN-END:|1307-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand13 ">//GEN-BEGIN:|1309-getter|0|1309-preInit
    /**
     * Returns an initialized instance of okCommand13 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand13() {
        if (okCommand13 == null) {
//GEN-END:|1309-getter|0|1309-preInit
 // write pre-init user code here
okCommand13 = new Command("Abrir", Command.OK, 0);//GEN-LINE:|1309-getter|1|1309-postInit
 // write post-init user code here
}//GEN-BEGIN:|1309-getter|2|
        return okCommand13;
    }
//</editor-fold>//GEN-END:|1309-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand1 ">//GEN-BEGIN:|1311-getter|0|1311-preInit
    /**
     * Returns an initialized instance of exitCommand1 component.
     *
     * @return the initialized component instance
     */
    public Command getExitCommand1() {
        if (exitCommand1 == null) {
//GEN-END:|1311-getter|0|1311-preInit
 // write pre-init user code here
exitCommand1 = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|1311-getter|1|1311-postInit
 // write post-init user code here
}//GEN-BEGIN:|1311-getter|2|
        return exitCommand1;
    }
//</editor-fold>//GEN-END:|1311-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txt1 ">//GEN-BEGIN:|1305-getter|0|1305-preInit
    /**
     * Returns an initialized instance of txt1 component.
     *
     * @return the initialized component instance
     */
    public TextField getTxt1() {
        if (txt1 == null) {
//GEN-END:|1305-getter|0|1305-preInit
 // write pre-init user code here
txt1 = new TextField("Para Guardar:", null, 32, TextField.ANY);//GEN-LINE:|1305-getter|1|1305-postInit
 // write post-init user code here
}//GEN-BEGIN:|1305-getter|2|
        return txt1;
    }
//</editor-fold>//GEN-END:|1305-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txt2 ">//GEN-BEGIN:|1306-getter|0|1306-preInit
    /**
     * Returns an initialized instance of txt2 component.
     *
     * @return the initialized component instance
     */
    public TextField getTxt2() {
        if (txt2 == null) {
//GEN-END:|1306-getter|0|1306-preInit
 // write pre-init user code here
txt2 = new TextField("En base de Datos:", null, 32, TextField.ANY);//GEN-LINE:|1306-getter|1|1306-postInit
 // write post-init user code here
}//GEN-BEGIN:|1306-getter|2|
        return txt2;
    }
//</editor-fold>//GEN-END:|1306-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: notesList ">//GEN-BEGIN:|1314-getter|0|1314-preInit
    /**
     * Returns an initialized instance of notesList component.
     *
     * @return the initialized component instance
     */
    public List getNotesList() {
        if (notesList == null) {
//GEN-END:|1314-getter|0|1314-preInit
 // write pre-init user code here
notesList = new List("list", Choice.IMPLICIT);//GEN-BEGIN:|1314-getter|1|1314-postInit
            notesList.addCommand(getOkCommand14());
            notesList.addCommand(getBackCommand3());
            notesList.setCommandListener(this);//GEN-END:|1314-getter|1|1314-postInit
 // write post-init user code here
}//GEN-BEGIN:|1314-getter|2|
        return notesList;
    }
//</editor-fold>//GEN-END:|1314-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Method: notesListAction ">//GEN-BEGIN:|1314-action|0|1314-preAction
    /**
     * Performs an action assigned to the selected list element in the notesList
     * component.
     */
    public void notesListAction() {
//GEN-END:|1314-action|0|1314-preAction
 // enter pre-action user code here
String __selectedString = getNotesList().getString(getNotesList().getSelectedIndex());//GEN-LINE:|1314-action|1|1314-postAction
 // enter post-action user code here
}//GEN-BEGIN:|1314-action|2|
//</editor-fold>//GEN-END:|1314-action|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand14 ">//GEN-BEGIN:|1318-getter|0|1318-preInit
    /**
     * Returns an initialized instance of okCommand14 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand14() {
        if (okCommand14 == null) {
//GEN-END:|1318-getter|0|1318-preInit
 // write pre-init user code here
okCommand14 = new Command("Ok", Command.OK, 0);//GEN-LINE:|1318-getter|1|1318-postInit
 // write post-init user code here
}//GEN-BEGIN:|1318-getter|2|
        return okCommand14;
    }
//</editor-fold>//GEN-END:|1318-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand3 ">//GEN-BEGIN:|1320-getter|0|1320-preInit
    /**
     * Returns an initialized instance of backCommand3 component.
     *
     * @return the initialized component instance
     */
    public Command getBackCommand3() {
        if (backCommand3 == null) {
//GEN-END:|1320-getter|0|1320-preInit
 // write pre-init user code here
backCommand3 = new Command("Back", Command.BACK, 0);//GEN-LINE:|1320-getter|1|1320-postInit
 // write post-init user code here
}//GEN-BEGIN:|1320-getter|2|
        return backCommand3;
    }
//</editor-fold>//GEN-END:|1320-getter|2|





//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand15 ">//GEN-BEGIN:|1327-getter|0|1327-preInit
    /**
     * Returns an initialized instance of okCommand15 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand15() {
        if (okCommand15 == null) {
//GEN-END:|1327-getter|0|1327-preInit
 // write pre-init user code here
okCommand15 = new Command("Ok", Command.OK, 0);//GEN-LINE:|1327-getter|1|1327-postInit
 // write post-init user code here
}//GEN-BEGIN:|1327-getter|2|
        return okCommand15;
    }
//</editor-fold>//GEN-END:|1327-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand4 ">//GEN-BEGIN:|1329-getter|0|1329-preInit
    /**
     * Returns an initialized instance of backCommand4 component.
     *
     * @return the initialized component instance
     */
    public Command getBackCommand4() {
        if (backCommand4 == null) {
//GEN-END:|1329-getter|0|1329-preInit
 // write pre-init user code here
backCommand4 = new Command("Atras", Command.OK, 0);//GEN-LINE:|1329-getter|1|1329-postInit
 // write post-init user code here
}//GEN-BEGIN:|1329-getter|2|
        return backCommand4;
    }
//</editor-fold>//GEN-END:|1329-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: formProd ">//GEN-BEGIN:|1326-getter|0|1326-preInit
    /**
     * Returns an initialized instance of formProd component.
     *
     * @return the initialized component instance
     */
    public Form getFormProd() {
        if (formProd == null) {
//GEN-END:|1326-getter|0|1326-preInit
 // write pre-init user code here
formProd = new Form("Producto", new Item[]{getTxtProductKey()});//GEN-BEGIN:|1326-getter|1|1326-postInit
            formProd.addCommand(getOkCommand15());
            formProd.addCommand(getBackCommand4());
            formProd.setCommandListener(this);//GEN-END:|1326-getter|1|1326-postInit
 // write post-init user code here
}//GEN-BEGIN:|1326-getter|2|
        return formProd;
    }
//</editor-fold>//GEN-END:|1326-getter|2|



//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtProductKey ">//GEN-BEGIN:|1334-getter|0|1334-preInit
    /**
     * Returns an initialized instance of txtProductKey component.
     *
     * @return the initialized component instance
     */
    public TextField getTxtProductKey() {
        if (txtProductKey == null) {
//GEN-END:|1334-getter|0|1334-preInit
 // write pre-init user code here
txtProductKey = new TextField("Codigo del Producto:", null, 32, TextField.NUMERIC);//GEN-LINE:|1334-getter|1|1334-postInit
 // write post-init user code here
}//GEN-BEGIN:|1334-getter|2|
        return txtProductKey;
    }
//</editor-fold>//GEN-END:|1334-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand16 ">//GEN-BEGIN:|1336-getter|0|1336-preInit
    /**
     * Returns an initialized instance of okCommand16 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand16() {
        if (okCommand16 == null) {
//GEN-END:|1336-getter|0|1336-preInit
 // write pre-init user code here
okCommand16 = new Command("Ok", Command.OK, 0);//GEN-LINE:|1336-getter|1|1336-postInit
 // write post-init user code here
}//GEN-BEGIN:|1336-getter|2|
        return okCommand16;
    }
//</editor-fold>//GEN-END:|1336-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: formCant ">//GEN-BEGIN:|1335-getter|0|1335-preInit
    /**
     * Returns an initialized instance of formCant component.
     *
     * @return the initialized component instance
     */
    public Form getFormCant() {
        if (formCant == null) {
//GEN-END:|1335-getter|0|1335-preInit
 // write pre-init user code here
formCant = new Form("form1", new Item[]{getStringItem3(), getTxtP(), getTxtU(), getStringItem4(), getTxtB(), getTxtD()});//GEN-BEGIN:|1335-getter|1|1335-postInit
            formCant.addCommand(getOkCommand16());
            formCant.addCommand(getOkCommand17());
            formCant.setCommandListener(this);//GEN-END:|1335-getter|1|1335-postInit
 // write post-init user code here
           // formCant.setTitle(nombreProducto());
}//GEN-BEGIN:|1335-getter|2|
        return formCant;
    }
//</editor-fold>//GEN-END:|1335-getter|2|
public Form getFormCantidad() {
        
         if(formCant!=null)
         {
             formCant=null;
         }
             switch(product_state)
                {
                    case PAQUETE:
                        Log.i("formCant","paquete");
                        formCant = new Form("form1", new Item[]{getStringItem3(), getTxtP(), getStringItem4(), getTxtB(), getTxtD()});
                       
                        break;
                    case UNIDAD:
                        Log.i("formCant","unidad");
                        formCant = new Form("form1", new Item[]{getStringItem3(), getTxtU(), getStringItem4(), getTxtB(), getTxtD()});
                
                        break;
//                    case SINPRODUCTO:
//                            switchDisplayable(null, getListMenu());
//                        break;
//                    default: switchDisplayable(null, getListMenu());            
                }                                    
            formCant.addCommand(getOkCommand16());
            formCant.addCommand(getOkCommand17());
            formCant.setCommandListener(this);                                      
 
                                  
        return formCant;
    }
//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand17 ">//GEN-BEGIN:|1343-getter|0|1343-preInit
    /**
     * Returns an initialized instance of okCommand17 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand17() {
        if (okCommand17 == null) {
//GEN-END:|1343-getter|0|1343-preInit
 // write pre-init user code here
okCommand17 = new Command("atras", Command.OK, 0);//GEN-LINE:|1343-getter|1|1343-postInit
 // write post-init user code here
}//GEN-BEGIN:|1343-getter|2|
        return okCommand17;
    }
//</editor-fold>//GEN-END:|1343-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: stringItem3 ">//GEN-BEGIN:|1339-getter|0|1339-preInit
    /**
     * Returns an initialized instance of stringItem3 component.
     *
     * @return the initialized component instance
     */
    public StringItem getStringItem3() {
        if (stringItem3 == null) {
//GEN-END:|1339-getter|0|1339-preInit
 // write pre-init user code here
stringItem3 = new StringItem("Cantidad", null);//GEN-LINE:|1339-getter|1|1339-postInit
 // write post-init user code here
}//GEN-BEGIN:|1339-getter|2|
        return stringItem3;
    }
//</editor-fold>//GEN-END:|1339-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtP ">//GEN-BEGIN:|1340-getter|0|1340-preInit
    /**
     * Returns an initialized instance of txtP component.
     *
     * @return the initialized component instance
     */
    public TextField getTxtP() {
        if (txtP == null) {
//GEN-END:|1340-getter|0|1340-preInit
 // write pre-init user code here
txtP = new TextField("Paquete:", null, 32, TextField.NUMERIC);//GEN-LINE:|1340-getter|1|1340-postInit
 // write post-init user code here
}//GEN-BEGIN:|1340-getter|2|
        return txtP;
    }
//</editor-fold>//GEN-END:|1340-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtU ">//GEN-BEGIN:|1341-getter|0|1341-preInit
    /**
     * Returns an initialized instance of txtU component.
     *
     * @return the initialized component instance
     */
    public TextField getTxtU() {
        if (txtU == null) {
//GEN-END:|1341-getter|0|1341-preInit
 // write pre-init user code here
txtU = new TextField("Unidad:", null, 32, TextField.NUMERIC);//GEN-LINE:|1341-getter|1|1341-postInit
 // write post-init user code here
}//GEN-BEGIN:|1341-getter|2|
        return txtU;
    }
//</editor-fold>//GEN-END:|1341-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: stringItem4 ">//GEN-BEGIN:|1345-getter|0|1345-preInit
    /**
     * Returns an initialized instance of stringItem4 component.
     *
     * @return the initialized component instance
     */
    public StringItem getStringItem4() {
        if (stringItem4 == null) {
//GEN-END:|1345-getter|0|1345-preInit
 // write pre-init user code here
stringItem4 = new StringItem("_______________________", null);//GEN-LINE:|1345-getter|1|1345-postInit
 // write post-init user code here
}//GEN-BEGIN:|1345-getter|2|
        return stringItem4;
    }
//</editor-fold>//GEN-END:|1345-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtB ">//GEN-BEGIN:|1346-getter|0|1346-preInit
    /**
     * Returns an initialized instance of txtB component.
     *
     * @return the initialized component instance
     */
    public TextField getTxtB() {
        if (txtB == null) {
//GEN-END:|1346-getter|0|1346-preInit
 // write pre-init user code here
txtB = new TextField("Bonificacion:", null, 32, TextField.NUMERIC);//GEN-LINE:|1346-getter|1|1346-postInit
 // write post-init user code here
}//GEN-BEGIN:|1346-getter|2|
        return txtB;
    }
//</editor-fold>//GEN-END:|1346-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtD ">//GEN-BEGIN:|1347-getter|0|1347-preInit
    /**
     * Returns an initialized instance of txtD component.
     *
     * @return the initialized component instance
     */
    public TextField getTxtD() {
        if (txtD == null) {
//GEN-END:|1347-getter|0|1347-preInit
 // write pre-init user code here
txtD = new TextField("Descuento Bs.:", null, 32, TextField.DECIMAL);//GEN-LINE:|1347-getter|1|1347-postInit
 // write post-init user codasdase here
}//GEN-BEGIN:|1347-getter|2|
        return txtD;
    }
//</editor-fold>//GEN-END:|1347-getter|2|





//<editor-fold defaultstate="collapsed" desc=" Generated Getter: stopCommand1 ">//GEN-BEGIN:|1350-getter|0|1350-preInit
    /**
     * Returns an initialized instance of stopCommand1 component.
     *
     * @return the initialized component instance
     */
    public Command getStopCommand1() {
        if (stopCommand1 == null) {
//GEN-END:|1350-getter|0|1350-preInit
 // write pre-init user code here
stopCommand1 = new Command("Stop", Command.STOP, 0);//GEN-LINE:|1350-getter|1|1350-postInit
 // write post-init user code here
}//GEN-BEGIN:|1350-getter|2|
        return stopCommand1;
    }
//</editor-fold>//GEN-END:|1350-getter|2|





//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand18 ">//GEN-BEGIN:|1356-getter|0|1356-preInit
    /**
     * Returns an initialized instance of okCommand18 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand18() {
        if (okCommand18 == null) {
//GEN-END:|1356-getter|0|1356-preInit
 // write pre-init user code here
okCommand18 = new Command("Ok", Command.OK, 0);//GEN-LINE:|1356-getter|1|1356-postInit
 // write post-init user code here
}//GEN-BEGIN:|1356-getter|2|
        return okCommand18;
    }
//</editor-fold>//GEN-END:|1356-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand19 ">//GEN-BEGIN:|1358-getter|0|1358-preInit
    /**
     * Returns an initialized instance of okCommand19 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand19() {
        if (okCommand19 == null) {
//GEN-END:|1358-getter|0|1358-preInit
 // write pre-init user code here
okCommand19 = new Command("Atras", Command.OK, 0);//GEN-LINE:|1358-getter|1|1358-postInit
 // write post-init user code here
}//GEN-BEGIN:|1358-getter|2|
        return okCommand19;
    }
//</editor-fold>//GEN-END:|1358-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: formRClient ">//GEN-BEGIN:|1354-getter|0|1354-preInit
    /**
     * Returns an initialized instance of formRClient component.
     *
     * @return the initialized component instance
     */
    public Form getFormRClient() {
        if (formRClient == null) {
//GEN-END:|1354-getter|0|1354-preInit
 // write pre-init user code here
formRClient = new Form("Reimpresion de Factura", new Item[]{getTxtCodCliente()});//GEN-BEGIN:|1354-getter|1|1354-postInit
            formRClient.addCommand(getOkCommand18());
            formRClient.addCommand(getOkCommand19());
            formRClient.setCommandListener(this);//GEN-END:|1354-getter|1|1354-postInit
 // write post-init user code here
}//GEN-BEGIN:|1354-getter|2|
        return formRClient;
    }
//</editor-fold>//GEN-END:|1354-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txtCodCliente ">//GEN-BEGIN:|1360-getter|0|1360-preInit
    /**
     * Returns an initialized instance of txtCodCliente component.
     *
     * @return the initialized component instance
     */
    public TextField getTxtCodCliente() {
        if (txtCodCliente == null) {
//GEN-END:|1360-getter|0|1360-preInit
 // write pre-init user code here
txtCodCliente = new TextField("Codigo de Cliente", null, 32, TextField.NUMERIC);//GEN-LINE:|1360-getter|1|1360-postInit
 // write post-init user code here
}//GEN-BEGIN:|1360-getter|2|
        return txtCodCliente;
    }
//</editor-fold>//GEN-END:|1360-getter|2|



//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand20 ">//GEN-BEGIN:|1367-getter|0|1367-preInit
    /**
     * Returns an initialized instance of okCommand20 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand20() {
        if (okCommand20 == null) {
//GEN-END:|1367-getter|0|1367-preInit
 // write pre-init user code here
okCommand20 = new Command("Alert", Command.OK, 0);//GEN-LINE:|1367-getter|1|1367-postInit
 // write post-init user code here
}//GEN-BEGIN:|1367-getter|2|
        return okCommand20;
    }
//</editor-fold>//GEN-END:|1367-getter|2|



//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand21 ">//GEN-BEGIN:|1370-getter|0|1370-preInit
    /**
     * Returns an initialized instance of okCommand21 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand21() {
        if (okCommand21 == null) {
//GEN-END:|1370-getter|0|1370-preInit
 // write pre-init user code here
okCommand21 = new Command("Ok", Command.OK, 0);//GEN-LINE:|1370-getter|1|1370-postInit
 // write post-init user code here
}//GEN-BEGIN:|1370-getter|2|
        return okCommand21;
    }
//</editor-fold>//GEN-END:|1370-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image14 ">//GEN-BEGIN:|1376-getter|0|1376-preInit
    /**
     * Returns an initialized instance of image14 component.
     *
     * @return the initialized component instance
     */
    public Image getImage14() {
        if (image14 == null) {
//GEN-END:|1376-getter|0|1376-preInit
 // write pre-init user code here
try {//GEN-BEGIN:|1376-getter|1|1376-@java.io.IOException
                image14 = Image.createImage("/facturaIpx.png");
            } catch (java.io.IOException e) {//GEN-END:|1376-getter|1|1376-@java.io.IOException
    e.printStackTrace();
            }//GEN-LINE:|1376-getter|2|1376-postInit
 // write post-init user code here
}//GEN-BEGIN:|1376-getter|3|
        return image14;
    }
//</editor-fold>//GEN-END:|1376-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image15 ">//GEN-BEGIN:|1377-getter|0|1377-preInit
    /**
     * Returns an initialized instance of image15 component.
     *
     * @return the initialized component instance
     */
    public Image getImage15() {
        if (image15 == null) {
//GEN-END:|1377-getter|0|1377-preInit
 // write pre-init user code here
try {//GEN-BEGIN:|1377-getter|1|1377-@java.io.IOException
                image15 = Image.createImage("/impresoraIpx.png");
            } catch (java.io.IOException e) {//GEN-END:|1377-getter|1|1377-@java.io.IOException
    e.printStackTrace();
            }//GEN-LINE:|1377-getter|2|1377-postInit
 // write post-init user code here
}//GEN-BEGIN:|1377-getter|3|
        return image15;
    }
//</editor-fold>//GEN-END:|1377-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: imageItem ">//GEN-BEGIN:|1378-getter|0|1378-preInit
    /**
     * Returns an initialized instance of imageItem component.
     *
     * @return the initialized component instance
     */
    public ImageItem getImageItem() {
        if (imageItem == null) {
//GEN-END:|1378-getter|0|1378-preInit
 // write pre-init user code here
imageItem = new ImageItem("", getImage18(), ImageItem.LAYOUT_DEFAULT, "<Missing Image>");//GEN-LINE:|1378-getter|1|1378-postInit
 // write post-init user code here
}//GEN-BEGIN:|1378-getter|2|
        return imageItem;
    }
//</editor-fold>//GEN-END:|1378-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image16 ">//GEN-BEGIN:|1379-getter|0|1379-preInit
    /**
     * Returns an initialized instance of image16 component.
     *
     * @return the initialized component instance
     */
    public Image getImage16() {
        if (image16 == null) {
//GEN-END:|1379-getter|0|1379-preInit
 // write pre-init user code here
try {//GEN-BEGIN:|1379-getter|1|1379-@java.io.IOException
                image16 = Image.createImage("/user.png");
            } catch (java.io.IOException e) {//GEN-END:|1379-getter|1|1379-@java.io.IOException
    e.printStackTrace();
            }//GEN-LINE:|1379-getter|2|1379-postInit
 // write post-init user code here
}//GEN-BEGIN:|1379-getter|3|
        return image16;
    }
//</editor-fold>//GEN-END:|1379-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image17 ">//GEN-BEGIN:|1380-getter|0|1380-preInit
    /**
     * Returns an initialized instance of image17 component.
     *
     * @return the initialized component instance
     */
    public Image getImage17() {
        if (image17 == null) {
//GEN-END:|1380-getter|0|1380-preInit
 // write pre-init user code here
try {//GEN-BEGIN:|1380-getter|1|1380-@java.io.IOException
                image17 = Image.createImage("/candado.png");
            } catch (java.io.IOException e) {//GEN-END:|1380-getter|1|1380-@java.io.IOException
    e.printStackTrace();
            }//GEN-LINE:|1380-getter|2|1380-postInit
 // write post-init user code here
}//GEN-BEGIN:|1380-getter|3|
        return image17;
    }
//</editor-fold>//GEN-END:|1380-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image18 ">//GEN-BEGIN:|1381-getter|0|1381-preInit
    /**
     * Returns an initialized instance of image18 component.
     *
     * @return the initialized component instance
     */
    public Image getImage18() {
        if (image18 == null) {
//GEN-END:|1381-getter|0|1381-preInit
 // write pre-init user code here
try {//GEN-BEGIN:|1381-getter|1|1381-@java.io.IOException
                image18 = Image.createImage("/usuarioipx.png");
            } catch (java.io.IOException e) {//GEN-END:|1381-getter|1|1381-@java.io.IOException
    e.printStackTrace();
            }//GEN-LINE:|1381-getter|2|1381-postInit
 // write post-init user code here
}//GEN-BEGIN:|1381-getter|3|
        return image18;
    }
//</editor-fold>//GEN-END:|1381-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image19 ">//GEN-BEGIN:|1382-getter|0|1382-preInit
    /**
     * Returns an initialized instance of image19 component.
     *
     * @return the initialized component instance
     */
    public Image getImage19() {
        if (image19 == null) {
//GEN-END:|1382-getter|0|1382-preInit
 // write pre-init user code here
try {//GEN-BEGIN:|1382-getter|1|1382-@java.io.IOException
                image19 = Image.createImage("/clienteIpx.png");
            } catch (java.io.IOException e) {//GEN-END:|1382-getter|1|1382-@java.io.IOException
    e.printStackTrace();
            }//GEN-LINE:|1382-getter|2|1382-postInit
 // write post-init user code here
}//GEN-BEGIN:|1382-getter|3|
        return image19;
    }
//</editor-fold>//GEN-END:|1382-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image20 ">//GEN-BEGIN:|1383-getter|0|1383-preInit
    /**
     * Returns an initialized instance of image20 component.
     *
     * @return the initialized component instance
     */
    public Image getImage20() {
        if (image20 == null) {
//GEN-END:|1383-getter|0|1383-preInit
 // write pre-init user code here
try {//GEN-BEGIN:|1383-getter|1|1383-@java.io.IOException
                image20 = Image.createImage("/produtoIpx.png");
            } catch (java.io.IOException e) {//GEN-END:|1383-getter|1|1383-@java.io.IOException
    e.printStackTrace();
            }//GEN-LINE:|1383-getter|2|1383-postInit
 // write post-init user code here
}//GEN-BEGIN:|1383-getter|3|
        return image20;
    }
//</editor-fold>//GEN-END:|1383-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image21 ">//GEN-BEGIN:|1384-getter|0|1384-preInit
    /**
     * Returns an initialized instance of image21 component.
     *
     * @return the initialized component instance
     */
    public Image getImage21() {
        if (image21 == null) {
//GEN-END:|1384-getter|0|1384-preInit
 // write pre-init user code here
try {//GEN-BEGIN:|1384-getter|1|1384-@java.io.IOException
                image21 = Image.createImage("/productosIpx.png");
            } catch (java.io.IOException e) {//GEN-END:|1384-getter|1|1384-@java.io.IOException
    e.printStackTrace();
            }//GEN-LINE:|1384-getter|2|1384-postInit
 // write post-init user code here
}//GEN-BEGIN:|1384-getter|3|
        return image21;
    }
//</editor-fold>//GEN-END:|1384-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image22 ">//GEN-BEGIN:|1385-getter|0|1385-preInit
    /**
     * Returns an initialized instance of image22 component.
     *
     * @return the initialized component instance
     */
    public Image getImage22() {
        if (image22 == null) {
//GEN-END:|1385-getter|0|1385-preInit
 // write pre-init user code here
try {//GEN-BEGIN:|1385-getter|1|1385-@java.io.IOException
                image22 = Image.createImage("/Impresora.png");
            } catch (java.io.IOException e) {//GEN-END:|1385-getter|1|1385-@java.io.IOException
    e.printStackTrace();
            }//GEN-LINE:|1385-getter|2|1385-postInit
 // write post-init user code here
}//GEN-BEGIN:|1385-getter|3|
        return image22;
    }
//</editor-fold>//GEN-END:|1385-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand22 ">//GEN-BEGIN:|1387-getter|0|1387-preInit
    /**
     * Returns an initialized instance of okCommand22 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand22() {
        if (okCommand22 == null) {
//GEN-END:|1387-getter|0|1387-preInit
 // write pre-init user code here
okCommand22 = new Command("Ok", Command.OK, 0);//GEN-LINE:|1387-getter|1|1387-postInit
 // write post-init user code here
}//GEN-BEGIN:|1387-getter|2|
        return okCommand22;
    }
//</editor-fold>//GEN-END:|1387-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: form1 ">//GEN-BEGIN:|1386-getter|0|1386-preInit
    /**
     * Returns an initialized instance of form1 component.
     *
     * @return the initialized component instance
     */
    public Form getForm1() {
        if (form1 == null) {
//GEN-END:|1386-getter|0|1386-preInit
 // write pre-init user code here
form1 = new Form("form1", new Item[]{textField, getTxt()});//GEN-BEGIN:|1386-getter|1|1386-postInit
            form1.addCommand(getOkCommand22());
            form1.addCommand(getOkCommand23());
            form1.setCommandListener(this);//GEN-END:|1386-getter|1|1386-postInit
 // write post-init user code here

            
            
        }//GEN-BEGIN:|1386-getter|2|
        return form1;
    }
//</editor-fold>//GEN-END:|1386-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand23 ">//GEN-BEGIN:|1389-getter|0|1389-preInit
    /**
     * Returns an initialized instance of okCommand23 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand23() {
        if (okCommand23 == null) {
//GEN-END:|1389-getter|0|1389-preInit
 // write pre-init user code here
okCommand23 = new Command("generar Codigo de control", Command.OK, 0);//GEN-LINE:|1389-getter|1|1389-postInit
 // write post-init user code here
}//GEN-BEGIN:|1389-getter|2|
        return okCommand23;
    }
//</editor-fold>//GEN-END:|1389-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand24 ">//GEN-BEGIN:|1394-getter|0|1394-preInit
    /**
     * Returns an initialized instance of okCommand24 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand24() {
        if (okCommand24 == null) {
//GEN-END:|1394-getter|0|1394-preInit
 // write pre-init user code here
okCommand24 = new Command("Obtener Clientes", Command.OK, 0);//GEN-LINE:|1394-getter|1|1394-postInit
 // write post-init user code here
}//GEN-BEGIN:|1394-getter|2|
        return okCommand24;
    }
//</editor-fold>//GEN-END:|1394-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand25 ">//GEN-BEGIN:|1396-getter|0|1396-preInit
    /**
     * Returns an initialized instance of okCommand25 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand25() {
        if (okCommand25 == null) {
//GEN-END:|1396-getter|0|1396-preInit
 // write pre-init user code here
okCommand25 = new Command("Enviar Facturas ", Command.OK, 0);//GEN-LINE:|1396-getter|1|1396-postInit
 // write post-init user code here
}//GEN-BEGIN:|1396-getter|2|
        return okCommand25;
    }
//</editor-fold>//GEN-END:|1396-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand5 ">//GEN-BEGIN:|1398-getter|0|1398-preInit
    /**
     * Returns an initialized instance of backCommand5 component.
     *
     * @return the initialized component instance
     */
    public Command getBackCommand5() {
        if (backCommand5 == null) {
//GEN-END:|1398-getter|0|1398-preInit
 // write pre-init user code here
backCommand5 = new Command("Atras", Command.BACK, 0);//GEN-LINE:|1398-getter|1|1398-postInit
 // write post-init user code here
}//GEN-BEGIN:|1398-getter|2|
        return backCommand5;
    }
//</editor-fold>//GEN-END:|1398-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: formSincronizacion ">//GEN-BEGIN:|1393-getter|0|1393-preInit
    /**
     * Returns an initialized instance of formSincronizacion component.
     *
     * @return the initialized component instance
     */
    public Form getFormSincronizacion() {
        if (formSincronizacion == null) {
//GEN-END:|1393-getter|0|1393-preInit
 // write pre-init user code here
formSincronizacion = new Form("Reporte de Datos", new Item[]{getStrNumClientes(), getStrNumFacturas()});//GEN-BEGIN:|1393-getter|1|1393-postInit
            formSincronizacion.addCommand(getOkCommand24());
            formSincronizacion.addCommand(getOkCommand30());
            formSincronizacion.addCommand(getOkCommand25());
            formSincronizacion.addCommand(getBackCommand5());
            formSincronizacion.addCommand(getOkCommand27());
            formSincronizacion.addCommand(getOkUpdate());
            formSincronizacion.addCommand(getOkCommand28());
            formSincronizacion.setCommandListener(this);//GEN-END:|1393-getter|1|1393-postInit
 // write post-init user code here
            
        }//GEN-BEGIN:|1393-getter|2|
        return formSincronizacion;
    }
//</editor-fold>//GEN-END:|1393-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: strNumClientes ">//GEN-BEGIN:|1402-getter|0|1402-preInit
    /**
     * Returns an initialized instance of strNumClientes component.
     *
     * @return the initialized component instance
     */
    public StringItem getStrNumClientes() {
        if (strNumClientes == null) {
//GEN-END:|1402-getter|0|1402-preInit
 // write pre-init user code here
strNumClientes = new StringItem("Total Clientes:", null);//GEN-LINE:|1402-getter|1|1402-postInit
 // write post-init user code here
}//GEN-BEGIN:|1402-getter|2|
        return strNumClientes;
    }
//</editor-fold>//GEN-END:|1402-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: strNumFacturas ">//GEN-BEGIN:|1403-getter|0|1403-preInit
    /**
     * Returns an initialized instance of strNumFacturas component.
     *
     * @return the initialized component instance
     */
    public StringItem getStrNumFacturas() {
        if (strNumFacturas == null) {
//GEN-END:|1403-getter|0|1403-preInit
 // write pre-init user code here
strNumFacturas = new StringItem("Cantidad de Facturas:", null);//GEN-LINE:|1403-getter|1|1403-postInit
 // write post-init user code here
}//GEN-BEGIN:|1403-getter|2|
        return strNumFacturas;
    }
//</editor-fold>//GEN-END:|1403-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image23 ">//GEN-BEGIN:|1404-getter|0|1404-preInit
    /**
     * Returns an initialized instance of image23 component.
     *
     * @return the initialized component instance
     */
    public Image getImage23() {
        if (image23 == null) {
//GEN-END:|1404-getter|0|1404-preInit
 // write pre-init user code here
try {//GEN-BEGIN:|1404-getter|1|1404-@java.io.IOException
                image23 = Image.createImage("/synchronize_32.png");
            } catch (java.io.IOException e) {//GEN-END:|1404-getter|1|1404-@java.io.IOException
    e.printStackTrace();
            }//GEN-LINE:|1404-getter|2|1404-postInit
 // write post-init user code here
}//GEN-BEGIN:|1404-getter|3|
        return image23;
    }
//</editor-fold>//GEN-END:|1404-getter|3|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand6 ">//GEN-BEGIN:|1405-getter|0|1405-preInit
    /**
     * Returns an initialized instance of backCommand6 component.
     *
     * @return the initialized component instance
     */
    public Command getBackCommand6() {
        if (backCommand6 == null) {
//GEN-END:|1405-getter|0|1405-preInit
 // write pre-init user code here
backCommand6 = new Command("Cerra Aplicacion", Command.BACK, 0);//GEN-LINE:|1405-getter|1|1405-postInit
 // write post-init user code here
}//GEN-BEGIN:|1405-getter|2|
        return backCommand6;
    }
//</editor-fold>//GEN-END:|1405-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand26 ">//GEN-BEGIN:|1411-getter|0|1411-preInit
    /**
     * Returns an initialized instance of okCommand26 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand26() {
        if (okCommand26 == null) {
//GEN-END:|1411-getter|0|1411-preInit
 // write pre-init user code here
okCommand26 = new Command("Ok", Command.OK, 0);//GEN-LINE:|1411-getter|1|1411-postInit
 // write post-init user code here
}//GEN-BEGIN:|1411-getter|2|
        return okCommand26;
    }
//</editor-fold>//GEN-END:|1411-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand7 ">//GEN-BEGIN:|1413-getter|0|1413-preInit
    /**
     * Returns an initialized instance of backCommand7 component.
     *
     * @return the initialized component instance
     */
    public Command getBackCommand7() {
        if (backCommand7 == null) {
//GEN-END:|1413-getter|0|1413-preInit
 // write pre-init user code here
backCommand7 = new Command("Atras", Command.OK, 0);//GEN-LINE:|1413-getter|1|1413-postInit
 // write post-init user code here
}//GEN-BEGIN:|1413-getter|2|
        return backCommand7;
    }
//</editor-fold>//GEN-END:|1413-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: formLogout ">//GEN-BEGIN:|1408-getter|0|1408-preInit
    /**
     * Returns an initialized instance of formLogout component.
     *
     * @return the initialized component instance
     */
    public Form getFormLogout() {
        if (formLogout == null) {
//GEN-END:|1408-getter|0|1408-preInit
 // write pre-init user code here
formLogout = new Form("Seguridad", new Item[]{getStringItem(), getTextField1()});//GEN-BEGIN:|1408-getter|1|1408-postInit
            formLogout.addCommand(getOkCommand26());
            formLogout.addCommand(getBackCommand7());
            formLogout.setCommandListener(this);//GEN-END:|1408-getter|1|1408-postInit
 // write post-init user code here
}//GEN-BEGIN:|1408-getter|2|
        return formLogout;
    }
//</editor-fold>//GEN-END:|1408-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField1 ">//GEN-BEGIN:|1409-getter|0|1409-preInit
    /**
     * Returns an initialized instance of textField1 component.
     *
     * @return the initialized component instance
     */
    public TextField getTextField1() {
        if (textField1 == null) {
//GEN-END:|1409-getter|0|1409-preInit
 // write pre-init user code here
textField1 = new TextField("Contrase\u00F1a:", null, 32, TextField.NUMERIC | TextField.PASSWORD);//GEN-LINE:|1409-getter|1|1409-postInit
 // write post-init user code here
}//GEN-BEGIN:|1409-getter|2|
        return textField1;
    }
//</editor-fold>//GEN-END:|1409-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: stringItem ">//GEN-BEGIN:|1410-getter|0|1410-preInit
    /**
     * Returns an initialized instance of stringItem component.
     *
     * @return the initialized component instance
     */
    public StringItem getStringItem() {
        if (stringItem == null) {
//GEN-END:|1410-getter|0|1410-preInit
 // write pre-init user code here
stringItem = new StringItem("Para Cerrar la sesion debe ingresar su contrase\u00F1a, ademas de verificar que no tenga facturas pendientes a ser enviadas.", null);//GEN-LINE:|1410-getter|1|1410-postInit
 // write post-init user code here
}//GEN-BEGIN:|1410-getter|2|
        return stringItem;
    }
//</editor-fold>//GEN-END:|1410-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: image24 ">//GEN-BEGIN:|1419-getter|0|1419-preInit
    /**
     * Returns an initialized instance of image24 component.
     *
     * @return the initialized component instance
     */
    public Image getImage24() {
        if (image24 == null) {
//GEN-END:|1419-getter|0|1419-preInit
 // write pre-init user code here
try {//GEN-BEGIN:|1419-getter|1|1419-@java.io.IOException
                image24 = Image.createImage("/cerra.jpg");
            } catch (java.io.IOException e) {//GEN-END:|1419-getter|1|1419-@java.io.IOException
    e.printStackTrace();
            }//GEN-LINE:|1419-getter|2|1419-postInit
 // write post-init user code here
}//GEN-BEGIN:|1419-getter|3|
        return image24;
    }
//</editor-fold>//GEN-END:|1419-getter|3|



//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand27 ">//GEN-BEGIN:|1422-getter|0|1422-preInit
    /**
     * Returns an initialized instance of okCommand27 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand27() {
        if (okCommand27 == null) {
//GEN-END:|1422-getter|0|1422-preInit
 // write pre-init user code here
okCommand27 = new Command("Imprimir Reporte", Command.OK, 0);//GEN-LINE:|1422-getter|1|1422-postInit
 // write post-init user code here
}//GEN-BEGIN:|1422-getter|2|
        return okCommand27;
    }
//</editor-fold>//GEN-END:|1422-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okUpdate ">//GEN-BEGIN:|1424-getter|0|1424-preInit
    /**
     * Returns an initialized instance of okUpdate component.
     *
     * @return the initialized component instance
     */
    public Command getOkUpdate() {
        if (okUpdate == null) {
//GEN-END:|1424-getter|0|1424-preInit
 // write pre-init user code here
okUpdate = new Command("Actualizar Aplicacion", Command.OK, 0);//GEN-LINE:|1424-getter|1|1424-postInit
 // write post-init user code here
}//GEN-BEGIN:|1424-getter|2|
        return okUpdate;
    }
//</editor-fold>//GEN-END:|1424-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand8 ">//GEN-BEGIN:|1427-getter|0|1427-preInit
    /**
     * Returns an initialized instance of backCommand8 component.
     *
     * @return the initialized component instance
     */
    public Command getBackCommand8() {
        if (backCommand8 == null) {
//GEN-END:|1427-getter|0|1427-preInit
 // write pre-init user code here
backCommand8 = new Command("Back", Command.BACK, 0);//GEN-LINE:|1427-getter|1|1427-postInit
 // write post-init user code here
}//GEN-BEGIN:|1427-getter|2|
        return backCommand8;
    }
//</editor-fold>//GEN-END:|1427-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand28 ">//GEN-BEGIN:|1429-getter|0|1429-preInit
    /**
     * Returns an initialized instance of okCommand28 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand28() {
        if (okCommand28 == null) {
//GEN-END:|1429-getter|0|1429-preInit
 // write pre-init user code here
okCommand28 = new Command("Informacion POS", Command.OK, 0);//GEN-LINE:|1429-getter|1|1429-postInit
 // write post-init user code here
}//GEN-BEGIN:|1429-getter|2|
        return okCommand28;
    }
//</editor-fold>//GEN-END:|1429-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: informacion ">//GEN-BEGIN:|1426-getter|0|1426-preInit
    /**
     * Returns an initialized instance of informacion component.
     *
     * @return the initialized component instance
     */
    public Form getInformacion() {
        if (informacion == null) {
//GEN-END:|1426-getter|0|1426-preInit
 // write pre-init user code here
informacion = new Form("Informacion v3.9.5", new Item[]{getStringItem1(), getStringItem2(), getStringItem5(), getStringItem6()});//GEN-BEGIN:|1426-getter|1|1426-postInit
            informacion.addCommand(getBackCommand8());
            informacion.addCommand(getOkCommand29());
            informacion.addCommand(getOkCommand31());
            informacion.setCommandListener(this);//GEN-END:|1426-getter|1|1426-postInit
 // write post-init user code here
            
        }//GEN-BEGIN:|1426-getter|2|
        return informacion;
    }
//</editor-fold>//GEN-END:|1426-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: stringItem1 ">//GEN-BEGIN:|1433-getter|0|1433-preInit
    /**
     * Returns an initialized instance of stringItem1 component.
     *
     * @return the initialized component instance
     */
    public StringItem getStringItem1() {
        if (stringItem1 == null) {
//GEN-END:|1433-getter|0|1433-preInit
 // write pre-init user code here
stringItem1 = new StringItem("sucursal:", null);//GEN-LINE:|1433-getter|1|1433-postInit
 // write post-init user code here
}//GEN-BEGIN:|1433-getter|2|
        return stringItem1;
    }
//</editor-fold>//GEN-END:|1433-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: stringItem2 ">//GEN-BEGIN:|1434-getter|0|1434-preInit
    /**
     * Returns an initialized instance of stringItem2 component.
     *
     * @return the initialized component instance
     */
    public StringItem getStringItem2() {
        if (stringItem2 == null) {
//GEN-END:|1434-getter|0|1434-preInit
 // write pre-init user code here
stringItem2 = new StringItem("facturas backup:", null);//GEN-LINE:|1434-getter|1|1434-postInit
 // write post-init user code here
}//GEN-BEGIN:|1434-getter|2|
        return stringItem2;
    }
//</editor-fold>//GEN-END:|1434-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: stringItem5 ">//GEN-BEGIN:|1435-getter|0|1435-preInit
    /**
     * Returns an initialized instance of stringItem5 component.
     *
     * @return the initialized component instance
     */
    public StringItem getStringItem5() {
        if (stringItem5 == null) {
//GEN-END:|1435-getter|0|1435-preInit
 // write pre-init user code here
stringItem5 = new StringItem("Informacion Adcional:", null);//GEN-LINE:|1435-getter|1|1435-postInit
 // write post-init user code here
}//GEN-BEGIN:|1435-getter|2|
        return stringItem5;
    }
//</editor-fold>//GEN-END:|1435-getter|2|



//<editor-fold defaultstate="collapsed" desc=" Generated Getter: stringItem6 ">//GEN-BEGIN:|1437-getter|0|1437-preInit
    /**
     * Returns an initialized instance of stringItem6 component.
     *
     * @return the initialized component instance
     */
    public StringItem getStringItem6() {
        if (stringItem6 == null) {
//GEN-END:|1437-getter|0|1437-preInit
 // write pre-init user code here
stringItem6 = new StringItem("", null);//GEN-LINE:|1437-getter|1|1437-postInit
 // write post-init user code here
}//GEN-BEGIN:|1437-getter|2|
        return stringItem6;
    }
//</editor-fold>//GEN-END:|1437-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand29 ">//GEN-BEGIN:|1438-getter|0|1438-preInit
    /**
     * Returns an initialized instance of okCommand29 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand29() {
        if (okCommand29 == null) {
//GEN-END:|1438-getter|0|1438-preInit
 // write pre-init user code here
okCommand29 = new Command("Enviar Backup", Command.OK, 0);//GEN-LINE:|1438-getter|1|1438-postInit
 // write post-init user code here
}//GEN-BEGIN:|1438-getter|2|
        return okCommand29;
    }
//</editor-fold>//GEN-END:|1438-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand30 ">//GEN-BEGIN:|1440-getter|0|1440-preInit
    /**
     * Returns an initialized instance of okCommand30 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand30() {
        if (okCommand30 == null) {
//GEN-END:|1440-getter|0|1440-preInit
 // write pre-init user code here
okCommand30 = new Command("Actualizar Clientes", Command.OK, 0);//GEN-LINE:|1440-getter|1|1440-postInit
 // write post-init user code here
}//GEN-BEGIN:|1440-getter|2|
        return okCommand30;
    }
//</editor-fold>//GEN-END:|1440-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand31 ">//GEN-BEGIN:|1442-getter|0|1442-preInit
    /**
     * Returns an initialized instance of okCommand31 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand31() {
        if (okCommand31 == null) {
//GEN-END:|1442-getter|0|1442-preInit
 // write pre-init user code here
okCommand31 = new Command("Eliminar Backup", Command.OK, 0);//GEN-LINE:|1442-getter|1|1442-postInit
 // write post-init user code here
}//GEN-BEGIN:|1442-getter|2|
        return okCommand31;
    }
//</editor-fold>//GEN-END:|1442-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand32 ">//GEN-BEGIN:|1447-getter|0|1447-preInit
    /**
     * Returns an initialized instance of okCommand32 component.
     *
     * @return the initialized component instance
     */
    public Command getOkCommand32() {
        if (okCommand32 == null) {
//GEN-END:|1447-getter|0|1447-preInit
 // write pre-init user code here
okCommand32 = new Command("Ok", Command.OK, 0);//GEN-LINE:|1447-getter|1|1447-postInit
 // write post-init user code here
}//GEN-BEGIN:|1447-getter|2|
        return okCommand32;
    }
//</editor-fold>//GEN-END:|1447-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand2 ">//GEN-BEGIN:|1449-getter|0|1449-preInit
    /**
     * Returns an initialized instance of exitCommand2 component.
     *
     * @return the initialized component instance
     */
    public Command getExitCommand2() {
        if (exitCommand2 == null) {
//GEN-END:|1449-getter|0|1449-preInit
 // write pre-init user code here
exitCommand2 = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|1449-getter|1|1449-postInit
 // write post-init user code here
}//GEN-BEGIN:|1449-getter|2|
        return exitCommand2;
    }
//</editor-fold>//GEN-END:|1449-getter|2|

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: txt ">//GEN-BEGIN:|1453-getter|0|1453-preInit
    /**
     * Returns an initialized instance of txt component.
     *
     * @return the initialized component instance
     */
    public StringItem getTxt() {
        if (txt == null) {
//GEN-END:|1453-getter|0|1453-preInit
 // write pre-init user code here
txt = new StringItem("Archivo", null);//GEN-LINE:|1453-getter|1|1453-postInit
 // write post-init user code here
}//GEN-BEGIN:|1453-getter|2|
        return txt;
    }
//</editor-fold>//GEN-END:|1453-getter|2|














public TextField getTextNativo()
{
    tnativo = new TextField("texto",null,32,TextField.PLAIN);
    tnativo.setInputMode(TextField.INITIAL_CAPS_SENTENCE);
    return tnativo;
}


    private double redondeo(double num,int numDecim){
        long p=1;
        for(int i=0; i<numDecim; i++)p*=10;
        return (double)(int)(p * num + 0.5) / p;
    }
 
    
    public Display getDisplay() {
        return Display.getDisplay(this);
    }

    public void exitMIDlet() {
        switchDisplayable(null, null);
//        try {
            destroyApp(false);
            notifyDestroyed();
            
       
        
    }


    public void startApp() {
        if (midletPaused) {
            resumeMIDlet();
        } else {
            initialize();
            startMIDlet();
        }
        midletPaused = false;
    }

    public void pauseApp() {
        midletPaused = true;
    }
    public void destroyApp(boolean unconditional){
de.enough.polish.util.Debug.exit();//        Thread tg = new Thread(){
//            public void run()
//            {   
//               try {
//			this.storage.save( this.notes, "notes");
//		} catch (IOException e) {
//			
//			System.out.println("Unable to store notes" + e );
//		}
//            }
//        };
//        tg.run();
    }

    
    
   
    public void Cargando()
    {
        Thread tsd = new Thread(){
            public void run()
            {
                switchDisplayable(null,getFormLoading());
            }
        };
        tsd.start();
       
    }
    public void Cargando(BufferRest bufferRest)
    {
        Thread tsd = new Thread(){
            
            public void run()
            {   
                
                switchDisplayable(null,getFormLoading());
            }
        };
        tsd.start();
       
    }
    public void Actualizando()
    {
        Thread tsd = new Thread(){
            public void run()
            {   
//                getFormLoading().setTitle("Actulizando");
                switchDisplayable(null,getFormActualizar());
               
            }
        };
        tsd.start();
       
    }
    public void cambiarPantalla()
    {
        switch(pantalla)
        {
             case AUTENTIFICACION:
                    switchDisplayable(null,getListPrincipal());
                    break;
             case CLIENTE:
                    switchDisplayable(null,getFormDatosCliente());
                    
                    break;
             case CLIENTES:
                    switchDisplayable(null,getFormSincronizacion());
                   break;
                    
             case GUARDARFACTURA:
                    switchDisplayable(null, getListPrincipal());
                    break;
             case CANTPROD:
                    switchDisplayable(null,getListProductos());
                    break;
             case REGISTRARCLIENTE:
                    switchDisplayable(null,getFormRegistro());
                    break;
            
            case PRINTFACTURA:
                switchDisplayable(null,getListPrincipal());
                    break;
             case GETFACTURA:
                    switchDisplayable(null,getFormVistaFactura());
                    break;
             case SINCRONIZAR:
                 switchDisplayable(null,getFormSincronizacion());
                 break;
                 
        }
                    
    }
    public void retornarPantalla()
    {
        switch(pantalla)
        {
            case AUTENTIFICACION:
                switchDisplayable(null,getFormLogin());
                break;
            case CLIENTE:
                switchDisplayable(null,getFormCliente());
                break;    
            case GUARDARFACTURA:
                switchDisplayable(null, getFormFactura());
                break;    
            case CANTPROD:
                switchDisplayable(null,getFormCant());
                break;
            case REGISTRARCLIENTE:
                if(estaRegistrado)
                {
                    switchDisplayable(null,getListMenu());
                }
                else
                {
                    switchDisplayable(null,getFormRegistro());
                }
                
                break;
            case FACTURAS:
                
                    switchDisplayable(null,getListPrincipal());
                break; 
            case PRINTFACTURA:
                switchDisplayable(null,getFormVistaFactura());
                    break;
            case GETFACTURA:
                    switchDisplayable(null,getFormRClient());
                    break;
            case  PRODNOTFOUND:
                    switchDisplayable(null,getFormProd());
        
                    break;
        }
    }   
    public String getAlertTitulo()
    { 
      switch(pantalla)
      {
          case AUTENTIFICACION:
//          case CLIENTE:
               
          case GUARDARFACTURA:
          case VERSION:
          case FACTURAS:
         
          case PRINTFACTURA:
               switch(conexion.getCodigoRespuesta())
                {
                    case 401:
                        titulo= "Autentificacion Fallida";
                        break;
                    case 404:
                        titulo= "Problemas de Conectividad";
                        break;
                    case 500:
                        titulo ="Error del Servidor";
                        break;
//                    case 200:
//                        titulo = "Cliente No Encontrado";
//                        break;
                    default: 
                        titulo="Sin Conexion";
                       
                }
              break;
               case GETFACTURA:
                   switch(conexion.getCodigoRespuesta())
                {
                    case 401:
                        titulo= "Autentificacion Fallida";
                        break;
                    case 404:
                        titulo= "Problemas de Conectividad";
                        break;
                    case 500:
                        titulo ="Error del Servidor";
                        break;
                    case 200:
                        titulo = "Cliente No Encontrado";
                        break;
                    default: 
                        titulo="Sin Conexion";
                       
                }
               break;
          case CLIENTE:
              switch(conexion.getCodigoRespuesta())
                {
                    case 401:
                        titulo= "Autentificacion Fallida";
                        break;
                    case 404:
                        titulo= "Problemas de Conectividad";
                        break;
                    case 500:
                        titulo ="Error del Servidor";
                        break;
                    case 200:
                        titulo ="Cliente no Encontrado";
                        break;
                    default: 
                        titulo="Sin Conexion";
                        break;
                       
                }
              
              break; 
          case CANTPROD:
                titulo= "Casilla de Texto Vacia";
              break;
          case REGISTRARCLIENTE:
                    if(estaRegistrado)
                    {
                        titulo ="Registro Exitoso";
                    }
                    else
                    {
                        switch(conexion.getCodigoRespuesta())
                        {
                            case 401:
                                titulo= "Verifique que el Usuario y Password sean CORRECTOS";
                                break;
                            case 404:
                                titulo= "Se perdio la coneccion con el servidor";
                                break;
                            case 500:
                                titulo= " Conflictos internos con el servidor";
                                break;

                        }
                    }
                 break;
          case PRODNOTFOUND:
                titulo ="Producto no Encontrado";
              break;
         
      }
       
        return this.titulo;
    }
    public String getAlertMensaje()
    {
        switch(pantalla)
        {
            case AUTENTIFICACION:
//            case CLIENTE:
                
            case GUARDARFACTURA:
            case VERSION:
            case FACTURAS:
//            case GETFACTURA:
            case PRINTFACTURA:
            
                switch(conexion.getCodigoRespuesta())
                {
                    case 401:
                        mensaje= "Verifique que el Usuario y Password sean CORRECTOS";
                        break;
                    case 404:
                        mensaje= "Se perdio la coneccion con el servidor";
                        break;
                    case 500:
                        mensaje= "Conflictos internos con el servidor";
                        break;
                    default:
                        mensaje= "Sin conexion a Internet";
                        break;

                }
                
                break;
            case GETFACTURA:
                switch(conexion.getCodigoRespuesta())
                {
                    case 401:
                        mensaje= "Verifique que el Usuario y Password sean CORRECTOS";
                        break;
                    case 404:
                        mensaje= "Se perdio la coneccion con el servidor";
                        break;
                    case 500:
                        mensaje= "Conflictos internos con el servidor";
                        break;
                    case 200:
                        mensaje ="Cliente no Registrado";
                        break;
                    default:
                        mensaje= "Sin conexion a Internet";
                        break;

                }
                break;
            case CLIENTE: 
                switch(conexion.getCodigoRespuesta())
                {
                    case 401:
                        mensaje= "Verifique que el Usuario y Password sean CORRECTOS";
                        break;
                    case 404:
                        mensaje= "Se perdio la coneccion con el servidor";
                        break;
                    case 500:
                        mensaje="Conflictos internos con el servidor";
                        break;
                    case 200:
                        mensaje="Cliente "+txtNit.getText()+" no registrado";
                        break;
                    default:
                        mensaje="Sin conexion a Internet";
                        break;
                }
                
                
                break;
            case CANTPROD:
                 mensaje="Por favor ingrese la cantidad del producto";
                 break;
            case REGISTRARCLIENTE:
                    if(estaRegistrado)
                    {
                        mensaje =" El usuario se registro CORRECTAMENTE";
                    }
                    else
                    {
                        switch(conexion.getCodigoRespuesta())
                        {
                            case 401:
                                mensaje= "Verifique que el Usuario y Password sean CORRECTOS";
                                break;
                            case 404:
                                mensaje= "Se perdio la coneccion con el servidor";
                                break;
                            case 500:
                                mensaje="Conflictos internos con el servidor";
                                break;

                        }
                    }
                 break;
            case PRODNOTFOUND:
                 
                 mensaje="Codigo de producto no Valido";
                 break;
        }
        
        return this.mensaje;
    }
    public Image loadImage(String url) throws IOException {
    HttpConnection hpc = null;
    DataInputStream dis = null;
    try {
      hpc = (HttpConnection) Connector.open(url);
      int length = (int) hpc.getLength();
      byte[] data = new byte[length];
      dis = new DataInputStream(hpc.openInputStream());
      dis.readFully(data);
      return Image.createImage(data, 0, data.length);
    } finally {
      if (hpc != null)
        hpc.close();
      if (dis != null)
        dis.close();
    }
  }
     
    public void NuevaFactura()
    {
     
        getListProductos().deleteAll();
        listaProductos.removeAllElements();
        product_state=SINPRODUCTO;
        if(cliente!=null)
        {
            cliente =null;
        }
        
//        listProductos.deleteAll();
        
//        cliente = null;
//        factura =null;
        
       
    }
   
    public void seleccionarProducto(Products producto,boolean flag)
    {
         for(int i=0;i<cuenta.getProductos().size();i++)
            {
                Products prod = (Products) cuenta.getProductos().elementAt(i);
                if (prod.getNotes().equals(producto.getNotes()))
                {
                    
                    prod.setSeleccionado(flag);
                    i= cuenta.getProductos().size();
                    
                }
                
            }
    }
    public void resetProductos()
    {
        for(int i=0;i<cuenta.getProductos().size();i++)
            {
                Products prod = (Products) cuenta.getProductos().elementAt(i);
                prod.setSeleccionado(false);
                
            }
    }
    public boolean buscarProducto(String codigo)
    {
        boolean b=false;
        for(int i=0;i<cuenta.getProductos().size();i++)
            {
                Products prod = (Products) cuenta.getProductos().elementAt(i);
              
                if (prod.getKey().equals(codigo))
                {
                    b=true;
                    puntero=i;
                    i= cuenta.getProductos().size();
                }
                else
                {
                    b=false;
                }
                 // txtProductKey.setText(txtProductKey.getText()+""+prod.getKey()+"=="+codigo+" "+b);
            }
        //txtProductKey.setText(txtProductKey.getText()+"-"+b);
        return b;
    }
    public boolean buscarListaProductos(String codigo)
    {
        boolean b = false;
        if(listaProductos.size()>0)
        {
        for(int i=0;i<listaProductos.size();i++)
        {
             Products prod = (Products) listaProductos.elementAt(i);
             if(prod.getKey().equals(codigo))
             {
                 b= true;
                 punteroModificar=i;
                 i=listProductos.size();
             }
             else
             {
                 b=false;
             }
        }
        }
        else{
            b=false;
        }
        return b;
    }
    
    public String  nombreProducto()
    {
        Products producto = (Products) cuenta.getProductos().elementAt(puntero);
        String nombre = producto.getKey() +" "+producto.getNotes();
       // producto = null;
        return nombre;
    }
    
    public void LimpiarItems()
    {
        getTxtProductKey().setText("");
        
        getTxtU().setText("");
        getTxtB().setText("");
        getTxtD().setText("");
        getTxtP().setText("");
    }
    
    public void LimpiarCliente()
    {
        getTxtNit().setText("");
        if(cliente!=null)
        {
            cliente = null;
        }
    }
    
    public void LimpiarLogin()
    {
        getTxtUsuario().setText("");
        getTxtPassword().setText("");
        getTxtPassword().setString("");
//        conexion.setLlave("");
//        this.llave ="";
      
        user.setUsuario("");
        user.setPassword("");
        user.setSesion(false);
        try {
                    storage.save( user, "usuario");
            } catch (IOException e) {

                    System.out.println("Unable to store notes" + e );
            }
    }
    
    public boolean estaVacio(TextField t)
    {
       boolean r = false;
       
       if(t.getString().equals(""))
       {
           
           r=true;
           
       }
       
       return r;
    }


    private void imprimirReporte() {
        Log.i("imprimir Reporte","Iniciando proceso");
        pantalla = SINCRONIZAR;
        FacturaOffline facturasDesordenadas[] = new FacturaOffline[facturas.size()];
//        Cargando();
        for(int i=0;i<facturas.size();i++)
        {
           if(!((FacturaOffline)facturas.elementAt(i)).isInvoice())
           {
               ((FacturaOffline)facturas.elementAt(i)).setIsInvoice(true);
               ((FacturaOffline)facturas.elementAt(i)).setInvoice_number(cuenta.getSucursal().getInvoice_number_counter());
               //actualizar el contador de facturas
                int contador= Integer.parseInt(cuenta.getSucursal().getInvoice_number_counter());
        //              
                contador++;
                cuenta.getSucursal().setInvoice_number_counter(""+contador);
                sucursal.setInvoice_number_counter(""+contador);
//                 try{
//                            storage.save(sucursal, "sucursal");
//                    } catch (IOException e) {
//                            System.out.println("no se pudo actualizar el invoice number" + e );
//                    }
               //*************************
               
                String numAutho=cuenta.getSucursal().getNumber_autho();
                String numInvoice=cuenta.getSucursal().getInvoice_number_counter();
           
                String nit=((FacturaOffline)facturas.elementAt(i)).getNitCliente();
                String date =com.david.torrez.DateUtil.getCodigoControFecha();
                String amount=((FacturaOffline)facturas.elementAt(i)).getAmount();
                String keyDosage=cuenta.getSucursal().getKey_dosage();
              try{
                ((FacturaOffline)facturas.elementAt(i)).setControl_code(CodigoDeControl.getCodigoDeControl(nit, numInvoice, date, amount, numAutho, keyDosage));
                }catch(Exception e){
                    System.out.println("Error en el Codigo de Control:" );
                    System.out.println("Numero de autorizacion:"+numAutho );
                    System.out.println("numInvoice:"+numInvoice );
                    System.out.println("Nit:"+nit );
                    System.out.println("Fecha:"+date );
                    System.out.println("Monto:"+amount );
                    System.out.println("Llave de Dosificacion:"+keyDosage );
                }

               }
               //generando copia de respaldo
               backup((FacturaOffline)facturas.elementAt(i));
            facturasDesordenadas[i]= ((FacturaOffline)facturas.elementAt(i));
        }
         try{
                    storage.save(sucursal, "sucursal");
            } catch (IOException e) {
                    System.out.println("no se pudo actualizar el invoice number" + e );
            }
        Log.i("Todos convertidos a facturas ", "size de facturas desordenadas "+facturasDesordenadas.length+"   facturas:"+facturas.size());
        for(int i =0;i<facturasDesordenadas.length;i++)
        {
            for(int j=0;j<facturasDesordenadas.length;j++)
            {
                if(Integer.parseInt(facturasDesordenadas[i].getInvoice_number())<Integer.parseInt(facturasDesordenadas[j].getInvoice_number()))
                {
                    FacturaOffline aux = facturasDesordenadas[i];
                    
                    facturasDesordenadas[i]= facturasDesordenadas[j];
                    facturasDesordenadas[j]=aux;
                }
            }
        }
        
          Log.i("Resporte de Facturas Emitidaas", "--> ");
        Log.i("facturasDesordenadas", "size de facturas desordenadas "+facturasDesordenadas.length+"   facturas:"+facturas.size());
        imprimir = Printer.getInstance();
        imprimir.printText("Reporte de facturas Emitidas", 1);
      
//        Arrays.sort(facturasDesordenadas);
        
        
        
        for(int i=0;i<facturasDesordenadas.length;i++)
        {
//            FacturaOffline factura = facturasDesordenadas[i];
              Log.i("Numero de factura ", facturasDesordenadas[i].getInvoice_number());
            imprimir.printText("\n  Factura Nro. "+facturasDesordenadas[i].getInvoice_number(), 1);
            imprimir.printText("  Cliente: "+facturasDesordenadas[i].getNameCliente(), 1);
            imprimir.printText("  Total Bs: "+facturasDesordenadas[i].getAmount(), 1);
        }
        imprimir.printEndLine();
//        cambiarPantalla();
        
    }

    private Alert alertaCliente() {
        
        //#style mailAlert
        Alert alert = new Alert("Cliente numero  "+cliente.getCliente().getId(), "Ya selecciono al cliente "+ cliente.getCliente().getName()+", \n desea seleccionar otro cliente?", null, AlertType.CONFIRMATION, de.enough.polish.ui.StyleSheet.mailalertStyle );
       final Command cmdYes = new Command("Si", Command.OK, 1);
       final Command cmdNo = new Command("No", Command.CANCEL, 1);
        
        alert.addCommand(cmdYes);
        alert.addCommand(cmdNo);
        alert.setCommandListener(new CommandListener() {
            public void commandAction(Command c, Displayable d) {
                if (c == cmdYes) {
                     switchDisplayable(null,getFormCliente());
                      LimpiarCliente();
                   
                }  
                else{
                     switchDisplayable(null,getListMenu());
                }
            }
        });
        return alert;
    }

 
    
    class CustomItemExample extends CustomItem
    {
        public CustomItemExample(String title)
        {
            super(title);
//            TextField t1 = new TextField("prueba rata", null, 32, TextField.NUMERIC);
       
        }

        public int getMinContentWidth()
        {
        return 100;
        }

        public int getMinContentHeight()
        {
        return 60;
        }

        public int getPrefContentWidth(int width)
        {
        return getMinContentWidth();
        }

        public int getPrefContentHeight(int height)
        {
        return getMinContentHeight();
        }

        public void paint(javax.microedition.lcdui.Graphics g, int w, int h)
        {
        }

//        protected void keyPressed(int keyCode)
//        {
//            getTextField1().setText("code is as - "+keyCode);
//        }
    }
    public Alert getAlerta(String titulo,String mensaje,int form) {
      
        setAlertaTitulo(titulo);
        setAlertaMensaje(mensaje);
        setFormulario(form);
        if(alerta!=null)
        {
            alerta=null;
        }
        if (alerta == null) {
                                     
           
            //#style mailAlert

            alerta = new Alert(getAlertaTitulo(),getAlertaMensaje(),getImage11(),AlertType.CONFIRMATION, de.enough.polish.ui.StyleSheet.mailalertStyle );
            final Command cmdYes = new Command("Aceptar", Command.OK, 1);
           
            alerta.addCommand(cmdYes);
        
            alerta.setCommandListener(new CommandListener() {
                    public void commandAction(Command c, Displayable d) {
                            if (c == cmdYes) {
                                
//                              switchDisplayable(null,formdeRetorno);
                               RetornarAlerta();
                            }			
                    }
            });
                   
           
            
        }                           
        return alerta;
    }
    
    public Alert getAlertaConfirmacion(String titulo,String mensaje)
    {
      
        setAlertaTitulo(titulo);
        setAlertaMensaje(mensaje);
//        setFormulario(form);

     
        if (alertaConfirm == null) {
                                     
           
            //#style mailAlert

            alertaConfirm = new Alert(getAlertaTitulo(),getAlertaMensaje(),getImage11(),AlertType.CONFIRMATION, de.enough.polish.ui.StyleSheet.mailalertStyle );
            final Command cmdYes = new Command("Si", Command.OK, 1);
            final Command cmdNo = new Command("No",Command.CANCEL,1);
            alertaConfirm.addCommand(cmdYes);
            alertaConfirm.addCommand(cmdNo);
        
            alertaConfirm.setCommandListener(new CommandListener() {
                    public void commandAction(Command c, Displayable d) {
                            if (c == cmdYes)
                            {
                                                                
                                 switchDisplayable(null,getFormCant());
//                               RetornarAlerta();
                                productoTemporal = (Products) listaProductos.elementAt(punteroModificar);
            //                   seleccionarProducto(pro,false);
                                 listaProductos.removeElementAt(punteroModificar);

                                 listProductos.delete(punteroModificar);
                                 getFormCant().setTitle( productoTemporal.getKey() +" "+productoTemporal.getNotes());
                                 getTxtP().setText(productoTemporal.getPaquete());
                                 getTxtU().setText(productoTemporal.getUnidad());
                                 getTxtB().setText(productoTemporal.getBoni());
                                 getTxtD().setText(productoTemporal.getDesc());
                                 
                                 swalert = true;
                                
                            }	
                            if(c==cmdNo)
                            {
                                 switchDisplayable(null,getFormProd());
                                 swalert=false;
                            }
                    }
            });
                   
           
            
        }                           
        return alertaConfirm;
    }
    
    public void setAlertaTitulo(String titulo)
    {
        alertaTitulo = titulo;
    }
    public void setAlertaMensaje(String mensaje)
    {
        alertaMensaje = mensaje;
    }
    public void setFormulario(int f)
    {
        formulario = f;
    }
    public void RetornarAlerta()
    {
        switch(formulario)
        {
             case  0:
             case  -1:
                    switchDisplayable(null,getFormRClient());
                 break;
             case PRODNOTFOUND:
                    switchDisplayable(null,getFormProd());
                 break;
             case CLIENTE:
                    switchDisplayable(null, getFormCliente());
                 break;
             case -10:
                    switchDisplayable(null, getFormSincronizacion());
             
                 break;
             case -11:
                    switchDisplayable(null, getFormLogout());
             
                 break;
        }
        
    }
    public String getAlertaTitulo()
    {
        return alertaTitulo;
    }
    public String getAlertaMensaje()
    {
        return alertaMensaje;
    }
//    public double calcularIce(String ice,String cc,String cant)
//    {
//        double c= Double.parseDouble(cc)/1000;
//        double r = Double.parseDouble(ice)*c*Integer.parseInt(cant);
////        String iceproducto=r+"";
//        
//        return r;
//    }
    
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
    
     private static int buscarCliente(Vector clientes,String publicId)
    {
//        Cliente clienteBuscado= null;
        int indice = -1;
        for(int i=0;i<clientes.size();i++)
        {
           //si se encuentra al cliente se retorna al objeto cliente
            if(((Clients ) clientes.elementAt(i)).getId().equals(publicId))
            {
//                 clienteBuscado = new Cliente();
//                 clienteBuscado.setCliente((Clients)clientes.elementAt(i));
             
//                punteroCliente = i;
                indice = i;
                i=clientes.size();
               
            }
        }
        
        return indice;
    }
    public boolean buscarFactura(Vector facturasGuardadas,String idCliente)
    {
        boolean sw=false;
        punteroFactura=-1;
        for(int i=0;i<facturasGuardadas.size();i++)
        {
            FacturaOffline fo= (FacturaOffline) facturasGuardadas.elementAt(i);
            if(fo.getClienteId().equals(idCliente))
            {
                System.out.print("Puntero"+i);
                punteroFactura =i;
//                i=facturasGuardadas.size();
                sw=true;
            }
        }

        
        return sw;
    }
     public Alert getFormEnviar() {
        
        
        
            //#style mailAlert
            
           Alert alert = new Alert("Enviar Facturas?", "Esta seguro de enviar sus facturas?", null, AlertType.CONFIRMATION, de.enough.polish.ui.StyleSheet.mailalertStyle );
           final Command cmdYes = new Command("Si", Command.OK, 1);
           final Command cmdNo =   new Command("No", Command.CANCEL, 1);
           alert.addCommand(cmdYes);
           alert.addCommand(cmdNo);
           alert.setCommandListener(new CommandListener() {
               public void commandAction(Command c, Displayable d) {
                   if (c == cmdYes) {
                       
                      
                       EnviarFacturasGuardadas();

                   } else {
                      switchDisplayable(null,getFormSincronizacion());
                   }               
               }
           });                    
        
        return alert;
    }
      public Form getFormActualizar() {
        
            if(formActualizar==null)
            {
            // write pre-init user code here
            //#style mailAlert
            formActualizar = new Form("Actualizando", new Item[]{getContenidoActulizar()}, de.enough.polish.ui.StyleSheet.mailalertStyle );    
            
            formActualizar.addCommand(getCancelCommand2());
            formActualizar.setCommandListener(this);     
              //#style gaugeItem
                        gauge = new Gauge( "Clientes:", true, 100, 1 , de.enough.polish.ui.StyleSheet.gaugeitemStyle );
                        formActualizar.append(gauge);
            // write post-init user code here
            
            }                 
            getContenidoActulizar().setText("Actualizando Clientes");
        return formActualizar;
    }
    public Form getFormObtenerClientes() {
        
            if(formClientes==null)
            {
                //#style mailAlert
                formClientes = new Form("Obteniendo Clientes", new Item[]{getContenidoClientes()}, de.enough.polish.ui.StyleSheet.mailalertStyle );    

                cmdClientes = new Command("aceptar",Command.OK,1);
                formClientes.addCommand(cmdClientes);
                formClientes.setCommandListener(new CommandListener() {
                   public void commandAction(Command c, Displayable d) {
                       if (c == cmdClientes) {

                           switchDisplayable(null,getFormSincronizacion());
                       }               
                   }
               });      
             
            }                 
          
        return formClientes;
    }
      
//      public Form getActualizarClientes() {
//        
//        
//        
//            //#style mailAlert
//            
//           final Form alert = new Form("Actualizar clientes ?",new Item[]{});
//           final Command cmdYes = new Command("Si", Command.OK, 1);
//           final Command cmdNo =   new Command("No", Command.CANCEL, 1);
//           alert.addCommand(cmdYes);
//           alert.addCommand(cmdNo);
//           alert.append(gauge);
//           alert.setCommandListener(new CommandListener() {
//               public void commandAction(Command c, Displayable d) {
//                   if (c == cmdYes) {
//                      
//                        //#style gaugeItem
////                        gauge = new Gauge( "Tiempo:", isInteractive, max, current );
////                        alert.append(gauge);
////                       EnviarFacturasGuardadas();
////                        gauge.set(10);
//
//                   } else {
//                      switchDisplayable(null,getFormSincronizacion());
//                   }               
//               }
//           });                    
//           
//        return alert;
//    }
    public void EnviarFacturasGuardadas()
    {
        // write pre-action user code here
        pantalla=SINCRONIZAR;

        
        Cargando();
        if(conexion !=null)
        {
            conexion = null;
        }
        conexion = new ConexionIpx();
        Thread t;       
        t = new Thread()
        {
            public void run()
            {

                if(conexion.getCodigoRespuesta()==200)
                {   
                    
//                     switchDisplayable(alerta("Envio Exitoso","Se envio la informacion Correctamente."), getFormSincronizacion());
//                                EliminarFacturas();
                      ResponseSave resultado = new ResponseSave(conexion.getRespuesta());
//                      
//                      
//                   
//                      
//                      
                        Log.i("saveoffline ",conexion.getRespuesta());
                     
                            if(resultado.getResultado().equals("0"))
                            {   
                                Log.i("saveOfflie Resultado",resultado.getResultado());
                                Log.i("saveOfflie Respuesta",""+resultado.getRespuesta());
                                if(resultado.getRespuesta()==facturas.size())
                                {
                                
                              
                                Log.i("saveoffline ",resultado.getResultado());
                                switchDisplayable(alerta("Envio Exitoso","Se envio la informacion Correctamente."), getFormSincronizacion());
                                EliminarFacturas();
                                }
                                else
                                {
                                    Log.i("saveoffline ",resultado.getResultado());
                                    switchDisplayable(alerta("Envio Fallido !! error:"+conexion.getCodigoRespuesta(),"No se pudo enviar la informacion por favor intentelo mas tarde. "), getFormSincronizacion());
                              
                                }
                            }                                
                            if(resultado.getResultado().equals("1"))
                           {
                               Log.i("saveoffline ",resultado.getResultado());
                               switchDisplayable(alerta("Envio Fallido !! error:"+conexion.getCodigoRespuesta(),"No se pudo enviar la informacion por favor intentelo mas tarde. "), getFormSincronizacion());
                                for(int i=0;i<facturas.size();i++)
        {
                                if(!((FacturaOffline)facturas.elementAt(i)).isInvoice())
                                {
                                    ((FacturaOffline)facturas.elementAt(i)).setIsInvoice(true);
                                    ((FacturaOffline)facturas.elementAt(i)).setInvoice_number(cuenta.getSucursal().getInvoice_number_counter());



                                    //actualizar el contador de facturas
                                     int contador= Integer.parseInt(cuenta.getSucursal().getInvoice_number_counter());
                             //              
                                     contador++;
                                     cuenta.getSucursal().setInvoice_number_counter(""+contador);
                                     sucursal.setInvoice_number_counter(""+contador);
                                      try{
                                                 storage.save(sucursal, "sucursal");
                                         } catch (IOException e) {
                                                 System.out.println("no se pudo actualizar el invoice number" + e );
                                         }
                                    //*************************

                                     String numAutho=cuenta.getSucursal().getNumber_autho();
                                     String numInvoice=cuenta.getSucursal().getInvoice_number_counter();
                                     String nit=cliente.getCliente().getNit();
                                     String date =com.david.torrez.DateUtil.getCodigoControFecha();
                                     String amount=factura.getAmount();
                                     String keyDosage=cuenta.getSucursal().getKey_dosage();
                                   try{
                                     ((FacturaOffline)facturas.elementAt(i)).setControl_code(CodigoDeControl.getCodigoDeControl(nit, numInvoice, date, amount, numAutho, keyDosage));
                                     }catch(Exception e){
                                         System.out.println("Error en el Codigo de Control:" );
                                         System.out.println("Numero de autorizacion:"+numAutho );
                                         System.out.println("numInvoice:"+numInvoice );
                                         System.out.println("Nit:"+nit );
                                         System.out.println("Fecha:"+date );
                                         System.out.println("Monto:"+amount );
                                         System.out.println("Llave de Dosificacion:"+keyDosage );
                                     }

                                    }

                             }
                           }
                     
                       
          
                
                }
                else
                {
                    switchDisplayable(alerta("Envio Fallido !! error"+conexion.getCodigoRespuesta(),"Hubo un problema al enviar las facturas, por favor verifique su conexion a internet e intentelo de nuevo "), getFormSincronizacion());
                }

            }
            
        };
        
        conexion.EnviarPost(ConexionIpx.GUARDARFACTURA,SendInvoices.toJSONObjects(facturas),this.user.getllave(),t);
//        conexion.Lenvantate();
        conexion.start();
    }
    
     public void backup(FacturaOffline factura)
    {
         try {
//            facturas.removeAllElements();
//            for(int i=0;i<facturas.size();i++)
//            {
//                 FacturaOffline factura =(FacturaOffline) facturas.elementAt(i);
                 FacturaOffline fac = new FacturaOffline();

        //         fac.setFactura(guardar);
                 fac.setListaProductos(factura.getListaProductos());
                 fac.setClienteId(factura.getClienteId());
                 fac.setItems(factura.getItems());
                 fac.setCodCli(factura.getClienteId());
                 fac.setAddress1(factura.getAddress1());
                 fac.setAddress2(factura.getAddress2());
                 fac.setAmount(factura.getAmount());
                 fac.setControl_code(factura.getControl_code());
                 fac.setFecha_limite(factura.getFecha_limite());
                 fac.setFiscal(factura.getFiscal());
                 fac.setIce(factura.getIce());
                 fac.setInvoice_date(factura.getInvoice_date());
               
                     fac.setInvoice_number(factura.getInvoice_number());
                
                 fac.setLaw(factura.getLaw());
                 fac.setNameCliente(factura.getNameCliente());
                 fac.setNameCuenta(factura.getNameCuenta());
                 fac.setNiCuenta(factura.getNiCuenta());
                 fac.setNitCliente(factura.getNitCliente());
                 fac.setNum_auto(factura.getNum_auto());
                 fac.setSubtotal(factura.getSubtotal());
                 fac.setIsInvoice(factura.isInvoice());
                 //llave de usuario 
                 fac.setLlaveUsuario(user.getllave());
                 

        //         fac.setInvoice(factura);
                 facturasbackup.addElement(fac);
                
//            }
//            
            storage.save(facturasbackup, "facturascopia");
            System.out.print("guardado de facturas copia");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void EliminarFacturas()
    {
        try {
            facturas.removeAllElements();
            
            getStrNumFacturas().setText(""+facturas.size());
            storage.save(facturas, "facturas");
            System.out.print("facturas borrados");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public Alert alerta(final String titulo,final String mensaje)
    {
        //#style mailAlert
        Alert alert = new Alert(titulo, mensaje, getImageInfo(), AlertType.CONFIRMATION, de.enough.polish.ui.StyleSheet.mailalertStyle );
       final Command cmdYes = new Command("Aceptar", Command.OK, 1);

        
        alert.addCommand(cmdYes);
        alert.setCommandListener(new CommandListener() {
            public void commandAction(Command c, Displayable d) {
                if (c == cmdYes) {
                    switch(pantalla)
                    {
                        //me estoy guiando en la variable pantalla para regresar en el alert XD 
                        case SINCRONIZAR:
                            switchDisplayable(null,getFormSincronizacion());
                            break;
                        case CLIENTE:
                            switchDisplayable(null,getFormCliente());
                            break;
                        case LISTMENU:
                             switchDisplayable(null,getListMenu());
                            break;
                        case GUARDARFACTURA:
                            switchDisplayable (null, getFormFactura());
                            break;
                       
                       
                       
                    }
                   
                }           
            }
        });
        return alert;
    }
    public Alert getSelectVenta()
    {
        //#style mailAlert
        Alert alert = new Alert("Tipo de Venta ", " Selecione un tipo de venta", getImageQuestion(), AlertType.CONFIRMATION, de.enough.polish.ui.StyleSheet.mailalertStyle );
       final Command cmdPaquete = new Command(" Paquete", Command.OK, 1);
       final Command cmdUnidad = new Command(" Unidad ", Command.BACK, 1);
        
        alert.addCommand(cmdPaquete);
        alert.addCommand(cmdUnidad);
        alert.setCommandListener(new CommandListener() {
            public void commandAction(Command c, Displayable d) {
                if (c == cmdPaquete) {
                    product_state=PAQUETE;
                   getListProductos().setTitle("Productos seleccionados en paquetes");
                }else{
                    product_state=UNIDAD;
                    getListProductos().setTitle("Productos seleccionados en unidades");
                }           
                switchDisplayable (null, getListProductos());
                
                
            }
        });
        return alert;
    }
   
    public void getAlerta(final String titulo,final String mensaje)
    {   
        //#style mailAlert
        Alert error = new Alert(titulo,mensaje , getImageInfo(), AlertType.INFO, de.enough.polish.ui.StyleSheet.mailalertStyle );
        error.setTimeout(1212313123);
        Display.getDisplay(this).setCurrent(error);
    }
    private void verificarFecha()
    {
        
        if(DateUtil.VerificarFechaAlerta(cuenta.getSucursal().getDeadline()))
        {
            switchDisplayable (alerta("Alerta de Fecha ","su llave de dosificacion esta apunto de vencer, solicite nuevas llaves o no podra emitir facturas"), getFormSincronizacion());
        }
    }
     private void borrarInformacion() {
        
        try {
//                           storage.deleteAll();
                            productos.removeAllElements();
                            clientes.removeAllElements();
                            facturas.removeAllElements();
                            
                            
                            
                            
                            
                            System.out.print("sucursal borrados");
                            
                            user.borrar();
                            storage.save( user,"usuario");
                            
                            System.out.print("productos borrados en teoria");
                            
                            
                            sucursal.borrar();
                            storage.save(sucursal, "sucursal");
                            System.out.print("usuario borrados en teoria");
                            serverInfo.setClientesTxt("");
                            storage.save(serverInfo, "servertxt");                            
                            
                            
//                            this.llave =null;
                           user.setUsuario("");
                           user.setPassword("");
                            System.out.print("productos borrados");
                            
                            storage.save( productos,"productos");
                            System.out.print("productos borrados");
                            storage.save( clientes, "clientes");
                            System.out.print("clientes borrados");
                            storage.save(facturas, "facturas");
                            System.out.print("facturas borrados");
                            
                            
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
    }
     
      
    public StringItem getContenidoActulizar() {
        if (contenidoActualizar == null) {

                    contenidoActualizar = new StringItem("", null);                                       
        }                           
        return contenidoActualizar;
    }
    public StringItem getContenidoClientes()
    {
        if(contenidoClientes ==null)
        {
            contenidoClientes = new StringItem("Respuesta: ",null);
        }
        return contenidoClientes;
    }
    public void procesarLogin(String jsonText)
    {
        
//          switchDisplayable(null,getFormLogin());
//          switchDisplayable(null,getFormActualizar());
//             getContenidoActulizar().setText("Procesando Informacion");           
             cuenta = new Cuenta(jsonText);
//                   cambiarPantalla();
                   getListPrincipal().setTitle("Usuario:"+getTxtUsuario().getText());
//                   Log.i("metho Login", rest.getRespuesta());
                   
                   //guardando usuario 
                   
                   user.setUsuario(getTxtUsuario().getText());
                   user.setPassword(getTxtPassword().getString());
                   user.setSesion(true);
                   user.setIce(cuenta.getIce());
                   
                    try {
                                storage.save( user, "usuario");
//                                getContenidoActulizar().setText("Usuario Registrado");
//                                Log.i("metho Login", "usuario guardado "+user.getUsuario());
                        } catch (IOException e) {

                                
//                                Log.i("metho Login", "Unable to store user XD");
                        }
                    
                    //guardando sucursal
                    sucursal.setActivity_pri(cuenta.getSucursal().getActivity_pri());
                    sucursal.setAddress1(cuenta.getSucursal().getAddress1());
                    sucursal.setAddress2(cuenta.getSucursal().getAddress2());
                    sucursal.setDeadline(cuenta.getSucursal().getDeadline());
                    sucursal.setInvoice_number_counter(cuenta.getSucursal().getInvoice_number_counter());
                    sucursal.setKey_dosage(cuenta.getSucursal().getKey_dosage());
                    sucursal.setLaw(cuenta.getSucursal().getLaw());
                    sucursal.setName(cuenta.getSucursal().getName());
                    sucursal.setNumber_autho(cuenta.getSucursal().getNumber_autho());
                    sucursal.setTerceros(cuenta.getSucursal().getTerceros());
                    
                     try {
                            storage.save( sucursal, "sucursal");
//                            getContenidoActulizar().setText("Sucursal Registrada");
                            Log.i("metho Login", "sucursales sucursal");
                        } catch (IOException e) {
                                Log.i("metho Login", "Unable to store sucursal XD");

                        }
                    productos.removeAllElements();
                    
                    for(int i=0;i<cuenta.getProductos().size();i++)
                    {
//                        getContenidoActulizar().setText("Registrando Producto "+i+" de "+cuenta.getProductos().size());
                        Products pr = (Products) cuenta.getProductos().elementAt(i);
                        productos.addElement(pr);
                    }
                    
                      try {
                                storage.save( productos, "productos");
//                                 Log.i("metho Login", "productos guardados");
//                                getContenidoActulizar().setText("Informacion Cargada Exitosamente");
                		} catch (IOException e) {
//                			Log.i("metho Login", "Unable to store productos XD");
                			
                		}
                    
                    //Cargando el titulo de la lista
                        DateUtil.VerificarFecha(cuenta.getSucursal().getDeadline());
    }
    
}
