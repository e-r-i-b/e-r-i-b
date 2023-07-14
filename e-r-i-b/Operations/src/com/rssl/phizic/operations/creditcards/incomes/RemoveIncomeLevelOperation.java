package com.rssl.phizic.operations.creditcards.incomes;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.creditcards.incomes.IncomeLevel;
import com.rssl.phizic.business.creditcards.incomes.IncomeLevelService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * @author Dorzhinov
 * @ created 29.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class RemoveIncomeLevelOperation extends OperationBase implements RemoveEntityOperation
{
	private static final IncomeLevelService incomeLevelService = new IncomeLevelService();

	private IncomeLevel incomeLevel;

	/**
	 * Инициализация
	 * @param incomeLevelId ID
	 */
	public void initialize(Long incomeLevelId) throws BusinessException
	{
		incomeLevel = incomeLevelService.findById(incomeLevelId);

		if(incomeLevel == null)
			throw new BusinessException("Соответствие дохода клиента доступным кредитным лимитам с Id = " + incomeLevelId + " не найдено");
	}

	/**
	 * Удалить карточный кредитный продукт
	 */
	public void remove() throws BusinessException
	{
		incomeLevelService.remove(incomeLevel);
	}

	public Object getEntity()
	{
		return incomeLevel;
	}
}
