package com.rssl.phizic.business.documents.templates;

/**
 * @author khudyakov
 * @ created 23.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class StringFieldValidationParameter implements FieldValidationParameter
{
	private String value;

	public StringFieldValidationParameter(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}