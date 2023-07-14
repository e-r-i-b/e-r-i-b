package com.rssl.phizic.business.ant.configs.gate.monitoring;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.ReplicaSource;
import com.rssl.phizic.gate.dictionaries.SynchKeyComparator;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.monitoring.InactiveType;
import com.rssl.phizic.gate.monitoring.MonitoringGateState;
import com.rssl.phizic.gate.monitoring.MonitoringServiceGateConfig;
import com.rssl.phizic.gate.monitoring.MonitoringServiceGateStateConfig;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlFileReader;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.io.File;
import java.util.*;

/**
 * @author akrenev
 * @ created 19.02.2013
 * @ $Author$
 * @ $Revision$
 *
 * Источник обновления настроек мониторинга
 */

public class MonitoringGateConfigReplicaSource implements ReplicaSource<MonitoringServiceGateConfig>
{
	private static final String CONFIG_TAG_NAME = "config";
	private static final String SERVICE_NAME_TAG_NAME = "serviceName";

	private static final String DEGRADATION_CONFIG_TAG_NAME = "degradationConfig";
	private static final String INACCESSIBLE_CONFIG_TAG_NAME = "inaccessibleConfig";

	private static final String AVAILABLE_TAG_NAME = "available";
	private static final String USE_MONITORING_TAG_NAME = "useMonitoring";
	private static final String TIMEOUT_TAG_NAME = "timeout";
	private static final String MONITORING_COUNT_TAG_NAME = "monitoringCount";
	private static final String MONITORING_TIME_TAG_NAME = "monitoringTime";
	private static final String MESSAGE_TEXT_TAG_NAME = "messageText";
	private static final String RECOVERY_TIME_TAG_NAME = "recoveryTime";

	private static final String AVAILABLE_CHANGE_INACTIVE_TYPE_TAG_NAME = "availableChangeInactiveType";
	private static final String INACTIVE_TYPE_TAG_NAME = "inactiveType";

	private final Map<String, MonitoringServiceGateConfig> configs = new HashMap<String, MonitoringServiceGateConfig>();

	private MonitoringServiceGateStateConfig getStateConfig(Element element)
	{
		Boolean available = Boolean.valueOf(XmlHelper.getSimpleElementValue(element, AVAILABLE_TAG_NAME));

		MonitoringServiceGateStateConfig stateConfig = new MonitoringServiceGateStateConfig();
		stateConfig.setAvailable(available);
		//если статус не доступен, то дальше парсить параметры статуса не нужно.
		if (!available)
		{
			stateConfig.setUseMonitoring(false);
			return stateConfig;
		}

		Boolean use = Boolean.valueOf(XmlHelper.getSimpleElementValue(element, USE_MONITORING_TAG_NAME));
		String timeout = XmlHelper.getSimpleElementValue(element, TIMEOUT_TAG_NAME);
		String count = XmlHelper.getSimpleElementValue(element, MONITORING_COUNT_TAG_NAME);
		String time = XmlHelper.getSimpleElementValue(element, MONITORING_TIME_TAG_NAME);
		String message = XmlHelper.getSimpleElementValue(element, MESSAGE_TEXT_TAG_NAME);
		String recoveryTime = XmlHelper.getSimpleElementValue(element, RECOVERY_TIME_TAG_NAME);

		Boolean availableChangeInactiveType = Boolean.valueOf(XmlHelper.getSimpleElementValue(element, AVAILABLE_CHANGE_INACTIVE_TYPE_TAG_NAME));
		InactiveType inactiveType = InactiveType.valueOf(XmlHelper.getSimpleElementValue(element, INACTIVE_TYPE_TAG_NAME));

		stateConfig.setUseMonitoring(use);
		stateConfig.setTimeout(Long.parseLong(timeout));
		stateConfig.setMonitoringCount((int) Long.parseLong(count));
		stateConfig.setMonitoringTime(Long.parseLong(time));
		stateConfig.setMessageText(message);
		stateConfig.setRecoveryTime(Long.parseLong(recoveryTime));
		stateConfig.setAvailableChangeInactiveType(availableChangeInactiveType);
		stateConfig.setInactiveType(inactiveType);
		return stateConfig;
	}

	private MonitoringServiceGateConfig getConfig(Element element) throws Exception
	{
		MonitoringServiceGateConfig config = new MonitoringServiceGateConfig();
		config.setServiceName(XmlHelper.getSimpleElementValue(element, SERVICE_NAME_TAG_NAME));
		config.setState(MonitoringGateState.NORMAL);
		config.setDegradationConfig(getStateConfig(XmlHelper.selectSingleNode(element, DEGRADATION_CONFIG_TAG_NAME)));
		config.setInaccessibleConfig(getStateConfig(XmlHelper.selectSingleNode(element, INACCESSIBLE_CONFIG_TAG_NAME)));
		return config;
	}

	/**
	 * проинициализировать источник обновления конфигов
	 * @param xmlFile файл-источник
	 * @throws GateException
	 */
	public void initialize(File xmlFile) throws GateException
	{
		try
		{
			XmlFileReader xmlFileReader = new XmlFileReader(xmlFile);
			Element pagesXMLElement = xmlFileReader.readDocument().getDocumentElement();
			XmlHelper.foreach(pagesXMLElement, CONFIG_TAG_NAME, new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					configs.put(XmlHelper.getSimpleElementValue(element, SERVICE_NAME_TAG_NAME), getConfig(element));
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Iterator<MonitoringServiceGateConfig> iterator() throws GateException, GateLogicException
	{
		List<MonitoringServiceGateConfig> configCollection = new ArrayList<MonitoringServiceGateConfig>(configs.values());
		Collections.sort(configCollection, new SynchKeyComparator());
		return configCollection.iterator();
	}

	public void initialize(GateFactory factory){}

	public void close(){}
}
