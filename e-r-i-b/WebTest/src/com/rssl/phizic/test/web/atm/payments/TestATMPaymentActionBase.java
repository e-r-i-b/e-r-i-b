package com.rssl.phizic.test.web.atm.payments;

import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * �������� ������: --init--> [initialData] --save/next--> [document]
 * @author Dorzhinov
 * @ created 30.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class TestATMPaymentActionBase extends TestATMDocumentAction
{
	protected static final String FORWARD_INIT = "Init";
	protected static final String FORWARD_FIRST_STEP = "FirstStep";
	protected static final String FORWARD_SECOND_STEP = "SecondStep";

	public ActionForward execute(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestATMDocumentForm form = (TestATMDocumentForm) frm;
		String operation = form.getOperation();

		if (StringHelper.isEmpty(operation))
			return mapping.findForward(FORWARD_INIT);

		if ("init".equals(operation))
			return submitInit(mapping,form);

		if ("next".equals(operation) || "save".equals(operation))
			return submitSave(mapping, form);

		return null;
	}

	protected ActionForward submitInit(ActionMapping mapping, TestATMDocumentForm form)
	{
		if (send(form) == 0)
			return mapping.findForward(FORWARD_FIRST_STEP);
		return  mapping.findForward(FORWARD_INIT);
	}

	protected ActionForward submitSave(ActionMapping mapping, TestATMDocumentForm form)
	{
		send(form);
		if (form.getDocument() != null)
			return mapping.findForward(FORWARD_SECOND_STEP);
		return mapping.findForward(FORWARD_FIRST_STEP);
	}
}

