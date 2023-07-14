package com.rssl.phizic.web.log.csa;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.log.MessageLogAction;
import com.rssl.phizic.web.log.MessageLogForm;
import org.apache.commons.lang.BooleanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author vagin
 * @ created 18.10.2012
 * @ $Author$
 * @ $Revision$
 * Экшен просмотра журнала сообщений ЦСА
 */
public class ViewCSAMessageLogAction extends MessageLogAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("ViewCSAMessageLogOperation");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ViewCSAMessageLogForm.CSA_MESSAGE_LOG_FILTER_FORM;
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation)
	{
		MessageLogForm frm = (MessageLogForm) form;
		frm.setFromStart(frm.isFromStart());
		List<String> systemList = new ArrayList<String>();
		systemList.add(System.jdbc.toString());
		systemList.add(System.iPas.toString());
		systemList.add(System.CSA2.toString());
		systemList.add(System.CSA_MQ.toString());
		systemList.add(System.dasreda.toString());
		systemList.add(System.way4.toString());
		systemList.add(System.rsa.toString());
		frm.setSystemList(systemList);
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

			query.setParameter("type", filterParams.get("type") );
			query.setParameter("application", filterParams.get("application") );
			query.setParameter("ipAddres", filterParams.get("ipAddres") );
			query.setParameter("login", filterParams.get("login"));
			query.setParameter("fio", filterParams.get("fio"));
			query.setParameter("departmentCode", filterParams.get("departmentCode"));
			query.setParameter("birthday", filterParams.get("birthday"));
			query.setParameter("number", filterParams.get("number"));
			query.setParameter("system", filterParams.get("system"));
			query.setParameter("requestTag", filterParams.get("requestTag"));
			//Поиск по слову в запросе/ответе выполняем в двух кодировках: UTF-8 и windows-1251, т.к. кодировка не регламентирована.
			//К тому же могут приходить некорректные xml, как сейчас из ЦСА: в заголовке encoding="UTF-8", но сама кириллица в файле в windows-1251.
			query.setParameter("requestWordWin1251", filterParams.get("requestWord"));
			query.setParameter("requestWordUTF8", getWordUTF8((String)filterParams.get("requestWord")));
			query.setParameter("responceWordWin1251", filterParams.get("responceWord"));
			query.setParameter("responceWordUTF8", getWordUTF8((String)filterParams.get("responceWord")));
			query.setParameter("requestresponceWordWin1251", filterParams.get("requestresponceWord"));
			query.setParameter("requestresponceWordUTF8", getWordUTF8((String)filterParams.get("requestresponceWord")));
			query.setParameter("promoterId", filterParams.get("promoterId"));
			query.setParameter("mGUID", filterParams.get("mGUID"));
			query.setParameter("requestState", filterParams.get("requestState"));
			query.setParameter("ipAddress", filterParams.get("ipAddress"));
			Boolean isGuest = (Boolean)filterParams.get("isGuestLog");
			if(BooleanUtils.isTrue(isGuest))
			{
				query.setParameter("phone", PhoneNumberUtil.getMobileInternationalPhoneNumber((String) filterParams.get(MessageLogForm.PHONE_NUMBER_NAME)));
				query.setParameter("isGuestLog", filterParams.get("isGuestLog"));
			}
		}
	}

	protected String[] getIndexedParameters()
	{
		return new String[]{"fio", "number", "operationUID", "application"};
	}

	protected String getLogName()
	{
		return "csa-message-log.html";
	}
}
