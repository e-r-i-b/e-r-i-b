package com.rssl.phizic.csaadmin.operation;

import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.AdminLogicException;
import com.rssl.phizic.csaadmin.business.login.Login;

/**
 * @author lepihina
 * @ created 17.12.13
 * @ $Author$
 * @ $Revision$
 */
public class ChangePasswordOperation extends OpenSessionOperationBase
{
	/**
	 * »нициализаци€ операции
	 * @param login - пользователь
	 * @throws com.rssl.phizic.csaadmin.business.common.AdminException
	 * @throws com.rssl.phizic.csaadmin.business.common.AdminLogicException
	 */
	public void initialize(Login login) throws AdminException, AdminLogicException
	{
		this.login = login;
	}

	/**
	 * »зменить пароль текущему сотруднику
	 * @param newPassword - новый пароль
	 */
	public void changePassword(String newPassword) throws AdminException, AdminLogicException
	{
		loginService.selfChangePassword(login, newPassword);
	}
}
