package com.rssl.phizic.web.audit;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.log.MessageLogAction;

import java.util.Map;

/**
 * @author akrenev
 * @ created 03.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class AuditMessageLogAction extends MessageLogAction
{
	private static final String OPERATION_UID = "operationUID";
	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return AuditMessageLogForm.MESSAGE_LOG_FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		super.fillQuery(query, filterParams);
		query.setParameter(OPERATION_UID, filterParams.get(OPERATION_UID));
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation)
	{
		super.updateFormAdditionalData(form, operation);
		form.setFromStart(false);
	}

	@Override
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws DataAccessException, BusinessException, BusinessLogicException
	{
		if (filterParams.get(OPERATION_UID) == null)
			throw new DataAccessException("Переход в журнал сообщений без указания параметра \"operationUID\" недопустим.");
    	super.doFilter(filterParams, operation, form);
	}
}
