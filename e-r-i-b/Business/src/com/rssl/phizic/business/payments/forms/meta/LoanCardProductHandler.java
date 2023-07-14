package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.documents.payments.LoanCardProductClaim;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;

/**
 * Хэндлер, выполняющий проверку, есть ли у клиента кредитная карта. Если есть, то создавать заявку на
 * кредитную карту нельзя.
 * @author Pankin
 * @ created 25.10.2011
 * @ $Author$
 * @ $Revision$
 */

public class LoanCardProductHandler extends BusinessDocumentHandlerBase
{
	private static final String CREDIT_CARD_EXIST_MESSAGE = "Вы не можете создать заявку на кредитную карту," +
			" потому что у Вас уже есть кредитная карта Сбербанка России.";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof LoanCardProductClaim))
		{
			throw new DocumentException("Неверный тип платежа id=" + ((BusinessDocument) document).getId() +
					" (Ожидается LoanCardProductClaim)");
		}

		PersonDataProvider personDataProvider = PersonContext.getPersonDataProvider();
		PersonData personData = personDataProvider == null ? null : personDataProvider.getPersonData();
		try
		{
			if (personData != null && CardsUtil.hasClientActiveCreditCard())
				throw new DocumentLogicException(CREDIT_CARD_EXIST_MESSAGE);
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
}
