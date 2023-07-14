package com.rssl.phizic.test.web.social.payments;

import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jatsky
 * @ created 13.10.14
 * @ $Author$
 * @ $Revision$
 */

public class TestSocialQuickCreateAutoPaymentAction extends TestSocialDocumentAction
{
	private static final String FORWARD_INIT = "Init";
	private static final String FORWARD_EDIT_AND_VIEW_DOCUMENT = "EditAndViewDocument";
	private static final String CONFIRM_ADDRESS = "/private/payments/confirm.do";

	public ActionForward execute(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response)
	{
		TestSocialDocumentForm form = (TestSocialDocumentForm) frm;
		String operation = form.getOperation();

		if (StringHelper.isEmpty(operation))
		{
			String method = request.getMethod();
			// 0. показ формы для заполнения id
			if (method == "GET")
				return mapping.findForward(FORWARD_INIT);
				// 1. заполнение id прототипа
				// /private/payments/makeLongOffer.do?id=N
			else if (method == "POST")
			{
				send(form);
				return mapping.findForward(FORWARD_EDIT_AND_VIEW_DOCUMENT);
			}
		}
		// 2. редактирование документа (CreateAutoPaymentDocument для iqwave, RurPayJurSB/CreateAutoSubscriptionDocument для шинных)
		// /private/payments/payment.do?operation=save
		else if ("save".equals(operation) || "next".equals(operation) || "edit".equals(operation))
		{
			if ("edit".equals(operation))
				form.setAddress(CONFIRM_ADDRESS);
			send(form);
			return mapping.findForward(FORWARD_EDIT_AND_VIEW_DOCUMENT);
		}

		return null;
	}
}

