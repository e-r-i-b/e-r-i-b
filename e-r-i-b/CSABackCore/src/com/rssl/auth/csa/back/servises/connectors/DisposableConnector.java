package com.rssl.auth.csa.back.servises.connectors;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.exceptions.RestrictionException;
import com.rssl.auth.csa.back.servises.*;
import com.rssl.auth.csa.back.servises.restrictions.NotEmptyStringRestriction;
import com.rssl.auth.csa.back.servises.restrictions.Restriction;
import com.rssl.phizic.common.types.registration.RegistrationType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.LockMode;

import java.util.List;
import java.util.Set;

/**
 * Временный коннектор
 * @author Jatsky
 * @ created 16.12.13
 * @ $Author$
 * @ $Revision$
 */

public class DisposableConnector extends PasswordBasedConnector
{
	private String disposablePass;

	public DisposableConnector()
	{
	}

	public DisposableConnector(String userId, String cbCode, String cardNumber, Profile profile, RegistrationType registrationType) throws RestrictionException
	{
		if (StringHelper.isEmpty(userId))
		{
			throw new IllegalArgumentException("Логин iPas не может быть null");
		}
		if (StringHelper.isEmpty(cbCode))
		{
			throw new IllegalArgumentException("cbCode не может быть null");
		}
		if (StringHelper.isEmpty(cardNumber))
		{
			throw new IllegalArgumentException("Номер карты не может быть null");
		}
		if (profile == null)
		{
			throw new IllegalArgumentException("Профиль не может быть null");
		}
		if (registrationType == null)
		{
			throw new IllegalArgumentException("Тип регистрации не может быть null");
		}
		setGuid(Utils.generateGUID());
		setState(ConnectorState.ACTIVE);
		setUserId(userId);
		setProfile(profile);
		setCbCode(cbCode);
		setCardNumber(cardNumber);
		setRegistrationType(registrationType);
		setSecurityType(SecurityType.LOW);
	}

	@Override public ConnectorType getType()
	{
		return ConnectorType.DISPOSABLE;
	}

	@Override public boolean isMigrated()
	{
		return false;
	}

	@Override public void generatePassword(Set<String> excludedPhones) throws Exception
	{
		throw new UnsupportedOperationException();
	}

	public void generateDisposablePassword(boolean sendSMS) throws Exception
	{
		disposablePass = new InternalPasswordGenerator(getGeneratedPasswordAllowedChars(), getGeneratedPasswordLength(), this).generateDisposablePassword(sendSMS);
	}

	/**
	 * Получить список незакрытых ЦСА-коннекторов по идентфикатору профиля
	 * @param profileId идентифкатор профиля
	 * @return список незакрытых ЦСА-конекторов или пустой список
	 * @throws Exception
	 */
	public static DisposableConnector findNotClosedByProfileId(final Long profileId) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("Идентификатор профиля не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<DisposableConnector>()
		{
			public DisposableConnector run(org.hibernate.Session session) throws Exception
			{
				return (DisposableConnector) session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.DisposableConnector.getNotClosedByProfileId")
						.setParameter("profile_id", profileId)
						.uniqueResult();
			}
		});
	}

	/**
	 * Получить список незакрытых ЦСА-коннекторов по идентфикатору профиля
	 * @param profileId идентифкатор профиля
	 * @return список незакрытых ЦСА-конекторов или пустой список
	 * @throws Exception
	 */
	public static DisposableConnector findByProfileId(final Long profileId) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("Идентификатор профиля не может быть null");
		}
		return getHibernateExecutor().execute(new HibernateAction<DisposableConnector>()
		{
			public DisposableConnector run(org.hibernate.Session session) throws Exception
			{
				return (DisposableConnector) session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.DisposableConnector.getByProfileId")
						.setParameter("profile_id", profileId)
						.uniqueResult();
			}
		});
	}

	/**
	 * Найти незакрытый коннектор на доступ в вебверсию по временному логину
	 * @param userId логин Ipas
	 * @return коннектор или null
	 * @throws Exception
	 */
	public static DisposableConnector findNotClosedByUserId(final String userId) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<DisposableConnector>()
		{
			public DisposableConnector run(org.hibernate.Session session) throws Exception
			{
				return (DisposableConnector) session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.DisposableConnector.getNotClosedByUserID")
						.setParameter("user_id", userId)
						.uniqueResult();
			}
		});
	}

	/**
	 * Найти незакрытый коннектор на доступ в вебверсию по временному логину
	 * @param login логин
	 * @return коннектор или null
	 * @throws Exception
	 */
	public static DisposableConnector findNotClosedByLogin(final String login) throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<DisposableConnector>()
		{
			public DisposableConnector run(org.hibernate.Session session) throws Exception
			{
				return (DisposableConnector) session.getNamedQuery("com.rssl.auth.csa.back.servises.connectors.DisposableConnector.getNotClosedByLogin")
						.setParameter("login", login)
						.uniqueResult();
			}
		});
	}

	public void generateDisposableLogin() throws Exception
	{
		new InternalPasswordGenerator("0123456789", 10, this).generateDisposableLogin();
	}

	public String getDisposablePass()
	{
		return disposablePass;
	}

	public void setDisposablePass(String disposablePass)
	{
		this.disposablePass = disposablePass;
	}

	public static DisposableConnector generateDisposableConnector(String userId, String cbCode, String cardNumber, Profile profile, RegistrationType registrationType, boolean sendSMS) throws Exception
	{
		return new InternalPasswordGenerator(getGeneratedPasswordAllowedChars(), getGeneratedPasswordLength(), null).generateDisposableConnector(userId, cbCode, cardNumber, profile, registrationType, sendSMS);
	}

	protected boolean setPassword(final String passwordValue) throws Exception
	{
		return executeAtomic(new HibernateAction<Boolean>()
		{
			public Boolean run(org.hibernate.Session session) throws Exception
			{
				session.lock(DisposableConnector.this, LockMode.UPGRADE_NOWAIT);
				boolean isChanged = false;
				Password prevPassword = getPassword();
				if (prevPassword != null)
				{
					isChanged = !prevPassword.check(passwordValue);
					prevPassword.delete();
				}
				Password password = new Password(passwordValue);
				password.setConnector(DisposableConnector.this);
				password.save();
				return isChanged;
			}
		}
		);
	}

	@Override protected Password getPassword() throws Exception
	{
		return Password.findActiveByConector(this);
	}

	@Override public Restriction<String> getPasswordRestriction()
	{
		return new NotEmptyStringRestriction("Пароль не может быть пустым");
	}

	/**
	 * @return множество допустимых символов сгененрированного пароля для доступа в ЕРИБ
	 */
	public static String getGeneratedPasswordAllowedChars()
	{
		return ConfigFactory.getConfig(Config.class).getGeneratedPasswordAllowedChars();
	}

	/**
	 * @return длина сгенерированного пароля для доступа в ЕРИБ
	 */
	public static int getGeneratedPasswordLength()
	{
		return ConfigFactory.getConfig(Config.class).getGeneratedPasswordLength();
	}

	@Override
	protected String getLogSecurityTypeMessage()
	{
		return SecurityTypeHelper.LOW_SECURITY_TYPE_DESCRIPTION_DISPOSABLE;
	}

	/**
	 * Получить список незакрытых коннекторов по идентфикатору профиля
	 * @param profileId идентифкатор профиля
	 * @return список незакрытых конекторов или пустой список
	 * @throws Exception
	 */
	public static List<DisposableConnector> findNotClosedByProfileID(Long profileId) throws Exception
	{
		return findNotClosedByProfileID(profileId, DisposableConnector.class);
	}
}
