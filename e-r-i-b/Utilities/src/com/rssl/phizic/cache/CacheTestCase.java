package com.rssl.phizic.cache;

import junit.framework.TestCase;
import net.sf.ehcache.*;

/**
 * @author Evgrafov
 * @ created 09.11.2006
 * @ $Author: krenev $
 * @ $Revision: 35904 $
 */

@SuppressWarnings({"JavaDoc"})
public class CacheTestCase extends TestCase
{
	public void test() throws Exception
	{
		CacheProvider.addCache("zzz");

		Cache cache = CacheProvider.getCache("zzz");
		assertNotNull(cache);

		cache.put(new Element("zzz", "zzz"));
		Element element = cache.get("zzz");
		assertNotNull(element);

		assertEquals(element.getValue(), "zzz");
	}
}