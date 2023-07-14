package com.rssl.phizic.test.web.atm.payments;

import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Тест создания автоплатежа оплаты услуг (iqwave и шинные)
 * @author Mescheryakova
 * @ created 22.12.2011
 * @ $Author$
 * @ $Revision$
 */

public class TestATMCreateAutoPaymentAction extends TestATMDocumentAction
{
	private static final String FORWARD_INIT = "Init";
	private static final String FORWARD_EDIT_INITIAL_DATA = "EditInitialData";
	private static final String FORWARD_EDIT_AND_VIEW_DOCUMENT = "EditAndViewDocument";

	public ActionForward execute(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response)
	{
		TestATMDocumentForm form = (TestATMDocumentForm) frm;
		String operation = form.getOperation();

		// 0. показ формы для заполнения service, billing, provider
		if (StringHelper.isEmpty(operation))
			return mapping.findForward(FORWARD_INIT);
		// 1. заполнение service, billing, provider
		else if ("init".equals(operation))
			return init(mapping, form);
		// 2. редактирование initialData (первый шаг оплаты билингового платежа)
		else if ("makeLongOffer".equals(operation))
			return submitMakeLongOffer(mapping, form);
		// 3. редактирование документа (CreateAutoPaymentDocument для iqwave, RurPayJurSB/CreateAutoSubscriptionDocument для шинных)
		else if ("next".equals(operation))
			return submitSaveAutoPayment(mapping, form);

		return null;
	}

	private ActionForward init(ActionMapping mapping, TestATMDocumentForm form)
	{
		if (send(form) == 0)
			return mapping.findForward(FORWARD_EDIT_INITIAL_DATA);
		else return mapping.findForward(FORWARD_INIT);
	}

	private ActionForward submitMakeLongOffer(ActionMapping mapping, TestATMDocumentForm form)
	{
		final int result = send(form);
		if (result == 0 || form.getDocument() != null)
			return mapping.findForward(FORWARD_EDIT_AND_VIEW_DOCUMENT);
		return mapping.findForward(FORWARD_EDIT_INITIAL_DATA);
	}

	private ActionForward submitSaveAutoPayment(ActionMapping mapping, TestATMDocumentForm form)
	{
		send(form);
		return mapping.findForward(FORWARD_EDIT_AND_VIEW_DOCUMENT);
	}
}