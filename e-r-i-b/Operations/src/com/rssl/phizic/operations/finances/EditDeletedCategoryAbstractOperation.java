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
	 * »нициализаци€ операции
	 * @param categoryId - ID категории
	 */
	public void initialize(Long categoryId) throws BusinessException, BusinessLogicException
	{
		super.initialize();
		category = cardOperationCategoryService.findById(categoryId);
		if (category == null)
			throw new BusinessException("Ќе найдена категори€ карточных операций ID=" + categoryId);

		if (category.getOwnerId() != null && !category.getOwnerId().equals(getLogin().getId()))
			throw new BusinessException(" атегори€ ID=" + categoryId + " не доступна клиенту LOGIN_ID=" + getLogin().getId());

		otherAvailableCategories = cardOperationService.getCardOperationCategories(category.isIncome(), getLogin());
	}

	/**
	 * @return категори€, дл€ которой строитс€ выписка
	 */
	public CardOperationCategory getCategory()
	{
		return category;
	}

	/**
	 * @return список категорий
	 */
	public List<CardOperationCategory> getOtherAvailableCategories()
	{
		return otherAvailableCategories;
	}

	/**
	 * ƒобавить новое значение категории дл€ операции в мапу
	 * @param operationId - id операции
	 * @param categoryId - id категории
	 */
	public void addChangedOperation(Long operationId, Long categoryId)
	{
		this.changedOperations.put(operationId, categoryId) ;
	}

	/**
	 * ѕолучить карточные операции
	 * @param maxResults - максимальное кол-во результатов
	 * @param firstResult - первый результат (дл€ пагинации)
	 * @return список карточных операций
	 * @throws BusinessException
	 */
	public List<CardOperation> getCardOperations(int maxResults, int firstResult) throws BusinessException
	{
		return cardOperationService.getCardOperations(getLogin(), category.getId(), maxResults, firstResult);
	}

	/**
	 * ѕолучить расширенную информацию по карточным операци€м
	 * @param maxResults - максимальное кол-во результатов
	 * @param firstResult - первый результат (дл€ пагинации)
	 * @return список карточных операций
	 * @throws BusinessException
	 */
	public List<CardOperationDescription> getCardOperationsDescription(int maxResults, int firstResult) throws BusinessException
	{
		CardOperationDescriber cardOperationDescriber = new CardOperationDescriber(getPersonCardLinks(), otherAvailableCategories);
		List<CardOperation> operations = getCardOperations(maxResults, firstResult);
		return cardOperationDescriber.transform(operations);
	}

	/**
	 * ”станавливает нове значение категории дл€ операций
	 * @param newCategoryId новое значение категории
	 * @throws BusinessException
	 */
	public void setGeneralCategory(Long newCategoryId) throws BusinessException
	{
		cardOperationService.setGeneralCategory(getLogin().getId(), category.getId(), newCategoryId);
	}

	/**
	 * —охранение операций с новым значением категории (новые значени€ в мапе)
	 * @throws BusinessException
	 */
	@Transactional
	public void saveOperations() throws BusinessException
	{
		cardOperationService.updateOperationsList(changedOperations, getLogin().getId());
	}
}
