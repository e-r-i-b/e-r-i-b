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
 * ������ ������ � ������������ ��������
 */
public final class SmsPasswordService
{
	private static final int MAX_ATTEMPT_COUNT = 10000;

	private SmsPasswordService(){}

	/**
	 * @return ��������� �������� ������������ ������
	 * @throws SecurityDbException
	 */
	public static String generatePassword() throws SecurityDbException
	{
		PasswordStrategyValidator validator = new PasswordStrategyValidator();
		validator.setParameter(PasswordStrategyValidator.STRATEGY_NAME, "client-one-time");
		String password = null;
		// �������� �������� ������
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
				//������ ��� ��� �������� ���������
			}
		}

		throw new SecurityDbException("�� ������� ��������� ������");
	}

	private static CryptoService getCrypto()
	{
		return SecurityFactory.cryptoService();
	}

	/**
	 * �������� ������
	 * @param password ������(����������������)
	 * @param confirmableObject ������ ��� �������� ���������

	 * @return ��������� ������
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
	 * ���������� ��� � ���-������ � ����� ����������� ������
	 * @param smsPassword - ������
	 * @param userPassword - ���������������� ��������
	 * @return �� ��� ���
	 */
	public static boolean checkPassword(OneTimePassword smsPassword, String userPassword)
	{
		return smsPassword.getHash().equals(getHash(userPassword));
	}

	/**
	 * @return ���������� �������
	 */
	public static int getWrongAttemptsCount()
	{
		return ConfigFactory.getConfig(SmsPasswordConfig.class).getSmsPasswordConfirmAttempts();
	}

	/**
	 * @return ����� �����
	 */
	public static int getLifeTime()
	{
		return ConfigFactory.getConfig(SmsPasswordConfig.class).getSmsPasswordLifeTime();
	}

	/**
	 * ��������� ���������� ���������� ������������� ��������
	 * @param userPassword - ���������������� ��������
	 * @param phone - ����� ��������
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
	 * ���������� �������� sms-������
	 *
	 * @param phone ����� ��������
	 * @return sms-������ ��� null
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
	 * ���������� ��� � ���-������ � ����� ����������� ������
	 * @param smsPassword - ������
	 * @param userPassword - ���������������� ��������
	 * @return �� ��� ���
	 */
	@Deprecated
	public static boolean checkPassword(OneTimePasswordERMB smsPassword, String userPassword)
	{
		return smsPassword.getHash().equals(getHash(userPassword));
	}

	/**
	 * �������� ������
	 * @param password ������(����������������)
	 * @param expiredDate ����� �����
	 * @return ��������� ������
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
	 * ������� ������, ���������� ���������� � ���� � ���������� ���������������� ������
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
