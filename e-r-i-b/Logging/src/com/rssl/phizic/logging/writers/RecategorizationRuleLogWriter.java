package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.finances.recategorization.ALFRecategorizationRuleEntry;

/**
 * @author lepihina
 * @ created 01.04.14
 * $Author$
 * $Revision$
 * Writer ��� ������ ����� � ����������/���������� ������� �����������������
 */
public interface RecategorizationRuleLogWriter
{
	/**
	 * ���������� ������ � ����������/���������� ������� �����������������
	 * @param entry - ������, ������������ ����� ������ � �����������������
	 * @throws Exception
	 */
	void write(ALFRecategorizationRuleEntry entry) throws Exception;
}
