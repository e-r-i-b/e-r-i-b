package com.rssl.phizic.business.payments;

import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.common.types.ConfirmStrategyType;

/**
 * @author basharin
 * @ created 08.05.15
 * @ $Author$
 * @ $Revision$
 */

public class WriteConfirmChannelToMonitoringLogHandler extends WriteToMonitoringLogHandlerBase
{
	@Override
	protected String getStateCode(AbstractAccountsTransfer document)
	{
		ConfirmStrategyType confirmStrategyType = document.getConfirmStrategyType();
		return confirmStrategyType == null ? ConfirmStrategyType.none.name() : confirmStrategyType.name();
	}
}
