package com.rssl.phizic.gate.payments.systems.recipients;

import java.math.BigDecimal;

/**
 * ���������� � ���������� �����
 *
 * @author khudyakov
 * @ created 20.03.2011
 * @ $Author$
 * @ $Revision$
 */
public interface BusinessRecipientInfo extends Recipient, RecipientInfo
{
	/**
	 * @return ������������ ��������
	 */
	public BigDecimal getMaxCommissionAmount();

	/**
	 * @return ����������� ��������
	 */
	public BigDecimal getMinCommissionAmount();

	/**
	 * @return ���������� ������ ��������
	 */
	public BigDecimal getCommissionRate();

	/**
	 * @return true - ������������ ������ ���������� ������
	 */
	public Boolean isPropsOnline();

	/**
	 * @return true - ������������ �������������
	 */
	public Boolean isDeptAvailable();
}