package com.rssl.phizic.web.client.userprofile.addressbook;

import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * ‘орма редактировани€ уровн€ довери€ контакта
 *
 * @author shapin
 * @ created 08.10.14
 * @ $Author$
 * @ $Revision$
 */

public class EditContactTrustForm extends EditFormBase
{
	private boolean isTrusted;
	private boolean allowOneConfirm;
	private String error;
	private ConfirmableObject confirmableObject;
	private ConfirmStrategy confirmStrategy;

	public ConfirmableObject getConfirmableObject()
	{
		return confirmableObject;
	}

	public void setConfirmableObject(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
	}

	public boolean getTrusted()
	{
		return isTrusted;
	}

	public void setTrusted(boolean isTrusted)
	{
		this.isTrusted = isTrusted;
	}

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}

	public boolean isAllowOneConfirm()
	{
		return allowOneConfirm;
	}

	public void setAllowOneConfirm(boolean allowOneConfirm)
	{
		this.allowOneConfirm = allowOneConfirm;
	}
}
