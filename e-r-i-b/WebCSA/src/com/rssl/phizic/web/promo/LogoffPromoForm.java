package com.rssl.phizic.web.promo;

import com.rssl.phizic.web.common.FilterActionForm;
import com.rssl.auth.csa.front.business.promo.PromoterSession;

/**
 * @ author: Gololobov
 * @ created: 29.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class LogoffPromoForm extends FilterActionForm
{
	private PromoterSession promoSession;

	public PromoterSession getPromoSession()
	{
		return promoSession;
	}

	public void setPromoSession(PromoterSession promoSession)
	{
		this.promoSession = promoSession;
	}
}
