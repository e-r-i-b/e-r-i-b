package com.rssl.phizic.logging.monitoring;

/**
 * Отчет по платежам в СБОЛ.
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
	 * @return Количество успешно выполненных платежей через  корзину за сутки.
	 */
	public Long getBasketExecutedPaymentsCount()
	{
		return basketExecutedPaymentsCount;
	}

	/**
	 * @param basketExecutedPaymentsCount Количество успешно выполненных платежей через  корзину за сутки.
	 */
	public void setBasketExecutedPaymentsCount(Long basketExecutedPaymentsCount)
	{
		this.basketExecutedPaymentsCount = basketExecutedPaymentsCount;
	}

	/**
	 * @return Норма: количество успешно выполненных платежей через  корзину за сутки.
	 */
	public Double getBasketExecutedPaymentsCountNorma()
	{
		return basketExecutedPaymentsCountNorma;
	}

	/**
	 * @param basketExecutedPaymentsCountNorma Норма: количество успешно выполненных платежей через  корзину за сутки.
	 */
	public void setBasketExecutedPaymentsCountNorma(Double basketExecutedPaymentsCountNorma)
	{
		this.basketExecutedPaymentsCountNorma = basketExecutedPaymentsCountNorma;
	}

	/**
	 * @return Количество успешно созданных автоплатежей за сутки.
	 */
	public Long getCreatedAutoPaymentsCount()
	{
		return createdAutoPaymentsCount;
	}

	/**
	 * @param createdAutoPaymentsCount Количество успешно созданных автоплатежей за сутки.
	 */
	public void setCreatedAutoPaymentsCount(Long createdAutoPaymentsCount)
	{
		this.createdAutoPaymentsCount = createdAutoPaymentsCount;
	}

	/**
	 * @return Норма: количество успешно созданных автоплатежей за сутки.
	 */
	public Double getCreatedAutoPaymentsCountNorma()
	{
		return createdAutoPaymentsCountNorma;
	}

	/**
	 * @param createdAutoPaymentsCountNorma Норма: количество успешно созданных автоплатежей за сутки.
	 */
	public void setCreatedAutoPaymentsCountNorma(Double createdAutoPaymentsCountNorma)
	{
		this.createdAutoPaymentsCountNorma = createdAutoPaymentsCountNorma;
	}

	/**
	 * @return Количество созданых шаблонов за сутки.
	 */
	public Long getCreatedTemplatesCount()
	{
		return createdTemplatesCount;
	}

	/**
	 * @param createdTemplatesCount Количество созданых шаблонов за сутки.
	 */
	public void setCreatedTemplatesCount(Long createdTemplatesCount)
	{
		this.createdTemplatesCount = createdTemplatesCount;
	}

	/**
	 * @return Норма: Количество созданых шаблонов за сутки.
	 */
	public Double getCreatedTemplatesCountNorma()
	{
		return createdTemplatesCountNorma;
	}

	/**
	 * @param createdTemplatesCountNorma Норма: Количество созданых шаблонов за сутки.
	 */
	public void setCreatedTemplatesCountNorma(Double createdTemplatesCountNorma)
	{
		this.createdTemplatesCountNorma = createdTemplatesCountNorma;
	}

	/**
	 * @return Количество успешно выполненных платежей за сутки.
	 */
	public Long getExecutedPaymentsCount()
	{
		return executedPaymentsCount;
	}

	/**
	 * @param executedPaymentsCount Количество успешно выполненных платежей за сутки.
	 */
	public void setExecutedPaymentsCount(Long executedPaymentsCount)
	{
		this.executedPaymentsCount = executedPaymentsCount;
	}

	/**
	 * @return Норма: Количество успешно выполненных платежей за сутки.
	 */
	public Double getExecutedPaymentsCountNorma()
	{
		return executedPaymentsCountNorma;
	}

	/**
	 * @param executedPaymentsCountNorma Норма: Количество успешно выполненных платежей за сутки.
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
	 * @return Количество успешно выполненных платежей по шаблонам за сутки.
	 */
	public Long getTemplateExecutedPaymentsCount()
	{
		return templateExecutedPaymentsCount;
	}

	/**
	 * @param templateExecutedPaymentsCount Количество успешно выполненных платежей по шаблонам за сутки.
	 */
	public void setTemplateExecutedPaymentsCount(Long templateExecutedPaymentsCount)
	{
		this.templateExecutedPaymentsCount = templateExecutedPaymentsCount;
	}

	/**
	 * @return Норма: Количество успешно выполненных платежей по шаблонам за сутки.
	 */
	public Double getTemplateExecutedPaymentsCountNorma()
	{
		return templateExecutedPaymentsCountNorma;
	}

	/**
	 * @param templateExecutedPaymentsCountNorma Норма: Количество успешно выполненных платежей по шаблонам за сутки.
	 */
	public void setTemplateExecutedPaymentsCountNorma(Double templateExecutedPaymentsCountNorma)
	{
		this.templateExecutedPaymentsCountNorma = templateExecutedPaymentsCountNorma;
	}
}
