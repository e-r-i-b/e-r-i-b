package com.rssl.phizic.web.limits;

import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.limits.ChannelType;

import java.util.Map;

/**
 * @author khudyakov
 * @ created 25.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListLimitsMobileAPIAction extends ListLimitsAction
{
	protected void fillQuery(Query query, Map<String, Object> filterParams, Long groupRiskId, String securityType) throws BusinessException, BusinessLogicException
	{
		fillQueryBase(query, filterParams, groupRiskId, securityType);
		query.setParameter("channel", ChannelType.MOBILE_API.name());
	}
}
