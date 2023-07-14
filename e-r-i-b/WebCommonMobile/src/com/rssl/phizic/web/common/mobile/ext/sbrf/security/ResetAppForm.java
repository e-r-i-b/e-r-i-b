package com.rssl.phizic.web.common.mobile.ext.sbrf.security;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * форма сброса регистрации мобильного приложения
 * @author Pankin
 * @ created 27.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class ResetAppForm extends ActionFormBase
{
	private String mguid;

	public String getMguid()
	{
		return mguid;
	}

	public void setMguid(String mGUID)
	{
		this.mguid = mGUID;
	}
}
