package com.rssl.phizic.gate.depo;

import com.rssl.phizic.common.types.Currency;

/**
 * @author lukina
 * @ created 18.10.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Информация о счете депонента
 */
public interface DepositorAccount
{
	/**
	 * @return Номер счета
	 */
	public String getAccountNumber();

	/**
	 * @return Вид пластиковой карты
	 */
	public String getCardType();

	/**
	 * @return Номер пластиковой карты
	 */
	public String getCardId();

	/**
	 * @return Наименование банка, где открыт счет
	 */
	public String getBankName();

	/**
	 * @return БИК
	 */
	public String getBIC();

	/**
	 * @return Корреспондентский счет
	 */
	public String getCorAccount();

	/**
	 * @return Наименование банка, где открыт корреспондентский счет
	 */
	public String getCorBankName();

	/**
	 * @return Назначение счета
	 */
	public String getDestination();

	/**
	 * @return Валюта счета
	 */
	public Currency getCurrency();
	
}
