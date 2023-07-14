package com.rssl.phizic.gate.longoffer;

import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.io.Serializable;

/**
 * @author krenev
 * @ created 20.08.2010
 * @ $Author$
 * @ $Revision$
 * строка графика исплнения длительного поручения
 */
public interface ScheduleItem extends Serializable
{
	/**
	 * @return дата исполения
	 */
	public Calendar getDate();

	/**
	 * @return сумма платежа
	 */
	public Money getAmount();

	/**
	 * @return статус платежа
	 */
	public SheduleItemState getState();
}
