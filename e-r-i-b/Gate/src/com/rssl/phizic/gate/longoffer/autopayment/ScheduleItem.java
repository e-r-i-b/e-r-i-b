package com.rssl.phizic.gate.longoffer.autopayment;

import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.io.Serializable;

/**
 * @author osminin
 * @ created 31.01.2011
 * @ $Author$
 * @ $Revision$
 * Строка графика исполнения автоплатежа
 */
public interface ScheduleItem extends Serializable
{
	/**
	 * @return Дата исполнения
	 */
	public Calendar getDate();

	/**
	 * @return Сумма платежа
	 */
	public Money getAmount();

	/**
	 * @return Статус платежа
	 */
	public ScheduleItemState getState();
}
