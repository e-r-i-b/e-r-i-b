package com.rssl.phizic.utils;

import java.util.Calendar;

/**
 * @author Omeliyanchuk
 * @ created 27.06.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Используется для тестов BeanHelper
 */

public class BigBean
{
	private Bean1 bean;
	private Bean2 bean2;
	int value;
	String strValue;
	Calendar calendarValue;

	public void setBean2(Bean2 bean2)
	{
		this.bean2 = bean2;
	}

	public void setBean(Bean1 bean)
	{
		this.bean = bean;
	}

	public void setCalendarValue(Calendar calendarValue)
	{
		this.calendarValue = calendarValue;
	}

	public void setStrValue(String strValue)
	{
		this.strValue = strValue;
	}

	public void setValue(int value)
	{
		this.value = value;
	}

	public Bean2 getBean2()
	{
		return bean2;
	}

	public Bean1 getBean()
	{
		return bean;
	}

	public Calendar getCalendarValue()
	{
		return calendarValue;
	}

	public String getStrValue()
	{
		return strValue;
	}

	public int getValue()
	{
		return value;
	}
}
