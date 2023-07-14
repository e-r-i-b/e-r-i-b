package com.rssl.phizic.business.ext.sbrf.deposits;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * Сущность для записей из таблицы FIELD_TDOG справочника ЦАС НСИ  (параметры для договоров условий по вкладам)
 *
 * @author EgorovaA
 * @ created 25.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsTDOG extends DictionaryRecordBase
{
	private static final String KEY_DELIMITER = "^";

	// id в БД
	private Long id;
	// вид вклада (VID)
	private Long depositType;
	// Подвид вклада (PVID)
	private Long depositSubType;
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

	public Comparable getSynchKey()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(depositType).append(KEY_DELIMITER).
				append(depositSubType);
		return sb.toString();
	}

	public void updateFrom(DictionaryRecord that)
	{
		((DepositsTDOG) that).setId(getId());
		super.updateFrom(that);
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getDepositType()
	{
		return depositType;
	}

	public void setDepositType(Long depositType)
	{
		this.depositType = depositType;
	}

	public Long getDepositSubType()
	{
		return depositSubType;
	}

	public void setDepositSubType(Long depositSubType)
	{
		this.depositSubType = depositSubType;
	}

	public String getSum()
	{
		return sum;
	}

	public void setSum(String sum)
	{
		this.sum = sum;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	public String getPeriod()
	{
		return period;
	}

	public void setPeriod(String period)
	{
		this.period = period;
	}

	public String getDateEnd()
	{
		return dateEnd;
	}

	public void setDateEnd(String dateEnd)
	{
		this.dateEnd = dateEnd;
	}

	public String getRate()
	{
		return rate;
	}

	public void setRate(String rate)
	{
		this.rate = rate;
	}

	public String getIncomingTransactions()
	{
		return incomingTransactions;
	}

	public void setIncomingTransactions(String incomingTransactions)
	{
		this.incomingTransactions = incomingTransactions;
	}

	public String getMinAdditionalFee()
	{
		return minAdditionalFee;
	}

	public void setMinAdditionalFee(String minAdditionalFee)
	{
		this.minAdditionalFee = minAdditionalFee;
	}

	public String getAdditionalFeePeriod()
	{
		return additionalFeePeriod;
	}

	public void setAdditionalFeePeriod(String additionalFeePeriod)
	{
		this.additionalFeePeriod = additionalFeePeriod;
	}

	public String getDebitTransactions()
	{
		return debitTransactions;
	}

	public void setDebitTransactions(String debitTransactions)
	{
		this.debitTransactions = debitTransactions;
	}

	public String getSumMinBalance()
	{
		return sumMinBalance;
	}

	public void setSumMinBalance(String sumMinBalance)
	{
		this.sumMinBalance = sumMinBalance;
	}

	public String getFrequencyPercent()
	{
		return frequencyPercent;
	}

	public void setFrequencyPercent(String frequencyPercent)
	{
		this.frequencyPercent = frequencyPercent;
	}

	public String getPercentOrder()
	{
		return percentOrder;
	}

	public void setPercentOrder(String percentOrder)
	{
		this.percentOrder = percentOrder;
	}

	public String getIncomeOrder()
	{
		return incomeOrder;
	}

	public void setIncomeOrder(String incomeOrder)
	{
		this.incomeOrder = incomeOrder;
	}

	public String getRenewals()
	{
		return renewals;
	}

	public void setRenewals(String renewals)
	{
		this.renewals = renewals;
	}
}
