package com.rssl.phizic.web.limits;

import com.rssl.phizic.common.types.limits.ChannelType;

/**
 * Экшен редактирования кумулятивного лимита (соцаильное АПИ)
 *
 * @author sergunin
 * @ created 16.07.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditLimitSocialAPIAction extends EditLimitAction
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
