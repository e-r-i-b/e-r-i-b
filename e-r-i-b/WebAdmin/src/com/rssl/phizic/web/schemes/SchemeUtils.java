package com.rssl.phizic.web.schemes;

import com.rssl.phizic.business.services.Service;

import java.util.Comparator;

/**
 * @author Evgrafov
 * @ created 31.05.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4221 $
 */

public class SchemeUtils
{
	// компаратор для сортировки услуг по имени
	public static final Comparator<Service> SERVICES_COMPARATOR = new Comparator<Service>()
	{
		public int compare(Service o1, Service o2)
		{
			int res = o1.getName().compareTo(o2.getName());

			if (res == 0)
				res = o1.getKey().compareTo(o2.getKey());

			return res;
		}
	};
}