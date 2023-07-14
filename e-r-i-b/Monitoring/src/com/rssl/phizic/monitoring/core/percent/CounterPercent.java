package com.rssl.phizic.monitoring.core.percent;

/**
 * ������� ����������� ������� ������� (��������)
 * @author Jatsky
 * @ created 03.02.15
 * @ $Author$
 * @ $Revision$
 */

public abstract class CounterPercent
{
	protected final Object locker = new Object();

	protected final MonitoringGateServiceStateConfigurationPercent configuration;

	protected volatile int maxPercent;

	protected volatile long interval;
	protected volatile boolean completed = false; //������ �� ��������� �������

	/**
	 * �����������
	 * @param configuration ������������ �����������
	 */
	CounterPercent(MonitoringGateServiceStateConfigurationPercent configuration)
	{
		this.configuration = configuration;
		refresh();
	}

	/**
	 * ���������� ��������
	 */
	public abstract void inc(boolean isFail);

	abstract void refresh();

	void stopCounter()
	{
		completed = true;
	}

	void runCounter()
	{
		completed = false;
	}

	protected void complete()
	{
		completed = true;
		configuration.reachState();
	}
}
