package com.rssl.phizic.operations.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.finances.CardOperation;
import com.rssl.phizic.business.finances.CardOperationService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.NotCreditCardFilter;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.BooleanHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.rssl.phizic.operations.finances.FinanceHelper.CARD_IDS_SEPARATOR;
import static com.rssl.phizic.operations.finances.FinanceHelper.CASH_PAYMENTS_ID;

/**
 * @author lepihina
 * @ created 12.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class GetListOfOperationsOperation extends FinancesOperationBase implements ListEntitiesOperation
{
	private static final CardOperationService cardOperationService = new CardOperationService();

	private List<CardLink> debitCards;
	private List<CardOperationCategory> personAvailableCategories;

	public void initialize() throws BusinessException, BusinessLogicException
	{
		super.initialize();
		debitCards = getFilterCardLinks(new NotCreditCardFilter(), getPersonCardLinks());
		personAvailableCategories = cardOperationCategoryService.getPersonAvailableCategories(getLogin().getId());
	}

	/**
	 * Получить карточные операции, удовлетворяющие фильтру (для страницы "Операции")
	 * @param filterParams - параметры фильтра
	 * @param maxResults - количество операций
	 * @param firstResult - номер 1-й операции в списке
	 * @return список карточных операций
	 * @throws BusinessException
	 */
	public List<CardOperationDescription> getCardOperations(Map<String, Object> filterParams, int maxResults, int firstResult) throws BusinessException
	{
		Boolean showCreditCards = BooleanHelper.asBoolean(filterParams.get("showCreditCards"));
		Boolean showOtherAccounts = BooleanHelper.asBoolean(filterParams.get("showOtherAccounts"));

		List<CardLink> cards = showCreditCards ? getPersonCardLinks() : debitCards;
		if (!showOtherAccounts)
			cards = getPersonOwnCards(cards);

		List<String> cardsNumbers = getCardNumbersList(cards);
		CardOperationDescriber cardOperationDescriber = new CardOperationDescriber(getPersonCardLinks(), personAvailableCategories);
		try
		{
			Query query = createQuery("list");
			query.setParameterList("cardNumbers", cardsNumbers);
			query.setParameter("loginId", getLogin().getId());
			query.setParameter("fromDate", filterParams.get("fromDate"));
			query.setParameter("toDate", filterParams.get("toDate"));
			query.setParameter("maxLoadDate", filterParams.get("openPageDate"));
			query.setParameter("income", filterParams.get("income"));
			query.setParameter("cash", filterParams.get("cash"));
			query.setParameter("showCash", BooleanHelper.asBoolean(filterParams.get("showCash")));
			query.setMaxResults(maxResults);
			query.setFirstResult(firstResult);
			query.setListTransformer(cardOperationDescriber);
			return query.executeList();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получить карточные операции, удовлетворяющие фильтру
	 * @param filterParams - параметры фильтра
	 * @param maxResults - количество операций
	 * @param firstResult - номер 1-й операции в списке
	 * @return список карточных операций
	 * @throws BusinessException
	 */
	public List<CardOperationDescription> getCardOperationsToCategories(Map<String, Object> filterParams, int maxResults, int firstResult, boolean orderByDesc) throws BusinessException
	{
		String selectedCardIds = (String)filterParams.get("selectedCardIds");
		boolean showCashPayments = false;
		if (filterParams.get("showCashPayments") != null)
		{
			// в mAPI значение параметра "отображать траты наличными" приходит в showCashPayments
			showCashPayments = BooleanHelper.asBoolean(filterParams.get("showCashPayments"));
		}
		else
		{
			// в основном приложении значение параметра "отображать траты наличными" определяется наличием значение CASH_PAYMENTS_ID в строке selectedCardIds (строка всегда не пустая)
			showCashPayments = Arrays.asList(selectedCardIds.split(CARD_IDS_SEPARATOR)).contains(CASH_PAYMENTS_ID.toString());
		}
		Boolean showCreditCards = (filterParams.get("showCreditCards") != null) ? BooleanHelper.asBoolean(filterParams.get("showCreditCards")) : true;
		List<CardLink> cards = showCreditCards ? getPersonCardLinks() : debitCards;
		List<String> cardsNumbers = (selectedCardIds == null && (MobileApiUtil.isMobileApiGE(MobileAPIVersions.V5_00) || ApplicationUtil.isWebAPI())) ? getCardNumbersList(cards) : FinanceHelper.getCardNumbersList(cards, selectedCardIds);

		CardOperationDescriber cardOperationDescriber = new CardOperationDescriber(getPersonCardLinks(), personAvailableCategories);
		List<Long> categoryIds = new ArrayList<Long>();
		if (Boolean.valueOf((String)filterParams.get("onlyAvailableCategories")))
		{
			categoryIds = getBudgetCategories(Boolean.valueOf((String)filterParams.get("showTransfers")));
		}
		else
		{
			Long categoryId = (Long) filterParams.get("categoryId");
			if (categoryId != null)
				categoryIds.add(categoryId);
		}

		List<CardOperation> operations = cardOperationService.getCardOperations(filterParams, maxResults, firstResult, cardsNumbers, categoryIds, getLogin().getId(), showCashPayments, orderByDesc);
		return cardOperationDescriber.transform(operations);
	}

	private List<Long> getBudgetCategories(boolean showTransfers) throws BusinessException
	{
		try
		{
			Query listBudgetsQuery = createQuery("getClientsBudgetList");
			listBudgetsQuery.setParameter("loginId", getLogin().getId());
			listBudgetsQuery.setParameter("showTransfer", showTransfers);
			return listBudgetsQuery.executeList();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}
}
