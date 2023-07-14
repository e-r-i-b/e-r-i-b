package com.rssl.phizic.operations.ext.sbrf.dictionaries.loanclaim.creditProduct.condition;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanclaim.LoanClaimHelper;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductConditionService;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Moshenko
 * @ created 24.01.2014
 * @ $Author$
 * @ $Revision$
 * ќпераци€ с публикаци€ми кредитных условий.
 */
public class PublishingCreditProductConditionOperation extends OperationBase
{
	private static final CreditProductConditionService conditionService = new CreditProductConditionService();
	/**
	 * ќпубликовать условие.
	 * @param id услови€.
	 */
	public void publish(Long id) throws BusinessException, BusinessLogicException
	{
		CreditProductCondition condition = getCondition(id);
		if (condition.isPublished())
			throw new BusinessLogicException("ƒанный кредитный продукт уже опубликован");
		if (!LoanClaimHelper.hasActiveCurrencyCondition(condition))
			throw new BusinessLogicException("ƒл€ опубликованного продукта должно быть задано хот€ бы одно " +
					"условие в разрезе валют");

		condition.setPublished(true);
		conditionService.addOrUpdate(condition);

	}
	/**
	 * —н€ть с публиковать условие.
	 * @param id услови€.
	 */
	public void unpublish(Long id) throws BusinessLogicException, BusinessException
	{
		CreditProductCondition condition = getCondition(id);
		if (!condition.isPublished())
			throw new BusinessLogicException("ƒанный кредитный продукт уже сн€т с публикации");

		condition.setPublished(false);
		conditionService.addOrUpdate(condition);
	}

	private  CreditProductCondition getCondition(Long id) throws BusinessException, BusinessLogicException
	{
		CreditProductCondition condition = conditionService.findeById(id);
		if (condition == null)
			throw new BusinessLogicException("”словие с Id: " + id + " не найдено в системе.");
		return condition;
	}
}
