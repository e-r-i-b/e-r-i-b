package com.rssl.phizic.web.log;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.log.DownloadSystemLogOperation;
import com.rssl.phizic.operations.log.guest.GuestSystemLogOperation;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;
import java.util.Calendar;

/**
 * @author Roshka
 * @ created 15.03.2006
 * @ $Author$
 * @ $Revision$
 */

public class DownloadSystemLogFilterAction extends LogFilterBaseAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		boolean isGuestLog = StringHelper.isNotEmpty((String) frm.getFilter(DownloadSystemLogForm.GUEST_LOG_NAME));
		if (isGuestLog)
		{
			if(checkAccess(GuestSystemLogOperation.class, "LogsService"))
				return createOperation(GuestSystemLogOperation.class, "LogsService");
			else if(checkAccess(GuestSystemLogOperation.class, "LogsServiceEmployee"))
				return createOperation(GuestSystemLogOperation.class, "LogsServiceEmployee");
			else
				return createOperation(GuestSystemLogOperation.class);
		}
		else
		{
			if(checkAccess(DownloadSystemLogOperation.class, "LogsService"))
				return createOperation(DownloadSystemLogOperation.class, "LogsService");
			else if(checkAccess(DownloadSystemLogOperation.class, "LogsServiceEmployee"))
				return createOperation(DownloadSystemLogOperation.class, "LogsServiceEmployee");
			else
				return createOperation(DownloadSystemLogOperation.class);
		}
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return DownloadSystemLogForm.SYSTEM_LOG_FILTER_FORM;
	}

	@Override
	protected void completeParametersForIndex(Query query)
	{
		boolean isGuestLog = (Boolean)query.getParameter(DownloadSystemLogForm.GUEST_LOG_NAME);
		if(!isGuestLog)
			super.completeParametersForIndex(query);
		 //else
		 // у гостевого журнала нет поля application, поэтому ничего не делаем.

	}

	protected String[] getIndexedParameters()
	{
		//SL_APP_DATE_INDEX,  SL_FIO_DATE_INDEX, SL_DI_DATE_INDEX, SL_LOGIN_DATE_INDEX, SL_IP_DATE_INDEX, PK
		return new String[]{"application", "fio", "number", "loginId", "ipAddres", "errorId"};
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		super.fillQuery(query, filterParams);

		String errorId = (String)filterParams.get("errorId");

		query.setParameter("messageType", filterParams.get("messageType"));
		query.setParameter("source", filterParams.get("source"));
		query.setParameter("messageWord", filterParams.get("messageWord"));
		query.setParameter("errorId", errorId == null || errorId.length() == 0 ? null : Long.parseLong(errorId));
		query.setParameter(DownloadSystemLogForm.GUEST_LOG_NAME, filterParams.get(DownloadSystemLogForm.GUEST_LOG_NAME));
		if(StringHelper.isNotEmpty((String)filterParams.get(DownloadSystemLogForm.PHONE_NUMBER_NAME)))
		{
			query.setParameter("phoneNumber", PhoneNumberUtil.getMobileInternationalPhoneNumber((String) filterParams.get(DownloadSystemLogForm.PHONE_NUMBER_NAME)));
		}
	}	

	protected String getLogName()
	{
		return "system-log.html";
	}

	/**
	 * В журнале системных действий дефолтный перод не более 1 часа
 	 * @return
	 */
	protected Calendar getDefaultFilterStartDate()
	{
		Calendar date = Calendar.getInstance();
	    date.add(Calendar.HOUR,-1);
	    return date;
	}
}
