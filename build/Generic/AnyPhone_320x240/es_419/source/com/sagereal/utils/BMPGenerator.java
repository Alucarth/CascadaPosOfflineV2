/*
 * Copyright ThinkTank Mathematics Limited 2007
 * 
 * This file is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This file is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this file. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package com.sagereal.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import  de.enough.polish.ui.Image;

/*
 * this is a class that can generate BMP got from Internet
 */
/**
 * BMPGenerator class.
 * 
 * @author Samuel Halliday, ThinkTank Mathematics Limited
 */
public final class BMPGenerator {

	/**
	 * Default constructor.
	 */
	private BMPGenerator() {
		super();
	}

	/**
	 * Encode BMP function.
	 * 
	 * @param image
	 *            image to encode
	 * @return a byte array.
	 * @throws IOException
	 *             in case of any errors.
	 * @see {@link #encodeBMP(int[], int, int)}
	 */
	public static byte[] encodeBMP(javax.microedition.lcdui.Image image) throws IOException {
		int width = image.getWidth();
		int height = image.getHeight();
		int[] rgb = new int[height * width];
		image.getRGB(rgb, 0, width, 0, 0, width, height);
		return encodeBMP(rgb, width, height);
	}

	/**
	 * A self-contained BMP generator, which takes a byte array (without any unusual offsets) extracted from an
	 * {@link Image}. The target platform is J2ME. You may wish to use the convenience method {@link #encodeBMP(Image)}
	 * instead of this.
	 * <p>
	 * A BMP file consists of 4 parts:-
	 * <ul>
	 * <li>header</li>
	 * <li>information header</li>
	 * <li>optional palette</li>
	 * <li>image data</li>
	 * </ul>
	 * At this time only 24 bit uncompressed BMPs with Windows V3 headers can be created. Future releases may become
	 * much more space-efficient, but will most likely be ditched in favour of a PNG generator.
	 * 
	 * @param rgb
	 *            rgb
	 * @param width
	 *            width
	 * @param height
	 *            height
	 * @return byte array with encoded BMP
	 * @throws IOException
	 *             in case of errors
	 * @see http://en.wikipedia.org/wiki/Windows_bitmap
	 */
	public static byte[] encodeBMP(int[] rgb, int width, int height) throws IOException {
		int pad = (4 - (width % 4)) % 4;
		// the size of the BMP file in bytes
		int size = 14 + 40 + height * (pad + width * 3);
		ByteArrayOutputStream bytes = new ByteArrayOutputStream(size);
		DataOutputStream stream = new DataOutputStream(bytes);
		// HEADER
		// the magic number used to identify the BMP file: 0x42 0x4D
		stream.writeByte(0x42);
		stream.writeByte(0x4D);
		stream.writeInt(swapEndian(size));
		// reserved
		stream.writeInt(0);
		// the offset, i.e. starting address of the bitmap data
		stream.writeInt(swapEndian(14 + 40));
		// INFORMATION HEADER (Windows V3 header)
		// the size of this header (40 bytes)
		stream.writeInt(swapEndian(40));
		// the bitmap width in pixels (signed integer).
		stream.writeInt(swapEndian(width));
		// the bitmap height in pixels (signed integer).
		stream.writeInt(swapEndian(height));
		// the number of colour planes being used. Must be set to 1.
		stream.writeShort(swapEndian((short) 1));
		// the number of bits per pixel, which is the colour depth of the image.
		stream.writeShort(swapEndian((short) 24));
		// the compression method being used.
		stream.writeInt(0);
		// image size. The size of the raw bitmap data. 0 is valid for
		// uncompressed.
		stream.writeInt(0);
		// the horizontal resolution of the image. (pixel per meter, signed
		// integer)
		stream.writeInt(0);
		// the vertical resolution of the image. (pixel per meter, signed
		// integer)
		stream.writeInt(0);
		// the number of colours in the colour palette, or 0 to default to 2n.
		stream.writeInt(0);
		// the number of important colours used, or 0 when every colour is
		// important;
		// generally ignored.
		stream.writeInt(0);
		// PALETTE
		// none for 24 bit depth
		// IMAGE DATA
		// starting in the bottom left, working right and then up
		// a series of 3 bytes per pixel in the order B G R.
		for (int j = height - 1; j >= 0; j--) {
			for (int i = 0; i < width; i++) {
				int val = rgb[i + width * j];
				stream.writeByte(val & 0x000000FF);
				stream.writeByte((val >>> 8) & 0x000000FF);
				stream.writeByte((val >>> 16) & 0x000000FF);
			}
			// number of bytes in each row must be padded to multiple of 4
			for (int i = 0; i < pad; i++) {
				stream.writeByte(0);
			}
		}
		byte[] out = bytes.toByteArray();
		bytes.close();
		// quick consistency check
		if (out.length != size) {
			throw new RuntimeException("bad math");
		}
		return out;
	}

	/**
	 * Swap the Endian-ness of a 32 bit integer.
	 * 
	 * @param value
	 *            value to be swapped
	 * @return swapped value.
	 */
	private static int swapEndian(int value) {
		int b1 = value & 0xff;
		int b2 = (value >> 8) & 0xff;
		int b3 = (value >> 16) & 0xff;
		int b4 = (value >> 24) & 0xff;

		return b1 << 24 | b2 << 16 | b3 << 8 | b4 << 0;
	}

	/**
	 * Swap the Endian-ness of a 16 bit integer.
	 * 
	 * @param value
	 *            value to be swapped
	 * @return swapped value
	 */
	private static short swapEndian(short value)
        {
		int b1 = value & 0xff;
		int b2 = (value >> 8) & 0xff;

		return (short) (b1 << 8 | b2 << 0);
	}

}
