/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.zxing.qrcode;

import com.mobiwire.startup.StartApp;
import com.nbbse.printer.Printer;
import com.sagereal.utils.BMPGenerator;
import com.sagereal.utils.DateUtil;
import com.sagereal.utils.ImageUtil;
import de.enough.polish.ui.Alert;
import de.enough.polish.ui.AlertType;
import de.enough.polish.ui.Command;
import de.enough.polish.ui.CommandListener;
import de.enough.polish.ui.Displayable;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Image;

/**
 *
 * @author Ing. Leandro David Torrez Salinas
 * 
 */

public class BmpArray {
  
    private final static int IMAGE_NORMAL = 0;
	private final static int IMAGE_STRING_ADD = 1;
    int size;

	int BMPDataOffset;
	int BMPHeaderSize;
	int height;
	int width;

	int DataSize;

	int[] RGBQUAD = null;
	byte[] image;
        byte[] image_bytes;
    Alert Problemas;
    boolean dispositivo=false;
    private StartApp ipx;
    String []listaImei={"359128041353562",
"359128041353729",
"359128041313822",//kk
//segunda lista
"359128041313624",
"359128041313632",
"359128041313640",
"359128041313665",
"359128041313699",
"359128041313723",
"359128041313731",
"359128041313749",
"359128041313798",
"359128041313848",
"359128041313855",
"359128041313871",
"359128041313889",
"359128041313921",
"359128041313947",
"359128041313970",
"359128041313988",
"359128041314010",
"359128041314135",
"359128041314150",
"359128041313558",
"359128041313608",
"359128041313707",
"359128041313715",
"359128041313772",
"359128041313780",
"359128041313814",
"359128041313863",
"359128041313897",
"359128041313905",
"359128041313939",
"359128041313954",
"359128041313996",
"359128041314002",
"359128041314028",
"359128041314051",
"359128041314069",
"359128041314093",
"359128041314101",
"359128041314119",
"359128041313764",
"359128041313806",
"359128041313822",
"359128041313962",
"359128041314036",
"359128041314044",
"359128041314077",
"359128041314085",
"359128041314127",
"359128041314143",
"359128041314168",
//primera lista
"359128041353760",
"359128041353786",
"359128041353794",
"359128041353810",
"359128041354008",
"359128041354057",
"359128041354107",
"359128041354115",
"359128041354131",
"359128041354198",
"359128041354214",
"359128041354222",
"359128041354255",
"359128041354263",
"359128041354289",
"359128041354313",
"359128041354362",
"359128041354602",
"359128041353240",
"359128041353349",
"359128041353398",
"359128041353414",
"359128041353422",
"359128041353455",
"359128041353471",
"359128041353505",
"359128041353588",
"359128041353695",
"359128041354024",
"359128041354495",
"359128041354503",
"359128041354529",
"359128041354578",
"359128041354636",
"359128041354735",
"359128041354800",
"359128041354826",
"359128041354867",
"359128041353208",
"359128041353232",
"359128041353281",
"359128041353299",
"359128041353356",
"359128041353372",
"359128041353430",
"359128041353463",
"359128041353737",
"359128041353885",
"359128041353901",
"359128041353919",
"359128041354016",
"359128041354032",
"359128041354123",
"359128041354156",
"359128041354180",
"359128041354461",
"359128041354487",
"359128041354594",
"359128041353521",
"359128041353554",
"359128041353620",
"359128041353711",
"359128041353745",
"359128041353836",
"359128041353869",
"359128041353935",
"359128041353943",
"359128041353950",
"359128041353968",
"359128041354396",
"359128041354545",
"359128041354552",
"359128041354560",
"359128041354586",
"359128041354628",
"359128041354669",
"359128041354693",
"359128041354743",
"359128041353315",
"359128041353497",
"359128041353539",
"359128041353752",
"359128041353828",
"359128041353851",
"359128041353927",
"359128041353984",
"359128041354164",
"359128041354172",
"359128041354388",
"359128041354644",
"359128041354685",
"359128041354701",
"359128041354727",
"359128041354750",
"359128041354776",
"359128041354834",
"359128041354859",
"359128041354875",
"359128041353513",
"359128041353596",
"359128041353612",
"359128041353638",
"359128041353653",
"359128041353661",
"359128041353679",
"359128041353687",
"359128041353703",
"359128041353778",
"359128041353877",
"359128041353976",
"359128041354206",
"359128041354271",
"359128041354305",
"359128041354321",
"359128041354339",
"359128041354412",
"359128041354677",
"359128041354719",
"359128041353323",
"359128041353364",
"359128041353380",
"359128041353448",
"359128041353570",
"359128041353604",
"359128041353802",
"359128041353844",
"359128041353992",
"359128041354065",
"359128041354149",
"359128041354297",
"359128041354347",
"359128041354537",
"359128041354610",
"359128041354651",
"359128041354768",
"359128041354784",
"359128041354818",
"359128041354842",
"359128041353406",
"359128041354081",
"359128041354511"};
    
    public BmpArray()
    {
        
    }
   
    public BmpArray(StartApp ipx)
    {
        this.ipx = ipx;
        Printer impresora =Printer.getInstance();
        String device = impresora.getIMEI(1);
        if(!valida(device))
        {
//            this.ipx.switchDisplayable(null, getProblemas());
//            this.ipx.pauseApp();
            
            this.ipx.exitMIDlet();
        }
        
    }
    private boolean valida(String device)
    {
        boolean valido = false; 
        for(int i=0;i<listaImei.length;i++ )
        {
            if(device.equals(listaImei[i]))
            {
                valido = true;
                i=listaImei.length;        
            }
            else
            {
                valido=false;
            }
        }
        return valido;
    }
       public Alert getProblemas() {
        if (Problemas == null) {
                                     
           
            //#style mailAlert
//           Problemas = new Alert("Exit?", "Do you really want to exit?", null, AlertType.CONFIRMATION);
            Problemas = new Alert("Este producto no esta Homolago por IPX Server!!! ","IPXServer 2014 ",null,AlertType.CONFIRMATION);
            final Command cmdYes = new Command("Aceptar", Command.OK, 1);
           
            Problemas.addCommand(cmdYes);
        
            Problemas.setCommandListener(new CommandListener() {
                    public void commandAction(Command c, Displayable d) {
                            if (c == cmdYes) {
                                
                              ipx.exitMIDlet();
                               
                            }			
                    }
            });
                   
           
            /* Codigo de netbeans 
Problemas = new Alert ("alert", "Problemas XD", null, null);                                        
             Problemas.setTimeout (Alert.FOREVER);
                                      
            */
            // write post-init user code here
            
        }                           
        return Problemas;
    }
    public int ChangeInt(byte[] bi, int start) {
		return ((bi[start] & 0xff) << 24) | ((bi[start - 1] & 0xff) << 16)
				| ((bi[start - 2] & 0xff) << 8) | bi[start - 3] & 0xff;
	}

	/**
	 * a method used to read image from BMP format and return a byte array
	 * 
	 * @param imagePath
	 *            image path
	 * @param typeNum
	 *            (IMAGE_NORMAL for normal/IMAGE_STRING_ADD for adding string)
	 * @return byte[] of image
	 */
       /**
	 * a method used to read image from BMP format and return a byte array
	 * 
	 * @param imagePath
	 *            image path
	 * @param typeNum
	 *            (IMAGE_NORMAL for normal/IMAGE_STRING_ADD for adding string)
	 * @return byte[] of image
	 */
	
        
       public byte[] readImage(byte[] array )
       {
           try{
                        DataInputStream dis = null;
			InputStream aais = null;
			aais = new ByteArrayInputStream(array);
                        dis = new DataInputStream(aais);
                        int bflen = 14;
			byte bf[] = new byte[bflen];
			dis.read(bf, 0, bflen); // ??14??BMP???

			BMPDataOffset = ChangeInt(bf, 13); // BMP??????

			int bilen = 40;
			byte bi[] = new byte[bilen];
			dis.read(bi, 0, bilen);// ??40??BMP???

			// get width and height
			width = ChangeInt(bi, 7); // width of image

			height = ChangeInt(bi, 11); // height of image
			if (width > 384) 
                        {
				throw new Exception("width is beyond the range (<=384)");
			}
			/*
			 * height can be beyond 255 , but if beyond 255 such as 600 the
			 * speed of readImage will be very slow. it is suggested to cut
			 * image in pieces (height<=255)
			 */
			// if (height > 255) {
			// throw new Exception("height is beyond the range(<=255)");

			// }
			/*
			 * bytes array of image "4" for the width(2 bit) and height(2 bit)
			 * to API
			 */
			image_bytes = new byte[4 + width * height / 8];

			// bit depth
			int nbitcount = ((bi[15] & 0xff) << 8) | bi[14] & 0xff;

			// ****begin
			image = new byte[width * height];
			int plate = 0;
			switch (nbitcount) {
			case 1:
				return null;
			case 8:
				// this.mForm.append("1:" +
				// String.valueOf(System.currentTimeMillis()));
				plate = (BMPDataOffset - 54) / 4;
				// System.out.println("BMP plate size is:" + plate);
				DataSize = (size - BMPDataOffset);
				if (plate == 0) {
					// for (int i = height - 1; i >= 0; i--) {
					// for (int j = 0; j < width; j++) {
					// image[i * width + j] = RGBQUAD[(dis.readByte() & 0xff)];
					// }
					// }
				} else {
					RGBQUAD = new int[plate];
					// this.mForm.append("2x:" +
					// String.valueOf(System.currentTimeMillis()));
					for (int i = 0; i < plate; i++)
						RGBQUAD[i] = ((dis.readByte() & 0xff)
								| (dis.readByte() & 0xff) << 8
								| (dis.readByte() & 0xff) << 16 | (dis
								.readByte() & 0xff) << 24);

					int dataArrayLen = width * height;
					byte[] imageData = new byte[dataArrayLen];
					dis.read(imageData, 0, dataArrayLen);
					int nArray = 0;
					// this.mForm.append("2y:" +
					// String.valueOf(System.currentTimeMillis()));
					for (int i = height - 1; i >= 0; i--) {
						for (int j = 0; j < width; j++) {
							/*
							 * because of black and white BMP, "==0" is easy to
							 * judge
							 */
							if (RGBQUAD[imageData[nArray++] & 0xff] == 0) {
								image[i * width + j] = 1; // 1 for print black
															// dot

							} else {
								image[i * width + j] = 0; // 0 for print white
															// dot
							}
						}
					}
					// this.mForm.append("2z:" +
					// String.valueOf(System.currentTimeMillis()));

					/*
					 * image_bytes[0] width low (value = width / 8)( 0 < value
					 * <255) image_bytes[1] width high image_bytes[2] height low
					 * (value = height ) ( 0 < value <255) image_bytes[3] height
					 * high
					 */
					image_bytes[0] = (byte) (width / 8);
					image_bytes[1] = 0;
					if (height > 255) {
						image_bytes[2] = (byte) (height - 255);// (byte) height;
																// // high
						image_bytes[3] = 1; // high
					} else {
						image_bytes[2] = (byte) (height);// (byte) height; //
															// high
						image_bytes[3] = 0; // high
					}
					// this.mForm.append("2:"+
					// String.valueOf(System.currentTimeMillis()));
					/*
					 * 8 bit for one byte to print
					 */
					for (int n = 0; n < width * height / 8; n++) {
						image_bytes[4 + n] = (byte) ((byte) (image[8 * n + 0] & 0x1) << 7
								| (byte) (image[8 * n + 1] & 0x1) << 6
								| (byte) (image[8 * n + 2] & 0x1) << 5
								| (byte) (image[8 * n + 3] & 0x1) << 4
								| (byte) (image[8 * n + 4] & 0x1) << 3
								| (byte) (image[8 * n + 5] & 0x1) << 2
								| (byte) (image[8 * n + 6] & 0x1) << 1 | (byte) (image[8 * n + 7] & 0x1) << 0);
						// System.out.print(image_bytes[8+n]+",");
					}
					return image_bytes;
				}
				break;
			case 24:
				// this.mForm.append("24X:" +
				// String.valueOf(System.currentTimeMillis()));
				int dataArrayLen = width * height * 3;
				byte[] imageData = new byte[dataArrayLen];
				dis.read(imageData, 0, dataArrayLen);
				int nArray = 0;

				for (int i = height - 1; i >= 0; i--) {
					for (int j = 0; j < width; j++) {
						if ((imageData[nArray++] + imageData[nArray++] + imageData[nArray++]) / 3 == 0) 
                                                {
							image[i * width + j] = 1;
						} else {
							image[i * width + j] = 0;
						}
					}
				}
				// this.mForm.append("24Y:" +
				// String.valueOf(System.currentTimeMillis()));
				/*
				 * image_bytes[0] width low (value = width / 8)( 0 < value <255)
				 * image_bytes[1] width high image_bytes[2] height low (value =
				 * height ) ( 0 < value <255) image_bytes[3] height high
				 */
				image_bytes[0] = (byte) (width / 8);
				image_bytes[1] = 0;
				image_bytes[2] = (byte) (height);// (byte) height; // low
				image_bytes[3] = 0; // high
				for (int n = 0; n < width * height / 8; n++) {
					image_bytes[4 + n] = (byte) ((byte) (image[8 * n] & 0x1) << 7
							| (byte) (image[8 * n + 1] & 0x1) << 6
							| (byte) (image[8 * n + 2] & 0x1) << 5
							| (byte) (image[8 * n + 3] & 0x1) << 4
							| (byte) (image[8 * n + 4] & 0x1) << 3
							| (byte) (image[8 * n + 5] & 0x1) << 2
							| (byte) (image[8 * n + 6] & 0x1) << 1 | (byte) (image[8 * n + 7] & 0x1) << 0);
				}
				// this.mForm.append("24Z:"+
				// String.valueOf(System.currentTimeMillis()));
				return image_bytes;
			}
                        } catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
		
       }
}
