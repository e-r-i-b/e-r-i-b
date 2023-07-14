package com.rssl.phizic.web.client.loanoffer;

import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.loanOffer.GetLoanCardOfferViewOperation;
import com.rssl.phizic.operations.loanOffer.GetLoanOfferViewOperation;
import com.rssl.phizic.web.actions.AsyncOperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gulov
 * @ created 27.01.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Ёкшн загрузки блока предложений на главной странице
 */
public class ViewOfferAction extends AsyncOperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewOfferForm frm = (ViewOfferForm) form;
		ArrayList<OfferId> offerIds = new ArrayList<OfferId>();
		frm.setLoanOffersNoInformed(offerIds);
		frm.setLoanOffersNoPresentation(offerIds);
		if (checkAccess(GetLoanOfferViewOperation.class))
			setBottomLoanOffer(frm);

		frm.setLoanCardOffersNoInformed(offerIds);
		frm.setLoanCardOffersNoPresentation(offerIds);
		if (checkAccess(GetLoanCardOfferViewOperation.class))
			setBottomLoanCardOffer(frm);
		return mapping.findForward(FORWARD_START);
	}

	private void setBottomLoanOffer(ViewOfferForm frm) throws BusinessException, BusinessLogicException
	{
		GetLoanOfferViewOperation operation = createOperation(GetLoanOfferViewOperation.class);
		operation.initialize(false);
		frm.setBottomLoanOffers(operation.getLoanOffers());
		frm.setLoanOffersNoInformed(operation.getListNoInformedOffers());
		frm.setLoanOffersNoPresentation(operation.getListNoPresentationOffers());
	}

	private void setBottomLoanCardOffer(ViewOfferForm frm) throws BusinessException, BusinessLogicException {
		GetLoanCardOfferViewOperation operation = createOperation(GetLoanCardOfferViewOperation.class);
		operation.initialize(false);
		frm.setBottomLoanCardOffers(operation.getLoanCardOffers());
		frm.setLoanCardOffersNoInformed(operation.getListNoInformedCardOffers());
		frm.setLoanCardOffersNoPresentation(operation.getListNoPresentationCardOffers());
	}
}
