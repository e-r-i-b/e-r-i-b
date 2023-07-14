package com.rssl.phizic.business;

import java.util.Collections;
import java.util.Map;

/**
 * ������, ��������� �������� ������������, ����� ������ ���� ����� �������� �� ����������� �� ���� ��������
 * ���� ��������� ����������� � ������� ��������������.
 @author Pankin
 @ created 04.03.2011
 @ $Author$
 @ $Revision$
 */
public class IndicateFormFieldsException extends BusinessLogicException
{
	private final Map<String, String> fieldMessages;
	private final boolean isError;

	public IndicateFormFieldsException(Map<String, String> fieldMessages, boolean isError, Throwable cause)
	{
		super(cause.getMessage(), cause);
		this.isError = isError;
		this.fieldMessages = fieldMessages;
	}

	/**
	 * @return ����� ���� �����, �� ������� ����� �������� �������� ������������, � ��������� ��� �����
	 */
	public Map<String, String> getFieldMessages()
	{
		return Collections.unmodifiableMap(fieldMessages);
	}

	/**
	 * @return ����� �� ���������� ��������� � ������
	 */
	public boolean isError()
	{
		return isError;
	}
}
