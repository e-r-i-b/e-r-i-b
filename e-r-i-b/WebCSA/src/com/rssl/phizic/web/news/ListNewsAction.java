package com.rssl.phizic.web.news;

import com.rssl.auth.csa.front.business.news.News;
import com.rssl.auth.csa.front.operations.news.ListNewsOperation;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.LookupDispatchAction;
import com.rssl.phizic.web.component.DatePeriodFilter;
import com.rssl.phizic.web.struts.forms.FormHelper;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.commons.lang.BooleanUtils;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author basharin
 * @ created 05.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListNewsAction extends LookupDispatchAction
{
	protected static final String START_FORWARD = "start";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.filter", "filter");
		return map;
	}

	protected Map<String, Object> getDefaultFilterParameters(ListNewsForm form)
	{
		Map<String, Object> filterParam = new HashMap<String, Object>();
		filterParam.put("typeDate", "period");

		Calendar toDate   = Calendar.getInstance();
		Calendar fromDate = DateHelper.getPreviousMonth(toDate);

		filterParam.put("fromDate", fromDate.getTime());
		filterParam.put("toDate",   toDate.getTime());
		return filterParam;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListNewsForm frm = (ListNewsForm) form;
		ListNewsOperation listNewsOperation = new ListNewsOperation();

		doFilter(getDefaultFilterParameters(frm), listNewsOperation, frm);		

		return mapping.findForward(START_FORWARD);
	}

	protected FormProcessor<ActionMessages, ?> createFormProcessor(FieldValuesSource valuesSource, Form form)
	{
		return FormHelper.newInstance(valuesSource, form);
	}

	protected FormProcessor<ActionMessages, ?> getFormProcessor(ListNewsForm frm) throws Exception
	{
		Map<String, Object> fields = frm.getFilters();
		FieldValuesSource valuesSource = new MapValuesSource(fields);
		Form form = ListNewsForm.NEWS_FORM;

		return createFormProcessor(valuesSource, form);
	}


	protected void doFilter(Map<String, Object> filterParams, ListNewsOperation operation, ListNewsForm frm) throws Exception
	{
		Map<String, Object> parameters = new DatePeriodFilter(filterParams).normalize().getParameters();


		Calendar tDate = DateHelper.toCalendar((Date)parameters.get("toDate"));
		tDate = DateHelper.addDays(tDate, 1);
		Calendar fDate = DateHelper.toCalendar((Date)parameters.get("fromDate"));


		operation.initialize(fDate,tDate);
		if (parameters.get("search") == null)
			operation.setSeach("");
		else
			operation.setSeach((String)parameters.get("search"));		
		if (BooleanUtils.toBoolean((Boolean)parameters.get("important")))
			operation.setImportant("HIGH");
		else
			operation.setImportant("");
        		
		if(frm.getPaginationSize() != null)
		{
			operation.setPaginationSize(frm.getPaginationSize());
		}

		if(frm.getPaginationOffset() != null)
		{
			operation.setPaginationOffset(frm.getPaginationOffset());
		}

		try
		{
			frm.setData(operation.getNews());
			frm.setFilters(filterParams);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
	}


	public final ActionForward filter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListNewsForm frm = (ListNewsForm) form;
		ListNewsOperation operation = new ListNewsOperation();

		FormProcessor<ActionMessages, ?> processor = getFormProcessor(frm);
		if (processor.process())
		{
			doFilter(processor.getResult(), operation, frm);
		}
		else
		{
			saveErrors(request, processor.getErrors());
			frm.setData(Collections.<News>emptyList());
		}				

		return mapping.findForward(START_FORWARD);
	}

}
