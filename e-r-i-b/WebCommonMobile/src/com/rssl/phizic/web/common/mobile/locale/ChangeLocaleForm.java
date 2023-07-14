package com.rssl.phizic.web.common.mobile.locale;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author komarov
 * @ created 20.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class ChangeLocaleForm extends EditFormBase
{
	public static final Form EDIT_FORM = FormBuilder.EMPTY_FORM;

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
