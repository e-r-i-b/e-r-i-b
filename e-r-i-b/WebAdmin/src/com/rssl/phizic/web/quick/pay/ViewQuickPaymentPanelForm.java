package com.rssl.phizic.web.quick.pay;

import com.rssl.phizic.business.quick.pay.QuickPaymentPanel;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * Форма просмотра панели быстрой оплаты
 * @author komarov
 * @ created 13.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class ViewQuickPaymentPanelForm extends EditFormBase
{
	private QuickPaymentPanel panel;

	public QuickPaymentPanel getPanel()
	{
		return panel;
	}

	public void setPanel(QuickPaymentPanel panel)
	{
		this.panel = panel;
	}
}