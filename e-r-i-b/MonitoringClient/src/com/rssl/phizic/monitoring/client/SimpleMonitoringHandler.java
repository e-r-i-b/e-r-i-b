package com.rssl.phizic.monitoring.client;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.build.MonitoringConfig;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.monitoring.*;
import com.rssl.phizic.logging.operations.context.OperationContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.NumericUtil;

/**
 * @author mihaylov
 * @ created 07.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ѕростой обработчик данных об ответах от внешних систем дл€ мониторинга
 * ¬ принципе может работать дл€ любой внешней системы.
 */
public class SimpleMonitoringHandler extends MonitoringHandler
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Gate);

	public void checkRequest(String requestTag) throws GateLogicException
	{
		String serviceName = ConfigFactory.getConfig(MonitoringConfig.class).getRequestTagConfiguration().get(requestTag);
		if(serviceName == null)
			return;
		try
		{
			MonitoringServiceGateConfig config = MonitoringGateServicesContext.getInstance().getConfig(serviceName);
			if(config == null)
				return;
			MonitoringGateState serviceState = config.getState();
			switch (serviceState)
			{
				case NORMAL: break;//ничего не делаем
				case DEGRADATION:
				{
					String operUid = OperationContext.getCurrentOperUID();
					int sumDigit = NumericUtil.sumDigitInString(operUid);
					//провер€ем пропускам ли запрос
					if(sumDigit % 2 == 0)
						break;
					InactiveTypeHelper.activate(config.getDegradationConfig());
					break;
				}
				case INACCESSIBLE:
					InactiveTypeHelper.activate(config.getInaccessibleConfig());
					break;
			}
		}
		catch (GateException e)
		{
			log.error("Ќе удалось проверить статус сервиса",e);
		}
	}

	public void processError(String requestTag)
	{
		processData(requestTag,0,null,true);
	}

	public void processRequest(String requestTag, long time, String errorCode)
	{
		processData(requestTag,time,errorCode,false);
	}

	/**
	 * ќбработать данные о выполнении запроса во внешнюю систему.
	 * @param requestTag - тип запроса(название тега)
	 * @param time - врем€ выполнени€ запроса во внешнюю систему(в миллисекундах)
	 * @param errorCode - код ошибки обработки запроса во внешней системе
	 * @param notCheckError - не учитывать ошибки при увеличении счетчика(true - увеличиваем в любом случае)
	 */
	private void processData(String requestTag, long time, String errorCode, boolean notCheckError)
	{
		String serviceName = ConfigFactory.getConfig(MonitoringConfig.class).getRequestTagConfiguration().get(requestTag);
		if(serviceName == null)
			return;
		try
		{
			MonitoringServiceGateConfig config = MonitoringGateServicesContext.getInstance().getConfig(serviceName);
			if(config == null)
				return;
			IncrementMode incrementMode = IncrementMode.EMPTY;

			MonitoringServiceGateStateConfig inaccessibleConfig = config.getInaccessibleConfig();
			if(inaccessibleConfig != null && inaccessibleConfig.isUseMonitoring() && (notCheckError || time > inaccessibleConfig.getTimeout() || ConfigFactory.getConfig(MonitoringConfig.class).getErrorCodes().contains(errorCode)))
				incrementMode = incrementMode.add(MonitoringGateState.INACCESSIBLE);

			MonitoringServiceGateStateConfig degradationConfig = config.getDegradationConfig();
			if(degradationConfig != null && degradationConfig.isUseMonitoring() && (notCheckError || time > degradationConfig.getTimeout()))
				incrementMode = incrementMode.add(MonitoringGateState.DEGRADATION);

			if (ConfigFactory.getConfig(MonitoringConfig.class).isUsePercentCounter())
			{
				if (incrementMode == IncrementMode.EMPTY)
				{
					if (inaccessibleConfig != null && inaccessibleConfig.isUseMonitoring())
						incrementMode = incrementMode.add(MonitoringGateState.INACCESSIBLE);
					if (degradationConfig != null && degradationConfig.isUseMonitoring())
						incrementMode = incrementMode.add(MonitoringGateState.DEGRADATION);
					GateSingleton.getFactory().service(IncrementMonitoringCounterService.class).incrementPercent(serviceName, incrementMode, false);
				}
				else
					GateSingleton.getFactory().service(IncrementMonitoringCounterService.class).incrementPercent(serviceName, incrementMode, true);
			}
			else
			{
				if (incrementMode == IncrementMode.EMPTY)
					return;
				GateSingleton.getFactory().service(IncrementMonitoringCounterService.class).increment(serviceName, incrementMode);
			}
		}
		catch (Exception e)
		{
			log.error("Ќе удалось увеличить счетчик мониторинга",e);
		}
	}

	public void processResponce(String responceTag, String errorCode)
	{
		String serviceName = ConfigFactory.getConfig(MonitoringConfig.class).getResponceTagConfiguration().get(responceTag);
		if(serviceName == null)
			return;
		try
		{
			MonitoringServiceGateConfig config = MonitoringGateServicesContext.getInstance().getConfig(serviceName);
			if(config == null)
				return;
			MonitoringServiceGateStateConfig inaccessibleConfig = config.getInaccessibleConfig();
			if(inaccessibleConfig != null && inaccessibleConfig.isUseMonitoring() && ConfigFactory.getConfig(MonitoringConfig.class).getErrorCodes().contains(errorCode))
				if(ConfigFactory.getConfig(MonitoringConfig.class).isUsePercentCounter())
					GateSingleton.getFactory().service(IncrementMonitoringCounterService.class).incrementPercent(serviceName, IncrementMode.INACCESSIBLE, true);
				else
			        GateSingleton.getFactory().service(IncrementMonitoringCounterService.class).increment(serviceName, IncrementMode.INACCESSIBLE);
		}
		catch (Exception e)
		{
			log.error("Ќе удалось увеличить счетчик мониторинга",e);
		}
	}

	public void processOk(String responceTag)
	{
		if (!ConfigFactory.getConfig(MonitoringConfig.class).isUsePercentCounter())
			return;

		String serviceName = ConfigFactory.getConfig(MonitoringConfig.class).getResponceTagConfiguration().get(responceTag);
		if (serviceName == null)
			return;
		try
		{
			MonitoringServiceGateConfig config = MonitoringGateServicesContext.getInstance().getConfig(serviceName);
			if (config == null)
				return;
			IncrementMode incrementMode = IncrementMode.EMPTY;

			MonitoringServiceGateStateConfig inaccessibleConfig = config.getInaccessibleConfig();
			if (inaccessibleConfig != null && inaccessibleConfig.isUseMonitoring())
				incrementMode = incrementMode.add(MonitoringGateState.INACCESSIBLE);

			MonitoringServiceGateStateConfig degradationConfig = config.getDegradationConfig();
			if (degradationConfig != null && degradationConfig.isUseMonitoring())
				incrementMode = incrementMode.add(MonitoringGateState.DEGRADATION);

			if (incrementMode == IncrementMode.EMPTY)
				return;
			GateSingleton.getFactory().service(IncrementMonitoringCounterService.class).incrementPercent(serviceName, incrementMode, false);
		}
		catch (Exception e)
		{
			log.error("Ќе удалось увеличить счетчик мониторинга", e);
		}
	}
}
