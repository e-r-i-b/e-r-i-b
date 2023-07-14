package com.rssl.phizic.web.common.mobile.longoffers;

import com.rssl.phizic.web.common.client.longoffers.ShowLongOfferInfoAction;
import com.rssl.phizic.web.common.client.longoffers.ShowLongOfferInfoForm;
import com.rssl.phizic.operations.longoffers.GetLongOfferInfoOperation;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Dorzhinov
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class ShowLongOfferInfoMobileAction extends ShowLongOfferInfoAction
{
	protected ActionForward doFilter(ActionMapping mapping, ShowLongOfferInfoForm frm, GetLongOfferInfoOperation operation) throws Exception
	{
		ActionForward forward = super.doFilter(mapping, frm, operation);
		ShowLongOfferInfoMobileForm form = (ShowLongOfferInfoMobileForm) frm;
		form.setPayerResourceLink(operation.getPayerResource());
		return forward;
	}
}
