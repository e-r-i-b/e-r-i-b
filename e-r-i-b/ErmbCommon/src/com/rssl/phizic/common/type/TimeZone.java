package com.rssl.phizic.common.type;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * @author EgorovaA
 * @ created 14.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class TimeZone
{
	private final static  List<TimeZone> LIST = new ArrayList<TimeZone>();

	public final static TimeZone KALININGRAD = createDefaultTimeZone(120, "(GMT+02:00)�����������");
	public final static TimeZone MOSCOW = createDefaultTimeZone(180, "(GMT+03:00)������");
	public final static TimeZone SAMARA = createDefaultTimeZone(240, "(GMT+04:00)������");
	public final static TimeZone EKATERINBURG = createDefaultTimeZone(300, "(GMT+05:00)������������");
	public final static TimeZone NOVOSIBIRSK = createDefaultTimeZone(360, "(GMT+06:00)�����������");
	public final static TimeZone KRASNOYARSK = createDefaultTimeZone(420, "(GMT+07:00)����������");
	public final static TimeZone IRKUTSK = createDefaultTimeZone(480, "(GMT+08:00)�������");
	public final static TimeZone YAKUTSK = createDefaultTimeZone(540, "(GMT+09:00)������");
	public final static TimeZone VLADIVOSTOK = createDefaultTimeZone(600, "(GMT+10:00)�����������");
	public final static TimeZone MAGADAN = createDefaultTimeZone(660, "(GMT+11:00)�������");
	public final static TimeZone KAMCHATKA = createDefaultTimeZone(720, "(GMT+12:00)��������");

	private final long code;
	private final String text;

	private TimeZone(long code, String text)
	{
		this.code = code;
		this.text = text;
	}

	/**
	 * ������� ������ ����������� ������� ������
	 * @param code - ���
	 * @param text - ��������
	 * @return
	 */
	private static TimeZone createDefaultTimeZone(long code, String text)
	{
		TimeZone timeZone = new TimeZone(code, text);
		LIST.add(timeZone);
		return timeZone;
	}

	/**
	 * ����� ���������� ������ ����������� ������� ������
	 * @return
	 */
	public static List<TimeZone> getKnownTimeZones()
	{
		return Collections.unmodifiableList(LIST);
	}

	/**
	 * ��� (�������� � ������� ������������ GMT)
	 * @return
	 */
	public long getCode()
	{
		return code;
	}

	/**
	 * �������� �������� ����� � ��������� ������� ����� � ������� GMT�hh:mm � ���������
	 * @return
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * �������� ������� ���� �� ����
	 * @param code - ���
	 * @return �����������(���� ���� �������� � ����� �����) ��� ��������� ������� ����
	 */
	public static TimeZone getTimeZoneByCode(long code)
	{
		TimeZone tz = getKnownTimeZone(code);
		if (tz == null)
			return createCustomTimeZone(code);
		return tz;
	}

	/**
	 * �������� ����������� ������� ���� �� ����
	 * @param code - ���
	 * @return ������� ���� (���� ���� � ����������� �������� � ����� �����) ��� null
	 */
	public static TimeZone getKnownTimeZone(long code)
	{
		for (TimeZone timeZone: getKnownTimeZones())
		{
			if (timeZone.getCode() == code)
				return timeZone;
		}
		return null;
	}

	/**
	 * ������� ��������� ������� ���� �� ����
	 * @param code - ���
	 * @return  ������� ���� � ���������� ����� � ��������������� ���������
	 */
	public static TimeZone createCustomTimeZone(long code)
	{
		String newTimeZoneText = "("+ TimeZone.getFormattedTimeZone(code)+")������";
		return new TimeZone(code, newTimeZoneText);
	}

	/**
	 * ������� ���� � ������� GMT�hh:mm
	 * @param timeZone ������� ���� � �������
	 * @return ������ � ������� ������ � ������� GMT�hh:mm
	 */
	public static String getFormattedTimeZone(long timeZone)
	{
		if (timeZone == 0)
			return "GMT";
		return String.format("GMT%+03d:%02d", timeZone/60, timeZone%60);
	}

	/**
	 * ������������ ������ ������� ������, ��������� �������. ���� ������� ���� ������� �� ���������
	 * �� � ����� �� "������������" ������, ��������� ����� �������
	 * @param code - ������� ���� (������ � �������), ��������� � ����-������� �������
	 * @return ������ ������� ������
	 */
	public static List<TimeZone> getTimeZoneList(long code)
	{
		if (getKnownTimeZone(code) != null)
			return getKnownTimeZones();
		//��� ���������� �� � ����� �� �������� "������������" ������
		List<TimeZone> timeZoneList = new ArrayList<TimeZone>();
		timeZoneList.addAll(TimeZone.getKnownTimeZones());
		timeZoneList.add(0, createCustomTimeZone(code));
		return timeZoneList;

	}
}
