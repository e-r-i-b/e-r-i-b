package com.rssl.phizic.operations.config.gate.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.configuration.gate.MonitoringGateConfigBusinessService;
import com.rssl.phizic.business.configuration.gate.locale.MultilocaleMonitoringGateConfigBusinessService;
import com.rssl.phizic.business.locale.EribLocaleService;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourceService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.gate.monitoring.MonitoringGateState;
import com.rssl.phizic.gate.monitoring.MonitoringServiceGateConfig;
import com.rssl.phizic.gate.monitoring.MonitoringServiceGateStateConfig;
import com.rssl.phizic.gate.monitoring.UpdateMonitoringConfigEvent;
import com.rssl.phizic.gate.monitoring.locale.MonitoringServiceGateStateConfigResources;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.locale.dynamic.resources.EditLanguageResourcesOperation;

import java.util.List;

/**
 * Операция редактирования многоязычных текстовок для MonitoringServiceGateStateConfig
 * @author komarov
 * @ created 29.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class EditMonitoringGateConfigResourcesOperation  extends OperationBase implements EditLanguageResourcesOperation<MonitoringServiceGateStateConfigResources, Long>
{

	private static final SimpleService service = new SimpleService();
	private static final MultilocaleMonitoringGateConfigBusinessService MONITORING_GATE_CONFIG_LANGUAGE_RESOURCE_SERVICE = new MultilocaleMonitoringGateConfigBusinessService();
	private static final EribLocaleService LOCALE_SERVICE = new EribLocaleService();

	private ERIBLocale locale;
	private MonitoringServiceGateStateConfigResources entity;
	private String serviceName;

	public void initialize(Long id, String localeId) throws BusinessException, BusinessLogicException
	{
		MonitoringServiceGateStateConfig config = service.findById(MonitoringServiceGateStateConfig.class, id);
		if(config == null)
			throw new BusinessLogicException("Настройки мониторинга сервиса с id = " + id + " не найдены");

		locale = LOCALE_SERVICE.getById(localeId, getInstanceName());

		if(locale == null)
			throw new BusinessLogicException("Локаль с id= " + localeId + "не найдена");

		entity = MONITORING_GATE_CONFIG_LANGUAGE_RESOURCE_SERVICE.findResById(config.getId(), localeId, getInstanceName());
		if(entity == null)
		{
			entity = new MonitoringServiceGateStateConfigResources();
			entity.setId(config.getId());
			entity.setLocaleId(localeId);
		}
	}

	public ERIBLocale getLocale()
	{
		return locale;
	}

	public MonitoringServiceGateStateConfigResources getEntity()
	{
		return entity;
	}

	/**
	 * @param serviceName Наименование сервиса
	 */
	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		MONITORING_GATE_CONFIG_LANGUAGE_RESOURCE_SERVICE.addOrUpdate(entity, serviceName);
	}
}
