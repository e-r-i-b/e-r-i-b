package com.rssl.phizic.gate.payments.systems.recipients;

/**
 * @author osminin
 * @ created 30.11.2010
 * @ $Author$
 * @ $Revision$
 *
 * Пара для списочных полей: отображаемое значение и значение для для расчета
 */
public class ListValue
{
	private String value; //отображаемое значение
	private String id;    //значение для расчета

	public ListValue()
	{
	}

	public ListValue(String value, String id)
	{
		this.value = value;
		this.id = id;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
}
