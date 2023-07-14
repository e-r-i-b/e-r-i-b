package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.exceptions.InvalidSessionException;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.auth.csa.back.servises.Session;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

/**
 * @author krenev
 * @ created 12.10.2012
 * @ $Author$
 * @ $Revision$
 * ������� ����� ��������, ������������� � ��������� ������
 */
public class SessionContextOperation extends Operation
{
	private static final String SID_PARAM = "sid";

	public SessionContextOperation() {}

	public SessionContextOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	protected void setSid(String sid)
	{
		addParameter(SID_PARAM, sid);
	}

	protected String getSid()
	{
		return getParameter(SID_PARAM);
	}

	/**
	 * ������������������� ��������
	 * @param sid ������������ ������
	 */
	public void initialize(final String sid) throws Exception
	{
		initialize(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session session) throws Exception
			{
				setSid(sid);
				return null;
			}
		});
	}

	/**
	 * @return �������� �������, � ��������� ������� ����������� ��������
	 * @throws Exception
	 */
	protected Session getSession() throws Exception
	{
		String sid = getSid();
		Session session = Session.findBySid(sid);
		if (session == null)
		{
			throw new InvalidSessionException("������ " + sid + " �� �������");
		}
		if (!session.isValid())
		{
			throw new InvalidSessionException("������ " + sid + " ���������");
		}
		return session;
	}

	/**
	 * @return �������� ���������, ��� �������� ������� ������.
	 */
	protected Connector getSessionConnector() throws Exception
	{
		Session session = getSession();
		Connector connector = session.getConnector();
		if (connector == null)
		{
			session.close();
			throw new InvalidSessionException("�� ������ ��������� " + session.getConnectorGuid() + " , ����������� � ������ " + session.getGuid());
		}
		if (connector.isClosed() || connector.isBlocked())
		{
			session.close();
			throw new InvalidSessionException("��������� " + session.getConnectorGuid() + " , ����������� � ������ " + session.getGuid() + " �����������");
		}
		return connector;
	}
}
