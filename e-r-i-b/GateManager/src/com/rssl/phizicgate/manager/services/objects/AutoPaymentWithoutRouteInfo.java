package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizicgate.manager.services.IDHelper;

/**
 * @author khudyakov
 * @ created 28.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class AutoPaymentWithoutRouteInfo extends AutoPaymentBase implements RouteInfoReturner
{
	public AutoPaymentWithoutRouteInfo(AutoPayment payment)
	{
		super(payment);
	}

	/**
	 * @return внешний идентификатор поручения
	 */
	public String getExternalId()
	{
		return IDHelper.restoreOriginalId(delegate.getExternalId());
	}

	public String getRouteInfo()
	{
		return IDHelper.restoreRouteInfo(delegate.getExternalId());
	}

	public String getId()
	{
		return getExternalId();
	}
}
