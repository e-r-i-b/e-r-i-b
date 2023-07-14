package com.rssl.phizic.business.ermb.profile;

import com.rssl.phizic.business.ermb.profile.events.ErmbResourseSetEvent;
import com.rssl.phizic.business.BusinessException;

/**
 * @author EgorovaA
 * @ created 12.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Наблюдатель, отслеживающий изменения в составе линков продуктов клиента
 */
public interface ErmbResourseSetListener
{
	void onResoursesReload(ErmbResourseSetEvent event) throws BusinessException;
}
