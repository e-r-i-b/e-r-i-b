package com.rssl.phizic.test.web.mobile.payments;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Типичный платеж: --init--> [initialData] --save/next--> [document]
 * @author Dorzhinov
 * @ created 30.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class TestMobilePaymentActionBase extends TestMobileDocumentAction
{
	protected static final String FORWARD_INIT = "Init";
	protected static final String FORWARD_EDIT = "Edit";
	protected static final String FORWARD_FIRST_STEP = "FirstStep";
	protected static final String FORWARD_SECOND_STEP = "SecondStep";

	public ActionForward execute(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestMobileDocumentForm form = (TestMobileDocumentForm) frm;
		String operation = form.getOperation();

		if (StringHelper.isEmpty(operation))
			return mapping.findForward(FORWARD_INIT);

		if ("init".equals(operation))
			return submitInit(mapping, form);

		if ("next".equals(operation) || "save".equals(operation))
			return submitSave(mapping, form);

		if ("edit".equals(operation))
		{
			return submitEdit(mapping, form);
		}

		return null;
	}

	protected ActionForward submitEdit(ActionMapping mapping, TestMobileDocumentForm form)
	{
		ActionForward forwardEdit = mapping.findForward(FORWARD_EDIT);
		if (forwardEdit == null)
		{
			return mapping.findForward(FORWARD_SECOND_STEP);
		}

		UrlBuilder builder = new UrlBuilder();
		builder.setUrl(forwardEdit.getPath())
			   .addParameter("id",               form.getId())
			   .addParameter("operation",        form.getOperation())
			   .addParameter("transactionToken", form.getTransactionToken());

		form.setAddress(builder.getUrl());
		if (send(form) == 0)
		{
			if (form.getDocument() != null)
			{
				return mapping.findForward(FORWARD_SECOND_STEP);
			}
		}

		return mapping.findForward(FORWARD_FIRST_STEP);
	}

	protected ActionForward submitInit(ActionMapping mapping, TestMobileDocumentForm form)
	{
		if (send(form) == 0)
			return mapping.findForward(FORWARD_FIRST_STEP);
		return mapping.findForward(FORWARD_INIT);
	}

	protected ActionForward submitSave(ActionMapping mapping, TestMobileDocumentForm form)
	{
		send(form);
		if (form.getDocument() != null)
			return mapping.findForward(FORWARD_SECOND_STEP);
		return mapping.findForward(FORWARD_FIRST_STEP);
	}
}
