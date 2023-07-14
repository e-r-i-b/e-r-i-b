package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.DateHelper;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Map;

/**
 * @author Gololobov
 * @ created 30.03.2012
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ���������� ����� ������� � ����������� �� ���� �������.
 */

public class DateTimePeriodMultiFieldValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_DATE_FROM = "date_from"; //���� ������ �������
	public static final String FIELD_TIME_FROM = "time_from"; //����� ������ �������
	public static final String FIELD_DATE_TO = "date_to";     //���� ��������� �������
	public static final String FIELD_TIME_TO = "time_to";     //����� ��������� �������
	public static final String FIELD_PERIOD_TYPE = "type_period"; //��� �������
	public static final String YAER_TYPE_PERIOD = "year"; //��� ������� - ���
	public static final String MONTH_TYPE_PERIOD = "month"; //��� ������� - �����
	public static final String DAY_TYPE_PERIOD = "day";     //��� ������� - ����
	public static final String HOUR_TYPE_PERIOD = "hour";   //��� ������� - ���

	private static final Long ONE_DAY_MILLIS = 1000L*3600*24; //����������� � ���
	private static final Long ONE_HOUR_MILLIS = 1000L*3600;   //����������� � ����

	//��� ������� (���, ����, �����)
	private String typePeriod;
	private int lengthPeriod = 1;//����������������� �������(��������, 10 ����)

	public String getTypePeriod()
	{
		return typePeriod;
	}

	public void setTypePeriod(String typePeriod)
	{
		this.typePeriod = typePeriod;
	}

	public int getLengthPeriod()
	{
		return lengthPeriod;
	}

	public void setLengthPeriod(int lengthPeriod)
	{
		this.lengthPeriod = lengthPeriod;
	}

	/*
		 * ���� ��� ������� ���, �� ������ ������ ���� �� ����� ����.
		 * ���� ��� ������� ���� - �� ����� ���.
		 * ���� ����� - �� ����� ������.
		 */
	public boolean validate(Map values) throws TemporalDocumentException
	{
		try
		{
			Calendar dateTimeFrom = DateHelper.createCalendar(retrieveFieldValue(FIELD_DATE_FROM, values),retrieveFieldValue(FIELD_TIME_FROM, values));
			Calendar dateTimeTo = DateHelper.createCalendar(retrieveFieldValue(FIELD_DATE_TO, values),retrieveFieldValue(FIELD_TIME_TO, values));
			if (typePeriod.equals(MONTH_TYPE_PERIOD))
				dateTimeFrom.add(Calendar.MONTH, lengthPeriod);
			if (typePeriod.equals(YAER_TYPE_PERIOD))
				dateTimeFrom.add(Calendar.YEAR, lengthPeriod);

			return (typePeriod.equals(HOUR_TYPE_PERIOD) && DateHelper.diff(dateTimeTo,dateTimeFrom) <= ONE_HOUR_MILLIS) ||
				   (typePeriod.equals(DAY_TYPE_PERIOD) && DateHelper.diff(dateTimeTo,dateTimeFrom) <= lengthPeriod*ONE_DAY_MILLIS) ||
				   (typePeriod.equals(MONTH_TYPE_PERIOD) && dateTimeTo.compareTo(dateTimeFrom) <= 0)||
				   (typePeriod.equals(YAER_TYPE_PERIOD) && dateTimeTo.compareTo(dateTimeFrom) <= 0);
		}
		catch (ParseException ignored)
		{
			return false;
		}
	}
}
