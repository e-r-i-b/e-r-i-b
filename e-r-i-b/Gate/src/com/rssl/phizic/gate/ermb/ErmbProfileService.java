package com.rssl.phizic.gate.ermb;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.StringCacheKeyComposer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.person.ErmbProfile;

import java.util.List;

/**
 @author: EgorovaA
 @ created: 14.11.2012
 @ $Author$
 @ $Revision$
 */
public interface ErmbProfileService extends Service
{
	/**
	 * Получение профилей ЕРМБ по номеру карты
	 * @param cardNumber номер карты
	 * @return список ЕРМБ-профилей
	 */
	@Cachable(keyResolver= StringCacheKeyComposer.class, name = "ErmbProfile.ermbProfiles", maxElementsInMemory = 0)
	List<ErmbProfile> getErmbProfiles(String cardNumber) throws GateException;
}
