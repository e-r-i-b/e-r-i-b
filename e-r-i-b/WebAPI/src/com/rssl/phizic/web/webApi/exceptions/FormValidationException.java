package com.rssl.phizic.web.webApi.exceptions;

import com.rssl.phizic.common.types.exceptions.LogicException;

import java.util.Map;

/**
 * Ошибка валидации формы
 * @author Jatsky
 * @ created 15.05.14
 * @ $Author$
 * @ $Revision$
 */

public class FormValidationException extends LogicException
{
	private String elementId;
	private Map<String, String> errors;

	public FormValidationException(String message, String elementId)
	{
		super(message);
		this.elementId = elementId;
	}

	public String getElementId()
	{
		return elementId;
	}

	public FormValidationException(Map<String, String> errors)
	{
		this.errors = errors;
	}

	public Map<String, String> getErrors()
	{
		return errors;
	}
}
