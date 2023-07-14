package com.rssl.phizic.operations.dictionaries.pfp.riskProfile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.AgeCategory;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.AgeCategoryService;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;

/**
 * @author akrenev
 * @ created 02.04.2012
 * @ $Author$
 * @ $Revision$
 *
 * Операция редактирования возрастных категорий
 */

public class EditAgeCategoryOperation extends EditDictionaryEntityOperationBase
{
	private static final AgeCategoryService service = new AgeCategoryService();
	private AgeCategory category;

	/**
	 * инициализация операции
	 * @param id идентификатор категории
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		if (id == null)
			category = new AgeCategory();
		else
			category = service.getById(id, getInstanceName());

		if (category == null)
			throw new ResourceNotFoundBusinessException("В системе не найдено категории с id: " + id, AgeCategory.class); 
	}

	protected void doSave() throws BusinessException, BusinessLogicException
	{
		if (service.isExistCrossingAgeCategories(category.getId(), category.getMinAge(), category.getMaxAge(), getInstanceName()))
			throw new BusinessLogicException("Данная возрастная категория уже существует в системе. Пожалуйста, укажите другие возрастные ограничения.");
		service.addOrUpdate(category, getInstanceName());
	}

	public Object getEntity()
	{
		return category;
	}
}
