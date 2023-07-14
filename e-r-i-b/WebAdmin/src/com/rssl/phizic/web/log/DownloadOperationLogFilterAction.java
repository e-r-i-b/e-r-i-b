package com.rssl.phizic.web.log;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.log.DownloadUserLogOperation;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Calendar;
import java.util.Map;

/**
 * @author Roshka
 * @ created 15.03.2006
 * @ $Author$
 * @ $Revision$
 */

public class DownloadOperationLogFilterAction extends LogFilterBaseAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		if(checkAccess(DownloadUserLogOperation.class, "LogsService"))
			return createOperation(DownloadUserLogOperation.class, "LogsService");
		else if(checkAccess(DownloadUserLogOperation.class, "LogsServiceEmployee"))
			return createOperation(DownloadUserLogOperation.class, "LogsServiceEmployee");
		else
			return createOperation(DownloadUserLogOperation.class);

	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return DownloadLogFilterForm.OPERATION_LOG_FILTER_FORM;
	}

	protected String[] getIndexedParameters()
	{
		//UL_APP_DATE_INDEX,  UL_FIO_DATE_INDEX, UL_DI_DATE_INDEX, UL_LOGIN_DATE_INDEX
		return new String[]{"application", "fio", "number", "loginId"};
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		super.fillQuery(query, filterParams);

		query.setParameter(DownloadLogFilterForm.GUEST_LOG_NAME, filterParams.get(DownloadLogFilterForm.GUEST_LOG_NAME));
		if(StringHelper.isNotEmpty((String) filterParams.get(DownloadLogFilterForm.PHONE_NUMBER_NAME)))
		{
			query.setParameter("phoneNumber", PhoneNumberUtil.getMobileInternationalPhoneNumber((String) filterParams.get(DownloadLogFilterForm.PHONE_NUMBER_NAME)));
		}
	}

	@Override
	protected void completeParametersForIndex(Query query)
	{
		boolean isGuestLog = (Boolean)query.getParameter(DownloadLogFilterForm.GUEST_LOG_NAME);
		if(!isGuestLog)
			super.completeParametersForIndex(query);
		//else
		// у гостевого журнала нет поля application, поэтому ничего не делаем.

	}

	protected String getLogName()
	{
		return "operations-log.html";
	}

	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		boolean isGuestLog = StringHelper.isNotEmpty((String) frm.getFilter(DownloadLogFilterForm.GUEST_LOG_NAME));
		return isGuestLog ? "guestList" : "list";
	}

	/**
	 * В журнале действий пользователя дефолтный перод не более 1 часа
 	 * @return
	 */
	protected Calendar getDefaultFilterStartDate()
	{
		Calendar date = Calendar.getInstance();
	    date.add(Calendar.HOUR,-1);
	    return date;
	}
}
