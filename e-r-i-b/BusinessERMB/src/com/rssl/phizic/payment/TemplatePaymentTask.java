package com.rssl.phizic.payment;


import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.business.documents.templates.TemplateDocument;

import java.math.BigDecimal;

/**
 * Платежная задача "оплата по шаблону"
 * @author Rtischeva
 * @created 19.09.13
 * @ $Author$
 * @ $Revision$
 */
public interface TemplatePaymentTask extends PaymentTask
{

	/**
	 * установить шаблон
	 * @param template - шаблон
	 */
	void setTemplate(TemplateDocument template);

	/**
	 * установить сумму
	 * @param amount - сумма платежа
	 */
	void setAmount(BigDecimal amount);

	/**
	 * установить карту списания
	 * @param cardAlias - карта списания
	 */
	void setCardAlias(String cardAlias);

	/**
	 * Установить линк списания (карта)
	 * @param cardLink
	 */
	void setCardLink (BankrollProductLink cardLink);
}
