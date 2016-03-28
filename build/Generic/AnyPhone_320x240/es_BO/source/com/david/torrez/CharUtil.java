/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.david.torrez;

/**
 *
 * @author David
 */
/**
 * Character Utility class
 * @author mkyong
 *
 */
public class CharUtil 
{   
	/**
	 * Convert the characters to ASCII value
	 * @param character character
	 * @return ASCII value
	 */
	public static int CharToASCII(final char character){
		return (int)character;
	}
 
	/**
	 * Convert the ASCII value to character
	 * @param ascii ascii value
	 * @return character value
	 */
	public static char ASCIIToChar(final int ascii){
		return (char)ascii;		
	}
}
