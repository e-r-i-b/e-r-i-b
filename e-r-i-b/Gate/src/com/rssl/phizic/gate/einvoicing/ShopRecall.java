package com.rssl.phizic.gate.einvoicing;

import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * ���������� �� ������� ��������/��������� ������� �� �������.
 *
 * @author bogdanov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */

public interface ShopRecall
{
	/**
	* @return ������������� (PK)
	*/
	public String getUuid();

	/**
	* @return ��������� �����.
	*/
	public String getOrderUuid();

	/**
	* @return ���������� ������������� ��������� ������/�������� � ����������
	*/
	public String getExternalId();

	/**
	* @return ������������� ��������� ������ ������ � ��������
	*/
	public String getUtrrno();

	/**
	* @return ���������� ������ ������/��������
	*/
	public RecallState getState();

	/**
	 * @return ��� ����������
	 */
	public String getReceiverCode();

	/**
	 * @return ���� ������
	 */
	public Calendar getDate();

	/**
	 * @return ���
	 */
	public RecallType getType();

	/**
	 * ����� ������ �������/�������� ������
	 * @return - �����
	 */
	public Money getAmount();
}
