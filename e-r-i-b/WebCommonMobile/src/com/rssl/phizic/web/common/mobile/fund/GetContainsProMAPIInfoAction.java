package com.rssl.phizic.web.common.mobile.fund;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.operations.fund.GetContainsProMAPIInfoOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 16.04.15
 * @ $Author$
 * @ $Revision$
 *
 * Экшен получения информации о налии мАпи ПРО версии у контактов
 */
public class GetContainsProMAPIInfoAction extends OperationalActionBase
{
	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		GetContainsProMAPIInfoOperation operation = createOperation(GetContainsProMAPIInfoOperation.class);
		GetContainsProMAPIInfoForm frm = (GetContainsProMAPIInfoForm) form;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new RequestValuesSource(request), GetContainsProMAPIInfoForm.FORM);

		if (processor.process())
		{
			String phonesValue = (String) processor.getResult().get(GetContainsProMAPIInfoForm.PHONES_PARAM);

			operation.initialize(phonesValue);
			operation.execute();

			frm.setInfoMap(operation.getInfoMap());
			frm.setPhoneNumbers(operation.getPhones());
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}

		return mapping.findForward(FORWARD_START);
	}
}
