package com.rssl.phizic.business.resources.external.resolver;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cache.BusinessWaitCreateCacheException;
import com.rssl.phizic.business.cards.CardImpl;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.autopayments.AutoSubscriptionService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateWaitCreateCacheException;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author khudyakov
 * @ created 13.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class AutoSubscriptionResolver<T extends ExternalResourceLink> implements LinkResolver<T>
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	private static final ExternalResourceService resourceService = new ExternalResourceService();

	public List<T> getLinks(CommonLogin login, String instanceName) throws BusinessException, BusinessLogicException
	{
		return getLinks(login.getId(), resourceService.getLinks(login, CardLink.class));
	}

	public List<T> getLinks(Long loginId, String instanceName) throws BusinessException, BusinessLogicException
	{
		return getLinks(loginId, resourceService.getLinks(loginId, CardLink.class));
	}

	public List<T> getInSystemLinks(CommonLogin login, String instanceName) throws BusinessException, BusinessLogicException
	{
		return getLinks(login.getId(), resourceService.getInSystemLinks(login, CardLink.class));
	}

	public List<T> getInMobileLinks(CommonLogin login, String instanceName) throws BusinessException, BusinessLogicException
	{
		return getLinks(login.getId(), resourceService.getInMobileLinks(login, CardLink.class));
	}

	public List<T> getInSocialLinks(CommonLogin login, String instanceName) throws BusinessException, BusinessLogicException
	{
		return getLinks(login.getId(), resourceService.getInSocialLinks(login, CardLink.class));
	}

	public List<T> getInATMLinks(CommonLogin login, String instanceName) throws BusinessException, BusinessLogicException
	{
		return getLinks(login.getId(), resourceService.getInATMLinks(login, CardLink.class));
	}

	public T findByExternalId(CommonLogin login, String externalId, String instanceName) throws BusinessException
	{
		throw new UnsupportedOperationException();
	}

	private List<T> getLinks(Long loginId, List<CardLink> cardLinks) throws BusinessException, BusinessLogicException
	{
		if (cardLinks.isEmpty())
		{
			log.info("Невозможно получить список автоплатежей из-за отсутствия карт у клиента");
			return Collections.emptyList();
		}

		List<Card> clientCards = new ArrayList<Card>();
		for (CardLink cardLink : cardLinks)
		{
			Card gateCard = cardLink.getCard();
			if (gateCard != null)
			{
				CardImpl card = new CardImpl(gateCard);
				card.setExpireDate(cardLink.getExpireDate());
				clientCards.add(card);
			}
		}

		try
		{
			AutoSubscriptionService subscriptionService = GateSingleton.getFactory().service(AutoSubscriptionService.class);
			List<AutoSubscription> subscriptions = subscriptionService.getAutoSubscriptions(clientCards);
			if (CollectionUtils.isEmpty(subscriptions))
				return Collections.emptyList();

			List<AutoSubscriptionLink> subscriptionLinks = new ArrayList<AutoSubscriptionLink>(subscriptions.size());
			for (AutoSubscription subscription : subscriptions)
			{
				subscriptionLinks.add(new AutoSubscriptionLink(loginId, subscription));
			}

			return (List<T>) subscriptionLinks;
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateWaitCreateCacheException e)
		{
			throw new BusinessWaitCreateCacheException(e.getMessage(), e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}

	}
}
