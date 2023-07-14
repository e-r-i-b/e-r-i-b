package com.rssl.phizic.business.ext.sbrf.deposits.entities;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Необходимые для построения вкладов поля из таблиц BCH_BUX и DCF_TAR
 *
 * @author EgorovaA
 * @ created 02.04.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositProductRate
{
	private Long id;
	private Long depositType;
	private Long depositSubType;
	private Long clientCategory; // Код категории клиента (KOD_VKL_CLNT)
	private Calendar dateBegin; // Дата установки ставки (DATE_BEG)
	private BigDecimal sumBegin; // Начальный остаток (SUM_BEG)
	private BigDecimal baseRate; // Ставка при выполнении условий (TAR_VKL)
	private String currencyCode; // Цифровой ISO код валюты (DCF_VAL)
	private Long segment; // Сегмент клиента (DCF_SEG)
	private Long periodBegin; // Начало диапазона срока хранения вклада (BEG_SROK)
	private Long periodEnd; // Конец диапазона срока хранения вклада (END_SROK)
	private String period; // Форматированный период (заполнияется при обработке вкладного продукта)
	private String dictionaryPeriod; // Период в формате справочника ЦАС НСИ

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

	public Long getClientCategory()
	{
		return clientCategory;
	}

	public void setClientCategory(Long clientCategory)
	{
		this.clientCategory = clientCategory;
	}

	public Calendar getDateBegin()
	{
		return dateBegin;
	}

	public void setDateBegin(Calendar dateBegin)
	{
		this.dateBegin = dateBegin;
	}

	public BigDecimal getSumBegin()
	{
		return sumBegin;
	}

	public void setSumBegin(BigDecimal sumBegin)
	{
		this.sumBegin = sumBegin;
	}

	public BigDecimal getBaseRate()
	{
		return baseRate;
	}

	public void setBaseRate(BigDecimal baseRate)
	{
		this.baseRate = baseRate;
	}

	public String getCurrencyCode()
	{
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode)
	{
		this.currencyCode = currencyCode;
	}

	public Long getSegment()
	{
		return segment;
	}

	public void setSegment(Long segment)
	{
		this.segment = segment;
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

	public String getPeriod()
	{
		return period;
	}

	public void setPeriod(String period)
	{
		this.period = period;
	}

	public String getDictionaryPeriod()
	{
		return dictionaryPeriod;
	}

	public void setDictionaryPeriod(String dictionaryPeriod)
	{
		this.dictionaryPeriod = dictionaryPeriod;
	}
}
