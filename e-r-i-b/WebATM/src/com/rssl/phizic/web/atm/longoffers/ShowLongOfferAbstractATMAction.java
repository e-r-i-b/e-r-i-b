package com.rssl.phizic.web.atm.longoffers;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.longoffers.GetLongOfferInfoOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.atm.common.FilterFormBase;
import com.rssl.phizic.web.common.client.longoffers.ShowLongOfferInfoAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action ��� ����������� ������� ������������ ���� LongOffer � ��������� ������
 * @ author Rtischeva
 * @ created 07.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class ShowLongOfferAbstractATMAction extends ShowLongOfferInfoAction
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowLongOfferAbstractATMForm frm = (ShowLongOfferAbstractATMForm) form;
		String from = frm.getFrom();
		String to = frm.getTo();
		if (StringHelper.isNotEmpty(from) || StringHelper.isNotEmpty(to))
			return showScheduleReport(mapping, form, request, response);
		else
			return getDefaultScheduleItems(mapping, form, request, response);
	}

	public ActionForward showScheduleReport(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowLongOfferAbstractATMForm frm = (ShowLongOfferAbstractATMForm) form;

		GetLongOfferInfoOperation operation = createOperation(GetLongOfferInfoOperation.class);
		operation.initialize(frm.getId());

		MapValuesSource valuesSourse = getFilterValues(frm);
		Form showForm = FilterFormBase.FILTER_DATE_FORM;;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(valuesSourse, showForm);
		if (processor.process())
		{
			try
			{
				Map<String, Object> results = processor.getResult();
				Calendar fromDate = DateHelper.toCalendar((Date) results.get(FilterFormBase.FROM_DATE_NAME));
				Calendar toDate = DateHelper.toCalendar((Date) results.get(FilterFormBase.TO_DATE_NAME));
				frm.setScheduleItems(operation.getScheduleReport(fromDate, toDate));
			}
			catch (BusinessLogicException e)
			{
				saveError(request, e);
			}
		}
		else
		{
			saveErrors(request, processor.getErrors());
		}
		return doFilter(mapping, frm, operation);
	}


	private MapValuesSource getFilterValues(ShowLongOfferAbstractATMForm frm)
	{
	    Map<String,Object> filter = new HashMap<String,Object>();
	    filter.put(FilterFormBase.FROM_DATE_NAME, frm.getFrom());
	    filter.put(FilterFormBase.TO_DATE_NAME, frm.getTo());
	    return new MapValuesSource(filter);
	}

}
