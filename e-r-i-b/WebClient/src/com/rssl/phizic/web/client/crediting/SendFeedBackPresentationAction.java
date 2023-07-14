package com.rssl.phizic.web.client.crediting;

import com.rssl.ikfl.crediting.FeedbackType;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.loanOffer.LoanOfferHelper;
import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Nady
 * @ created 22.01.2015
 * @ $Author$
 * @ $Revision$
 */

/**
 * Экшн отправки отклика "Презентация"
 */
public class SendFeedBackPresentationAction extends OperationalActionBase
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		SendFeedBackPresentationForm frm = (SendFeedBackPresentationForm)form;
		String offerId = frm.getId();

		List<OfferId> offerIds = new ArrayList<OfferId>();

		if (frm.isCardOffer())
		{
			if (StringHelper.isNotEmpty(offerId))
			{
				offerIds.add(OfferId.fromString(offerId));
			}
			else
			{
				List<LoanCardOffer> loanCardOffers = personData.getLoanCardOfferByPersonData(null, null);
				for (LoanCardOffer loanCardOffer : loanCardOffers)
				{
					offerIds.add(loanCardOffer.getOfferId());
				}
			}
			if (!offerIds.isEmpty())
				personData.sendLoanCardOffersFeedback(offerIds, FeedbackType.PRESENTATION);
		}
		else
		{
			if (StringHelper.isNotEmpty(offerId))
			{
				offerIds.add(OfferId.fromString(offerId));
			}
			else
			{
				Set<LoanOffer> loanOffers = LoanOfferHelper.filterLoanOffersByEndDate(personData.getLoanOfferByPersonData(null, null));
				for (LoanOffer loanOffer : loanOffers)
				{
					offerIds.add(loanOffer.getOfferId());
				}
			}
			if (!offerIds.isEmpty())
				personData.sendLoanOffersFeedback(offerIds, FeedbackType.PRESENTATION);
		}
		return null;
	}

	protected boolean isAjax()
	{
		return true;
	}
}
