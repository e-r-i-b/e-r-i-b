package com.rssl.phizic.operations.card;

import com.rssl.phizic.business.BankrollServiceHelper;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.ermb.card.ErmbSmsChannelCardLinkFilter;
import com.rssl.phizic.business.operations.restrictions.CardRestriction;
import com.rssl.phizic.business.resources.external.ActiveCardFilter;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.StoredCard;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.link.GetExternalResourceLinkOperation;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.MockHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 17.10.2005 Time: 20:16:09 */
public class GetCardsOperation extends GetExternalResourceLinkOperation<CardRestriction>
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static ExternalResourceService resourceService = new ExternalResourceService();
	
	private List<CardLink> mainCardsLinks = new ArrayList<CardLink>();
	private List<Card> mainCards = new ArrayList<Card>();
	private List<CardLink> addCards = new ArrayList<CardLink>();
	private boolean isSorted = false;
	//была ли заглушена ошибка
	private boolean isBackError = false;

	/**
	 * ѕолучение списка карт клиента
	 * @return список карт
	 */
	private List<CardLink> getCards()
	{
		try
		{
			return getPersonData().getCards();
		}
		catch (BusinessException e)
		{
			log.error("ќшибка получени€ списка карт из Ѕƒ", e);
			return new ArrayList<CardLink>();
		}
		catch (BusinessLogicException e)
		{
			log.error("ќшибка получени€ списка карт из Ѕƒ", e);
			return new ArrayList<CardLink>();
		}
	}

	/**
	 *  получение доп карт дл€ основной
	 * @param card - карта(ы) дл€ которой необходимо получить дополнительные
	 * @return —писок ссылок на дополнительные карты
	 */
	public List<CardLink> getAdditionalCards(List<CardLink> card)
	{
		BankrollServiceHelper bankrollHelper = new BankrollServiceHelper();
		List<CardLink> list = bankrollHelper.getAllowedAdditionalCards(card);
		isBackError = true;
		return list;
	}

	/**
	 * получение списка ссылок на карты клиента - без учета нужно ли на главной.
	 * @return список ссылок на карты клиента
	 */
	public List<CardLink> getPersonCardLinks()
	{
		return getPersonCardLinks(false, false);
	}

	/**
	 * получение списка ссылок на карты клиента
	 * @param forMain - признак, определ€ющий, дл€ главной страницы составл€етс€ список или нет.
	 * @param activeOnly - признак, определ€ющий, нужно ли получать только активные карты или все.
	 * @return список карт, доступных системе.
	 */
	public List<CardLink> getPersonCardLinks(boolean forMain, boolean activeOnly)
	{
		List<CardLink> cardLinks = new ArrayList<CardLink>();
		boolean needRequest = false;
		for (CardLink link : getCards())
		{
			if (ApplicationUtil.isMobileApi())
				needRequest = link.getShowInMobile();
			else if (ApplicationUtil.isATMApi())
				needRequest = link.getShowInATM();
			else
				needRequest = link.getShowInSystem();

			if (needRequest)
			{
				if (forMain && (!link.getShowInMain()))
					continue;
				//дл€ того чтобы не делать лишний запрос, сначала опередел€ем, а нужна ли эта карта в системе
				Card card = link.getCard();

				if (activeOnly && !((new ActiveCardFilter()).accept(card)))
					continue;

				if (!MockHelper.isMockObject(card))
					cardLinks.add(link);
				else
					isBackError = true;

				if (!isUseStoredResource())
				{
					setUseStoredResource(card instanceof StoredCard);
				}
			}
		}
		return cardLinks;
	}

	/**
	 * получение списка ссылок на отображаемые карты клиента
	 * @return список ссылок на отображаемые карты клиента
	 */
	public List<CardLink> getPersonShowsCardLinks()
	{
		return getPersonCardLinks(true, false);
	}

	/**
	 * возвращает список основных карт клиента
	 * @param cardLinks
	 * @return
	 * @throws BusinessException
	 */
	public List<CardLink> getPersonMainCardLinks(List<CardLink> cardLinks)
	{
		if (!isSorted)
			sort(cardLinks);
		return mainCardsLinks;
	}

	/**
	 *  возвращает список доп карт клиента
	 * @param cardLinks
	 * @return
	 * @throws BusinessException
	 */
	public List<CardLink> getPersonAdditionalCards(List<CardLink> cardLinks)
	{
		if (!isSorted)
			sort(cardLinks);
		return addCards;
	}

	/**
	 *  заполнение списков доп и основных карт клиента
	 * @param cardLinks
	 * @return
	 * @throws BusinessException
	 */
	public List<CardLink> sort(List<CardLink> cardLinks)
	{
		CardRestriction restriction = getRestriction();
		List<CardLink> result = new ArrayList<CardLink>();
		for (CardLink cardLink : cardLinks)
		{
			Card card = cardLink.getValue();
			if (MockHelper.isMockObject(card))
			{
				isBackError = true;
				continue;
			}
			if (restriction.accept(card))
			{
				if (!card.isMain())
					addCards.add(cardLink);
				else
				{
					mainCardsLinks.add(cardLink);
					mainCards.add(card);
				}
			}
		}
		isSorted = true;
		return result;
	}

	/**
	 * была ли ошибка св€занна€ с бэк-офисом
	 * @return
	 */
	public boolean isBackError()
	{
		return isBackError;
	}

	/**
	 * ћетод дл€ получени€ линков карт, доступных в смс-канале (не закрытых и не заблокированных)
	 * @return список карт
	 */
	public List<CardLink> getErmbActiveCards() throws BusinessException, BusinessLogicException
	{
		List<CardLink> cardsAll = getPersonData().getCardsAll();
		List<CardLink> result = new ArrayList<CardLink>(cardsAll.size());
		CollectionUtils.select(cardsAll, new ErmbSmsChannelCardLinkFilter(), result);
		return result;
	}

	/**
	 * @return карты, доступные дл€ выбора в качестве платежной дл€ услуги ≈–ћЅ
	 */
	public List<CardLink> getErmbAvailablePaymentCards() throws BusinessLogicException, BusinessException
	{
		List<CardLink> cardsAll = getPersonData().getCardsAll();
		return CardsUtil.filterPotentialErmbPaymentCards(cardsAll);
	}

	/**
	 * «адать статус сообщени€ осзакрытии дл€ карт "показано"
	 * @param cardNumber - номер карты
	 */
	public void setCardLinksFalseClosedState(String cardNumber)
	{
		try
		{
			resourceService.setCardLinksFalseClosedState(cardNumber);
		}
		catch (BusinessException e)
		{
			log.error("ќшибка при обновлении статуса показа сообщени€ о закрытии карт", e);
		}
	}
}
