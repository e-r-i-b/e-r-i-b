package com.rssl.phizic.business.ext.sbrf.deposits.locale;

import com.rssl.phizic.locale.dynamic.resources.LanguageResource;

/**
 * Локализованные ресурсы для записи из таблицы FIELD_TDOG справочника ЦАС НСИ (параметры для договоров условий по вкладам)
 * @author lepihina
 * @ created 09.06.15
 * $Author$
 * $Revision$
 */
public class DepositsTDOGResources extends LanguageResource
{
	private Long id;
	// Сумма вклада (SUM_VKL)
	private String sum;
	// Валюта вклада (CURR_VKL)
	private String currency;
	// Срок хранения вклада (QSROK)
	private String period;
	// Дата окончания срока хранения вклада (DATA_END)
	private String dateEnd;
	// Процентная ставка по вкладу (PERCENT)
	private String rate;
	// Приходные операции по вкладу (PRIXOD)
	private String incomingTransactions;
	// Минимальный размер дополнительного взноса (MIN_ADD)
	private String minAdditionalFee;
	// Периодичность внесения дополнительных взносов (PER_ADD)
	private String additionalFeePeriod;
	// Расходные операции по вкладу (RASXOD)
	private String debitTransactions;
	// Сумма неснижаемого остатка (SUM_NOST)
	private String sumMinBalance;
	// Периодичность выплаты процентов (PER_PERCENT)
	private String frequencyPercent;
	// Порядок уплаты процентов (ORD_PERCENT)
	private String percentOrder;
	// Порядок начисления дохода при досрочном востребовании вклада (ORD_DOXOD)
	private String incomeOrder;
	// Количество пролонгаций Договора на новый срок (QPROL)
	private String renewals;

	/**
	 * @return сумма вклада
	 */
	public String getSum()
	{
		return sum;
	}

	/**
	 * @param sum сумма вклада
	 */
	public void setSum(String sum)
	{
		this.sum = sum;
	}

	/**
	 * @return валюта вклада
	 */
	public String getCurrency()
	{
		return currency;
	}

	/**
	 * @param currency валюта вклада
	 */
	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	/**
	 * @return срок хранения вклада
	 */
	public String getPeriod()
	{
		return period;
	}

	/**
	 * @param period срок хранения вклада
	 */
	public void setPeriod(String period)
	{
		this.period = period;
	}

	/**
	 * @return дата окончания срока хранения вклада
	 */
	public String getDateEnd()
	{
		return dateEnd;
	}

	/**
	 * @param dateEnd дата окончания срока хранения вклада
	 */
	public void setDateEnd(String dateEnd)
	{
		this.dateEnd = dateEnd;
	}

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return процентная ставка по вкладу
	 */
	public String getRate()
	{
		return rate;
	}

	/**
	 * @param rate процентная ставка по вкладу
	 */
	public void setRate(String rate)
	{
		this.rate = rate;
	}

	/**
	 * @return приходные операции по вкладу
	 */
	public String getIncomingTransactions()
	{
		return incomingTransactions;
	}

	/**
	 * @param incomingTransactions приходные операции по вкладу
	 */
	public void setIncomingTransactions(String incomingTransactions)
	{
		this.incomingTransactions = incomingTransactions;
	}

	/**
	 * @return минимальный размер дополнительного взноса
	 */
	public String getMinAdditionalFee()
	{
		return minAdditionalFee;
	}

	/**
	 * @param minAdditionalFee минимальный размер дополнительного взноса
	 */
	public void setMinAdditionalFee(String minAdditionalFee)
	{
		this.minAdditionalFee = minAdditionalFee;
	}

	/**
	 * @return периодичность внесения дополнительных взносов
	 */
	public String getAdditionalFeePeriod()
	{
		return additionalFeePeriod;
	}

	/**
	 * @param additionalFeePeriod периодичность внесения дополнительных взносов
	 */
	public void setAdditionalFeePeriod(String additionalFeePeriod)
	{
		this.additionalFeePeriod = additionalFeePeriod;
	}

	/**
	 * @return расходные операции по вкладу
	 */
	public String getDebitTransactions()
	{
		return debitTransactions;
	}

	/**
	 * @param debitTransactions расходные операции по вкладу
	 */
	public void setDebitTransactions(String debitTransactions)
	{
		this.debitTransactions = debitTransactions;
	}

	/**
	 * @return сумма неснижаемого остатка
	 */
	public String getSumMinBalance()
	{
		return sumMinBalance;
	}

	/**
	 * @param sumMinBalance сумма неснижаемого остатка
	 */
	public void setSumMinBalance(String sumMinBalance)
	{
		this.sumMinBalance = sumMinBalance;
	}

	/**
	 * @return периодичность выплаты процентов
	 */
	public String getFrequencyPercent()
	{
		return frequencyPercent;
	}

	/**
	 * @param frequencyPercent периодичность выплаты процентов
	 */
	public void setFrequencyPercent(String frequencyPercent)
	{
		this.frequencyPercent = frequencyPercent;
	}

	/**
	 * @return порядок уплаты процентов
	 */
	public String getPercentOrder()
	{
		return percentOrder;
	}

	/**
	 * @param percentOrder порядок уплаты процентов
	 */
	public void setPercentOrder(String percentOrder)
	{
		this.percentOrder = percentOrder;
	}

	/**
	 * @return порядок начисления дохода при досрочном востребовании вклада
	 */
	public String getIncomeOrder()
	{
		return incomeOrder;
	}

	/**
	 * @param incomeOrder порядок начисления дохода при досрочном востребовании вклада
	 */
	public void setIncomeOrder(String incomeOrder)
	{
		this.incomeOrder = incomeOrder;
	}

	/**
	 * @return количество пролонгаций договора на новый срок
	 */
	public String getRenewals()
	{
		return renewals;
	}

	/**
	 * @param renewals количество пролонгаций договора на новый срок
	 */
	public void setRenewals(String renewals)
	{
		this.renewals = renewals;
	}
}
