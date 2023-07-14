package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.exceptions.IllegalOperationStateException;
import com.rssl.auth.csa.back.exceptions.InvalidSessionException;
import com.rssl.auth.csa.back.exceptions.OperationNotFoundException;
import com.rssl.auth.csa.back.servises.*;
import com.rssl.auth.csa.back.servises.connectors.PasswordBasedConnector;
import com.rssl.auth.csa.back.servises.restrictions.security.CSAPasswordSecurityRestriction;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

/**
 * @author krenev
 * @ created 28.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class ValidatePasswordOperation extends Operation
{
	private static final String SID_PARAM = "sid";
	private static final String OUID_PARAM = "ouid";

	public ValidatePasswordOperation() {}

	public ValidatePasswordOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	/**
	 * Идентификации операции в разрезе сессии
	 * @param sid идентификатор сессии
	 * @throws Exception
	 */
	public void initializeBySID(final String sid) throws Exception
	{
		// если сессии нет или она не корректна, отказываем в проверке
		Session session = Session.findBySid(sid);
		if(session == null)
			throw new InvalidSessionException("Сессия " + sid + " не найдена");

		if(!session.isValid())
			throw new InvalidSessionException("Сессия " + sid + " невалидна");

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
	 * Инициализация операции в разрезе другой операции
	 * @param ouid идентификатор другой операции
	 * @throws Exception
	 */
	public void initializeByOUID(final String ouid) throws Exception
	{
		// если операции нет или она не корректна, отказываем в проверке
		Operation operation = Operation.findByOUID(ouid);
		if(operation == null)
			throw new OperationNotFoundException("Не найдена операция с иденфтификатором " + ouid);

		if(operation.getState() != OperationState.CONFIRMED)
			throw new IllegalOperationStateException("Операция с идентификатором " + ouid + " не подтверждена");

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
	 * проверить пароль на корректность
	 * @param passwordValue пароль для проверки.
	 */
	public void execute(final String passwordValue) throws Exception
	{
		execute(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session hibernateSession) throws Exception
			{
				//Проверяем пароля на требования безопасности.
				CSAPasswordSecurityRestriction.getInstance(Profile.findById(getProfileId(), null)).check(passwordValue);
				return null;
			}
		});
	}
}