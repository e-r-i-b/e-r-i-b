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
	 * ������������� ��������
	 * @param categoryId - ID ���������
	 */
	public void initialize(Long categoryId) throws BusinessException, BusinessLogicException
	{
		super.initialize();
		category = cardOperationCategoryService.findById(categoryId);
		if (category == null)
			throw new BusinessException("�� ������� ��������� ��������� �������� ID=" + categoryId);

		if (category.getOwnerId() != null && !category.getOwnerId().equals(getLogin().getId()))
			throw new BusinessException("��������� ID=" + categoryId + " �� �������� ������� LOGIN_ID=" + getLogin().getId());
		
		personAvailableCategories = cardOperationCategoryService.getPersonAvailableCategories(getLogin().getId());
	}

	/**
	 * @return ���������, ��� ������� �������� �������
	 */
	public CardOperationCategory getCategory()
	{
		return category;
	}

	/**
	 * �������� ��������� ��������, ��������������� �������
	 * @param filter - ������ ��� ���������� ����������
	 * @return ������ ��������� ��������
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
			// cash �����, �.�. ��� ������� ��������,
			// � income �� �����, �.�. ��� ������� ���������, � ��������� �������
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
