package com.rssl.phizic.module;

import com.rssl.phizic.utils.ClassHelper;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Erkin
 * @ created 07.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Реализация менеджера модулей на базе статической мапы "класс_модуля -> модуль"
 */
public class ModuleStaticManager implements ModuleManager
{
	private static final ModuleManager instance = new ModuleStaticManager();

	private final ConcurrentHashMap<Class, Entry> entries = new ConcurrentHashMap<Class, Entry>();

	///////////////////////////////////////////////////////////////////////////

	private ModuleStaticManager() {}

	/**
	 * @return инстанс менеджера
	 */
	public static ModuleManager getInstance()
	{
		return instance;
	}

	public <T extends Module> T getModule(Class<T> moduleClass)
	{
		Entry newEntry = new Entry();
		Entry entry = entries.putIfAbsent(moduleClass, newEntry);
		if (entry == null)
			entry = newEntry;

		if (entry.module == null)
		{
			//noinspection SynchronizationOnLocalVariableOrMethodParameter
			synchronized (entry)
			{
				if (entry.module == null)
					entry.module = ClassHelper.newInstance(moduleClass);
			}
		}

		//noinspection unchecked
		return (T) entry.module;
	}

	private static class Entry
	{
		private Module module;
	}
}
