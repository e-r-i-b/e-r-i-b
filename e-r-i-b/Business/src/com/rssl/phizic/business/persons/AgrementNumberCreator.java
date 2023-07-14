package com.rssl.phizic.business.persons;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.BusinessException;

/**
 * @author Omeliyanchuk
 * @ created 19.12.2006
 * @ $Author$
 * @ $Revision$
 */

public interface AgrementNumberCreator
{
	void init(Department department);
	String getNextAgreementNumber() throws BusinessException;
}
