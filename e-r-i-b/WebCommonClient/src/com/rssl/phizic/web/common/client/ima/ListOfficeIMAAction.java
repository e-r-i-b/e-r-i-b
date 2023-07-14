package com.rssl.phizic.web.common.client.ima;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.ima.GetIMAOfficeListOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * @author Mescheryakova
 * @ created 16.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListOfficeIMAAction  extends ListActionBase
{
	private static final String FILTER_PARAM = "officeInfo";

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ListOfficeIMAForm form = (ListOfficeIMAForm) frm;
		GetIMAOfficeListOperation operation = createOperation(GetIMAOfficeListOperation.class, "IMAOpeningClaim");
		operation.initialize(form.getParentSynchKey());
		return operation;
	}

    protected String getFilterString(ListFormBase frm)
    {
        return (String) frm.getFilter(FILTER_PARAM);
    }

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		Query query = operation.createQuery("list");
		query.setParameter("name", getFilterString(frm));
		frm.setData(query.executeList());
	}
}
