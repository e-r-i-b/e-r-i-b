package com.rssl.phizic.business.operations.restrictions.defaults;

import com.rssl.phizic.business.operations.restrictions.DepartmentRestriction;
import com.rssl.phizic.business.BusinessException;

/**
 * @author egorova
 * @ created 27.04.2009
 * @ $Author$
 * @ $Revision$
 */
public class NullDepartmentRestriction implements DepartmentRestriction
{
	public boolean accept(Long departmentId) throws BusinessException
	{
		return true;
	}
}
