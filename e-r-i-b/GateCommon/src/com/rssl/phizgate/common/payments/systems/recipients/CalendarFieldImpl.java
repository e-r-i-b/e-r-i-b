package com.rssl.phizgate.common.payments.systems.recipients;

import com.rssl.phizic.gate.payments.systems.recipients.CalendarField;
import com.rssl.phizic.gate.payments.systems.recipients.CalendarFieldPeriod;

/**
 * @author Gainanov
 * @ created 18.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class CalendarFieldImpl extends FieldImpl implements CalendarField 
{
	private String month;
	private CalendarFieldPeriod period;

	public CalendarFieldPeriod getPeriod()
	{
		return period;
	}

	/**
	 * установить мес€ц
	 * @param month мес€ц
	 */
	public void setMonth(String month)
	{
		this.month = month;
	}

	/**
	 *  установить период
	 * @param period период
	 */
	public void setPeriod(CalendarFieldPeriod period)
	{
		this.period = period;
	}
}
