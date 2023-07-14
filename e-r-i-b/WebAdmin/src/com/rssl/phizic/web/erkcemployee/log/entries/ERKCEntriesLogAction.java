package com.rssl.phizic.web.erkcemployee.log.entries;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ERKCNotFoundClientBusinessException;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.log.LoggingJournalOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.log.LogEntriesAction;
import com.rssl.phizic.web.util.ERKCEmployeeUtil;

import java.util.Map;

/**
 * @author akrenev
 * @ created 01.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ERKCEntriesLogAction extends LogEntriesAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		return createOperation(LoggingJournalOperation.class, "LoggingJournalServiceEmployeeUseClientForm");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ERKCEntriesLogForm.FILTER_FORM;
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation)
	{
		ERKCEmployeeUtil.addUserDataForERKC(form, ERKCEmployeeUtil.getUserInfo());
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws BusinessException, DataAccessException, BusinessLogicException
	{
		Map<String, Object> filterParameters = ERKCEmployeeUtil.getUserInfo();
		if (filterParameters.isEmpty())
		{
			throw new ERKCNotFoundClientBusinessException("Для того чтобы выполнить поиск записей, перейдите к журналу из анкеты клиента.");
		}
		filterParams.put("loginId", ERKCEmployeeUtil.getUserLoginId());
		filterParams.put("fio", filterParameters.get("fio"));
		filterParams.put("birthday", filterParameters.get("birthday"));
		filterParams.put("dul", StringHelper.getEmptyIfNull(filterParameters.get("passportSeries")) + StringHelper.getEmptyIfNull(filterParameters.get("passportNumber")));
		super.doFilter(filterParams, operation, form);
		updateFormAdditionalData(form, operation);
	}

	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "list.erkc";
	}

}
