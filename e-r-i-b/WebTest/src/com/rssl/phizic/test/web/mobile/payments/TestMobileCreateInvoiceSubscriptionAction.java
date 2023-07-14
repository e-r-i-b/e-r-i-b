package com.rssl.phizic.test.web.mobile.payments;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Balovtsev
 * @since 31.10.14.
 */
public class TestMobileCreateInvoiceSubscriptionAction extends TestMobilePaymentActionBase
{

	protected static final String FORWARD_CONFIRM = "Confirm";

	public ActionForward execute(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestMobileDocumentForm form = (TestMobileDocumentForm) frm;

		String operation = form.getOperation();
		if ("next".equals(operation))
		{
			return submitNext(mapping, form);
		}

		if ("save".equals(operation))
		{
			return submitSave(mapping, form);
		}

		return super.execute(mapping, frm, request, response);
	}

	protected ActionForward submitNext(ActionMapping mapping, TestMobileDocumentForm form)
	{
		int status = send(form);

		if (status == 0 || form.getDocument() != null)
		{
			return mapping.findForward(FORWARD_SECOND_STEP);
		}

		return mapping.findForward(FORWARD_FIRST_STEP);
	}

	@Override
	protected ActionForward submitEdit(ActionMapping mapping, TestMobileDocumentForm form)
	{
		super.submitEdit(mapping, form);
		return mapping.findForward(FORWARD_FIRST_STEP);
	}

	protected ActionForward submitSave(ActionMapping mapping, TestMobileDocumentForm form)
	{
		int status = send(form);

		if (status == 0 || form.getDocument() != null)
		{
			return mapping.findForward(FORWARD_CONFIRM);
		}

		return mapping.findForward(FORWARD_SECOND_STEP);
	}
}
