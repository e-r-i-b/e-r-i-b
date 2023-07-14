package com.rssl.phizic.operations.creditcards.incomes;

import com.rssl.ikfl.crediting.CreditingConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.creditcards.incomes.IncomeLevelService;
import com.rssl.phizic.business.loanCardClaim.LoanCardClaimHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Dorzhinov
 * @ created 04.07.2011
 * @ $Author$
 * @ $Revision$
 */
public class GetIncomeLevelListOperation extends OperationBase implements ListEntitiesOperation
{
	private static final IncomeLevelService incomeLevelService = new IncomeLevelService();

	/**
	 * Определить есть ли записи в таблице соответствия дохода клиента и доступных кредитных лимитов (кредитных продуктов)
	 * @return true/false
	 */
	public Boolean isIncomeLevelsExists() throws BusinessException
	{
		return incomeLevelService.isIncomeLevelsExists();
	}

	/** проверяет наличие предодобренных кредитных карт для клиента
	 * @return если предодобренных карт  нет, то null,
	 *         если есть одна и типа "Предложение на увеличение кредитного лимита", то id предложения
	 *         иначе если предложение на изменение типа, то -2
	 *         иначе -1
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public Long checkLoanCardOfferClient() throws BusinessException
	{
		return LoanCardClaimHelper.checkLoanCardOfferClient();
	}

	/**
	 * Проверяет наличие кредитных карт для клиента по коду дохода
	 * @param incomeId - id дохода
	 * @return - true - есть карты, false - нет
	 * @throws BusinessException
	 */
	public boolean productByIncomeExists(final Long incomeId) throws BusinessException
	{
		return incomeLevelService.productByIncomeExists(incomeId);
	}

	public int getTimeToRefresh() throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if (personData.isOfferReceivingInProgress())
			return ConfigFactory.getConfig(CreditingConfig.class).getWaitingTime();
		else return 0;
	}
}
