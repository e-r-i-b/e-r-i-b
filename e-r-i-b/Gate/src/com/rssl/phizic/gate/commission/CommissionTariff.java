package com.rssl.phizic.gate.commission;

import java.math.BigDecimal;

/**
 * ����� ��������
 * @author niculichev
 * @ created 23.04.2012
 * @ $Author$
 * @ $Revision$
 */
public interface CommissionTariff
{
	/**
	 * @return ��� ������
	 */
	String getCurrencyCode();

	/**
	 * @return ��� ��������
	 */
	TransferType getTransferType();

	/**
	 * @return �������
	 */
	BigDecimal getPercent();

	/**
	 * @return ����������� �����
	 */
	BigDecimal getMinAmount();

	/**
	 * @return ������������ �����
	 */
	BigDecimal getMaxAmount();
}
