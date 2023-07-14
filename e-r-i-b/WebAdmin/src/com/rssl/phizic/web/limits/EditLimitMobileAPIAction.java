package com.rssl.phizic.web.limits;

import com.rssl.phizic.common.types.limits.ChannelType;

/**
 * Экшен редактирования кумулятивного лимита (мобильное АПИ)
 *
 * @author khudyakov
 * @ created 25.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditLimitMobileAPIAction extends EditLimitAction
{
	private static final String CHANNEL_TYPE = "mobile";

	protected ChannelType getLimitChannelType()
	{
		return ChannelType.MOBILE_API;
	}

	protected String getChannelType()
	{
		return CHANNEL_TYPE;
	}
}
