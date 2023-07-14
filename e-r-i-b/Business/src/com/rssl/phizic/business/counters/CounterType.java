package com.rssl.phizic.business.counters;

import com.rssl.phizic.config.promoCodesDeposit.PromoCodesDepositConfig;
import com.rssl.phizic.utils.ClientConfig;

/**
 * ��� ��������
 * @author niculichev
 * @ created 07.06.2012
 * @ $Author$
 * @ $Revision$
 */
public enum CounterType
{
	/**
	 * ������� ��������� ���� � ���������� �� ������ �������� ��� �������� ������ ��� ����
	 *  ��������� � ������ ���������� � ������ ��������� > ����������� �� ��������
	 */
	RECEIVE_INFO_BY_PHONE(ClientConfig.INFO_PERSON_PAYMENT_LIMIT_KEY),

	//������� ����� ���������
	PROMO_CODE(PromoCodesDepositConfig.MAX_UNSUCCESSFULL_ITERATIONS);

	private String value;

	CounterType(String value)
	{
		this.value = value;
	}

	public String toValue()
	{
		return value;
	}
}
