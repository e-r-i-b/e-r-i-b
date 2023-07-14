package com.rssl.phizic.context;

import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;

/**
 * @author mihaylov
 * @ created 24.02.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� ���������� � ������� ���
 */
public class CSAAdminEmployeeContext
{
	private static final String KEY_IN_STORE = CSAAdminEmployeeContext.class.getName();

	private static Store getStore()
	{
		return StoreManager.getCurrentStore();
	}

	/**
	 * @param data ������ ����������
	 */
	public static void setData(MultiNodeEmployeeData data)
	{
		getStore().save(KEY_IN_STORE,data);
	}

	/**
	 * @return ������ ����������
	 */
	public static MultiNodeEmployeeData getData()
	{
		return  (MultiNodeEmployeeData)getStore().restore(KEY_IN_STORE);
	}

}
