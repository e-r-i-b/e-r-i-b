package com.rssl.phizic.web.ext.sbrf.logging;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.log.DownloadCommonLogOperation;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.log.LogFilterBaseAction;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Erkin
 * @ created 04.04.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Обобщённый журнал
 */
public class DownloadCommonLogAction extends LogFilterBaseAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{

		DownloadCommonLogForm frm = (DownloadCommonLogForm) form;
		DownloadCommonLogOperation operation;
		if(checkAccess(DownloadCommonLogOperation.class, "CommonLogService"))
			operation = createOperation(DownloadCommonLogOperation.class, "CommonLogService");
		else if(checkAccess(DownloadCommonLogOperation.class, "CommonLogServiceEmployee"))
			operation = createOperation(DownloadCommonLogOperation.class, "CommonLogServiceEmployee");
		else
			operation = createOperation(DownloadCommonLogOperation.class);
		operation.initialize(frm.getClientId());
		return operation;
	}

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		// никаких доп. кнопок нет
		return new HashMap<String, String>();
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return DownloadCommonLogForm.FILTER_FORM;
	}

	private Boolean getBooleanValueOfParam(Object o)
	{
		return o == null ? Boolean.FALSE : (Boolean) o;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		super.fillQuery(query, filterParams);

		// если ни один из журналов не был выбран, то ставим галки на всех журналах (используется для быстрого поиска)
		if (!getBooleanValueOfParam( filterParams.get("showUserLog")) && !getBooleanValueOfParam(filterParams.get("showSystemLog")) && !getBooleanValueOfParam(filterParams.get("showMessageLog")))
		{
			filterParams.put("showUserLog", true);
			filterParams.put("showSystemLog", true);
			filterParams.put("showMessageLog", true);			
		}
		
		query.setParameter("showUserLog", filterParams.get("showUserLog"));
		query.setParameter("showSystemLog", filterParams.get("showSystemLog"));
		query.setParameter("showMessageLog", filterParams.get("showMessageLog"));
		query.setParameter("sessionId", filterParams.get("sessionId"));
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws DataAccessException, BusinessException, BusinessLogicException
	{
		form.setFromStart(form.isFromStart() && StringHelper.isEmpty((String)form.getFilter("sessionId")));
		super.doFilter(filterParams, operation,  form);
	}

	protected String[] getIndexedParameters()
	{
		//*L_APP_DATE_INDEX, *L_SESSION_DATE_INDEX, *L_FIO_DATE_INDEX, *L_DI_DATE_INDEX, *L_LOGIN_DATE_INDEX
		return new String[]{"application", "sessionId", "fio", "number", "loginId"};
	}

	protected Map<String, Object> getFilterParams(ListFormBase frm, ListEntitiesOperation op)
			throws BusinessException, BusinessLogicException
	{
		DownloadCommonLogOperation operation = (DownloadCommonLogOperation) op;
		if( frm.isFromStart() && operation.getClientPerson() != null)
			return getDefaultFilterParameters(frm, op);
		else
			return super.getFilterParams(frm, op);
	}
    	
	protected Map<String, Object> getDefaultFilterParameters(ListFormBase form, ListEntitiesOperation op) throws BusinessException, BusinessLogicException
	{
		DownloadCommonLogOperation operation = (DownloadCommonLogOperation) op;

		Map<String, Object> map = super.getDefaultFilterParameters(form, operation);
		map.put("showUserLog", true);
		map.put("showSystemLog", true);
		map.put("showMessageLog", true);
		
		Person clientPerson = operation.getClientPerson();
		if (clientPerson != null)
		{
			StringBuilder builder = new StringBuilder();
			builder.append(clientPerson.getSurName());
			builder.append(" ");
			builder.append(clientPerson.getFirstName());
			builder.append(" ");
			builder.append(StringHelper.getEmptyIfNull(clientPerson.getPatrName()));

			map.put("fio", builder.toString());
			PersonDocument priorityDocument = PersonHelper.getPersonPriorityDocument(clientPerson);
			if(priorityDocument == null || (priorityDocument.getDocumentType() != PersonDocumentType.PASSPORT_WAY && !priorityDocument.getDocumentMain()))
			{
				throw new BusinessException("Не найден документ удостоверяющий личность для персоны с id = " + clientPerson.getId());
			}

			map.put("series", priorityDocument.getDocumentSeries());
			map.put("number", priorityDocument.getDocumentNumber());
			map.put("birthday", clientPerson.getBirthDay().getTime());
			map.put("TB", operation.getClientTB());
			map.put("withChildren", true);
		}

		return map;
	}

	protected Calendar getDefaultFilterStartDate()
	{
		Calendar date = Calendar.getInstance();
		date.add(Calendar.HOUR, -1);
		return date;
	}

	protected String getLogName()
	{
		return "common-log.html";
	}
}
