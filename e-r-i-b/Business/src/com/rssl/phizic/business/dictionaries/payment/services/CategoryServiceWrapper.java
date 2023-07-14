package com.rssl.phizic.business.dictionaries.payment.services;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lukina
 * @ created 22.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class CategoryServiceWrapper
{
	private static final Map<String, CategoryServiceType> categoryServiceType = new HashMap<String, CategoryServiceType>();

	static
	{
		categoryServiceType.put("01", CategoryServiceType.COMMUNICATION);
		categoryServiceType.put("02", CategoryServiceType.TRANSFER);
		categoryServiceType.put("05", CategoryServiceType.TAX_PAYMENT);
		categoryServiceType.put("06", CategoryServiceType.EDUCATION);
		categoryServiceType.put("07", CategoryServiceType.PFR);
		categoryServiceType.put("18", CategoryServiceType.SERVICE_PAYMENT);
		categoryServiceType.put("99", CategoryServiceType.OTHER);
	}

	public static CategoryServiceType getCategoryServiceType(String code)
	{
		if (code == null)
			return null;
		CategoryServiceType category = categoryServiceType.get(code);
		return category;
	}
}
