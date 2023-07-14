package com.rssl.phizic.business.configuration.gate.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.configuration.gate.MonitoringGateConfigBusinessService;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourceService;
import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.gate.monitoring.MonitoringServiceGateConfig;
import com.rssl.phizic.gate.monitoring.MonitoringServiceGateStateConfig;
import com.rssl.phizic.gate.monitoring.UpdateMonitoringConfigEvent;
import com.rssl.phizic.gate.monitoring.locale.MonitoringServiceGateStateConfigResources;

import java.util.List;

/**
 * Сервис для работы  мультиязычными полями Настроек мониторинга сервиса
 * @author komarov
 * @ created 03.06.2015
 * @ $Author$
 * @ $Revision$
 */
public class MultilocaleMonitoringGateConfigBusinessService extends LanguageResourceService<MonitoringServiceGateStateConfigResources>
{
	private static final MonitoringGateConfigBusinessService SERVICE = new MonitoringGateConfigBusinessService();
	private static final LanguageResourceService<MonitoringServiceGateStateConfigResources> MONITORING_GATE_CONFIG_LANGUAGE_RESOURCE_SERVICE = new LanguageResourceService<MonitoringServiceGateStateConfigResources>(MonitoringServiceGateStateConfigResources.class);

	/**
	 * Конструктор
	 */
	public MultilocaleMonitoringGateConfigBusinessService()
	{
		super(MonitoringServiceGateStateConfigResources.class);
	}
	/**
	 *
	 * @param serviceName имя сервиса
	 * @return настройки из БД
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public MonitoringServiceGateConfig findMultiLocaleConfig(final String serviceName) throws BusinessException
	{
		MonitoringServiceGateConfig config = SERVICE.findConfig(serviceName);
		List<MonitoringServiceGateStateConfigResources> inaccessibleRes = MONITORING_GATE_CONFIG_LANGUAGE_RESOURCE_SERVICE.findResourcesById(config.getInaccessibleConfig().getId(), null);
		List<MonitoringServiceGateStateConfigResources> degradationRes = MONITORING_GATE_CONFIG_LANGUAGE_RESOURCE_SERVICE.findResourcesById(config.getDegradationConfig().getId(), null);
		setMultiLocaleRes(config.getDegradationConfig(), degradationRes);
		setMultiLocaleRes(config.getInaccessibleConfig(), inaccessibleRes);
		return config;
	}

	private void setMultiLocaleRes(MonitoringServiceGateStateConfig state, List<MonitoringServiceGateStateConfigResources> resources)
	{
		for(MonitoringServiceGateStateConfigResources res : resources)
		{
			state.putResource(res.getLocaleId(), res.getMessageText());
		}
	}


	// оповещаем шлюзы
	private void sendChangesToGates(MonitoringServiceGateConfig config) throws BusinessException
	{
		try
		{
			EventSender.getInstance().sendEvent(new UpdateMonitoringConfigEvent(config));
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public MonitoringServiceGateStateConfigResources addOrUpdate(MonitoringServiceGateStateConfigResources entity, String serviceName) throws BusinessException
	{
		MONITORING_GATE_CONFIG_LANGUAGE_RESOURCE_SERVICE.addOrUpdate(entity);
		MonitoringServiceGateConfig config = findMultiLocaleConfig(serviceName);
		sendChangesToGates(config);
		return entity;
	}
}
