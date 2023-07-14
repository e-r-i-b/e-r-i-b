package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.gate.utils.StoredResourcesService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * Сервис для обновления сохраненных продуктов
 * @author Pankin
 * @ created 26.02.14
 * @ $Author$
 * @ $Revision$
 */
public class StoredResourcesServiceImpl extends AbstractService implements StoredResourcesService
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();
	private static final SimpleService simpleService = new SimpleService();
	private static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	protected StoredResourcesServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public void updateStoredAccount(Long loginId, Account account)
	{
		StoredResourceHelper.updateStoredResource(findLink(loginId, ResourceType.ACCOUNT, account.getNumber()), account);
	}

	public void updateStoredCard(Long loginId, Card card)
	{
		StoredResourceHelper.updateStoredResource(findLink(loginId, ResourceType.CARD, card.getNumber()), card);
	}

	public void updateStoredLoan(Long loginId, Loan loan)
	{
		StoredResourceHelper.updateStoredResource(findLink(loginId, ResourceType.LOAN, loan.getAccountNumber()), loan);
	}

	public void updateStoredDepoAccount(Long loginId, DepoAccount depoAccount)
	{
		StoredResourceHelper.updateStoredResource(findLink(loginId, ResourceType.DEPO_ACCOUNT, depoAccount.getAccountNumber()), depoAccount);
	}

	public void updateStoredIMAccount(Long loginId, IMAccount imAccount)
	{
		StoredResourceHelper.updateStoredResource(findLink(loginId, ResourceType.IM_ACCOUNT, imAccount.getNumber()), imAccount);
	}

	public void updateStoredLongOffer(Long loginId, LongOffer longOffer)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(LongOfferLink.class).
				add(Expression.eq("number", longOffer.getNumber())).
				add(Expression.eq("loginId", loginId));
		try
		{
			LongOfferLink link = simpleService.findSingle(criteria);
			StoredResourceHelper.updateStoredResource(link, longOffer);
		}
		catch (Exception e)
		{
			LOG.error("Ошибка во время обновления информации оффлайн продукта", e);
		}
	}

	public void updateStoredSecurityAccount(Long loginId, SecurityAccount securityAccount)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(SecurityAccountLink.class).
				add(Expression.eq("number", securityAccount.getSerialNumber())).
				add(Expression.eq("loginId", loginId));
		try
		{
			SecurityAccountLink link = simpleService.findSingle(criteria);
			StoredResourceHelper.updateStoredResource(link, securityAccount);
		}
		catch (Exception e)
		{
			LOG.error("Ошибка во время обновления информации оффлайн продукта", e);
		}
	}

	private ExternalResourceLink findLink(Long loginId, ResourceType type, String number)
	{
		try
		{
			return externalResourceService.findLinkByNumber(loginId, type, number);
		}
		catch (Exception e)
		{
			LOG.error("Ошибка во время обновления информации оффлайн продукта", e);
			return null;
		}
	}
}
