package com.rssl.phizic.business.ext.sbrf.deposits;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * �������� ��� ������� �� ������� ContractDeposit2 ����������� ��� ���
 * (���� �������, ��������������� �������������� ������� ��� ������ "������ � ����� ���������� ���")
 * @author EgorovaA
 * @ created 25.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsContract extends DictionaryRecordBase
{
	private static final String KEY_DELIMITER = "^";

	// id � ��
	private Long id;
	// ��� ������ (QDTN1)
	private Long depositType;
	// ������ ������ (QDTSUB)
	private Long depositSubType;
	// ��� �������� "�����-������" (QVAL). 0 - �����, 1 - ������
	private Boolean foreignCurrency;
	// ������ �������� (CONTRACTTEMPLATE)
	private  Long contractTemplate;

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
		((DepositsContract) that).setId(getId());
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

	public Long getContractTemplate()
	{
		return contractTemplate;
	}

	public void setContractTemplate(Long contractTemplate)
	{
		this.contractTemplate = contractTemplate;
	}
}
