package com.rssl.phizic.business.ext.sbrf.deposits;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * �������� ��� ������� �� ������� v_alg ����������� ��� ���
 *
 * @author EgorovaA
 * @ created 25.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsVALG extends DictionaryRecordBase
{
	private static final String KEY_DELIMITER = "^";

	// id � ��
	private Long id;
	// ��� ������ (V_AVKL_QDTN1)
	private Long depositType;
	// ������ ������ (V_AVKL_QDTSUB)
	private Long depositSubType;
	// ��� �������� "�����-������" (V_AVKL_QVAL). 0 - �����, 1 - ������
	private Boolean foreignCurrency;
	// ��� ���� ���������� �������� (V_TYPE_OP)
	private Long operationCode;
	// ��� ���� ������, ���������������� �������� ��������� ������ �������� (V_CODE_VKL)
	private Long depositCode;

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
		((DepositsVALG) that).setId(getId());
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

	public Long getOperationCode()
	{
		return operationCode;
	}

	public void setOperationCode(Long operationCode)
	{
		this.operationCode = operationCode;
	}

	public Long getDepositCode()
	{
		return depositCode;
	}

	public void setDepositCode(Long depositCode)
	{
		this.depositCode = depositCode;
	}
}
