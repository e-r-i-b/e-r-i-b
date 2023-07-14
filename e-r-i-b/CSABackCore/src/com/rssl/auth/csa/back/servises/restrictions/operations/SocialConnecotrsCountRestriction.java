package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.exceptions.TooManyMobileConnectorsException;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.ConnectorState;
import com.rssl.auth.csa.back.servises.ConnectorType;
import com.rssl.auth.csa.back.servises.operations.SocialRegistrationOperation;
import com.rssl.phizic.config.ConfigFactory;

/**
 * @author sergunin
 * @ created 03.09.14
 * @ $Author$
 * @ $Revision$
 * Ограничение на количество зарегистрированных устройств
 */
public class SocialConnecotrsCountRestriction implements OperationRestriction<SocialRegistrationOperation>
{
	private  static final SocialConnecotrsCountRestriction INSTANCE = new SocialConnecotrsCountRestriction();

	public static SocialConnecotrsCountRestriction getInstance()
	{
		return INSTANCE;
	}
	public void check(SocialRegistrationOperation operation) throws Exception
	{
		if (operation == null)
		{
			throw new IllegalArgumentException("Операция на может быть null");
		}
		Long profileId = operation.getProfileId();
		int mobileConnectorsCount = Connector.getCount(profileId, ConnectorType.SOCIAL, operation.getDeviceId(), ConnectorState.ACTIVE, ConnectorState.BLOCKED);
		int maxMobileConnectorsCount = ConfigFactory.getConfig(Config.class).getMaxMobileConnectorsCount();
		if (mobileConnectorsCount >= maxMobileConnectorsCount)
		{
			throw new TooManyMobileConnectorsException(profileId);
		}
	}
}
