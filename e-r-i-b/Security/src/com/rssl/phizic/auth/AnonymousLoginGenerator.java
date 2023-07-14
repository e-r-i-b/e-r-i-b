package com.rssl.phizic.auth;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.security.SecurityDbException;
import org.hibernate.Session;

/**
 * @author Evgrafov
 * @ created 13.08.2007
 * @ $Author: gladishev $
 * @ $Revision: 8259 $
 */

public class AnonymousLoginGenerator implements LoginGenerator
{
	private String userId;
	private static final SecurityService securityService = new SecurityService();

	/**
	 * ctor
	 * @param userId user_id анонимного пользавателя
	 */
	public AnonymousLoginGenerator(String userId) 
	{
		this.userId = userId;
	}

	public Login generate() throws SecurityDbException, DublicateUserIdException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<Login>()
		    {
		        public Login run(Session session) throws Exception
		        {

			        Login login = securityService.getClientLogin(userId);

			        if(login != null)
			            return login;

					LoginImpl newLogin = new LoginImpl();
					newLogin.setUserId(userId);
					session.save(newLogin);

			        return newLogin;

		        }
		    });
		}
		catch (Exception e)
		{
		   throw new SecurityDbException(e);
		}
	}
}