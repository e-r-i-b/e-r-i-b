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
 * ��������� ����������� ������� �������
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
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ �������������
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return �������� �� ������ �������
	 */
	public boolean isAvailable()
	{
		return available;
	}

	/**
	 * ������ ����������� ������� �������
	 * @param available �������� �� ������ �������
	 */
	public void setAvailable(boolean available)
	{
		this.available = available;
	}

	/**
	 * @return ������������ �� ����������
	 */
	public boolean isUseMonitoring()
	{
	    return useMonitoring;
	}

	/**
	 * ������ ����� ������������� �����������
	 * @param useMonitoring ������������ �� ����������
	 */
	public void setUseMonitoring(boolean useMonitoring)
	{
	    this.useMonitoring = useMonitoring;
	}

	/**
	 * @return ���������� ���������� ������
	 */
	public int getMonitoringCount()
	{
	    return monitoringCount;
	}

	/**
	 * ������ ���������� ���������� ������
	 * @param monitoringCount ���������� ���������� ������
	 */
	public void setMonitoringCount(int monitoringCount)
	{
	    this.monitoringCount = monitoringCount;
	}

	/**
	 * @return ����� ����������� � �������������
	 */
	public long getMonitoringTime()
	{
	    return monitoringTime;
	}

	/**
	 * ������ ����� ����������� � �������������
	 * @param monitoringTime ����� ����������� � �������������
	 */
	public void setMonitoringTime(long monitoringTime)
	{
	    this.monitoringTime = monitoringTime;
	}

	/**
	 * @return ������� � �������������
	 */
	public long getTimeout()
	{
	    return timeout;
	}

	/**
	 * ������ ������� � �������������
	 * @param timeout ������� � �������������
	 */
	public void setTimeout(long timeout)
	{
	    this.timeout = timeout;
	}

	/**
	 * @return ������ ��������� �������
	 */
	public String getMessageText()
	{
		return messageText;
	}

	/**
	 * ������ ������ ��������� �������
	 * @param messageText ������ ��������� �������
	 */
	public void setMessageText(String messageText)
	{
		this.messageText = messageText;
	}

	/**
	 * @return ����� �������������� ������� � �������������
	 */
	public Long getRecoveryTime()
	{
		return recoveryTime;
	}

	/**
	 * ������ ����� �������������� ������� � �������������
	 * @param recoveryTime ����� �������������� ������� � �������������
	 */
	public void setRecoveryTime(Long recoveryTime)
	{
		this.recoveryTime = recoveryTime;
	}

	/**
	 * @return ����� ������� ������������������ ������ �������
	 */
	public Calendar getDeteriorationTime()
	{
		return deteriorationTime;
	}

	/**
	 * ������ ����� ������� ������������������ ������ �������
	 * @param deteriorationTime ����� ������� ������������������ ������ �������
	 */
	public void setDeteriorationTime(Calendar deteriorationTime)
	{
		this.deteriorationTime = deteriorationTime;
	}

	/**
	 * @return �������� �� �������� ��������� ��������� �������� �� ������������ �� ��
	 */
	public boolean isAvailableChangeInactiveType()
	{
		return availableChangeInactiveType;
	}

	/**
	 * ������ ����������� ��������� ��������� ��������� �������� �� ������������ �� ��
	 * @param availableChangeInactiveType ����������� ��������� ��������� ��������� �������� �� ������������ �� ��
	 */
	public void setAvailableChangeInactiveType(boolean availableChangeInactiveType)
	{
		this.availableChangeInactiveType = availableChangeInactiveType;
	}

	/**
	 * @return ��������� ��������� �������� �� ������������ �� ��
	 */
	public InactiveType getInactiveType()
	{
		return inactiveType;
	}

	/**
	 * ������ ��������� ��������� �������� �� ������������ �� ��
	 * @param inactiveType ��������� ��������� �������� �� ������������ �� ��
	 */
	public void setInactiveType(InactiveType inactiveType)
	{
		this.inactiveType = inactiveType;
	}

	/**
	 * ������ ��������� ��������� �������� �� ������������ �� ��
	 * @param inactiveType ��������� ������������� ��������� ��������� �������� �� ������������ �� ��
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
	 * @return ��������������� ���������
	 */
	public Map<String, String> getResources()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return resources;
	}

	/**
	 * @param resources ��������������� ���������
	 */
	public void setResources(Map<String, String> resources)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.resources = resources;
	}

	/**
	 * ��������� ���������
	 * @param locale ������
	 * @param resource ���������
	 */
	public void putResource(String locale, String resource)
	{
		this.resources.put(locale, resource);
	}

	/**
	 * @return ��������������� ���������
	 */
	public String getLocaledMessageText()
	{
		return StringHelper.isEmpty(resources.get(MultiLocaleContext.getLocaleId())) ? getMessageText() : resources.get(MultiLocaleContext.getLocaleId());
	}

	/**
	 * �������� ���������
	 * ������ ����������� ���������� �� ��������� � ������� ��������
	 * @param otherConfig ����� ���������
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
	 * �������� ���������
	 * ���������� ���������� ������ � ������ ��������� ����������� ������� (isAvailable())
	 * @param stateConfigSource �������� ����������
	 */
	public void updateFrom(MonitoringServiceGateStateConfig stateConfigSource)
	{
		if (isAvailable() ^ stateConfigSource.isAvailable())
			update(stateConfigSource);
	}
}
