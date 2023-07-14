package com.rssl.phizic.business.dictionaries.offices.common;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.gate.dictionaries.officies.Code;

import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 19.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class DepartmentImpl extends Department
{
	public DepartmentImpl()
	{
		code = new CodeImpl();
	}

	public Code getCode()
	{
		return code;
	}

	public void setCode(Code code)
	{
		if (code == null)
			return;
		this.code = new CodeImpl(code);
	}

	public void buildCode(Map<String, Object> codeFields)
	{
		code = new CodeImpl((String) codeFields.get("officeId"));
	}

	public String getFullName()
	{
		return getName();
	}
}
