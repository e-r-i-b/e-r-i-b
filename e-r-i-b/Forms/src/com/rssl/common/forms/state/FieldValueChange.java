package com.rssl.common.forms.state;

/**
 * User: Balovtsev
 * Date: 27.12.2011
 * Time: 11:35:24
 *
 * ������ ��� �������� ��������� �������� ����
 */
public class FieldValueChange
{
	private final String fieldName;
	private final Object oldValue;
	private final Object newValue;
	//�������������� ���������, ������� ����� ������������ � ���������� ����
	private final String fieldMessage;

	public FieldValueChange(String fieldName, Object newValue, Object oldValue, String fieldMessage)
	{
		this.fieldName = fieldName;
		this.oldValue  = oldValue;
		this.newValue  = newValue;
		this.fieldMessage = fieldMessage;
	}

	public String getFieldName()
	{
		return fieldName;
	}

	public Object getOldValue()
	{
		return oldValue;
	}

	public Object getNewValue()
	{
		return newValue;
	}

	public String getFieldMessage()
	{
		return fieldMessage;
	}
}
