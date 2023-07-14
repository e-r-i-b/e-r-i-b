package com.rssl.phizic.utils.jaxb;

import com.rssl.phizic.utils.xml.XMLDatatypeHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @author Erkin
 * @ created 13.02.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����������� JAXB-������� ��� ���� � ���� ���������
 */
public abstract class AbstractCalendarAdapter extends XmlAdapter<String, Calendar>
{
	public Calendar unmarshal(String dateAsString)
	{
		SimpleDateFormat dateFormat = getDateFormat();
		try
		{
			return XMLDatatypeHelper.parseDateTime(dateAsString);
		}
		catch (RuntimeException e)
		{
			throw new IllegalArgumentException("�� ������� ��������� ������: " + dateAsString + " �� ������� " + dateFormat.toPattern(), e);
		}
	}

	public String marshal(Calendar dateAsCalendar)
	{
		SimpleDateFormat dateFormat = getDateFormat();
		return dateFormat.format(dateAsCalendar.getTime());
	}

	protected abstract SimpleDateFormat getDateFormat();
}
