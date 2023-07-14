package com.rssl.phizic.test.web.atm.payments;

import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Balovtsev
 * Date: 09.01.13
 * Time: 13:21
 */
public class TestATMLongOfferAction extends TestATMRurPaymentAction
{
	protected static final String FORWARD_MAKE_LONG_OFFER = "MakeLongOffer";

	public ActionForward execute(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestATMDocumentForm form = (TestATMDocumentForm) frm;
		String operation = form.getOperation();

		if (StringHelper.isEmpty(operation))
			return mapping.findForward(FORWARD_INIT);

		if ("init".equals(operation))
			return submitInit(mapping, form);

		if ("makeLongOffer".equals(operation))
			return submitMakeLongOffer(mapping, form);

		if ("next".equals(operation) || "save".equals(operation))
			return submitSave(mapping, form);

		return null;
	}

	protected ActionForward submitInit(ActionMapping mapping, TestATMDocumentForm form)
	{
		if (send(form) == 0)
			return mapping.findForward(FORWARD_MAKE_LONG_OFFER);
		return mapping.findForward(FORWARD_INIT);
	}

	private ActionForward submitMakeLongOffer(ActionMapping mapping, TestATMDocumentForm form)
	{
		if (send(form) == 0)
			return mapping.findForward(FORWARD_FIRST_STEP);
		return mapping.findForward(FORWARD_MAKE_LONG_OFFER);
	}

	protected ActionForward submitSave(ActionMapping mapping, TestATMDocumentForm form)
	{
		send(form);
		if (form.getDocument() != null)
			return mapping.findForward(FORWARD_SECOND_STEP);
		return mapping.findForward(FORWARD_FIRST_STEP);
	}
}
