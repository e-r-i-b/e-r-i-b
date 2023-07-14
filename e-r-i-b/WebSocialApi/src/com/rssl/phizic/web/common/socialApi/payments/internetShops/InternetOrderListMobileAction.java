package com.rssl.phizic.web.common.socialApi.payments.internetShops;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.processing.NullFieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.FilterPeriodHelper;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.client.payments.internetShops.InternetOrderListAction;
import static com.rssl.phizic.web.common.client.payments.internetShops.InternetOrderListForm.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Список заказов интернет-магазинов
 * @author Dorzhinov
 * @ created 10.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class InternetOrderListMobileAction extends InternetOrderListAction
{
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return InternetOrderListMobileForm.FILTER_FORM;
	}

	protected FieldValuesSource getFilterValuesSource(ListFormBase form)
	{
		InternetOrderListMobileForm frm = (InternetOrderListMobileForm) form;

		String dateFrom = frm.getDateFrom();
		String dateTo = frm.getDateTo();
		String state = frm.getState();
		String amountFrom = frm.getAmountFrom();
		String amountTo = frm.getAmountTo();
		String currency = frm.getCurrency();
		if (StringHelper.isEmpty(dateFrom) && StringHelper.isEmpty(dateTo) && StringHelper.isEmpty(state)
				&& StringHelper.isEmpty(amountFrom) && StringHelper.isEmpty(amountTo) && StringHelper.isEmpty(currency))
			return new NullFieldValuesSource();

		DateFormat dateFormat = new SimpleDateFormat(InternetOrderListMobileForm.DATE_FORMAT);
		//формируем поля фильтрации для валидации
		Map<String, Object> filter = new HashMap<String, Object>();
        if (StringHelper.isEmpty(dateFrom) && StringHelper.isEmpty(dateTo))
        {
            Calendar monthBefore = Calendar.getInstance();
            monthBefore.add(Calendar.MONTH, -1);
            filter.put(FROM_DATE, dateFormat.format(monthBefore.getTime()));

            Calendar today = Calendar.getInstance();
            today.add(Calendar.DAY_OF_MONTH, 1);
            filter.put(TO_DATE, dateFormat.format(today.getTime()));

            filter.put(TYPE_DATE, FilterPeriodHelper.PERIOD_TYPE_MONTH);
        }
        else
        {
            filter.put(FROM_DATE, dateFrom);
            filter.put(TO_DATE, dateTo);
            filter.put(TYPE_DATE, FilterPeriodHelper.PERIOD_TYPE_CUSTOM);
        }
		filter.put(FROM_AMOUNT, amountFrom);
		filter.put(TO_AMOUNT, amountTo);
		filter.put(CURRENCY, currency);
		filter.put(STATE_CODE, state);
		return new MapValuesSource(filter);
	}
}
