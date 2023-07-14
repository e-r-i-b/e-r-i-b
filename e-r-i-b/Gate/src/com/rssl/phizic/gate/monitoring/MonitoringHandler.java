package com.rssl.phizic.gate.monitoring;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author mihaylov
 * @ created 06.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ќбработчик данных об ответах внешних систем дл€ целей мониторинга
 */
public abstract class MonitoringHandler
{
	private static final Object LOCKER = new Object();
	private static final String MONITORING_HANDLER_CLASS_NAME = "com.rssl.phizic.gate.monitoring.MonitoringHandler";

	private static volatile MonitoringHandler handler = null;

	/**
	 * ќбработать данные о выполнении запроса во внешнюю систему
	 * @param requestTag - тип запроса(название тега)
	 * @param time - врем€ выполнени€ запроса во внешнюю систему(в миллисекундах)
	 * @param errorCode - код ошибки обработки запроса во внешней системе
	 */
	public abstract void processRequest(String requestTag, long time, String errorCode);

	/**
	 * ѕроверить сервис на доступность.
	 * @param requestTag - тип запроса(название тега)
	 */
	public abstract void checkRequest(String requestTag) throws GateLogicException;

	/**
	 * ќбработать данные об ошибке
	 * @param requestTag - тип запроса(название тега)
	 */
	public abstract void processError(String requestTag);

	/**
	 * ќбработать сообщение об ошибки из ответа от внешней системы
	 * @param responceTag - тип ответа от внешней системы
	 * @param errorCode - код ошибки
	 */
	public abstract void processResponce(String responceTag,String errorCode);

	/**
	 * ќбработать сообщение успешный ответ от внешней системы
	 * @param responceTag - тип ответа от внешней системы
	 */
	public abstract void processOk(String responceTag);

	/**
	 * ѕолучение хендлера мониторинга дл€ текущего гейта
	 * @return
	 * @throws GateException
	 */
	public static MonitoringHandler getInstance() throws GateException
	{
		if(handler == null)
		{
			synchronized (LOCKER)
			{
				if(handler == null)
				{
					String handlerClassName = ConfigFactory.getConfig(GateConnectionConfig.class).getProperty(MONITORING_HANDLER_CLASS_NAME);
					if(StringHelper.isEmpty(handlerClassName))
						handler = new EmptyMonitoringHandler();
					else
					{
						try
						{
							Class<MonitoringHandler> handlerClass = ClassHelper.loadClass(handlerClassName);
							handler = handlerClass.newInstance();
						}
						catch (Exception e)
						{
							throw new GateException(e);
						}
					}
				}
			}
		}
		return handler;
	}
}
