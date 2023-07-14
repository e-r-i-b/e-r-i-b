package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * @author khudyakov
 * @ created 01.07.14
 * @ $Author$
 * @ $Revision$
 */
public class OperationEmployeeNameTemplateValidator implements TemplateDocumentFilter
{
	public static final String EMPLOYEE_NAME_ATTRIBUTE_NAME             = "employeeStateChange";


	private String filterName;

	public OperationEmployeeNameTemplateValidator(Map<String, Object> params)
	{
		filterName = (String) params.get(EMPLOYEE_NAME_ATTRIBUTE_NAME);
	}

	public boolean accept(TemplateDocument template)
	{
		if (StringHelper.isEmpty(filterName))
		{
			return true;
		}

		try
		{
			String employeeName = template.getConfirmedEmployeeInfo().getPersonName().getFullName();
			if (StringHelper.isEmpty(employeeName))
			{
				return true;
			}

			return employeeName.toUpperCase().replace(" ", "").contains(filterName.toUpperCase().replace(" ", ""));
		}
		catch (GateException e)
		{
			throw new IllegalArgumentException(e);
		}
	}
}
