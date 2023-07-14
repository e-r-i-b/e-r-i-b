package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

/**
 * @author EgorovaA
 * @ created 07.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Операция для изменения признака подключения push-уведомлений.
 */
public class ChangePushSupportedParamOperation extends Operation
{
	private static final String GUID_PARAM = "guid";

	public ChangePushSupportedParamOperation()
	{
	}

	public ChangePushSupportedParamOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	private void setGuid(String guid)
	{
		addParameter(GUID_PARAM,guid);
	}

	private String getGuid()
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

	public Connector execute(final boolean pushSupported, final String securityToken) throws Exception
	{
		final String guid = getGuid();
		return execute(new HibernateAction<Connector>()
		{
			public Connector run(org.hibernate.Session session) throws Exception
			{
				Connector connector = findConnectorByGuid(guid, null);
				connector.setPushSupported(pushSupported);
				connector.setSecurityToken(securityToken);
				connector.save();
				return connector;
			}
		});
	}

}
