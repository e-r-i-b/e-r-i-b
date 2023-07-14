package com.rssl.phizic.documents;

import com.rssl.common.forms.DocumentException;

/**
 * �������� �������� �������� ����
 *
 * @author khudyakov
 * @ created 17.11.14
 * @ $Author$
 * @ $Revision$
 */
public interface AbstractP2PTransfer extends AbstractPaymentTransfer
{
	/**
	 * ��� ����������
	 * @return �����/���� (PHIZ_RECEIVER_TYPE_VALUE/JUR_RECEIVER_TYPE_VALUE)
	 */
	String getReceiverType();

	/**
	 * ������ ����������
	 * @return �� ���� � ���������/�� ����� � ��������/�� ���� � ������ �����
	 */
	String getReceiverSubType();

	/**
	 * @return ����� ����������.
	 */
	public String getReceiverCard();

	/**
	 * @return ����� ���������� ��������
	 */
	public String getMobileNumber();

	/**
	 * @return ����� �������� �� �������� �����
	 */
	public String getContactPhone() throws DocumentException;

	/**
	 * @return true -- ������� �� ����� ������� ����� �� ����� ���� �������� ��������� � ��������
	 */
	public boolean isOurBankCard();

	/**
	 * ���������� ����� ��� ���-��������� ���������� ��������, ���� �� ����� �� ����� ��������������
	 * @return ����� ��� ���-��������� ��� ������ ������
	 */
	public String getMessageToReceiver();

	/**
	 * ������������� ����� ��� ���-��������� ���������� ��������, ���� �� ����� �� ����� ��������������
	 * @param status ������
	 */
	public void setMessageToReceiverStatus(String status);
}
