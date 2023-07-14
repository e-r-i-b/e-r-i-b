package com.rssl.phizic.operations;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.operations.Operation;

/**
 * @author Krenev
 * @ created 31.12.2008
 * @ $Author$
 * @ $Revision$
 */
public interface EditEntityOperation<E, R extends Restriction> extends Operation<R>, ViewEntityOperation<E>
{
	/**
	 * Cохранить сущность.
	 * @throws BusinessLogicException логическая ошибка
	 * @throws BusinessException
	 */
	public void save() throws BusinessException, BusinessLogicException;
}
