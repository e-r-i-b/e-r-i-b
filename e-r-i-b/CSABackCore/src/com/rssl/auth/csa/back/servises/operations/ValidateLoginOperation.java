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
	 * ������������� �������� � ������� ������
	 * @param sid ������������� ������
	 * @throws Exception
	 */
	public void initializeBySID(final String sid) throws Exception
	{
		// ���� ������ ��� ��� ��� �� ���������, ���������� � ��������
		Session session = Session.findBySid(sid);
		if(session == null)
			throw new InvalidSessionException("������ " + sid + " �� �������");

		if(!session.isValid())
			throw new InvalidSessionException("������ " + sid + " ���������");

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
	 * ������������� �������� � ������� ������ ��������
	 * @param ouid ������������� ������ ��������
	 * @throws Exception
	 */
	public void initializeByOUID(final String ouid) throws Exception
	{
		// ���� �������� ��� ��� ��� �� ���������, ���������� � ��������
		Operation operation = Operation.findByOUID(ouid);
		if(operation == null)
			throw new OperationNotFoundException("�� ������� �������� � ���������������� " + ouid);

		if(operation.getState() != OperationState.CONFIRMED)
			throw new IllegalOperationStateException("�������� � ��������������� " + ouid + " �� ������������");

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
	 * ��������� ����� �� ������������
	 * @param login ����� ��� ��������.
	 * @param allowSameLoginForUser ���� �� ����� � ������������
	 * @param checkPassword ��������� �� ����� �� ���������� � �������
	 */
	public void execute(final String login, final boolean allowSameLoginForUser, final boolean checkPassword) throws Exception
	{
		execute(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session hibernateSession) throws Exception
			{
				//��������� ����� �� ���������� ������������.
				LoginSecurityRestriction.getInstance().check(login);
				//��������� ����� �� ���������� � �������
				doCheckPassword(login, checkPassword);
				//��������� ��������� ������
				Connector extConnector = allowSameLoginForUser ? Connector.findByAlias(login, getProfileId()) : Connector.findByAlias(login);
				if (extConnector != null)
				{
					throw new LoginAlreadyRegisteredException(login + " ��� ��������������� � �������");
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