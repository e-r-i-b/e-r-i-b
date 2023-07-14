package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.basket.invoice.Invoice;

/**
 * @author osminin
 * @ created 10.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Ограничение на принадлежность инвойса клиенту
 */
public class OwnInvoiceRestriction implements InvoiceRestriction
{
	public boolean accept(Invoice invoice)
	{
		CommonLogin login = AuthModule.getAuthModule().getPrincipal().getLogin();
		return invoice.getLoginId() == login.getId();
	}
}
