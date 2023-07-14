package com.rssl.phizic.config.locale;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author mihaylov
 * @ created 19.09.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��� ���������������. ������ ������������� ������� ������.
 */
public class MultiLocaleContext
{
	public static final String LOCALE_KEY = MultiLocaleContext.class.getName().toUpperCase() + ".LOCALE_KEY";
	private static ThreadLocal<String> localeId = new ThreadLocal<String>();

	/**
	 * @param locale ������������� ������
	 */
	public static void setLocaleId(String locale)
	{
		localeId.set(locale);
	}

	/**
	 * @return ������������� ������
	 */
	public static String getLocaleId()
	{
		String currentLocaleId = localeId.get();
		if(StringHelper.isEmpty(currentLocaleId))
			return ConfigFactory.getConfig(ERIBLocaleConfig.class).getDefaultLocaleId();
		return currentLocaleId;
	}

	/**
	 * @return ��������� �� ������� ������ � ���������
	 */
	public static boolean isDefaultLocale()
	{
		String currentLocaleId = localeId.get();
		String defaultLocaleId = ConfigFactory.getConfig(ERIBLocaleConfig.class).getDefaultLocaleId();
		return StringHelper.isEmpty(currentLocaleId) || currentLocaleId.equals(defaultLocaleId);
	}

}
