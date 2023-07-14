package com.rssl.phizic.business.ermb.card;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardLevel;
import com.rssl.phizic.gate.bankroll.CardState;
import com.rssl.phizic.gate.bankroll.CardType;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.*;

/**
 * @author Gulov
 * @ created 16.10.13
 * @ $Author$
 * @ $Revision$
 */
/**
 * ќпределитель приоритетной карты
 * ѕриоритет 1: основна€ лична€ дебетова€ незаблокированна€ карта в рубл€х (кроме социальной)
 * ѕриоритет 2: основна€ лична€ дебетова€ социальна€ незаблокированна€ карта в рубл€х (при наличии у клиента такой карты)
 * ѕриоритет 3: основна€ лична€ дебетова€ незаблокированна€ карта в валюте (евро, доллары —Ўј)
 * ѕриоритет 4: перва€ попавша€с€ карта
 */
public class PrimaryCardResolver
{
	private static final ErmbConfig config = ConfigFactory.getConfig(ErmbConfig.class);
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private interface Filter
	{
		boolean accept(Card card) throws BusinessException;
	}

	private static class CardFilter implements Filter
	{
		public boolean accept(Card card) throws BusinessException
		{
			CardType cardType = card.getCardType();
			Calendar expireDate = card.getExpireDate();
			return card.isMain()
					&& (cardType == CardType.debit
					|| cardType == CardType.overdraft)
					&& card.getCardState() == CardState.active
					&& !card.isVirtual()
					&& (expireDate == null || expireDate.after(Calendar.getInstance()));
		}
	}

	private static class CardFilterWithAmountCheck extends CardFilter
	{
		private final Money amount;

		private CardFilterWithAmountCheck(Money amount)
		{
			this.amount = amount;
		}

		public boolean accept(Card card) throws BusinessException
		{
			boolean result = super.accept(card);
			if (!result)
				return result;
			return CardsUtil.hasAvailableLimit(card, amount);
		}
	}

	/**
	 * ¬ыбрать приоритетный линк карты дл€ клиента с профилем profile
	 * @param profile ермб профиль клиента
	 * @return линк карты
	 */
	public static CardLink getPrimaryLinkForAbonentPay(ErmbProfileImpl profile) throws BusinessException
	{
		CardLink result = ErmbHelper.getForeginProduct(profile);
		if (result != null)
			return result;
		return getPrimaryLink(profile.getCardLinks());
	}

	/**
	 * ¬ыбрать приоритетный линк карты по списку линков
	 * @param links список линков карт
	 * @return линк карты
	 */
	public static CardLink getPrimaryLink(Collection<CardLink> links) throws BusinessException
	{
		return getPrimaryLink(links, new CardFilter());
	}

	/**
	 * ¬ыбрать приоритетный линк карты по списку линков, чтобы баланс карты был больше или равен суммы amount
	 * @param links список линков карт
	 * @param amount сумма списани€
	 * @return линк карты
	 */
	public static CardLink getPrimaryLink(Collection<CardLink> links, Money amount) throws BusinessException
	{
		return getPrimaryLink(links, new CardFilterWithAmountCheck(amount));
	}

	private static CardLink getPrimaryLink(Collection<CardLink> links, Filter filter) throws BusinessException
	{
		if (CollectionUtils.isEmpty(links))
			throw new BusinessException("—писок линков карт пустой");
		Map<Card, CardLink> cards = getCards(links);
		if (MapUtils.isEmpty(cards))
			throw new BusinessException("—писок карт пустой");
		Card priorityCard = resolve(cards.keySet(), filter);
		checkCard(priorityCard, cards.keySet());
		CardLink priorityCardLink = cards.get(priorityCard);
		checkObject(priorityCardLink);
		return priorityCardLink;
	}

	/**
	 * ќпределить приоритетную карту
	 * @param cards список карт
	 * @return приоритетна€ карта
	 */
	public static Card getPrimaryCard(Collection<Card> cards) throws BusinessException
	{
		if (CollectionUtils.isEmpty(cards))
			throw new BusinessException("—писок карт пустой");
		if (cards.size() == 1)
			return cards.iterator().next();
		Card priorityCard = resolve(cards, new CardFilter());
		checkCard(priorityCard, cards);
		return priorityCard;
	}

	/**
	 * ќпределить приоритетную карту
	 * @return карта
	 */
	private static Card resolve(Collection<Card> cards, Filter filter) throws BusinessException
	{
		Card defaultCard = null;
		Card potentialDefaultCard = null;
		List<String> social = config.getSocial();
		List<Card> sortedCards = sort(cards);
		for (Card card : sortedCards)
		{
			if (filter.accept(card))
			{
				String currencyCode = card.getCurrency().getCode();
				if (currencyCode.equals("RUB") || currencyCode.equals("RUR"))
				{
					CardLevel cardKind = card.getCardLevel();
					if (cardKind == null || !social.contains(cardKind.name()))
						return card;
					else
						defaultCard = card;
				}
				else if (currencyCode.equals("EUR") || currencyCode.equals("USD"))
					potentialDefaultCard = card;
			}
		}
		if (defaultCard != null)
			return defaultCard;
		if (potentialDefaultCard != null)
			return potentialDefaultCard;
		return null;
	}

	//”бираем хаотичность с выбора карт (при каждом вызове дл€ одного набора должна выбиратьс€ одна и та же)
	private static List<Card> sort(Collection<Card> cards)
	{
		ArrayList<Card> list = new ArrayList<Card>(cards);
		Collections.sort(list, new Comparator<Card>()
		{
			public int compare(Card o1, Card o2)
			{
				return o1.getId().compareTo(o2.getId());
			}
		});
		return list;
	}

	private static Map<Card, CardLink> getCards(Collection<CardLink> links)
	{
		Map<Card, CardLink> cards = new HashMap<Card, CardLink>(links.size());
		for (CardLink link : links)
		{
			if (link.getShowInSms())
				cards.put(link.getCard(), link);
		}
		return cards;
	}

	private static void checkCard(Card priorityCard, Collection<Card> cards) throws BusinessException
	{
		if (priorityCard == null)
		{
			StringBuilder sb = new StringBuilder("ѕриоритетна€ карта не определена.");
			for (Card card : cards)
				sb.append(card);
			log.error(sb);
		}
		checkObject(priorityCard);
	}

	private static void checkObject(Object object) throws BusinessException
	{
		if (object == null)
			throw new BusinessException("Ќевозможно определить приоритетную карту");
	}
}
