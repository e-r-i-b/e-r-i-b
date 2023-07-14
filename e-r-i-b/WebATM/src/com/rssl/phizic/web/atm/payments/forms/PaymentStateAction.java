package com.rssl.phizic.web.atm.payments.forms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.payment.GetPaymentStateOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Запрос статуса платежа
 * @author Jatsky
 * @ created 24.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class PaymentStateAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PaymentStateForm frm = (PaymentStateForm) form;
		Long documentId = frm.getId();

		if (documentId == null)
			throw new BusinessException("ИД документа не может быть null");

		GetPaymentStateOperation getPaymentStateOperation = createOperation(GetPaymentStateOperation.class, "PaymentList");
		frm.setState(getPaymentStateOperation.getPaymentStateById(documentId));

		return mapping.findForward("Show");
	}
}
