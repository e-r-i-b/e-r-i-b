package com.rssl.phizic.web.client.longoffers;

import com.rssl.phizic.business.resources.external.LongOfferLink;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.operations.longoffers.GetLongOfferInfoOperation;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author osminin
 * @ created 03.10.2010
 * @ $Author$
 * @ $Revision$
 */
public class PrintLongOfferInfoAction extends OperationalActionBase
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		PrintLongOfferInfoForm frm = (PrintLongOfferInfoForm) form;
		GetLongOfferInfoOperation operation = createOperation(GetLongOfferInfoOperation.class);
		operation.initialize(frm.getId());

		LongOfferLink longOfferLink = operation.getLongOfferLink();
		LongOffer longOffer = longOfferLink.getValue();

		if (!MockHelper.isMockObject(longOffer))
		{
			ExecutionEventType eventType = longOffer.getExecutionEventType();

			if (eventType == ExecutionEventType.ONCE_IN_MONTH || eventType == ExecutionEventType.ONCE_IN_HALFYEAR ||
					eventType == ExecutionEventType.ONCE_IN_QUARTER || eventType == ExecutionEventType.ONCE_IN_YEAR)
				frm.setLongOfferType("Периодически");
			else
				frm.setLongOfferType("По событию");
		}

		frm.setLongOffer(longOffer);
		frm.setLongOfferLink(longOfferLink);
		frm.setCurrentDepartment(operation.getCurrentDepartment());
		frm.setTopLevelDepartment(operation.getTopLevelDepartment());

		return mapping.findForward(FORWARD_SHOW);
	}
}
