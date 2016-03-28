/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ipx.util;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


/**Clase -helper- para manejo de fechas.
 *
 * @author christian salazar, christiansalazarh@gmail.com
 * @Mejorado by David Torrez dtorrez@ipxserver.com
 */
public class DateUtil {




    /* entrega la fecha actual:  aaaa/mm/dd, ejemplo: 2011/04/14 */

    public static String getCurrentDate()
    {
        Date d = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.setTime(d);


        int imes = (cal.get(Calendar.MONTH)+1);
        String mes = imes+"";
        if(imes < 10) mes="0"+imes;


        int idia = cal.get(Calendar.DAY_OF_MONTH);
        String dia = idia+"";
        if(idia < 10)
            dia="0"+idia;
        
        return cal.get(Calendar.YEAR)+"/"+mes
                +"/"+dia;
    }


    public static int getYear()
    {
        Date d = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.setTime(d);
        return cal.get(Calendar.YEAR);
    }
    public static int getMonth()
    {
        Date d = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.setTime(d);
        return 1+cal.get(Calendar.MONTH);
    }
    public static int getDay()
    {
        Date d = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.setTime(d);
        return cal.get(Calendar.DAY_OF_MONTH);
    }
    public static int getHour()
    {
        Date d = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.setTime(d);
        return cal.get(Calendar.HOUR);
    }
    public static int getMinute()
    {
        Date d = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.setTime(d);
        return cal.get(Calendar.MINUTE);
    }
    public static int getSecond()
    {
        Date d = new Date();
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        cal.setTime(d);
        return cal.get(Calendar.SECOND);
    }
    public static String getHora()
    {
        String hora = DateUtil.getHour()+":"+DateUtil.getMinute()+":"+DateUtil.getSecond();
        return hora;
    }
}
