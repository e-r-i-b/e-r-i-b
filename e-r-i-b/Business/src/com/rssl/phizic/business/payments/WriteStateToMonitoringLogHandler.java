package com.rssl.phizic.business.payments;

import com.rssl.phizic.business.documents.AbstractAccountsTransfer;

/**
 * «апись в лог мониторинга статусов переводов
 * @author basharin
 * @ created 07.05.15
 * @ $Author$
 * @ $Revision$
 */

public class WriteStateToMonitoringLogHandler extends WriteToMonitoringLogHandlerBase
{
	@Override
	protected String getStateCode(AbstractAccountsTransfer document)
	{
		return document.getState().getCode();
	}
}
