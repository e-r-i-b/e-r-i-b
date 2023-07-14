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
	//���� �� ��������� ������
	private boolean isBackError = false;

	/**
	 * ��������� ������ ���� �������
	 * @return ������ ����
	 */
	private List<CardLink> getCards()
	{
		try
		{
			return getPersonData().getCards();
		}
		catch (BusinessException e)
		{
			log.error("������ ��������� ������ ���� �� ��", e);
			return new ArrayList<CardLink>();
		}
		catch (BusinessLogicException e)
		{
			log.error("������ ��������� ������ ���� �� ��", e);
			return new ArrayList<CardLink>();
		}
	}

	/**
	 *  ��������� ��� ���� ��� ��������
	 * @param card - �����(�) ��� ������� ���������� �������� ��������������
	 * @return ������ ������ �� �������������� �����
	 */
	public List<CardLink> getAdditionalCards(List<CardLink> card)
	{
		BankrollServiceHelper bankrollHelper = new BankrollServiceHelper();
		List<CardLink> list = bankrollHelper.getAllowedAdditionalCards(card);
		isBackError = true;
		return list;
	}

	/**
	 * ��������� ������ ������ �� ����� ������� - ��� ����� ����� �� �� �������.
	 * @return ������ ������ �� ����� �������
	 */
	public List<CardLink> getPersonCardLinks()
	{
		return getPersonCardLinks(false, false);
	}

	/**
	 * ��������� ������ ������ �� ����� �������
	 * @param forMain - �������, ������������, ��� ������� �������� ������������ ������ ��� ���.
	 * @param activeOnly - �������, ������������, ����� �� �������� ������ �������� ����� ��� ���.
	 * @return ������ ����, ��������� �������.
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
				//��� ���� ����� �� ������ ������ ������, ������� �����������, � ����� �� ��� ����� � �������
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
	 * ��������� ������ ������ �� ������������ ����� �������
	 * @return ������ ������ �� ������������ ����� �������
	 */
	public List<CardLink> getPersonShowsCardLinks()
	{
		return getPersonCardLinks(true, false);
	}

	/**
	 * ���������� ������ �������� ���� �������
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
	 *  ���������� ������ ��� ���� �������
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
	 *  ���������� ������� ��� � �������� ���� �������
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
	 * ���� �� ������ ��������� � ���-������
	 * @return
	 */
	public boolean isBackError()
	{
		return isBackError;
	}

	/**
	 * ����� ��� ��������� ������ ����, ��������� � ���-������ (�� �������� � �� ���������������)
	 * @return ������ ����
	 */
	public List<CardLink> getErmbActiveCards() throws BusinessException, BusinessLogicException
	{
		List<CardLink> cardsAll = getPersonData().getCardsAll();
		List<CardLink> result = new ArrayList<CardLink>(cardsAll.size());
		CollectionUtils.select(cardsAll, new ErmbSmsChannelCardLinkFilter(), result);
		return result;
	}

	/**
	 * @return �����, ��������� ��� ������ � �������� ��������� ��� ������ ����
	 */
	public List<CardLink> getErmbAvailablePaymentCards() throws BusinessLogicException, BusinessException
	{
		List<CardLink> cardsAll = getPersonData().getCardsAll();
		return CardsUtil.filterPotentialErmbPaymentCards(cardsAll);
	}

	/**
	 * ������ ������ ��������� ���������� ��� ���� "��������"
	 * @param cardNumber - ����� �����
	 */
	public void setCardLinksFalseClosedState(String cardNumber)
	{
		try
		{
			resourceService.setCardLinksFalseClosedState(cardNumber);
		}
		catch (BusinessException e)
		{
			log.error("������ ��� ���������� ������� ������ ��������� � �������� ����", e);
		}
	}
}
