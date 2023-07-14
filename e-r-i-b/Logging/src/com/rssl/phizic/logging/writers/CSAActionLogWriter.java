package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.csaAction.CSAActionLogEntryBase;

/**
 * @author vagin
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 * Writer ��� �������� � ��� ������������������� ������������.
 */
public interface CSAActionLogWriter
{
	/**
	 * ������ � ������.
	 * @param entry - ������ � �������
	 */
	void write(CSAActionLogEntryBase entry) throws Exception;
}
