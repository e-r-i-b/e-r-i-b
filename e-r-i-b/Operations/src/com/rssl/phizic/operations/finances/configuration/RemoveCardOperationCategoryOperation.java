package com.rssl.phizic.operations.finances.configuration;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategoryService;
import com.rssl.phizic.business.dictionaries.finances.MerchantCategoryCodeService;
import com.rssl.phizic.business.dictionaries.finances.locale.CardOperationCategoryResources;
import com.rssl.phizic.business.finances.recategorization.ALFRecategorizationRuleService;
import com.rssl.phizic.business.locale.dynamic.resources.LanguageResourceService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author Koptyaev
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 */
public class RemoveCardOperationCategoryOperation extends OperationBase implements RemoveEntityOperation
{
	private static final CardOperationCategoryService cardOperationCategoryService = new CardOperationCategoryService();
	private static final MerchantCategoryCodeService merchantCategoryCodeService = new MerchantCategoryCodeService();
	private static final ALFRecategorizationRuleService recategorizationRuleService = new ALFRecategorizationRuleService();
	private static final LanguageResourceService<CardOperationCategoryResources> LANGUAGE_RESOURCE_SERVICE = new LanguageResourceService<CardOperationCategoryResources>(CardOperationCategoryResources.class);
	private CardOperationCategory category;

	/**
	 * Инициализация операции
	 * @param id идентификатор сущности для удаления.
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		if (id == null)
		{
			throw new BusinessLogicException("id категории не задан.");
		}
		else
		{
			category = cardOperationCategoryService.findById(id);
			if (category == null)
				throw new ResourceNotFoundBusinessException("Категория с id " + id + " не найдена.", CardOperationCategory.class);
			if (category.getIsDefault() || category.isForInternalOperations() || (category.getOwnerId() != null))
				throw new BusinessLogicException("Вы не можете удалить категорию \"" + category.getName() + "\".");
		}
	}

	/**
	 *  Удалить сущность.
	 */
	public void remove() throws BusinessException, ConstraintViolationException
	{
		try
	    {
	        HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
	        {
	            public Void run(Session session) throws BusinessException
	            {
		            merchantCategoryCodeService.updateOrDeleteMCC(category.getExternalId(), category.isIncome());
		            recategorizationRuleService.removeRecategorizationRuleByCategory(category.getId());
		            LANGUAGE_RESOURCE_SERVICE.removeRes(category.getId());
		            cardOperationCategoryService.delete(category);
		            return null;
	            }
	        });
	    }
		catch (ConstraintViolationException e)
		{
			throw e;
		}
	    catch (Exception e)
	    {
	        throw new BusinessException(e);
	    }
	}

	/**
	 * @return удаляемую/удаленную сущность.
	 */
	public Object getEntity()
	{
		return category;
	}
}
