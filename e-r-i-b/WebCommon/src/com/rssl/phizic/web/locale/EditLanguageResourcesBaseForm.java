package com.rssl.phizic.web.locale;

import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.web.common.FilterActionForm;

/**
 * @author koptyaev
 * @ created 30.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditLanguageResourcesBaseForm extends FilterActionForm
{
	private String localeId;
	private ERIBLocale locale;
	private Long id;
	private String stringId;

	/**
	 * @return текущая локаль
	 */
	@SuppressWarnings("UnusedDeclaration")
	public ERIBLocale getLocale()
	{
		return locale;
	}

	/**
	 * Получить текущую локать
	 * @param locale локаль
	 */
	public void setLocale(ERIBLocale locale)
	{
		this.locale = locale;
	}

	/**
	 * @return идентификатор локали
	 */
	public String getLocaleId()
	{
		return localeId;
	}

	/**
	 * @param localeId идентификатор локали
	 */
	public void setLocaleId(String localeId)
	{
		this.localeId = localeId;
	}

	/**
	 * @return идентификатор сущности
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Установить идентификатор сущности
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return строковый идентификатор сущности
	 */
	public String getStringId()
	{
		return stringId;
	}

	/**
	 * Установить строковый идентификатор сущности
	 * @param stringId строковый идентификатор
	 */
	public void setStringId(String stringId)
	{
		this.stringId = stringId;
	}
}
