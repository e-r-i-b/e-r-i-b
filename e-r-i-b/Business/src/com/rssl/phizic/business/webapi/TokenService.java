package com.rssl.phizic.business.webapi;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.webapi.WebAPIConfig;
import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.DateHelper;
import org.hibernate.Query;

import java.util.Calendar;

/**
 * Сервис для работы с токенами WebAPI
 * @author Jatsky
 * @ created 11.04.14
 * @ $Author$
 * @ $Revision$
 */

public class TokenService
{
	private static final DatabaseServiceBase simpleService = new DatabaseServiceBase();

	public void createToken(String guid, Login login) throws BusinessException
	{
		Calendar currentDate = Calendar.getInstance();
		Token token = new Token(guid, login, currentDate);
		addOrUpdate(token);
	}

	public void addOrUpdate(Token token) throws BusinessException
	{
		try
		{
			simpleService.addOrUpdate(token, null);
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public Token findToken(final String token) throws BusinessException
	{
		try
		{
			final WebAPIConfig webAPIConfig = ConfigFactory.getConfig(WebAPIConfig.class);
			Token connector = HibernateExecutor.getInstance().execute(new HibernateAction<Token>()
			{
				public Token run(org.hibernate.Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.webapi.Token.findToken");
					query.setParameter("token", token);
					query.setParameter("life_time", DateHelper.addSeconds(Calendar.getInstance(), (-1 * webAPIConfig.getTokenLifetime())));
					Token result = (Token) query.uniqueResult();
					if (result != null)
						session.delete(result);
					return result;
				}
			});
			return connector;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
