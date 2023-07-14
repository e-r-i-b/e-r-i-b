package com.rssl.phizic.payment;

import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.utils.PhoneNumber;

import java.math.BigDecimal;

/**
 * ��������� ������ "����������� �����������"
 * @author Rtischeva
 * @ created 13.06.14
 * @ $Author$
 * @ $Revision$
 */
public interface CreateAutoPaymentTask extends PaymentTask
{
	/**
	 * ���������� ����� ��������
	 * @param phoneNumber - ����� ��������
	 */
	public void setPhoneNumber(PhoneNumber phoneNumber);

	/**
	 * ���������� �����
	 * @param amount - �����
	 */
	public void setAmount(BigDecimal amount);

	/**
	 * ���������� �����
	 * @param threshold - �����
	 */
	public void setThreshold(BigDecimal threshold);

	/**
	 * ���������� �����
	 * @param limit - �����
	 */
	public void setLimit(BigDecimal limit);

	/**
	 * ���������� ���� ����� ��������
	 * @param cardLink - ���� ����� ��������
	 */
	public void setCardLink(BankrollProductLink cardLink);

	/**
	 * ���������� ���������� �����
	 * @param provider
	 */
	public void setProvider(BillingServiceProvider provider);

}
