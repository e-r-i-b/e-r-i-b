package com.rssl.phizic.business.advertising;

import java.util.Comparator;

/**
 * Компаратор для сортировки объектов баннера по orderIndex.
 * @author lepihina
 * @ created 29.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class AdvertisingOrderedFieldComparator implements Comparator<AdvertisingOrderedField>
{
	public int compare(AdvertisingOrderedField orderedField1, AdvertisingOrderedField orderedField2)
	{
		if (orderedField1.getOrderIndex() < orderedField2.getOrderIndex())
			return -1;
		if (orderedField1.getOrderIndex() > orderedField2.getOrderIndex())
			return 1;
		return 0;
	}
}
