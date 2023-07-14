package com.rssl.phizic.web.limits;

import com.rssl.phizic.common.types.limits.ChannelType;

/**
 * Редактирование кумулятивных лимитов ЕРМБ канала
 *
 * @author khudyakov
 * @ created 23.10.13
 * @ $Author$
 * @ $Revision$
 */
public class EditDailylLimitsERMBAction extends EditDailyLimitAction
{
	private static final String CHANNEL_TYPE = "ermb";

	@Override
	protected ChannelType getLimitChannelType()
	{
		return ChannelType.ERMB_SMS;
	}

	@Override
	protected String getChannelType()
	{
		return CHANNEL_TYPE;
	}
}
