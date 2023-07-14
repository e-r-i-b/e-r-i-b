package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.push.StatisticLogEntry;

/**
 * Writer ��� ������ ����� ��� ����������
 * @author basharin
 * @ created 13.11.13
 * @ $Author$
 * @ $Revision$
 */

public interface StatisticLogWriter
{
	/**
	 * ���������� ������ � ������ ����������
     * @param entry - ������, ������������ ����� ������ � ������� ����������
	 * @throws Exception
	 */
	void write(StatisticLogEntry entry) throws Exception;
}
