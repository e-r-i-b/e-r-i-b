package com.rssl.phizic.web.currency;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.currency.ShowCurrenciesRateOperation;
import com.rssl.phizic.operations.ima.RateOfExchange;
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
 * @ author: Gololobov
 * @ created: 09.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class ShowCurrencyRateAction extends SaveFilterParameterAction
{
	private static final String DATE_FORMAT_STRING = "%1$td.%1$tm.%1$tY";
	private static final String TIME_FORMAT_STRING = "%1$tH:%1$tM:%1$tS";
	
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ShowCurrenciesRateOperation.class, "CurreciesRateService");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ShowCurrencyRateForm.FILTER_FORM;
	}

	protected FieldValuesSource getFilterValuesSource(ListFormBase frm)
	{
		ShowCurrencyRateForm form = (ShowCurrencyRateForm)frm;
		//формируем поля фильтрации для валидации
	    Map<String,Object> filter = new HashMap<String,Object>();
		if (StringHelper.isNotEmpty(form.getRateActualDate()))
	        filter.put(ShowCurrencyRateForm.FIELD_RATE_ACTUAL_DATE, form.getRateActualDate());
		if (StringHelper.isNotEmpty(form.getRateActualTime()))
	        filter.put(ShowCurrencyRateForm.FIELD_DATE_ACTUAL_TIME, form.getRateActualTime());
		FieldValuesSource fieldValuesSource = new MapValuesSource(filter);

		return fieldValuesSource.isEmpty() ? super.getFilterValuesSource(frm) : fieldValuesSource;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		ShowCurrenciesRateOperation oper = (ShowCurrenciesRateOperation) operation;
		ShowCurrencyRateForm form = (ShowCurrencyRateForm) frm;
		oper.initialize(form.getId());

		//Актуальная дата для курсов валют
		Calendar actualDate = Calendar.getInstance();
		if (CollectionUtils.isNotEmpty(filterParams.values()))
		{
			Object fTime = filterParams.get(ShowCurrencyRateForm.FIELD_DATE_ACTUAL_TIME) != null ?
					filterParams.get(ShowCurrencyRateForm.FIELD_DATE_ACTUAL_TIME) : DateHelper.BEGIN_DAY_TIME;
			actualDate = createCalendar(filterParams.get(ShowCurrencyRateForm.FIELD_RATE_ACTUAL_DATE), fTime);
			if (actualDate!=null) actualDate.set(Calendar.MILLISECOND,0);
		}

		//Курсы покупки/продажи валют (не металлических) актуальных на дату "actualDate" в разрезе ратифных планов
		GroupResult<Currency, Map<String, RateOfExchange>> ratesGroup = oper.getAllCurrenciesRatesByTarifPlan(actualDate);

		//сохраняем ошибки если есть
		ActionMessages msgs = new ActionMessages();
		for (Map.Entry<Currency, IKFLException> entry: ratesGroup.getExceptions().entrySet())
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(entry.getValue().getMessage(), false));
		}
		saveMessages(currentRequest(), msgs);
		form.setRates(ratesGroup.getResults());
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
		filterParams.put(ShowCurrencyRateForm.FIELD_RATE_ACTUAL_DATE, String.format(DATE_FORMAT_STRING, rateActualDate));
		filterParams.put(ShowCurrencyRateForm.FIELD_DATE_ACTUAL_TIME, String.format(TIME_FORMAT_STRING, rateActualDate));

		return filterParams;
	}

	protected Calendar getDefaultFilterCurDate()
    {
	    Calendar date = Calendar.getInstance();
	    return date;
    }
}
