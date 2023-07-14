package com.rssl.phizic.web.common.client.offer;

import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.operations.loanOffer.GetLoanOfferViewOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.client.product.LoanProductListForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mescheryakova
 * @ created 02.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class LoanOfferAction extends OperationalActionBase
{
	protected static final String FORWARD_NEXT = "Next";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map =   new HashMap<String, String>();
		map.put("button.next", "next");
		map.put("next", "next");
		map.put("init", "start");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		LoanProductListForm frm = (LoanProductListForm) form;
		GetLoanOfferViewOperation operation = createOperation(GetLoanOfferViewOperation.class, "LoanOffer");
		operation.initialize(true);
		frm.setData(operation.getLoanOffers());
		frm.setPaymentForm(FormConstants.LOAN_OFFER_FORM);
		return getCurrentMapping().findForward(FORWARD_START);
	}

	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest requst, HttpServletResponse response) throws Exception
	{
		LoanProductListForm frm = (LoanProductListForm) form;
		ActionForward actionForward = new ActionForward(getCurrentMapping().findForward(FORWARD_NEXT));
		actionForward.setPath(actionForward.getPath() + getParamsURL(frm));
		return actionForward;
	}

	private String getParamsURL(LoanProductListForm frm)
	{
		return  "?form="
				+ ((frm.getDoRedirectToLoanCardOffer() == null || frm.getDoRedirectToLoanCardOffer() < 0) ? FormConstants.LOAN_OFFER_FORM : FormConstants.LOAN_CARD_OFFER_FORM)
				+ "&loan=" + frm.getLoanId()
				+ "&duration=" + StringHelper.getEmptyIfNull(frm.getDuration());
	}
}