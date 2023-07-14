package com.rssl.phizic.web.dictionaries.jbt;

import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.jbt.UnloadingState;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.operations.dictionaries.jbt.UnloadJBTOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.actions.UnloadOperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessages;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akrenev
 * @ created 22.01.2010
 * @ $Author$
 * @ $Revision$
 */
public class UnloadJBTAction extends UnloadOperationalActionBase<HashMap<String, StringBuilder>>
{
	private static final String ENCODING = "Cp866";

	public Pair<String, HashMap<String, StringBuilder>> createData(ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		UnloadJBTForm frm = (UnloadJBTForm) form;
		UnloadJBTOperation operation = createOperation(UnloadJBTOperation.class, "UnloadJBTManagement");
		MapValuesSource mapValuesSource = new MapValuesSource(frm.getFields());
		FormProcessor<ActionMessages, ?> formProcessor = createFormProcessor(mapValuesSource, UnloadJBTForm.UNLOAD_JBT_FORM);
		if (formProcessor.process())
		{
			Calendar unloadPeriodDateFrom = createCalendar(frm.getField("unloadPeriodDateFrom"),
														   frm.getField("unloadPeriodTimeFrom"));
			if (unloadPeriodDateFrom!=null)
			{
				unloadPeriodDateFrom.set(Calendar.MILLISECOND, 0);
			}
			Calendar unloadPeriodDateTo   = createCalendar(frm.getField("unloadPeriodDateTo"),
														   frm.getField("unloadPeriodTimeTo"));
			if (unloadPeriodDateTo  !=null)
			{
				unloadPeriodDateTo.set(Calendar.MILLISECOND, 999);
			}
			operation.initialize(frm.getSelectedIds(), UnloadingState.valueOf((String) frm.getField("state")), (String) frm.getField("billingId"), unloadPeriodDateFrom, unloadPeriodDateTo);
			return new Pair<String, HashMap<String, StringBuilder>>(operation.getArchiveName(), operation.getUnloadedData());
		}
		else
		{
			saveErrors(request, formProcessor.getErrors());
		}
		return null;
	}

	private Calendar createCalendar(Object date, Object time) throws BusinessException
	{
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

}
