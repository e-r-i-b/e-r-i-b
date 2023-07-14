package com.rssl.phizic.business.loans;

import com.rssl.phizic.common.AbstractCompatator;
import com.rssl.phizic.gate.loans.ScheduleItem;

/**
 * Сравнение двух платежей по кредиту по их номерам в графике платежей
 * @author gladishev
 * @ created 29.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class LoanPaymentComparator extends AbstractCompatator
{
	public int compare(Object o1, Object o2)
	{
		ScheduleItem item1 = (ScheduleItem) o1;
		ScheduleItem item2 = (ScheduleItem) o2;

		if (item1 == null && item2 == null)
		{
			return 0;
		}
		if (item1 == null || item2 == null)
		{
			return -1;
		}
		return item1.getPaymentNumber().compareTo(item2.getPaymentNumber());
	}
}
