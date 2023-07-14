package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.documents.payments.LoanCardClaim;
import com.rssl.phizic.business.documents.payments.LoanCardOfferClaim;
import com.rssl.phizic.business.documents.payments.LoanClaimBase;
import com.rssl.phizic.business.documents.payments.LoanOfferClaim;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author gulov
 * @ created 08.07.2011
 * @ $Authors$
 * @ $Revision$
 */
public class RemoveLoanOfferHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		String errorMsgByLoanOfferType = null;

		try
		{
			// удаляем предложение по кредиту/кредитной карте
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

			if (document instanceof LoanCardClaim)
			{
				errorMsgByLoanOfferType = "карте";

				//в предодобренной заявке на кред. карту id предложения вынесен в отдельное поле "offer-id", т.к. поле "loan" уже занято id условия.
				String offerIdAsString = ((LoanCardClaim) document).getOfferId();

				if (StringHelper.isNotEmpty(offerIdAsString))
				{
					//Предложение по картам не удаляем а помечаем как использованное
					personData.updateLoanCardOfferAsUsed(OfferId.fromString(offerIdAsString));
				}
			}
			else if (document instanceof LoanCardOfferClaim) // предложение по карте
			{
				errorMsgByLoanOfferType = "карте";

				//в предодобренной заявке на кред. карту id предложения вынесен в отдельное поле "offer-id", т.к. поле "loan" уже занято id условия.
				String offerIdAsString = ((LoanCardOfferClaim) document).getOfferId();

				if (StringHelper.isNotEmpty(offerIdAsString))
				{
				    //Предложение по картам не удаляем а помечаем как использованное
					personData.updateLoanCardOfferAsUsed(OfferId.fromString(offerIdAsString));
				}
			}
			else if (document instanceof LoanOfferClaim) // предложение по кредиту
			{
				errorMsgByLoanOfferType = "кредиту";

				//в предодобренной заявке на кредит id предложения хранится в поле "loan"
				String offerIdAsString = ((LoanClaimBase) document).getLoan();

				if (StringHelper.isNotEmpty(offerIdAsString))
				{
					personData.updateLoanOfferAsUsed(OfferId.fromString(offerIdAsString));
				}
			}
		}
		catch (Exception e)
		{
			// При любых ислючениях пишем ошибку в лог т.к. платеж успешно исполнился. и его надо отобразить клиенту.
			log.error(String.format("Ошибка при обработке предодобренного предложения по %s", errorMsgByLoanOfferType), e);
		}
	}
}
