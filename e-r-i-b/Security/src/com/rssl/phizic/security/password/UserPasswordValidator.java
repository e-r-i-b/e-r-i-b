package com.rssl.phizic.security.password;

import com.rssl.phizic.auth.*;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.config.SecurityFactory;
import com.rssl.phizic.security.crypto.CryptoService;
import com.rssl.phizic.armour.*;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.*;

/**
 * Класс проверяет пароль пользователя на подлинность
 * @author Kidyaev
 * @ created 23.12.2005
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"JavaDoc"})
public class UserPasswordValidator implements NamePasswordValidator
{
	private static final SecurityService securityService = new SecurityService();
	private static final CryptoService cryptoService= SecurityFactory.cryptoService();

	private String scope;

	public UserPasswordValidator()
	{
		this(SecurityService.SCOPE_CLIENT);
	}

	public UserPasswordValidator(String scope)
	{
		this.scope = scope;
	}

	public CommonLogin validateLoginInfo(final String userId, final char[] password) throws SecurityLogicException, SecurityException
	{
		try
		{
		    Object result = HibernateExecutor.getInstance().execute(new HibernateAction<Object>()
		    {
		        public Object run(Session session) throws Exception
		        {
			        CommonLogin login = securityService.getLogin(userId, scope);
			        if (login == null)
				        throw new InvalidLoginException("логина с заданным userId не существует");
			        Query query = session.getNamedQuery("com.rssl.phizic.security.password.getPasswordByLogin");
			        query.setParameter("loginId", login.getId());

			        UserPassword userValue = (UserPassword) query.uniqueResult();

			        // нет пароля для переданного userId -> неверный логин
			        if (userValue == null)
				        throw new InvalidLoginException("у логина с заданным userId нет пароля");

			        session.lock(login, LockMode.UPGRADE);

			        precheck(login);

			        boolean isValid = checkValidity(userValue, password);

			        if ( !isValid )
				        return processWrongAttempt(login);
			        securityService.clearWrongAttempts(login);
			        return login;
		        }
		    });

			if(result instanceof SecurityLogicException)
				throw ((SecurityLogicException)result);

			return (CommonLogin) result;
		}
		catch(SecurityLogicException e)
		{
			throw e;
		}
		catch (SecurityException e)
		{
			throw e;
		}
		catch (ArmourException e)
		{
			throw e;
		}
		catch (Exception e)
		{
		   throw new SecurityException(e);
		}

	}

	protected boolean checkValidity(UserPassword userPassword, char[] password)
	{
		String hash = cryptoService.hash(new String(password));
		char[] hashChars = hash.toCharArray();

		return userPassword.isValid() && Arrays.equals( userPassword.getValue(), hashChars);
	}

	private PasswordValidationException processWrongAttempt(CommonLogin login) throws SecurityDbException, BlockedException
	{
		long wrongAttempts = login.getWrongLoginAttempts() + 1;
		PasswordValidationException exception;

		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
		int maxLoginAttempts = securityConfig.getLoginAttempts();
		boolean adminNeedPassword = securityConfig.isAdminNeedBlocked();
		boolean isSuperAdmin = login.getUserId().equals(securityConfig.getDefaultAdminName());

		if((wrongAttempts >= maxLoginAttempts) && ( (isSuperAdmin && adminNeedPassword) || (!isSuperAdmin)) )
		{
			if (securityConfig.getBlockedTimeout() == 0)
			{
				securityService.lockAndClear(login, null);
				Calendar blockForm = new GregorianCalendar();
				exception = new BlockedException("Неправильный ввод пароля. Доступ в систему запрещен с " + String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS", blockForm.getTime())+".");

			}
			else
			{
				Calendar blockUntil = new GregorianCalendar();
				blockUntil.add(Calendar.SECOND, securityConfig.getBlockedTimeout());
				securityService.lockAndClear(login, blockUntil.getTime());
				exception = new BlockedException("Неправильный ввод пароля. Доступ в систему запрещен до " + String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS", blockUntil.getTime())+".");
			}
		}
		else
		{
			if(!(isSuperAdmin&&!adminNeedPassword))
				securityService.addWrongAttempt(login);

			exception = new WrongAttemptException(maxLoginAttempts - wrongAttempts, !(isSuperAdmin&&!adminNeedPassword));
		}

		return exception;
	}

	private void precheck(CommonLogin login) throws BlockedException
	{
		Calendar blockUntil = new GregorianCalendar();
		List<LoginBlock> blocks = securityService.getBlocksForLogin(login, blockUntil.getTime(), null);

		if (!blocks.isEmpty())
		{
			Comparator comparator = new LoginBlocksComparator();
			Collections.sort(blocks, comparator);

			LoginBlock blockForCompare = new LoginBlock();

			blockForCompare.setReasonType(BlockingReasonType.employee);
			if (Collections.binarySearch(blocks, blockForCompare, comparator) >= 0)
			{
				String message = "Доступ в систему запрещен администратором с " + String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS",blocks.get(0).getBlockedFrom().getTime());
				if (blocks.get(0).getBlockedUntil() != null)
					message += " до " + String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS",blocks.get(0).getBlockedUntil().getTime());
				if (blocks.get(0).getReasonDescription() != null)
					message += ". Причина: " + blocks.get(0).getReasonDescription();
				throw new BlockedException(message);
			}
			blockForCompare.setReasonType(BlockingReasonType.wrongLogons);
			if (Collections.binarySearch(blocks, blockForCompare, comparator) >= 0)
			{
				String message = "Неправильный ввод пароля. Доступ в систему запрещен с " + String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS",blocks.get(0).getBlockedFrom().getTime());
				if (blocks.get(0).getBlockedUntil() != null)
					message += " до " + String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS",blocks.get(0).getBlockedUntil().getTime());
				throw new BlockedException(message +".");
			}

			blockForCompare.setReasonType(BlockingReasonType.system);
			if (Collections.binarySearch(blocks, blockForCompare, comparator) >= 0)
			{
				if (blocks.get(0).getReasonDescription() != null)
					throw new BlockedException("Доступ в систему запрещен с " + String.format("%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS",blocks.get(0).getBlockedFrom().getTime())+" Причина: "+blocks.get(0).getReasonDescription());
				else
					throw new BlockedException("Системная ошибка.");
			}
		}
	}
}
