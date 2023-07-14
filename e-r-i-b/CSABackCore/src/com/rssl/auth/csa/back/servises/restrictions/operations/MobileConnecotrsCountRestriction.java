package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.exceptions.TooManyMobileConnectorsException;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.ConnectorState;
import com.rssl.auth.csa.back.servises.ConnectorType;
import com.rssl.auth.csa.back.servises.operations.MobileRegistrationOperation;
import com.rssl.phizic.config.ConfigFactory;

/**
 * @author krenev
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 * Ограничение на количество зарегистрированных устройств
 */
public class MobileConnecotrsCountRestriction implements OperationRestriction<MobileRegistrationOperation>
{
	private  static final MobileConnecotrsCountRestriction INSTANCE = new MobileConnecotrsCountRestriction();

	public static MobileConnecotrsCountRestriction getInstance()
	{
		return INSTANCE;
	}
	public void check(MobileRegistrationOperation operation) throws Exception
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("Операция на может быть null");
		}
		Long profileId = operation.getProfileId();
		int mobileConnectorsCount = Connector.getCount(profileId, ConnectorType.MAPI, operation.getDeviceId(), ConnectorState.ACTIVE, ConnectorState.BLOCKED);
		int maxMobileConnectorsCount = ConfigFactory.getConfig(Config.class).getMaxMobileConnectorsCount();
		if (mobileConnectorsCount >= maxMobileConnectorsCount)
		{
			throw new TooManyMobileConnectorsException(profileId);
		}
	}
}
