package com.rssl.phizic.business.fields;

/**
 * @author hudyakov
 * @ created 14.01.2010
 * @ $Author$
 * @ $Revision$
 */

public class StringFieldValidationParameter extends FieldValidationParameterBase
{
	private String value;

	public StringFieldValidationParameter()
	{
		super();
	}

	public StringFieldValidationParameter(String value)
	{
		super();
		this.value = value;
	}

	public StringFieldValidationParameter(StringFieldValidationParameter value)
	{
		super();
		this.value = value.getValue();
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
