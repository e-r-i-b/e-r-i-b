package com.rssl.phizic.gate.einvoicing;

/**
 * конечный поставщик услуг (поставщик, стоящий за фасилитатором)
 * @author gladishev
 * @ created 24.12.2014
 * @ $Author$
 * @ $Revision$
 */

public class FacilitatorProvider
{
	private Long id;
	private String facilitatorCode;
	private String code;
	private String name;
	private String inn;
	private String url;
	private boolean deleted;
	private boolean mobileCheckoutEnabled;
	private boolean einvoiceEnabled;
	private boolean mbCheckEnabled;

	/**
	 * @return идентификатор записи
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Установить идентификатор записи
	 * @param id - идентификатор записи
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return код фасилитатора
	 */
	public String getFacilitatorCode()
	{
		return facilitatorCode;
	}

	/**
	 * Установить код фасилитатора
	 * @param facilitatorCode - код фасилитатора
	 */
	public void setFacilitatorCode(String facilitatorCode)
	{
		this.facilitatorCode = facilitatorCode;
	}

	/**
	 * @return код поставщика услуг
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * Установить код поставщика услуг
	 * @param code - код поставщика услуг
	 */
	public void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * @return Наименование поставщика
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Установить наименование поставщика
	 * @param name - наименование поставщика
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ИНН поставщика
	 */
	public String getInn()
	{
		return inn;
	}

	/**
	 * Установить ИНН поставщика
	 * @param inn - ИНН поставщика
	 */
	public void setInn(String inn)
	{
		this.inn = inn;
	}

	/**
	 * @return урл поставщика
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * Установить урл поставщика
	 * @param url - урл поставщика
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}

	/**
	 * @return true - поставщик удален
	 */
	public boolean isDeleted()
	{
		return deleted;
	}

	/**
	 * Установить признак удаленного поставщика
	 * @param deleted - признак удаленного поставщика
	 */
	public void setDeleted(boolean deleted)
	{
		this.deleted = deleted;
	}

	/**
	 * @return доступность по mobile-checkout
	 */
	public boolean isMobileCheckoutEnabled()
	{
		return mobileCheckoutEnabled;
	}

	/**
	 * Установить признак доступности по mobile-checkout
	 * @param mobileCheckoutEnabled - признак доступности по mobile-checkout
	 */
	public void setMobileCheckoutEnabled(boolean mobileCheckoutEnabled)
	{
		this.mobileCheckoutEnabled = mobileCheckoutEnabled;
	}

	/**
	 * @return доступность по e-invoicing
	 */
	public boolean isEinvoiceEnabled()
	{
		return einvoiceEnabled;
	}

	/**
	 * Установить признак доступности по e-invoicing
	 * @param einvoiceEnabled - признак доступности по e-invoicing
	 */
	public void setEinvoiceEnabled(boolean einvoiceEnabled)
	{
		this.einvoiceEnabled = einvoiceEnabled;
	}

	/**
	 * @return доступность для проверки в МБ
	 */
	public boolean isMbCheckEnabled()
	{
		return mbCheckEnabled;
	}

	/**
	 * Установить признак доступности для проверки в МБ
	 * @param mbCheckEnabled - признак доступности
	 */
	public void setMbCheckEnabled(boolean mbCheckEnabled)
	{
		this.mbCheckEnabled = mbCheckEnabled;
	}
}
