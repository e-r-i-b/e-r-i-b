package com.rssl.phizic.web.log.csa;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.logging.operations.CSAOperations;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.log.LogFilterBaseAction;

import java.util.Calendar;
import java.util.Map;

/**
 * @author vagin
 * @ created 25.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class ViewCSAOperationLogAction extends LogFilterBaseAction
{
	protected String[] getIndexedParameters()
	{
		return new String[]{"state", "type"};
	}

	protected String getLogName()
	{
		return "csa-operation-log.html";
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation("ViewCSAOperationLogOperation","CSALogMangement");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ViewCSAOperationLogForm.OPERATION_LOG_FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		fillQueryBase(query, filterParams);
		//используем в запросе только те типы операций что описаны в CSAOperations - это операции
		// для логирования, для фильтра и для отображения с описанием.
		if(!StringHelper.isEmpty((String)filterParams.get("type")))
			query.setParameter("type", filterParams.get("type") );
		else
			query.setParameterList("type", CSAOperations.getStringValues());
		query.setParameter("state", filterParams.get("state") );
		query.setParameter("login", filterParams.get("login"));
		query.setParameter("fio", filterParams.get("fio"));
		query.setParameter("departmentCode", filterParams.get("departmentCode"));
		query.setParameter("birthday", filterParams.get("birthday"));
		query.setParameter("number", filterParams.get("number"));
	}

	protected Calendar getDefaultFilterStartDate()
	{
		Calendar date = Calendar.getInstance();
		date.add(Calendar.HOUR, -1);
		return date;
	}
}
