package com.rssl.phizic.gate.payments;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Calendar;

/**
 * @author Novikov_A
 * @ created 05.06.2007
 * @ $Author$
 * @ $Revision$
 * @deprecated Ќе удовлетвор€ет модели гейта.
 */

@Deprecated
public interface StatusChangerTask
{
	/**
	 * ќбновление статусов платежей
	 * @param startDate начало периода обновлени€
	 * @param endDate окончание
	 * @throws GateException
	 */
	void updateStatus(Calendar startDate, Calendar endDate) throws GateException, GateLogicException;
}
