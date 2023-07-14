package com.rssl.phizic.business.ext.sbrf.deposits;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * Сущность для записей из таблицы BCH_BUX справочника ЦАС НСИ
 * @author EgorovaA
 * @ created 25.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsBCHBUX extends DictionaryRecordBase
{
	private static final String KEY_DELIMITER = "^";

	// id в БД
	private Long id;
	// Код вида вклада (BCH_VKL)
	private Long depositType;
	// Код подвида вклада (BCH_PVVKL)
	private Long depositSubType;
	// Код признака "рубли-валюта" (BCH_VAL). 0 - рубли; 1 - иностранная валюта
	private Boolean foreignCurrency;
	// Признак  резидента (FL_REZ). 0- резидент; 1 - нерезидент
	private Boolean residentDeposit;
	// Балансовый счет первого или второго порядка (BSSCH)
	private String balanceOrder;
	// Начало диапазона срока хранения вклада (BEG_SROK)
	private Long periodBegin;
	// Конец диапазона срока хранения вклада (END_SROK)
	private Long periodEnd;

	public Comparable getSynchKey()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(depositType).append(KEY_DELIMITER).
				append(depositSubType).append(KEY_DELIMITER).
				append(foreignCurrency);
		return sb.toString();
	}

	public void updateFrom(DictionaryRecord that)
	{
		((DepositsBCHBUX) that).setId(getId());
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

	public Boolean getForeignCurrency()
	{
		return foreignCurrency;
	}

	public void setForeignCurrency(Boolean foreignCurrency)
	{
		this.foreignCurrency = foreignCurrency;
	}

	public Boolean getResidentDeposit()
	{
		return residentDeposit;
	}

	public void setResidentDeposit(Boolean residentDeposit)
	{
		this.residentDeposit = residentDeposit;
	}

	public String getBalanceOrder()
	{
		return balanceOrder;
	}

	public void setBalanceOrder(String balanceOrder)
	{
		this.balanceOrder = balanceOrder;
	}

	public Long getPeriodBegin()
	{
		return periodBegin;
	}

	public void setPeriodBegin(Long periodBegin)
	{
		this.periodBegin = periodBegin;
	}

	public Long getPeriodEnd()
	{
		return periodEnd;
	}

	public void setPeriodEnd(Long periodEnd)
	{
		this.periodEnd = periodEnd;
	}
}
