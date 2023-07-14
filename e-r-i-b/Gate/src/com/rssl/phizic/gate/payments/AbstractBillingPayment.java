package com.rssl.phizic.gate.payments;

import com.rssl.phizic.gate.documents.AbstractTransfer;

/**
 * @author osminin
 * @ created 28.01.2011
 * @ $Author$
 * @ $Revision$
 *
 * ����� ��������� ��� ����������� ��������
 */
public interface AbstractBillingPayment extends AbstractTransfer
{
	/**
	 * ��� ����� ���������� ��������
	 * @return ��� ����� ���������� ��������
	 */
	String getReceiverPointCode();

	/**
	 * @return ���������� ������������� ���������� �����
	 */
	String getMultiBlockReceiverPointCode();

	/**
	 * @return ���������� ������������� ����������
	 */
	Long getReceiverInternalId();
}
