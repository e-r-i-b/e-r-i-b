package com.rssl.phizic.gate.depo;

/**
 * @author akrenev
 * @ created 15.12.2011
 * @ $Author$
 * @ $Revision$
 */
/**
 * Подоперации над ценными бумагами
 */
public enum TransferSubOperation
{
	/**
	 * перевод на счет депо внутри депозитария
	 */
	INTERNAL_TRANSFER,
	/**
	 * перевод на счет в реестре
	 */
	LIST_TRANSFER,
	/**
	 * перевод на счет в другом депозитарии
	 */
	EXTERNAL_TRANSFER,
	/**
	 * прием перевода со счета депо внутри депозитария
	 */
	INTERNAL_RECEPTION,
	/**
	 * прием перевода со счета в реестре
	 */
	LIST_RECEPTION,
	/**
	 * прием перевода со счета в другом депозитарии
	 */
	EXTERNAL_RECEPTION,
	/**
	 * перевод между разделами счета депо
	 */
	INTERNAL_ACCOUNT_TRANSFER;
}