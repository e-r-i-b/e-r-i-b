package com.rssl.phizic.operations.finances;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategoryService;
import com.rssl.phizic.business.finances.CardOperation;
import com.rssl.phizic.business.finances.CardOperationService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.util.ApplicationUtil;

/**
 * @author lepihina
 * @ created 15.01.14
 * @ $Author$
 * @ $Revision$
 */
public class AddCardOperationOperation extends OperationBase implements EditEntityOperation
{
	private static final CardOperationCategoryService cardOperationCategoryService = new CardOperationCategoryService();
	protected static final CardOperationService cardOperationService = new CardOperationService();

	protected CardOperation operation;
	protected Login login;

	/**
	 * Инициализация операции
	 * @throws BusinessException
	 */
	public void initialize() throws BusinessException
	{
		operation = new CardOperation();
		login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		operation.setOwnerId(login.getId());
		cardOperationService.addOrUpdate(operation);
	}

	/**
	 * Устанавливает категорию для операции
	 * @param categoryId - идентификатор категории операции
	 * @throws BusinessException
	 */
	public void setCategory(Long categoryId) throws BusinessException
	{
		CardOperationCategory category = cardOperationCategoryService.findById(categoryId);
		if (category == null)
			throw new BusinessException("Не найдена категория с id=" + categoryId);
		if (!ApplicationUtil.isMobileApi() && category.isIncome())
			throw new BusinessException("Категория с id=" + categoryId + " не является расходной.");
		if (PersonContext.isAvailable())
		{
			Long id = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin().getId();
			if (category.getOwnerId() != null && !category.getOwnerId().equals(id))
				throw new BusinessException("Категория ID=" + categoryId + " не доступна пользователю LOGIN_ID=" + id);
		}
		if (!category.isIncome())
			operation.setNationalAmount(operation.getNationalAmount().abs().negate());
		else
			operation.setNationalAmount(operation.getNationalAmount().abs());
		operation.setCategory(category);
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return operation;
	}
}
