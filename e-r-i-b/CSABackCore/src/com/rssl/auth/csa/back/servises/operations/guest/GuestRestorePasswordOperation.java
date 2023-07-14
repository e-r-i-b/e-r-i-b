package com.rssl.auth.csa.back.servises.operations.guest;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.exceptions.IdentificationFailedException;
import com.rssl.auth.csa.back.servises.GuestProfile;
import com.rssl.auth.csa.back.servises.connectors.PasswordBasedConnector;
import com.rssl.auth.csa.back.servises.restrictions.security.CSAPasswordSecurityRestriction;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.hibernate.Session;

/**
 * @author niculichev
 * @ created 15.02.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestRestorePasswordOperation extends GuestConfirmableOperation
{
	/**
	 * Инициализиация операции
	 * @param login номер телефона
	 * @throws Exception
	 */
	public void initialize(final String login) throws Exception
	{
		final String phone = GuestProfile.findByLogin(login).getPhone();
		initialize(new HibernateAction<Object>()
		{
			public Object run(Session session) throws Exception
			{
				setPhone(phone);
				setPassword(generatePassword());
				return null;
			}
		});

		try
		{
			publishCode(phone, getPassword());
		}
		catch (Exception e)
		{
			refused(e);
			log.error("Ошибка отправки СМС кода на номер " + phone, e);
		}

	}

	public void execute(final String password) throws Exception
	{
		execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				GuestProfile profile = GuestProfile.findByPhone(getPhone());
				profile.changePassword(password);
				return null;
			}
		});
	}

	/**
	 * @return время жизни заявки
	 */
	public static int getLifeTime()
	{
		return ConfigFactory.getConfig(Config.class).getRestorePasswordTimeout();
	}
}
