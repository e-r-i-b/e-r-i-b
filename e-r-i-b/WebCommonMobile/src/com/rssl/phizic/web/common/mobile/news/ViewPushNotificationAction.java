package com.rssl.phizic.web.common.mobile.news;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.news.ViewNewsOperation;
import com.rssl.phizic.operations.news.ViewPushNotificationOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.client.news.ViewNewsAction;

/**
 * @author Barinov
 * @ created 07.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ViewPushNotificationAction extends ViewNewsAction
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ViewNewsOperation operation = createOperation(ViewPushNotificationOperation.class, "PushNotifications");
		operation.initialize(frm.getId());
		return operation;
	}
}
