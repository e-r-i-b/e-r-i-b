package com.rssl.phizic.gate.fund;

import com.rssl.phizic.common.types.fund.FundRequestState;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author osminin
 * @ created 17.09.14
 * @ $Author$
 * @ $Revision$
 *
 * Запрос на сбор средств
 */
public interface Request
{
	/**
	 * Состоит из номера блока инициатора, разделителя '@' и внутреннего идентификатора
	 * @return внешний идентификатор запроса
	 */
	String getExternalId();

	/**
	 * @return статус запроса
	 */
	FundRequestState getState();

	/**
	 * @return необходимая общая сумма
	 */
	BigDecimal getRequiredSum();

	/**
	 * @return рекомендованная сумма
	 */
	BigDecimal getReccomendSum();

	/**
	 * @return сообщение отправителям денег
	 */
	String getMessage();

	/**
	 * @return ресурс списания
	 */
	String getResource();

	/**
	 * @return Дата закрытия зарпоса
	 */
	Calendar getClosedDate();

	/**
	 * @return ожидаемая дата закрытия запроса
	 */
	Calendar getExpectedClosedDate();

	/**
	 * @return дата открытия запроса
	 */
	Calendar getCreatedDate();
}
