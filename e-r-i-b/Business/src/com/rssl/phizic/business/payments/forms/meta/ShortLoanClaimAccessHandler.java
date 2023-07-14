package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.ShortLoanClaim;
import com.rssl.phizic.business.loanOffer.LoanOfferHelper;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductConditionService;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.utils.StringHelper;

/**
 * User: Moshenko
 * Date: 22.04.14
 * Time: 15:16
 * Хендлер который проверяет опубликовано ли кредитное условие по которому оформляется кредит.
 */
public class ShortLoanClaimAccessHandler extends BusinessDocumentHandlerBase
{
	private static final CreditProductConditionService conditionService  = new CreditProductConditionService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ShortLoanClaim))
		{
			throw new DocumentException("Неверный тип платежа id=" + ((BusinessDocument) document).getId() + " (Ожидается ShortLoanClaim)");
		}

		ShortLoanClaim shortLoanDocument = (ShortLoanClaim) document;
		if (StringHelper.isEmpty(shortLoanDocument.getLoanOfferId()))
		{
			try
			{
				CreditProductCondition commonCondition = conditionService.findeById(shortLoanDocument.getConditionId());
				if (commonCondition == null)
					throw new DocumentException("Не найдено кредитное условие c id=" + shortLoanDocument.getConditionId());
				if (!commonCondition.isPublished())
					throw new DocumentException("Кредитное условие c id=" + shortLoanDocument.getConditionId() + " не опубликовано.");
			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}
		} else
		{
			try
			{
				PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
				OfferId offerId = OfferId.fromString(shortLoanDocument.getLoanOfferId());
				LoanOffer loanOffer = personData.findLoanOfferById(offerId);
				if (!LoanOfferHelper.checkOwnership(loanOffer, shortLoanDocument.getOwner().getPerson()))
					throw new DocumentException("Предодобренное условие c id=" + loanOffer.getOfferId() + " не опубликовано для пользователя с id=" + shortLoanDocument.getOwner().getPerson());
			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}
		}
	}
}
