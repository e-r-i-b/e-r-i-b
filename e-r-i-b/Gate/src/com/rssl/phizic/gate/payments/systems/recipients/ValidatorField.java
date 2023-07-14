package com.rssl.phizic.gate.payments.systems.recipients;

/**
 * Поле с валидатором
 * @author Jatsky
 * @ created 26.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class ValidatorField
{
	private String type;       
	private String message;
	private String parameter;

	public ValidatorField(String type, String message, String parameter)
	{
		this.type = type;
		this.message = message;
		this.parameter = parameter;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getParameter()
	{
		return parameter;
	}

	public void setParameter(String parameter)
	{
		this.parameter = parameter;
	}
}
