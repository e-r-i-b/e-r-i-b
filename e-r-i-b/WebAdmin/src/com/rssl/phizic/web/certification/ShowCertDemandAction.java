package com.rssl.phizic.web.certification;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.certification.GetCertDemandListAdminOperation;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 20.11.2006 Time: 15:25:24 To change this template use
 * File | Settings | File Templates.
 */
public class ShowCertDemandAction extends SaveFilterParameterAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetCertDemandListAdminOperation.class);
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams)
	{
		Boolean signed = null;
		if(!(filterParams.get("signed") == null || ((String) filterParams.get("signed")).length()==0))
			signed = ((filterParams.get("signed")).equals("1"))?true:false;
		query.setParameter("firstName",filterParams.get("firstName"));
		query.setParameter("surName",filterParams.get("surName") );
		query.setParameter("patrName",filterParams.get("patrName") );
		query.setParameter("status",filterParams.get("status") );
		query.setParameter("issueDateTo",filterParams.get("toDate") );
		query.setParameter("issueDateFrom",filterParams.get("fromDate") );
		query.setParameter("signed",signed);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ShowCertDemandForm.FILTER_FORM;
	}
}
