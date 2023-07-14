package com.rssl.phizic.payment;

import java.math.BigDecimal;

/**
 * ��������� ������ "������ ��������"
 * @author Rtischeva
 * @created 03.10.13
 * @ $Author$
 * @ $Revision$
 */
public interface RechargePhoneTask extends PaymentTask
{
	/**
	 * ������ ��� ������� ��������
	 * @param fromResourceCode
	 */
	void setFromResourceCode(String fromResourceCode);

	/**
	 * ������ ��� ���� � ������� ������ �� ������� �������
	 */
	void setAmountExternalId(String amountExternalId);

	/**
	 * ������ ����� ��������
	 * @param amount
	 */
	void setAmount(BigDecimal amount);

	/**
	 * true, ���� �� ����� ������������
	 * @param needConfirm
	 */
	void setNotNeedConfirm(boolean needConfirm);

	/**
	 * ������ ����� ��������
	 * @param phoneNumber
	 */
	void setPhoneNumber(String phoneNumber);

	/**
	 * ������ ��� ���������� �����
	 * @param providerKey
	 */
	void setProviderKey(Long providerKey);

	/**
	 * ���������� ��� ���. ���� ����������, � ������� �������� ����� ��������
	 * @param fieldName
	 */
	void setRechargePhoneExternalId(String fieldName);

	/**
	 * ���������� �������� ���������� ���������
	 * @param name
	 */
	public void setOperatorName(String name);
}
