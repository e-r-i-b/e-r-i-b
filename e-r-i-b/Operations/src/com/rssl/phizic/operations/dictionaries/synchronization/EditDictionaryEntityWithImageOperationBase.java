package com.rssl.phizic.operations.dictionaries.synchronization;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.SaveImageOperationBase;

/**
 * @author akrenev
 * @ created 20.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Базовая операция редактирования записей справочников (с возможностью работать с картинками)
 */

public abstract class EditDictionaryEntityWithImageOperationBase <E, R extends Restriction> extends SaveImageOperationBase<R> implements EditEntityOperation<E, R>
{
	protected abstract void doSave() throws BusinessException, BusinessLogicException;

	protected Class<?> getEntityClass() throws BusinessLogicException, BusinessException
	{
		return getEntity().getClass();
	}

	@Override
	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}

	public final void save() throws BusinessException, BusinessLogicException
	{
		doSave();
		MultiBlockModeDictionaryHelper.updateDictionary(getEntityClass());
	}
}
