package com.rssl.phizic.logging.writers;

import com.rssl.phizic.logging.quick.pay.QuickPaymentPanelLogEntry;

/**
 *  Writer ��� ������ ����� ��� ���������� �� ���
 * @author lukina
 * @ created 04.08.2014
 * @ $Author$
 * @ $Revision$
 */
public interface QuickPaymentPanelLogWriter
{
	/**
	 * �������� ���������
	 * @param entry ���������
	 */
	void write(QuickPaymentPanelLogEntry entry) throws Exception;
}
