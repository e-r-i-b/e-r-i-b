package com.rssl.phizic.test;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.operations.restrictions.RestrictionProvider;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.operations.restrictions.RestrictionProviderImpl;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.auth.CommonLogin;

/**
 * @author Roshka
 * @ created 18.04.2006
 * @ $Author$
 * @ $Revision$
 */

public class MockRestrictionProvider implements RestrictionProvider
{
	public Restriction get(Service serviceId, OperationDescriptor operationDescriptor) throws BusinessException
	{
		return new RestrictionProviderImpl().getDefault(operationDescriptor);
	}

	public Restriction get(CommonLogin login, Service service, OperationDescriptor operationDescriptor) throws BusinessException
	{
		return new RestrictionProviderImpl().getDefault(operationDescriptor);
	}
}