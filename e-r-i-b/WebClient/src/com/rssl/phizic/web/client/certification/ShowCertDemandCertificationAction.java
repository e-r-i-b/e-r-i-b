package com.rssl.phizic.web.client.certification;

import com.rssl.phizic.operations.certification.GetCertDemandListOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;

import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 17.11.2006 Time: 12:15:49 To change this template use
 * File | Settings | File Templates.
 */
public class ShowCertDemandCertificationAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		GetCertDemandListOperation operation = createOperation(GetCertDemandListOperation.class);
		operation.initialize(AuthenticationContext.getContext().getLogin());

		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		GetCertDemandListOperation op = (GetCertDemandListOperation) operation;
		frm.setData(op.getPersonCertDemands());
	}
}
