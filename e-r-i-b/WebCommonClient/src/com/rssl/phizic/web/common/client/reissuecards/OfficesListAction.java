package com.rssl.phizic.web.common.client.reissuecards;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.departments.GetAllowedReissueCardOfficesOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * Список подразделений, в которых возможен перевыпуск карт.
 *
 * @author bogdanov
 * @ created 21.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class OfficesListAction extends ListActionBase
{
	private static final String FILTER_PARAM = "officeInfo";

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		GetAllowedReissueCardOfficesOperation operation = createOperation(GetAllowedReissueCardOfficesOperation.class, "ReIssueCardClaim");
		operation.initialize(getFilterString(frm));
		return operation;
	}

    protected String getFilterString(ListFormBase frm)
    {
        return (String) frm.getFilter(FILTER_PARAM);
    }

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		GetAllowedReissueCardOfficesOperation op = (GetAllowedReissueCardOfficesOperation) operation;
		OfficesListForm form = (OfficesListForm) frm;

		form.setData(op.getOffices());
	}
}
