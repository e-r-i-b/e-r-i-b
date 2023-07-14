package com.rssl.phizic.web.erkcemployee.log.message;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ERKCNotFoundClientBusinessException;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.log.MessageLogOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.log.MessageLogAction;
import com.rssl.phizic.web.util.ERKCEmployeeUtil;

import java.util.Map;

/**
 * @author akrenev
 * @ created 01.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ERKCMessageLogAction extends MessageLogAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		return createOperation(MessageLogOperation.class, "MessageLogServiceEmployeeUseClientForm");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ERKCMessageLogForm.FILTER_FORM;
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation)
	{
		super.updateFormAdditionalData(form, operation);
		ERKCEmployeeUtil.addUserDataForERKC(form, ERKCEmployeeUtil.getUserInfo());
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		super.fillQuery(query, filterParams);
		query.setParameter("operationUID", filterParams.get("operationUID"));
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws DataAccessException, BusinessException, BusinessLogicException
	{
		Map<String, Object> filterParameters = ERKCEmployeeUtil.getUserInfo();
		if (filterParameters.isEmpty())
		{
			throw new ERKCNotFoundClientBusinessException("Для того чтобы выполнить поиск записей, перейдите к журналу из анкеты клиента.");
		}
		filterParams.put("number", filterParameters.remove("passportNumber"));
		filterParams.put("series", filterParameters.remove("passportSeries"));
		filterParams.putAll(filterParameters);

		String operationUID = (String) filterParams.get("operationUID");
		if (!StringHelper.isEmpty(operationUID))
		{
			filterParams.put("operationUID", operationUID);
			form.setFromStart(false);
		}

		super.doFilter(filterParams, operation,  form);
		updateFormAdditionalData(form, operation);
	}
}
