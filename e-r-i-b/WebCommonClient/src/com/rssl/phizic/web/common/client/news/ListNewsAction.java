package com.rssl.phizic.web.common.client.news;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.news.ListNewsOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.*;

/**
 * User: Zhuravleva
 * Date: 24.12.2006
 * Time: 17:24:35
 */
public class ListNewsAction  extends ListActionBase
{
	private static final String PERIOD_TYPE_WEEK = "week";
	private static final String PERIOD_TYPE_MONTH = "month";

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListNewsOperation.class, "ViewNewsManagment");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListNewsForm.FORM;
	}

	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "clientList";
	}

	// устанавливаем общие параметры запроса
	protected void fillCommonQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		Region region = PersonContext.getPersonDataProvider().getPersonData().getCurrentRegion();
		String departmentTB = region == null ? null : region.getCodeTB();
		query.setParameter("departmentTB", departmentTB);
		query.setParameter("type", "MAIN_PAGE");
	}

	/**
	 * Устанавливаем значения фильтра по умолчанию
	 */
	protected Map<String, Object> getDefaultFilterParameters(ListFormBase form, ListEntitiesOperation operation)
	{

		Map<String, Object> filterParam = new HashMap<String, Object>();
		filterParam.put("typeDate", PERIOD_TYPE_MONTH);
		
		Calendar toDate   = new GregorianCalendar();
		Calendar fromDate = DateHelper.getPreviousMonth(toDate);

		filterParam.put("fromDate", fromDate.getTime());
		filterParam.put("toDate",   toDate.getTime());

		return filterParam;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		fillCommonQuery(query, filterParams);
		String periodType = (String)filterParams.get("typeDate");
		
		if(PERIOD_TYPE_WEEK.equals(periodType) || PERIOD_TYPE_MONTH.equals(periodType))
		{
			filterParams.put("toDate", new GregorianCalendar().getTime());
			Calendar startDate = new GregorianCalendar();
			if (PERIOD_TYPE_WEEK.equals(periodType))
				startDate.add(Calendar.WEEK_OF_MONTH, -1);
			else
				startDate.add(Calendar.MONTH, -1);
			filterParams.put("fromDate", startDate.getTime());
		}

		query.setParameter("fromDate", filterParams.get("fromDate"));
		Date toDate = (Date)filterParams.get("toDate");
		if (toDate != null)
		{
			// прибавляем 1 день, чтобы учитывались письма, отправленные после 00:00 выбранной toDate даты.
			// в связи с этим, в hql установлено     mail.CREATION_DATE < :extra_toDate
			toDate = DateHelper.add(toDate, 0, 0, 1);
		}
		query.setParameter("toDate", toDate);
		query.setParameter("search", filterParams.get("search"));
		String important = null;
		if (filterParams.get("important") != null) important = "HIGH";
		query.setParameter("important", important);
	}

}
