package com.rssl.phizic.business.businessProperties;

import com.rssl.common.forms.parsers.SqlTimeParser;
import com.rssl.phizic.common.types.transmiters.Triplet;
import org.apache.commons.lang.time.DateFormatUtils;

import java.sql.Time;
import java.text.ParseException;

/**
 * ������ ��� ������ � ������ � ����������� ������� ������ ������� ��� ��������� ������
 * @author basharin
 * @ created 04.04.14
 * @ $Author$
 * @ $Revision$
 */

public class LoanReceptionTimeHelper
{
	private static final String TIME_FORMAT = "HH:mm";
	public static final String PREFIX_KEY = "com.rssl.business.LoanReceptionTimeProperty.";
	public static final String SEPARATOR = "-";
	private static final SqlTimeParser parser = new SqlTimeParser();

	/**
	 * �������� �������� ��������� ��� ������ � �� �� �� ��������
	 * @param fromTime - ������� ������ ������� ��� ��������� ������ (�� �������)
	 * @param toTime - ������� ������ ������� ��� ��������� ������ (�� �������)
	 * @param timeZone - ��� �������� �����
	 * @return �������� ��������� ��� ������ � ��
	 */
	public static String getValueFromTime(Time fromTime, Time toTime, String timeZone)
	{
		return DateFormatUtils.format(fromTime, TIME_FORMAT) + SEPARATOR + DateFormatUtils.format(toTime, TIME_FORMAT) +SEPARATOR + timeZone;
	}

	/**
	 * �������� ������� ������ ������� ��� ��������� ������
	 * @param value - �������� ���������
	 * @return ������ �� �������� (�� �������, �� �������, ��� �������� �����)
	 * @throws ParseException
	 */
	public static Triplet<Time, Time, String> getTimeFromValue(String value) throws ParseException
	{
		String[] arr = value.split(SEPARATOR);
		return new Triplet<Time, Time, String>(parser.parse(arr[0]), parser.parse(arr[1]), arr[2]);
	}

	/**
	 * ���� ��������� ��� ������ � ��.
	 * @param loanSystem - ��������� �������
	 * @return ���� ��������� ��� ������ � ��.
	 */
	public static String getFullKey(String loanSystem)
	{
		return PREFIX_KEY + loanSystem;
	}

	/**
	 * �������� �������� ��������� �������
	 * @param fullKey - ���� ��������� ��� ������ � ��
	 * @return �������� ��������� �������
	 */
	public static String getNameLoanSystem(String fullKey)
	{
		return fullKey.substring(PREFIX_KEY.length());
	}
}
