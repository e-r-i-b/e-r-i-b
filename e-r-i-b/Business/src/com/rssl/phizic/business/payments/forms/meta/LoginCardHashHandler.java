package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.comparator.CardLinkDateComparator;
import com.rssl.phizic.config.loyalty.LoyaltyHelper;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.utils.StringHelper;

import java.util.Collections;
import java.util.List;

/**
 * —охран€ет в создаваемую завку захешированный номер карты (дл€ отчета по ло€льности)
 *
 @author: Egorovaa
 @ created: 17.09.2012
 @ $Author$
 @ $Revision$
 */
public class LoginCardHashHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		// ѕолучаем номер карты, по которой вошел клиент
		Login clientLogin = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		String loginCardNumber = clientLogin.getLastLogonCardNumber();

		// ≈сли не получили, ищем среди карт клиента ту, что имеет наиболее поздний срок действи€
		if (StringHelper.isEmpty(loginCardNumber))
		{
			try
			{
				List<CardLink> res = new ExternalResourceService().getLinks(clientLogin, CardLink.class);
				Collections.sort(res, new CardLinkDateComparator());

				loginCardNumber = res.get(res.size() - 1).getNumber();
			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}
			catch (BusinessLogicException e)
			{
				throw new DocumentLogicException(e);
			}
		}

		if (StringHelper.isNotEmpty(loginCardNumber))
		{
			String loginCardHash = LoyaltyHelper.generateHash(loginCardNumber);
			AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;
			accountOpeningClaim.setLoginCardHash(loginCardHash);
		}
		else
		{
			log.error("” клиента c loginId " + clientLogin.getId() + " не найдено на одной карты");
		}
	}
}
