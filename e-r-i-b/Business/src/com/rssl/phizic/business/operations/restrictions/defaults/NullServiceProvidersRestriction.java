package com.rssl.phizic.business.operations.restrictions.defaults;

import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.restrictions.ServiceProvidersRestriction;
import org.hibernate.Session;

/**
 * @author krenev
 * @ created 16.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class NullServiceProvidersRestriction implements ServiceProvidersRestriction
{
	public boolean accept(ServiceProviderBase provider) throws BusinessException
	{
		return true;
	}

	public void applyFilter(Session session)
	{
	}
}
