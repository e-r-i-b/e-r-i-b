package com.rssl.phizic.common.types;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Collections;
import java.io.Serializable;

/**
 * @author Erkin
 * @ created 27.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ���������: ����������� � ������
 */
public final class MessageCollector implements Serializable
{
	private final Collection<String> messages = new LinkedHashSet<String>();

	private final Collection<String> errors = new LinkedHashSet<String>();

	private final Collection<String> inactiveSystemErrors = new LinkedHashSet<String>();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ���������� ��������� �����������
	 * @return ��������� ��������� ���������-����������� (never null)
	 */
	public Collection<String> getMessages()
	{
		return Collections.unmodifiableCollection(messages);
	}

	/**
	 * ���������� ��������� ������
	 * @return ��������� ��������� ���������-������ (never null)
	 */
	public Collection<String> getErrors()
	{
		return Collections.unmodifiableCollection(errors);
	}

	/**
	 * ���������� ��������� ������ � ������������� ������� ������.
	 * @return ��������� ��������� ���������-������ (never null)
	 */
	public Collection<String> getInactiveErrors()
	{
		return Collections.unmodifiableCollection(inactiveSystemErrors);
	}

	/**
	 * ��������� ���� �����������
	 * @param message - ����� ���������-�����������
	 */
	public void addMessage(String message)
	{
		if (message == null || message.length() == 0)
			throw new IllegalArgumentException("�������� 'message' �� ����� ���� ������");
		this.messages.add(message);
	}

	/**
	 * ��������� ���� ������
	 * @param error - ����� ���������-������
	 */
	public void addError(String error)
	{
		if (error == null || error.length() == 0)
			throw new IllegalArgumentException("�������� 'error' �� ����� ���� ������");
		this.errors.add(error);
	}

	/**
	 * ��������� ���� ������ � ���������� � ������������ ������� �������.
	 * @param error - ����� ���������-������
	 */
	public void addInactiveSystemError(String error)
	{
		if (error == null || error.length() == 0)
			throw new IllegalArgumentException("�������� 'error' �� ����� ���� ������");
		this.inactiveSystemErrors.add(error);
	}

	/**
	 * ��������� ��������� �����������
	 * @param messages - ��������� ���������-�����������
	 */
	public void addMessages(Collection<String> messages)
	{
		this.messages.addAll(messages);
	}

	/**
	 * ��������� ��������� ������
	 * @param errors - ��������� ���������-������
	 */
	public void addErrors(Collection<String> errors)
	{
		this.errors.addAll(errors);
	}

	/**
	 * ��������� ��������� ������ � ���������� � ����������� ������� �������.
	 * @param errors - ��������� ���������-������
	 */
	public void addInactiveSystemErrors(Collection<String> errors)
	{
		this.inactiveSystemErrors.addAll(errors);
	}

	/**
	 * ������� ��������� ��������� (����������� � ������)
	 */
	public void clear()
	{
		this.messages.clear();
		this.errors.clear();
		this.inactiveSystemErrors.clear();
	}

	/**
	 * ��������� ��� ���������� �� �������
	 * @return true ���� ��� ���������� �����
	 */
	public boolean isEmpty()
	{
		return messages.isEmpty() && errors.isEmpty() && inactiveSystemErrors.isEmpty();
	}

	/**
	 * ���������, ��� ���� �� ���� �� ����������� �� ����
	 * @return true ���� �� ���� ���� �� ����
	 */
	public boolean isNotEmpty()
	{
		return !isEmpty();
	}
}
