package com.rssl.phizic.web.common;

import com.rssl.phizic.utils.store.StoreManager;

/**
 * @author akrenev
 * @ created 13.05.2013
 * @ $Author$
 * @ $Revision$
 *
 * контекст УРЛов
 */

public class URLContext
{
	private static final String MAIN_URL_ATTRIBUTE_NAME = "mainURL";

	/**
	 * задать основной урл
	 * @param mainUrl основной урл
	 */
	public static void setMainUrlInfo(String mainUrl)
	{
		StoreManager.getCurrentStore().save(MAIN_URL_ATTRIBUTE_NAME, mainUrl);
	}

	/**
	 * @return основной урл
	 */
	public static String getMainUrlInfo()
	{
		return (String) StoreManager.getCurrentStore().restore(MAIN_URL_ATTRIBUTE_NAME);
	}
}
