package com.rssl.phizic.gate.fund;

import com.rssl.phizic.common.types.fund.FundRequestState;

import java.math.BigDecimal;

/**
 * @author osminin
 * @ created 29.12.14
 * @ $Author$
 * @ $Revision$
 *
 * Информация о запросе на сбор средств
 */
public interface RequestInfo
{
	/**
	 * @return внутренний идентификатор
	 */
	Long getInternalId();

	/**
	 * @return статус запроса на сбор средств
	 */
	FundRequestState getState();

	/**
	 * @return текущая собранная сумма
	 */
	BigDecimal getAccumulatedSum();
}
