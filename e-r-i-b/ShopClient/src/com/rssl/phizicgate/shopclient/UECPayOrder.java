package com.rssl.phizicgate.shopclient;

/**
 * @author Erkin
 * @ created 13.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��� � ������� ��������� ��������� ���.
 * ������������ ��� �������� ������� ��������� � ���:
 * - �� ����� DocUID � ������ �������
 * - �� ������ ��������� �������� �������� (���������� / �� ����������).
 */
public class UECPayOrder
{
	/**
	 * ID ����-������, ���������������� ���������
	 */
	private final long orderId;

	/**
	 * ID ��������� � ���
	 */
	private final String docUID;

	/**
	 * ������ ����-�������, ���������������� ���������
	 */
	private final String paymentState;

	/**
	 * ��� ������� ����������
	 */
	private Long notifyStatusCode = null;

	/**
	 * ��������� ������� ����������
	 */
	private String notifyStatusDescr = null;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param orderId - ID ����-�������, ���������������� ���������
	 * @param docUID - ID ��������� � ���
	 * @param paymentState - ������ ����-�������, ���������������� ���������
	 */
	public UECPayOrder(long orderId, String docUID, String paymentState)
	{
		this.orderId = orderId;
		this.docUID = docUID;
		this.paymentState = paymentState;
	}

	/**
	 * @return ID ��������� � ���
	 */
	public String getDocUID()
	{
		return docUID;
	}

	/**
	 * @return ID ����-�������, ���������������� ���������
	 */
	public long getOrderId()
	{
		return orderId;
	}

	/**
	 * @return ������ ����-�������, ���������������� ���������
	 */
	public String getPaymentState()
	{
		return paymentState;
	}

	public Long getNotifyStatusCode()
	{
		return notifyStatusCode;
	}

	public void setNotifyStatusCode(Long notifyStatusCode)
	{
		this.notifyStatusCode = notifyStatusCode;
	}

	public String getNotifyStatusDescr()
	{
		return notifyStatusDescr;
	}

	public void setNotifyStatusDescr(String notifyStatusDescr)
	{
		this.notifyStatusDescr = notifyStatusDescr;
	}
}
