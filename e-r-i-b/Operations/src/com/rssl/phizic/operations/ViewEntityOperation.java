package com.rssl.phizic.operations;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author hudyakov
 * @ created 27.04.2009
 * @ $Author$
 * @ $Revision$
 */

public interface ViewEntityOperation<E>
{
	/**
	 * Получить просматриваемую/редактируемую сущность
	 * @return просматриваемая/редактируемая сущность.
	 */
	public E getEntity() throws BusinessException, BusinessLogicException;
}
