package com.rssl.phizic.gate.messaging.configuration;

import com.rssl.phizic.gate.messaging.configuration.generated.MessagingConfigType;
import com.rssl.phizic.gate.messaging.configuration.generated.Request;
import com.rssl.phizic.utils.resources.ResourceHelper;

import java.io.InputStream;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 * @author Roshka
 * @ created 15.11.2006
 * @ $Author$
 * @ $Revision$
 */

public class ConfigurationLoader
{
	private static final String MESSAGING_CONFIGURATION_PACKAGE = MessagingConfigType.class.getPackage().getName();

	/**
	 * Прочитать настройки из ресурса
	 * @param resourceName имя ресурса
	 * @return MessagingConfig
	 */
	public static MessagingConfig load(String resourceName) throws GateMessagingConfigurationException
	{

		MessagingConfigType xmlConfig = unmarshall(resourceName);

		MessagingConfig config = new MessagingConfig();

		//noinspection unchecked
		List<Request> requests = xmlConfig.getRequests();

		for (Request request : requests)
		{
			config.addToMap(request);
		}

		xmlConfig.toString();

		return config;
	}

	private static MessagingConfigType unmarshall(String resourceName) throws GateMessagingConfigurationException
	{
		MessagingConfigType messagingConfiguration;
		try
		{
			InputStream inputStream = ResourceHelper.loadResourceAsStream(resourceName);
			JAXBContext jaxbContext = JAXBContext.newInstance(MESSAGING_CONFIGURATION_PACKAGE);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			unmarshaller.setEventHandler(new MessagingConfigurationValidator());

			messagingConfiguration = (MessagingConfigType) unmarshaller.unmarshal(inputStream);
		}
		catch (Exception e)
		{
			throw new GateMessagingConfigurationException("Ошибка загрузки конфигурации.", e);
		}
		return messagingConfiguration;
	}
}