package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author khudyakov
 * @ created 28.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class AutoPaymentWithRouteInfo extends AutoPaymentBase
{
	private String routeInfo;

	public AutoPaymentWithRouteInfo(AutoPayment payment, String routeInfo)
	{
		super(payment);
		this.routeInfo = routeInfo;
	}

	/**
	 * @return внешний идентификатор поручения
	 */
	public String getExternalId()
	{
		return IDHelper.storeRouteInfo(delegate.getExternalId(), routeInfo);
	}

	public String getCodeService()
	{
		return IDHelper.storeRouteInfo(delegate.getCodeService(), routeInfo);
	}
}
