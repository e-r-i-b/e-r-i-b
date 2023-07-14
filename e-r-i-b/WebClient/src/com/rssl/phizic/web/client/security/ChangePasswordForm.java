package com.rssl.phizic.web.client.security;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Kidyaev
 * @ created 22.12.2005
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"JavaDoc"})
public class ChangePasswordForm extends ActionFormBase
{
	private String oldPassword;       // старый пароль
	private String newPassword;       // новый пароль
    private String confirmNewPassword;// подтверждение нового пароля

	public String getOldPassword()
	{
		return oldPassword;
	}

	public void setOldPassword(String oldPassword)
	{
		this.oldPassword = oldPassword;
	}

	public String getNewPassword()
	{
		return newPassword;
	}

    public void setNewPassword(String newPassword)
	{
		this.newPassword = newPassword;
	}

    public String getConfirmNewPassword()
	{
		return confirmNewPassword;
	}

    public void setConfirmNewPassword(String confirmNewPassword)
	{
		this.confirmNewPassword = confirmNewPassword;
	}
}
