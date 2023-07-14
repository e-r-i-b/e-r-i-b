package com.rssl.phizic.operations.autopayments.links;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.longoffer.autopayment.links.AutoPaymentLinksService;
import com.rssl.phizic.business.resources.external.AutoPaymentLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author osminin
 * @ created 08.02.2011
 * @ $Author$
 * @ $Revision$
 * ќпераци€ дл€ получени€ списка линков автоплатежей
 */
public class ListAutoPaymentLinksOperation extends OperationBase implements ListEntitiesOperation
{
	private static AutoPaymentLinksService linksService = new AutoPaymentLinksService();
	private final AutoPaymentLinksService autoPaymentLinksService = new AutoPaymentLinksService();

	private List<AutoPaymentLink> links = new ArrayList<AutoPaymentLink>();

	public void initialize() throws BusinessException, BusinessLogicException
	{
		links = linksService.findByUserId(AuthModule.getAuthModule().getPrincipal().getLogin());
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		List<CardLink> cardLinks = personData.getCards();
		autoPaymentLinksService.updateAutoPaymentLinks(personData.getPerson().getLogin(), cardLinks, links);
	}

	public void initialize(CardLink cardLink) throws BusinessException, BusinessLogicException
	{
		initialize();
		if (links == null)
			return;
		Iterator tLinks = links.iterator();
		while (tLinks.hasNext())
		{
			AutoPaymentLink link = (AutoPaymentLink) tLinks.next();
			if (link.getCardLink() == null || !link.getCardLink().getNumber().equals(cardLink.getNumber()))
			{
				tLinks.remove();
			}
		}
	}

	public void initialize(Long cardId) throws BusinessException, BusinessLogicException
	{
		if (cardId == null)
			initialize();
		else
		{
			CardLink cardLink = PersonContext.getPersonDataProvider().getPersonData().getCard(cardId);
			initialize(cardLink);
		}
	}

	public List<AutoPaymentLink> getEntity()
	{
		return links;
	}
}
