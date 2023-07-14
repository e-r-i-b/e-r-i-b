package com.rssl.phizic.common.types.exceptions;

import java.util.List;
import java.util.Collections;

/**
 * ���������� � ������������ ���������� ������� ������
 *
 * @author khudyakov
 * @ created 13.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class CompositeInactiveExternalSystemException extends InactiveExternalSystemException
{
	private List<String> errors;

	public CompositeInactiveExternalSystemException(String message)
	{
		super(message);
	}

	public CompositeInactiveExternalSystemException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public CompositeInactiveExternalSystemException(Throwable cause)
	{
		super(cause);
	}

	public CompositeInactiveExternalSystemException(List<String> errors)
	{
		super("� ������ ������ ������� ������� ���������.");

		this.errors = errors;
	}

	/**
	 * @return ������ ������
	 */
	public List<String> getErrors()
	{
		return Collections.unmodifiableList(errors);
	}
}
