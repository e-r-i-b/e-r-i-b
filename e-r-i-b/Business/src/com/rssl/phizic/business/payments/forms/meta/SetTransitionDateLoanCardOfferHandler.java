package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.LoanCardClaim;
import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.utils.StringHelper;

/**
 * Установить дату  перехода на страницу оформления заявки
 * @author Moshenko
 * @ created 27.02.14
 * @ $Author$
 * @ $Revision$
 */
public class SetTransitionDateLoanCardOfferHandler  extends LoanCardOfferHandlerBase
{
	@Override
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (document instanceof LoanCardClaim)
		{
			LoanCardClaim cardOffer  = (LoanCardClaim) document;
			String        idAsString = cardOffer.getOfferId();

			if (!cardOffer.getPreapproved())
			{
				return;
			}

			if (StringHelper.isEmpty(idAsString))
			{
				throw new DocumentException("К документу с id = " + cardOffer.getId() + "не привязано предодобренное предложение по карте.");
			}

			OfferId    offerId    = OfferId.fromString(idAsString);
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();

			LoanCardOffer offer = null;
			try
			{
				offer = personData.findLoanCardOfferById(offerId);

				if (offer != null)
				{
					updateOffer(offer);
				}
			}
			catch (Exception e)
			{
				throw new DocumentException("Ошибка при обработке LoanCardOffer c id:" + offerId , e);
			}

			if(offer == null)
			{
				throw new DocumentException("Не найдено предложение по предодобенной карте с id=" + offerId + ".");
			}
		}
		else
		{
			super.process(document, stateMachineEvent);
		}
	}

	public void updateOffer(LoanCardOffer offer) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		personData.updateLoanCardOfferTransitionDate(offer.getOfferId());
	}
}
