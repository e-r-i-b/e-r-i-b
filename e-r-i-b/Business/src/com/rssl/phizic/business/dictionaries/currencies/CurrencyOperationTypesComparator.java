package com.rssl.phizic.business.dictionaries.currencies;

import com.rssl.phizic.gate.dictionaries.CurrencyOperationType;

import java.util.Comparator;

/**
 * @author Egorova
 * @ created 07.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyOperationTypesComparator implements Comparator<CurrencyOperationType>
{
	public int compare(CurrencyOperationType type1, CurrencyOperationType type2)
		{
			if (type1 == null && type2 == null)
			{
				return 0;
			}
			if (type1 == null && type2 != null)
			{
				return 1;
			}
			if (type1 != null && type2 == null)
			{
				return -1;
			}

			return type1.getCode().compareTo( type2.getCode() );
		}
}
