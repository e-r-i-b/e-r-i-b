package com.rssl.phizic.gate.commission;

import java.math.BigDecimal;

/**
 * Тариф комиссий
 * @author niculichev
 * @ created 23.04.2012
 * @ $Author$
 * @ $Revision$
 */
public interface CommissionTariff
{
	/**
	 * @return код валюты
	 */
	String getCurrencyCode();

	/**
	 * @return тип перевода
	 */
	TransferType getTransferType();

	/**
	 * @return процент
	 */
	BigDecimal getPercent();

	/**
	 * @return минимальная сумма
	 */
	BigDecimal getMinAmount();

	/**
	 * @return максимальная сумма
	 */
	BigDecimal getMaxAmount();
}
