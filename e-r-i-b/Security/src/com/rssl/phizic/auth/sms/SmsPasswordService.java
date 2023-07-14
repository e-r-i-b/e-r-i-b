package com.rssl.phizic.auth.sms;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.passwords.PasswordStrategyValidator;
import com.rssl.common.forms.validators.passwords.SmsPasswordConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.config.SecurityFactory;
import com.rssl.phizic.security.crypto.CryptoService;
import com.rssl.phizic.security.password.OneTimePassword;
import com.rssl.phizic.security.password.OneTimePasswordERMB;
import com.rssl.phizic.utils.RandomHelper;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Calendar;

/**
 * @author eMakarov
 * @ created 17.06.2008
 * @ $Author$
 * @ $Revision$
 *
 * Сервис работы с одноразовыми паролями
 */
public final class SmsPasswordService
{
	private static final int MAX_ATTEMPT_COUNT = 10000;

	private SmsPasswordService(){}

	/**
	 * @return случайное значение одноразового пароля
	 * @throws SecurityDbException
	 */
	public static String generatePassword() throws SecurityDbException
	{
		PasswordStrategyValidator validator = new PasswordStrategyValidator();
		validator.setParameter(PasswordStrategyValidator.STRATEGY_NAME, "client-one-time");
		String password = null;
		// контроль качества пароля
		int length = ConfigFactory.getConfig(SmsPasswordConfig.class).getSmsPasswordLength();
		String allowedChars = ConfigFactory.getConfig(SmsPasswordConfig.class).getSmsPasswordAllowedChars();
		for(int i = 0; i< MAX_ATTEMPT_COUNT; i++)
		{
			try
			{
				password = RandomHelper.rand(length, allowedChars);
				if(validator.validate(password))
					return password;
			}
			catch (TemporalDocumentException ignore)
			{
				//просто еще раз пытаемся сгенерить
			}
		}

		throw new SecurityDbException("Не удалось сгенерить пароль");
	}

	private static CryptoService getCrypto()
	{
		return SecurityFactory.cryptoService();
	}

	/**
	 * Созадать пароль
	 * @param password пароль(незахешированный)
	 * @param confirmableObject объект для которого создается

	 * @return созданный пароль
	 */
	public static OneTimePassword createSmsPassword(String password, ConfirmableObject confirmableObject)
	{
		String   hash = getHash(password);
		Calendar expiredDate = Calendar.getInstance();
		expiredDate.add(Calendar.SECOND, getLifeTime());

		return new OneTimePassword(expiredDate, hash, confirmableObject.getClass().getName(), confirmableObject.getId());
	}

	private static String getHash(String toHash)
	{
		return getCrypto().hash(toHash);
	}

	/**
	 * сравнивает хэш в смс-пароле с хэшем переданного пароля
	 * @param smsPassword - объект
	 * @param userPassword - пользовательское значение
	 * @return да или нет
	 */
	public static boolean checkPassword(OneTimePassword smsPassword, String userPassword)
	{
		return smsPassword.getHash().equals(getHash(userPassword));
	}

	/**
	 * @return количество попыток
	 */
	public static int getWrongAttemptsCount()
	{
		return ConfigFactory.getConfig(SmsPasswordConfig.class).getSmsPasswordConfirmAttempts();
	}

	/**
	 * @return время жизни
	 */
	public static int getLifeTime()
	{
		return ConfigFactory.getConfig(SmsPasswordConfig.class).getSmsPasswordLifeTime();
	}

	/**
	 * проверяет валидность введенного пользователем значения
	 * @param userPassword - пользовательское значение
	 * @param phone - номер телефона
	 * @return
	 * @throws SecurityDbException
	 */
	@Deprecated
	public static boolean checkPasswordOfflineDoc(String userPassword, final String phone) throws SecurityDbException
	{
		OneTimePasswordERMB smsPassword = getSmsPassword(phone);

		if (smsPassword != null)
			return checkPassword(smsPassword, userPassword);
		else
			return false;
	}

	/**
	 * Возвращает активный sms-пароль
	 *
	 * @param phone номер телефона
	 * @return sms-пароль или null
	 * @throws com.rssl.phizic.security.SecurityDbException
	 */
	@Deprecated
	public static OneTimePasswordERMB getSmsPassword(final String phone) throws SecurityDbException
	{
		try
		{
			return HibernateExecutor.getInstance("OfflineDoc").execute(new HibernateAction<OneTimePasswordERMB>()
			{
				public OneTimePasswordERMB run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.auth.sms.getPasswordByLoginERMB");
					query.setParameter("currentDate", Calendar.getInstance());
					query.setParameter("phone", phone);
					query.setMaxResults(1);
					return (OneTimePasswordERMB) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * сравнивает хэш в смс-пароле с хэшем переданного пароля
	 * @param smsPassword - объект
	 * @param userPassword - пользовательское значение
	 * @return да или нет
	 */
	@Deprecated
	public static boolean checkPassword(OneTimePasswordERMB smsPassword, String userPassword)
	{
		return smsPassword.getHash().equals(getHash(userPassword));
	}

	/**
	 * Созадать пароль
	 * @param password пароль(незахешированный)
	 * @param expiredDate время жизни
	 * @return созданный пароль
	 */
	@Deprecated
	private static OneTimePasswordERMB createSmsPassword(String password, Calendar expiredDate, String phone)
	{
		String   hash = getHash(password);
		Calendar issueDate = Calendar.getInstance();
		if (expiredDate == null)
		{
			expiredDate = Calendar.getInstance();
			expiredDate.add(Calendar.SECOND, getLifeTime());
		}

		OneTimePasswordERMB smsPassword = new OneTimePasswordERMB(issueDate, expiredDate, hash, phone);
		return smsPassword;
	}

	/*
	 * создает пароль, записывает информацию в базу и возвращает незахэшированный пароль
	 */
	@Deprecated
	public static String createSmsPassword(final Calendar expiredDate, final String phone) throws SecurityDbException
	{
		try
		{
			return HibernateExecutor.getInstance("OfflineDoc").execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					String password = generatePassword();
					OneTimePasswordERMB smsPassword = createSmsPassword(password, expiredDate, phone);
					session.saveOrUpdate(smsPassword);
					return password;
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}
}
