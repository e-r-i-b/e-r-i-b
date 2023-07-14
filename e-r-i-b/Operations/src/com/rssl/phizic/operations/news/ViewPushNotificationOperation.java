package com.rssl.phizic.operations.news;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;

/**
 * @author Barinov
 * @ created 07.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ViewPushNotificationOperation extends ViewNewsOperation implements ViewEntityOperation
{
	public void initialize(Long newsId) throws BusinessException, BusinessLogicException
	{
		super.initialize(newsId);
		if(!"PUSH".equals(getEntity().getType()))
			throw new BusinessException("Событие с id " + newsId + " не является push notification");
	}
}
