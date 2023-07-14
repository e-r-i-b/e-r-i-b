package com.rssl.phizic.operations.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;

import java.util.List;

/**
 * @author Erkin
 * @ created 13.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class ShowCategoryAbstractOperation extends FinancesOperationBase
{
	private CardOperationCategory category;

	private List<CardOperationCategory> personAvailableCategories;

	/**
	 * Инициализация операции
	 * @param categoryId - ID категории
	 */
	public void initialize(Long categoryId) throws BusinessException, BusinessLogicException
	{
		super.initialize();
		category = cardOperationCategoryService.findById(categoryId);
		if (category == null)
			throw new BusinessException("Не найдена категория карточных операций ID=" + categoryId);

		if (category.getOwnerId() != null && !category.getOwnerId().equals(getLogin().getId()))
			throw new BusinessException("Категория ID=" + categoryId + " не доступна клиенту LOGIN_ID=" + getLogin().getId());
		
		personAvailableCategories = cardOperationCategoryService.getPersonAvailableCategories(getLogin().getId());
	}

	/**
	 * @return категория, для которой строится выписка
	 */
	public CardOperationCategory getCategory()
	{
		return category;
	}

	/**
	 * Получить карточные операции, удовлетворяющие фильтру
	 * @param filter - фильтр над карточными операциями
	 * @return список карточных операций
	 */
	public List<CardOperationDescription> getCardOperations(final FinanceFilterData filter) throws BusinessException
	{
		List<String> cards = filter.isOnlyOwnCards() ? getPersonOwnCards() : getPersonCards();
		CardOperationDescriber cardOperationDescriber = new CardOperationDescriber(getPersonCardLinks(), personAvailableCategories);
		try
		{
			Query query = createQuery("getCardOperations");
			query.setParameter("loginId", getLogin().getId());
			query.setParameter("categoryId", category.getId());
			query.setParameter("start", filter.getFromDate());
			query.setParameter("until", filter.getToDate());
			// cash нужен, т.к. это атрибут операции,
			// а income не нужен, т.к. это атрибут категории, а категория указана
			query.setParameter("cash", filter.isCash());
			// query.setParameter("income", filter.isIncome());
			query.setParameterList("cards", cards);
			query.setListTransformer(cardOperationDescriber);
			return query.executeList();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}
}
