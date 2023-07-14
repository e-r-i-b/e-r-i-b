package com.rssl.phizic.auth;

import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.auth.modes.MultiInstanceAccessPolicyService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.password.PasswordValueGenerator;
import com.rssl.phizic.security.password.UserPassword;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Calendar;

/**
 * @author Kosyakov
 * @ created 12.01.2006
 * @ $Author: jatsky $
 * @ $Revision: 62737 $
 */
public abstract class LoginGeneratorBase<L extends CommonLogin> implements LoginGenerator<L>
{
	protected static final MultiInstanceAccessPolicyService accessModeService = new MultiInstanceAccessPolicyService();

	private Calendar expireDate;
	private boolean  needChangePasword = false;
	private String csaUserId = null;

	private UserIdValueGenerator userIdValueGenerator;
	private PasswordValueGenerator passwordValueGenerator;
	protected String instanceName;

	protected LoginGeneratorBase(UserIdValueGenerator userIdValueGenerator, PasswordValueGenerator passwordValueGenerator, String instanceName)
	{
		this.passwordValueGenerator = passwordValueGenerator;
		this.userIdValueGenerator = userIdValueGenerator;
		this.instanceName = instanceName;

		expireDate = DateHelper.getCurrentDate();
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
		expireDate.add(Calendar.DATE, securityConfig.getPasswordLifeTime());
	}

	public Calendar getExpireDate()
	{
		return expireDate;
	}

	public void setExpireDate(Calendar value)
	{
		expireDate = value;
	}

	public boolean isNeedChangePasword()
	{
		return needChangePasword;
	}

	public void setNeedChangePasword(boolean needChangePasword)
	{
		this.needChangePasword = needChangePasword;
	}

	public void setCsaUserId(String csaUserId)
	{
		this.csaUserId = csaUserId;
	}

	private L generate ( final String userId, final char[] password ) throws SecurityDbException, DublicateUserIdException
    {
	    try
        {
            return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<L>()
            {
                public L run ( Session session ) throws Exception
                {
	                checkDublicates(userId);

	                LoginBase login = newInstance();

                    login.setUserId(userId);
	                if (StringHelper.isNotEmpty(csaUserId))
		                login.setCsaUserId(csaUserId);

	                session.save(login);

	                if (needSavePassword())
	                {
		                UserPassword newPassword = createPassword(login, password);
		                session.save(newPassword);
	                }

	                enableDefaultAccess(login);

	                session.flush();

	                //noinspection unchecked
	                return (L) login;
                }
            });
        }
        catch(ConstraintViolationException e)
        {
	        throw new DublicateUserIdException(userId, "admin", e);
        }
        catch (Exception e)
        {
            throw new SecurityDbException(e);
        }
    }

	protected abstract void enableDefaultAccess(CommonLogin login) throws SecurityDbException, SecurityLogicException;

	protected abstract AuthenticationConfig getAuthenticationConfig();

	private UserPassword createPassword(LoginBase login, char[] password)
	{
		UserPassword newPassword=new UserPassword();
		newPassword.setLoginId(login.getId());
		newPassword.setValue(password);
		newPassword.setValid(true);
		newPassword.setIssueDate(DateHelper.getCurrentDate());
		newPassword.setExpireDate(expireDate);
		newPassword.setNeedChange(needChangePasword);
		return newPassword;
	}

	protected abstract void checkDublicates(String userId) throws SecurityDbException, DublicateUserIdException;

	public L generate() throws SecurityDbException, DublicateUserIdException
	{
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);

		int length = securityConfig.getPasswordLength();
		String userId = userIdValueGenerator.newUserId(length);
		char[] password = passwordValueGenerator.newPassword(
				securityConfig.getPasswordLength(),
				securityConfig.getPasswordAllowedChars().toCharArray());

		return generate(userId, password);
	}

	protected abstract LoginBase newInstance ();

	protected boolean needSavePassword()
	{
		return true;
	}
}
