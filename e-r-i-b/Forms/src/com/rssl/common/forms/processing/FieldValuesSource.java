package com.rssl.common.forms.processing;

import java.io.Serializable;
import java.util.Map;

/**
 * �������� ������� ����� ��� ������ � �������
 * @author Evgrafov
 * @ created 15.12.2005
 * @ $Author: niculichev $
 * @ $Revision: 52888 $
 */
public interface FieldValuesSource extends Serializable
{
	/**
	 * ���������� �������� ���� �� ��� �����
	 * @param name ��� ����
	 * @return �������� ����
	 */
	String getValue(String name);

	/**
	 * @return ��� �������� � ���� ���� (����-��������)
	 */
	Map<String, String> getAllValues();

	/**
	 *
	 * @param name ��� ����
	 * @return ���������� ���� ��� ���
	 */
	boolean isChanged(String name);

	/**
	 * @return ���� �� ��������.
	 */
	boolean isEmpty();

	/**
	 * ������������� �� ����� � ������ ��������� �� �������� ���� � ������ name
	 * @param name ��� ����
	 * @return true - �������������
	 */
	boolean isMasked(String name);
}
