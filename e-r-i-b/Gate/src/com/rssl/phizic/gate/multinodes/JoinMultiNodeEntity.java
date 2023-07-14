package com.rssl.phizic.gate.multinodes;

import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 11.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ��� ����������� �������� ������� �
 * ������������ ������ ���������� �� ������ ������ �� ���������� �����
 */
public interface JoinMultiNodeEntity<T> extends Comparable<T>, Serializable
{
	/**
	 * ��������� �������
	 * @param anotherObject ������ � ������� ���������� ��������� �������
	 */
	public void join(T anotherObject);
}
