package br.com.trier.spring.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
	
	private static DateTimeFormatter dtfBR = DateTimeFormatter.ofPattern("dd/MM/yyy");
	
	public static ZonedDateTime strToZonedDateTime(String dateStr) {
		LocalDate local = LocalDate.parse(dateStr, dtfBR);
		return local.atStartOfDay(ZoneId.systemDefault());		
	}
	
	public static String ZonedDateTimeToStr(ZonedDateTime date) {
		return dtfBR.format(date);
	}

}
