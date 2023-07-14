package com.rssl.phizic.auth;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.password.UserPassword;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Evgrafov
 * @ created 28.12.2005
 * @ $Author: jatsky $
 * @ $Revision: 62507 $
 */

public class LoginInfoProviderImpl implements LoginInfoProvider
{
	protected UserPassword getUserPassword ( final CommonLogin login) throws SecurityDbException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<UserPassword>()
			{
				public UserPassword run ( Session session ) throws Exception
				{
					Query query=session.getNamedQuery("com.rssl.phizic.security.password.getPasswordByLogin");
					query.setParameter("loginId", login.getId());
					return (UserPassword)query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	public boolean needChangePassword (CommonLogin login ) throws SecurityDbException
	{
		UserPassword userPassword=getUserPassword(login);
		return userPassword.getNeedChange();
	}
}
