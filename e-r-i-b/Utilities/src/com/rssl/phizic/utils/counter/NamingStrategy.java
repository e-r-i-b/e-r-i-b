package com.rssl.phizic.utils.counter;

import java.util.Set;

/**
 * ��������� ����������
 * @author Puzikov
 * @ created 19.05.15
 * @ $Author$
 * @ $Revision$
 */

public interface NamingStrategy
{
	/**
	 * ������������� ��� � �������� � ��������� ����������
	 * @param name ����� ���
	 * @return �������� ���
	 */
	String transform(String name);

	/**
	 * �������� ����������������� ��� �� ������ ��������� ���� ������
	 * @param existingNames ������ �����
	 * @param standardName ����� ���
	 * @return ����� ����������������� ���
	 */
	String unify(Set<String> existingNames, String standardName);
}
