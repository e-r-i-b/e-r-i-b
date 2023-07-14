package com.rssl.phizic.web.ext.sbrf.reports;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.departments.ListDepartmentsAction;
import com.rssl.phizic.web.departments.ListDepartmentsForm;

import java.util.Map;

/**
 * @author Mescheryakova
 * @ created 10.06.2010
 * @ $Author$
 * @ $Revision$
 */

public class GetListDepartmentsAction extends ListDepartmentsAction
{

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		ListDepartmentsForm form = (ListDepartmentsForm) frm;

		// заполн€ем параметры отбора подразделений
		Query query = operation.createQuery(getQueryName(form));

		query.setParameter("parent", form.getId());
		query.setParameter("checkChild", true);
		query.setParameter("name",  "");

		fillQuery(query, filterParams);
		form.setAllChildrenDepartmetns(query.executeList());
		form.setFilters(filterParams);
	}

	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "GetTreeChildren";
	}

}
