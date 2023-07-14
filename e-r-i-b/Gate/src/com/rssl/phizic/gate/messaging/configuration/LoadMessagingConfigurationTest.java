package com.rssl.phizic.gate.messaging.configuration;

import com.rssl.phizic.gate.messaging.configuration.MessagingConfig;
import junit.framework.TestCase;

import java.io.IOException;

/**
 * @author Roshka
 * @ created 14.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class LoadMessagingConfigurationTest extends TestCase
{
	public void test() {}

	public void manualTestLoadXmlBeans() throws IOException, GateMessagingConfigurationException
	{
		ConfigurationLoader loader = new ConfigurationLoader();
		MessagingConfig messagingConfiguration = loader.load("com/rssl/phizic/gate/ext/messaging/configuration/messaging.cfg.xml");
		assertNotNull(messagingConfiguration);
	}
}
