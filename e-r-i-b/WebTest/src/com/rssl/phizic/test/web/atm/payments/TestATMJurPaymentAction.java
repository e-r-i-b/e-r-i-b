package com.rssl.phizic.test.web.atm.payments;

import com.rssl.phizic.business.test.atm.generated.ResponseElement;
import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Erkin
 * @ created 10.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class TestATMJurPaymentAction extends TestATMDocumentAction
{
	private static final String FORWARD_INIT = "Init";
	private static final String FORWARD_FIRST_STEP = "FirstStep";
	private static final String FORWARD_SECOND_STEP = "SecondStep";
	private static final String FORWARD_SELECT_PROVIDER = "SelectProvider";

	public ActionForward execute(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response)
	{
		TestATMJurPaymentForm form = (TestATMJurPaymentForm) frm;
		String documentStatus = form.getDocumentStatus();
		String operation = form.getOperation();

		if (StringHelper.isEmpty(documentStatus))
			documentStatus = "INITIAL";

		if (documentStatus.equals("INITIAL"))
		{
			if (StringHelper.isEmpty(operation))
				return mapping.findForward(FORWARD_INIT);

			if ("init".equals(operation))
				return submitInit(mapping, form);

			if ("next".equals(operation))
				return submitFirstStep(mapping, form);

			return null;
		}

		if (documentStatus.equals("DRAFT"))
		{
			if ("next".equals(operation))
				return submitSecondStep(mapping, form);

			return null;
		}

		return null;
	}

	private ActionForward submitInit(ActionMapping mapping, TestATMDocumentForm form)
	{
		switch (send(form))
		{
			case 0:
			case 1:
				if (form.getDocument() != null)
					return mapping.findForward(FORWARD_SECOND_STEP);
				break;
		}
		return mapping.findForward(FORWARD_FIRST_STEP);
	}

	private ActionForward submitFirstStep(ActionMapping mapping, TestATMDocumentForm form)
	{
		switch (send(form))
		{
			case 0:
				if (form.getResponse() != null)
				{
					ResponseElement.BillingPayments billingPayments = ((com.rssl.phizic.business.test.atm.generated.ResponseElement)form.getResponse()).getBillingPayments();
					if (billingPayments != null)
							return mapping.findForward(FORWARD_SELECT_PROVIDER);
				}
				if (form.getDocument() != null)
					return mapping.findForward(FORWARD_SECOND_STEP);
				break;
			case 1:
				if (form.getDocument() != null)
					return mapping.findForward(FORWARD_SECOND_STEP);
				break;
		}

		return mapping.findForward(FORWARD_FIRST_STEP);
	}

	private ActionForward submitSecondStep(ActionMapping mapping, TestATMDocumentForm form)
	{
		send(form);
		return mapping.findForward(FORWARD_SECOND_STEP);
	}
}