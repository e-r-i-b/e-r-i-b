package com.rssl.phizic.operations.erkc.context;

import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;

/**
 * @author akrenev
 * @ created 11.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Контекст работы сотрудников ЕРКЦ
 */

public final class ERKCEmployeeContext
{
	private static final String KEY_IN_STORE = ERKCEmployeeContext.class.getName();

	private ERKCEmployeeContext(){}

	private static Store getStore()
	{
		return StoreManager.getCurrentStore();
	}

	/**
	 * задать информацию сотрудника ЕРКЦ
	 * @param data информация сотрудника ЕРКЦ
	 */
	public static void setData(ERKCEmployeeData data)
	{
		getStore().save(KEY_IN_STORE, data);
	}

	/**
	 * @return информация сотрудника ЕРКЦ
	 */
	public static ERKCEmployeeData getData()
	{
		//noinspection unchecked
		return (ERKCEmployeeData) getStore().restore(KEY_IN_STORE);
	}
}