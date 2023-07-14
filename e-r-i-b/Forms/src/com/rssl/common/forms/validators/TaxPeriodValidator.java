package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;

import java.util.*;
import java.text.DateFormat;
import java.text.ParseException;

/**
 * @author gladishev
 * @ created 24.05.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ������������ ���������� ���������� �������
 */
public class TaxPeriodValidator extends MultiFieldsValidatorBase
{
	protected static final int minYear = 1753;
	protected static final int maxYear = 9999;
	private static final Date minDate = new GregorianCalendar(minYear, 1, 1).getTime();
	private static final Date maxDate = new GregorianCalendar(maxYear, 12, 31).getTime();
	public static final String FIELD_DAY = "day";
	public static final String FIELD_MONTH = "month";
	public static final String FIELD_YEAR = "year";
	public static final String FIELD_GROUND = "ground";

	public boolean validate(Map values) throws TemporalDocumentException
	{

		String day = (String) retrieveFieldValue(FIELD_DAY, values);
		String month = (String) retrieveFieldValue(FIELD_MONTH, values);
		String year = (String) retrieveFieldValue(FIELD_YEAR, values);
		String ground = (String) retrieveFieldValue(FIELD_GROUND, values);

		//���� ��������� ������� �� ��� ��, �� � ���� ������� ������������� ����
		if (ground.equals("��") || ground.equals("��"))
		{
			if (!day.equals("00") || !month.equals("00") || !year.equals("0000"))
			{
				setMessage("��� ��������� ������� �� ��� �� ���� ���������� ������� ������ ���� ��������� ������");
				return false;
			}
			else return true;
		}
		else
		{
			if ((day.equals("0") && month.equals("") && year.equals("")) || ((day.equals("") && month.equals("") && year.equals(""))))
				return true;
			else
			{
				setMessage("��������� ������������ ���������� �������");
				if (!checkBorder(year, minYear, maxYear))
					return false;
				if (day.equals("�1") || day.equals("�2") || day.equals("�3") || day.equals("��"))
				{
					setMessage("�� ������ ���� ������� ������� ����� ������");
					return checkBorder(month, 1, 12);
				}
				else if (day.equals("��"))
				{
					setMessage("�� ������ ���� ������� ������� ����� ��������");
					return checkBorder(month, 1, 4);
				}
				else if (day.equals("��"))
				{
					setMessage("�� ������ ���� ������� ������� ����� ���������");
					return checkBorder(month, 1, 2);
				}
				else if (day.equals("��"))
				{
					setMessage("��� ������� ������� ��������� ������� ���� ������� ������ ���� \\u002200\\u0022");
					return checkBorder(month, 0, 0);
				}
				else
				{
					setMessage("����������� ������� ���� ���������� �������. ������� ���� � ������� ��.��.����");
					return checkDate(day + "." + month + "." + year);
				}
			}
		}
	}

	/**
	 *�������� �������������� ��������� [borderL, borderR] �����, ���������� � ������ str 
	 */
	protected boolean checkBorder(String str, int borderL, int borderR)
	{
		try
		{
			int value = Integer.parseInt(str);
			if (borderL <= value && value <= borderR)
				return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
		return false;
	}

	/**
	 * �������� ������������ ��������� ����
	 */
	private boolean checkDate(String value)
	{
		DateFormat format;
		format = DateFormat.getDateInstance(DateFormat.SHORT);
		format.setLenient(false);

		Date date = null;
		try
		{
			date = format.parse(value);
		}
		catch (ParseException e)
		{
			return false;
		}

		if (date.before(minDate) || date.after(maxDate))
		{
			return false;
		}
		return true;
	}
}
