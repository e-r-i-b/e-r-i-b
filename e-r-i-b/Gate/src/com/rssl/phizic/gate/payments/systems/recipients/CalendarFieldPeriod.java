package com.rssl.phizic.gate.payments.systems.recipients;

/**
 * возможные значени€ дл€ периода оплаты
 * @author Gainanov
 * @ created 18.08.2008
 * @ $Author$
 * @ $Revision$
 */
public enum CalendarFieldPeriod
{
	/**
	 * можно отметить на календаре отдельные мес€цы. Ќапример: май, август, окт€брь.
	 */
	broken ,

	/**
	 * можно отметить только непрерывный период
 	 */
	unbroken;
}
