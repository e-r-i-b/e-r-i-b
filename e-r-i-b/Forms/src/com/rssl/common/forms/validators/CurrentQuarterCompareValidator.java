package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.DateHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Chegodaev
 * @ created 28.03.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������, ��� �������� ���� (����) ��������� � ���������� �����
 * ���� ������ �������� ��������
 * �
 * ���� ������ �������� �������� + ������������ ������ � ��������� ������������
 *
 */
public class CurrentQuarterCompareValidator extends FieldValidatorBase
{
	private int maxPlanningQuarters;
	private DateFormat format;
	private String messageLess;
	private String messageGreater;

	/**
	 * @param formatString ������ ����
	 */
	public CurrentQuarterCompareValidator(String formatString)
	{
		this.format = new SimpleDateFormat(formatString);
		this.format.setLenient(false);
	}

	private DateFormat getFormat()
	{
		return (DateFormat)format.clone();
	}

	/**
	 * ������������� ������������ ������ ������������
	 * @param maxPlanningQuarters ������������ ������ ������������ � ���������
	 */
	public void setMaxPlanningQuarters(int maxPlanningQuarters)
	{
		this.maxPlanningQuarters = maxPlanningQuarters;
	}

	/**
	 * ������������� ��������� ��� ����������
	 * @param messageLess ���� ������ �����������
	 * @param messageGreater ���� ������ ������������
	 */
	public void setMessages(String messageLess, String messageGreater)
	{
		this.messageLess = messageLess;
		this.messageGreater = messageGreater;
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		if (isValueEmpty(value))
			return true;

		Calendar date = null;
		try
		{
			date = DateHelper.toCalendar(getFormat().parse(value));
			if (date == null)
				return false;
		}
		catch (ParseException ignore)
		{
			return false;
		}

		Calendar first = DateHelper.getStartQuarterDate(DateHelper.getCurrentDate());
		Calendar last = DateHelper.getStartQuarterDate(DateHelper.getCurrentDate());
		DateHelper.addQuarters(last, maxPlanningQuarters);

		if(first.compareTo(date) >= 0)
		{
			setMessage(messageLess);
			return false;
		}

		if(last.compareTo(date) < 0)
		{
			setMessage(messageGreater);
			return false;
		}

		return true;
	}
}
