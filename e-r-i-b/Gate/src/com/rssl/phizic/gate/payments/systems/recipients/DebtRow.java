package com.rssl.phizic.gate.payments.systems.recipients;

import com.rssl.phizic.common.types.Money;

/**
 * @author krenev
 * @ created 30.04.2010
 * @ $Author$
 * @ $Revision$
 * Детальная информация по задолженности
 */
public interface DebtRow
{
	/**
	 * Получить код детальной информации по задолженности
	 * @return код детальной информации по задолженности
	 */
	String getCode();

	/**
	 * Получить описание информации по задолженности
	 * @return описание информации по задолженности
	 */
	String getDescription();

	/**
	 * Получить сумму долга.
	 *
	 * @return получить сумму долга
	 */
	Money getDebt();

	/**
	 * Сумма штрафа
	 *
	 * @return сумма штрафа или Money(0), если нет
	 */
	Money getFine();

	/**
	 * Получить комиссию за оплату долга
	 * @return сумма комиссии или Money(0), если нет
	 */
	Money getCommission();
}
