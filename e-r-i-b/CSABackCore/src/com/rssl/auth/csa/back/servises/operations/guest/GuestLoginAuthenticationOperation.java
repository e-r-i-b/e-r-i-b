package com.rssl.auth.csa.back.servises.operations.guest;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.exceptions.GuestAuthenticationFailedException;
import com.rssl.auth.csa.back.servises.*;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.utils.DateHelper;
import org.hibernate.LockMode;
import org.hibernate.Session;

import java.util.Calendar;

/**
 * Операция аутентификации неклиента
 * @author niculichev
 * @ created 15.01.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestLoginAuthenticationOperation extends GuestOperation
{
	/**
	 * Инициализация операции
	 * @param userId логин
	 * @throws Exception
	 */
	public void initialize(String userId) throws Exception
	{
		final Login login = Login.findBylogin(userId);
		final GuestProfile profile = GuestProfile.findById(login.getGuestId(), null);

		fillLogContext(profile, userId);

		initialize(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				setPhone(profile.getPhone());
				return null;
			}
		});
	}

	/**
	 * Выполнение аутентификации
	 * @param password пароль
	 * @throws Exception
	 */
	public void execute(final String password) throws Exception
	{
		final GuestProfile profile = GuestProfile.findByPhone(getPhone());
		execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				if(!checkPassword(password, profile))
					throw new GuestAuthenticationFailedException(profile);

				return null;
			}
		});
	}

	private void fillLogContext(GuestProfile profile, String login)
	{
		LogThreadContext.setGuestCode(profile.getCode());
		LogThreadContext.setGuestPhoneNumber(profile.getPhone());
		LogThreadContext.setFirstName(profile.getFirstname());
		LogThreadContext.setSurName(profile.getSurname());
		LogThreadContext.setPatrName(profile.getPatrname());
		LogThreadContext.setDepartmentCode(profile.getTb());
		LogThreadContext.setBirthday(profile.getBirthdate());
		LogThreadContext.setNumber(profile.getPassport());
		LogThreadContext.setLogin(login);
		LogThreadContext.setDepartmentRegion(profile.getTb());
	}

	private boolean checkPassword(final String password, final GuestProfile profile) throws Exception
	{
		return ActiveRecord.executeIsolated(new HibernateAction<Boolean>()
		{
			public Boolean run(Session session) throws Exception
			{
				session.lock(profile, LockMode.UPGRADE_NOWAIT);
				if(profile.checkBlock())
				{
					profile.setBlock(true);
					return false;
				}

				// не заблокирован - сбрасываем
				profile.setBlockedUntil(null);

				GuestPassword activePassword = GuestPassword.findActiveByProfileId(profile.getId());
				boolean result = activePassword.check(password);
				if(!result)
				{
					profile.incrementAuthErrors();
					if(profile.getAuthErrors() >= ConfigFactory.getConfig(Config.class).getMaxAuthenticationFailed())
					{
						Calendar startBlockDate = getCurrentDate();
						int blockingTimeOut = ConfigFactory.getConfig(Config.class).getAuthenticationFailedBlockingTimeOut();

						profile.setBlockedUntil(DateHelper.addSeconds(startBlockDate, blockingTimeOut));
						profile.clearAuthErrors();
						profile.setBlock(true);
					}
				}
				else
					profile.clearAuthErrors();

				profile.save();
				return result;
			}
		});
	}
}
