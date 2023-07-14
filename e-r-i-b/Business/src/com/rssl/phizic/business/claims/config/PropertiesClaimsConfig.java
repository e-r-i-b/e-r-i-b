package com.rssl.phizic.business.claims.config;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.security.config.Constants;

/**
 * Конфиг для работы с заявками.
 * @author egorova
 * @ created 30.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class PropertiesClaimsConfig extends Config
{
	//срок хранения заявок
	private Integer claimWorkingLife;

	public PropertiesClaimsConfig(PropertyReader propertyReader)
	{
		super(propertyReader);
	}

	public Integer getClaimWorkingLife()
	{
		return claimWorkingLife;
	}

	public void doRefresh() throws ConfigurationException
	{
		claimWorkingLife = getIntProperty(Constants.CLAIM_WORKING_LIFE);
	}
}
