package com.rssl.phizic.common.types;

/**
 * @author Omeliyanchuk
 * @ created 12.02.2008
 * @ $Author$
 * @ $Revision$
 */

public abstract class CurrencyISOComparator
{
	public static boolean compare(Currency c, Currency d)
	{
		if(d == c)
			return true;
		if (d == null && c == null) return true;

		if ( (d != null && c == null) || (d == null && c != null))
			return false;

		if( c.getCode().equals(d.getCode()) || (c.getCode().equals("RUR")&& d.getCode().equals("RUB"))
				||	(c.getCode().equals("RUB") && d.getCode().equals("RUR")))
			return true;

		return false;
	}
}
