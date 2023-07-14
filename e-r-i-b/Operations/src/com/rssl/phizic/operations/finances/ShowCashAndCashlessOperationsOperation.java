package com.rssl.phizic.operations.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.finances.CardOperationService;
import com.rssl.phizic.business.finances.CashAndCashlessOperationsGraphAbstract;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.NotCreditCardFilter;

import java.util.List;

/**
 * @author lepihina
 * @ created 10.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class ShowCashAndCashlessOperationsOperation extends FinancesOperationBase
{
	private static CardOperationService cardOperationservice = new CardOperationService();

	private List<CardLink> debitCards;

	public void initialize() throws BusinessException, BusinessLogicException
	{
		super.initialize();
		debitCards = getFilterCardLinks(new NotCreditCardFilter(), getPersonCardLinks());
	}

	/**
	 * Данные для постороения графика "Наличные и безналичные средства"
	 * @param filterData - данные фильтра
	 * @return List - данные для постоения графика "Наличные и безналичные средства"
	 */
	public List<CashAndCashlessOperationsGraphAbstract> getCashOperationsGraphData(FinanceFilterData filterData) throws BusinessException
	{
		//Карты, по которым получать данные
		List<CardLink> cards = filterData.isCreditCards() ? getPersonCardLinks() : debitCards;
		if (filterData.isOnlyOwnCards())
			cards = getPersonOwnCards(cards);

		return cardOperationservice.getCashOperationsGraphData(filterData.getFromDate(), filterData.getToDate(), getCardNumbersList(cards), filterData.isCash(), getLogin());
	}
}
