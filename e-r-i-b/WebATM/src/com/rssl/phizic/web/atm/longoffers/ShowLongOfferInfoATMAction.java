package com.rssl.phizic.web.atm.longoffers;

import com.rssl.phizic.operations.longoffers.GetLongOfferInfoOperation;
import com.rssl.phizic.web.common.client.longoffers.ShowLongOfferInfoAction;
import com.rssl.phizic.web.common.client.longoffers.ShowLongOfferInfoForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Pankin
 * @ created 29.01.2013
 * @ $Author$
 * @ $Revision$
 */

public class ShowLongOfferInfoATMAction extends ShowLongOfferInfoAction
{
	protected ActionForward doFilter(ActionMapping mapping, ShowLongOfferInfoForm frm, GetLongOfferInfoOperation operation) throws Exception
	{
		ActionForward forward = super.doFilter(mapping, frm, operation);
		ShowLongOfferInfoATMForm form = (ShowLongOfferInfoATMForm) frm;
		form.setPayerResourceLink(operation.getPayerResource());
		return forward;
	}
}
