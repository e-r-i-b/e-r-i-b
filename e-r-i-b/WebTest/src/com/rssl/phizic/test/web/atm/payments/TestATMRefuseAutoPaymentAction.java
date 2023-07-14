package com.rssl.phizic.test.web.atm.payments;

import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Mescheryakova
 * @ created 19.01.2012
 * @ $Author$
 * @ $Revision$
 */

public class TestATMRefuseAutoPaymentAction extends TestATMDocumentAction
{
	private static final String FORWARD_INIT = "Init";
	private static final String FORWARD_FIRST_STEP = "FirstStep";
	private static final String FORWARD_SECOND_STEP = "SecondStep";

	public ActionForward execute(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestATMDocumentForm form = (TestATMDocumentForm) frm;
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

			if ("save".equals(operation))
				return submitSave(mapping, form);

			if ("next".equals(operation))
				return submitConfirm(mapping, form);
		}

		return null;
	}

	private ActionForward submitInit(ActionMapping mapping, TestATMDocumentForm form)
	{
		if (send(form) == 0)
			return mapping.findForward(FORWARD_FIRST_STEP);
		return mapping.findForward(FORWARD_INIT);
	}

	private ActionForward submitSave(ActionMapping mapping, TestATMDocumentForm form)
	{
		if (send(form) == 0)
			return mapping.findForward(FORWARD_SECOND_STEP);
		return mapping.findForward(FORWARD_FIRST_STEP);
	}

	private ActionForward submitConfirm(ActionMapping mapping, TestATMDocumentForm form)
	{
		send(form);
		return mapping.findForward(FORWARD_SECOND_STEP);
	}
}