package com.rssl.phizic.operations.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.finances.CardOperationAbstract;
import com.rssl.phizic.business.finances.CardOperationService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.NotCreditCardFilter;

import java.util.List;

/**
 * @author lepihina
 * @ created 08.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class ShowFinanceOperationsOperation extends FinancesOperationBase
{
	private static CardOperationService cardOperationservice = new CardOperationService();

	private List<CardLink> debitCards;

	public void initialize() throws BusinessException, BusinessLogicException
	{
		super.initialize();
		debitCards = getFilterCardLinks(new NotCreditCardFilter(), getPersonCardLinks());
	}

	/**
	 * Метод для получения данных для построения графика "Поступления и списания"
	 * @param filterData
	 * @return список операций
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<CardOperationAbstract> getCategoriesGraphData(FinanceFilterData filterData) throws BusinessException
	{
		List<CardLink> cards = filterData.isCreditCards() ? getPersonCardLinks() : debitCards;
		if (filterData.isOnlyOwnCards())
			cards = getPersonOwnCards(cards);
		return cardOperationservice.getOperationsGraphData(filterData.getFromDate(), filterData.getToDate(), getCardNumbersList(cards), filterData.isCash(), getLogin());
	}
}
