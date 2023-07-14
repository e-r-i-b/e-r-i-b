package com.rssl.phizic.logging.monitoring;

import java.math.BigDecimal;
import java.util.Map;

/**
 * ����� �� ��������� � ����.
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
			case AA: return "�����-�����";
			case CA: return "�����-�����";
			case AC: return "�����-�����";
			case CAOTB: return "����� �� ���� � ���������";
			case CAOB: return "����� �� ���� � ������ �����";
			case CCS: return "�����-����� ���������";
			case CCNS: return "�����-����� �� ��������� (���, VMT)";
		}

		throw new IllegalArgumentException("��� " + documentType.name() + " �� �������������� ������� " + getClass().getName());
	}

	public void setName(String name)
	{
	}

	/**
	 * @return ��� ���������.
	 */
	public MonitoringDocumentType getDocumentType()
	{
		return documentType;
	}

	/**
	 * @param documentType ��� ���������.
	 */
	public void setDocumentType(MonitoringDocumentType documentType)
	{
		this.documentType = documentType;
	}

	/**
	 * @return ����� ���������� ������� ����������� ���������.
	 */
	public Long getExecutedTransferCount()
	{
		return executedTransferCount;
	}

	/**
	 * @param executedTransferCount ����� ���������� ������� ����������� ���������.
	 */
	public void setExecutedTransferCount(Long executedTransferCount)
	{
		this.executedTransferCount = executedTransferCount;
	}

	/**
	 * @return �����: ����� ���������� ������� ����������� ���������.
	 */
	public Double getExecutedTransferCountNorma()
	{
		return executedTransferCountNorma;
	}

	/**
	 * @param executedTransferCountNorma �����: ����� ���������� ������� ����������� ���������.
	 */
	public void setExecutedTransferCountNorma(Double executedTransferCountNorma)
	{
		this.executedTransferCountNorma = executedTransferCountNorma;
	}

	/**
	 * @return ����� ����� ������� ����������� ���������
	 */
	public BigDecimal getTotalAmount()
	{
		return totalAmount;
	}

	/**
	 * @param totalAmount ����� ����� ������� ����������� ���������
	 */
	public void setTotalAmount(BigDecimal totalAmount)
	{
		this.totalAmount = totalAmount;
	}

	/**
	 * @return �����: ����� ����� ������� ����������� ���������
	 */
	public BigDecimal getTotalAmountNorma()
	{
		return totalAmountNorma;
	}

	/**
	 * @param totalAmountNorma �����: ����� ����� ������� ����������� ���������.
	 */
	public void setTotalAmountNorma(BigDecimal totalAmountNorma)
	{
		this.totalAmountNorma = totalAmountNorma;
	}

	/**
	 * @return ����� ���������� ������� ����������� ��������� ��� ��.
	 */
	public Map<String, Long> getExecutedTransferForTbCount()
	{
		return executedTransferForTbCount;
	}

	/**
	 * @param executedTransferForTbCount ����� ���������� ������� ����������� ��������� ��� ��.
	 */
	public void setExecutedTransferForTbCount(Map<String, Long> executedTransferForTbCount)
	{
		this.executedTransferForTbCount = executedTransferForTbCount;
	}

	/**
	 * @return �����: ����� ���������� ������� ����������� ��������� ��� ��.
	 */
	public Map<String, Double> getExecutedTransferForTbCountNorma()
	{
		return executedTransferForTbCountNorma;
	}

	/**
	 * @param executedTransferForTbCountNorma �����: ����� ���������� ������� ����������� ��������� ��� ��.
	 */
	public void setExecutedTransferForTbCountNorma(Map<String, Double> executedTransferForTbCountNorma)
	{
		this.executedTransferForTbCountNorma = executedTransferForTbCountNorma;
	}

	/**
	 * @return ����� ����� ������� ����������� ��������� �� ��.
	 */
	public Map<String, BigDecimal> getTotalAmountForTb()
	{
		return totalAmountForTb;
	}

	/**
	 * @param totalAmountForTb ����� ����� ������� ����������� ��������� �� ��.
	 */
	public void setTotalAmountForTb(Map<String, BigDecimal> totalAmountForTb)
	{
		this.totalAmountForTb = totalAmountForTb;
	}

	/**
	 * @return �����: ����� ����� ������� ����������� ��������� �� ��.
	 */
	public Map<String, BigDecimal> getTotalAmountForTbNorma()
	{
		return totalAmountForTbNorma;
	}

	/**
	 * @param totalAmountForTbNorma �����: ����� ����� ������� ����������� ��������� �� ��.
	 */
	public void setTotalAmountForTbNorma(Map<String, BigDecimal> totalAmountForTbNorma)
	{
		this.totalAmountForTbNorma = totalAmountForTbNorma;
	}
}
