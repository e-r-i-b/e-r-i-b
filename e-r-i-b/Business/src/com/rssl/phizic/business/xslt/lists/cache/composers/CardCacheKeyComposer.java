package com.rssl.phizic.business.xslt.lists.cache.composers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.bankroll.AdditionalCardType;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.utils.SynchKeyUtils;

import java.util.ArrayList;
import java.util.List;

import static com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton.DELIMETER_KEY;

/**
 * @author gladishev
 * @ created 19.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class CardCacheKeyComposer implements SessionCacheKeyComposer
{
	public List<String> getSessionKeys(Object object) throws BusinessException
	{
		if (!PersonContext.isAvailable())
			return null;

		if (!(object instanceof Card))
			throw new BusinessException("Ошибка в CardCacheKeyComposer: Ожидался Card");

		Card card = (Card) object;

		StringBuilder key = new StringBuilder();
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();
		//если карточка основная или дополнительная с типом
		// CLIENTTOCLIENT то владелец у карты будет только один, берем его идентификатор из контекста
		if (card.isMain() || card.getAdditionalCardType() == AdditionalCardType.CLIENTTOCLIENT)
		{
			List<String> result = new ArrayList<String>();
			if (applicationConfig.getLoginContextApplication() == Application.PhizIA)
				key.append(Application.PhizIA.name()).append(DELIMETER_KEY);
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			key.append(XmlEntityListCacheSingleton.SESSION_LISTS_KEY).append(personData.createDocumentOwner().getSynchKey());
			result.add(key.toString());
			return result;
		}

		//прежние варианты не подошли, ищем владельцев карты в базе
		List<Long> loginIds = SessionComposerQueryHelper.getLoginIdsOfOwners(CardLink.class, card.getNumber());
		if (loginIds == null || loginIds.isEmpty())
			throw new BusinessException("Для карты с номером " + card.getNumber() + " не найдены владельцы");

		List<String> result = new ArrayList<String>();
		for (Long id : loginIds)
		{
			if (applicationConfig.getLoginContextApplication() == Application.PhizIA)
				key.append(Application.PhizIA.name()).append(DELIMETER_KEY);
			key.append(XmlEntityListCacheSingleton.SESSION_LISTS_KEY).append(SynchKeyUtils.makeClientSynchKey(id));
			result.add(key.toString());
		}
		
		return result;
	}

	public String getKey(Object object)
	{
		return null;
	}
}