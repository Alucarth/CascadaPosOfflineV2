package com.sagereal.utils;

import com.ipx.util.Log;
import de.enough.polish.util.StringTokenizer;
import java.util.Calendar;


import net.sf.microlog.core.Logger;
import net.sf.microlog.core.LoggerFactory;

/**
 * a class to process date&time to be a string like. (21 Jun 2012 9:36am) {@link #DateString()} or (9:36 am 21/06/2012)
 * {@link #DateString1()}
 * 
 * @author Zheng Zhida (zhida.zheng@nbbse.com)
 * 
 */

public final class DateUtil {

	/**
	 * Month array.
	 */
	private static String[] monthArray = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct",
			"Nov", "Dec" };

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * Default constructor.
	 */
	private DateUtil() {
		super();
	}

	/**
	 * Date to string function.
	 * 
	 * @return a date in String format.
	 */
	public static String dateToString() {
		Calendar calendar = Calendar.getInstance();
		LOGGER.info(calendar.getTime());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);
		// int weekDay = calendar.get((Calendar.DAY_OF_WEEK));
		int amPm = calendar.get(Calendar.AM_PM);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		// int second = calendar.get(Calendar.SECOND);

		StringBuffer dateStr = new StringBuffer();
		if (1 <= day & day < 10) {
			dateStr.append("0");
		}
		dateStr.append(day).append("  ").append(monthArray[month]).append("  ").append(year).append("    ")
				.append(hour).append(":");
		if (minute < 10) {
			dateStr.append("0");
		}
		dateStr.append(minute);
		if (amPm == Calendar.AM) {
			dateStr.append("am");
		} else {
			dateStr.append("pm");
		}

		return dateStr.toString();
	}

	/**
	 * Date to String function 2.
	 * 
	 * @return a date in string format
	 */
	public static String dateToString1() {
		Calendar calendar = Calendar.getInstance();
		LOGGER.info(calendar.getTime());
//		int year = calendar.get(Calendar.YEAR);
//		int month = calendar.get(Calendar.MONTH);
//		int day = calendar.get(Calendar.DATE);
		// int weekDay = calendar.get((Calendar.DAY_OF_WEEK));
		int amPm = calendar.get(Calendar.AM_PM);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		// int second = calendar.get(Calendar.SECOND);

		StringBuffer dateStr = new StringBuffer();
		if (hour < 10) {
			dateStr.append("0");
		}
		dateStr.append(hour).append(":");
		if (minute < 10) {
			dateStr.append("0");
		}
		dateStr.append(minute).append("  ");
		if (amPm == Calendar.AM) {
			dateStr.append("am");
		} else {
			dateStr.append("pm");
		}
//		dateStr.append("   ").append(day).append("/");
//		if (month < 10) {
//			dateStr.append("0");
//		}
//		dateStr.append(month + 1).append("/").append(year);

		return dateStr.toString();
	}
        //Ipx function by Ing.David Torrez Salinas
        
        public static boolean VerificarFecha(String FechaEmision)
        {
            boolean disponible=false;
            Log.i("Fecha",""+FechaEmision);
             StringTokenizer token = new StringTokenizer(FechaEmision,"-");
             
             Calendar c1 = Calendar.getInstance();
             Calendar fa = Calendar.getInstance();
             c1.set(Calendar.YEAR, Integer.parseInt(token.nextToken()));
             c1.set(Calendar.MONTH, Integer.parseInt(token.nextToken())-1);//eso es por el mes esta seteado 0 a 11 
         
             c1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(token.nextToken()));
             
             
             int dia = c1.get(Calendar.DAY_OF_MONTH);
             int mes = c1.get(Calendar.MONTH);
             int anio = c1.get(Calendar.YEAR);
             
             Log.i("Fecha seteada", dia+"/"+mes+"/"+anio);
             Log.i("Fecha seteada", ""+c1.getTime());
             Log.i("Fecha actual", ""+fa.getTime());
             
              if (c1.before(fa)) {
                  
                Log.i("la fecha  c1 menor a ",""+fa.getTime());
                   disponible=true;
               } else if (c1.after(fa)) {
                   Log.i("la fecha c1  mayor",""+fa.getTime());
                   disponible =false;
               } else if (c1.equals(fa)) {
                   Log.i("la fecha c1  igual",""+fa.getTime());
                   disponible =false;
//                System.out.println(?is equal to ? + fm.format(c2.getTime()));
               }
         
          return disponible;
        }
        public static boolean VerificarFechaAlerta(String FechaEmision)
        {
            boolean disponible=false;
            Log.i("Fecha",""+FechaEmision);
             StringTokenizer token = new StringTokenizer(FechaEmision,"-");
             
             Calendar c1 = Calendar.getInstance();
             Calendar fa = Calendar.getInstance();
             c1.set(Calendar.YEAR, Integer.parseInt(token.nextToken()));
                
             c1.set(Calendar.MONTH, Integer.parseInt(token.nextToken())-1);//eso es por el mes esta seteado 0 a 11 
         
             c1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(token.nextToken()));
             
             
             int dia = c1.get(Calendar.DAY_OF_MONTH);
             int mes = c1.get(Calendar.MONTH);
             int anio = c1.get(Calendar.YEAR);
             
             Log.i("Fecha seteada", dia+"/"+mes+"/"+anio);
             Log.i("Fecha seteada", ""+c1.getTime());
             Log.i("Fecha actual", ""+fa.getTime());
             
               if (c1.equals(fa)) {
                   Log.i("la fecha c1  igual",""+fa.getTime());
                   disponible =true;
//                System.out.println(?is equal to ? + fm.format(c2.getTime()));
               }
               else
               {
                   disponible =false;
               }
         
          return disponible;
        }
}
