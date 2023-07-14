package com.rssl.phizic.web.client.quick.pay;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author komarov
 * @ created 20.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class QuickPaymentPanelLogForm  extends ActionFormBase
{
	
	private Long quickPaymentPanelBlokId;

	public Long getQuickPaymentPanelBlokId()
	{
		return quickPaymentPanelBlokId;
	}

	public void setQuickPaymentPanelBlokId(Long quickPaymentPanelBlokId)
	{
		this.quickPaymentPanelBlokId = quickPaymentPanelBlokId;
	}
}