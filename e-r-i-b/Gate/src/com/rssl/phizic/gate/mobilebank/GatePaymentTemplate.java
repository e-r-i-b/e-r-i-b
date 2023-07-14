package com.rssl.phizic.gate.mobilebank;

import java.io.Serializable;

/**
 * @author Erkin
 * @ created 06.10.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ �������
 */
public interface GatePaymentTemplate extends Serializable
{
	/**
	 * ���������� ID ������� �� ������� �������
	 *
	 * @return ID ������� �� ������� �������
	 */
	String getExternalId();

	/**
	 * @return ����� �����, �� ������� �������������� �����
	 */
	String getCardNumber();

	/**
	 * ���������� ��� ���������� �������
	 * (��� � �������� ������� �������)
	 *
	 * @return ��� ���������� �������
	 */
	String getRecipientCode();

	/**
	 * ���������� ��� �����������
	 * (��� � �������� ������� �������)
	 *
	 * @return ��� �����������
	 */
	String getPayerCode();

	/**
	 * ���������� ������ �������
	 *
	 * @return true, ���� ������ ��������
	 */
	boolean isActive();
}
