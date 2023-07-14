package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.finances.category.CardOperationCategoryChangingLogEntry;

/**
 * Writer ��� ������ ����� � ��������� �������� ���������
 * @author lukina
 * @ created 05.08.2014
 * @ $Author$
 * @ $Revision$
 */
public interface CardOperationCategoryChangingLogWriter
{
	/**
	 * �������� ���������
	 * @param logEntry ���������
	 */
	void write(CardOperationCategoryChangingLogEntry logEntry) throws Exception;
}
