package com.rssl.phizgate.common.providers;

import java.util.Calendar;

/**
 * Свойства поставщика
 * @author gladishev
 * @ created 14.01.2015
 * @ $Author$
 * @ $Revision$
 */

public class ProviderPropertiesEntry
{
	private long providerId;
	private boolean mbCheckEnabled;
	private boolean mcheckoutDefaultEnabled;
	private boolean einvoiceDefaultEnabled;
	private boolean mbCheckDefaultEnabled;
	private Calendar updateDate;
	private boolean useESB;

	/**
	 * Идентификатор поставщика услуг
	 * @return
	 */
	public long getProviderId()
	{
		return providerId;
	}

	/**
	 * Установить идентификатор поставщика услуг
	 * @param providerId - идентификатор поставщика услуг
	 */
	public void setProviderId(long providerId)
	{
		this.providerId = providerId;
	}

	/**
	 * @return дата обновления
	 */
	public Calendar getUpdateDate()
	{
		return updateDate;
	}

	/**
	 * Установить дату обновления
	 * @param updateDate - дата обновления
	 */
	public void setUpdateDate(Calendar updateDate)
	{
		this.updateDate = updateDate;
	}

	/**
	 * @return значение признака доступность по-умолчанию mobile checkout для добавляемых КПУ
	 */
	public boolean isMbCheckDefaultEnabled()
	{
		return mbCheckDefaultEnabled;
	}

	/**
	 * Установить значение признака доступность по-умолчанию mobile checkout для добавляемых КПУ
	 * @param mbCheckDefaultEnabled - значение признака доступность по-умолчанию mobile checkout для добавляемых КПУ
	 */
	public void setMbCheckDefaultEnabled(boolean mbCheckDefaultEnabled)
	{
		this.mbCheckDefaultEnabled = mbCheckDefaultEnabled;
	}

	/**
	 * @return значение признака доступность по-умолчанию einvoicing для добавляемых КПУ
	 */
	public boolean isEinvoiceDefaultEnabled()
	{
		return einvoiceDefaultEnabled;
	}

	/**
	 * Установить значение признака доступность по-умолчанию einvoicing для добавляемых КПУ
	 * @param einvoiceDefaultEnabled - значение признака доступность по-умолчанию einvoicing для добавляемых КПУ
	 */
	public void setEinvoiceDefaultEnabled(boolean einvoiceDefaultEnabled)
	{
		this.einvoiceDefaultEnabled = einvoiceDefaultEnabled;
	}

	/**
	 * @return значение признака доступность по-умолчанию проверки МБ для добавляемых КПУ
	 */
	public boolean isMcheckoutDefaultEnabled()
	{
		return mcheckoutDefaultEnabled;
	}

	/**
	 * Установить значение признака "доступность по-умолчанию проверки МБ для добавляемых КПУ"
	 * @param mcheckoutDefaultEnabled
	 */
	public void setMcheckoutDefaultEnabled(boolean mcheckoutDefaultEnabled)
	{
		this.mcheckoutDefaultEnabled = mcheckoutDefaultEnabled;
	}

	/**
	 * @return значение признака "поддержка проверки МБ"
	 */
	public boolean isMbCheckEnabled()
	{
		return mbCheckEnabled;
	}

	/**
	 * Установить признак "поддержка проверки МБ"
	 * @param mbCheckEnabled - значение признака
	 */
	public void setMbCheckEnabled(boolean mbCheckEnabled)
	{
		this.mbCheckEnabled = mbCheckEnabled;
	}

	/**
	 * Возвращает значение признака "Работа через КСШ"
	 * @return значение признака
	 */
	public boolean isUseESB()
	{
		return useESB;
	}

	/**
	 * Установить признак "Работа через КСШ"
	 * @param useESB - значение признака
	 */
	public void setUseESB(boolean useESB)
	{
		this.useESB = useESB;
	}
}
