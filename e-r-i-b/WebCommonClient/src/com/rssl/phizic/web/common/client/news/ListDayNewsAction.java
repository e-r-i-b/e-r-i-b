package com.rssl.phizic.web.common.client.news;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.*;

/**
 * @author lukina
 * @ created 06.06.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListDayNewsAction extends ListNewsAction
{
	private static final String PERIOD_TYPE = "period";

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListDayNewsForm.FORM;
	}

	protected Map<String, Object> getDefaultFilterParameters(ListFormBase form, ListEntitiesOperation operation)
	{

		Map<String, Object> filterParam = new HashMap<String, Object>();
		filterParam.put("typeDate", PERIOD_TYPE);
		Calendar fromDate = DateHelper.getCurrentDate();
		filterParam.put("fromDate", fromDate.getTime());
		return filterParam;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		fillCommonQuery(query, filterParams);
		Date fromDate = (Date)filterParams.get("fromDate");

		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", DateHelper.add(fromDate, 0, 0, 1));
		query.setParameter("search", filterParams.get("search"));
		query.setParameter("important", filterParams.get("important") != null ? "HIGH" : null);
	}
}
