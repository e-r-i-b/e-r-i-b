package com.rssl.phizic.messaging.jobs;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.business.messaging.info.MockTemplateLoader;
import com.rssl.phizic.messaging.mail.messagemaking.MessageBuilder;
import com.rssl.phizic.messaging.mail.messagemaking.MessageBuilderFactory;
import com.rssl.phizic.messaging.mail.messagemaking.MessagemakingConfig;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 16.03.2007
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */

public class MessageBuilderFactoryTest extends BusinessTestCaseBase
{
	protected void setUp() throws Exception
	{
		super.setUp();

		System.setProperty("com.rssl.freemarker.cache.TemplateLoader", MockTemplateLoader.class.getName());
	}

	public void testMB() throws Exception
	{
		MessageBuilderFactory factory = new MessageBuilderFactory();

		List<String> channels = ConfigFactory.getConfig(MessagemakingConfig.class).getAllChannels();

		for (String channel : channels)
		{
			MessageBuilder messageBuilder = factory.newMessageBuilder(channel, "test");
			assertNotNull(messageBuilder);
		}
	}
}