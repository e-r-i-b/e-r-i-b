package com.rssl.phizic.web.limits;

import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.limits.ChannelType;

import java.util.Map;

/**
 * User: Balovtsev
 * Date: 18.10.2012
 * Time: 18:25:42
 */
public class ListLimitsSelfServiceDeviceAction extends ListLimitsAction
{
	protected void fillQuery(Query query, Map<String, Object> filterParams, Long groupRiskId, String securityType) throws BusinessException, BusinessLogicException
	{
		fillQueryBase(query, filterParams, groupRiskId, securityType);
		query.setParameter("channel", ChannelType.SELF_SERVICE_DEVICE.name());
	}
}
