package com.rssl.phizic.business.ext.sbrf.deposits;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

import java.text.DecimalFormat;

/**
 * �������� ��� ������� �� ������� qvkl_val ����������� ��� ���  (�������� �����)
 *
 * @author EgorovaA
 * @ created 24.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsVAL extends DictionaryRecordBase
{
	private static final String KEY_DELIMITER = "^";

	// id � ��
	private Long id;
	// ��� ������ (QVKL_T_QDTN1)
	private Long depositType;
	// ������ ������ (QVKL_T_QDTSUB)
	private Long depositSubType;
	// ��� �������� "�����-������" (QVKL_T_QVAL). 0 - �����, 1 - ������
	private Boolean foreignCurrency;
	// �������� ISO ��� ������ (QVKL_V)
	private String currencyCode;

	public Comparable getSynchKey()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(depositType).append(KEY_DELIMITER).
				append(depositSubType).append(KEY_DELIMITER).
				append(foreignCurrency).append(KEY_DELIMITER).
				append(currencyCode);
		return sb.toString();
	}

	public void updateFrom(DictionaryRecord that)
	{
		((DepositsVAL) that).setId(getId());
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

	public String getCurrencyCode()
	{
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode)
	{
		this.currencyCode = currencyCode;
	}
}
