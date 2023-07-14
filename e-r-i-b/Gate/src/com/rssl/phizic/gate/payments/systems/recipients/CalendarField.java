package com.rssl.phizic.gate.payments.systems.recipients;

/**
 * @author Gainanov
 * @ created 18.08.2008
 * @ $Author$
 * @ $Revision$
 */
public interface CalendarField extends Field
{
	/**
	 * получить период оплаты (непрерывный, разбитый на части)
	 * @return период оплаты
	 */
	public CalendarFieldPeriod getPeriod();
}
