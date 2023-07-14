package com.rssl.phizic.web.common.mobile.news;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.mobile.common.FilterFormBase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Rydvanskiy
 * @ created 25.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class ListNewsAction extends com.rssl.phizic.web.common.client.news.ListNewsAction
{
	protected FieldValuesSource getFilterValuesSource(ListFormBase form)
	{
		ListNewsForm frm = (ListNewsForm)form;
		//формируем поля фильтрации для валидации
	    Map<String,Object> filter = new HashMap<String,Object>();
	    filter.put(FilterFormBase.FROM_DATE_NAME, frm.getFrom());
	    filter.put(FilterFormBase.TO_DATE_NAME, frm.getTo());
	    return new MapValuesSource(filter);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FilterFormBase.FILTER_DATE_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		fillCommonQuery(query, filterParams);

		Date toDate = (Date)filterParams.get(FilterFormBase.TO_DATE_NAME);
		if (toDate != null)
		{
			toDate = DateHelper.add(toDate, 0, 0, 1);
		}
		query.setParameter("fromDate", filterParams.get(FilterFormBase.FROM_DATE_NAME));
		query.setParameter("toDate", toDate);

		query.setParameter("search", null);
		query.setParameter("important", null);
	}

	protected void forwardFilterFail(ListFormBase form, ListEntitiesOperation operation) throws Exception
	{
		//если произошла ошибка (в частности, ошибка валидации), то список новостей должен быть пуст
	}
}
