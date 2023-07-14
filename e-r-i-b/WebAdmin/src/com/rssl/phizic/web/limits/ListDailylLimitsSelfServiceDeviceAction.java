package com.rssl.phizic.web.limits;

import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.business.limits.LimitType;
import com.rssl.phizic.common.types.limits.ChannelType;

import java.util.Map;

/**
 * User: Balovtsev
 * Date: 18.10.2012
 * Time: 18:28:13
 */
public class ListDailylLimitsSelfServiceDeviceAction extends ListDailyLimitsAction
{
	@Override
	protected ChannelType getChannelType()
	{
		return ChannelType.SELF_SERVICE_DEVICE;
	}
}
