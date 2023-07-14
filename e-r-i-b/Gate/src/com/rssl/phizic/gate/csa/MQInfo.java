package com.rssl.phizic.gate.csa;

/**
 * @author osminin
 * @ created 04.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Информация об очереди сообщений
 */
public final class MQInfo
{
	private String queueName;
	private String factoryName;

	/**
	 * ctor
	 * @param queueName наименование очереди
	 * @param factoryName наименование фабрики
	 */
	public MQInfo(String queueName, String factoryName)
	{
		this.queueName = queueName;
		this.factoryName = factoryName;
	}

	/**
	 * @return наименование очереди
	 */
	public String getQueueName()
	{
		return queueName;
	}

	/**
	 * @param queueName наименование очереди
	 */
	public void setQueueName(String queueName)
	{
		this.queueName = queueName;
	}

	/**
	 * @return наименование фабрики
	 */
	public String getFactoryName()
	{
		return factoryName;
	}

	/**
	 * @param factoryName наименование фабрики
	 */
	public void setFactoryName(String factoryName)
	{
		this.factoryName = factoryName;
	}

	@Override
	public int hashCode()
	{
		return getFactoryName().hashCode() + getQueueName().hashCode();
	}
}
