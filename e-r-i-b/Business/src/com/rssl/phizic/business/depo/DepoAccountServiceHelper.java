package com.rssl.phizic.business.depo;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.AbstractDepoAccountClaim;
import com.rssl.phizic.business.documents.RecallDepositaryClaim;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;

import java.util.List;

/**
 * @author lukina
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepoAccountServiceHelper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final ExternalResourceService resourceService = new ExternalResourceService();
	/*
	Поиск ссылки на счет депо по номеру счета
	 */
	public static DepoAccountLink findDepoAccountLinkByNumber(List<DepoAccountLink> depoAccountLinks, String accountNumber)
	{
		for (DepoAccountLink link : depoAccountLinks)
		{
			if(link.getAccountNumber().equals(accountNumber))
				return link;
		}
		return null;
	}

	/**
	 * Ищет DepoAccountLink в базе по externalId из документа
	 * @param document - документ
	 * @return DepoAccountLink
	 */
	public static DepoAccountLink getDepoAccountLinkFromDocument(BusinessDocument document)
	{
		if (document instanceof AbstractDepoAccountClaim || document instanceof RecallDepositaryClaim)
		{
			String externalId;
			if (document instanceof AbstractDepoAccountClaim)
				externalId = ((AbstractDepoAccountClaim) document).getDepoExternalId();
			else
				externalId = ((RecallDepositaryClaim) document).getDepoExternalId();

			if (externalId == null)
				return null;
			
			ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
			try
			{
				return resourceService.findLinkByExternalId(DepoAccountLink.class, externalId, person.getLogin());
			}
			catch (BusinessException e)
			{
				log.error("Ошибка при получении ссылки на счет депо, externalId=" + externalId);
				return null;
			}
		}

		log.error("Ошибка при получении ссылки на счет депо: Неверный тип документа " + document.getClass());
		return null;
	}
}
