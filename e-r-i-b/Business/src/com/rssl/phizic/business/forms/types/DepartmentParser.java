package com.rssl.phizic.business.forms.types;

import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.BusinessException;

import java.text.ParseException;

/**
 * @author Omeliyanchuk
 * @ created 04.02.2008
 * @ $Author$
 * @ $Revision$
 */

public class DepartmentParser implements FieldValueParser<Department>
{
	public Department parse(String value) throws ParseException
	{
		try
		{
			Long departmentId = Long.parseLong( value );
			Department department = new DepartmentService().findById( departmentId );
			return department;
		}
		catch (NumberFormatException e)
		{
			throw new ParseException(e.getMessage(), 0);
		}
		catch(BusinessException be)
		{
			throw new ParseException(be.getMessage(),0);
		}
	}
}
