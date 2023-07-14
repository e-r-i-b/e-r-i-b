package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.exceptions.AuthenticationFailedException;
import com.rssl.auth.csa.back.exceptions.ConnectorNotFoundException;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.ConnectorType;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.LockMode;

import java.util.List;

/**
 * @author krenev
 * @ created 25.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class MobileAuthenticationOperation extends Operation
{
	private static final String GUID_PARAM = "guid";

	public MobileAuthenticationOperation()
	{
	}

	public MobileAuthenticationOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

    protected void setGuid(String guid)
	{
		addParameter(GUID_PARAM,guid);
	}

	protected String getGuid()
	{
		return getParameter(GUID_PARAM);
	}

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

    protected ConnectorType getConnectorType()
    {
        return ConnectorType.MAPI;
    }

	/**
	 * ��������� ������
	 * @param deviceState ��������� ����������. ����������� � ����������, ���� �� null � �� ��������� � �����������.
	 * @param deviceId ���������� ������������� ����������
	 * @param version ������ ���������� ��� (can be null)
	 * @param pin PIN-���
	 * @return ���������, �� �������� ��������� ��������������.
	 * @throws Exception ���������� ������
	 * @throws ConnectorNotFoundException � ������ ����������� ��� �������������� �������������� ����������.
	 * @throws AuthenticationFailedException ������� ������ ������������� �� ��������� ����������(��������� ���������� � �.�.)
	 */
	public Connector execute(final String deviceState, final String deviceId, final String version, final String pin) throws Exception
	{
		final String guid = getGuid();
		Connector result = execute(new HibernateAction<Connector>()
		{
			public Connector run(org.hibernate.Session session) throws Exception
			{
				//�������� ��������� c ����������� (��� ���������� ���� �������)... ��������� �����.
				Connector connector = findActiveConnectorByGuid(guid, LockMode.UPGRADE_NOWAIT, getConnectorType());
				if (StringHelper.isNotEmpty(deviceId))
					checkDeviceId(connector, deviceId);
				if (StringHelper.isNotEmpty(version))
					connector.setVersion(version);
				if (deviceState != null && !deviceState.equals(connector.getDeviceState()))
				{
					connector.setDeviceState(deviceState);
					connector.save();
				}
				return connector;
			}
		});

		if (StringHelper.isNotEmpty(pin))
			result.authenticate(pin);

		return result;
	}

	/**
	 * ���������, ������������� �� ���������� ������������� ���������� ����������, �� �������� ��������� ��������������
	 * @param connector - ���������, �� �������� ��������� ��������������
	 * @param deviceId - ���������� ������������� ����������
	 * @throws Exception
	 */
	protected void checkDeviceId(Connector connector, String deviceId) throws Exception
	{
		String savedDeviceId = connector.getDeviceId();
		// ���� ��������� �������� ������������� ����������, ��������� ��� (deviceID) �� ���������� � ��������� ��� ��������������
		if (StringHelper.isNotEmpty(savedDeviceId))
		{
			// ���� �������������� �� ���������, ����� ��������� � ���
			if (!StringHelper.equalsNullIgnore(savedDeviceId, deviceId))
				log.error("��������� ID ����������: ������ " + savedDeviceId + " | ����� " + deviceId + " ��� mGUID " + connector.getGuid());
		}
		// ���� ��������� �� �������� ������������� ����������, ��������� ��������� deviceID � ������� ���� �����������
		else
		{
			List<Connector> connectors = Connector.findByDeviceId(deviceId);
			if (CollectionUtils.isEmpty(connectors))
				connector.setDeviceId(deviceId);
			else
			{
				for (Connector duplicateConnector : connectors)
				{
					log.error("������������ ID ����������: ����������� "+ deviceId +" mGUID "+ connector.getGuid() +" | ����� ����������� "+ duplicateConnector.getDeviceId() +" mGUID "+ duplicateConnector.getGuid());
				}
			}
		}
	}

}
