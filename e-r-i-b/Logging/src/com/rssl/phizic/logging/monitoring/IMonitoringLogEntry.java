package com.rssl.phizic.logging.monitoring;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * запись лога для мониторинга операций.
 *
 * @author bogdanov
 * @ created 19.02.15
 * @ $Author$
 * @ $Revision$
 */
public interface IMonitoringLogEntry extends MonitoringEntry, Serializable
{
	/**
	 * @return тип счета.
	 */
	public String getAccountType();

	/**
	 * @param accountType тип счета.
	 */
	public void setAccountType(String accountType);

	/**
	 * @return сумма операции.
	 */
	public BigDecimal getAmount();

	/**
	 * @param amount сумма операции.
	 */
	public void setAmount(BigDecimal amount);

	/**
	 * @return валюта операции.
	 */
	public String getAmountCurrency();

	/**
	 * @param amountCurrency валюта операции.
	 */
	public void setAmountCurrency(String amountCurrency);

	/**
	 * @return тип операции.
	 */
	public String getDocumentType();

	/**
	 * @param documentType тип операции.
	 */
	public void setDocumentType(String documentType);

	/**
	 * @return идентификатор поставщика услуг.
	 */
	public String getProviderUuid();

	/**
	 * @param providerUuid идентификатор поставщика услуг.
	 */
	public void setProviderUuid(String providerUuid);

	/**
	 * @return дата создания документа.
	 */
	public Calendar getCreationDate();

	/**
	 * @param creationDate дата создания документа.
	 */
	public void setCreationDate(Calendar creationDate);

	/**
	 * @return состояние документа.
	 */
	public String getStateCode();

	/**
	 * @param stateCode состояние документа.
	 */
	public void setStateCode(String stateCode);
}
