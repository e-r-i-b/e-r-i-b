package com.rssl.common.forms.doc;

import com.rssl.common.forms.state.FieldValueChange;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.utils.StringHelper;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Balovtsev
 * Date: 27.12.2011
 * Time: 11:33:56
 */
public class StateMachineEvent
{
	private final Map<String, FieldValueChange> fieldValueChanges = new HashMap<String, FieldValueChange>();
	private MessageCollector messageCollector = new MessageCollector();
	private String nextState = null; //��������� ��������� ��������� �������, ����� ���������� ���������
	private String errorCode;       //��� ������, ��������� � ��������

/**
	 *
	 * ����������� ��������� �������� ���� � �������� � ����� ����.
	 *
	 * @param fieldName - �������� ����
	 * @param newValue - ����� ��������
	 * @param oldValue - ������ ��������
	 * @param fieldMessage - ���������, ������� ����� ������������ � ����
	 */
	public void registerChangedField(String fieldName, Object newValue, Object oldValue, String fieldMessage)
	{
		if (StringHelper.isEmpty(fieldName))
		{
			throw new NullPointerException("���� fieldName ������ ���� ������");
		}

		fieldValueChanges.put(fieldName, new FieldValueChange(fieldName, newValue, oldValue, fieldMessage));
	}

	/**
	 *
	 * ����������� ��������� �������� ����.
	 *
	 * @param fieldName ��� ����
	 * @param newValue  ������ ��������
	 * @param oldValue  ����� ��������
	 */
	public void registerChangedField(String fieldName, Object newValue, Object oldValue)
	{
		registerChangedField(fieldName, newValue, oldValue, null);
	}

	/**
	 *
	 * ����������� ��������� �������� ����.
	 *
	 * @param fieldName ��� ����
	 */
	public void registerChangedField(String fieldName)
	{
		registerChangedField(fieldName, null, null, null);
	}

	/**
	 *
	 * �������� �� ������� ���������� �����
	 *
	 * @return boolean
	 */
	public boolean hasChangedFields()
	{
		return !fieldValueChanges.isEmpty();
	}

	/**
	 *
	 * ���������� FieldValueChange �� �������� ����.
	 *
	 * @param fieldName �������� ����
	 * @return FieldValueChange
	 */
	public FieldValueChange getChangedField(String fieldName)
	{
		if (StringHelper.isEmpty(fieldName))
		{
			throw new NullPointerException("���� fieldName ������ ���� ������");
		}

		return fieldValueChanges.get(fieldName);
	}

	/**
	 *
	 * ���������, ���� �� ��������� ���� � ��������� fieldName
	 *
	 * @param fieldName �������� ����
	 * @return boolean
	 */
	public boolean hasChangedField(String fieldName)
	{
		return fieldValueChanges.containsKey(fieldName);
	}

	/**
	 *
	 * �������� ��������� ������� ���������� ���������� ������������
	 *
	 * @param messageCollector ������ �������������� ��������� � ��������� �� �������
	 */
	public void setMessageCollector(MessageCollector messageCollector)
	{
		this.messageCollector = messageCollector;
	}

	/**
	 *
	 * ���������� ������ �������� �������������� ��������� � ��������� �� �������
	 *
	 * @return MessageCollector
	 */
	public MessageCollector getMessageCollector()
	{
		return messageCollector;
	}

	/**
	 *
	 * �������� �������������� ���������.
	 * ���� ��������� ������, �� ��� �� �����������.
	 *
	 * @param message ���������
	 */
	public void addMessage(String message)
	{
		if (StringHelper.isEmpty(message))
		{
			return;
		}

		messageCollector.addMessage(message);
	}

	/**
	 * �������� ��������� �� ������
	 * @param errorMsg - ��������� �� ������
	 */
	public void addErrorMessage(String errorMsg)
	{
		if (StringHelper.isEmpty(errorMsg))
		{
			return;
		}

		messageCollector.addError(errorMsg);
	}

	/**
	 *
	 * ���������� ����� �������� FieldValueChange ���������� ������������ ���� � �� ��������
	 *
	 * @return Collection&lt;FieldValueChange&gt;
	 */
	public Collection<FieldValueChange> getChangedFields()
	{
		return fieldValueChanges.values();
	}

    /**
     * ������� ������������������ ������������ ����
     */
    public void clearChangedFields()
    {
        fieldValueChanges.clear();
    }

	public String getNextState()
	{
		return nextState;
	}

	public void setNextState(String nextState)
	{
		this.nextState = nextState;
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}
}
