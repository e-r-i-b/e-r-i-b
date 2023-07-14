package com.rssl.phizic.web.ext.sbrf.technobreaks;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizgate.ext.sbrf.technobreaks.PeriodicType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.externalsystem.AutoTechnoBreakConfig;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.technobreaks.ListTechnoBreakOperation;
import com.rssl.phizic.operations.ext.sbrf.technobreaks.RemoveTechnoBreakOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author niculichev
 * @ created 10.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class ListTechnoBreakAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListTechnoBreakOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListTechnoBreakForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("externalSystemId", filterParams.get(Constants.ADAPTER_UUID));
		query.setParameter("periodic", filterParams.get(Constants.PERIODIC));

		// если задан данных флажок то отображаются только действующие перерывы(по умолчанию установлен)
		query.setParameter("onlyWorking", filterParams.get(Constants.SHOW_WORKING));

		Date fromDate = (Date) filterParams.get(Constants.FROM_DATE);
		Date fromTime = (Date) filterParams.get(Constants.FROM_TIME);
		Date toDate = (Date) filterParams.get(Constants.TO_DATE);
		Date toTime = (Date) filterParams.get(Constants.TO_TIME);

		try
		{
			query.setParameter("fromDate", DateHelper.createCalendar(fromDate, fromTime));
			query.setParameter("toDate", DateHelper.createCalendar(toDate, toTime));
		}
		catch(ParseException e)
		{
			throw new BusinessException(e);
		}

		query.setParameter("message", filterParams.get(Constants.MESSAGE));
		query.setParameter("autoBreaks", ConfigFactory.getConfig(AutoTechnoBreakConfig.class).isManualRemovingAutoTechnoBreak());
		query.setParameter("currentDate", Calendar.getInstance());
	}
	
	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemoveTechnoBreakOperation.class);
	}

	protected ActionMessages doRemove(RemoveEntityOperation operation, Long id) throws Exception
	{
		ActionMessages msgs = new ActionMessages();
		try
		{
			super.doRemove(operation, id);
		}
		catch (BusinessLogicException ble)
		{
			msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ble.getMessage(), false));
		}
		return msgs;
	}

	protected Map<String, Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)
			throws BusinessException, BusinessLogicException
	{
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(Constants.SHOW_WORKING, Boolean.TRUE);
		result.put(Constants.PERIODIC, PeriodicType.SINGLE.toString());

		Calendar currentDate = DateHelper.getCurrentDate();
		result.put(Constants.FROM_DATE, DateHelper.toDate(currentDate));

		return result;
	}
}
