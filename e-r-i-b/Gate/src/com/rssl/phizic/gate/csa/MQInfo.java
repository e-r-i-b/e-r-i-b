package com.rssl.phizic.gate.csa;

/**
 * @author osminin
 * @ created 04.11.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �� ������� ���������
 */
public final class MQInfo
{
	private String queueName;
	private String factoryName;

	/**
	 * ctor
	 * @param queueName ������������ �������
	 * @param factoryName ������������ �������
	 */
	public MQInfo(String queueName, String factoryName)
	{
		this.queueName = queueName;
		this.factoryName = factoryName;
	}

	/**
	 * @return ������������ �������
	 */
	public String getQueueName()
	{
		return queueName;
	}

	/**
	 * @param queueName ������������ �������
	 */
	public void setQueueName(String queueName)
	{
		this.queueName = queueName;
	}

	/**
	 * @return ������������ �������
	 */
	public String getFactoryName()
	{
		return factoryName;
	}

	/**
	 * @param factoryName ������������ �������
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
