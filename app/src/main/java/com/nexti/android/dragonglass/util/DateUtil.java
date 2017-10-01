package com.nexti.android.dragonglass.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ISCesar on 01/08/2017.
 */
public class DateUtil {

    /**
     * Método para convertir Fecha de JAVA a
     * DateTime para SQL Formato yyyy-MM-dd HH:mm:ss
     */
    public static String dateToSQLDateTime(Date dateTime){
        if (dateTime==null)
            return null;

        java.text.SimpleDateFormat sdf =  new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(dateTime);
    }

    /**
     * Método para convertir Fecha de JAVA a
     * Time para SQL Formato HH:mm:ss
     */
    public static String timeToSQLDateTime(Date time){
        if (time==null)
            return null;

        java.text.SimpleDateFormat sdf =  new java.text.SimpleDateFormat("HH:mm:ss");

        return sdf.format(time);
    }

    /**
     * Método para expresar en String la fecha indicada
     * La cadena retornada tiene el formato para sentencias SQL: yyyy-MM-dd
     * @return String con la fecha con el formato SQL yyyy-MM-dd
     */
    public static String formatDateToSQL(Date dateTime) {
        if (dateTime==null)
            return null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(dateTime);
    }

    /**
     * Método para expresar en String la fecha y hora actual
     * La cadena retornada tiene el formato: ddMMyy
     * @return String con la fecha y hora con el formato yyyyMMddHHmmssSSS
     */
    public static String getDateHourStringTiny(){
        DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Método para convertir Fecha de JAVA a
     * DateTime para SQL Formato dd-MM-yyyy HH:mm
     */
    public static String dateTimeToHumanReadableString(Date dateTime){
        if (dateTime==null)
            return null;

        java.text.SimpleDateFormat sdf =  new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm");

        return sdf.format(dateTime);
    }

    /**
     * Método para convertir Fecha de JAVA a
     * DateTime para SQL Formato dd-MM-yyyy HH:mm
     */
    public static String dateToHumanReadableString(Date dateTime){
        if (dateTime==null)
            return null;

        java.text.SimpleDateFormat sdf =  new java.text.SimpleDateFormat("dd-MM-yyyy");

        return sdf.format(dateTime);
    }

    /**
     * Recibe un objeto Date y regresa un String con el formato dd 'de' MMMM 'de' yyyy
     * por ejemplo: 12 de Junio de 2009
     * @param date Date a transformar
     * @return Cadena con formato dd 'de' MMMM 'de' yyyy
     */
    public static String dateToStringEspanol(Date date) {
        String fecha = "";
        SimpleDateFormat formateador = new SimpleDateFormat(
                "EEEE ', ' dd 'de' MMMM 'de' yyyy", new Locale("ES"));
        fecha = formateador.format(date);
        return fecha;
    }



    /**
     * Convierte de datos aislados (dia, mes, año) a una cadena con formato "dd/MM/yyyy"
     * @param day Dia del mes
     * @param month Mes del año
     * @param year Año a 4 posiciones
     * @return String
     */
    public static String partDateToString(int day, int month, int year){

        String fecha = "";
        fecha = rellenarEspaciosIzquierda(""+day, "0",2) + "/" + rellenarEspaciosIzquierda(""+month, "0",2) + "/" + rellenarEspaciosIzquierda(""+year, "0",4);

        System.out.println("dia: " + day + " mes: " + month + " año: " +year + " --- Fecha compuesta: " + fecha);

        return fecha;
    }

    private static String rellenarEspaciosIzquierda(String dato, String completarCon, int totalEspacios){
        String resultado = dato;

        //int charCount=0;

        for (int charCount = (dato.length()+1); charCount <= totalEspacios; charCount++){
            resultado =  completarCon + resultado;
        }

        return resultado;
    }

    /**
     * Convierte una cadena en formato Date SQL (yyyy-MM-dd) a Date de JAVA
     * @param dateTimeStr
     * @return
     */
    public static Date parseDateSQL(String dateTimeStr) {
        if (dateTimeStr==null)
            return null;
        if (dateTimeStr.trim().equals(""))
            return null;

        Date parsedDate = null;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            parsedDate = dateFormat.parse(dateTimeStr);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return parsedDate;
    }

    /**
     * Convierte una cadena en formato DateTime SQL (yyyy-MM-dd HH:mm:ss) a Date de JAVA
     * @param dateTimeStr
     * @return
     */
    public static Date parseDateTimeSQL(String dateTimeStr) {
        if (dateTimeStr==null)
            return null;
        if (dateTimeStr.trim().equals(""))
            return null;

        Date parsedDate = null;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            parsedDate = dateFormat.parse(dateTimeStr);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return parsedDate;
    }

    /**
     * Convierte una cadena en formato Time SQL (HH:mm:ss) a Date de JAVA
     * @param timeStr
     */
    public static Date parseTimeSQL(String timeStr) {
        if (timeStr==null)
            return null;
        if (timeStr.trim().equals(""))
            return null;

        Date parsedDate = null;
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            parsedDate = dateFormat.parse(timeStr);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return parsedDate;
    }

    /**
     * Convierte una cadena en formato DateTime SQL (dd-MM-yyyy) a Date de JAVA
     * @param dateTimeStr
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String dateTimeStr) throws ParseException {
        if (dateTimeStr==null)
            return null;
        if (dateTimeStr.trim().equals(""))
            return null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        return dateFormat.parse(dateTimeStr);
    }

    /**
     * Convierte una cadena en formato especificado a Date de JAVA
     * @param dateTimeStr Cadena de tiempo
     * @param format Formato de la cadena
     * @return Date
     * @throws ParseException
     */
    public static Date parseDateGeneric(String dateTimeStr, String format){
        if (dateTimeStr==null)
            return null;
        if (dateTimeStr.trim().equals(""))
            return null;

        Date response = null;
        try{
            Locale spanish = new Locale("es", "ES");
            DateFormat dateFormat = new SimpleDateFormat(format, spanish);
            response = dateFormat.parse(dateTimeStr);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        if (response == null){
            try{
                //Si no se pudo convertir con Locale ES, intentamos con EN
                DateFormat dateFormatEn = new SimpleDateFormat(format, Locale.ENGLISH);
                response = dateFormatEn.parse(dateTimeStr);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

        return response;
    }

    /**
     * Convierte un Date a una cadena con formato especificado
     * @param dateTime Date
     * @param format formato a aplicar para crear Cadena
     * @return String
     */
    public static String formatDateGeneric(Date dateTime, String format){
        if (dateTime==null)
            return null;

        Locale spanish = new Locale("es", "ES");
        DateFormat sdf = new SimpleDateFormat(format, spanish);

        String formatDate = null;

        try {
            formatDate = sdf.format(dateTime);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        if (formatDate==null){
            try {
                //Si no se convirtio, intentamos con otro Locale (en ingles)
                DateFormat sdfEn = new SimpleDateFormat(format, Locale.ENGLISH);
                formatDate = sdfEn.format(dateTime);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

        return formatDate;
    }

    /**
     * Método para expresar en String la fecha y hora actual incluyendo hasta milisegundos
     * La cadena retornada tiene el formato: yyyyMMddHHmmssSSS
     * @return String con la fecha y hora con el formato yyyyMMddHHmmssSSS
     */
    public static String getDateHourString(){
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = new Date();
        return dateFormat.format(date);
    }

    /**
     * Determina si una hora y fecha de un objeto Date estan dentro
     * del día de Hoy
     * @param date Fecha a comparar
     * @return true en caso de ser del día de hoy, false en caso contrario
     */
    public static boolean isDateInToday(Date date, int horaInicio){
        boolean inToday = false;
        Date fechaDesde = new Date();
        Date fechaHasta = new Date();

        try{
            Calendar calDesde = Calendar.getInstance();
            int horaActual = calDesde.get(Calendar.HOUR_OF_DAY);
            //calDesde.setTime(fechaCorte);
            calDesde.set(Calendar.HOUR_OF_DAY, 0);
            calDesde.set(Calendar.MINUTE, 0);
            if(horaInicio > 0){
                calDesde.set(Calendar.HOUR_OF_DAY, horaInicio);
            }


            if(horaActual < horaInicio)
                calDesde.add(Calendar.DAY_OF_YEAR, -1);

            fechaDesde.setTime(calDesde.getTimeInMillis());
        }catch(Exception ex){
            ex.printStackTrace();
        }

        try{
            Calendar calHasta = Calendar.getInstance();
            int horaActual = calHasta.get(Calendar.HOUR_OF_DAY);
            //calHasta.setTime(fechaCorte);
            calHasta.set(Calendar.HOUR_OF_DAY, 23);
            calHasta.set(Calendar.MINUTE, 59);

            if(horaInicio > 0){
                calHasta.set(Calendar.HOUR_OF_DAY, horaInicio - 1);
                if(horaActual > horaInicio)
                    calHasta.add(Calendar.DAY_OF_YEAR, +1);
            }
            fechaHasta.setTime(calHasta.getTimeInMillis());
        }catch(Exception ex){
            ex.printStackTrace();
        }

        if ( date.after(fechaDesde) && date.before(fechaHasta) ){
            inToday = true;
        }

        return inToday;
    }


    /**
     * Determina si una hora y fecha de un objeto Date estan dentro
     * del día de Hoy
     * @return true en caso de ser del día de hoy, false en caso contrario
     */
    public static boolean isDateInDate(Date dateBase, Date dateCompare){
        boolean inDate = false;
        Date fechaDesde = dateBase;
        Date fechaHasta = dateBase;

        try{
            Calendar calDesde = Calendar.getInstance();

            //calDesde.setTime(fechaCorte);
            calDesde.set(Calendar.HOUR_OF_DAY, 0);
            calDesde.set(Calendar.MINUTE, 1);
            fechaDesde.setTime(calDesde.getTimeInMillis());
        }catch(Exception ex){
            ex.printStackTrace();
        }

        try{
            Calendar calHasta = Calendar.getInstance();
            //calHasta.setTime(fechaCorte);
            calHasta.set(Calendar.HOUR_OF_DAY, 23);
            calHasta.set(Calendar.MINUTE, 59);
            fechaHasta.setTime(calHasta.getTimeInMillis());
        }catch(Exception ex){
            ex.printStackTrace();
        }

        Log.d("DateManage", "Fecha Desde: " +fechaDesde  + " , Fecha Hasta: " + fechaHasta);

        if ( dateCompare.after(fechaDesde) && dateCompare.before(fechaHasta) ){
            inDate = true;
        }

        return inDate;
    }

    /**
     * Determina si una hora/minuto de un objeto Date estan dentro
     * de otro minuto
     * @return true en caso de ser del día de hoy, false en caso contrario
     */
    public static boolean isTimeMinuteInTime(Date dateBase, Date dateCompare){
        boolean inTime = false;
        Date fechaDesde = new Date();
        Date fechaHasta = new Date();

        try{
            Calendar calDesde = Calendar.getInstance();
            calDesde.setTime(dateBase);
            calDesde.set(Calendar.SECOND, 0);
            calDesde.add(Calendar.SECOND, -1);//recorremos adicional un segundo atras, por que la rutina AFTER es como un > y no un >=
            fechaDesde.setTime(calDesde.getTimeInMillis());
        }catch(Exception ex){
            ex.printStackTrace();
        }

        try{
            Calendar calHasta = Calendar.getInstance();
            calHasta.setTime(dateBase);
            calHasta.set(Calendar.SECOND, 59);
            calHasta.add(Calendar.SECOND, 1);//recorremos adicional un segundo adelante, por que la rutina BEFORE es como un < y no un <=
            fechaHasta.setTime(calHasta.getTimeInMillis());
        }catch(Exception ex){
            ex.printStackTrace();
        }

        //Log.d("DateManage", "Fecha Desde: " +fechaDesde  + " , Fecha Hasta: " + fechaHasta + ", Comparar: " + dateCompare);

        if ( dateCompare.after(fechaDesde) && dateCompare.before(fechaHasta) ){
            inTime = true;
        }

        return inTime;
    }
}
