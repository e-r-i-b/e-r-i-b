package com.rssl.phizic.business.persons;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Session;

/**
 * @author  Balovtsev
 * @created 15.09.14.
 */
public class UserServiceInfoService
{
	/**
	 * »щет служебную информацию пользовател€ по логину
	 *
	 * @param loginId логин пользовател€
	 * @return UserServiceInfo
	 */
	public UserServiceInfo findByLoginId(final Long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<UserServiceInfo>()
			{
				public UserServiceInfo run(Session session) throws Exception
				{
					return (UserServiceInfo) session.getNamedQuery("com.rssl.phizic.business.persons.UserServiceInfoService.findByLoginId")
						                            .setParameter("loginId", loginId)
						                            .uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
