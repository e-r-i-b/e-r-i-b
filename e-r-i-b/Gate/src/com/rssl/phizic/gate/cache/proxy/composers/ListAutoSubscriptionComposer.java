package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.gate.autopayments.AutoSubscriptionCardsComparator;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoPayStatusType;
import org.apache.commons.lang.ArrayUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Композер вычисленя ключа для метода AutoSubscriptionService#getAutoSubscriptions
 * @author niculichev
 * @ created 27.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListAutoSubscriptionComposer extends AbstractCacheKeyComposer
{
	private static final String DELEMITER = "|";

	public Serializable getKey(Object[] args, String params)
	{
		// сортируем карты по идентифкатору карт
		List<Card> cards = (List<Card>) args[0];
		Collections.sort(cards, new AutoSubscriptionCardsComparator());
		// сортируем типы статусов
		List<String> strTypes =  getNameAutoPayStatusTypes((AutoPayStatusType[]) args[1]);
		Collections.sort(strTypes);
		// формируем ключ
		StringBuilder builder = new StringBuilder();
		for(Card card : cards)
			builder.append(CardCacheKeyComposer.buildKey(card.getId())).append(DELEMITER);

		for(String type : strTypes)
			builder.append(type).append(DELEMITER);

		return builder.toString();
	}

	private List<String> getNameAutoPayStatusTypes(AutoPayStatusType[] types)
	{
		List<String> result = new ArrayList<String>();
		if(ArrayUtils.isEmpty(types))
			return result;

		for(AutoPayStatusType type : types)
			result.add(type.name());

		return result;
	}
}
