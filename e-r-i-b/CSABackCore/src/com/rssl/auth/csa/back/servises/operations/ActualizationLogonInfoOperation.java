package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.exceptions.AuthentificationReguiredException;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.servises.connectors.MAPIConnector;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author krenev
 * @ created 11.01.2013
 * @ $Author$
 * @ $Revision$
 * �������� ������������ ���������� � �����.
 */
public class ActualizationLogonInfoOperation extends Operation
{
	private static final String CONNETORS_COUNT_PARAM = "connetorsCount";
	private static final String CONNECTOR_GUID_PARAM = "connetor_";
	private static final String AUTH_CONNECTOR_PARAM = "authConnecotrUID";

	public ActualizationLogonInfoOperation() {}

	public ActualizationLogonInfoOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	/**
	 * ������������������� ��������
	 * @param connectors ������ �����������.
	 * @param authConnectorUID ������������ ���������, �� �������� ��������� ��������������.
	 * @throws Exception
	 */
	public void initialize(final List<CSAConnector> connectors, final String authConnectorUID) throws Exception
	{
		initialize(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session session) throws Exception
			{
				setAuthConnectorUID(authConnectorUID);
				addParameter(CONNETORS_COUNT_PARAM, connectors.size());
				int i = 0;
				for (CSAConnector connector : connectors)
				{
					addParameter(CONNECTOR_GUID_PARAM + i++, connector.getGuid());
				}
				return null;
			}
		});
	}

	/**
	 * @return ������ ����������� ��� ������ �� ���.
	 */
	public List<CSAConnector> getConnectors() throws Exception
	{
		int count = Integer.parseInt(getParameter(CONNETORS_COUNT_PARAM));
		List<CSAConnector> result = new ArrayList<CSAConnector>();
		for (int i = 0; i < count; i++)
		{
			result.add((CSAConnector) CSAConnector.findByGUID(getParameter(CONNECTOR_GUID_PARAM + i)));
		}
		return result;
	}

	private void setAuthConnectorUID(String authConnectorUID)
	{
		addParameter(AUTH_CONNECTOR_PARAM, authConnectorUID);
	}

	private String getAuthConnecotUID()
	{
		return getParameter(AUTH_CONNECTOR_PARAM);
	}

	/**
	 * ����� ������ ���������� �����, ������� ���������� � ���������.
	 * @param guid ������������ ���������� ������� ��������� ��������.
	 * @return ���������, ������� ��� ��������.
	 */
	public Connector execute(final String guid) throws Exception
	{
		Connector connector = execute(new HibernateAction<Connector>()
		{
			public Connector run(org.hibernate.Session session) throws Exception
			{
				Profile profile = lockProfile();
				log.trace("�������� ������ ����������� ��� ������������");
				List<CSAConnector> connectors = getConnectors();
				Connector newestConnector = null; //�������� ����� ���������
				for (CSAConnector connector : connectors)
				{
					if (newestConnector == null || newestConnector.getCreationDate().before(connector.getCreationDate()))
					{
						//���� ����� ������ ���������.
						newestConnector = connector;
					}
					if (connector.getGuid().equals(guid))
					{
						continue; // ���������� ���������, ������� ��������� ��������.
					}
					//�������� ������ �����������, ������������ ����������� ��� ������� ��������
					connector.setSecurityType(null);
					log.info("������ �������� " + connector.getGuid() + " ��� ������������ ���������� � ����� ");
					connector.close();
				}
				//�������� ������ �����������, ������������ ����������� ��� ������� ��������
				MAPIConnector.setSecurityTypeToNotClosed(profile.getId(), null);

				if (newestConnector.getGuid().equals(guid))
				{
					//������� ������������ ���������� ���������� ����������� �������
					profile.setSecurityType(newestConnector.calcSecurityType());
					profile.save();
					//�������� ������� ������������ ����������
					newestConnector.setSecurityType(null);
					newestConnector.save();
					return newestConnector; //���� ������ ����� ����� ���������, ��� � ����������.
				}
				log.info("������������� ��� ���������� " + guid + " ��������� ����� �� ���������� " + newestConnector.getGuid());
				Connector connector = Connector.findByGUID(guid);
				connector.changeCardInfo(newestConnector);
				//������� ������������ ���������� ���������� ����������� �������
				profile.setSecurityType(connector.calcSecurityType());
				profile.save();
				//�������� ������� ������������ ����������
				connector.setSecurityType(null);
				connector.save();
				return connector;
			}
		});
		//��������� �� ����� �� ���������� ������ ������������������.
		if (!connector.getGuid().equals(getAuthConnecotUID()))
		{
			//���. ����� ������ ����� ������ �� ���������� ����������.
			throw new AuthentificationReguiredException();
		}
		return connector;
	}
}
