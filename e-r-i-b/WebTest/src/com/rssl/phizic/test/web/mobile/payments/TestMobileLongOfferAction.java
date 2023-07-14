package com.rssl.phizic.test.web.mobile.payments;

import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * —оздание автоплатежа - длительного поручени€ (LoanPayment, InternalPayment, RurPayment)
 * @author Dorzhinov
 * @ created 03.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileLongOfferAction extends TestMobileDocumentAction
{
	private static final String FORWARD_INIT = "Init";
	private static final String FORWARD_MAKE_LONG_OFFER = "MakeLongOffer";
	private static final String FORWARD_FIRST_STEP = "FirstStep";
	private static final String FORWARD_SECOND_STEP = "SecondStep";

	public ActionForward execute(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestMobileDocumentForm form = (TestMobileDocumentForm) frm;
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

	private ActionForward submitInit(ActionMapping mapping, TestMobileDocumentForm form)
	{
		if (send(form) == 0)
			return mapping.findForward(FORWARD_MAKE_LONG_OFFER);
		return mapping.findForward(FORWARD_INIT);
	}

	private ActionForward submitMakeLongOffer(ActionMapping mapping, TestMobileDocumentForm form)
	{
		if (send(form) == 0)
			return mapping.findForward(FORWARD_FIRST_STEP);
		return mapping.findForward(FORWARD_MAKE_LONG_OFFER);
	}

	private ActionForward submitSave(ActionMapping mapping, TestMobileDocumentForm form)
	{
		send(form);
		if (form.getDocument() != null)
			return mapping.findForward(FORWARD_SECOND_STEP);
		return mapping.findForward(FORWARD_FIRST_STEP);
	}
}
