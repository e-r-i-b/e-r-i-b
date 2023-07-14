package com.rssl.phizic.utils;

/**
 * User: Moshenko
 * Date: 24.09.2012
 * Time: 13:12:13
 */
public class CronExpressionUtils
{

	/**
	 * ����� ������ days ����, � time �����.
	 * @param time ����� hh:mm
	 * @param days ����
	 * @return ���������
	 */
	public static String getDayTimeExp(String time, String days)
	{
		return "0 " + time.split(":")[1] + " " + time.split(":")[0] + " 1/" + days + " * ?";
	}

	/**
	 * ������ days ���� ������
	 * @param days ����
	 * @return ���������
	 */
	public static String getDayMonthExp(String days)
	{
		return "1 0 0 "+ days + " * ?";
	}

	/**
	 * �������� ����� ���
	 * @param expression
	 * @return
	 */
	public static String getDayFromExpression(String expression)
	{
		String[] splitExpression = expression.toString().split(" ");
		String dayValue = splitExpression[3];
		if (org.apache.commons.lang.StringUtils.contains(expression,'/'))
			return splitExpression[3].split("/")[1];
		else
			return dayValue;
	}

}
