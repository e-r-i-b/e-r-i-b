package com.rssl.phizic.payment;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.utils.PhoneNumber;

import java.math.BigDecimal;

/**
 * �������� ������ "������� �� ������ ��������"
 * @author Dorzhinov
 * @ created 24.05.2013
 * @ $Author$
 * @ $Revision$
 */
public interface PhoneTransferTask extends PaymentTask
{
	/**
	 * ���������� ����� ����� ��������
	 * @param fromResourceAlias    ����� ����� �������� (never null)
	 */
	void setFromResourceAlias(String fromResourceAlias);

	/**
	 * @return ����� ����� ��������
	 */
	String getFromResourceAlias();

	/**
	 * ���������� ��� ����� ��������
	 * @param fromResourceCode - ��� ����� �������� (never null)
	 */
	void setFromResourceCode(String fromResourceCode);

	/**
	 * @return ��� ����� ��������
	 */
	String getFromResourceCode();
	
	/**
	 * ���������� ������� ���������� ��������.
	 * @param phoneNumber ������� ���������� �������� (never null)
	 */
	void setPhone(PhoneNumber phoneNumber);

	/**
	 * ���������� ����� ��������.
	 * exactAmount = ����� ��������.
	 * @param amount - ����� �������� � ������ (never null)
	 */
	void setAmount(BigDecimal amount);

	/**
	 * @return ����� ��������
	 */
	BigDecimal getAmount();

	/**
	 * ���������� ���� ����� ��������
	 * @param chargeOffLink
	 */
	void setChargeOffLink(CardLink chargeOffLink);
}
