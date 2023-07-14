package com.rssl.phizic.web.mapi.locale;

import com.rssl.phizic.web.auth.ActionFormBase;

/**
 * Форма измнения локали
 * @author komarov
 * @ created 20.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class ChangeLocaleForm extends ActionFormBase
{
	private String localeId;

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
}
