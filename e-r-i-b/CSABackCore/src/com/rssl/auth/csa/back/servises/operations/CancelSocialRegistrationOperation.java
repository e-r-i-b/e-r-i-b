package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.exceptions.ConnectorNotFoundException;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.connectors.SocialAPIConnector;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.hibernate.Session;

/**
 * @author sergunin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 */
public class CancelSocialRegistrationOperation extends CancelMobileRegistrationOperation
{

	public CancelSocialRegistrationOperation()
	{
	}

	public CancelSocialRegistrationOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
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
				if (!(connector instanceof SocialAPIConnector))
				{
					throw new ConnectorNotFoundException("��������� " + getGuid() + " ����� ������������ ��� " + connector.getType());
				}
				connector.close();
				return connector;
			}
		});
	}
}
