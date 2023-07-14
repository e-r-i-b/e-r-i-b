package com.rssl.phizic.messaging.jobs;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.messaging.info.PersonalSubscriptionData;

import java.util.Calendar;

/**
 * @author Evgrafov
 * @ created 23.06.2006
 * @ $Author: gladishev $
 * @ $Revision: 61544 $
 */

public interface DataSupplier
{
	Object getData(final PersonalSubscriptionData data, final Calendar current)
			throws BusinessException, BusinessLogicException;
}
