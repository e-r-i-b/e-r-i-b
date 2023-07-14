package com.rssl.phizic.web.limits;

import com.rssl.phizic.common.types.limits.ChannelType;

/**
 * Экшен редактирования суточного кумулятивного лимита (социальное АПИ)
 *
 * @author sergunin
 * @ created 16.07.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditDailylLimitsSocialAPIAction extends EditDailyLimitAction
{
	private static final String CHANNEL_TYPE = "social";

	protected ChannelType getLimitChannelType()
	{
		return ChannelType.SOCIAL_API;
	}

	protected String getChannelType()
	{
		return CHANNEL_TYPE;
	}
}
