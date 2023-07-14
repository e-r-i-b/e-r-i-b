package com.rssl.phizic.business.dictionaries.replication;

import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.common.AbstractCompatator;
import com.rssl.phizic.gate.dictionaries.officies.Office;

/**
 * @author egorova
 * @ created 24.07.2009
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedOfficesComparator extends AbstractCompatator
{
	//Пришло 2 депа. Один больше второго, если у него НЕ заполнено как можно больше полей.
	//Т.е. если region, branch, office
	public int compare(Object o1, Object o2)
	{
		if (compareToNull(o1,o2)!=9)
			return compareToNull(o1,o2);

		Office dep1 = (Office) o1;
		Office dep2 = (Office) o2;

		ExtendedCodeImpl c1 = new ExtendedCodeImpl(dep1.getCode());
		ExtendedCodeImpl c2 = new ExtendedCodeImpl(dep2.getCode());

		if (compareToNull(c1,c2)!=9)
			return compareToNull(c1,c2);

		if (compareToNull(c1.getRegion(),c2.getRegion())!=9)
			return compareToNull(c1.getRegion(),c2.getRegion());

		//Если ТБ не совпадает, то сразу определяем
		if (!c1.getRegion().equals(c2.getRegion()))
			return Long.valueOf(c1.getRegion()).compareTo(Long.valueOf(c2.getRegion()));

		if (compareToNull(c1.getBranch(),c2.getBranch())!=9)
			return compareToNull(c1.getRegion(),c2.getRegion());

		//Если ОСБ не совпадает, то сразу определяем
		if (!c1.getBranch().equals(c2.getBranch()))
			return Long.valueOf(c1.getBranch()).compareTo(Long.valueOf(c2.getBranch()));

		if (compareToNull(c1.getOffice(),c2.getOffice())!=9)
			return compareToNull(c1.getOffice(),c2.getOffice());

		//И последняя проверка идет уже по ВСП
		if (!c1.getOffice().equals(c2.getOffice()))
			return Long.valueOf(c1.getOffice()).compareTo(Long.valueOf(c2.getOffice()));

		return 0;
	}

	private int compareToNull(Object o1, Object o2)
	{
		if (o1 == null && o2 == null)
		{
			return 0;
		}
		if (o1 == null && o2 != null)
		{
			return -1;
		}
		if (o1 != null && o2 == null)
		{
			return 1;
		}
		return 9;
	}
}
