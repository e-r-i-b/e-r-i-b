package com.rssl.phizic.operations.dictionaries.synchronization;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.SaveImageOperationBase;

/**
 * @author akrenev
 * @ created 20.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Базовая операция удаления записей справочников (с возможностью работать с картинками)
 */

public abstract class RemoveDictionaryEntityWithImageOperationBase<E, R extends Restriction> extends SaveImageOperationBase<R> implements RemoveEntityOperation<E, R>
{
	protected abstract void doRemove() throws BusinessException, BusinessLogicException;

	protected Class<?> getEntityClass()
	{
		return getEntity().getClass();
	}

	@Override
	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}

	public final void remove() throws BusinessException, BusinessLogicException
	{
		doRemove();
		MultiBlockModeDictionaryHelper.updateDictionary(getEntityClass());
	}
}
