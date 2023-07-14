package com.rssl.phizic.gate.autopayments;

import com.rssl.phizic.utils.DateHelper;

import java.util.Comparator;

/**
 * @author vagin
 * @ created 04.07.2012
 * @ $Author$
 * @ $Revision$
 */
public class ScheduleItemByDateComparator implements Comparator<ScheduleItem>
{
	public int compare(ScheduleItem o1, ScheduleItem o2)
	{
		if (o1 == null && o2 == null)
			return 0;
		else if (o1 == null)
			return 1;
		else if(o2 == null)
		    return -1;
		else
			return o2.getDate().compareTo(o1.getDate());
	}
}
