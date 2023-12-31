package com.rssl.phizic.web.erkcemployee.log.system;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ERKCNotFoundClientBusinessException;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.log.DownloadSystemLogOperation;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.log.DownloadSystemLogFilterAction;
import com.rssl.phizic.web.util.ERKCEmployeeUtil;

import java.util.Map;

/**
 * @author akrenev
 * @ created 01.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ERKCSystemLogAction extends DownloadSystemLogFilterAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(DownloadSystemLogOperation.class, "LogsServiceEmployeeUseClientForm");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ERKCSystemLogForm.FILTER_FORM;
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation)
	{
		ERKCEmployeeUtil.addUserDataForERKC(form, ERKCEmployeeUtil.getUserInfo());
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws DataAccessException, BusinessException, BusinessLogicException
	{
		Map<String, Object> filterParameters = ERKCEmployeeUtil.getUserInfo();
		if (filterParameters.isEmpty())
		{
			throw new ERKCNotFoundClientBusinessException("��� ���� ����� ��������� ����� �������, ��������� � ������� �� ������ �������.");
		}
		filterParams.put("number", filterParameters.remove("passportNumber"));
		filterParams.put("series", filterParameters.remove("passportSeries"));
		filterParams.putAll(filterParameters);
		super.doFilter(filterParams, operation,  form);
		updateFormAdditionalData(form, operation);
	}
}
