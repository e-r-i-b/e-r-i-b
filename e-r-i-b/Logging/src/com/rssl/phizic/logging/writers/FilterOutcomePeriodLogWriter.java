package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.finances.period.FilterOutcomePeriodLogRecord;

/**
 * Writer ��� ������ ����� � ������ ������� ���������� ��������
 * @author lukina
 * @ created 05.08.2014
 * @ $Author$
 * @ $Revision$
 */
public interface FilterOutcomePeriodLogWriter
{
	/**
	 * �������� ���������
	 * @param logEntry ���������
	 */
	void write(FilterOutcomePeriodLogRecord logEntry) throws Exception;
}
