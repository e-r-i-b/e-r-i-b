package com.rssl.phizic.web.client.ext.sbrf.mobilebank.ermb;

import com.rssl.phizic.operations.ext.sbrf.payment.ListPopularPaymentsOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 @author: Egorovaa
 @ created: 16.10.2012
 @ $Author$
 @ $Revision$
 */
public class SmsHistoryAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String, String>();
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SmsHistoryForm frm = (SmsHistoryForm) form;
		ListPopularPaymentsOperation operation =  createOperation("ListPopularPaymentsOperation", "RurPayJurSB");

		frm.setPayments(operation.getUserSMSPayments());
		return getCurrentMapping().findForward(FORWARD_START);
	}

}
