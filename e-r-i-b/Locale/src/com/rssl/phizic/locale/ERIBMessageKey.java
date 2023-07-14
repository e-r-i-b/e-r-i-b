package com.rssl.phizic.locale;

/**
 * @author mihaylov
 * @ created 25.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Набор данных для поиска текстовки.
 * Необходим во внутренних механизмах получения текстовок.
 */
class ERIBMessageKey
{
	private String key;
	private String bundle;
	private String localeId;

	/**
	 * Конструктор
	 * @param key - ключ
	 * @param bundle - бандл
	 * @param localeId - идентификатор локали
	 */
	ERIBMessageKey(String key, String bundle, String localeId)
	{
		this.key = key;
		this.bundle = bundle;
		this.localeId = localeId;
	}

	/**
	 * @return ключ
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * @return бандл
	 */
	public String getBundle()
	{
		return bundle;
	}

	/**
	 * @return идентификатор локали
	 */
	public String getLocaleId()
	{
		return localeId;
	}
}
