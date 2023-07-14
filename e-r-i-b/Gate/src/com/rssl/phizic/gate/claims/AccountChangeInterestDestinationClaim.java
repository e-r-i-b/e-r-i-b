package com.rssl.phizic.gate.claims;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.gate.documents.Claim;
import com.rssl.phizic.gate.payments.AccountOrIMATransferBase;

/**
 * Заявка на изменение порядка уплаты процентов по вкладу
 * @author Pankin
 * @ created 15.09.13
 * @ $Author$
 * @ $Revision$
 */
public interface AccountChangeInterestDestinationClaim extends Claim, AccountOrIMATransferBase
{
	/**
	 * @return номер счета, для которого выполняется заявка
	 */
	String getChangePercentDestinationAccountNumber() throws DocumentException;

	/**
	 * @return номер карты, на которую следует перечислять проценты, либо null, если проценты надо перечислять на счет.
	 */
	String getPercentCardNumber();

	/**
	 * Установка ИД промоутера
	 * @param promoterId
	 */
	void setPromoterId(String promoterId);

	/**
	 * Установка номера карты, под которой вощел клиент
	 * @param cardNumber
	 */
	void setLogonCardNumber(String cardNumber);
}
