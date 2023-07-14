package com.rssl.phizic.web.common.mobile.fund;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * @author usachev
 * @ created 05.12.14
 * @ $Author$
 * @ $Revision$
 *
 * Action для получения списка исходящих запросов на сбор средств
 */

public class ListFundOutgoingRequestAction extends ListActionBase
{
	private final static String FROM_DATE = "from_date";

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("ListFundOutgoingRequestOperation");

	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FundListRequestForm.FORM;
	}

	@Override
	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "getByLoginAndDate";
	}

	@Override
	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		Calendar calendar = getDate((Date) filterParams.get(FROM_DATE));
		Long id = PersonContext.getPersonDataProvider().getPersonData().getLogin().getId();
		query.setParameter(FROM_DATE, calendar);
		query.setParameter("login_id", id);
	}

	/**
	 * Возвращает дату, начиная с которой, нужно вернуть исходящие запросы.
	 * @param date Дата
	 * @return Если задан параметр date, то возвращает эту дату. В противном случае, предыдущий месяц.
	 */
	private Calendar getDate(Date date)
	{
		if (date == null)
		{
			return getLifeTimeDate();
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar;
	}

	/**
	 * Возвращает дату месяц назад
	 * @return Дата месяц назад
	 */
	private Calendar getLifeTimeDate()
	{
		Calendar date = new GregorianCalendar();
		date.add(Calendar.DAY_OF_MONTH, -ConfigFactory.getConfig(MobileApiConfig.class).getFundLifeTimeInDays());
		return date;
	}
}
