package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Kidyaev
 * @ created 14.12.2005
 * @ $Author: krenev_a $
 * @ $Revision: 36164 $
 * @noinspection MagicNumber
 */
public class DateFieldValidator extends FieldValidatorBase
{
	private static final String PARAMETER_FORMAT = "format";

	protected Date minDate = new GregorianCalendar(1753, 1, 1).getTime();
	protected Date maxDate = new GregorianCalendar(9999, 12, 31).getTime();

	public void setMinDate(Date minDate)
	{
		this.minDate = minDate;
	}

	public void setMaxDate(Date maxDate)
	{
		this.maxDate = maxDate;
	}

	private DateFormat format;

    private DateFormat getFormat()
    {
		return (DateFormat)format.clone();    
    }

	/**
	 * Формат ДД.ММ.ГГГГ
	 */
	public DateFieldValidator()
	{
		format = DateFormat.getDateInstance(DateFormat.SHORT);
		format.setLenient(false);
	}

	/**
	 *  @param  formatString формат даты\времени
	 */
	public DateFieldValidator(String formatString)
	{
		this.format = new SimpleDateFormat(formatString);
		this.format.setLenient(false);
	}

	/**
	 * @param formatString формат даты\времени
	 * @param message сообщение
	 */
	public DateFieldValidator(String formatString, String message)
	{
		this(formatString);
		setMessage(message);
	}

	public void setParameter(String name, String value)
	{
		if ( name.equalsIgnoreCase(PARAMETER_FORMAT) ) {
			format = new SimpleDateFormat(value);
			format.setLenient(false);
		}
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		return validate(value,minDate,maxDate);
	}

	protected boolean validate(String value, Date minDate, Date maxDate) throws TemporalDocumentException
	{

		if(isValueEmpty(value))
		    return true;

		Date date = null;
		try
		{
			date = getFormat().parse(value);
			if (date == null)
				return false;
		}
		catch (ParseException e)
		{
			return false;
		}

		if(date.before(minDate) || date.after(maxDate))
			return false;

		return true;
	}
}
