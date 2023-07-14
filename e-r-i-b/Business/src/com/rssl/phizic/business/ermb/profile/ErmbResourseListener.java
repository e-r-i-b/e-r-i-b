package com.rssl.phizic.business.ermb.profile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.profile.events.ErmbResourseParamsEvent;

/**
 * @author EgorovaA
 * @ created 10.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Наблюдатель, отслеживающий изменения ресурсов клиента, влияющие на изменение ЕРМБ-профиля
 */
public interface ErmbResourseListener
{
	void onResoursesUpdate(ErmbResourseParamsEvent event) throws BusinessException;
}
