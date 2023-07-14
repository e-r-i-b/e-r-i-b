package com.rssl.phizic.business.operations.restrictions.defaults;

import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.operations.restrictions.EmployeeRestriction;

/**
 * @author hudyakov
 * @ created 27.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class NullEmployeeRestriction implements EmployeeRestriction
{
	public static final NullEmployeeRestriction INSTANCE = new NullEmployeeRestriction();

	public NullEmployeeRestriction()
	{
	}

	public boolean accept(Employee person)
	{
		return true;
	}
}
