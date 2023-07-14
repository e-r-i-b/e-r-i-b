package com.rssl.phizic.web.configure.exceptions;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.exception.ExceptionEntryType;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.config.exceptions.ExceptionEntryEditOperation;
import com.rssl.phizic.operations.config.exceptions.ExceptionEntryListOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * @author mihaylov
 * @ created 17.04.2013
 * @ $Author$
 * @ $Revision$
 * Ёкшен отображени€ справочника маппинга ошибок
 */
public class ExceptionEntryListAction extends ListActionBase
{

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ExceptionEntryListForm form = (ExceptionEntryListForm) frm;
		ExceptionEntryListOperation operation = createOperation(ExceptionEntryListOperation.class);
		operation.initialize(form.isDecoratedException(), ExceptionEntryType.valueOf(getCurrentMapping().getParameter()));
		return operation;
	}

	@Override
	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ExceptionEntryEditOperation.class);						
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ExceptionEntryListForm.FILTER_FORM;
	}

	@Override
	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation) throws Exception
	{
		super.updateFormAdditionalData(frm, operation);
		((ExceptionEntryListForm) frm).setExceptionEntryType(ExceptionEntryType.valueOf(getCurrentMapping().getParameter()));
	}

	@Override
	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("id",filterParams.get("id"));
		query.setParameter("operationType",filterParams.get("operationType"));
		query.setParameter("application",filterParams.get("application"));
		query.setParameter("system",filterParams.get("system"));
		query.setParameter("errorCode",filterParams.get("errorCode"));
		query.setMaxResults(webPageConfig().getListLimit() + 1);
	}
}
