package com.rssl.phizic.gate.payments;

/**
 * User: Balovtsev
 * Date: 05.10.2011
 * Time: 16:28:38
 */
public interface AccountClosingPayment extends ClientAccountsTransfer
{
	/**
	 * Информация о наличии длительного поручения
	 * (null = нет длительного поручения) 
	 *
	 * @return String
	 */
	String getLongOffertFormalized();

	void setLongOffertFormalized(String longOffertFormalized);
	
	/**
	 * Информация о нарушении депозитного договора по вкладу
	 * (null = нарушений нет)
	 *
	 * @return String
	 */
	String getAgreementViolation();

	void setAgreementViolation(String violationText);

	/**
	 * Информация о наличии зависимости закрываемого вклада от тарифного плана
	 * @return
	 */
	boolean getTariffClosingMsg();

	void setTariffClosingMsg(boolean isTariff);
}
