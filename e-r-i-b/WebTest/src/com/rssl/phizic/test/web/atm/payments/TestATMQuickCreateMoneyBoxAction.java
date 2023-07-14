package com.rssl.phizic.test.web.atm.payments;

import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author vagin
 * @ created 16.01.15
 * @ $Author$
 * @ $Revision$
 * Тестовый экшен создания копилки "с нуля"
 */
public class TestATMQuickCreateMoneyBoxAction extends TestATMDocumentAction
{
	private static final String FORWARD_EDIT_AND_VIEW_DOCUMENT = "EditAndViewDocument";

	public ActionForward execute(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response)
	{
		TestATMDocumentForm form = (TestATMDocumentForm) frm;
		String operation = form.getOperation();

		if (StringHelper.isEmpty(operation) || "save".equals(operation) || "next".equals(operation))
		{
			send(form);
			return mapping.findForward(FORWARD_EDIT_AND_VIEW_DOCUMENT);
		}
		return null;
	}
}
