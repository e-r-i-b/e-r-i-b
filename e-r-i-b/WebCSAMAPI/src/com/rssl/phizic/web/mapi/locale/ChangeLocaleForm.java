package com.rssl.phizic.web.mapi.locale;

import com.rssl.phizic.web.auth.ActionFormBase;

/**
 * ����� �������� ������
 * @author komarov
 * @ created 20.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class ChangeLocaleForm extends ActionFormBase
{
	private String localeId;

	/**
	 * @return ������������� ������
	 */
	public String getLocaleId()
	{
		return localeId;
	}

	/**
	 * @param localeId ������������� ������
	 */
	public void setLocaleId(String localeId)
	{
		this.localeId = localeId;
	}
}
