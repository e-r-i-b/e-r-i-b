package com.rssl.phizic.web.erkcemployee.mail;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ERKCNotFoundClientBusinessException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.mail.ListMailOperation;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.ext.sbrf.mail.ListSentMailAction;
import com.rssl.phizic.web.util.ERKCEmployeeUtil;

import java.util.Map;

/**
 * @author akrenev
 * @ created 02.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class ERKCOutgoingMailAction extends ListSentMailAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListMailOperation.class, "MailManagementUseClientForm");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ERKCOutgoingMailForm.FILTER_FORM;
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation)
	{
		ERKCEmployeeUtil.addUserDataForERKC(form, ERKCEmployeeUtil.getUserInfo());
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws Exception
	{
		Map<String, Object> filterParameters = ERKCEmployeeUtil.getUserInfo();
		if (filterParameters.isEmpty())
		{
			throw new ERKCNotFoundClientBusinessException("Для того чтобы выполнить поиск записей, перейдите к журналу из анкеты клиента.");
		}
		filterParams.putAll(filterParameters);
		super.doFilter(filterParams, operation, form);
	}
}
