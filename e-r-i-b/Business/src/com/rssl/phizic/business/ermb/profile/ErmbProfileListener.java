package com.rssl.phizic.business.ermb.profile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.profile.events.ErmbProfileEvent;

/**
 * @author EgorovaA
 * @ created 10.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Наблюдатель, отслеживающий изменения ЕРМБ-профиля клиента
 */
public interface ErmbProfileListener
{
	void beforeProfileUpdate(ErmbProfileEvent event);

	void afterProfileUpdate(ErmbProfileEvent event) throws BusinessException;
}
