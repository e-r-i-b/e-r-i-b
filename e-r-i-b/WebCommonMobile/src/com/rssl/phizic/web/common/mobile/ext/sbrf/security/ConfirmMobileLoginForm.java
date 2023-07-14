package com.rssl.phizic.web.common.mobile.ext.sbrf.security;

import com.rssl.phizic.web.common.client.ext.sbrf.security.ConfirmWay4PasswordForm;

/**
 * @author Rydvanskiy
 * @ created 26.01.2011
 * @ $Author$
 * @ $Revision$
 */

public class ConfirmMobileLoginForm extends ConfirmWay4PasswordForm
{
	private boolean confirmAutoStart;

	public boolean isConfirmAutoStart()
	{
		return confirmAutoStart;
	}

	public void setConfirmAutoStart(boolean confirmAutoStart)
	{
		this.confirmAutoStart = confirmAutoStart;
	}
}
