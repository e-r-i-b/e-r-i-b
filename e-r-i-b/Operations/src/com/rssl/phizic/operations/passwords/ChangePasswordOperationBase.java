package com.rssl.phizic.operations.passwords;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.security.password.PasswordValidationException;

/**
 * ������� ����� ��� �������� ����� ������
 * @author Kidyaev
 * @ created 15.02.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 3093 $
 */
public abstract class ChangePasswordOperationBase extends OperationBase
{
	protected static final SecurityService securityService = new SecurityService();

	private   CommonLogin  login;
	private   String       newPassword;

	public CommonLogin getLogin()
	{
		return login;
	}

	protected void initialize(CommonLogin login)
	{
		this.login = login;
	}

	protected String getNewPassword()
	{
		return newPassword;
	}

	public void setNewPassword(String newPassword)
	{
		this.newPassword = newPassword;
	}

	/**
	 * ��������� ������ ������������
	 */
	public abstract void changePassword() throws BusinessException, PasswordValidationException;
}
