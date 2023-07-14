package com.rssl.phizic.business.ermb.profile.events;

import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.BusinessException;

/**
 * @author EgorovaA
 * @ created 12.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Сущность для передачи ЕРМБ-профиля клиента наблюдателю за изменениями его ресурсов
 */
public class ErmbResourseEvent
{
	private ErmbProfileImpl profile;
	private final static ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();

	public ErmbResourseEvent(Long personId) throws BusinessException
	{
		this.profile = profileService.findByPersonId(personId);
	}

	public ErmbResourseEvent(ErmbProfileImpl profile)
	{
		this.profile = profile;
	}

	public ErmbProfileImpl getProfile()
	{
		return profile;
	}
}
