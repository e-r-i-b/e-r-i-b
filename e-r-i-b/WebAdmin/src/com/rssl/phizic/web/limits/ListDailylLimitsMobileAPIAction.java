package com.rssl.phizic.web.limits;

import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.business.limits.LimitType;
import com.rssl.phizic.common.types.limits.ChannelType;

import java.util.Map;

/**
 * Экшен для получения списка суточных комулятивных лимитов (мобильное АПИ)
 *
 * @author khudyakov
 * @ created 25.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListDailylLimitsMobileAPIAction extends ListDailyLimitsAction
{
	@Override
	protected ChannelType getChannelType()
	{
		return ChannelType.MOBILE_API;
	}
}
