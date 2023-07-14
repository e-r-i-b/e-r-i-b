package com.rssl.phizicgate.sbrf.ws;

import junit.framework.TestCase;
import com.rssl.phizic.gate.messaging.configuration.GateMessagingConfigurationException;
import com.rssl.phizic.gate.messaging.configuration.ConfigurationLoader;
import com.rssl.phizic.gate.messaging.configuration.MessagingConfig;

/**
 * @author Roshka
 * @ created 23.11.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 3720 $
 */

public class LoadConfigurationTest extends TestCase
{
	public void testLoadConfiguration() throws GateMessagingConfigurationException
	{
		ConfigurationLoader loader = new ConfigurationLoader();

		MessagingConfig config = loader.load(Constants.OUTGOING_MESSAGES_CONFIG);

		assertNotNull(config);
	}
}