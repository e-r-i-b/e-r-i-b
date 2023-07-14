package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.documents.DocumentHelper;

/**
 * Фильтр доступности операций со счетов для физ.лиц в данном подразделении.
 *
 * @author bogdanov
 * @ created 18.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class AllowedExternalPhizAccountFilter implements AccountFilter
{
	public boolean accept(AccountLink accountLink)
	{
		return DocumentHelper.isExternalPhizAccountPaymentsAllowed();
	}
}
