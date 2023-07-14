package com.rssl.phizic.web.common.client.news;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.news.ListLoginPageNewsOperation;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * @author lukina
 * @ created 11.05.2011
 * @ $Author$
 * @ $Revision$
 */

public class LoginListNewsAction  extends ListNewsAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return new ListLoginPageNewsOperation();
	}

	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "list";
	}

	// устанавливаем общие параметры запроса
	protected void fillCommonQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameterList("type", new String[]{"LOGIN_PAGE"});
	}
}
