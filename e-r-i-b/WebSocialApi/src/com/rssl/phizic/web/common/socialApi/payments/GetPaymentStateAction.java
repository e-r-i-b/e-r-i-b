package com.rssl.phizic.web.common.socialApi.payments;

import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.operations.payment.GetPaymentStateOperation;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Проверка статуса документа
 * @author Dorzhinov
 * @ created 25.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class GetPaymentStateAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		GetPaymentStateOperation operation = createOperation(GetPaymentStateOperation.class, "AirlineReservationPayment");
		GetPaymentStateForm frm = (GetPaymentStateForm) form;

		operation.initialize(frm.getId());
		String paymentState = operation.getPaymentState();
		frm.setState("SENT".equals(paymentState)? "UNKNOW": paymentState);
		return mapping.findForward(FORWARD_START);
	}
}
