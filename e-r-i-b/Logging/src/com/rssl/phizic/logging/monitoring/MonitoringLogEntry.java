package com.rssl.phizic.logging.monitoring;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * ������ ���� ��� ����������� ��������.
 *
 * @author bogdanov
 * @ created 19.02.15
 * @ $Author$
 * @ $Revision$
 */
public class MonitoringLogEntry implements IMonitoringLogEntry, Serializable
{
	private Calendar startDate;
	private Calendar creationDate;
	private String providerUuid;
	private String documentType;
	private String accountType;
	private BigDecimal amount;
	private String amountCurrency;
	private String application;
	private String tb;
	private String platform;
	private String stateCode;
	private long nodeId;

	/**
	 * @return ��� �����.
	 */
	public String getAccountType()
	{
		return accountType;
	}

	/**
	 * @param accountType ��� �����.
	 */
	public void setAccountType(String accountType)
	{
		this.accountType = accountType;
	}

	/**
	 * @return ����� ��������.
	 */
	public BigDecimal getAmount()
	{
		return amount;
	}

	/**
	 * @param amount ����� ��������.
	 */
	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	/**
	 * @return ������ ��������.
	 */
	public String getAmountCurrency()
	{
		return amountCurrency;
	}

	/**
	 * @param amountCurrency ������ ��������.
	 */
	public void setAmountCurrency(String amountCurrency)
	{
		this.amountCurrency = amountCurrency;
	}

	public String getApplication()
	{
		return application;
	}

	public void setApplication(String application)
	{
		this.application = application;
	}

	/**
	 * @return ��� ��������.
	 */
	public String getDocumentType()
	{
		return documentType;
	}

	/**
	 * @param documentType ��� ��������.
	 */
	public void setDocumentType(String documentType)
	{
		this.documentType = documentType;
	}

	public long getNodeId()
	{
		return nodeId;
	}

	public void setNodeId(long nodeId)
	{
		this.nodeId = nodeId;
	}

	public String getPlatform()
	{
		return platform;
	}

	public void setPlatform(String platform)
	{
		this.platform = platform;
	}

	/**
	 * @return ������������� ���������� �����.
	 */
	public String getProviderUuid()
	{
		return providerUuid;
	}

	/**
	 * @param providerUuid ������������� ���������� �����.
	 */
	public void setProviderUuid(String providerUuid)
	{
		this.providerUuid = providerUuid;
	}

	/**
	 * @return ���� �������� ���������.
	 */
	public Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @param creationDate ���� �������� ���������.
	 */
	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	public Calendar getStartDate()
	{
		return startDate;
	}

	public void setStartDate(Calendar startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @return ��������� ���������.
	 */
	public String getStateCode()
	{
		return stateCode;
	}

	/**
	 * @param stateCode ��������� ���������.
	 */
	public void setStateCode(String stateCode)
	{
		this.stateCode = stateCode;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}
}
