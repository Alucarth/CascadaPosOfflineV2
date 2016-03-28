package com.sagereal.utils;

import java.io.IOException;

import  de.enough.polish.ui.Font;
import  de.enough.polish.ui.Graphics;
import  de.enough.polish.ui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 * a class to process images. use {@link #image_add_str(Image, String, int, int, int)} to add string on image(like
 * date&time and transcation ID and price and PIN num)
 * 
 * @author Zheng Zhida (zhida.zheng@nbbse.com)
 * 
 */
public final class ImageUtil extends IOException {

	/**
	 * Generated UID.
	 */
	private static final long serialVersionUID = -3271483708124701405L;
	/**
	 * Turn left.
	 */
	public static final int TURN_LEFT = 1;
	/**
	 * Turn right.
	 */
	public static final int TURN_RIGHT = 2;

	/**
	 * Get pixels.
	 * 
	 * @param src
	 *            source image
	 * @return pixels array
	 */
	public int[] getPixels(Image src) {
		int w = src.getWidth();
		int h = src.getHeight();
		int[] pixels = new int[w * h];
		src.getRGB(pixels, 0, w, 0, 0, w, h);
		return pixels;
	}

	/**
	 * Draw pixels.
	 * 
	 * @param pixels
	 *            pixels to draw
	 * @param w
	 *            width
	 * @param h
	 *            height
	 * @return image
	 */
	public Image drawPixels(int[] pixels, int w, int h) {
		Image image = Image.createRGBImage(pixels, w, h, true);
		pixels = null;
		return image;
	}

	/**
	 * Resize image.
	 * 
	 * @param src
	 *            source image
	 * @param destW
	 *            destination width
	 * @param destH
	 *            destination height
	 * @return the resized image.
	 */
	public Image effectResizeImage(Image src, int destW, int destH) {
		int srcW = src.getWidth();
		int srcH = src.getHeight();

		int[] destPixels = new int[destW * destH];

		int[] srcPixels = getPixels(src);

		for (int destY = 0; destY < destH; ++destY) {
			for (int destX = 0; destX < destW; ++destX) {
				int srcX = (destX * srcW) / destW;
				int srcY = (destY * srcH) / destH;
				destPixels[destX + destY * destW] = srcPixels[srcX + srcY * srcW];
			}
		}

		return drawPixels(destPixels, destW, destH);
	}

	/**
	 * Add light contrast.
	 * 
	 * @param src
	 *            source image
	 * @param contrast
	 *            contrast value
	 * @param light
	 *            luminozity value
	 * @return modified image.
	 */
	public Image effectLightContrast(Image src, double contrast, int light) {
		int srcW = src.getWidth();
		int srcH = src.getHeight();
		int[] srcPixels = getPixels(src);
		int r = 0;
		int g = 0;
		int b = 0;
		int a = 0;
		int argb;

		// int para_b = light - 127 * (light - 1);
		for (int i = 0; i < srcH; i++) {
			for (int ii = 0; ii < srcW; ii++) {
				argb = srcPixels[i * srcW + ii];
				a = ((argb & 0xff000000) >> 24); // alpha channel
				r = ((argb & 0x00ff0000) >> 16); // red channel
				g = ((argb & 0x0000ff00) >> 8); // green channel
				b = (argb & 0x000000ff); // blue channel
				r = (int) (r * contrast + light);
				g = (int) (g * contrast + light);
				b = (int) (b * contrast + light);

				/*
				 * r =(int)((r -127 ) * contrast + 127+para_b); g =(int)((g -127 ) * contrast + 127+para_b); b =(int)((b
				 * -127 ) * contrast + 127+para_b);
				 */
				if (r > 255) {
					r = 255;
				} else if (r < 0) {
					r = 0;
				}
				if (g > 255) {
					g = 255;
				} else if (g < 0) {
					g = 0;
				}
				if (b > 255) {
					b = 255;
				} else if (b < 0) {
					b = 0;
				}
				srcPixels[i * srcW + ii] = ((a << 24) | (r << 16) | (g << 8) | b);

			}
		}
		return drawPixels(srcPixels, srcW, srcH);
	}

	/**
	 * Mirror effect.
	 * 
	 * @param src
	 *            source image
	 * @return mirrored image
	 */
	public Image effectMirror(Image src) {
		int srcW = src.getWidth();
		int srcH = src.getHeight();
		int[] srcPixels = getPixels(src);
		int len;
		int temp;
		for (int i = 0; i < srcH; i++) {
			len = (i + 1) * srcW;
			for (int ii = 0; ii < srcW / 2; ii++) {
				temp = srcPixels[i * srcW + ii];
				srcPixels[i * srcW + ii] = srcPixels[len - 1 - ii];
				srcPixels[len - 1 - ii] = temp;
			}
		}
		return drawPixels(srcPixels, srcW, srcH);
	}

	/**
	 * Cut effect.
	 * 
	 * @param src
	 *            source image
	 * @param cutXPos
	 *            x position to cut
	 * @param cutYPos
	 *            y posistion to cut
	 * @param cutWidth
	 *            width
	 * @param cutHeight
	 *            height
	 * @return cut image
	 */
	public Image effectCut(Image src, int cutXPos, int cutYPos, int cutWidth, int cutHeight) {
		int srcW = src.getWidth();
		int srcH = src.getHeight();
		int[] srcPixels = getPixels(src);
		int[] desPixels = new int[cutWidth * cutHeight];
		int argb;
		int num = 0;
		for (int i = 0; i < srcH; i++) {
			if (i >= cutYPos && i < cutHeight + cutYPos) {
				for (int ii = 0; ii < srcW; ii++) {
					if (ii >= cutXPos && ii < cutWidth + cutXPos) {
						desPixels[num] = srcPixels[i * srcW + ii];
						num++;

					}
				}
			}
		}
		return drawPixels(desPixels, cutWidth, cutHeight);
	}

	/**
	 * Add image.
	 * 
	 * @param src
	 *            source image.
	 * @param image
	 *            image to add
	 * @param xPpos
	 *            x position
	 * @param yPos
	 *            y position
	 * @return modified image.
	 * 
	 */
	public Image effectImageAddImage(Image src, Image image, int xPpos, int yPos) {
		Image temp = Image.createImage(src.getWidth(), src.getHeight());
		Graphics g = temp.getGraphics();
		// g.drawImage(src,x_pos,y_pos,Graphics.LEFT|Graphics.TOP);
		// g.drawImage(image,x_pos,y_pos,Graphics.LEFT|Graphics.TOP);*/
		int alpha = 168;
		int[] srcRgbdata = new int[src.getWidth() * src.getHeight()];
		int[] desRgbdata = new int[image.getWidth() * image.getHeight()];
		src.getRGB(srcRgbdata, 0, src.getWidth(), 0, 0, src.getWidth(), src.getHeight());
		image.getRGB(desRgbdata, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
		g.drawRGB(getTransImg(alpha, srcRgbdata, desRgbdata), 0, src.getWidth(), 0, 0, src.getWidth(), src.getHeight(),
				false);
		src = null;
		image = null;
		return temp;
	}

	/**
	 * Add string on Image.
	 * 
	 * @param imageSrc
	 *            image source
	 * @param str
	 *            the string
	 * @param xPos
	 *            position x-axis
	 * @param yPos
	 *            position y-axis
	 * @param size
	 *            Font size (Font.SIZE_LARGE/Font.SIZE_MEDIUM/Font.SIZE_SMALL)
	 * @return Image with string
	 */
	public static Image imageAddStr(Image imageSrc, String str, int xPos, int yPos, int size) {
		Image temp = Image.createImage(imageSrc.getWidth(), imageSrc.getHeight());
		Graphics g = temp.getGraphics();
		g.drawImage(imageSrc, 0, 0, Graphics.LEFT | Graphics.TOP);
		g.setColor(0x000000);

		Font myFont;
		myFont = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN | Font.STYLE_BOLD, size);// ��������

		g.setFont(myFont);

		g.drawString(str, xPos, yPos, Graphics.LEFT | Graphics.TOP);
		return temp;
	}

	/**
	 * Negative effect.
	 * 
	 * @param src
	 *            source image
	 * @return modified image
	 */
	public Image effectNegative(Image src) {
		int srcW = src.getWidth();
		int srcH = src.getHeight();
		int[] srcPixels = getPixels(src);
		int r = 0;
		int g = 0;
		int b = 0;
		int a = 0;
		int argb;
		for (int i = 0; i < srcH; i++) {
			for (int ii = 0; ii < srcW; ii++) {
				argb = srcPixels[i * srcW + ii];
				a = ((argb & 0xff000000) >> 24); // alpha channel
				r = 255 - ((argb & 0x00ff0000) >> 16); // red channel
				g = 255 - ((argb & 0x0000ff00) >> 8); // green channel
				b = 255 - (argb & 0x000000ff); // blue channel
				srcPixels[i * srcW + ii] = ((a << 24) | (r << 16) | (g << 8) | b);
			}
		}
		return drawPixels(srcPixels, srcW, srcH);

	}

	/**
	 * Black & white effect.
	 * 
	 * @param src
	 *            source image
	 * @return modified image
	 */
	public Image effectBlackWhite(Image src) {
		int srcW = src.getWidth();
		int srcH = src.getHeight();
		int[] srcPixels = getPixels(src);
		int r = 0;
		int g = 0;
		int b = 0;
		int a = 0;
		int argb;
		int temp;

		for (int i = 0; i < srcH; i++) {
			for (int ii = 0; ii < srcW; ii++) {
				argb = srcPixels[i * srcW + ii];
				a = ((argb & 0xff000000) >> 24); // alpha channel
				r = ((argb & 0x00ff0000) >> 16); // red channel
				g = ((argb & 0x0000ff00) >> 8); // green channel
				b = (argb & 0x000000ff); // blue channel
				temp = (int) (.299 * r + .587 * g + .114 * b);
				r = temp;
				g = temp;
				b = temp;
				srcPixels[i * srcW + ii] = ((a << 24) | (r << 16) | (g << 8) | b);
			}
		}
		return drawPixels(srcPixels, srcW, srcH);

	}

	/**
	 * Crayon effect.
	 * 
	 * @param src
	 *            source image
	 * @return modified image
	 */
	public Image effectCrayon(Image src) {
		int srcW = src.getWidth();
		int srcH = src.getHeight();
		int[] srcPixels = getPixels(src);
		int r = 0;
		int g = 0;
		int b = 0;
		int a = 0;
		int argb;
		int r1 = 0;
		int g1 = 0;
		int b1 = 0;
		int a1 = 0;
		int r2 = 0;
		int g2 = 0;
		int b2 = 0;
		int a2 = 0;

		for (int i = 0; i < srcH; i++) {
			for (int ii = 0; ii < srcW; ii++) {
				argb = srcPixels[i * srcW + ii];
				a = ((argb & 0xff000000) >> 24); // alpha channel
				r = ((argb & 0x00ff0000) >> 16); // red channel
				g = ((argb & 0x0000ff00) >> 8); // green channel
				b = (argb & 0x000000ff); // blue channel
				if (i + 1 == srcH) {
					r1 = 0;
					g1 = 0;
					b1 = 0;
				} else {
					argb = srcPixels[(i + 1) * srcW + ii];
					// a1 = ((argb & 0xff000000) >> 24); // alpha channel
					r1 = ((argb & 0x00ff0000) >> 16); // red channel
					g1 = ((argb & 0x0000ff00) >> 8); // green channel
					b1 = (argb & 0x000000ff); // blue channel
				}
				if (ii + 1 == srcW) {
					r2 = 0;
					g2 = 0;
					b2 = 0;
				} else {
					argb = srcPixels[i * srcW + ii + 1];
					r2 = ((argb & 0x00ff0000) >> 16); // red channel
					g2 = ((argb & 0x0000ff00) >> 8); // green channel
					b2 = (argb & 0x000000ff); // blue channel
				}
				// rr1=(r1-r2)^2 rr2=(r1-r3)^2
				r = (int) Math.sqrt((2 * (r - r1) * (r - r1) + (r - r2) * (r - r2)));
				g = (int) Math.sqrt((2 * (g - g1) * (g - g1) + (g - g2) * (g - g2)));
				b = (int) Math.sqrt((2 * (b - b1) * (b - b1) + (b - b2) * (b - b2)));
				r = 255 - r; // red channel
				g = 255 - g; // green channel
				b = 255 - b; // blue channel
				srcPixels[i * srcW + ii] = ((a << 24) | (r << 16) | (g << 8) | b);
			}
		}
		return drawPixels(srcPixels, srcW, srcH);
	}

	/**
	 * HoodWink effect.
	 * 
	 * @param src
	 *            image source
	 * @return modified image
	 */
	public Image effectHoodwink(Image src) {
		int srcW = src.getWidth();
		int srcH = src.getHeight();
		int[] srcPixels = getPixels(src);
		int r = 0;
		int g = 0;
		int b = 0;
		int a = 0;
		int argb;

		for (int i = 0; i < srcH; i++) {
			for (int ii = 0; ii < srcW; ii++) {
				argb = srcPixels[i * srcW + ii];
				a = ((argb & 0xff000000) >> 24); // alpha channel
				r = ((argb & 0x00ff0000) >> 16); // red channel
				g = ((argb & 0x0000ff00) >> 8); // green channel
				b = (argb & 0x000000ff); // blue channel
				r = (int) (.299 * r);
				g = (int) (.587 * g);
				b = (int) (.114 * b);
				srcPixels[i * srcW + ii] = ((a << 24) | (r << 16) | (g << 8) | b);
			}
		}
		return drawPixels(srcPixels, srcW, srcH);

	}

	/**
	 * Transposed image.
	 * 
	 * @param alpha
	 *            alpha
	 * @param srcRgbdata
	 *            source RGB
	 * @param desRgbdata
	 *            destination RGB
	 * @return integer array.
	 */
	private int[] getTransImg(int alpha, int[] srcRgbdata, int[] desRgbdata) {
		int[] tempRgbData = new int[desRgbdata.length];

		int sr;
		int sg;
		int sb;
		int dr;
		int dg;
		int db;
		int tr;
		int tg;
		int tb;
		for (int i = 0; i < desRgbdata.length; i++) {
			sr = (srcRgbdata[i] & 0xff0000) >> 16;
			sg = (srcRgbdata[i] & 0xff00) >> 8;
			sb = srcRgbdata[i] & 0xff;
			dr = (desRgbdata[i] & 0xff0000) >> 16;
			dg = (desRgbdata[i] & 0xff00) >> 8;
			db = desRgbdata[i] & 0xff;
			tr = (sr * alpha + dr * (255 - alpha)) / 255;
			tg = (sg * alpha + dg * (255 - alpha)) / 255;
			tb = (sb * alpha + db * (255 - alpha)) / 255;
			tempRgbData[i] = (tr << 16) | (tg << 8) | tb;
		}
		return tempRgbData;
	}

	

	/**
	 * Neon light effect.
	 * 
	 * @param src
	 *            source image
	 * @return modified image
	 */
	public Image effectNeonLight(Image src) {
		int srcW = src.getWidth();
		int srcH = src.getHeight();
		int[] srcPixels = getPixels(src);
		int r = 0;
		int g = 0;
		int b = 0;
		int a = 0;
		int argb;
		int r1 = 0;
		int g1 = 0;
		int b1 = 0;
		int a1 = 0;
		int r2 = 0;
		int g2 = 0;
		int b2 = 0;
		int a2 = 0;

		for (int i = 0; i < srcH; i++) {
			for (int ii = 0; ii < srcW; ii++) {
				argb = srcPixels[i * srcW + ii];
				a = ((argb & 0xff000000) >> 24); // alpha channel
				r = ((argb & 0x00ff0000) >> 16); // red channel
				g = ((argb & 0x0000ff00) >> 8); // green channel
				b = (argb & 0x000000ff); // blue channel
				if (i + 1 == srcH) {
					r1 = 0;
					g1 = 0;
					b1 = 0;
				} else {
					argb = srcPixels[(i + 1) * srcW + ii];
					// a1 = ((argb & 0xff000000) >> 24); // alpha channel
					r1 = ((argb & 0x00ff0000) >> 16); // red channel
					g1 = ((argb & 0x0000ff00) >> 8); // green channel
					b1 = (argb & 0x000000ff); // blue channel
				}
				if (ii + 1 == srcW) {
					r2 = 0;
					g2 = 0;
					b2 = 0;
				} else {
					argb = srcPixels[i * srcW + ii + 1];
					r2 = ((argb & 0x00ff0000) >> 16); // red channel
					g2 = ((argb & 0x0000ff00) >> 8); // green channel
					b2 = (argb & 0x000000ff); // blue channel
				}
				// rr1=(r1-r2)^2 rr2=(r1-r3)^2
				r = (int) Math.sqrt((2 * (r - r1) * (r - r1) + (r - r2) * (r - r2)));
				g = (int) Math.sqrt((2 * (g - g1) * (g - g1) + (g - g2) * (g - g2)));
				b = (int) Math.sqrt((2 * (b - b1) * (b - b1) + (b - b2) * (b - b2)));
				srcPixels[i * srcW + ii] = ((a << 24) | (r << 16) | (g << 8) | b);
			}
		}
		return drawPixels(srcPixels, srcW, srcH);
	}

}