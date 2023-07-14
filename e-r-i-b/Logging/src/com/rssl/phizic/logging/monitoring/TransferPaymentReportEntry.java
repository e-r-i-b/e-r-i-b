package com.rssl.phizic.logging.monitoring;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Отчет по переводам в СБОЛ.
 *
 * @author bogdanov
 * @ created 30.03.15
 * @ $Author$
 * @ $Revision$
 */

public class TransferPaymentReportEntry implements ReportEntry
{
	private MonitoringDocumentType documentType;
	private Long executedTransferCount;
	private BigDecimal totalAmount;
	private Map<String, Long> executedTransferForTbCount;
	private Map<String, BigDecimal> totalAmountForTb;

	private Double executedTransferCountNorma;
	private BigDecimal totalAmountNorma;
	private Map<String, Double> executedTransferForTbCountNorma;
	private Map<String, BigDecimal> totalAmountForTbNorma;

	public String getName()
	{
		switch (documentType)
		{
			case AA: return "Вклад-Вклад";
			case CA: return "Карта-Вклад";
			case AC: return "Вклад-Карта";
			case CAOTB: return "Карта на счет в Сбербанке";
			case CAOB: return "Карта на счет в другом банке";
			case CCS: return "Карта-Карта Сбербанка";
			case CCNS: return "Карта-Карта не Сбербанка (ММС, VMT)";
		}

		throw new IllegalArgumentException("Тип " + documentType.name() + " не поддерживается классом " + getClass().getName());
	}

	public void setName(String name)
	{
	}

	/**
	 * @return тип документа.
	 */
	public MonitoringDocumentType getDocumentType()
	{
		return documentType;
	}

	/**
	 * @param documentType тип документа.
	 */
	public void setDocumentType(MonitoringDocumentType documentType)
	{
		this.documentType = documentType;
	}

	/**
	 * @return Общее количество успешно выполненных переводов.
	 */
	public Long getExecutedTransferCount()
	{
		return executedTransferCount;
	}

	/**
	 * @param executedTransferCount Общее количество успешно выполненных переводов.
	 */
	public void setExecutedTransferCount(Long executedTransferCount)
	{
		this.executedTransferCount = executedTransferCount;
	}

	/**
	 * @return Норма: Общее количество успешно выполненных переводов.
	 */
	public Double getExecutedTransferCountNorma()
	{
		return executedTransferCountNorma;
	}

	/**
	 * @param executedTransferCountNorma Норма: Общее количество успешно выполненных переводов.
	 */
	public void setExecutedTransferCountNorma(Double executedTransferCountNorma)
	{
		this.executedTransferCountNorma = executedTransferCountNorma;
	}

	/**
	 * @return Общая сумма успешно выполненных переводов
	 */
	public BigDecimal getTotalAmount()
	{
		return totalAmount;
	}

	/**
	 * @param totalAmount Общая сумма успешно выполненных переводов
	 */
	public void setTotalAmount(BigDecimal totalAmount)
	{
		this.totalAmount = totalAmount;
	}

	/**
	 * @return Норма: Общая сумма успешно выполненных переводов
	 */
	public BigDecimal getTotalAmountNorma()
	{
		return totalAmountNorma;
	}

	/**
	 * @param totalAmountNorma Норма: Общая сумма успешно выполненных переводов.
	 */
	public void setTotalAmountNorma(BigDecimal totalAmountNorma)
	{
		this.totalAmountNorma = totalAmountNorma;
	}

	/**
	 * @return Общее количество успешно выполненных переводов для ТБ.
	 */
	public Map<String, Long> getExecutedTransferForTbCount()
	{
		return executedTransferForTbCount;
	}

	/**
	 * @param executedTransferForTbCount Общее количество успешно выполненных переводов для ТБ.
	 */
	public void setExecutedTransferForTbCount(Map<String, Long> executedTransferForTbCount)
	{
		this.executedTransferForTbCount = executedTransferForTbCount;
	}

	/**
	 * @return Норма: общее количество успешно выполненных переводов для ТБ.
	 */
	public Map<String, Double> getExecutedTransferForTbCountNorma()
	{
		return executedTransferForTbCountNorma;
	}

	/**
	 * @param executedTransferForTbCountNorma Норма: Общее количество успешно выполненных переводов для ТБ.
	 */
	public void setExecutedTransferForTbCountNorma(Map<String, Double> executedTransferForTbCountNorma)
	{
		this.executedTransferForTbCountNorma = executedTransferForTbCountNorma;
	}

	/**
	 * @return Общая сумма успешно выполненных переводов по ТБ.
	 */
	public Map<String, BigDecimal> getTotalAmountForTb()
	{
		return totalAmountForTb;
	}

	/**
	 * @param totalAmountForTb Общая сумма успешно выполненных переводов по ТБ.
	 */
	public void setTotalAmountForTb(Map<String, BigDecimal> totalAmountForTb)
	{
		this.totalAmountForTb = totalAmountForTb;
	}

	/**
	 * @return Норма: Общая сумма успешно выполненных переводов по ТБ.
	 */
	public Map<String, BigDecimal> getTotalAmountForTbNorma()
	{
		return totalAmountForTbNorma;
	}

	/**
	 * @param totalAmountForTbNorma Норма: Общая сумма успешно выполненных переводов по ТБ.
	 */
	public void setTotalAmountForTbNorma(Map<String, BigDecimal> totalAmountForTbNorma)
	{
		this.totalAmountForTbNorma = totalAmountForTbNorma;
	}
}
