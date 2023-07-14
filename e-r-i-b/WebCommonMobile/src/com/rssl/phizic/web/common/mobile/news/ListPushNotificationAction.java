package com.rssl.phizic.web.common.mobile.news;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.news.ListNewsOperation;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * @author Barinov
 * @ created 07.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListPushNotificationAction extends ListNewsAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListNewsOperation.class, "PushNotifications");
	}

	protected void fillCommonQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("departmentTB", null);
		query.setParameter("type", "PUSH");
	}
}
