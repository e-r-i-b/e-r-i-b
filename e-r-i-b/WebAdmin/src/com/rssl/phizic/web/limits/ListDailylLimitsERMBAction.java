package com.rssl.phizic.web.limits;

import com.rssl.phizic.business.limits.LimitType;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.dataaccess.query.Query;

import java.util.Map;

/**
 * Список кумулятивных лимитов канала ЕРМБ
 *
 * @author khudyakov
 * @ created 23.10.13
 * @ $Author$
 * @ $Revision$
 */
public class ListDailylLimitsERMBAction extends ListDailyLimitsAction
{
	@Override
	protected ChannelType getChannelType()
	{
		return ChannelType.ERMB_SMS;
	}
}
