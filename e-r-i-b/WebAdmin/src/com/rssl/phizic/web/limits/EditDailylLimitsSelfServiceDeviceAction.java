package com.rssl.phizic.web.limits;

import com.rssl.phizic.common.types.limits.ChannelType;

/**
 * User: Balovtsev
 * Date: 18.10.2012
 * Time: 18:36:23
 */
public class EditDailylLimitsSelfServiceDeviceAction extends EditDailyLimitAction
{
	private static final String CHANNEL_TYPE = "atm";

	protected ChannelType getLimitChannelType()
	{
		return ChannelType.SELF_SERVICE_DEVICE;
	}

	protected String getChannelType()
	{
		return CHANNEL_TYPE;
	}
}
