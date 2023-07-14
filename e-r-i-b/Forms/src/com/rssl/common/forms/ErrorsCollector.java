package com.rssl.common.forms;

import com.rssl.common.forms.validators.MessageHolder;

import java.util.Collection;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 26.05.2006
 * @ $Author: rtishcheva $
 * @ $Revision: 73973 $
 */

public interface ErrorsCollector<E>
{
	//��������� �� ������ � ������, ���� ��������� �� ����� ���� �������� ��-�� ������������� ������� ��������.
	public static final String UNACCESSIBLE_MESSAGE = "com.rssl.common.forms.validators.temporal.unaccessible.message";
	/**
	 * ��������� ������
	 */
	E errors();

	/**
	 * �������� ������
	 * @param value ��������� ��������
	 * @param field ����������� ����
	 * @param messageHolder ������ ���������� � ���� ���������� �� ������
	 */
	void add(String value, Field field, MessageHolder messageHolder);

	/**
	 * �������� ������
	 * @param values �������� ����� �����
	 * @param errorFieldNames - �������� ��������� �����
	 * @param messageHolder ����� ���������� � ���� ���������� �� ������
	 */
	void add(Map<String, Object> values, Collection<String> errorFieldNames, MessageHolder messageHolder);

	/**
	 * �������� ������
	 * @param messageHolder ������, ���������� � ���� ���������� �� ������
	 */
	void add(MessageHolder messageHolder);

	/**
	 * �������� ������������ � ���, ��� ��� ����������� �� ����� ���� ��������, �.�. ��������� ������� �������� ����������
	 */
	void setTemporalUnAccesible();

	/**
	 * ���������� ��������� ������
	 */
	int count();
}
