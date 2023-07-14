package com.rssl.phizic.payment;


import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.business.documents.templates.TemplateDocument;

import java.math.BigDecimal;

/**
 * ��������� ������ "������ �� �������"
 * @author Rtischeva
 * @created 19.09.13
 * @ $Author$
 * @ $Revision$
 */
public interface TemplatePaymentTask extends PaymentTask
{

	/**
	 * ���������� ������
	 * @param template - ������
	 */
	void setTemplate(TemplateDocument template);

	/**
	 * ���������� �����
	 * @param amount - ����� �������
	 */
	void setAmount(BigDecimal amount);

	/**
	 * ���������� ����� ��������
	 * @param cardAlias - ����� ��������
	 */
	void setCardAlias(String cardAlias);

	/**
	 * ���������� ���� �������� (�����)
	 * @param cardLink
	 */
	void setCardLink (BankrollProductLink cardLink);
}
