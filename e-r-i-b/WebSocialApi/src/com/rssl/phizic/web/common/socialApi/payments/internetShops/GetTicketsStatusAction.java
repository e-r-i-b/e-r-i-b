package com.rssl.phizic.web.common.socialApi.payments.internetShops;

import com.rssl.phizic.operations.payment.GetPaymentStateOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.w3c.dom.Document;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Проверка статуса выпуска билетов
 * @author Dorzhinov
 * @ created 25.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class GetTicketsStatusAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		GetPaymentStateOperation operation = createOperation(GetPaymentStateOperation.class, "AirlineReservationPayment");
		GetTicketsStatusForm frm = (GetTicketsStatusForm) form;

		operation.initialize(frm.getId());
		String ticketsInfo = operation.getTicketsInfo();
		if (StringHelper.isEmpty(ticketsInfo))
			frm.setTicketsStatus(null);
		else
		{
			Document document = XmlHelper.parse(ticketsInfo);
			frm.setTicketsStatus(XmlHelper.getSimpleElementValue(document.getDocumentElement(), "TicketsStatus"));
		}
		return mapping.findForward(FORWARD_START);
	}
}
