package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.auth.csa.back.servises.connectors.MAPIConnector;
import com.rssl.auth.csa.back.exceptions.ConnectorNotFoundException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.hibernate.Session;

/**
 * @author krenev
 * @ created 28.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class CancelMobileRegistrationOperation extends Operation
{
	private static final String GUID_PARAM = "guid";

	public CancelMobileRegistrationOperation()
	{
	}

	public CancelMobileRegistrationOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	protected void setGuid(String guid)
	{
		addParameter(GUID_PARAM, guid);
	}

    protected String getGuid()
	{
		return getParameter(GUID_PARAM);
	}

	/**
	 * ������������������� ��������
	 * @param guid ������������� ������������ ����������
	 * @throws Exception
	 */
	public void initialize(final String guid) throws Exception
	{
		initialize(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session session) throws Exception
			{
				setGuid(guid);
				return null;
			}
		});
	}

	/**
	 * ��������� ������
	 * @return ����������� ���������
	 * @throws Exception ���������� ������
	 * @throws com.rssl.auth.csa.back.exceptions.ConnectorNotFoundException � ������ ����������� ��� �������������� �������������� ����������.
	 */
	public Connector execute() throws Exception
	{
		return execute(new HibernateAction<Connector>()
		{
			public Connector run(Session session) throws Exception
			{
				Connector connector = findConnectorByGuid(getGuid(), null);
				if (!(connector instanceof MAPIConnector))
				{
					throw new ConnectorNotFoundException("��������� " + getGuid() + " ����� ������������ ��� " + connector.getType());
				}
				connector.close();
				return connector;
			}
		});
	}
}
