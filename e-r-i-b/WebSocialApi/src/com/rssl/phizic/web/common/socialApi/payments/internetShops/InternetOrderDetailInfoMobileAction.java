package com.rssl.phizic.web.common.socialApi.payments.internetShops;

import com.rssl.phizic.web.common.client.payments.internetShops.AsyncOrderDetailInfoAction;

/**
 * Детальная информация по заказу в интернет-магазине
 * @author Dorzhinov
 * @ created 13.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class InternetOrderDetailInfoMobileAction extends AsyncOrderDetailInfoAction
{
	protected boolean isAjax()
	{
		return false;
	}
}
