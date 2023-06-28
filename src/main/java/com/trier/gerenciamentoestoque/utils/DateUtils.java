package com.trier.gerenciamentoestoque.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static DateTimeFormatter dtfBR = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    
    private static DateTimeFormatter dtfBR2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
    public static ZonedDateTime strToZonedDateTime(String dateStr) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, dtfBR);
        return localDateTime.atZone(ZoneId.systemDefault());
    }
    
    public static ZonedDateTime strToZonedDate(String dateStr) {
        LocalDate localDate = LocalDate.parse(dateStr, dtfBR2);
        return ZonedDateTime.of(localDate, LocalTime.MIDNIGHT, ZoneId.systemDefault());
    }
	
	public static String zonedDateTimeToStr(ZonedDateTime date) {
		return dtfBR.format(date);
	}
	
	public static LocalDate strToLocalDate(String dateStr) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateStr, dtfBR);
        return localDateTime.toLocalDate();
    }
}