package com.rssl.phizic.business.ermb.profile;

import com.rssl.phizic.business.ermb.profile.events.ErmbPersonEvent;
import com.rssl.phizic.business.BusinessException;

/**
 * @author EgorovaA
 * @ created 16.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Наблюдатель, отслеживающий изменения данных клиента
 */
public interface ErmbPersonListener
{
	void onPersonUpdate(ErmbPersonEvent event) throws BusinessException;
}
