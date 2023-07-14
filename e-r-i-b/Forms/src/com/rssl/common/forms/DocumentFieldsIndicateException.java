package com.rssl.common.forms;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * ������, ��������� �������� ������������, ����� ������ ���� ����� �������� �� ����������� �� ���� ��������
 * ���� ��������� ����������� � ������� ��������������.
 @author Pankin
 @ created 04.03.2011
 @ $Author$
 @ $Revision$
 */
public class DocumentFieldsIndicateException extends DocumentLogicException
{
	private final Map<String, String> fieldMessages;
	private final boolean isError;

	public DocumentFieldsIndicateException(Map<String, String> fieldMessages, boolean isError, String message)
	{
		super(message);
		this.isError = isError;
		this.fieldMessages = fieldMessages;
	}

	public DocumentFieldsIndicateException(Set<String> fieldNames, boolean isError, String message)
	{
		super(message);
		this.isError = isError;
		fieldMessages = new HashMap<String, String>();
		for (String fieldName : fieldNames)
		{
			fieldMessages.put(fieldName, "");
		}
	}

	public DocumentFieldsIndicateException(String fieldName, boolean isError, String message)
	{
		super(message);
		this.isError = isError;
		fieldMessages = new HashMap<String, String>();
		fieldMessages.put(fieldName, isError ? message : "");
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
