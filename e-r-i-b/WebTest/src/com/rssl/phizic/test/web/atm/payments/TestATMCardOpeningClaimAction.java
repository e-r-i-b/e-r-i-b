package com.rssl.phizic.test.web.atm.payments;

import com.rssl.phizic.business.test.atm.generated.ResponseElement;
import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Moshenko
 * Date: 29.02.12
 * Time: 10:50
 */
public class TestATMCardOpeningClaimAction extends TestATMDocumentAction
{
	protected static final String FORWARD_INIT = "Init";
	protected static final String INCOME_STEP = "IncomeStep";
	protected static final String FORWARD_LOAN_CARD_OFFER_STEP = "LoanCardOfferStep";
	protected static final String FORWARD_LOAN_CARD_PRODUCT_STEP = "LoanCardProductStep";
	protected static final String FORWARD_LOAN_PRODUCT = "LoanCardProduct";
	protected static final String FORWARD_LOAN_OFFER = "LoanCardOffer";

	public ActionForward execute(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestATMCardOpeningClaimForm form = (TestATMCardOpeningClaimForm) frm;
		String documentStatus = form.getDocumentStatus();
		String operation = form.getOperation();

		if (StringHelper.isEmpty(documentStatus))
			documentStatus = "INITIAL";

		if (documentStatus.equals("INITIAL"))
		{
			if (StringHelper.isEmpty(operation))
				return mapping.findForward(FORWARD_INIT);

			return submitInit(mapping, form);
		}

		return null;
	}

	protected ActionForward submitInit(ActionMapping mapping, TestATMDocumentForm form)
	{
		int stateCode = send(form);
		if (stateCode == 0)
		{
			if (form.getResponse() instanceof ResponseElement)
			{
				ResponseElement	respMob4 = (ResponseElement) form.getResponse();
			if (respMob4.getLoanCardOfferStage()!=null)
					return mapping.findForward(FORWARD_LOAN_CARD_OFFER_STEP);
			else if (respMob4.getLoanCardProductStage()!=null)
					return mapping.findForward(FORWARD_LOAN_CARD_PRODUCT_STEP);
			else if (respMob4.getIncomeStage()!=null)
					return mapping.findForward(INCOME_STEP);
			else if (respMob4.getInitialData() != null && respMob4.getInitialData().getLoanCardProduct()!=null)
					return mapping.findForward(FORWARD_LOAN_PRODUCT);
			else if (respMob4.getDocument() != null && respMob4.getDocument().getLoanCardProductDocument()!=null)
					return mapping.findForward(FORWARD_LOAN_PRODUCT);
			else if (respMob4.getInitialData() != null && respMob4.getInitialData().getLoanCardOffer()!=null)
					return mapping.findForward(FORWARD_LOAN_OFFER);
			else if (respMob4.getDocument() != null && respMob4.getDocument().getLoanCardOfferDocument()!=null)
					return mapping.findForward(FORWARD_LOAN_OFFER);
			}
		}
			return  mapping.findForward(FORWARD_INIT);
	}
}