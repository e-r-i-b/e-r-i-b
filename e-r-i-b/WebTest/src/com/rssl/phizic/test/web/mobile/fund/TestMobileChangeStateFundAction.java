package com.rssl.phizic.test.web.mobile.fund;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.fund.FundResponseState;
import com.rssl.phizic.operations.test.mobile.SendMobileRequestOperation;
import com.rssl.phizic.test.web.mobile.payments.TestMobileDocumentForm;
import com.rssl.phizic.test.web.mobile.payments.TestMobilePaymentActionBase;
import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 23.12.14
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileChangeStateFundAction extends TestMobilePaymentActionBase
{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestMobileChangeStateFundForm frm = (TestMobileChangeStateFundForm) form;
		String operation = frm.getOperation();

		if (StringHelper.isEmpty(operation))
		{
			return mapping.findForward(FORWARD_INIT);
		}
		if ("init".equals(operation))
		{
			if (frm.getState().equals(FundResponseState.SUCCESS.name()))
			{
				return submitInit(mapping, frm);
			}

			return send(mapping, form, request, response);
		}
		if ("next".equals(operation) || "save".equals(operation))
		{
			return submitSave(mapping, frm);
		}
		if ("edit".equals(operation))
		{
			return submitEdit(mapping, frm);
		}

		return null;
	}

	protected ActionForward submitInit(ActionMapping mapping, TestMobileDocumentForm form)
	{
		if (send(form) == 0)
		{
			return mapping.findForward(FORWARD_FIRST_STEP);
		}
		return mapping.findForward(FORWARD_INIT);
	}

	private ActionForward send(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestMobileChangeStateFundForm form = (TestMobileChangeStateFundForm) frm;
		form.setSubmit(true);

		try
		{
			SendMobileRequestOperation operation = new SendMobileRequestOperation();
			updateOperation(operation, form);

			operation.send();

			updateForm(form, operation);
		}
		catch (SystemException e)
		{
			form.setResult("Error \n" + e.getMessage());
		}

		return mapping.findForward(FORWARD_INIT);
	}
}
