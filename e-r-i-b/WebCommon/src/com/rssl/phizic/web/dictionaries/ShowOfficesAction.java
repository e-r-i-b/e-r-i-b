package com.rssl.phizic.web.dictionaries;

import com.rssl.common.forms.Form;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.offices.common.CodeImpl;
import com.rssl.phizic.business.dictionaries.offices.common.DepartmentImpl;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.dictionaries.GetOfficesOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 18.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class ShowOfficesAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		ApplicationConfig applicationConfig = ApplicationConfig.getIt();

		GetOfficesOperation operation;
		Application application = applicationConfig.getLoginContextApplication();
		switch (application)
		{
			case PhizIC:
				operation = createOperation(GetOfficesOperation.class, "Offices");
				break;
			case PhizIA:
				operation = createOperation(GetOfficesOperation.class, "OfficesAdmin");
				break;
			case atm:
				operation = createOperation(GetOfficesOperation.class, "Offices");
				break;
			default:
				if(applicationConfig.getApplicationInfo().isMobileApi())
				{
					operation = createOperation(GetOfficesOperation.class, "Offices");
					break;
				}
				throw new UnsupportedOperationException();
		}

		return operation;
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ShowOfficesForm.FILTER_FORM;
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws Exception
	{
		ShowOfficesForm     frm = (ShowOfficesForm) form;
		GetOfficesOperation op  = (GetOfficesOperation) operation;

		frm.setData(op.getOffices(createTemplate(filterParams), frm.getPaginationOffset(), frm.getPaginationSize()));
		frm.setFilters(filterParams);
		updateFormAdditionalData(frm, operation);
	}

	protected Office createTemplate(Map<String, Object> filterParams)
	{
		DepartmentImpl template = new DepartmentImpl();
		template.setName((String) filterParams.get("name"));
		template.setCode(new CodeImpl());
		return template;
	}
}
