package com.rssl.phizic.gate.config;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.NumericUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @author akrenev
 * @ created 18.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * конфиг внешних систем
 */

public class ExternalSystemConfig extends Config
{
	private static final String INTEGRATION_MODE_PROPERTY_KEY_PREFIX       = "com.rssl.es.integration.service.mode.";
	private static final String MQ_INTEGRATION_TIMEOUT_PROPERTY_KEY_PREFIX = "com.rssl.es.integration.service.timeout.mq.";
	private static final String CARD_PAYMENT_SYSTEM_PAYMENT_PROVIDERS_PROPERTY_KEY_PREFIX = "com.rssl.es.integration.service.providers.mq.CardPaymentSystemPayment";
	private static final String DEFAULT_TIMEOUT = "com.rssl.es.integration.service.default.timeout.mq";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);


	private Map<String, ExternalSystemIntegrationMode> integrationModeMap = new HashMap<String, ExternalSystemIntegrationMode>();
	private Map<String, Long> mqIntegrationTimeoutMap                     = new HashMap<String, Long>();
	private List<String> providerCodes;
	private Long defaultTimeout;

	/**
	 * Любой конфиг должен реализовать данный конструктор.
	 * @param reader ридер.
	 */
	public ExternalSystemConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		defaultTimeout = getLongProperty(DEFAULT_TIMEOUT);

		integrationModeMap = new HashMap<String, ExternalSystemIntegrationMode>();
		Properties integrationModeProperties = getProperties(INTEGRATION_MODE_PROPERTY_KEY_PREFIX);
		for (Map.Entry<Object, Object> entry : integrationModeProperties.entrySet())
		{
			String key = ((String) entry.getKey()).substring(INTEGRATION_MODE_PROPERTY_KEY_PREFIX.length());
			ExternalSystemIntegrationMode value = ExternalSystemIntegrationMode.valueOf((String) entry.getValue());
			integrationModeMap.put(key, value);
		}
		mqIntegrationTimeoutMap = new HashMap<String, Long>();
		Properties mqIntegrationTimeoutProperties = getProperties(MQ_INTEGRATION_TIMEOUT_PROPERTY_KEY_PREFIX);
		for (Map.Entry<Object, Object> entry : mqIntegrationTimeoutProperties.entrySet())
		{
			String key = ((String) entry.getKey()).substring(MQ_INTEGRATION_TIMEOUT_PROPERTY_KEY_PREFIX.length());
			Long value = Long.parseLong((String) entry.getValue()) * DateHelper.MILLISECONDS_IN_SECOND;
			mqIntegrationTimeoutMap.put(key, value);
		}

		providerCodes = new ArrayList<String>();

		String providers = getProperty(CARD_PAYMENT_SYSTEM_PAYMENT_PROVIDERS_PROPERTY_KEY_PREFIX);
		if (StringHelper.isNotEmpty(providers))
			providerCodes.addAll(Arrays.asList(StringUtils.split(providers, ',')));
	}

	/**
	 * Получить настройку режима интеграции по имени сервиса
	 * @param serviceName имя сервиса
	 * @return настройка режима интеграции
	 */
	public ExternalSystemIntegrationMode getIntegrationMode(String serviceName)
	{
		return integrationModeMap.get(serviceName);
	}

	/**
	 * Получить настройку таймаута взаимодействия через MQ по имени сервиса
	 * @param serviceName имя сервиса
	 * @return настройка таймаута взаимодействия через MQ
	 */
	public long getMQIntegrationTimeout(String serviceName)
	{
		Long timeout = mqIntegrationTimeoutMap.get(serviceName);
		if (timeout == null)
		{
			timeout = defaultTimeout * DateHelper.MILLISECONDS_IN_SECOND;
			log.error("Ошибка при получении настройки таймаута взаимодействия через MQ для сервиса " + serviceName);
		}
		return timeout;
	}

	/**
	 * @return коды поставщиков, работающие через MQ
	 */
	public List<String> getProviderCodes()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return providerCodes;
	}
}
