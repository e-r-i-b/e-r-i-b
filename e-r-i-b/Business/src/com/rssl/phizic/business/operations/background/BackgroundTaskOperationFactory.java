package com.rssl.phizic.business.operations.background;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.operations.OperationFactoryImpl;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.operations.restrictions.RestrictionProvider;
import com.rssl.phizic.business.services.Service;

/**
 * @author niculichev
 * @ created 31.08.2011
 * @ $Author$
 * @ $Revision$
 */
public class BackgroundTaskOperationFactory extends OperationFactoryImpl
{
	private CommonLogin login;

	public BackgroundTaskOperationFactory(CommonLogin login, RestrictionProvider restrictionProvider)
	{
		super(restrictionProvider);
		this.login = login;
	}

	protected Restriction getRestriction(OperationDescriptor od, Service service) throws BusinessException
	{
		if (od.getRestrictionInterfaceName() == null)
			return null;

		return restrictionProvider.get(login, service, od);
	}

	protected boolean isCheckAccess()
	{
		return false;
	}
}
