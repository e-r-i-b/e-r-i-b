package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mescheryakova
 * @ created 07.12.2011
 * @ $Author$
 * @ $Revision$
 *
 * Для заявок на общих основаниях проверяет, что по выбранному кредитному продукту/карте еще нет заявок, отправленных на рассмотрение
 * Для предодоборенных кредитов / кредитных карт проверяет отсутствие заявок, отправленных на рассмотрение
 */

public class LoanProductHandler extends BusinessDocumentHandlerBase
{
	private static final Map<Class, String> messages = new HashMap<Class, String>();
	private static final BusinessDocumentService service = new BusinessDocumentService();

	public static final String PREAPPROVED_LOAN_CARD_TEXT = "Вы не можете повторно отправить в банк заявку на выдачу предодобренной кредитной карты, пока предыдущая заявка не обработана в банке.";

	static
	{
		messages.put(LoanProductClaim.class, "Вы не можете повторно отправить в банк заявку на выдачу кредита по данному продукту, пока предыдущая заявка не обработана в банке.");
		messages.put(LoanCardProductClaim.class, "Вы не можете повторно отправить в банк заявку на выдачу данной кредитной карты, пока предыдущая заявка не обработана в банке.");
		messages.put(LoanOfferClaim.class, "Вы не можете повторно отправить в банк заявку на выдачу предодобренного кредита, пока предыдущая заявка не обработана в банке.");
		messages.put(LoanCardOfferClaim.class, PREAPPROVED_LOAN_CARD_TEXT);
		messages.put(PreapprovedLoanCardClaim.class, PREAPPROVED_LOAN_CARD_TEXT);
	}

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof LoanClaimBase))
			throw new DocumentException("Неверный тип платежа id=" + ((BusinessDocument) document).getId() + " (Ожидается LoanClaimBase)");

		try
		{
			if(service.isExistDispatchedClaim((LoanClaimBase)document))
				throw new DocumentLogicException(messages.get(document.getClass()));
		}
		catch(BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}

