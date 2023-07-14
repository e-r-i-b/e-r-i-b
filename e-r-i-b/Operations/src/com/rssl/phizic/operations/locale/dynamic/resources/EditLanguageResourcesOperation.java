package com.rssl.phizic.operations.locale.dynamic.resources;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.locale.dynamic.resources.LanguageResources;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.operations.EditEntityOperation;

/**
 * @author koptyaev
 * @ created 01.10.2014
 * @ $Author$
 * @ $Revision$
 */
public interface EditLanguageResourcesOperation<T extends LanguageResources, I> extends EditEntityOperation
{
	/**
	 * Инициализировать операцию данными основной сущности и локали
	 * @param id идентификатор базовой сущности
	 * @param localeId локаль
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(I id, String localeId) throws BusinessException, BusinessLogicException;

	/**
	 * Получить локаль
	 * @return локаль
	 */
	public ERIBLocale getLocale();

	/**
	 * Получить локаль
	 * @return локаль
	 */
	public T getEntity();

}
