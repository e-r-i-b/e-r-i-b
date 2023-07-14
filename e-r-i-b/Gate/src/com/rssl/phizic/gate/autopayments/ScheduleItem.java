package com.rssl.phizic.gate.autopayments;

import java.util.Calendar;
import java.math.BigDecimal;
import java.io.Serializable;

/**
 * @author bogdanov
 * @ created 27.01.2012
 * @ $Author$
 * @ $Revision$
 *
 * платеж, совершенный по подписке на автоплатежи.
 */

public interface ScheduleItem extends Serializable
{
	/**
	 * @return Внешний идентификатор платежа.
	 */
	String getExternalId();

	/**
	 * @return Номер платежа.
	 */
	Long getNumber();

	/**
	 * @return дата.
	 */
	Calendar getDate();

	/**
	 * @return статус.
	 */
	PaymentStatus getStatus();

	/**
	 * @return причина отказа.
	 */
	String getRejectionCause();

	/**
	 * @return сумма.
	 */
	BigDecimal getSumm();

	/**
	 * @return комиссия.
	 */
	BigDecimal getCommission();

	/**
	 * @return имя получателя.
	 */
	String getRecipientName();
}
