package com.rssl.phizic.web.ima;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.ima.RateOfExchange;
import com.rssl.phizic.operations.ima.ShowIMAccountRateOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

/**
 * @author rydvanskiy
 * @ created 30.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class ShowIMAccountRateAction extends SaveFilterParameterAction
{
	private static final String DATE_FORMAT_STRING = "%1$td.%1$tm.%1$tY";
	private static final String TIME_FORMAT_STRING = "%1$tH:%1$tM:%1$tS";
	
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		String parameter = getCurrentMapping() != null ? getCurrentMapping().getParameter() : "Show";
		return createOperation(parameter+"IMAccountRateOperation", "IMAccountRateService");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ShowIMAccountsRateForm.FILTER_FORM;
	}

	protected FieldValuesSource getFilterValuesSource(ListFormBase frm)
	{
		ShowIMAccountsRateForm form = (ShowIMAccountsRateForm)frm;
		//��������� ���� ���������� ��� ���������
	    Map<String,Object> filter = new HashMap<String,Object>();
		if (StringHelper.isNotEmpty(form.getRateActualDate()))
	        filter.put(ShowIMAccountsRateForm.FIELD_RATE_ACTUAL_DATE, form.getRateActualDate());
		if (StringHelper.isNotEmpty(form.getRateActualTime()))
	        filter.put(ShowIMAccountsRateForm.FIELD_DATE_ACTUAL_TIME, form.getRateActualTime());
		FieldValuesSource fieldValuesSource = new MapValuesSource(filter);

		return fieldValuesSource.isEmpty() ? super.getFilterValuesSource(frm) : fieldValuesSource;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		ShowIMAccountRateOperation oper = (ShowIMAccountRateOperation) operation;
		ShowIMAccountsRateForm form = (ShowIMAccountsRateForm) frm;
		oper.initialize(form.getId());

		Calendar actualDate = Calendar.getInstance();
		if (CollectionUtils.isNotEmpty(filterParams.values()))
		{
			Object fTime = filterParams.get(ShowIMAccountsRateForm.FIELD_DATE_ACTUAL_TIME) != null ?
					filterParams.get(ShowIMAccountsRateForm.FIELD_DATE_ACTUAL_TIME) : DateHelper.BEGIN_DAY_TIME;
			actualDate = createCalendar(filterParams.get(ShowIMAccountsRateForm.FIELD_RATE_ACTUAL_DATE), fTime);
			if (actualDate!=null) actualDate.set(Calendar.MILLISECOND,0);
		}
		GroupResult<String, RateOfExchange> rateGroup = oper.getIMARates(null, actualDate);

		//��������� ������ ���� ����
		ActionMessages msgs = new ActionMessages();
		for (Map.Entry<String, IKFLException> entry: rateGroup.getExceptions().entrySet())
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(entry.getValue().getMessage(), false));
		}
		saveMessages(currentRequest(), msgs);
		
		form.setRates(rateGroup.getResults());
		form.setFilters(filterParams);

		form.setRateActualDate(String.format(DATE_FORMAT_STRING, actualDate));
		form.setRateActualTime(String.format(TIME_FORMAT_STRING, actualDate));
	}

	private Calendar createCalendar(Object date, Object time) throws BusinessException
	{
		if (date==null)	return null;
		Calendar dateCalendar;
		Calendar timeCalendar;
		try
		{
			dateCalendar = DateHelper.toCalendar((Date) (date instanceof String ? DateHelper.parseDate((String) date) : date));
			timeCalendar = DateHelper.toCalendar((Date) (time instanceof String ? DateHelper.parseTime((String) time) : time));
		}
		catch (ParseException e)
		{
			throw new BusinessException(e);
		}

		if (time != null)
		{
			dateCalendar.set(Calendar.HOUR_OF_DAY,timeCalendar.get(Calendar.HOUR_OF_DAY));
			dateCalendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
			dateCalendar.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND));
		}
		return dateCalendar;
	}

	protected Map<String, Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation) throws BusinessException, BusinessLogicException
	{

		Map<String, Object> filterParams = super.getDefaultFilterParameters(frm, operation);
		Calendar rateActualDate = getDefaultFilterCurDate();
		filterParams.put(ShowIMAccountsRateForm.FIELD_RATE_ACTUAL_DATE, String.format(DATE_FORMAT_STRING, rateActualDate));
		filterParams.put(ShowIMAccountsRateForm.FIELD_DATE_ACTUAL_TIME, String.format(TIME_FORMAT_STRING, rateActualDate));
		
		return filterParams;
	}

	protected Calendar getDefaultFilterCurDate()
    {
	    Calendar date = Calendar.getInstance();
	    return date;
    }
}
