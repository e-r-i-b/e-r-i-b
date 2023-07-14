package com.rssl.phizic.web.security;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.persons.LoginHelper;
import org.apache.struts.action.ActionMessage;

/**
 * @author Gainanov
 * @ created 28.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class LoginMessagesHelper
{
	/**
	 * получить сообщение о том, что пользователь заблокирован
	 * @param userId логин клиента
	 * @return сообщение
	 */
	@Deprecated
	public static ActionMessage getLoginBlockMessage(String userId)
	{
		ActionMessage mess = null;
		try
		{
			SecurityService securityService = new SecurityService();
			return getLoginBlockMessage(securityService.getClientLogin(userId));
		}
		catch (Exception e)
		{
			mess = new ActionMessage("error.login.permanent.blocked.message");
		}
		return mess;
	}

	/**
	 * получить сообщение о том, что пользователь заблокирован
	 * @param login логин клиента
	 * @return сообщение
	 */
	public static ActionMessage getLoginBlockMessage(Login login)
	{
		ActionMessage mess = null;
		try
		{
			Department department = LoginHelper.getDepartmentByLogin(login);
			mess = new ActionMessage("error.login.permanent.blocked.message", department.getTelephone());
		}
		catch (Exception e)
		{
			mess = new ActionMessage("error.login.permanent.blocked.message");
		}
		return mess;
	}
}
