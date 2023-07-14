package com.rssl.phizic.gate.monitoring;

import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 *
 * Настройки мониторинга статуса сервиса
 */

public class MonitoringServiceGateStateConfig implements Serializable
{
	private Long id;

	private boolean available;
	private boolean useMonitoring;

	private int monitoringCount;
	private long monitoringTime;
	private long timeout;

	private String messageText;
	private Long recoveryTime;
	private Calendar deteriorationTime;

	private boolean availableChangeInactiveType;
	private InactiveType inactiveType;

	private int monitoringErrorPercent;

	private Map<String, String> resources = new HashMap<String, String>();

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return доступен ли статус сервису
	 */
	public boolean isAvailable()
	{
		return available;
	}

	/**
	 * задать доступность статуса сервису
	 * @param available доступен ли статус сервису
	 */
	public void setAvailable(boolean available)
	{
		this.available = available;
	}

	/**
	 * @return использовать ли мониторинг
	 */
	public boolean isUseMonitoring()
	{
	    return useMonitoring;
	}

	/**
	 * задать режим использования мониторинга
	 * @param useMonitoring использовать ли мониторинг
	 */
	public void setUseMonitoring(boolean useMonitoring)
	{
	    this.useMonitoring = useMonitoring;
	}

	/**
	 * @return предельное количество ошибок
	 */
	public int getMonitoringCount()
	{
	    return monitoringCount;
	}

	/**
	 * задать предельное количество ошибок
	 * @param monitoringCount предельное количество ошибок
	 */
	public void setMonitoringCount(int monitoringCount)
	{
	    this.monitoringCount = monitoringCount;
	}

	/**
	 * @return время мониторинга в миллисекундах
	 */
	public long getMonitoringTime()
	{
	    return monitoringTime;
	}

	/**
	 * задать время мониторинга в миллисекундах
	 * @param monitoringTime время мониторинга в миллисекундах
	 */
	public void setMonitoringTime(long monitoringTime)
	{
	    this.monitoringTime = monitoringTime;
	}

	/**
	 * @return таймаут в миллисекундах
	 */
	public long getTimeout()
	{
	    return timeout;
	}

	/**
	 * задать таймаут в миллисекундах
	 * @param timeout таймаут в миллисекундах
	 */
	public void setTimeout(long timeout)
	{
	    this.timeout = timeout;
	}

	/**
	 * @return шаблон сообщения клиенту
	 */
	public String getMessageText()
	{
		return messageText;
	}

	/**
	 * задать шаблон сообщения клиенту
	 * @param messageText шаблон сообщения клиенту
	 */
	public void setMessageText(String messageText)
	{
		this.messageText = messageText;
	}

	/**
	 * @return время восстановления сервиса в миллисекундах
	 */
	public Long getRecoveryTime()
	{
		return recoveryTime;
	}

	/**
	 * задать время восстановления сервиса в миллисекундах
	 * @param recoveryTime время восстановления сервиса в миллисекундах
	 */
	public void setRecoveryTime(Long recoveryTime)
	{
		this.recoveryTime = recoveryTime;
	}

	/**
	 * @return время падения производительности работы сервиса
	 */
	public Calendar getDeteriorationTime()
	{
		return deteriorationTime;
	}

	/**
	 * задать время падения производительности работы сервиса
	 * @param deteriorationTime время падения производительности работы сервиса
	 */
	public void setDeteriorationTime(Calendar deteriorationTime)
	{
		this.deteriorationTime = deteriorationTime;
	}

	/**
	 * @return возможно ли изменять стратегию обработки запросов не отправляемых во ВС
	 */
	public boolean isAvailableChangeInactiveType()
	{
		return availableChangeInactiveType;
	}

	/**
	 * задать возможность изменения стратегии обработки запросов не отправляемых во ВС
	 * @param availableChangeInactiveType возможность изменения стратегии обработки запросов не отправляемых во ВС
	 */
	public void setAvailableChangeInactiveType(boolean availableChangeInactiveType)
	{
		this.availableChangeInactiveType = availableChangeInactiveType;
	}

	/**
	 * @return стратегия обработки запросов не отправляемых во ВС
	 */
	public InactiveType getInactiveType()
	{
		return inactiveType;
	}

	/**
	 * задать стратегию обработки запросов не отправляемых во ВС
	 * @param inactiveType стратегия обработки запросов не отправляемых во ВС
	 */
	public void setInactiveType(InactiveType inactiveType)
	{
		this.inactiveType = inactiveType;
	}

	/**
	 * задать стратегию обработки запросов не отправляемых во ВС
	 * @param inactiveType строковое представление стратегии обработки запросов не отправляемых во ВС
	 */
	public void setInactiveType(String inactiveType)
	{
		this.inactiveType = InactiveType.valueOf(inactiveType);
	}

	public int getMonitoringErrorPercent()
	{
		return monitoringErrorPercent;
	}

	public void setMonitoringErrorPercent(int monitoringErrorPercent)
	{
		this.monitoringErrorPercent = monitoringErrorPercent;
	}

	/**
	 * @return локалезависимые текстовки
	 */
	public Map<String, String> getResources()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return resources;
	}

	/**
	 * @param resources локалезависимые текстовки
	 */
	public void setResources(Map<String, String> resources)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.resources = resources;
	}

	/**
	 * добавляет текстовку
	 * @param locale локаль
	 * @param resource текстовка
	 */
	public void putResource(String locale, String resource)
	{
		this.resources.put(locale, resource);
	}

	/**
	 * @return локалезависимая текстовка
	 */
	public String getLocaledMessageText()
	{
		return StringHelper.isEmpty(resources.get(MultiLocaleContext.getLocaleId())) ? getMessageText() : resources.get(MultiLocaleContext.getLocaleId());
	}

	/**
	 * обновить настройки
	 * полное копирование параметров из источника в текущую сущность
	 * @param otherConfig новые настройки
	 */
	public void update(MonitoringServiceGateStateConfig otherConfig)
	{
		setAvailable(otherConfig.isAvailable());
		setUseMonitoring(otherConfig.isUseMonitoring());

		setMonitoringCount(otherConfig.getMonitoringCount());
		setMonitoringTime(otherConfig.getMonitoringTime());
		setTimeout(otherConfig.getTimeout());

		setMessageText(otherConfig.getMessageText());
		setResources(otherConfig.getResources());
		setRecoveryTime(otherConfig.getRecoveryTime());
		setDeteriorationTime(otherConfig.getDeteriorationTime());

		setAvailableChangeInactiveType(otherConfig.isAvailableChangeInactiveType());
		setInactiveType(otherConfig.getInactiveType());
		setMonitoringErrorPercent(otherConfig.getMonitoringErrorPercent());
	}

	/**
	 * обновить настройки
	 * обновление происходит только в случае изменения доступности статуса (isAvailable())
	 * @param stateConfigSource источник обновления
	 */
	public void updateFrom(MonitoringServiceGateStateConfig stateConfigSource)
	{
		if (isAvailable() ^ stateConfigSource.isAvailable())
			update(stateConfigSource);
	}
}
