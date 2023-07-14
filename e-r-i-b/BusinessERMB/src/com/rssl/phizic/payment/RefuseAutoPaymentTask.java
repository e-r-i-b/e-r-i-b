package com.rssl.phizic.payment;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.utils.PhoneNumber;

/**
 * ��������� ������ "���������� �����������"
 * @author Rtischeva
 * @ created 24.06.14
 * @ $Author$
 * @ $Revision$
 */
public interface RefuseAutoPaymentTask extends PaymentTask
{
	/**
	 * ���������� ����� ��������
	 * @param phoneNumber - ����� ��������
	 */
	public void setPhoneNumber(PhoneNumber phoneNumber);

	/**
	 * ���������� ���� �����
	 * @param cardLink - ���� �����
	 */
	public void setCardLink(CardLink cardLink);

	/**
	 * ���������� id ����� �����������
	 * @param autoPaymentLinkId - id ����� �����������
	 */
	public void setAutoPaymentLinkId(String autoPaymentLinkId);
}
