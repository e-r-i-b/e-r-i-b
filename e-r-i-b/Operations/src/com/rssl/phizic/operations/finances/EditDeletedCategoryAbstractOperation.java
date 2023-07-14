package com.rssl.phizic.operations.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.finances.CardOperation;
import com.rssl.phizic.business.finances.CardOperationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lepihina
 * @ created 21.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditDeletedCategoryAbstractOperation extends FinancesOperationBase
{
	private static final CardOperationService cardOperationService = new CardOperationService();

	private CardOperationCategory category;
	private List<CardOperationCategory> otherAvailableCategories;
	private Map<Long, Long> changedOperations = new HashMap<Long, Long>();

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

		otherAvailableCategories = cardOperationService.getCardOperationCategories(category.isIncome(), getLogin());
	}

	/**
	 * @return ���������, ��� ������� �������� �������
	 */
	public CardOperationCategory getCategory()
	{
		return category;
	}

	/**
	 * @return ������ ���������
	 */
	public List<CardOperationCategory> getOtherAvailableCategories()
	{
		return otherAvailableCategories;
	}

	/**
	 * �������� ����� �������� ��������� ��� �������� � ����
	 * @param operationId - id ��������
	 * @param categoryId - id ���������
	 */
	public void addChangedOperation(Long operationId, Long categoryId)
	{
		this.changedOperations.put(operationId, categoryId) ;
	}

	/**
	 * �������� ��������� ��������
	 * @param maxResults - ������������ ���-�� �����������
	 * @param firstResult - ������ ��������� (��� ���������)
	 * @return ������ ��������� ��������
	 * @throws BusinessException
	 */
	public List<CardOperation> getCardOperations(int maxResults, int firstResult) throws BusinessException
	{
		return cardOperationService.getCardOperations(getLogin(), category.getId(), maxResults, firstResult);
	}

	/**
	 * �������� ����������� ���������� �� ��������� ���������
	 * @param maxResults - ������������ ���-�� �����������
	 * @param firstResult - ������ ��������� (��� ���������)
	 * @return ������ ��������� ��������
	 * @throws BusinessException
	 */
	public List<CardOperationDescription> getCardOperationsDescription(int maxResults, int firstResult) throws BusinessException
	{
		CardOperationDescriber cardOperationDescriber = new CardOperationDescriber(getPersonCardLinks(), otherAvailableCategories);
		List<CardOperation> operations = getCardOperations(maxResults, firstResult);
		return cardOperationDescriber.transform(operations);
	}

	/**
	 * ������������� ���� �������� ��������� ��� ��������
	 * @param newCategoryId ����� �������� ���������
	 * @throws BusinessException
	 */
	public void setGeneralCategory(Long newCategoryId) throws BusinessException
	{
		cardOperationService.setGeneralCategory(getLogin().getId(), category.getId(), newCategoryId);
	}

	/**
	 * ���������� �������� � ����� ��������� ��������� (����� �������� � ����)
	 * @throws BusinessException
	 */
	@Transactional
	public void saveOperations() throws BusinessException
	{
		cardOperationService.updateOperationsList(changedOperations, getLogin().getId());
	}
}
