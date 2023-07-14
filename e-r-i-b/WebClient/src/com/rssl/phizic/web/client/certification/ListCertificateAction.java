package com.rssl.phizic.web.client.certification;

import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.operations.certification.GetCertificateListOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.common.forms.Form;

import java.util.Map;
import java.util.Date;

/**
 * @author Omeliyanchuk
 * @ created 28.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class ListCertificateAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation( GetCertificateListOperation.class );
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListCertificateForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("startDate", DateHelper.toCalendar((Date)filterParams.get("startDate")));
		query.setParameter("endDate"  , DateHelper.toCalendar(DateHelper.add((Date) filterParams.get("endDate"), 0, 0, 1)));
		query.setParameter("status"   , filterParams.get("status"));
		query.setMaxResults(webPageConfig().getListLimit() + 1);
	}
}
