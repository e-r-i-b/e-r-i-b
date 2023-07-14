package com.rssl.phizic.business.ext.sbrf.deposits.locale;

import com.rssl.phizic.locale.dynamic.resources.LanguageResource;

/**
 * �������������� ������� ��� ������ �� ������� FIELD_TDOG ����������� ��� ��� (��������� ��� ��������� ������� �� �������)
 * @author lepihina
 * @ created 09.06.15
 * $Author$
 * $Revision$
 */
public class DepositsTDOGResources extends LanguageResource
{
	private Long id;
	// ����� ������ (SUM_VKL)
	private String sum;
	// ������ ������ (CURR_VKL)
	private String currency;
	// ���� �������� ������ (QSROK)
	private String period;
	// ���� ��������� ����� �������� ������ (DATA_END)
	private String dateEnd;
	// ���������� ������ �� ������ (PERCENT)
	private String rate;
	// ��������� �������� �� ������ (PRIXOD)
	private String incomingTransactions;
	// ����������� ������ ��������������� ������ (MIN_ADD)
	private String minAdditionalFee;
	// ������������� �������� �������������� ������� (PER_ADD)
	private String additionalFeePeriod;
	// ��������� �������� �� ������ (RASXOD)
	private String debitTransactions;
	// ����� ������������ ������� (SUM_NOST)
	private String sumMinBalance;
	// ������������� ������� ��������� (PER_PERCENT)
	private String frequencyPercent;
	// ������� ������ ��������� (ORD_PERCENT)
	private String percentOrder;
	// ������� ���������� ������ ��� ��������� ������������� ������ (ORD_DOXOD)
	private String incomeOrder;
	// ���������� ����������� �������� �� ����� ���� (QPROL)
	private String renewals;

	/**
	 * @return ����� ������
	 */
	public String getSum()
	{
		return sum;
	}

	/**
	 * @param sum ����� ������
	 */
	public void setSum(String sum)
	{
		this.sum = sum;
	}

	/**
	 * @return ������ ������
	 */
	public String getCurrency()
	{
		return currency;
	}

	/**
	 * @param currency ������ ������
	 */
	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	/**
	 * @return ���� �������� ������
	 */
	public String getPeriod()
	{
		return period;
	}

	/**
	 * @param period ���� �������� ������
	 */
	public void setPeriod(String period)
	{
		this.period = period;
	}

	/**
	 * @return ���� ��������� ����� �������� ������
	 */
	public String getDateEnd()
	{
		return dateEnd;
	}

	/**
	 * @param dateEnd ���� ��������� ����� �������� ������
	 */
	public void setDateEnd(String dateEnd)
	{
		this.dateEnd = dateEnd;
	}

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ���������� ������ �� ������
	 */
	public String getRate()
	{
		return rate;
	}

	/**
	 * @param rate ���������� ������ �� ������
	 */
	public void setRate(String rate)
	{
		this.rate = rate;
	}

	/**
	 * @return ��������� �������� �� ������
	 */
	public String getIncomingTransactions()
	{
		return incomingTransactions;
	}

	/**
	 * @param incomingTransactions ��������� �������� �� ������
	 */
	public void setIncomingTransactions(String incomingTransactions)
	{
		this.incomingTransactions = incomingTransactions;
	}

	/**
	 * @return ����������� ������ ��������������� ������
	 */
	public String getMinAdditionalFee()
	{
		return minAdditionalFee;
	}

	/**
	 * @param minAdditionalFee ����������� ������ ��������������� ������
	 */
	public void setMinAdditionalFee(String minAdditionalFee)
	{
		this.minAdditionalFee = minAdditionalFee;
	}

	/**
	 * @return ������������� �������� �������������� �������
	 */
	public String getAdditionalFeePeriod()
	{
		return additionalFeePeriod;
	}

	/**
	 * @param additionalFeePeriod ������������� �������� �������������� �������
	 */
	public void setAdditionalFeePeriod(String additionalFeePeriod)
	{
		this.additionalFeePeriod = additionalFeePeriod;
	}

	/**
	 * @return ��������� �������� �� ������
	 */
	public String getDebitTransactions()
	{
		return debitTransactions;
	}

	/**
	 * @param debitTransactions ��������� �������� �� ������
	 */
	public void setDebitTransactions(String debitTransactions)
	{
		this.debitTransactions = debitTransactions;
	}

	/**
	 * @return ����� ������������ �������
	 */
	public String getSumMinBalance()
	{
		return sumMinBalance;
	}

	/**
	 * @param sumMinBalance ����� ������������ �������
	 */
	public void setSumMinBalance(String sumMinBalance)
	{
		this.sumMinBalance = sumMinBalance;
	}

	/**
	 * @return ������������� ������� ���������
	 */
	public String getFrequencyPercent()
	{
		return frequencyPercent;
	}

	/**
	 * @param frequencyPercent ������������� ������� ���������
	 */
	public void setFrequencyPercent(String frequencyPercent)
	{
		this.frequencyPercent = frequencyPercent;
	}

	/**
	 * @return ������� ������ ���������
	 */
	public String getPercentOrder()
	{
		return percentOrder;
	}

	/**
	 * @param percentOrder ������� ������ ���������
	 */
	public void setPercentOrder(String percentOrder)
	{
		this.percentOrder = percentOrder;
	}

	/**
	 * @return ������� ���������� ������ ��� ��������� ������������� ������
	 */
	public String getIncomeOrder()
	{
		return incomeOrder;
	}

	/**
	 * @param incomeOrder ������� ���������� ������ ��� ��������� ������������� ������
	 */
	public void setIncomeOrder(String incomeOrder)
	{
		this.incomeOrder = incomeOrder;
	}

	/**
	 * @return ���������� ����������� �������� �� ����� ����
	 */
	public String getRenewals()
	{
		return renewals;
	}

	/**
	 * @param renewals ���������� ����������� �������� �� ����� ����
	 */
	public void setRenewals(String renewals)
	{
		this.renewals = renewals;
	}
}
