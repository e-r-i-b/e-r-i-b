package com.rssl.phizic.logging.monitoring;

/**
 * ����� �� �������� � ����.
 *
 * @author bogdanov
 * @ created 30.03.15
 * @ $Author$
 * @ $Revision$
 */

public class ServicePaymentReportEntry implements ReportEntry
{
	private String serviceProviderName;
	private Long executedPaymentsCount;
	private Long createdAutoPaymentsCount;
	private Long templateExecutedPaymentsCount;
	private Long createdTemplatesCount;
	private Long basketExecutedPaymentsCount;

	private Double executedPaymentsCountNorma;
	private Double createdAutoPaymentsCountNorma;
	private Double templateExecutedPaymentsCountNorma;
	private Double createdTemplatesCountNorma;
	private Double basketExecutedPaymentsCountNorma;

	public String getName()
	{
		return serviceProviderName;
	}

	public void setName(String name)
	{
		serviceProviderName = name;
	}

	/**
	 * @return ���������� ������� ����������� �������� �����  ������� �� �����.
	 */
	public Long getBasketExecutedPaymentsCount()
	{
		return basketExecutedPaymentsCount;
	}

	/**
	 * @param basketExecutedPaymentsCount ���������� ������� ����������� �������� �����  ������� �� �����.
	 */
	public void setBasketExecutedPaymentsCount(Long basketExecutedPaymentsCount)
	{
		this.basketExecutedPaymentsCount = basketExecutedPaymentsCount;
	}

	/**
	 * @return �����: ���������� ������� ����������� �������� �����  ������� �� �����.
	 */
	public Double getBasketExecutedPaymentsCountNorma()
	{
		return basketExecutedPaymentsCountNorma;
	}

	/**
	 * @param basketExecutedPaymentsCountNorma �����: ���������� ������� ����������� �������� �����  ������� �� �����.
	 */
	public void setBasketExecutedPaymentsCountNorma(Double basketExecutedPaymentsCountNorma)
	{
		this.basketExecutedPaymentsCountNorma = basketExecutedPaymentsCountNorma;
	}

	/**
	 * @return ���������� ������� ��������� ������������ �� �����.
	 */
	public Long getCreatedAutoPaymentsCount()
	{
		return createdAutoPaymentsCount;
	}

	/**
	 * @param createdAutoPaymentsCount ���������� ������� ��������� ������������ �� �����.
	 */
	public void setCreatedAutoPaymentsCount(Long createdAutoPaymentsCount)
	{
		this.createdAutoPaymentsCount = createdAutoPaymentsCount;
	}

	/**
	 * @return �����: ���������� ������� ��������� ������������ �� �����.
	 */
	public Double getCreatedAutoPaymentsCountNorma()
	{
		return createdAutoPaymentsCountNorma;
	}

	/**
	 * @param createdAutoPaymentsCountNorma �����: ���������� ������� ��������� ������������ �� �����.
	 */
	public void setCreatedAutoPaymentsCountNorma(Double createdAutoPaymentsCountNorma)
	{
		this.createdAutoPaymentsCountNorma = createdAutoPaymentsCountNorma;
	}

	/**
	 * @return ���������� �������� �������� �� �����.
	 */
	public Long getCreatedTemplatesCount()
	{
		return createdTemplatesCount;
	}

	/**
	 * @param createdTemplatesCount ���������� �������� �������� �� �����.
	 */
	public void setCreatedTemplatesCount(Long createdTemplatesCount)
	{
		this.createdTemplatesCount = createdTemplatesCount;
	}

	/**
	 * @return �����: ���������� �������� �������� �� �����.
	 */
	public Double getCreatedTemplatesCountNorma()
	{
		return createdTemplatesCountNorma;
	}

	/**
	 * @param createdTemplatesCountNorma �����: ���������� �������� �������� �� �����.
	 */
	public void setCreatedTemplatesCountNorma(Double createdTemplatesCountNorma)
	{
		this.createdTemplatesCountNorma = createdTemplatesCountNorma;
	}

	/**
	 * @return ���������� ������� ����������� �������� �� �����.
	 */
	public Long getExecutedPaymentsCount()
	{
		return executedPaymentsCount;
	}

	/**
	 * @param executedPaymentsCount ���������� ������� ����������� �������� �� �����.
	 */
	public void setExecutedPaymentsCount(Long executedPaymentsCount)
	{
		this.executedPaymentsCount = executedPaymentsCount;
	}

	/**
	 * @return �����: ���������� ������� ����������� �������� �� �����.
	 */
	public Double getExecutedPaymentsCountNorma()
	{
		return executedPaymentsCountNorma;
	}

	/**
	 * @param executedPaymentsCountNorma �����: ���������� ������� ����������� �������� �� �����.
	 */
	public void setExecutedPaymentsCountNorma(Double executedPaymentsCountNorma)
	{
		this.executedPaymentsCountNorma = executedPaymentsCountNorma;
	}

	public String getServiceProviderName()
	{
		return serviceProviderName;
	}

	public void setServiceProviderName(String serviceProviderName)
	{
		this.serviceProviderName = serviceProviderName;
	}

	/**
	 * @return ���������� ������� ����������� �������� �� �������� �� �����.
	 */
	public Long getTemplateExecutedPaymentsCount()
	{
		return templateExecutedPaymentsCount;
	}

	/**
	 * @param templateExecutedPaymentsCount ���������� ������� ����������� �������� �� �������� �� �����.
	 */
	public void setTemplateExecutedPaymentsCount(Long templateExecutedPaymentsCount)
	{
		this.templateExecutedPaymentsCount = templateExecutedPaymentsCount;
	}

	/**
	 * @return �����: ���������� ������� ����������� �������� �� �������� �� �����.
	 */
	public Double getTemplateExecutedPaymentsCountNorma()
	{
		return templateExecutedPaymentsCountNorma;
	}

	/**
	 * @param templateExecutedPaymentsCountNorma �����: ���������� ������� ����������� �������� �� �������� �� �����.
	 */
	public void setTemplateExecutedPaymentsCountNorma(Double templateExecutedPaymentsCountNorma)
	{
		this.templateExecutedPaymentsCountNorma = templateExecutedPaymentsCountNorma;
	}
}
