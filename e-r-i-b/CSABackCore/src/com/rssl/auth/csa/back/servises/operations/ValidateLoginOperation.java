package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.exceptions.IllegalOperationStateException;
import com.rssl.auth.csa.back.exceptions.InvalidSessionException;
import com.rssl.auth.csa.back.exceptions.LoginAlreadyRegisteredException;
import com.rssl.auth.csa.back.exceptions.OperationNotFoundException;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.auth.csa.back.servises.OperationState;
import com.rssl.auth.csa.back.servises.Session;
import com.rssl.auth.csa.back.servises.restrictions.security.LoginMatchPasswordRestriction;
import com.rssl.auth.csa.back.servises.restrictions.security.LoginSecurityRestriction;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

/**
 * @author krenev
 * @ created 28.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class ValidateLoginOperation extends Operation
{
	private static final String SID_PARAM = "sid";
	private static final String OUID_PARAM = "ouid";

	public ValidateLoginOperation() {}

	public ValidateLoginOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	/**
	 * »дентификации операции в разрезе сессии
	 * @param sid идентификатор сессии
	 * @throws Exception
	 */
	public void initializeBySID(final String sid) throws Exception
	{
		// если сессии нет или она не корректна, отказываем в проверке
		Session session = Session.findBySid(sid);
		if(session == null)
			throw new InvalidSessionException("—есси€ " + sid + " не найдена");

		if(!session.isValid())
			throw new InvalidSessionException("—есси€ " + sid + " невалидна");

		initialize(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session session) throws Exception
			{
				addParameter(SID_PARAM, sid);
				return null;
			}
		});
	}

	/**
	 * »нициализаци€ операции в разрезе другой операции
	 * @param ouid идентификатор другой операции
	 * @throws Exception
	 */
	public void initializeByOUID(final String ouid) throws Exception
	{
		// если операции нет или она не корректна, отказываем в проверке
		Operation operation = Operation.findByOUID(ouid);
		if(operation == null)
			throw new OperationNotFoundException("Ќе найдена операци€ с иденфтификатором " + ouid);

		if(operation.getState() != OperationState.CONFIRMED)
			throw new IllegalOperationStateException("ќпераци€ с идентификатором " + ouid + " не подтверждена");

		initialize(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session session) throws Exception
			{
				addParameter(OUID_PARAM, ouid);
				return null;
			}
		});
	}

	/**
	 * проверить логин на корректность
	 * @param login логин дл€ проверки.
	 * @param allowSameLoginForUser есть ли логин у пользовател€
	 * @param checkPassword провер€ть ли логин на совпадение с паролем
	 */
	public void execute(final String login, final boolean allowSameLoginForUser, final boolean checkPassword) throws Exception
	{
		execute(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session hibernateSession) throws Exception
			{
				//ѕровер€ем логин на требовани€ безопасности.
				LoginSecurityRestriction.getInstance().check(login);
				//ѕровер€ем логин на совпадение с паролем
				doCheckPassword(login, checkPassword);
				//провер€ем зан€тость логина
				Connector extConnector = allowSameLoginForUser ? Connector.findByAlias(login, getProfileId()) : Connector.findByAlias(login);
				if (extConnector != null)
				{
					throw new LoginAlreadyRegisteredException(login + " уже зарегистрирован в системе");
				}
				return null;
			}
		});
	}

	private void doCheckPassword(String login, boolean checkPassword) throws Exception
	{
		if (checkPassword)
		{
			LoginMatchPasswordRestriction matchPasswordRestriction = new LoginMatchPasswordRestriction(getProfileId());
			matchPasswordRestriction.check(login);
		}
	}
}