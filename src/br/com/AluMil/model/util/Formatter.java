/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.AluMil.model.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author OCTI01
 */
public class Formatter {

    public static String formartarData(String formato, Date data) {
        if (data == null) {
            return "";
        }
        return new SimpleDateFormat(formato).format(data);
    }

    public static String toDate(Date data) {
        return formartarData("dd/MM/yyyy", data);
    }

    public static Date toDate(LocalDate data) {
        if (data == null) {
            return null;
        }
        return Date.from(data.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate toLocalDate(Date data) {
        if (data == null) {
            return null;
        }
        return LocalDate.from(Instant.ofEpochMilli(data.getTime()).atZone(ZoneId.systemDefault()));
    }

    public static String toMonth(Date inicio) {
        return formartarData("MMMM", inicio);
    }
    public static String toHour(Date inicio) {
        return formartarData("HH:mm:ss", inicio);
    }

    public static String toYear(Date data) {
        return formartarData("yyyy", data);
    }
}
