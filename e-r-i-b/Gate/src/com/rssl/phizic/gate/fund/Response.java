package com.rssl.phizic.gate.fund;

import com.rssl.phizic.common.types.fund.FundResponseState;

import java.math.BigDecimal;

/**
 * @author osminin
 * @ created 17.09.14
 * @ $Author$
 * @ $Revision$
 *
 * Результат обработки запроса на сбор средств
 */
public interface Response
{
	/**
	 * Состоит из номера блока инициатора, разделителя '@' и внутреннего идентификатора ответа
	 * @return внешний идентификатор ответа
	 */
	String getExternalId();

	/**
	 * Состоит из номера блока инициатора, разделителя '@' и внутреннего идентификатора запроса
	 * @return внешний идентификатор запроса
	 */
	String getExternalRequestId();

	/**
	 * @return статус обработки запроса
	 */
	FundResponseState getState();

	/**
	 * @return сообщение отправителя денег
	 */
	String getMessage();

	/**
	 * @return сумма перевода
	 */
	BigDecimal getSum();
}
