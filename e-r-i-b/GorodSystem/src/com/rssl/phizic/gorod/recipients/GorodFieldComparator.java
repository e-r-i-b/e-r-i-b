package com.rssl.phizic.gorod.recipients;

import com.rssl.phizgate.common.payments.systems.recipients.CommonField;

import java.util.Comparator;

/**
 * @author Gainanov
 * @ created 16.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class GorodFieldComparator implements Comparator
{
	public int compare(Object o1, Object o2)
	{
		CommonField f1 = (CommonField)o1;
		CommonField f2 = (CommonField)o2;
		return f1.getNum() - f2.getNum();
	}
}
