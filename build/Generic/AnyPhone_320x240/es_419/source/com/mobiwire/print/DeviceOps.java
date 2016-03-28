/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobiwire.print;


import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import net.sf.microlog.core.Logger;
import net.sf.microlog.core.LoggerFactory;

/**
 * Device operations. Intended to simplify the printing process
 *
 * @author sgherghesanu
 */
public final class DeviceOps {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceOps.class);
    /**
     * Instance of this.
     */
    private static DeviceOps instance = null;

    /**
     * Default constructor.
     */
    private DeviceOps() {
    }

    /**
     * Factory for the instance.
     *
     * @return instance
     */
    public static DeviceOps getInstance() {
        if (instance == null) {
            instance = new DeviceOps();
        }

        return instance;

    }

    /**
     * A method used to read image from BMP format and return a byte array.
     *
     * @param imagePath image path
     * @param typeNum (IMAGE_NORMAL for normal/IMAGE_STRING_ADD for adding string)
     * @return byte[] of image
     */
    public byte[] readImage(String imagePath, int typeNum) {

        int size = 0;
        int bmpDataOffset = 0;
        int bmpHeaderSize = 0;
        int height = 0;
        int width = 0;
        int dataSize = 0;
        int[] rgbquad = null;
        byte[] image = null;
        byte[] imageBytes = null;
        InputStream is = null;
        InputStream dis = null;
        InputStream aais = null;

        try {


            is = getClass().getResourceAsStream(imagePath);

            dis = new DataInputStream(is);
            if (typeNum == 1) {
                dis = null;

                dis = new DataInputStream(aais);
            }

            int bflen = 14;
            byte[] bf = new byte[bflen];
            int reader = dis.read(bf, 0, bflen);
            if(reader == -1) {
                LOGGER.info("No more data, the stream end reached");
            }

            bmpDataOffset = changeInt(bf, 13);

            int bilen = 40;
            byte[] bi = new byte[bilen];

            reader = dis.read(bi, 0, bilen);
            if(reader == -1) {
                LOGGER.info("No more data, the stream end reached");
            }

            // get width and height
            width = changeInt(bi, 7); // width of image

            height = changeInt(bi, 11); // height of image
            if (width > 384) {
                throw new Exception("width is beyond the range (<=384)");
            }
            /*
             * height can be beyond 255 , but if beyond 255 it is suggested to cut image in pieces (height<=255)
             */
            if (height > 255) {
                throw new Exception("height is beyond the range(<=255)");

            }
            /*
             * bytes array of image "4" for the width(2 bit) and height(2 bit) to API
             */
            imageBytes = new byte[4 + width * height / 8];

            // bit depth
            int nbitcount = ((bi[15] & 0xff) << 8) | bi[14] & 0xff;

            // System.err.println("contex bitcount " + nbitcount);

            // ****begin
            image = new byte[width * height];
            int plate = 0;
            switch (nbitcount) {
                case 1:
                    return null;
                case 8:

                    plate = (bmpDataOffset - 54) / 4;
                    // System.out.println("BMP plate size is:" + plate);
                    dataSize = (size - bmpDataOffset);
                    LOGGER.info("[dataSize]" + dataSize);

                    if (plate == 0) {
                        LOGGER.info("Cannot process.");
                    } else {
                        rgbquad = new int[plate];
                        // this.mForm.append("2x:" +
                        // String.valueOf(System.currentTimeMillis()));
                        for (int i = 0; i < plate; i++) {
                            rgbquad[i] = (((DataInputStream) dis).readByte() & 0xff)
                                    | (((DataInputStream) dis).readByte() & 0xff) << 8
                                    | (((DataInputStream) dis).readByte() & 0xff) << 16
                                    | ((((DataInputStream) dis).readByte() & 0xff) << 24);
                        }

                        int dataArrayLen = width * height;
                        byte[] imageData = new byte[dataArrayLen];
                        reader = dis.read(imageData, 0, dataArrayLen);
                        if (reader == -1) {
                            LOGGER.info("No more data, the stream end reached");
                        }
                        int nArray = 0;

                        for (int i = height - 1; i >= 0; i--) {
                            for (int j = 0; j < width; j++) {
                                /*
                                 * because of black and white BMP, "==0" is easy to judge
                                 */
                                if (rgbquad[imageData[nArray++] & 0xff] == 0) {
                                    image[i * width + j] = 1; // 1 for print black
                                    // dot

                                } else {
                                    image[i * width + j] = 0; // 0 for print white
                                    // dot
                                }
                            }
                        }


                        imageBytes[0] = (byte) (width / 8);
                        imageBytes[1] = 0;
                        imageBytes[2] = (byte) (height);// (byte) height; // high
                        imageBytes[3] = 0; // high

                        /*
                         * 8 bit for one byte to print
                         */
                        for (int n = 0; n < width * height / 8; n++) {
                            imageBytes[4 + n] = (byte) ((byte) (image[8 * n + 0] & 0x1) << 7
                                    | (byte) (image[8 * n + 1] & 0x1) << 6
                                    | (byte) (image[8 * n + 2] & 0x1) << 5
                                    | (byte) (image[8 * n + 3] & 0x1) << 4
                                    | (byte) (image[8 * n + 4] & 0x1) << 3
                                    | (byte) (image[8 * n + 5] & 0x1) << 2
                                    | (byte) (image[8 * n + 6] & 0x1) << 1 | (byte) (image[8 * n + 7] & 0x1) << 0);
                            // System.out.print(image_bytes[8+n]+",");
                        }
                        return imageBytes;
                    }
                    break;
                case 24:

                    int dataArrayLen = width * height * 3;
                    byte[] imageData = new byte[dataArrayLen];
                    reader = dis.read(imageData, 0, dataArrayLen);
                    if (reader == -1) {
                        LOGGER.info("No more data, the stream end reached");
                    }

                    int nArray = 0;

                    for (int i = height - 1; i >= 0; i--) {
                        for (int j = 0; j < width; j++) {
                            if ((imageData[nArray++] + imageData[nArray++] + imageData[nArray++]) / 3 == 0) {
                                image[i * width + j] = 1;
                            } else {
                                image[i * width + j] = 0;
                            }
                        }
                    }

                    imageBytes[0] = (byte) (width / 8);
                    imageBytes[1] = 0;
                    imageBytes[2] = (byte) (height);// (byte) height; // low
                    imageBytes[3] = 0; // high
                    for (int n = 0; n < width * height / 8; n++) {
                        imageBytes[4 + n] = (byte) ((byte) (image[8 * n] & 0x1) << 7
                                | (byte) (image[8 * n + 1] & 0x1) << 6
                                | (byte) (image[8 * n + 2] & 0x1) << 5
                                | (byte) (image[8 * n + 3] & 0x1) << 4
                                | (byte) (image[8 * n + 4] & 0x1) << 3
                                | (byte) (image[8 * n + 5] & 0x1) << 2
                                | (byte) (image[8 * n + 6] & 0x1) << 1 | (byte) (image[8 * n + 7] & 0x1) << 0);
                    }

                    return imageBytes;
                default:
                    //do nothing
                    break;

            }

        } catch (Exception ex) {
            LOGGER.error("[Exception]" + ex.getMessage());
        } finally {
            LOGGER.error("[Close the streams]");
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    LOGGER.error("ERROR" + ex.getMessage());
                }
            }
            if (aais != null) {
                try {
                    aais.close();
                } catch (IOException ex) {
                    LOGGER.error("ERROR" + ex.getMessage());
                }
            }
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException ex) {
                    LOGGER.error("ERROR" + ex.getMessage());
                }
            }

        }//finnaly


        return null;

    }

    /**
     * Swifting an array by bytes starting from a value.
     *
     * @param bi array to be changed
     * @param start starting point
     * @return integer
     */
    public int changeInt(byte[] bi, int start) {
        return ((bi[start] & 0xff) << 24) | ((bi[start - 1] & 0xff) << 16)
                | ((bi[start - 2] & 0xff) << 8) | bi[start - 3] & 0xff;
    }
    
    
}
