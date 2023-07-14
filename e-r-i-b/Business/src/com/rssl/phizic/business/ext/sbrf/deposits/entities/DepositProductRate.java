package com.rssl.phizic.business.ext.sbrf.deposits.entities;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * ����������� ��� ���������� ������� ���� �� ������ BCH_BUX � DCF_TAR
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
	private Long clientCategory; // ��� ��������� ������� (KOD_VKL_CLNT)
	private Calendar dateBegin; // ���� ��������� ������ (DATE_BEG)
	private BigDecimal sumBegin; // ��������� ������� (SUM_BEG)
	private BigDecimal baseRate; // ������ ��� ���������� ������� (TAR_VKL)
	private String currencyCode; // �������� ISO ��� ������ (DCF_VAL)
	private Long segment; // ������� ������� (DCF_SEG)
	private Long periodBegin; // ������ ��������� ����� �������� ������ (BEG_SROK)
	private Long periodEnd; // ����� ��������� ����� �������� ������ (END_SROK)
	private String period; // ��������������� ������ (������������ ��� ��������� ��������� ��������)
	private String dictionaryPeriod; // ������ � ������� ����������� ��� ���

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
