package com.rssl.phizic.logging.monitoring;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * агрегированная запись лога для мониторинга операций.
 *
 * @author bogdanov
 * @ created 19.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AgregateMonitoringLogEntry implements IMonitoringLogEntry, Serializable
{
	private Calendar startDate;
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
	private long count;

	/**
	 * @return тип счета.
	 */
	public String getAccountType()
	{
		return accountType;
	}

	/**
	 * @param accountType тип счета.
	 */
	public void setAccountType(String accountType)
	{
		this.accountType = accountType;
	}

	/**
	 * @return сумма операции.
	 */
	public BigDecimal getAmount()
	{
		return amount;
	}

	/**
	 * @param amount сумма операции.
	 */
	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}

	/**
	 * @return валюта операции.
	 */
	public String getAmountCurrency()
	{
		return amountCurrency;
	}

	/**
	 * @param amountCurrency валюта операции.
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
	 * @return тип операции.
	 */
	public String getDocumentType()
	{
		return documentType;
	}

	/**
	 * @param documentType тип операции.
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
	 * @return идентификатор поставщика услуг.
	 */
	public String getProviderUuid()
	{
		return providerUuid;
	}

	/**
	 * @param providerUuid идентификатор поставщика услуг.
	 */
	public void setProviderUuid(String providerUuid)
	{
		this.providerUuid = providerUuid;
	}

	public Calendar getCreationDate()
	{
		throw new UnsupportedOperationException();
	}

	public void setCreationDate(Calendar creationDate)
	{
		throw new UnsupportedOperationException();
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
	 * @return состояние документа.
	 */
	public String getStateCode()
	{
		return stateCode;
	}

	/**
	 * @param stateCode состояние документа.
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

	/**
	 * @return количество операций.
	 */
	public long getCount()
	{
		return count;
	}

	/**
	 * @param count количество операций.
	 */
	public void setCount(long count)
	{
		this.count = count;
	}
}
