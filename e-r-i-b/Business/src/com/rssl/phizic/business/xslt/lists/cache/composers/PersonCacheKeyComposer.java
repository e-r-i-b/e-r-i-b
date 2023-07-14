package com.rssl.phizic.business.xslt.lists.cache.composers;

import com.rssl.phizic.person.Person;

/**
 * @author gladishev
 * @ created 09.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class PersonCacheKeyComposer implements CacheKeyComposer
{
	private static final String STATIC_PART_KEY = "person";

	public String getKey(Object object)
	{
		Long id = ((Person) object).getId();
		if (id == null)
			return null;
		
		return STATIC_PART_KEY + id.toString();
	}
}