package fr.amu.directorymanage.controller;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BirthdayEditor extends DateFormat {

	private static final List<? extends DateFormat> 
	DATE_FORMATS = Arrays.asList(new SimpleDateFormat("yyyy-mm-dd"),
			new SimpleDateFormat("dd/mm/yyyy"));

	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo, 
			FieldPosition fieldPosition) {
		throw new UnsupportedOperationException
		("This custom date formatter can only be used to *parse* Dates.");
	}

	@Override
	public Date parse(String source, ParsePosition pos) {
		Date res = null;
		for (final DateFormat dateFormat : DATE_FORMATS) {
			if ((res = dateFormat.parse(source, pos)) != null) {
				return res;
			}
		}

		return null;
	}

}
