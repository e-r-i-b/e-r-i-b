package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;

/**
 * @author Balovtsev
 * @since  23.04.2015.
 */
public class LoanCardClaimHandler extends BusinessDocumentHandlerBase
{
	private static final String CREDIT_CARD_CLAIM_EXIST_MESSAGE = "Уважаемый клиент! От Вашего имени ранее уже поступала заявка на кредитную карту. Оформление второй кредитной карты не допускается. Ваша заявка отклонена.";

	private final BusinessDocumentService service = new BusinessDocumentService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!PersonContext.isAvailable())
			return;

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		if (personData.isGuest())
			return;

		try
		{
			if (!service.isLoanCardClaimAvailable(personData.getLogin()))
			{
				throw new DocumentLogicException(CREDIT_CARD_CLAIM_EXIST_MESSAGE);
			}
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
