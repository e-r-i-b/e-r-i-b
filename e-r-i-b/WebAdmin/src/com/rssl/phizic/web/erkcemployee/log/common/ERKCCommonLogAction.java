package com.rssl.phizic.web.erkcemployee.log.common;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ERKCNotFoundClientBusinessException;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.log.DownloadCommonLogOperation;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.ext.sbrf.logging.DownloadCommonLogAction;
import com.rssl.phizic.web.util.ERKCEmployeeUtil;

import java.util.Map;

/**
 * @author akrenev
 * @ created 31.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class ERKCCommonLogAction extends DownloadCommonLogAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		return createOperation(DownloadCommonLogOperation.class, "CommonLogServiceEmployeeUseClientForm");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ERKCCommonLogForm.FILTER_FORM;
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation)
	{
		ERKCEmployeeUtil.addUserDataForERKC(form, ERKCEmployeeUtil.getUserInfo());
	}

	protected Map<String, Object> getDefaultFilterParameters(ListFormBase form, ListEntitiesOperation op) throws BusinessLogicException, BusinessException
	{
		Map<String, Object> parameters = super.getDefaultFilterParameters(form, op);
		Map<String, Object> filterParameters = ERKCEmployeeUtil.getUserInfo();
		if (!filterParameters.isEmpty())
		{
			parameters.put("number", filterParameters.remove("passportNumber"));
			parameters.put("series", filterParameters.remove("passportSeries"));
			parameters.putAll(filterParameters);
		}
		return parameters;
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
		super.doFilter(filterParams, operation,  form);
		updateFormAdditionalData(form, operation);
	}
}