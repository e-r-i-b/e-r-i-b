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
	 * ��������� ������ �� ������������
	 * @param passwordValue ������ ��� ��������.
	 */
	public void execute(final String passwordValue) throws Exception
	{
		execute(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session hibernateSession) throws Exception
			{
				//��������� ������ �� ���������� ������������.
				CSAPasswordSecurityRestriction.getInstance(Profile.findById(getProfileId(), null)).check(passwordValue);
				return null;
			}
		});
	}
}