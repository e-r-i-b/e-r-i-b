package com.rssl.phizic.gate.documents;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 19.04.2010
 * @ $Author$
 * @ $Revision$

 * јбстрактный перевод с карты клиента куда-либо
 *
 * ѕо умолчанию если в описании конкретного интерфейса не указано возможен
 * только способ взымани€ комиссии со счета списани€ (getChargeOffAccount)
 */
public interface AbstractCardTransfer extends AbstractTransfer
{
	/**
	 *  арта списани€.  арта, с которой списываютс€ средства при совершении платежа(или другой операции)
	 *
	 * @return номер карты списани€
	 */
	String getChargeOffCard();

	/**
	 * @return валоюта карты списани€
	 */
    Currency getChargeOffCurrency() throws GateException;

	/**
	 * @return карточный счет
	 */
	String getChargeOffCardAccount();

	/**
	 * ƒата окончани€ срока действи€ карты списани€
	 *
	 * @return дата окончани€ срока действи€ карты списани€
	 */
	Calendar getChargeOffCardExpireDate();

	/**
	 * @return  ќписание катры (Visa Classic, MasterCard, Maestro Cirrus etc)
	 */
	String getChargeOffCardDescription();

	/**
	 * ”становить код авторизации
	 * @param authorizeCode код  авторизации
	 */
	void setAuthorizeCode(String authorizeCode);

	/**
	 * ѕолучить код авторизации
 	 * @return код авторизации
	 */
	String getAuthorizeCode();

	/**
 	 * @return дата авторизации платежа в ѕ÷
	 */
	Calendar getAuthorizeDate();

	/**
	 * ”становить дату авторизации платежа в ѕ÷
	 * @param authorizeDate дата авторизации платежа в ѕ÷
	 */
	void setAuthorizeDate(Calendar authorizeDate);
}
