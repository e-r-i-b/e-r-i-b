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
	 * »сполнить за€вку
	 * @param deviceState сосот€ние устройства. ќбновл€етс€ в коннекторе, если не null и не совпадает с сохраненной.
	 * @param deviceId уникальный идентификатор устройства
	 * @param version верси€ мобильного апи (can be null)
	 * @param pin PIN-код
	 * @return коннектор, по которому проведена аутентификаци€.
	 * @throws Exception внутренние ошибки
	 * @throws ConnectorNotFoundException в случае невалидного дл€ аутентификации идентификатора коннектора.
	 * @throws AuthenticationFailedException вслучае ошибок аутенти€кации по валидному коннектору(временна€ блокировка и т.п.)
	 */
	public Connector execute(final String deviceState, final String deviceId, final String version, final String pin) throws Exception
	{
		final String guid = getGuid();
		Connector result = execute(new HibernateAction<Connector>()
		{
			public Connector run(org.hibernate.Session session) throws Exception
			{
				//получаем коннектор c блокировкой (дл€ исключени€ лост апдейта)... пессимизм рулит.
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
	 * ѕровер€ет, соответствует ли переданный идентификатор устройства коннектору, по которому проведена аутентификаци€
	 * @param connector - коннектор, по которому проведена аутентификаци€
	 * @param deviceId - переданный идентификатор устройства
	 * @throws Exception
	 */
	protected void checkDeviceId(Connector connector, String deviceId) throws Exception
	{
		String savedDeviceId = connector.getDeviceId();
		// ≈сли коннектор содержит идентификатор устройства, провер€ем его (deviceID) на совпадение с пришедшим при аутентификации
		if (StringHelper.isNotEmpty(savedDeviceId))
		{
			// ≈сли идентификаторы не совпадают, пишем сообщение в лог
			if (!StringHelper.equalsNullIgnore(savedDeviceId, deviceId))
				log.error("»зменение ID устройства: старое " + savedDeviceId + " | новое " + deviceId + " дл€ mGUID " + connector.getGuid());
		}
		// ≈сли коннектор не содержит идентификатор устройства, провер€ем пришедший deviceID в разрезе всех коннекторов
		else
		{
			List<Connector> connectors = Connector.findByDeviceId(deviceId);
			if (CollectionUtils.isEmpty(connectors))
				connector.setDeviceId(deviceId);
			else
			{
				for (Connector duplicateConnector : connectors)
				{
					log.error("ƒублирование ID устройства: дублирующее "+ deviceId +" mGUID "+ connector.getGuid() +" | ранее сохраненное "+ duplicateConnector.getDeviceId() +" mGUID "+ duplicateConnector.getGuid());
				}
			}
		}
	}

}
