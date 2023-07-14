package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.exceptions.LoginAlreadyRegisteredException;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.Login;
import com.rssl.auth.csa.back.servises.restrictions.security.LoginMatchPasswordRestriction;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author krenev
 * @ created 15.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class ChangeLoginOperation extends SessionContextOperation
{
	public ChangeLoginOperation() {}

	public ChangeLoginOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	public Connector execute(final String login) throws Exception
	{
		try
		{
			return execute(new HibernateAction<Connector>()
			{
				public Connector run(Session session) throws Exception
				{
					Connector connector = getSessionConnector();
					doCheckLogin (login, connector);

					Login loginEntity = Login.findLoginForConnector(connector.getId());
					if(loginEntity == null)
					{
						Login.createClientLogin(login, connector.getId());
					}
					else
					{
						loginEntity.changeValue(login);
					}

					return connector;
				}
			});
		}
		catch (ConstraintViolationException e)
		{
			refused(e);
			throw new LoginAlreadyRegisteredException(e);
		}
	}

	/**
	 * Проверяем логин на валидность по ограничениям и на совпадение
	 * 1) с паролем
	 * 2) с зарегистрированными логинами(в том числе текущим).
	 * @param loginVal - новый логин клиента.
	 * @throws Exception
	 */
	private void doCheckLogin(String loginVal, Connector connector) throws Exception
	{
		if(Login.isExistLogin(loginVal))
		{
			throw new LoginAlreadyRegisteredException(loginVal + " уже зарегистрирован в системе");
		}
		LoginMatchPasswordRestriction matchPasswordRestriction = new LoginMatchPasswordRestriction(getProfileId());
		matchPasswordRestriction.check(loginVal);

		connector.getLoginRestriction().check(loginVal);
	}
}
