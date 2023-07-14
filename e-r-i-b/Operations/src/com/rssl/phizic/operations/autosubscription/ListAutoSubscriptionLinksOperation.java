package com.rssl.phizic.operations.autosubscription;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOtherBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.ExternalCardsTransferToOurBankLongOffer;
import com.rssl.phizic.gate.payments.autosubscriptions.InternalCardsTransferLongOffer;
import com.rssl.phizic.gate.payments.longoffer.CardToAccountLongOffer;
import com.rssl.phizic.operations.ListEntitiesOperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Операция получения списка подписок на автоплатежи.
 *
 * @author bogdanov
 * @ created 23.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListAutoSubscriptionLinksOperation extends AutoSubscriptionOperationBase implements ListEntitiesOperation
{
	private List<AutoSubscriptionLink> subscriptionsLinks  = new ArrayList<AutoSubscriptionLink>();
	private List<AutoSubscriptionLink> p2pSubscriptionsLinks = new ArrayList<AutoSubscriptionLink>(); //автоплатежи карта-карта

	/**
	 * инициализация операции.
	 *
	 * @param cardId идентификатор карты. если не указан, то по всем картам.
	 */
	public void initialize(Long cardId) throws BusinessException, BusinessLogicException
	{
		super.initialize();
		PersonData personData = getPersonData();

		CardLink cardLink = cardId != null ? personData.getCard(cardId) : null;
		if (cardLink == null)
		{
			List<AutoSubscriptionLink> links1 = personData.getAutoSubscriptionLinks();
			for (AutoSubscriptionLink link : links1)
			{
				//в списках не отображаем копилки, они отображаются на формах детальной информации.
				if(isMoneyBoxSubscription(link)){}
				else if (isP2PAutoSubscription(link))
					p2pSubscriptionsLinks.add(link);
				else
					subscriptionsLinks.add(link);
			}
			return;
		}

		subscriptionsLinks = new ArrayList<AutoSubscriptionLink>();
		List<AutoSubscriptionLink> links = personData.getAutoSubscriptionLinks();
		for (AutoSubscriptionLink link : links)
		{
			if (link.getCardLink().getNumber().equals(cardLink.getNumber()))
			{
				subscriptionsLinks.add(link);
			}
		}
	}

	private boolean isP2PAutoSubscription(AutoSubscriptionLink link)
	{
		Class<? extends GateDocument> type = link.getValue().getType();
		return type != null && (type.equals(InternalCardsTransferLongOffer.class) || type.equals(ExternalCardsTransferToOurBankLongOffer.class) ||
				type.equals(ExternalCardsTransferToOtherBankLongOffer.class));
	}

	private boolean isMoneyBoxSubscription(AutoSubscriptionLink link)
	{
		Class<? extends GateDocument> type = link.getValue().getType();
		return type != null && (type.equals(CardToAccountLongOffer.class));
	}

	/**
	 * @return список простых подписок на автоплатежи.
	 */
	public List<AutoSubscriptionLink> getEntity()
	{
		return Collections.unmodifiableList(subscriptionsLinks);
	}

	/**
	 * @return список подписок карта-карта
	 */
	public List<AutoSubscriptionLink> getP2pSubscriptionsLinks()
	{
		return Collections.unmodifiableList(p2pSubscriptionsLinks);
	}

	/**
	 * @return список подписок карта-карта
	 */
	public List<AutoSubscriptionLink> getP2pSubscriptionsLinks(CardLink cardLink) throws BusinessLogicException, BusinessException
    {
        PersonData personData = getPersonData();

        p2pSubscriptionsLinks = new ArrayList<AutoSubscriptionLink>();
        List<AutoSubscriptionLink> links = personData.getAutoSubscriptionLinks();
        for (AutoSubscriptionLink link : links)
        {
            if (link.getCardLink().getNumber().equals(cardLink.getNumber()) && isP2PAutoSubscription(link))
            {
                p2pSubscriptionsLinks.add(link);
            }
        }

        return p2pSubscriptionsLinks;
	}
}
