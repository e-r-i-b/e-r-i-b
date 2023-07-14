package com.rssl.phizic.utils;

/**
 * @author Dorzhinov
 * @ created 03.10.2013
 * @ $Author$
 * @ $Revision$
 */
public class BooleanHelper
{
	/**
	 * ���� ��������� ������ ���� Boolean, �� ���������� ���
	 * ���� ������ ���� String, �� ���������� Boolean.valueOf(String)
	 * ����� - Boolean.valueOf(obj.toString())
	 * @param obj
	 * @return
	 */
	public static Boolean asBoolean(Object obj)
	{
		if (obj == null)
			throw new IllegalArgumentException("�������� obj �� ����� ���� null");

		if (obj instanceof Boolean)
			return (Boolean) obj;
		else if (obj instanceof String)
			return Boolean.valueOf((String) obj);
		else
			return Boolean.valueOf(obj.toString());

	}

	/**
	 * �������������� Boolean � �������� �����(� ������ null ��������� ��������)
	 * @param value ������ Boolean
	 * @param defaultValue ��������� ��������
	 * @return ��������
	 */
	public static boolean getNullBooleanValue(Boolean value, boolean defaultValue)
	{
		if (value == null)
		{
			return defaultValue;
		}
		return value;
	}
}
