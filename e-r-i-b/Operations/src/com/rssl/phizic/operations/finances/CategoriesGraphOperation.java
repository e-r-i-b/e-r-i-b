package com.rssl.phizic.operations.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategoryGraphAbstract;
import com.rssl.phizic.business.dictionaries.finances.DiagramAbstract;
import com.rssl.phizic.business.finances.Budget;
import com.rssl.phizic.business.finances.BudgetService;
import com.rssl.phizic.business.finances.CardOperation;
import com.rssl.phizic.business.finances.CardOperationService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.NotCreditCardFilter;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.userSettings.UserPropertiesConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.util.*;

import static com.rssl.phizic.operations.finances.FinanceHelper.CASH_PAYMENTS_ID;

/**
 * @author rydvanskiy
 * @ created 02.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class CategoriesGraphOperation extends FilterCardFinanceOperation
{
	private static final CardOperationService cardOperationService = new CardOperationService();
	private static final BudgetService budgetService = new BudgetService();
	private static final double COEF = 100.0/6.0;

	private List<CardLink> debitCards;

	public void initialize() throws BusinessException, BusinessLogicException
	{
		initialize(null);
	}

	public void initialize(String selectedId) throws BusinessException, BusinessLogicException
	{
		super.initialize(selectedId);
		debitCards = getFilterCardLinks(new NotCreditCardFilter(), getPersonCardLinks());
	}

	/**
	 * Метод для получения пары сумма операций и категория
	 * @param filterData данные фильтрации
	 * @return выписка по категории для графика "Структура финансов по категориям"
	 * @throws BusinessException
	 */
	public List<CardOperationCategoryGraphAbstract> getCategoriesGraphData (FinanceFilterData filterData) throws BusinessException
	{
		List<CardLink> cards = filterData.isCreditCards() ? getPersonCardLinks() : debitCards;
		if (filterData.isOnlyOwnCards())
			cards = getPersonOwnCards(cards);

		return cardOperationService.getCategoriesGraphData (filterData.getFromDate(), filterData.getToDate(), getCardNumbersList(cards), filterData.isCash(),
															filterData.isIncome(), filterData.isTransfer(), getLogin(), null, true, filterData.getOperationCountry());
	}

	/**
	 * Метод для получения пары сумма операций и категория
	 * @param filterData данные фильтрации
	 * @param cardIds идентификаторы CardLink'ов
	 * @return выписка по категории для графика "Структура финансов по категориям"
	 * @throws BusinessException
	 */
	public List<CardOperationCategoryGraphAbstract> getCategoriesGraphData(FinanceFilterData filterData, List<Long> cardIds)  throws BusinessException
	{
		return getCategoriesGraphData(filterData, cardIds, null);
	}

	/**
	 *
	 * Список расходов\доходов за каждый месяц периода
	 *
	 * @param  filterData
	 * @return List&lt;DiagramGraphAbstract&gt;
	 * @throws BusinessException
	 */
	public List<DiagramAbstract> getDiagramAbstracts(final FinanceFilterData filterData, final boolean isEmptyFormSelectedId) throws BusinessException
	{
		List<Long> selectedIds = getRoot().getSelectedIds();
		List<CardLink>      cards  = getSelectedCards(selectedIds);
		List<CardOperation> source = cardOperationService.getClientLoadedOperations(getLogin().getId(),
														                            filterData.getFromDate(),
														                            filterData.getToDate());

		// Фильтрация по типу учитываемых при суммировании операций с наличными
		CollectionUtils.filter(source, new Predicate()
		{
			public boolean evaluate(Object object)
			{
				CardOperationCategory category = ((CardOperation) object).getCategory();
				if (category == null)
				{
					return false;
				}

				boolean isInternal = category.isForInternalOperations();
				boolean isTransfer = category.getIsTransfer();

				if (filterData.isNoTransfer())
				{
					return !(isInternal || isTransfer);
				}

				if (filterData.isTransfer())
				{
					return isInternal || isTransfer;
				}

				if (filterData.isInternalTransfer())
				{
					return isInternal;
				}

				return true;
			}
		});

		List<DiagramAbstract>   income   = new ArrayList<DiagramAbstract>();
		Iterator<CardOperation> iterator = source.listIterator();

		while (iterator.hasNext())
		{
			CardOperation operation = iterator.next();

			// Фильтрация по номерам карт
			if (CollectionUtils.isNotEmpty(cards) && !isEmptyFormSelectedId)
			{
				boolean contains = false;

				for (CardLink card : cards)
				{
					if (card.getNumber().equals(operation.getCardNumber()))
					{
						contains = true;
						break;
					}
				}

				if (!contains)
				{
					iterator.remove();
					continue;
				}
			}

			// Операции несвязанные с операциями пришедшими из ИПС учитывать не нужно
			if (operation.getLoadDate() == null)
			{
				iterator.remove();
				continue;
			}

			// Учет при суммировании операций с наличными
			if (filterData.isOnlyCash() && !operation.isCash())
			{
				iterator.remove();
				continue;
			}

			// Фильтрация по типу учитываемых при суммировании операций с наличными
			Double   operationAmount = operation.getNationalAmount().doubleValue();
			Calendar operationDate   = DateHelper.clearTime(operation.getDate());

			if (operation.getCategory().isIncome())
			{
				divideByMonth(income, new DiagramAbstract(true, operationAmount, operationDate), true);
			}
			else
			{
				divideByMonth(income, new DiagramAbstract(false, operationAmount, operationDate), false);
			}

			iterator.remove();
		}

		return income;
	}

	private void divideByMonth(List<DiagramAbstract> source, DiagramAbstract newDiagramAbstract, boolean income)
	{
		if (source.isEmpty())
		{
			source.add(newDiagramAbstract);
		}
		else
		{
			DiagramAbstract found = null;

			for (DiagramAbstract diagramAbstract : source)
			{
				if (diagramAbstract.hasSameYearAndMonth(newDiagramAbstract))
				{
					found = diagramAbstract;
					break;
				}
			}

			if (found == null)
			{
				source.add(newDiagramAbstract);
			}
			else
			{
				if (income)
				{
					found.setIncomeAmount(found.getIncomeAmount() + newDiagramAbstract.getIncomeAmount());
				}
				else
				{
					found.setOutcomeAmount(found.getOutcomeAmount() + newDiagramAbstract.getOutcomeAmount());
				}
			}
		}
	}

	/**
	 * Метод для получения пары сумма операций и категория
	 * @param filterData данные фильтрации
	 * @param cardIds идентификаторы CardLink'ов
	 * @param excludeCategories исключаемые категории операций
	 * @return выписка по категории для графика "Структура финансов по категориям"
	 * @throws BusinessException
	 */
	public List<CardOperationCategoryGraphAbstract> getCategoriesGraphData(FinanceFilterData filterData, List<Long> cardIds, String[] excludeCategories)  throws BusinessException
	{
		List<CardLink> selectedCards = getSelectedCards(cardIds);
		boolean showCashPayments = filterData.getShowCashPayments() != null ? filterData.getShowCashPayments() : cardIds.contains(CASH_PAYMENTS_ID);
		List<CardOperationCategoryGraphAbstract> categoriesGraphData = cardOperationService.getCategoriesGraphData (filterData.getFromDate(), filterData.getToDate(),
				getCardNumbersList(selectedCards), filterData.isCash(), filterData.isIncome(), filterData.isTransfer(), getLogin(), excludeCategories, showCashPayments, filterData.getOperationCountry());

		if(CollectionUtils.isEmpty(categoriesGraphData))
			return categoriesGraphData;

		List<Budget> budgets = budgetService.findBudgetByLogin(getLogin());
		if(CollectionUtils.isNotEmpty(budgets))
		{
			for(CardOperationCategoryGraphAbstract category: categoriesGraphData)
			{
				for(Budget budget : budgets)
				{
					if(category.getId().equals(budget.getCategoryId()))
					{
						category.setBudgetSum(budget.getBudget());

					}
				}
			}			
			return categoriesGraphData;
		}

		if(isUsedALFBudgets())
			return categoriesGraphData;

		Calendar toDate = (Calendar) filterData.getToDate().clone();
		toDate.add(Calendar.MONTH, -6);
		List<CardOperationCategoryGraphAbstract> avgCategoriesGraphData = cardOperationService.getCategoriesGraphData(toDate, filterData.getToDate(), getCardNumbersList(selectedCards),
				filterData.isCash(), filterData.isIncome(), filterData.isTransfer(), getLogin(), excludeCategories, cardIds.contains(CASH_PAYMENTS_ID),filterData.getOperationCountry());

		if(CollectionUtils.isEmpty(avgCategoriesGraphData))
			return categoriesGraphData;

		for(CardOperationCategoryGraphAbstract category: categoriesGraphData)
		{
			int i = avgCategoriesGraphData.indexOf(category);
			if(i != -1)
				category.setBudgetSum(Math.abs(Math.round(avgCategoriesGraphData.get(i).getCategorySum()*COEF)/100.0));
			category.setAvgBudget(true);
		}
		return categoriesGraphData;
	}

	/**
	 * Получение категорий операций банка и клиента
	 * @param filterData - параметры фильтрации
	 * @return список категорий операций
	 * @throws BusinessException
	 */
	public List<CardOperationCategory> getCategories(FinanceFilterData filterData) throws BusinessException
	{
		return cardOperationService.getCardOperationCategories(filterData.isIncome(), filterData.isTransfer(), getLogin());
	}

	/**
	 * @return true - клиент настраивал бюджеты для категорий
	 * @throws BusinessException
	 */
	public boolean isUsedALFBudgets() throws BusinessException
	{
		return ConfigFactory.getConfig(UserPropertiesConfig.class).isUsedALFBudgets();
	}
}
