package com.rssl.phizic.business.dictionaries.finances;

import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * @author Erkin
 * @ created 25.07.2011
 * @ $Author$
 * @ $Revision$
 * MCC-���
 */
public class MerchantCategoryCode extends DictionaryRecordBase
{
	private Long code;
	private CardOperationCategory incomeOperationCategory; //�������� �������� ��� MCCCODE
	private CardOperationCategory outcomeOperationCategory;//��������� �������� ��� MCCCODE

	/**
	 * @return ���
	 */
	public Long getCode()
	{
		return code;
	}

	/**
	 * @param code - ���
	 */
	public void setCode(Long code)
	{
		this.code = code;
	}

	/**
	 * @return �������� ��������
	 */
	public CardOperationCategory getIncomeOperationCategory()
	{
		return incomeOperationCategory;
	}

	/**
	 * @param incomeOperationCategory - �������� ��������
	 */
	public void setIncomeOperationCategory(CardOperationCategory incomeOperationCategory)
	{
		this.incomeOperationCategory = incomeOperationCategory;
	}

	/**
	 * @return ��������� ��������
	 */
	public CardOperationCategory getOutcomeOperationCategory()
	{
		return outcomeOperationCategory;
	}

	/**
	 * @param outcomeOperationCategory - ��������� ��������
	 */
	public void setOutcomeOperationCategory(CardOperationCategory outcomeOperationCategory)
	{
		this.outcomeOperationCategory = outcomeOperationCategory;
	}

	/**
	 * ��������� ��������� ��� ���-����
	 * @param operationCategory - ���������
	 */
	public void addOperationCategory(CardOperationCategory operationCategory)
	{
		if(operationCategory.isIncome())
			this.incomeOperationCategory = operationCategory;
		else
			this.outcomeOperationCategory = operationCategory;
	}

	/**
	 * ���������� ��������� ��� ���-���� �� ���� ����������
	 * @param type - ��� ����������
	 * @return ���������
	 */
	public CardOperationCategory getOperationCategory(boolean type)
	{
		if (type)
			return this.incomeOperationCategory;
		else
			return this.outcomeOperationCategory;
	}

	public Comparable getSynchKey()
	{
		return code;
	}
}
