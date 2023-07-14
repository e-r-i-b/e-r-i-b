package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.exceptions.AuthenticationFailedException;
import com.rssl.auth.csa.back.exceptions.ConnectorNotFoundException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.ConnectorType;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.auth.csa.back.servises.Session;
import com.rssl.phizic.common.types.csa.AuthorizedZoneType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.LockMode;

import java.util.Map;
import java.util.TreeMap;

import static com.rssl.phizic.context.Constants.*;

/**
 * @author krenev
 * @ created 24.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class UserLogonOperation extends Operation
{
	private static final String CONNECTOR_UID_TYPE_PARAM    = "connectorUID";
	private static final String CONNECTOR_TYPE_PARAM        = "connectorType";
	private static final String AUTHORIZED_ZONE_TYPE_PARAM  = "authorizedZone";


	public UserLogonOperation() {}

	public UserLogonOperation(IdentificationContext identificationContext) throws Exception
	{
		super(identificationContext);
	}

	private void setConnectorUID(String connectorUID)
	{
		addParameter(CONNECTOR_UID_TYPE_PARAM, connectorUID);
	}

	private String getConnectorUID()
	{
		return getParameter(CONNECTOR_UID_TYPE_PARAM);
	}

	private void setConnectorType(ConnectorType connectorType)
	{
		addParameter(CONNECTOR_TYPE_PARAM, connectorType.name());
	}

	private ConnectorType getConnectorType()
	{
		return ConnectorType.valueOf(getParameter(CONNECTOR_TYPE_PARAM));
	}

	private void setAuthorizedZoneType(AuthorizedZoneType authorizedZoneType)
	{
		addParameter(AUTHORIZED_ZONE_TYPE_PARAM, authorizedZoneType.name());
	}

	/**
	 * ���������� ���������� ��� ������� RSA(����-����������).
	 * ��������� � ������� ������������, ��� ��� ���������� ���������� ������ ��������� � ������������� �������� � �������� �������.
	 * @param rsaData - ���������� ������
	 */
	public void setRSAData(Map<String, String> rsaData)
	{
		addParameterWithReplacingDelimiter(DEVICE_TOKEN_FSO_PARAMETER_NAME, rsaData.get(DEVICE_TOKEN_FSO_PARAMETER_NAME));
		addParameterWithReplacingDelimiter(JS_EVENTS_PARAMETER_NAME, rsaData.get(JS_EVENTS_PARAMETER_NAME));
		addParameterWithReplacingDelimiter(DEVICE_PRINT_PARAMETER_NAME, rsaData.get(DEVICE_PRINT_PARAMETER_NAME));
		addParameterWithReplacingDelimiter(DOM_ELEMENTS_PARAMETER_NAME, rsaData.get(DOM_ELEMENTS_PARAMETER_NAME));
		addParameterWithReplacingDelimiter(DEVICE_TOKEN_COOKIE_PARAMETER_NAME, rsaData.get(DEVICE_TOKEN_COOKIE_PARAMETER_NAME));

		addParameterWithReplacingDelimiter(HTTP_ACCEPT_HEADER_NAME, rsaData.get(HTTP_ACCEPT_HEADER_NAME));
		addParameterWithReplacingDelimiter(HTTP_ACCEPT_CHARS_HEADER_NAME, rsaData.get(HTTP_ACCEPT_CHARS_HEADER_NAME));
		addParameterWithReplacingDelimiter(HTTP_ACCEPT_ENCODING_HEADER_NAME, rsaData.get(HTTP_ACCEPT_ENCODING_HEADER_NAME));
		addParameterWithReplacingDelimiter(HTTP_ACCEPT_LANGUAGE_HEADER_NAME, rsaData.get(HTTP_ACCEPT_LANGUAGE_HEADER_NAME));
		addParameterWithReplacingDelimiter(HTTP_REFERRER_HEADER_NAME, rsaData.get(HTTP_REFERRER_HEADER_NAME));
		addParameterWithReplacingDelimiter(HTTP_USER_AGENT_HEADER_NAME, rsaData.get(HTTP_USER_AGENT_HEADER_NAME));
		addParameterWithReplacingDelimiter(PAGE_ID_PARAMETER_NAME, rsaData.get(PAGE_ID_PARAMETER_NAME));
	}

	/**
	 * �������� ���������� ��� �������� � ������� RSA/
	 * ��� �� ��������������� �������� �����������
	 * @return - RSA-������
	 */
	public Map<String, String> getRSAData()
	{
		TreeMap<String, String> result = new TreeMap<String, String>();
		result.put(DEVICE_TOKEN_FSO_PARAMETER_NAME, getParameterWithReplacingDelimiter(DEVICE_TOKEN_FSO_PARAMETER_NAME));
		result.put(JS_EVENTS_PARAMETER_NAME, getParameterWithReplacingDelimiter(JS_EVENTS_PARAMETER_NAME));
		result.put(DEVICE_PRINT_PARAMETER_NAME, getParameterWithReplacingDelimiter(DEVICE_PRINT_PARAMETER_NAME));
		result.put(DOM_ELEMENTS_PARAMETER_NAME, getParameterWithReplacingDelimiter(DOM_ELEMENTS_PARAMETER_NAME));
		result.put(DEVICE_TOKEN_COOKIE_PARAMETER_NAME, getParameterWithReplacingDelimiter(DEVICE_TOKEN_COOKIE_PARAMETER_NAME));

		result.put(HTTP_ACCEPT_HEADER_NAME, getParameterWithReplacingDelimiter(HTTP_ACCEPT_HEADER_NAME));
		result.put(HTTP_ACCEPT_CHARS_HEADER_NAME, getParameterWithReplacingDelimiter(HTTP_ACCEPT_CHARS_HEADER_NAME));
		result.put(HTTP_ACCEPT_ENCODING_HEADER_NAME, getParameterWithReplacingDelimiter(HTTP_ACCEPT_ENCODING_HEADER_NAME));
		result.put(HTTP_ACCEPT_LANGUAGE_HEADER_NAME, getParameterWithReplacingDelimiter(HTTP_ACCEPT_LANGUAGE_HEADER_NAME));
		result.put(HTTP_REFERRER_HEADER_NAME, getParameterWithReplacingDelimiter(HTTP_REFERRER_HEADER_NAME));
		result.put(HTTP_USER_AGENT_HEADER_NAME, getParameterWithReplacingDelimiter(HTTP_USER_AGENT_HEADER_NAME));
		result.put(PAGE_ID_PARAMETER_NAME, getParameterWithReplacingDelimiter(PAGE_ID_PARAMETER_NAME));

		return result;
	}

	/**
	 * ���������� mobileSDKData(���������� ��� �������� �� ����-����������, ��� ����� ����� mAPI)
	 * @param data - ������ � �������
	 */
	public void setMobileSDKData(String data)
	{
		addParameterWithReplacingDelimiter(Constants.MOBILE_SDK_DATA_TAG, data);
	}

	/**
	 * �������� mobileSDKData
	 * @return - ������ � mobileSDKData
	 */
	public String getMobileSDKData()
	{
		return getParameterWithReplacingDelimiter(Constants.MOBILE_SDK_DATA_TAG);
	}

	/**
	 * @return ��� ���� ����� ������������
	 */
	public AuthorizedZoneType getAuthorizedZoneType()
	{
		return AuthorizedZoneType.valueOf(getParameter(AUTHORIZED_ZONE_TYPE_PARAM));
	}

	/**
	 * @return ������ �� ������ � �������� ����������, � �� � ���
	 */
	public boolean isERIBUser()
	{
		return ConnectorType.MAPI != getConnectorType() &&  ConnectorType.SOCIAL != getConnectorType() && ConnectorType.ATM != getConnectorType();
	}

	/**
	 * �������� �������� ��������� �����.
	 * @param lockMode ����� ����������. ���� �� ����� - ��� ����������
	 * @return ��������� ���������. � ������ ������������ - ����������
	 */
	public Connector getConnector(LockMode lockMode) throws Exception
	{
		return findActiveConnectorByGuid(getConnectorUID(), lockMode);
	}

	/**
	 * ���������������� ��������
	 * @param connectorUID ������������ ����������
	 * @param connectorType ��� ���������
	 * @param authorizedZoneType ��� ���� ����� ������������
	 * @throws Exception
	 */
	public void initialize(final String connectorUID, final ConnectorType connectorType, final AuthorizedZoneType authorizedZoneType, final Map<String, String> rsaData, final String mobileSDKData) throws Exception
	{
		initialize(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session session) throws Exception
			{
				setConnectorUID(connectorUID);
				setConnectorType(connectorType);
				if (authorizedZoneType != null)
				{
					setAuthorizedZoneType(authorizedZoneType);
				}
				if (rsaData != null)
				{
					setRSAData(rsaData);
				}
				if (!StringHelper.isEmpty(mobileSDKData))
				{
					setMobileSDKData(mobileSDKData);
				}
				return null;
			}
		});
	}

	/**
	 * ��������� ������ �� ����
	 * @return ��������� ������
	 * @throws Exception ���������� ������
	 * @throws ConnectorNotFoundException � ������ ����������� ��� �������������� �������������� ����������.
	 * @throws AuthenticationFailedException ������� ������ �������������� �� ��������� ����������(��������� ���������� � �.�.)
	 */
	public Session execute() throws Exception
	{
		return execute(new HibernateAction<Session>()
		{
			public Session run(org.hibernate.Session hibernateSession) throws Exception
			{
				//�������� ��������� c ����������� ��� �������� ����������������� (����� ��������������� � ������ ����� ��������� ���������� ����������).
				Connector connector = getConnector(LockMode.UPGRADE_NOWAIT);
				return Session.create(connector);
			}
		});
	}

	/**
	 * @return ����� ����� ������
	 */
	public static int getLifeTime()
	{
		return ConfigFactory.getConfig(Config.class).getAuthenticationTimeout();
	}
}