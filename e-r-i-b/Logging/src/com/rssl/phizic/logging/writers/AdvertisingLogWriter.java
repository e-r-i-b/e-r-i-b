package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.advertising.AdvertisingLogEntry;

/**
 *  Writer ��� ������ ����� ��� ���������� �� ��������
 * @author lukina
 * @ created 04.08.2014
 * @ $Author$
 * @ $Revision$
 */
public interface AdvertisingLogWriter
{
	/**
	 * �������� ���������
	 * @param entry ���������
	 */
	void write(AdvertisingLogEntry entry) throws Exception;
}
