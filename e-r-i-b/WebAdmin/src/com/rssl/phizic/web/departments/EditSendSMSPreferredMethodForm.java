package com.rssl.phizic.web.departments;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.*;

/**
 * @author Pankin
 * @ created 18.11.2011
 * @ $Author$
 * @ $Revision$
 */

public class EditSendSMSPreferredMethodForm extends ActionFormBase
{
	private Set<Department> departments;
	private Map<String, String> departmentMethods = new HashMap<String, String>();;

	public Set<Department> getDepartments()
	{
		return Collections.unmodifiableSet(departments);
	}

	public void setDepartments(List<Department> departments)
	{
		this.departments = new HashSet<Department>(departments);
	}

	public Map<String, String> getDepartmentMethods()
	{
		return departmentMethods;
	}

	public void setDepartmentMethods(Map<String, String> departmentMethods)
	{
		this.departmentMethods = departmentMethods;
	}
}
