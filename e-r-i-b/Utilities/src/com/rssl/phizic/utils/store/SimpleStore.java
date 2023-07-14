package com.rssl.phizic.utils.store;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Krenev
 * @ created 03.02.2009
 * @ $Author$
 * @ $Revision$
 */
public class SimpleStore implements Store
{
	private Map<String, Object> store = new HashMap<String, Object>();

	public String getId()
	{
		return store.toString();
	}

	public void save(String key, Object obj)
	{
		store.put(key,obj);
	}

	public Object restore(String key)
	{
		return store.get(key);
	}

	public void clear()
	{
		store.clear();
	}

	public void remove(String key)
	{
		store.remove(key);
	}

	public Object getSyncObject()
	{
		return store;
	}
}
