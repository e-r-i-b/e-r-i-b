package com.rssl.phizic.web.log;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.log.LoggingJournalOperation;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * @author komarov
 * @ created 03.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class LogEntriesAction extends LogFilterBaseAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		if (checkAccess(LoggingJournalOperation.class, "LoggingJournalServiceEmployee"))
		{
			return createOperation(LoggingJournalOperation.class, "LoggingJournalServiceEmployee");
		}
		else if (checkAccess(LoggingJournalOperation.class, "LoggingJournalService"))
		{
			return createOperation(LoggingJournalOperation.class, "LoggingJournalService");
		}
		else
		{
			return createOperation(LoggingJournalOperation.class);
		}
	}

	protected String getLogName()
	{
		return null;
	}

	protected void completeParametersForIndex(Query query)
	{
		//¬ журнале регистрации входов нет индекса на приложение. ничего не делаем.
	}

	protected String[] getIndexedParameters()
	{
		//RL_FIO_DATE_INDEX, RL_LOGIN_DATE_INDEX, RL_DUL_DATE_INDEX
		return new String[]{"fio", "loginId", "dul"};
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		super.fillQuery(query, filterParams);
		query.setParameter("dul", filterParams.get(LogEntriesForm.DOCUMENT_FIELD_NAME));

	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return LogEntriesForm.FILTER_FORM;
	}
}
