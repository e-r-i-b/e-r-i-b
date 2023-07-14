package com.rssl.phizic.web.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gulov
 * @ created 07.04.15
 * @ $Author$
 * @ $Revision$
 */
public class ConfimLoanClaimAction extends ConfirmEmployeePaymentAction
{
	@Override
	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmFormPaymentOperation operation = getOperation(request);
		ConfirmEmployeePaymentByFormForm frm = (ConfirmEmployeePaymentByFormForm) form;
		return doConfirm(mapping, operation, frm, request, response);
	}

	protected ActionForward createNextStageDocumentForward(ConfirmFormPaymentOperation operation, boolean backToEdit) throws BusinessException
	{
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl("/claim/payments/view.do");
		urlBuilder.addParameter("id", StringHelper.getEmptyIfNull(operation.getDocument().getId()));
		return new ActionForward(urlBuilder.toString(), true);
	}
}
