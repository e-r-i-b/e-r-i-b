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
public interface IMonitoringLogEntry extends MonitoringEntry, Serializable
{
	/**
	 * @return ��� �����.
	 */
	public String getAccountType();

	/**
	 * @param accountType ��� �����.
	 */
	public void setAccountType(String accountType);

	/**
	 * @return ����� ��������.
	 */
	public BigDecimal getAmount();

	/**
	 * @param amount ����� ��������.
	 */
	public void setAmount(BigDecimal amount);

	/**
	 * @return ������ ��������.
	 */
	public String getAmountCurrency();

	/**
	 * @param amountCurrency ������ ��������.
	 */
	public void setAmountCurrency(String amountCurrency);

	/**
	 * @return ��� ��������.
	 */
	public String getDocumentType();

	/**
	 * @param documentType ��� ��������.
	 */
	public void setDocumentType(String documentType);

	/**
	 * @return ������������� ���������� �����.
	 */
	public String getProviderUuid();

	/**
	 * @param providerUuid ������������� ���������� �����.
	 */
	public void setProviderUuid(String providerUuid);

	/**
	 * @return ���� �������� ���������.
	 */
	public Calendar getCreationDate();

	/**
	 * @param creationDate ���� �������� ���������.
	 */
	public void setCreationDate(Calendar creationDate);

	/**
	 * @return ��������� ���������.
	 */
	public String getStateCode();

	/**
	 * @param stateCode ��������� ���������.
	 */
	public void setStateCode(String stateCode);
}
