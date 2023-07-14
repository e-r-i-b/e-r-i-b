package com.rssl.phizgate.common.profile;

import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Действия при добавлении или удалении телефона.
 *
 * @author bogdanov
 * @ created 29.01.15
 * @ $Author$
 * @ $Revision$
 */

public interface MBKPhoneLoaderCallBack
{
	/**
	 * при добавлении телефона.
	 *
	 * @param phone телефон.
	 */
	public void onAdd(String phone);

	/**
	 * при удалении телефона.
	 *
	 * @param phone телефон.
	 */
	public void onDelete(String phone) throws GateException;
}
