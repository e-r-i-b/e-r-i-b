package com.rssl.phizic.security.password;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.dataaccess.hibernate.*;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.DateHelper;
import org.hibernate.*;

import java.util.Calendar;

/**
 * @author Kosyakov
 * @ created 11.10.2006
 * @ $Author: jatsky $
 * @ $Revision: 62507 $
 */
public abstract class PasswordChangerBase implements PasswordChanger
{
	private boolean needChangePassword = false;
	private Calendar expireDate;

	protected void setNeedChangePassword ( boolean needChangePassword )
	{
		this.needChangePassword = needChangePassword;
	}
	public void changePassword(CommonLogin login, char[] newPassword) throws SecurityDbException
	{
		changePassword(login, newPassword, null);
	}

	protected void saveNewPassword ( final CommonLogin login, final char[] newPassword, final String instanceName ) throws SecurityDbException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run ( Session session ) throws Exception
				{
					session.lock(login, LockMode.NONE);
					Query query = session.getNamedQuery("com.rssl.phizic.security.password.getPasswordByLogin");
					query.setParameter("loginId", login.getId());

					UserPassword userPassword = (UserPassword)query.uniqueResult();
					userPassword.setValue(newPassword);
					userPassword.setNeedChange(needChangePassword);
					if (expireDate!=null) userPassword.setExpireDate(expireDate);
					session.update(userPassword);
					return null;
				}
			});
		}

		catch (Exception e)

		{
			throw new SecurityDbException(e);
		}

	}

	protected void calculateExpireDate()
	{
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
		expireDate = DateHelper.getCurrentDate();
		expireDate.add(Calendar.DATE, securityConfig.getPasswordLifeTime());
	}

}