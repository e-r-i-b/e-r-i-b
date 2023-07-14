package com.rssl.phizic.business.ext.sbrf.deposits;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * �������� ��� ������� �� ������� BCH_BUX ����������� ��� ���
 * @author EgorovaA
 * @ created 25.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsBCHBUX extends DictionaryRecordBase
{
	private static final String KEY_DELIMITER = "^";

	// id � ��
	private Long id;
	// ��� ���� ������ (BCH_VKL)
	private Long depositType;
	// ��� ������� ������ (BCH_PVVKL)
	private Long depositSubType;
	// ��� �������� "�����-������" (BCH_VAL). 0 - �����; 1 - ����������� ������
	private Boolean foreignCurrency;
	// �������  ��������� (FL_REZ). 0- ��������; 1 - ����������
	private Boolean residentDeposit;
	// ���������� ���� ������� ��� ������� ������� (BSSCH)
	private String balanceOrder;
	// ������ ��������� ����� �������� ������ (BEG_SROK)
	private Long periodBegin;
	// ����� ��������� ����� �������� ������ (END_SROK)
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
