package br.com.leandro.prova_a3.infrastructure;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateHelper {

    public static String format(Date input){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("pt", "BR"));
        // setar um timezone no SimpleDateFormat, para não depender do default
        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

        return sdf.format(input);
    }
}
