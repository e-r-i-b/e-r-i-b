package com.rssl.phizic.web.client.basket.searchProviders;

import com.rssl.phizic.web.actions.payments.IndexAction;
import com.rssl.phizic.web.actions.payments.IndexForm;

/**
 * @author tisov
 * @ created 10.07.14
 * @ $Author$
 * @ $Revision$
 */

public class InitialListServicesAction extends IndexAction
{
	protected boolean needFillInvoices()
	{
		return false;
	}

	protected String getChanel()
	{
		return "WEB";
	}

	protected String[] getFunctionality(IndexForm frm)
	{
		return new String[]{"BASKET"};
	}

	protected String getTopLevelServicesQueryName(IndexForm frm)
	{
		return "toplist.available";
	}
}
