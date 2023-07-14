package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.LoanCardOfferClaim;
import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.utils.StringHelper;

/**
 * ������� ������� ��� ��������� ����������� �� ����.
 * @author Moshenko
 * @ created 27.02.14
 * @ $Author$
 * @ $Revision$
 */

public abstract class LoanCardOfferHandlerBase extends BusinessDocumentHandlerBase
{

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (document instanceof LoanCardOfferClaim)
		{
			LoanCardOfferClaim cardOffer = (LoanCardOfferClaim) document;
			String offerIdAsString = cardOffer.getOfferId();
			if (StringHelper.isEmpty(offerIdAsString))
				throw new DocumentException("� ��������� � id = " +  cardOffer.getId() + "�� ��������� �������������� ����������� �� �����.");
			OfferId offerId = OfferId.fromString(offerIdAsString);
			try
			{
				PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
				LoanCardOffer offer = personData.findLoanCardOfferById(offerId);
				if (offer != null)
				{
					updateOffer(offer);
				}
				else
					throw new DocumentException("�� ������� ����������� �� ������������� ����� � id="+ offerId + ".");
			}
			catch (Exception e)
			{
				throw new DocumentException("������ ��� ��������� LoanCardOffer c id:" + offerId , e);
			}
		}
	}
	/**
	 * @param offer �������������� ����������� �� �����.
	 */
	public abstract void updateOffer(LoanCardOffer offer) throws BusinessException;
}
