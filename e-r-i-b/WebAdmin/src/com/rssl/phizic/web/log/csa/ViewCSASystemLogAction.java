package com.rssl.phizic.web.log.csa;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.log.csa.ViewCSASystemLogOperation;
import com.rssl.phizic.operations.log.csa.ViewGuestCSASystemLogOperation;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.log.DownloadSystemLogForm;
import com.rssl.phizic.web.log.LogFilterBaseAction;
import org.apache.commons.lang.BooleanUtils;

import java.util.Calendar;
import java.util.Map;

/**
 * @author vagin
 * @ created 10.10.2012
 * @ $Author$
 * @ $Revision$
 * Экшен просмотра журнала системных действий ЦСА
 */
public class ViewCSASystemLogAction extends LogFilterBaseAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		boolean isGuestLog = StringHelper.isNotEmpty((String) frm.getFilter(DownloadSystemLogForm.GUEST_LOG_NAME));
		if (isGuestLog)
		{
			return createOperation(ViewGuestCSASystemLogOperation.class);
		}
		return createOperation(ViewCSASystemLogOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ViewCSASystemLogForm.SYSTEM_LOG_FILTER_FORM;
	}

	protected String[] getIndexedParameters()
	{
		//CSA_SL_APP_DATE_INDEX, CSA_SL_DI_DATE_INDEX, CSA_SL_IP_DATE_INDEX, PK
		return new String[]{"number", "ipAddres", "errorId", "login", "application"};
	}

	@Override
	protected void completeParametersForIndex(Query query)
	{
		boolean isGuestLog = BooleanUtils.isTrue((Boolean)query.getParameter(ViewCSASystemLogForm.GUEST_LOG_NAME)) ;
		if (!isGuestLog)
		{
			super.completeParametersForIndex(query);
		}
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		String logUID = (String)filterParams.get("logUID");
		//если задан logUID - ищем по нему, остальное не важно.
		if(StringHelper.isNotEmpty(logUID))
			query.setParameter("logUID",logUID);
		else
		{
			fillQueryBase(query, filterParams);

			boolean isGuestLog = (Boolean) filterParams.get(ViewCSASystemLogForm.GUEST_LOG_NAME);
			query.setParameter(ViewCSASystemLogForm.GUEST_LOG_NAME, isGuestLog);

			if (isGuestLog)
			{
				String phoneNumber = (String) filterParams.get(DownloadSystemLogForm.PHONE_NUMBER_NAME);
				query.setParameter("phoneNumber", StringHelper.isEmpty(phoneNumber) ? null : PhoneNumberUtil.getMobileInternationalPhoneNumber(phoneNumber));
			}
			else
			{
				query.setParameter("login", StringHelper.isEmpty((String)filterParams.get("login"))?null:filterParams.get("login"));
				query.setParameter("application", filterParams.get("application"));
			}

			query.setParameter("source", filterParams.get("source"));
			query.setParameter("ipAddres", StringHelper.isEmpty((String)filterParams.get("ipAddres"))?null:filterParams.get("ipAddres") );
			query.setParameter("fio", StringHelper.isEmpty((String)filterParams.get("fio"))?null:filterParams.get("fio"));
			query.setParameter("birthday", filterParams.get("birthday"));
			query.setParameter("number", StringHelper.isEmpty((String)filterParams.get("number"))?null:filterParams.get("number"));

			String errorId = StringHelper.isEmpty((String) filterParams.get("errorId"))?null:(String) filterParams.get("errorId");

			query.setParameter("messageType", StringHelper.isEmpty((String)filterParams.get("messageType"))?null:filterParams.get("messageType"));
			query.setParameter("messageWord", StringHelper.isEmpty((String)filterParams.get("messageWord"))?null:filterParams.get("messageWord"));
			query.setParameter("errorId", errorId == null || errorId.length() == 0 ? null : Long.parseLong(errorId));
			query.setParameter("departmentCode", StringHelper.isEmpty((String)filterParams.get("departmentCode"))?null:filterParams.get("departmentCode"));
		}
	}

	protected String getLogName()
	{
		return "csa-system-log.html";
	}

	/**
	 * В журнале системных действий дефолтный перод не более 1 часа
	 * @return
	 */
	protected Calendar getDefaultFilterStartDate()
	{
		Calendar date = Calendar.getInstance();
		date.add(Calendar.HOUR, -1);
		return date;
	}
}
