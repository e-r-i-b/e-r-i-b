package com.rssl.phizic.gate.reminder;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 26.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Состояние напоминания
 */
public interface ReminderState
{
	/**
	 * @return дата оплаты напоминания
	 */
	Calendar getProcessDate();

	/**
	 * @return дата, на которую было отложено напоминание
	 */
	Calendar getDelayedDate();

	/**
	 * @return дата оставшегося выставленного счета
	 */
	Calendar getResidualDate();
}
