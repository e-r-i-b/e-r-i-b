package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.documents.DocumentHelper;

/**
 * @author osminin
 * @ created 07.02.2013
 * @ $Author$
 * @ $Revision$
 *
 * Фильтр доступности операций со счетов для юр.лиц в данном подразделении.
 */
public class AllowedExternalJurAccountFilter implements AccountFilter
{

	public boolean accept(AccountLink accountLink)
	{
		return DocumentHelper.isExternalJurAccountPaymentsAllowed();
	}
}
