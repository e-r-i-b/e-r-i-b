package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.longoffer.LongOfferServiceHelper;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.LongOfferLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.LongOfferService;

import java.util.List;

/**
 * @author osminin
 * @ created 29.10.2010
 * @ $Author$
 * @ $Revision$
 *
 * Хендлер для обновления линков длительных поручений
 */
public class UpdateLongOfferLinksHandler extends BusinessDocumentHandlerBase
{
	private static ExternalResourceService externalResourceService = new ExternalResourceService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent)
	{
		try
		{
			LongOfferService longOfferService = GateSingleton.getFactory().service(LongOfferService.class);

			ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
			Client client = person.asClient();

			List<LongOffer> longOffers = longOfferService.getClientsLongOffers(client);
			List<LongOfferLink> links = externalResourceService.getLinks(person.getLogin(), LongOfferLink.class);

            for (LongOffer longOffer: longOffers)
            {
	            LongOfferLink longOfferLink = LongOfferServiceHelper.findLongOfferLinkByNumber(links, longOffer.getNumber());
	            if (longOfferLink == null)
		            externalResourceService.addLongOfferLink(person.getLogin(), longOffer);
	            else
		            links.remove(longOfferLink);
            }
                                            			
			for (LongOfferLink link: links)
				externalResourceService.removeLink(link);
		}
		catch(Exception e)
		{
			log.error("Ошибка при обновлении линков автоплатежей.", e);
		}
	}
}
