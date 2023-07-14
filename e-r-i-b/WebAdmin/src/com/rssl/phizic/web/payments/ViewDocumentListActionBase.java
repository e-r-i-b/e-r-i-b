package com.rssl.phizic.web.payments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 31.10.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class ViewDocumentListActionBase extends SaveFilterParameterAction
{
	/**
	 * ћаксимальное количество переменный во фразах in.
	 */
	public static final int MAX_BIND_VARS_FOR_INT_COUNT = 10;

	protected static final String BEGIN_DAY = "00:00:00";  // врем€ начала дн€
	protected static final String END_DAY = "23:59:59"; // врем€ конца дн€

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException
	{
		Object tTime = filterParams.get("toTime")   != null ? filterParams.get("toTime")   : END_DAY;
		Object fTime = filterParams.get("fromTime") != null ? filterParams.get("fromTime") : BEGIN_DAY;

		Calendar tDate = filterParams.get("toDate") != null ? createCalendar(filterParams.get("toDate"), tTime) : Calendar.getInstance();
		Calendar fDate = filterParams.get("fromDate") != null ? createCalendar(filterParams.get("fromDate"), fTime) : DateHelper.getPreviousWeek(tDate);

		if (tDate!=null) tDate.set(Calendar.MILLISECOND, 999);
		if (fDate!=null) fDate.set(Calendar.MILLISECOND, 0);

		query.setParameter("toDate",   tDate);
		query.setParameter("fromDate", fDate);
	}

	protected Map<String,Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
	{
		Map<String,Object> parameters = new HashMap<String,Object>();

		Calendar currentDate = Calendar.getInstance();
		//TODO на врем€, пока нет фильтра
		Calendar beforeWeek = Calendar.getInstance();
		beforeWeek.add(Calendar.DAY_OF_MONTH,-7);
		parameters.put("fromDate", String.format("%1$td.%1$tm.%1$tY", beforeWeek));
		parameters.put("fromTime", BEGIN_DAY);
		parameters.put("toDate", String.format("%1$td.%1$tm.%1$tY", currentDate));
		parameters.put("toTime", END_DAY);

		return parameters;
	}

	protected Calendar createCalendar(Object date, Object time) throws BusinessException
	{
		if (date == null && time == null)
			return null;

		try
		{
			//т.к. из базы беретс€ String, а с формы Date
			Calendar dateCalendar = DateHelper.toCalendar((Date) (date instanceof String ? DateHelper.parseDate((String) date) : date));
			Calendar timeCalendar = DateHelper.toCalendar((Date) (time instanceof String ? DateHelper.parseTime((String) time) : time));
			dateCalendar.set(Calendar.HOUR_OF_DAY, timeCalendar.get(Calendar.HOUR_OF_DAY));
			dateCalendar.set(Calendar.MINUTE, timeCalendar.get(Calendar.MINUTE));
			dateCalendar.set(Calendar.SECOND, timeCalendar.get(Calendar.SECOND));
			return dateCalendar;
		}
		catch (ParseException e)
		{
			return Calendar.getInstance();
		}
	}
}
