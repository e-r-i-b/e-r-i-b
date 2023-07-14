package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.servises.*;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.servises.connectors.DisposableConnector;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.registration.RegistrationType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * �������� ��������������� ������� � ����� ���, � ������ ���� �� ����� ��� ������� ������ ���
 * @author niculichev
 * @ created 06.02.14
 * @ $Author$
 * @ $Revision$
 */
public class AutoUserRegistrationOperation extends Operation
{
	private static final String OLD_CONNECTOR = "old-connector";

	public AutoUserRegistrationOperation(){}

	public AutoUserRegistrationOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	public void initialize(final Connector connector) throws Exception
	{
		initialize(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session session) throws Exception
			{
				setOldConnectorGUID(connector.getGuid());
				return null;
			}
		});
	}

	public Connector execute(final String password) throws Exception
	{
		return execute(new HibernateAction<Connector>()
		{
			public Connector run(org.hibernate.Session session) throws Exception
			{
				// ����� �������, ����� ���������������� ��������
				Profile profile = lockProfile();

				// ���� ���� ��� ����������, ������ ������ ������ �� �����
				List<CSAConnector> csaConnectors = CSAConnector.findNotClosedByProfileID(profile.getId());
				if (CollectionUtils.isNotEmpty(csaConnectors))
					throw new LogicException("��� ������� ��� ���������� ��� ���������� c id = " + csaConnectors.get(0).getGuid());

				Connector connector = findConnectorByGuid(getOldConnectorGUID(), null);
				// ���� ����� ��� ����������
				Login oldLogin = Login.findLoginForConnector(connector.getId());
				// ������������� ������ ���������
				connector.close();

				// ����� ������� ����� ��� ��������� � ��������� ������� � �������
				CSAConnector result = new CSAConnector(connector.getUserId(), connector.getCbCode(), connector.getCardNumber(), profile, RegistrationType.AUTO);
				result.save();
				result.setPassword(password);
				oldLogin.changeConnector(result.getId());

				// ��� �������������� ����������� ������� ������������� ������ ������� ������������
				profile.setSecurityType(SecurityType.LOW);
				profile.save();

				// �������� ������ ���������� ����������� ������ ��������� �����������
				DisposableConnector activeDisposableConnector = DisposableConnector.findNotClosedByUserId(connector.getUserId());
				if (activeDisposableConnector != null)
					activeDisposableConnector.close();

				return result;
			}
		});
	}

	private void setOldConnectorGUID(String guid)
	{
		addParameter(OLD_CONNECTOR, guid);
	}

	private String getOldConnectorGUID()
	{
		return getParameter(OLD_CONNECTOR);
	}
}
