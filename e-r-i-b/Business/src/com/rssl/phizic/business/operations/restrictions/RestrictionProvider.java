package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.auth.CommonLogin;

/**
 * @author Roshka
 * @ created 18.04.2006
 * @ $Author$
 * @ $Revision$
 */

public interface RestrictionProvider
{
	Restriction get(Service service, OperationDescriptor operationDescriptor) throws BusinessException;

	Restriction get(CommonLogin login, Service service, OperationDescriptor operationDescriptor) throws BusinessException;
}
