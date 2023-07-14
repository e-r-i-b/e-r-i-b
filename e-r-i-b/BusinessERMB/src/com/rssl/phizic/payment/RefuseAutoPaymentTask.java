package com.rssl.phizic.payment;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.utils.PhoneNumber;

/**
 * Платежная задача "отключение автоплатежа"
 * @author Rtischeva
 * @ created 24.06.14
 * @ $Author$
 * @ $Revision$
 */
public interface RefuseAutoPaymentTask extends PaymentTask
{
	/**
	 * установить номер телефона
	 * @param phoneNumber - номер телефона
	 */
	public void setPhoneNumber(PhoneNumber phoneNumber);

	/**
	 * установить линк карты
	 * @param cardLink - линк карты
	 */
	public void setCardLink(CardLink cardLink);

	/**
	 * установить id линка автоплатежа
	 * @param autoPaymentLinkId - id линка автоплатежа
	 */
	public void setAutoPaymentLinkId(String autoPaymentLinkId);
}
