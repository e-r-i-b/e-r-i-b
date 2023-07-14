package com.rssl.phizic.web.gate.services.documents.types;

/**
 * @author egorova
 * @ created 28.05.2009
 * @ $Author$
 * @ $Revision$
 */
public class PaymentSystemField
{
	private String name;
	private String code;
	private Object value;
	private boolean mandatory;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}

	public boolean isMandatory()
	{
		return mandatory;
	}

	public void setMandatory(boolean mandatory)
	{
		this.mandatory = mandatory;
	}
}
