package com.rssl.phizic.business.dictionaries.finances;

import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * @author Erkin
 * @ created 25.07.2011
 * @ $Author$
 * @ $Revision$
 * MCC-код
 */
public class MerchantCategoryCode extends DictionaryRecordBase
{
	private Long code;
	private CardOperationCategory incomeOperationCategory; //доходная операция для MCCCODE
	private CardOperationCategory outcomeOperationCategory;//расходная операция для MCCCODE

	/**
	 * @return код
	 */
	public Long getCode()
	{
		return code;
	}

	/**
	 * @param code - код
	 */
	public void setCode(Long code)
	{
		this.code = code;
	}

	/**
	 * @return доходная операция
	 */
	public CardOperationCategory getIncomeOperationCategory()
	{
		return incomeOperationCategory;
	}

	/**
	 * @param incomeOperationCategory - доходная операция
	 */
	public void setIncomeOperationCategory(CardOperationCategory incomeOperationCategory)
	{
		this.incomeOperationCategory = incomeOperationCategory;
	}

	/**
	 * @return расходная операция
	 */
	public CardOperationCategory getOutcomeOperationCategory()
	{
		return outcomeOperationCategory;
	}

	/**
	 * @param outcomeOperationCategory - расходная операция
	 */
	public void setOutcomeOperationCategory(CardOperationCategory outcomeOperationCategory)
	{
		this.outcomeOperationCategory = outcomeOperationCategory;
	}

	/**
	 * Добавляет категорию для МСС-кода
	 * @param operationCategory - категория
	 */
	public void addOperationCategory(CardOperationCategory operationCategory)
	{
		if(operationCategory.isIncome())
			this.incomeOperationCategory = operationCategory;
		else
			this.outcomeOperationCategory = operationCategory;
	}

	/**
	 * Возвращает категорию для МСС-кода по виду доходности
	 * @param type - вид доходности
	 * @return категория
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
