package com.rssl.phizic.web.erkcemployee.audit;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ERKCNotFoundClientBusinessException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.payment.GetEmployeePaymentListOperation;
import com.rssl.phizic.web.audit.ShowBusinessDocumentListAction;
import com.rssl.phizic.web.audit.ShowBusinessDocumentListForm;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.util.ERKCEmployeeUtil;

import java.util.Map;

/**
 * @author akrenev
 * @ created 27.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class ERKCShowBusinessDocumentListAction extends ShowBusinessDocumentListAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessLogicException, BusinessException
	{
		return createOperation(GetEmployeePaymentListOperation.class, "ViewPaymentListUseClientForm");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ERKCShowBusinessDocumentListForm.FILTER_FORM;
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException
	{
		super.updateFormAdditionalData(form, operation);
		ERKCEmployeeUtil.addUserDataForERKC(form, ERKCEmployeeUtil.getUserInfo());
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws Exception
	{
		Long userLoginId = ERKCEmployeeUtil.getUserLoginId();
		if (userLoginId == null)
		{
			throw new ERKCNotFoundClientBusinessException("Для того чтобы выполнить поиск записей, перейдите к журналу из анкеты клиента.");
		}
		filterParams.put(ShowBusinessDocumentListForm.LOGIN_ID_FIELD_NAME, userLoginId);
		super.doFilter(filterParams, operation, form);
	}
}
