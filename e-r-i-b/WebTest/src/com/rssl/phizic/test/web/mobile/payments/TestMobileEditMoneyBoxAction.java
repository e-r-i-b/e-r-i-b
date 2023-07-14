package com.rssl.phizic.test.web.mobile.payments;

import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author vagin
 * @ created 02.02.15
 * @ $Author$
 * @ $Revision$
 * Тестовый экшен редактирования копилки.
 */
public class TestMobileEditMoneyBoxAction extends TestMobileDocumentAction
{
	private static final String PAYMENT_URL = "/private/moneyboxes/edit.do";
	private static final String FORWARD_CONFIRMED_STEP = "СonfirmedStep";
	protected static final String FORWARD_INIT = "Init";
	protected static final String FORWARD_FIRST_STEP = "FirstStep";

	public ActionForward execute(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestMobileDocumentForm form = (TestMobileDocumentForm) frm;
		String operation = form.getOperation();

		if (StringHelper.isEmpty(operation))
			return mapping.findForward(FORWARD_INIT);

		if ("init".equals(operation))
			return submitInit(mapping,form);

		if ("next".equals(operation) || "save".equals(operation))
			return submitSave(mapping, form);

		return null;
	}

	protected ActionForward submitInit(ActionMapping mapping, TestMobileDocumentForm form)
	{
		if (send(form) == 0)
		{
			String lastUrl = form.getLastUrl().toExternalForm();
			if (lastUrl.contains(PAYMENT_URL))
			{
				return mapping.findForward(FORWARD_FIRST_STEP);
			}
		}
		return mapping.findForward(FORWARD_INIT);
	}

	protected ActionForward submitSave(ActionMapping mapping, TestMobileDocumentForm form)
	{
		if(send(form) == 0)
		{
			return mapping.findForward(FORWARD_CONFIRMED_STEP);
		}
		return mapping.findForward(FORWARD_FIRST_STEP);
	}
}
