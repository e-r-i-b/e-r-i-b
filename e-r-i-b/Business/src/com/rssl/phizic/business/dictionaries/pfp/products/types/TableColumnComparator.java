package com.rssl.phizic.business.dictionaries.pfp.products.types;

import java.util.Comparator;

/**
 * @author koptyaev
 * @ created 14.08.13
 * @ $Author$
 * @ $Revision$
 */
public class TableColumnComparator  implements Comparator
{
	public int compare(Object o, Object o2)
	{
		TableColumn column1 = (TableColumn)o;
		TableColumn column2 = (TableColumn)o2;
		return column1.getOrderIndex().compareTo(column2.getOrderIndex());
	}
}
