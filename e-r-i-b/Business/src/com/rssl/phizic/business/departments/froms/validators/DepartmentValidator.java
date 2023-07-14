package com.rssl.phizic.business.departments.froms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author komarov
 * @ created 10.11.2011
 * @ $Author$
 * @ $Revision$
 *
 * Валидатор доступности подразделений
 */

public class DepartmentValidator extends TBValidator
{
	/**
	 * конструктор
	 */
	public DepartmentValidator(){}

	private List<Long> parse(List<String> ids)
	{
		List<Long> departmentIds = new ArrayList<Long>();
		for(String id : ids)
		{
			departmentIds.add(Long.parseLong(id));
		}
		return departmentIds;
	}

	protected boolean isIdsValid(List<String> ids) throws TemporalDocumentException
	{
		try
		{
			return AllowedDepartmentsUtil.isDepartmentsAllowed(parse(ids));
		}
		catch (BusinessException be)
		{
			throw new TemporalDocumentException(be);
		}
	}
}
